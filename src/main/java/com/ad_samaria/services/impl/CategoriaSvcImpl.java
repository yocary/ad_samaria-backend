/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services.impl;

import com.ad_samaria.commons.CommonSvcImpl;
import com.ad_samaria.dto.ActualizarCategoriaReq;
import com.ad_samaria.dto.CategoriaMiniRes;
import com.ad_samaria.dto.CategoriaRes;
import com.ad_samaria.dto.CrearCategoriaReq;
import com.ad_samaria.dto.TipoMovimientoMini;
import com.ad_samaria.models.Categoria;
import com.ad_samaria.models.Tesoreria;
import com.ad_samaria.models.TipoMovimiento;
import com.ad_samaria.projections.CategoriaMini;
import com.ad_samaria.repositories.CategoriaRepository;
import com.ad_samaria.repositories.TesoreriaRepository;
import com.ad_samaria.repositories.TipoMovimientoRepository;
import com.ad_samaria.services.CategoriaSvc;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Yocary
 */
@Service
public class CategoriaSvcImpl extends CommonSvcImpl<Categoria, CategoriaRepository> implements CategoriaSvc {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private TipoMovimientoRepository tipoRepo;

    @Autowired
    private TesoreriaRepository tesoreriaRepo;

    private Long resolveTipoId(String tipo, Long tipoId) {
        if (tipoId != null) {
            return tipoId;
        }
        String q = (tipo == null ? "" : tipo.trim().toLowerCase());
        String like = q.startsWith("ingreso") ? "ingreso%" : "egreso%";
        return jdbc.queryForObject(
                "SELECT id FROM ad_samaria.tipo_movimiento WHERE lower(nombre) LIKE ? LIMIT 1",
                new Object[]{like}, Long.class
        );
    }

