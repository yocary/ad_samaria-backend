/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services.impl;

import com.ad_samaria.commons.CommonSvcImpl;
import com.ad_samaria.dto.ActualizarTesoreriaReq;
import com.ad_samaria.dto.MovimientoRowDTO;
import com.ad_samaria.dto.ResumenDTO;
import com.ad_samaria.dto.TesoreriaRowDTO;
import com.ad_samaria.models.Tesoreria;
import com.ad_samaria.projections.MovimientoProjection;
import com.ad_samaria.projections.ResumenProjection;
import com.ad_samaria.repositories.MovimientoRepository;
import com.ad_samaria.repositories.TesoreriaRepository;
import com.ad_samaria.services.TesoreriaSvc;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Yocary
 */
@Service
public class TesoreriaSvcImpl extends CommonSvcImpl<Tesoreria, TesoreriaRepository> implements TesoreriaSvc {

    @Autowired
    private MovimientoRepository movRepo;

    @Override
    public Tesoreria crearTesoreria(String nombre, Boolean estado) {
        if (nombre == null || nombre.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre es requerido");
        }

        if (repository.existsByNombreIgnoreCase(nombre)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una tesorería con ese nombre");
        }

        Tesoreria t = new Tesoreria();
        t.setNombre(nombre.trim());
        t.setEstado(Boolean.TRUE.equals(estado));
        return repository.save(t);
    }

    private LocalDate[] rango(String periodo) {
        LocalDate hoy = LocalDate.now(ZoneId.of("America/Guatemala"));
        switch (periodo == null ? "mes" : periodo) {
            case "mes_anterior":
                LocalDate iniMA = hoy.minusMonths(1).withDayOfMonth(1);
                LocalDate finMA = iniMA.withDayOfMonth(iniMA.lengthOfMonth());
                return new LocalDate[]{iniMA, finMA};
            case "anio":
                return new LocalDate[]{hoy.withDayOfYear(1), hoy.withMonth(12).withDayOfMonth(31)};
            case "todos":
                return new LocalDate[]{LocalDate.of(2000, 1, 1), hoy.plusYears(50)};
            default: // "mes"
                LocalDate ini = hoy.withDayOfMonth(1);
                LocalDate fin = hoy.withDayOfMonth(hoy.lengthOfMonth());
                return new LocalDate[]{ini, fin};
        }
    }

    @Override
    public List<TesoreriaRowDTO> listar(String estado, String q, String periodo) {
        // 1) tesorerías base
        List<Tesoreria> bases = repository.search(
                estado == null ? "activas" : estado,
                (q == null || q.trim().isEmpty()) ? null : q.trim());

        // 2) totales por período
        LocalDate[] r = rango(periodo);
        Map<Long, BigDecimal[]> totales = new HashMap<>();
        for (Object[] row : movRepo.totalesPorTesoreriaEntre(r[0], r[1])) {
            Long tesId = ((Number) row[0]).longValue();
            BigDecimal ing = (BigDecimal) row[1];
            BigDecimal egr = (BigDecimal) row[2];
            totales.put(tesId, new BigDecimal[]{ing, egr});
        }

        // 3) mapear DTO
        List<TesoreriaRowDTO> out = new ArrayList<>();
        for (Tesoreria t : bases) {
            BigDecimal[] te = totales.getOrDefault(t.getId(),
                    new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO});
            out.add(new TesoreriaRowDTO(t.getId(), t.getNombre(), t.getEstado(), te[0], te[1]));
        }
        return out;
    }

    private LocalDate[] rangoTesoreria(String periodo) {
        LocalDate hoy = LocalDate.now(ZoneId.of("America/Guatemala"));
        if (periodo == null) {
            periodo = "mes";
        }
        switch (periodo) {
            case "mes_anterior":
                LocalDate a = hoy.minusMonths(1).withDayOfMonth(1);
                LocalDate b = a.withDayOfMonth(a.lengthOfMonth());
                return new LocalDate[]{a, b};
            case "anio":
                return new LocalDate[]{hoy.withDayOfYear(1), hoy.withMonth(12).withDayOfMonth(31)};
            case "todos":
                return new LocalDate[]{LocalDate.of(2000, 1, 1), hoy.plusYears(50)};
            default: // "mes"
                LocalDate i = hoy.withDayOfMonth(1);
                LocalDate f = hoy.withDayOfMonth(hoy.lengthOfMonth());
                return new LocalDate[]{i, f};
        }
    }

    public List<MovimientoRowDTO> listarMovimientos(Long tesoreriaId, String periodo, String q) {
        String qNorm = (q == null) ? "" : q.trim();
        LocalDate[] rf = rangoTesoreria(periodo);
        List<MovimientoProjection> rows = movRepo.findMovimientos(tesoreriaId, rf[0], rf[1], qNorm);
        return rows.stream()
                .map(p -> new MovimientoRowDTO(
                p.getId(), p.getTreasuryId(), p.getType(), p.getDate(),
                p.getConcept(), p.getCategory(), p.getAmount(),
                p.getMethod(), p.getMemberName(), p.getNotes()
        )).collect(Collectors.toList());
    }

    public ResumenDTO resumen(Long tesoreriaId, String periodo) {
        LocalDate[] rf = rangoTesoreria(periodo);
        ResumenProjection r = movRepo.resumenTesoreria(tesoreriaId, rf[0], rf[1]);
        return new ResumenDTO(r.getTotalIngresos(), r.getTotalEgresos());
    }

    @Override
    public void actualizarTesoreria(Long id, ActualizarTesoreriaReq req) {
        if (id == null) {
            throw new IllegalArgumentException("Id requerido");
        }
        if (req == null) {
            throw new IllegalArgumentException("Body requerido");
        }
        if (req.nombre == null || req.nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (req.estado == null) {
            throw new IllegalArgumentException("El estado es obligatorio");
        }

        Tesoreria t = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tesorería no encontrada"));

        // (opcional) evitar duplicados
        if (repository.existsByNombreIgnoreCaseAndIdNot(req.nombre.trim(), id)) {
            throw new IllegalArgumentException("Ya existe una tesorería con ese nombre");
        }

        t.setNombre(req.nombre.trim());
        t.setEstado(req.estado); // boolean
        repository.save(t);
    }

    @Override
    public void eliminarTesoreria(Long id) {
        Tesoreria t = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tesorería no encontrada"));

        long cantMovs = movRepo.countByTesoreriaId(id);
        if (cantMovs > 0) {
            throw new IllegalStateException("No se puede eliminar: la tesorería tiene movimientos asociados");
        }

        repository.deleteById(id);
    }
}
