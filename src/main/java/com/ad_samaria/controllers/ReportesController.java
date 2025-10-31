/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.controllers;

import com.ad_samaria.services.ReporteFinanzasService;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Yocary
 */
@RestController
@RequestMapping("/reportes")
public class ReportesController {

    private final ReporteFinanzasService service;

    public ReportesController(ReporteFinanzasService service) {
        this.service = service;
    }

    /**
     * GET /reportes/finanzas/tesorerias/{id}?mes=2025-10
     */
    @GetMapping("/finanzas/tesorerias/{id}")
    public ResponseEntity<byte[]> pdfFinanzasTesoreria(
            @PathVariable("id") Long tesoreriaId,
            @RequestParam("mes") String mes // formato YYYY-MM
    ) {
        YearMonth ym = YearMonth.parse(mes, DateTimeFormatter.ofPattern("yyyy-MM"));
        byte[] pdf = service.generarPdfFinanzasTesoreria(tesoreriaId, ym);

        String filename = "finanzas_" + tesoreriaId + "_" + mes + ".pdf";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/finanzas/tesoreriasTodas")
    public ResponseEntity<byte[]> pdfFinanzasTodasTesoreria(
            @RequestParam("mes") String mes // formato YYYY-MM
    ) {
        YearMonth ym = YearMonth.parse(mes, DateTimeFormatter.ofPattern("yyyy-MM"));
        byte[] pdf = service.generarPdfFinanzasTodasTesorerias(ym);

        String filename = "finanzas" + "_" + mes + ".pdf";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
