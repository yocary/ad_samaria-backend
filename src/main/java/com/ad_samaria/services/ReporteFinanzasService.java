/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services;

import com.ad_samaria.dto.MovRepRow;
import com.ad_samaria.repositories.MovimientoRepository;
import com.ad_samaria.utils.NumeroALetrasEs;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

/**
 *
 * @author Yocary
 */
@Service
public class ReporteFinanzasService {

    private final MovimientoRepository movRepo;

    public ReporteFinanzasService(MovimientoRepository movRepo) {
        this.movRepo = movRepo;
    }

    public byte[] generarPdfFinanzasTesoreria(Long tesoreriaId, YearMonth ym) {
        LocalDate desde = ym.atDay(1);
        LocalDate hasta = ym.atEndOfMonth();

        // 🔹 Obtener los movimientos con nombres de tipo y categoría
        List<Object[]> raw = movRepo.findMovimientosConNombres(tesoreriaId, desde, hasta);

        List<MovRepRow> rows = new ArrayList<>();
        for (Object[] r : raw) {
            String concepto = (String) r[0];
            String tipo = (String) r[1];
            String categoria = (String) r[2];
            BigDecimal cantidad = (BigDecimal) r[3];

            // Conversión segura de fecha (evita ClassCastException con java.sql.Date)
            Object fechaCol = r[4];
            LocalDate fecha = null;
            if (fechaCol instanceof LocalDate) {
                fecha = (LocalDate) fechaCol;
            } else if (fechaCol instanceof java.sql.Date) {
                fecha = ((java.sql.Date) fechaCol).toLocalDate();
            } else if (fechaCol instanceof java.util.Date) {
                fecha = ((java.util.Date) fechaCol)
                        .toInstant()
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalDate();
            }

            rows.add(new MovRepRow(concepto, tipo, categoria, cantidad, fecha));
        }

        // 🔹 Calcular totales
        BigDecimal totIng = rows.stream()
                .filter(r -> r.getTipo() != null && r.getTipo().equalsIgnoreCase("Ingreso"))
                .map(MovRepRow::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totEgr = rows.stream()
                .filter(r -> r.getTipo() != null && r.getTipo().equalsIgnoreCase("Egreso"))
                .map(MovRepRow::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal saldo = totIng.subtract(totEgr);

        Locale esGT = new Locale("es", "GT");
        String mesTexto = ym.getMonth().getDisplayName(TextStyle.FULL, esGT).toUpperCase() + " " + ym.getYear();

        Map<String, Object> params = new HashMap<>();
        params.put("REPORT_LOCALE", esGT); // importante para formateos
        params.put("TITULO_IGLESIA", "ASAMBLEA DE DIOS SAMARIA");
        params.put("TITULO_REPORTE", "FINANZAS GENERALES");
        params.put("MES_TEXTO", mesTexto);
        params.put("TOTAL_INGRESOS", totIng);
        params.put("TOTAL_EGRESOS", totEgr);
        params.put("SALDO", saldo);

        params.put("TOTAL_EN_LETRAS", NumeroALetrasEs.montoEnQuetzales(saldo));

        try (InputStream in = getClass().getResourceAsStream("/reportes/finanzas_general.jrxml")) {
            if (in == null) {
                throw new IllegalStateException("No se encontró la plantilla del reporte: /reportes/finanzas_general.jrxml");
            }
            JasperReport jr = JasperCompileManager.compileReport(in);
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(rows);
            JasperPrint jp = JasperFillManager.fillReport(jr, params, ds);
            return JasperExportManager.exportReportToPdf(jp);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo generar el PDF de finanzas", e);
        }
    }

    public byte[] generarPdfFinanzasTodasTesorerias(YearMonth ym) {
        LocalDate desde = ym.atDay(1);
        LocalDate hasta = ym.atEndOfMonth();

        // Trae todos los movimientos entre fechas de todas las tesorerías
        List<Object[]> raw = movRepo.findMovimientosConNombresTodas(desde, hasta);
        List<MovRepRow> rows = new ArrayList<>();
        for (Object[] r : raw) {
            String tesoreria = (String) r[0];
            String concepto = (String) r[1];
            String tipo = (String) r[2];
            String categoria = (String) r[3];
            BigDecimal monto = (BigDecimal) r[4];
            java.sql.Date f = (java.sql.Date) r[5];
            LocalDate fecha = f == null ? null : f.toLocalDate();

            String conceptoFinal = (tesoreria == null ? "" : tesoreria)
                    + " - "
                    + (categoria == null ? "" : categoria);

            rows.add(new MovRepRow(conceptoFinal, tipo, categoria, monto, fecha));
        }

        // Totales globales (todas las tesorerías)
        BigDecimal totIng = rows.stream()
                .filter(r -> r.getTipo() != null && r.getTipo().equalsIgnoreCase("Ingreso"))
                .map(MovRepRow::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totEgr = rows.stream()
                .filter(r -> r.getTipo() != null && r.getTipo().equalsIgnoreCase("Egreso"))
                .map(MovRepRow::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal saldo = totIng.subtract(totEgr);

        Map<String, Object> params = new HashMap<>();
        params.put("TITULO_IGLESIA", "ASAMBLEA DE DIOS SAMARIA");
        params.put("TITULO_REPORTE", "FINANZAS GENERALES (TODAS LAS TESORERÍAS)");
        params.put("MES_TEXTO", ym.getMonth().getDisplayName(java.time.format.TextStyle.FULL, new Locale("es", "GT")).toUpperCase() + " " + ym.getYear());
        params.put("TOTAL_INGRESOS", totIng);
        params.put("TOTAL_EGRESOS", totEgr);
        params.put("SALDO", saldo);
        
        params.put("TOTAL_EN_LETRAS", NumeroALetrasEs.montoEnQuetzales(saldo));

        try (InputStream in = getClass().getResourceAsStream("/reportes/finanzas_general.jrxml")) {
            if (in == null) {
                throw new IllegalStateException("No se encontró la plantilla del reporte: /reportes/finanzas_general.jrxml");
            }
            JasperReport jr = JasperCompileManager.compileReport(in);
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(rows);
            JasperPrint jp = JasperFillManager.fillReport(jr, params, ds);
            return JasperExportManager.exportReportToPdf(jp);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo generar el PDF de finanzas", e);
        }
    }
}
