/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services.impl;

import com.ad_samaria.commons.CommonSvcImpl;
import com.ad_samaria.dto.CertificadoDTO;
import com.ad_samaria.models.Certificado;
import com.ad_samaria.repositories.CertificadoRepository;
import com.ad_samaria.services.CertificadoSvc;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Yocary
 */
@Service
public class CertificadoSvcImpl extends CommonSvcImpl<Certificado, CertificadoRepository> implements CertificadoSvc {

    @Autowired
    private JasperPdfService jasper;

    @Override
    public byte[] generarMembresiaPdf(Map<String, Object> params) {

        System.out.println("DATOS:::: " + params);
        try {
            // Carga/compila el JRXML
            ClassPathResource jr = new ClassPathResource("reportes/certificado_membresia.jrxml");
            try (InputStream in = jr.getInputStream()) {
                JasperReport jasper = JasperCompileManager.compileReport(in);

                // Si tu .jrxml solo pinta texto/imagen,
                // usa un datasource vacío:
                JasperPrint jp = JasperFillManager.fillReport(
                        jasper,
                        params,
                        new JREmptyDataSource()
                );

                return JasperExportManager.exportReportToPdf(jp);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF de Membresía", e);
        }
    }

    @Override
    public Certificado crearCertificadoMembresia(String nombreMiembro, String fechaIso) {
        try {
            Certificado c = new Certificado();

            // fecha
            LocalDate ld = (fechaIso != null && !fechaIso.trim().isEmpty())
                    ? LocalDate.parse(fechaIso)
                    : LocalDate.now();
            c.setFecha(Date.valueOf(ld));

            // tipo
            c.setTipoId(4);

            // observaciones como JSON (no String)
            Map<String, Object> obs = new HashMap<>();
            obs.put("nombreMiembro", nombreMiembro);
            c.setObservaciones(obs);

            return repository.save(c);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear certificado de membresía", e);
        }
    }

    @Override
    public List<CertificadoDTO> listar(String q) {
        List<Object[]> filas = repository.listarComoFilas((q == null || q.trim().isEmpty()) ? null : q.trim());
        return filas.stream().map(r -> {
            Long id = ((Number) r[0]).longValue();
            String miembro = (String) r[1];
            String tipo = (String) r[2];
            LocalDate fecha;
            Object f = r[3];
            if (f instanceof java.sql.Date) {
                fecha = ((java.sql.Date) f).toLocalDate();
            } else if (f instanceof java.sql.Timestamp) {
                fecha = ((java.sql.Timestamp) f).toLocalDateTime().toLocalDate();
            } else {
                fecha = (LocalDate) f;
            }

            return new CertificadoDTO(id, miembro, tipo, fecha);
        }).collect(Collectors.toList());
    }

    public ResultadoPdf generarPdfPorId(Long id) {
        // 1) Traer certificado
        Certificado c = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Certificado no encontrado: " + id));

        // 2) Armar parámetros para la plantilla
        Map<String, Object> params = new HashMap<>();

        // Observaciones (JSONB → Map) se agregan directo
        Map<String, Object> obs = c.getObservaciones();
        if (obs != null && !obs.isEmpty()) {
            params.putAll(obs);
        }

        if (obs.containsKey("fechaBautismo")) {
            String fechaBautismoFormateada = formatearFechaDesdeString((String) obs.get("fechaBautismo"));
            params.put("fechaBautismo", fechaBautismoFormateada);
        }

        if (obs.containsKey("fechaExpedicion")) {
            String fechaExpedicionFormateada = formatearFechaDesdeString((String) obs.get("fechaExpedicion"));
            params.put("fechaExpedicion", fechaExpedicionFormateada);
        }

        String fechaExp = formatearFecha(c.getFecha());
        if (fechaExp != null && !fechaExp.isEmpty() && !params.containsKey("fecha")) {
            params.put("fecha", fechaExp);
        }

        // 3) Resolver plantilla según tipo
        String template = templatePorTipo(c.getTipoId());
        if (template == null || template.trim().isEmpty()) {
            throw new IllegalArgumentException("No se encontró plantilla para tipoId=" + c.getTipoId());
        }

        // 4) Generar PDF
        byte[] pdf = jasper.renderPdf(template, params, java.util.Collections.emptyList());

        String base = nombrePorTipo(c.getTipoId()); // p.ej. "Certificado de Membresía"
        StringBuilder fn = new StringBuilder();
        fn.append(base != null ? base : "certificado");

        if (fechaExp != null && !fechaExp.isEmpty()) {
            fn.append(" (").append(fechaExp).append(")");
        }
        fn.append(".pdf");

        String filename = fn.toString().replaceAll("[\\\\/:*?\"<>|]", "_");

        return new ResultadoPdf(filename, pdf);
    }

    /**
     * Convierte null/objeto a String seguro (sin "null").
     */
    private String safe(Object v) {
        return v == null ? "" : String.valueOf(v).trim();
    }

    private String templatePorTipo(Integer tipoId) {
        // Ajusta estos IDs a tu catálogo real. Mencionaste: Membresía = 4
        if (tipoId == null) {
            throw new IllegalArgumentException("tipo_id nulo");
        }
        switch (tipoId) {
            case 4:
                return "certificado_membresia";     // /reports/certificado_membresia.jrxml
            case 1:
                return "certificado_bautismo";
            case 3:
                return "certificado_niños";         // ojo con la ñ; si el archivo se llama "certificado_niños.jrxml"
            case 2:
                return "certificado_matrimonio";
            default:
                throw new IllegalArgumentException("Tipo no soportado: " + tipoId);
        }
    }

    private String nombrePorTipo(Integer tipoId) {
        if (tipoId == null) {
            return "Certificado";
        }
        switch (tipoId) {
            case 4:
                return "Certificado Membresía";
            case 1:
                return "Certificado Bautismo";
            case 3:
                return "Certificado Presentación de Niños";
            case 2:
                return "Certificado Matrimonio";
            default:
                return "Certificado";
        }
    }

    private String formatearFecha(Object fecha) {
        if (fecha == null) {
            return null;
        }
        try {
            if (fecha instanceof java.util.Date) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                return sdf.format((java.util.Date) fecha);
            }
            if (fecha instanceof LocalDate) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                return ((LocalDate) fecha).format(formatter);
            }
            if (fecha instanceof java.sql.Date) {
                LocalDate localDate = ((java.sql.Date) fecha).toLocalDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                return localDate.format(formatter);
            }
            return String.valueOf(fecha);
        } catch (Exception e) {
            return String.valueOf(fecha);
        }
    }

    public static class ResultadoPdf {

        public final String filename;
        public final byte[] bytes;

        public ResultadoPdf(String filename, byte[] bytes) {
            this.filename = filename;
            this.bytes = bytes;
        }

        public String encodedFilename() {
            try {
                return URLEncoder.encode(filename, StandardCharsets.UTF_8.name());
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("Error al codificar nombre de archivo", e);
            }
        }

    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        boolean existe = repository.existsById(id);
        if (!existe) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Certificado no encontrado: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public byte[] generarBautismoPdf(Map<String, Object> params) {
        try {
            ClassPathResource jr = new ClassPathResource("reportes/certificado_bautismo.jrxml");
            try (InputStream in = jr.getInputStream()) {
                JasperReport jasper = JasperCompileManager.compileReport(in);
                JasperPrint jp = JasperFillManager.fillReport(jasper, params, new JREmptyDataSource());
                return JasperExportManager.exportReportToPdf(jp);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF de Bautismo", e);
        }
    }

    @Override
    public Certificado crearCertificadoBautismo(String nombreMiembro, String fechaBautismoIso, String fechaExpedicionIso) {
        try {
            Certificado c = new Certificado();

            LocalDate ld = (fechaExpedicionIso != null && !fechaExpedicionIso.trim().isEmpty())
                    ? LocalDate.parse(fechaExpedicionIso)
                    : LocalDate.now();
            c.setFecha(java.sql.Date.valueOf(ld));

            c.setTipoId(1);

            // observaciones JSONB
            Map<String, Object> obs = new HashMap<>();
            obs.put("nombreMiembro", nombreMiembro);
            obs.put("fechaBautismo", formatearFecha(fechaBautismoIso));
            obs.put("fechaExpedicion", formatearFecha(fechaExpedicionIso));

            c.setObservaciones(obs);

            return repository.save(c);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear certificado de bautismo", e);
        }
    }

    private String formatearFechaDesdeString(String fechaStr) {
        if (fechaStr == null || fechaStr.trim().isEmpty()) {
            return "";
        }
        try {
            // Parsear la fecha desde formato ISO (yyyy-MM-dd)
            LocalDate fecha = LocalDate.parse(fechaStr);
            // Formatear a dd/MM/yyyy
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return fecha.format(formatter);
        } catch (Exception e) {
            // Si hay error, devolver la fecha original
            return fechaStr;
        }
    }

    @Override
    public Certificado crearCertificadoNinos(
            String nombreMiembro,
            String nombrePadre,
            String nombreMadre,
            String lugarFechaNacimiento,
            String fechaExpedicionIso
    ) {
        try {
            Certificado c = new Certificado();

            // fecha del registro = fecha de expedición (o hoy si no viene)
            LocalDate ld = (fechaExpedicionIso != null && !fechaExpedicionIso.trim().isEmpty())
                    ? LocalDate.parse(fechaExpedicionIso)
                    : LocalDate.now();
            c.setFecha(java.sql.Date.valueOf(ld));

            // tipo: Presentación de Niños
            c.setTipoId(3);

            // observaciones (JSONB) — guardamos claves en “doble” por compatibilidad con el JRXML
            Map<String, Object> obs = new HashMap<>();
            obs.put("nombreMiembro", nombreMiembro);

            obs.put("nombrePadre", nombrePadre);

            obs.put("nombreMadre", nombreMadre);

            obs.put("lugarFechaNacimiento", lugarFechaNacimiento);

            obs.put("fechaExpedicion", fechaExpedicionIso);

            c.setObservaciones(obs);

            return repository.save(c);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear certificado de Presentación de Niños", e);
        }
    }

    @Override
    public byte[] generarNinosPdf(Map<String, Object> params) {
        try {
            // archivo: src/main/resources/reportes/certificado_niños.jrxml
            ClassPathResource jr = new ClassPathResource("reportes/certificado_niños.jrxml");
            try (InputStream in = jr.getInputStream()) {
                JasperReport jasper = JasperCompileManager.compileReport(in);
                JasperPrint jp = JasperFillManager.fillReport(jasper, params, new JREmptyDataSource());
                return JasperExportManager.exportReportToPdf(jp);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF de Presentación de Niños", e);
        }
    }

    @Override
    public byte[] generarMatrimonioPdf(Map<String, Object> params) {
        try {
            org.springframework.core.io.ClassPathResource jr
                    = new org.springframework.core.io.ClassPathResource("reportes/certificado_matrimonio.jrxml");
            try (java.io.InputStream in = jr.getInputStream()) {
                net.sf.jasperreports.engine.JasperReport jasperRpt
                        = net.sf.jasperreports.engine.JasperCompileManager.compileReport(in);
                net.sf.jasperreports.engine.JasperPrint jp
                        = net.sf.jasperreports.engine.JasperFillManager.fillReport(
                                jasperRpt,
                                params,
                                new net.sf.jasperreports.engine.JREmptyDataSource()
                        );
                return net.sf.jasperreports.engine.JasperExportManager.exportReportToPdf(jp);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF de Matrimonio", e);
        }
    }

    @Override
    public Certificado crearCertificadoMatrimonio(String esposo, String esposa, String fechaExpedicionIso) {
        try {
            Certificado c = new Certificado();

            // fecha = expedición si viene, si no hoy
            java.time.LocalDate ld = (fechaExpedicionIso != null && !fechaExpedicionIso.trim().isEmpty())
                    ? java.time.LocalDate.parse(fechaExpedicionIso)
                    : java.time.LocalDate.now();
            c.setFecha(java.sql.Date.valueOf(ld));

            // tipo_id para Matrimonio
            c.setTipoId(2);

            // observaciones JSONB
            java.util.Map<String, Object> obs = new java.util.HashMap<>();
            obs.put("esposo", esposo);
            obs.put("esposa", esposa);
            obs.put("nombreMiembro", esposo + ", " + esposa);

            obs.put("fechaExpedicion", fechaExpedicionIso);

            c.setObservaciones(obs);

            return repository.save(c);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear certificado de matrimonio", e);
        }
    }

}
