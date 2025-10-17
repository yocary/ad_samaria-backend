/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services;

import com.ad_samaria.commons.CommonSvc;
import com.ad_samaria.dto.CrearRolRequest;
import com.ad_samaria.dto.EditarRolRequest;
import com.ad_samaria.dto.LiderazgoListadoDTO;
import com.ad_samaria.dto.LiderazgoMiembroDTO;
import com.ad_samaria.models.Liderazgo;
import com.ad_samaria.projections.RolListadoProjection;
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

    List<LiderazgoMiembroDTO> listarMiembros(Long liderazgoId);

    void desactivarMiembro(Long liderazgoMiembroId);

    List<RolListadoProjection> listarRoles(Long liderazgoId);

    void crearRol(Long liderazgoId, CrearRolRequest req);

    void editarRol(Long liderazgoId, Long rolId, EditarRolRequest req);

    void eliminarRol(Long liderazgoId, Long rolId);
    
    void agregarMiembro(long liderazgoId, long personaId, long rolId);

}
