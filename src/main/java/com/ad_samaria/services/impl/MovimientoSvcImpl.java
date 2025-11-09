/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services.impl;

import com.ad_samaria.commons.CommonSvcImpl;
import com.ad_samaria.dto.CrearMovimientoReq;
import com.ad_samaria.dto.MovimientosGeneralesItem;
import com.ad_samaria.dto.MovimientosGeneralesRes;
import com.ad_samaria.dto.MovimientosGeneralesTotales;
import com.ad_samaria.models.Categoria;
import com.ad_samaria.models.Movimiento;
import com.ad_samaria.models.Persona;
import com.ad_samaria.projections.MovimientoGeneralProjection;
import com.ad_samaria.projections.TotalesGeneralesProjection;
import com.ad_samaria.repositories.CategoriaRepository;
import com.ad_samaria.repositories.MetodoPagoRepository;
import com.ad_samaria.repositories.MovimientoRepository;
import com.ad_samaria.repositories.PersonaRepository;
import com.ad_samaria.repositories.TipoMovimientoRepository;
import com.ad_samaria.services.MovimientoSvc;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
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
    TipoMovimientoRepository tipoRepo;

    @Autowired
    MetodoPagoRepository metodoRepo;

    @Autowired
    CategoriaRepository categoriaRepo;

    @Autowired
    PersonaRepository personaRepo;

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
    public Long crearDesdeDTO(Long tesoreriaId, CrearMovimientoReq dto) {
        Long tipoId = resolveTipoId(dto.tipo, dto.tipoId);
        Movimiento m = crear(
                tesoreriaId, tipoId, dto.fecha, dto.concepto, dto.cantidad,
                dto.metodoPagoId, dto.personaId, dto.categoriaId, dto.observaciones
        );
        return m.getId();
    }

    @Override
    public void eliminar(Long tesoreriaId, Long movimientoId) {
        Movimiento m = repository.findById(movimientoId)
                .orElseThrow(() -> new EntityNotFoundException("Movimiento no encontrado"));

        if (!m.getTesoreriaId().equals(tesoreriaId)) {
            throw new IllegalArgumentException("El movimiento no pertenece a la tesorería indicada");
        }

        repository.delete(m);
    }

    @Override
    public void actualizarDesdeDTO(Long tesoreriaId, Long movimientoId, CrearMovimientoReq req) {
        Movimiento m = repository.findById(movimientoId)
                .orElseThrow(() -> new EntityNotFoundException("Movimiento no encontrado"));

        if (!m.getTesoreriaId().equals(tesoreriaId)) {
            throw new IllegalArgumentException("Movimiento no pertenece a la tesorería indicada");
        }

        mapearDTOaEntidad(m, tesoreriaId, req);
        repository.save(m);
    }

    private void mapearDTOaEntidad(Movimiento m, Long tesoreriaId, CrearMovimientoReq req) {

        // --- Validaciones mínimas ---
        if (tesoreriaId == null) {
            throw new IllegalArgumentException("Tesorería requerida");
        }
        if (req == null) {
            throw new IllegalArgumentException("Payload requerido");
        }
        if (req.fecha == null) {
            throw new IllegalArgumentException("La fecha es obligatoria");
        }
        if (req.cantidad == null) {
            throw new IllegalArgumentException("La cantidad es obligatoria");
        }
        if (req.metodoPagoId == null) {
            throw new IllegalArgumentException("El método de pago es obligatorio");
        }
        // Si decidiste que persona es OBLIGATORIA:
        if (req.personaId == null) {
            throw new IllegalArgumentException("La persona es obligatoria");
        }
        // Si decidiste que la categoría es OBLIGATORIA:
        if (req.categoriaId == null) {
            throw new IllegalArgumentException("La categoría es obligatoria");
        }

        // --- Asignaciones directas ---
        m.setTesoreriaId(tesoreriaId);
        m.setFecha(req.fecha);
        m.setConcepto(req.concepto != null ? req.concepto.trim() : "");
        m.setCantidad(req.cantidad);
        m.setObservaciones(req.observaciones != null ? req.observaciones.trim() : null);

        // --- Tipo de movimiento ---
        // Si viene tipoId lo usamos; si no, resolvemos por nombre en 'tipo'
        if (req.tipoId != null) {
            // valida que exista
            tipoRepo.findById(req.tipoId)
                    .orElseThrow(() -> new IllegalArgumentException("Tipo de movimiento inválido"));
            m.setTipoId(req.tipoId);
        } else {
            String tipoNombre = (req.tipo == null ? "" : req.tipo.trim());
            if (tipoNombre.isEmpty()) {
                throw new IllegalArgumentException("Tipo de movimiento requerido");
            }
            Long tipoId = tipoRepo.findIdByNombreILike(tipoNombre)
                    .orElseThrow(() -> new IllegalArgumentException("Tipo de movimiento inválido"));
            m.setTipoId(tipoId);
        }

        // --- Método de pago ---
        // En tu DTO viene metodoPagoId, así que solo validamos que exista y lo seteamos
        metodoRepo.findById(req.metodoPagoId)
                .orElseThrow(() -> new IllegalArgumentException("Método de pago inválido"));
        m.setMetodoPagoId(req.metodoPagoId);

        // --- Categoría (por id) ---
        Categoria c = categoriaRepo.findById(req.categoriaId)
                .orElseThrow(() -> new IllegalArgumentException("Categoría inválida"));
        m.setCategoriaId(c.getId());

        // --- Persona (si es obligatoria ya validaste arriba; si fuera opcional, solo setear si no es null) ---
        if (req.personaId != null) {
            Persona p = personaRepo.findById(req.personaId)
                    .orElseThrow(() -> new IllegalArgumentException("Persona inválida"));
            m.setPersonaId(p.getId());
        } else {
            m.setPersonaId(null);
        }
    }

    // MovimientoSvcImpl.java
    @Override
    public MovimientosGeneralesRes obtenerMovimientosGenerales(String periodo, String q, String mesISO) {
        LocalDate desde;
        LocalDate hasta;

        // 1) Si viene mes=YYYY-MM, priorízalo siempre
        if (StringUtils.hasText(mesISO)) {
            YearMonth ym = YearMonth.parse(mesISO); // formato "yyyy-MM"
            desde = ym.atDay(1);
            hasta = ym.plusMonths(1).atDay(1); // [desde, hasta)
        } else {
            // 2) Fallback por periodo (igual que antes)
            YearMonth ahora = YearMonth.now();
            if (!StringUtils.hasText(periodo)) {
                periodo = "mes";
            }
            switch (periodo) {
                case "mes":
                    desde = ahora.atDay(1);
                    hasta = ahora.plusMonths(1).atDay(1);
                    break;
                case "mes_anterior":
                    YearMonth prev = ahora.minusMonths(1);
                    desde = prev.atDay(1);
                    hasta = ahora.atDay(1);
                    break;
                case "anio":
                    int y = LocalDate.now().getYear();
                    desde = LocalDate.of(y, 1, 1);
                    hasta = LocalDate.of(y + 1, 1, 1);
                    break;
                case "todos":
                default:
                    desde = LocalDate.of(2020, 1, 1);
                    hasta = LocalDate.now().plusYears(1);
                    break;
            }
        }

        // Normaliza q
        if (q != null) {
            q = q.trim();
            if (q.isEmpty()) {
                q = null;
            }
        }

        // Consulta y armado de respuesta (SIN CAMBIOS)
        List<MovimientoGeneralProjection> rows = repository.listarMovimientosGenerales(desde, hasta, q);
        List<MovimientosGeneralesItem> items = rows.stream()
                .map(r -> new MovimientosGeneralesItem(
                r.getTesoreriaId(),
                r.getTesoreria(),
                r.getCategoriaId(),
                r.getCategoria(),
                r.getTipo(),
                r.getFecha(),
                r.getAmount()
        ))
                .collect(Collectors.toList());

        TotalesGeneralesProjection t = repository.totalesMovimientosGenerales(desde, hasta, q);
        MovimientosGeneralesTotales totales = new MovimientosGeneralesTotales(
                t != null && t.getIngresos() != null ? t.getIngresos().doubleValue() : 0d,
                t != null && t.getEgresos() != null ? t.getEgresos().doubleValue() : 0d
        );

        return new MovimientosGeneralesRes(items, totales);
    }

}
