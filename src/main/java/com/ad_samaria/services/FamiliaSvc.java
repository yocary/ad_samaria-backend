/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services;

import com.ad_samaria.commons.CommonSvc;
import com.ad_samaria.models.Familia;
import com.ad_samaria.models.FamiliaPersona;
import com.ad_samaria.projections.FamiliaMiembroProjection;
import com.ad_samaria.projections.FamiliaMiniProjection;
import java.util.List;

/**
 *
 * @author Yocary
 */
public interface FamiliaSvc extends CommonSvc<Familia> {

    List<FamiliaMiniProjection> listar(String q);

    Familia crear(String nombre);

    void actualizarNombre(Long id, String nombre);

    void eliminar(Long id);

    List<FamiliaMiembroProjection> listarMiembros(Long familiaId);

    FamiliaPersona agregarMiembro(Long familiaId, Long personaId, Short rolFamId);

    void eliminarMiembro(Long familiaPersonaId);

    List<Familia> listarFamilia(String q);

    FamiliaMiniProjection obtenerPorId(Long id);

}
