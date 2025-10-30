/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services.impl;

import com.ad_samaria.commons.CommonSvcImpl;
import com.ad_samaria.models.Movimiento;
import com.ad_samaria.repositories.MovimientoRepository;
import com.ad_samaria.services.MovimientoSvc;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *
 * @author Yocary
 */
@Service
public class MovimientoSvcImpl extends CommonSvcImpl<Movimiento, MovimientoRepository> implements MovimientoSvc {

    @Autowired
    private JdbcTemplate jdbc;

    private Long resolveTipoId(String tipo, Long tipoId) {
        if (tipoId != null) {
            return tipoId;
        }
        if (!StringUtils.hasText(tipo)) {
            throw new IllegalArgumentException("tipo/tipoId requerido");
        }
        String t = tipo.trim().toLowerCase();
        return jdbc.queryForObject(
                "select id from ad_samaria.tipo_movimiento where lower(nombre) like ? limit 1",
                new Object[]{t.startsWith("ingreso") ? "ingreso%" : "egreso%"},
                Long.class
        );
    }

    public Movimiento crear(Long tesoreriaId,
            Long tipoId,
            LocalDate fecha,
            String concepto,
            BigDecimal cantidad,
            Long metodoPagoId,
            Long personaId,
            Long categoriaId,
            String observaciones) {
        if (tesoreriaId == null) {
            throw new IllegalArgumentException("tesoreriaId requerido");
        }
        if (tipoId == null) {
            throw new IllegalArgumentException("tipoId requerido");
        }
        if (fecha == null) {
            throw new IllegalArgumentException("fecha requerida");
        }
        if (cantidad == null) {
            throw new IllegalArgumentException("cantidad requerida");
        }
        if (metodoPagoId == null) {
            throw new IllegalArgumentException("metodoPagoId requerido");
        }

        Movimiento m = new Movimiento();
        m.setTesoreriaId(tesoreriaId);
        m.setTipoId(tipoId);
        m.setFecha(fecha);
        m.setConcepto(concepto);
        m.setCantidad(cantidad);
        m.setMetodoPagoId(metodoPagoId);
        m.setPersonaId(personaId);
        m.setCategoriaId(categoriaId); // puede ser null
        m.setObservaciones(observaciones);
        return repository.save(m);
    }

    @Override
    public Long crearDesdeDTO(Long tesoreriaId, com.ad_samaria.dto.CrearMovimientoReq dto) {
        Long tipoId = resolveTipoId(dto.tipo, dto.tipoId);
        Movimiento m = crear(
                tesoreriaId, tipoId, dto.fecha, dto.concepto, dto.cantidad,
                dto.metodoPagoId, dto.personaId, dto.categoriaId, dto.observaciones
        );
        return m.getId();
    }
}
