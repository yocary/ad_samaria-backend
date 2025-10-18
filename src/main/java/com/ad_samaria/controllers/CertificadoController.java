/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.controllers;

import com.ad_samaria.commons.CommonController;
import com.ad_samaria.dto.BautismoRequest;
import com.ad_samaria.dto.CertificadoDTO;
import com.ad_samaria.dto.CrearCertificadoMembresiaRequest;
import com.ad_samaria.dto.MatrimonioRequest;
import com.ad_samaria.dto.MembresiaRequest;
import com.ad_samaria.dto.NinosRequest;
import com.ad_samaria.models.Certificado;
import com.ad_samaria.services.CertificadoSvc;
import com.ad_samaria.services.impl.CertificadoSvcImpl.ResultadoPdf;
import com.ad_samaria.validator.CertificadoValidator;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Yocary
 */
@RequestMapping("/certificado")
@RestController
public class CertificadoController extends CommonController<Certificado, CertificadoSvc, CertificadoValidator> {

    @PostMapping(value = "/membresia/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generarMembresia(@RequestBody MembresiaRequest req) throws Exception {
        // Parámetros que el .jrxml espera:
        Map<String, Object> params = new HashMap<>();
        String nombre = safe(req.getNombreMiembro());
        String fecha = safe(req.getFecha());

        String fechaFormateada = formatearFecha(fecha);

        params.put("fecha", fechaFormateada);
        params.put("nombreMiembro", nombre);

        byte[] pdf = service.generarMembresiaPdf(params);

        String fileName = URLEncoder.encode("certificado_membresia.pdf", "UTF-8");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename*=UTF-8''" + fileName)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }

    @PostMapping("/membresia/crear")
    public ResponseEntity<Certificado> crearMembresia(@RequestBody CrearCertificadoMembresiaRequest req) {
        Certificado c = service.crearCertificadoMembresia(req.getNombreMiembro(), req.getFecha());
        return ResponseEntity.ok(c);
    }

    private String formatearFecha(String fecha) {
        try {
            LocalDate localDate = LocalDate.parse(fecha);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return localDate.format(formatter);
        } catch (Exception e) {
            return fecha;
        }
    }

    @GetMapping
    public List<CertificadoDTO> listar(@RequestParam(value = "q", required = false) String q) {
        return service.listar(q);
    }

    @GetMapping(value = "/{id}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> descargar(@PathVariable("id") Long id) {
        ResultadoPdf r = service.generarPdfPorId(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename*=UTF-8''" + r.encodedFilename())
                .contentType(MediaType.APPLICATION_PDF)
                .body(r.bytes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build(); // 204
    }

    @PostMapping(value = "/bautismo/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generarBautismo(@RequestBody BautismoRequest req) throws Exception {
        Map<String, Object> params = new HashMap<>();

        String nombre = safe(req.getNombreMiembro());
        String fBaut = safe(req.getFechaBautismo());
        String fExpIso = safe(req.getFechaExpedicion());

        // Formateos dd/MM/yyyy
        String fechaBautismo = formatearFecha(fBaut);
        String fechaExpedicion = formatearFecha(fExpIso);

        params.put("nombreMiembro", nombre);

        params.put("fechaBautismo", fechaBautismo);

        params.put("fechaExpedicion", fechaExpedicion);

        byte[] pdf = service.generarBautismoPdf(params);

        String fileName = URLEncoder.encode("certificado_bautismo.pdf", "UTF-8");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename*=UTF-8''" + fileName)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @PostMapping("/bautismo/crear")
    public ResponseEntity<Certificado> crearBautismo(@RequestBody BautismoRequest req) {
        Certificado creado = service.crearCertificadoBautismo(
                safe(req.getNombreMiembro()),
                safe(req.getFechaBautismo()),
                safe(req.getFechaExpedicion())
        );
        return ResponseEntity.ok(creado);
    }

    @PostMapping("/ninos/crear")
    public ResponseEntity<Certificado> crearNinos(@RequestBody NinosRequest req) {
        Certificado c = service.crearCertificadoNinos(
                safe(req.getNombreMiembro()),
                safe(req.getNombrePadre()),
                safe(req.getNombreMadre()),
                safe(req.getLugarFechaNacimiento()),
                safe(req.getFechaExpedicion())
        );
        return ResponseEntity.ok(c);
    }

    // ====== PDF directo (opcional): Presentación de Niños ======
    @PostMapping(value = "/ninos/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generarNinosPdf(@RequestBody NinosRequest req) throws Exception {
        Map<String, Object> params = new HashMap<>();
        // Mapeo “doble” por compatibilidad con el jrxml:
        params.put("nombreMiembro", safe(req.getNombreMiembro()));

        params.put("nombrePadre", safe(req.getNombrePadre()));

        params.put("nombreMadre", safe(req.getNombreMadre()));

        params.put("lugarFechaNacimiento", safe(req.getLugarFechaNacimiento()));

        // fecha expedición → dd/MM/yyyy en “fecha” y “FECHA_EXPEDICION”
        String f = formatearFecha(req.getFechaExpedicion());
        params.put("fechaExpedicion", f);

        byte[] pdf = service.generarNinosPdf(params);

        String fileName = URLEncoder.encode("certificado_presentacion_ninos.pdf", "UTF-8");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename*=UTF-8''" + fileName)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @PostMapping("/matrimonio/crear")
    public Certificado crearMatrimonio(@RequestBody MatrimonioRequest req) {
        String esposo = safe(req.getEsposo());
        String esposa = safe(req.getEsposa());
        String fecha = safe(req.getFechaExpedicion()); // ISO
        return service.crearCertificadoMatrimonio(esposo, esposa, fecha);
    }

    @PostMapping(value = "/matrimonio/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generarMatrimonio(@RequestBody MatrimonioRequest req) throws Exception {
        java.util.Map<String, Object> params = new java.util.HashMap<>();
        String esposo = safe(req.getEsposo());
        String esposa = safe(req.getEsposa());
        String fechaIso = safe(req.getFechaExpedicion()); // ISO yyyy-MM-dd

        // nombres que el .jrxml usará
        params.put("esposo", esposo);
        params.put("esposa", esposa);
        params.put("fechaExpedicion", fechaIso);

        byte[] pdf = service.generarMatrimonioPdf(params);

        String fileName = java.net.URLEncoder.encode("certificado_matrimonio.pdf", "UTF-8");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename*=UTF-8''" + fileName)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
