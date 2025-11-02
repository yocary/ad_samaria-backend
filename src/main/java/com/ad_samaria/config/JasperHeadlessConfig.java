/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.config;

import java.awt.GraphicsEnvironment;
import javax.annotation.PostConstruct;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperReportsContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Yocary
 */
@Configuration
public class JasperHeadlessConfig {

    private static final Logger log = LoggerFactory.getLogger(JasperHeadlessConfig.class);

    static {
        // Forzar headless ANTES de que Jasper/AWT se inicialice
        System.setProperty("java.awt.headless", "true");
        System.setProperty("file.encoding", "UTF-8");
    }

    @PostConstruct
    public void init() {
        // Defaults seguros para Jasper (fuentes embebibles)
        try {
            JasperReportsContext ctx = DefaultJasperReportsContext.getInstance();
            JRPropertiesUtil props = JRPropertiesUtil.getInstance(ctx);
            props.setProperty("net.sf.jasperreports.default.font.name", "DejaVu Sans");
            props.setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");
        } catch (Exception e) {
            log.warn("No se pudieron aplicar props de Jasper por código", e);
        }

        // Log de verificación
        log.info("java.awt.headless={} | GraphicsEnvironment.isHeadless()={}",
                System.getProperty("java.awt.headless"),
                GraphicsEnvironment.isHeadless());
    }
}
