package com.ad_samaria.services;

import com.ad_samaria.dto.CrearDiezmoReq;
import com.ad_samaria.dto.DiezmoRow;
import com.ad_samaria.dto.GetDiezmosRes;
import com.ad_samaria.models.Diezmo;
import com.ad_samaria.models.Persona;
import com.ad_samaria.models.TipoSimple;
import com.ad_samaria.repositories.DiezmoRepository;
import com.ad_samaria.repositories.PersonaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DiezmoService {

    private final DiezmoRepository diezmoRepo;
    private final PersonaRepository personaRepo;

    public DiezmoService(DiezmoRepository diezmoRepo, PersonaRepository personaRepo) {
        this.diezmoRepo = diezmoRepo;
        this.personaRepo = personaRepo;
    }

    @Transactional(readOnly = true)
    public GetDiezmosRes listar(String periodo, String q) {
        LocalDate hoy = LocalDate.now();

        LocalDate desde;
        LocalDate hasta;

        String per = (periodo == null ? "mes" : periodo);
        switch (per) {
            case "anio": {
                desde = hoy.withDayOfYear(1);
                hasta = hoy.withDayOfYear(hoy.lengthOfYear()); // último día del año
                break;
            }
            case "mes_anterior": {
                LocalDate ini = hoy.withDayOfMonth(1).minusMonths(1);
                desde = ini;
                hasta = ini.withDayOfMonth(ini.lengthOfMonth());
                break;
            }
            case "todos": {
                desde = LocalDate.of(1970, 1, 1);
                hasta = hoy; // o el último día del mes actual si prefieres
                break;
            }
            default: { // "mes"
                desde = hoy.withDayOfMonth(1);
                hasta = hoy.withDayOfMonth(hoy.lengthOfMonth()); // 👈 mes completo
                break;
            }
        }

        String query = (q == null ? "" : q.trim());

        List<Diezmo> lista = diezmoRepo.buscarPorRangoYPersona(desde, hasta, query);
        List<DiezmoRow> rows = lista.stream().map(d -> {
            Persona p = d.getPersona();
            String nombre = construirNombrePersona(p);
            return new DiezmoRow(
                    d.getId(),
                    p.getId(),
                    nombre,
                    d.getCantidad(),
                    d.getFecha(),
                    d.getTipo().name()
            );
        }).collect(Collectors.toList());

        BigDecimal totIngresos = diezmoRepo.totalPorTipoYFecha(TipoSimple.Ingreso, desde, hasta);
        BigDecimal totEgresos = diezmoRepo.totalPorTipoYFecha(TipoSimple.Egreso, desde, hasta);

        if (totIngresos == null) {
            totIngresos = BigDecimal.ZERO;
        }
        if (totEgresos == null) {
            totEgresos = BigDecimal.ZERO;
        }

        return new GetDiezmosRes(rows, new GetDiezmosRes.Totales(totIngresos, totEgresos));
    }

    @Transactional
    public Long crear(CrearDiezmoReq req) {
        validar(req);
        Diezmo d = new Diezmo();
        mapear(d, req);
        diezmoRepo.save(d);
        return d.getId();
    }

    @Transactional
    public void actualizar(Long id, CrearDiezmoReq req) {
        validar(req);
        Diezmo d = diezmoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Diezmo no encontrado"));
        mapear(d, req);
        diezmoRepo.save(d);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!diezmoRepo.existsById(id)) {
            throw new IllegalArgumentException("Diezmo no encontrado");
        }
        diezmoRepo.deleteById(id);
    }

    /* ================== helpers ================== */
    private void validar(CrearDiezmoReq req) {
        if (req == null) {
            throw new IllegalArgumentException("Solicitud vacía");
        }
        if (req.personaId == null) {
            throw new IllegalArgumentException("personaId es requerido");
        }
        if (req.cantidad == null || req.cantidad.signum() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que 0");
        }
        if (req.fecha == null) {
            throw new IllegalArgumentException("La fecha es requerida");
        }
        if (!"Ingreso".equalsIgnoreCase(req.tipo) && !"Egreso".equalsIgnoreCase(req.tipo)) {
            throw new IllegalArgumentException("Tipo inválido");
        }

        // valida existencia de persona
        personaRepo.findById(req.personaId)
                .orElseThrow(() -> new IllegalArgumentException("Persona no encontrada"));
    }

    private void mapear(Diezmo d, CrearDiezmoReq req) {
        // Version compatible con Spring Data "clásico"
        Persona persona = personaRepo.findById(req.personaId)
                .orElseThrow(() -> new IllegalArgumentException("Persona no encontrada"));
        d.setPersona(persona);
        d.setCantidad(req.cantidad);
        d.setFecha(req.fecha);
        d.setTipo("Ingreso".equalsIgnoreCase(req.tipo) ? TipoSimple.Ingreso : TipoSimple.Egreso);
    }

    private String construirNombrePersona(Persona p) {
        String nombres = p.getNombres() != null ? p.getNombres().trim() : "";
        String apellidoPaterno = p.getApellidoPaterno() != null ? p.getApellidoPaterno().trim() : "";
        String apellidoMaterno = p.getApellidoMaterno() != null ? p.getApellidoMaterno().trim() : "";
        String full = (nombres + " " + apellidoPaterno + " " + apellidoMaterno).trim();
        return full.isEmpty() ? ("ID:" + p.getId()) : full;
    }
}
