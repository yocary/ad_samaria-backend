/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services.impl;

import com.ad_samaria.commons.CommonSvcImpl;
import com.ad_samaria.dto.EventoItemDTO;
import com.ad_samaria.models.Evento;
import com.ad_samaria.repositories.EventoRepository;
import com.ad_samaria.repositories.LiderazgoRepository;
import com.ad_samaria.services.EventoSvc;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Yocary
 */
@Service
public class EventoSvcImpl extends CommonSvcImpl<Evento, EventoRepository> implements EventoSvc {

    @Autowired
    private LiderazgoRepository liderazgoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EventoItemDTO> listarEventos(Long liderazgoId) {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        return repository.findByLiderazgoIdOrderByFechaDesc(liderazgoId)
                .stream()
                .map(e -> new EventoItemDTO(
                e.getId(),
                e.getNombre(),
                e.getFecha() != null ? fmt.format(e.getFecha()) : null,
                e.getDescripcion()
        ))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Evento crearEvento(Long liderazgoId, String nombre, Date fecha, String descripcion) {
        Evento e = new Evento();
        e.setLiderazgoId(liderazgoId);
        e.setNombre(nombre);
        e.setFecha(fecha);
        e.setDescripcion(descripcion);
        e.setAvisoWhatsapp(Boolean.FALSE);
        return repository.save(e);
    }

    @Override
    @Transactional
    public void guardarObservacion(Long liderazgoId, Long eventoId, String observacion) {
        Evento ev = repository.findByIdAndLiderazgoId(eventoId, liderazgoId)
                .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado para el liderazgo indicado"));

        ev.setDescripcion(observacion != null ? observacion.trim() : null);
        repository.save(ev);
    }

    @Override
    @Transactional(readOnly = true)
    public String obtenerObservacion(Long liderazgoId, Long eventoId) {
        return repository.findByIdAndLiderazgoId(eventoId, liderazgoId)
                .map(Evento::getDescripcion)
                .orElse("");
    }
}
