/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services;

import com.ad_samaria.commons.CommonSvc;
import com.ad_samaria.dto.LiderazgoListadoDTO;
import com.ad_samaria.dto.LiderazgoMiembroDTO;
import com.ad_samaria.models.Liderazgo;
import java.util.List;

/**
 *
 * @author Yocary
 */
public interface LiderazgoSvc extends CommonSvc<Liderazgo> {

    List<LiderazgoListadoDTO> listar();

    void crear(String nombre);

    void renombrar(Long id, String nombre);

    void eliminar(Long id);

    List<String> listarRoles(Long liderazgoId);              // solo nombres o podrías devolver id/nombre

    void crearRol(Long liderazgoId, String nombre);

    void eliminarRol(Long rolId);

    List<LiderazgoMiembroDTO> listarMiembros(Long liderazgoId);

    void agregarMiembro(Long liderazgoId, Long personaId, Long rolId, String desde);

    void desactivarMiembro(Long liderazgoMiembroId);
}