    @Override
    public List<CategoriaMini> listarPorTipo(String tipo, Long tipoId) {
        Long id = resolveTipoId(tipo, tipoId);
        return repository.listarPorTipo(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoMovimientoMini> listarTipos() {
        return tipoRepo.findAllByOrderByNombreAsc()
                .stream()
                .map(t -> new TipoMovimientoMini(t.getId(), t.getNombre()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaMiniRes> listarPorTipoCategoria(String tipoNombre) {
        return repository.findByTipoNombre(tipoNombre)
                .stream()
                .map(c -> {
                    String tipo = tipoRepo.findById(c.getAplicaA()).map(TipoMovimiento::getNombre).orElse("");
                    return new CategoriaMiniRes(c.getId(), c.getNombre(), tipo, c.getFinanzasGenerales());
                })
                .collect(Collectors.toList());
    }

//    @Override
//    @Transactional
//    public CategoriaMiniRes crearCategoria(CrearCategoriaReq req) {
//        if (req == null || req.nombre == null || req.nombre.trim().isEmpty()) {
//            throw new IllegalArgumentException("El nombre es obligatorio");
//        }
//        if (req.tipoMovimientoId == null) {
//            throw new IllegalArgumentException("El tipo de movimiento es obligatorio");
//        }
//
//        TipoMovimiento tipo = tipoRepo.findById(req.tipoMovimientoId)
//                .orElseThrow(() -> new IllegalArgumentException("Tipo de movimiento inválido"));
//
//        if (repository.existsByNombreIgnoreCaseAndAplicaA(req.nombre.trim(), req.tipoMovimientoId)) {
//            throw new IllegalArgumentException("Ya existe una categoría con ese nombre para el tipo seleccionado");
//        }
//
//        Categoria c = new Categoria();
//        c.setNombre(req.nombre.trim());
//        c.setAplicaA(tipo.getId());
//        c.setFinanzasGenerales(Boolean.TRUE.equals(req.finanzasGenerales));
//        Categoria saved = repository.save(c);
//        return new CategoriaMiniRes(saved.getId(), saved.getNombre(), tipo.getNombre(), saved.getFinanzasGenerales());
//    }
//    @Override
//    @Transactional
//    public CategoriaMiniRes actualizarCategoria(Long id, ActualizarCategoriaReq req) {
//        if (id == null) {
//            throw new IllegalArgumentException("Id requerido");
//        }
//        if (req == null || req.nombre == null || req.nombre.trim().isEmpty()) {
//            throw new IllegalArgumentException("El nombre es obligatorio");
//        }
//        if (req.tipoMovimientoId == null) {
//            throw new IllegalArgumentException("El tipo de movimiento es obligatorio");
//        }
//
//        Categoria c = repository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
//
//        // Validar tipo
//        TipoMovimiento tipo = tipoRepo.findById(req.tipoMovimientoId)
//                .orElseThrow(() -> new IllegalArgumentException("Tipo de movimiento inválido"));
//
//        // Evitar duplicados (mismo nombre + tipo, distinto id)
//        if (repository.existsByNombreIgnoreCaseAndAplicaAAndIdNot(
//                req.nombre.trim(), req.tipoMovimientoId, id)) {
//            throw new IllegalArgumentException("Ya existe una categoría con ese nombre para el tipo seleccionado");
//        }
//
//        c.setNombre(req.nombre.trim());
//        c.setAplicaA(tipo.getId());
//        c.setFinanzasGenerales(Boolean.TRUE.equals(req.finanzasGenerales));
//        Categoria saved = repository.save(c);
//
//        return new CategoriaMiniRes(saved.getId(), saved.getNombre(), tipo.getNombre(), saved.getFinanzasGenerales());
//    }
    @Override
    @Transactional
    public void eliminarCategoria(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Categoría no encontrada");
        }
        repository.deleteById(id);
    }

    //CAMBIOS
    private Short toShortTipoId(Long tipoId) {
        if (tipoId == null) {
            throw new ResponseStatusException(BAD_REQUEST, "tipoId es requerido");
        }
        if (tipoId < Short.MIN_VALUE || tipoId > Short.MAX_VALUE) {
            throw new ResponseStatusException(BAD_REQUEST, "tipoId fuera de rango short");
        }
        short s = tipoId.shortValue();
        // si solo aceptas 1 o 2:
        if (s != 1 && s != 2) {
            throw new ResponseStatusException(BAD_REQUEST, "tipoId inválido (solo 1=Ingreso, 2=Egreso)");
        }
        return s;
    }

    private Long toLongTipoId(Short tipoId) {
        return tipoId == null ? null : Long.valueOf(tipoId);
    }

    @Override
    public CategoriaRes crearCategoria(CrearCategoriaReq req) {
        if (req.getTesoreriaId() == null) {
            throw new ResponseStatusException(BAD_REQUEST, "tesoreriaId es requerido");
        }

        Tesoreria t = tesoreriaRepo.findById(req.getTesoreriaId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Tesorería no encontrada"));

        Short tipoShort = toShortTipoId(req.getTipoId());

        Categoria c = new Categoria();
        c.setNombre(req.getNombre().trim());
        c.setAplicaA(req.getTipoId()); // entidad sigue con Short
        c.setFinanzasGenerales(Boolean.TRUE.equals(req.getFinanzasGenerales()));
        c.setTesoreria(t);

        c = repository.save(c);
        return new CategoriaRes(
                c.getId(),
                c.getNombre(),
                c.getAplicaA(), // exponer como Long hacia fuera
                Boolean.TRUE.equals(c.getFinanzasGenerales())
        );
    }

    @Override
    public List<CategoriaRes> listarPorTesoreriaYTipo(Long tesoreriaId, Long tipoId) {
        Short tipoShort = toShortTipoId(tipoId);
        return repository.findByTesoreria_IdAndAplicaAOrderByNombreAsc(tesoreriaId, tipoId)
                .stream()
                .map(c -> new CategoriaRes(
                c.getId(),
                c.getNombre(),
                c.getAplicaA(),
                Boolean.TRUE.equals(c.getFinanzasGenerales())))
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    @Transactional
    public CategoriaRes actualizarCategoria(Long id, ActualizarCategoriaReq req) {
        Categoria c = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Categoría no encontrada"));

        if (req.getTesoreriaId() != null && !c.getTesoreria().getId().equals(req.getTesoreriaId())) {
            throw new ResponseStatusException(BAD_REQUEST, "No se permite cambiar la tesorería de la categoría");
        }

        if (req.getNombre() != null) {
            c.setNombre(req.getNombre().trim());
        }
        if (req.getTipoId() != null) {
            c.setAplicaA(req.getTipoId());
        }
        if (req.getFinanzasGenerales() != null) {
            c.setFinanzasGenerales(req.getFinanzasGenerales());
        }

        c = repository.save(c);
        return new CategoriaRes(
                c.getId(),
                c.getNombre(),
                c.getAplicaA(),
                Boolean.TRUE.equals(c.getFinanzasGenerales()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaMiniRes> listarPorTesoreriaYTipoNombre(Long tesoreriaId, String tipoNombre) {
        if (tesoreriaId == null) {
            throw new ResponseStatusException(BAD_REQUEST, "tesoreriaId es requerido");
        }
        // Usa el repo por nombre:
        List<Categoria> list = repository.findByTesoreriaAndTipoNombre(tesoreriaId, tipoNombre);

        return list.stream()
                .map(c -> new CategoriaMiniRes(
                c.getId(),
                c.getNombre(),
                // Devuelve el texto del tipo para compatibilidad con tu DTO MiniRes
                tipoNombre,
                Boolean.TRUE.equals(c.getFinanzasGenerales())
        ))
                .collect(Collectors.toList());
    }
}
