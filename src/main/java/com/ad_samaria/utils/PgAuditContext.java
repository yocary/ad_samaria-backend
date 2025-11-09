/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 *
 * @author Pablo
 */
@Component
@RequiredArgsConstructor
public class PgAuditContext {

    private final DataSource dataSource;
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PgAuditContext.class);

    public void apply(String appName, String username, String ip) {
        // ✅ Llamada correcta al método estático
        boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();

        if (!txActive) {
            log.debug("PgAuditContext: sin transacción activa; se omite set_config/SET LOCAL");
            return;
        }

        Connection con = DataSourceUtils.getConnection(dataSource);
        try (Statement st = con.createStatement()) {
            st.execute("SET LOCAL application_name = '" + safe(appName) + "'");
        } catch (Exception e) {
            log.warn("SET LOCAL application_name falló: {}", e.getMessage());
        }

        setConfig(con, "app.user", username);
        setConfig(con, "app.ip", ip);
    }

    private void setConfig(Connection con, String key, String val) {
        if (val == null) {
            val = "";
        }
        try (PreparedStatement ps = con.prepareStatement("select set_config(?, ?, true)")) {
            ps.setString(1, key);
            ps.setString(2, val);
            ps.execute();
        } catch (Exception e) {
            log.warn("set_config({}, ...) falló (¿fuera de TX?): {}", key, e.getMessage());
        }
    }

    private String safe(String x) {
        return x == null ? "" : x.replace("'", "''");
    }
}
