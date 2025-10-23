/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services.impl;

import com.ad_samaria.commons.CommonSvcImpl;
import com.ad_samaria.models.Evento;
import com.ad_samaria.models.Ofrenda;
import com.ad_samaria.repositories.EventoRepository;
import com.ad_samaria.repositories.OfrendaRepository;
import com.ad_samaria.services.OfrendaSvc;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Yocary
 */
@Service
public class OfrendaSvcImpl extends CommonSvcImpl<Ofrenda, OfrendaRepository> implements OfrendaSvc {

    @Autowired
    private EventoRepository eventoRepo;

    @Override
    @Transactional(readOnly = true)
    public List<Ofrenda> listarPorEvento(Long eventoId) {
        // Valida existencia del evento para dar 404 si no existe
        if (!eventoRepo.existsById(eventoId)) {
            throw new EntityNotFoundException("Evento no encontrado");
        }
        return repository.findByEventoIdOrderByFechaDesc(eventoId);
    }

    @Override
    @Transactional
    public Ofrenda crearParaEvento(Long eventoId, Date fecha, BigDecimal cantidad, String descripcion) {
        // 1) Verifica que el evento exista
        Evento ev = eventoRepo.findById(eventoId)
                .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado"));

        // 2) Validaciones básicas de negocio
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha es requerida");
        }
        if (cantidad == null || cantidad.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("La cantidad debe ser >= 0");
        }

        // 3) Crear entidad
        Ofrenda o = new Ofrenda();
        o.setFecha(fecha);
        o.setDescripcion(descripcion);
        o.setCantidad(cantidad);
        // Tu modelo usa campo simple 'eventoId' (no relación @ManyToOne)
        o.setEventoId(ev.getId());

        // 4) Guardar
        return repository.save(o);
    }

    @Override
    public Optional<Ofrenda> findById(Long id) {
        return repository.findById(id);
    }
}
