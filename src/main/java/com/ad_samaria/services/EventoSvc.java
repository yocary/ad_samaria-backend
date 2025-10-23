/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services;

import com.ad_samaria.commons.CommonSvc;
import com.ad_samaria.dto.EventoItemDTO;
import com.ad_samaria.models.Evento;
import java.util.List;

/**
 *
 * @author Yocary
 */
public interface EventoSvc extends CommonSvc<Evento> {

    List<EventoItemDTO> listarEventos(Long liderazgoId);

    Evento crearEvento(Long liderazgoId, String nombre, java.util.Date fecha, String descripcion);

    void guardarObservacion(Long liderazgoId, Long eventoId, String observacion);

    String obtenerObservacion(Long liderazgoId, Long eventoId);
}
