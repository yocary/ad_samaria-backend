/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services.impl;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

/**
 *
 * @author Yocary
 */
@Service
public class JasperPdfService {

    // Compila jrxml (o carga jasper si lo prefieres) y genera PDF
    public byte[] renderPdf(String nombrePlantilla, Map<String, Object> params, List<?> data) {
        try (InputStream in = new ClassPathResource("reportes/" + nombrePlantilla + ".jrxml").getInputStream()) {
            JasperReport rpt = JasperCompileManager.compileReport(in);
            JRDataSource ds = (data == null || data.isEmpty())
                    ? new JREmptyDataSource()
                    : new JRBeanCollectionDataSource(data);
            JasperPrint print = JasperFillManager.fillReport(rpt, params, ds);
            return JasperExportManager.exportReportToPdf(print);
        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF", e);
        }
    }

}
