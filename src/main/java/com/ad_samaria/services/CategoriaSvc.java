/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services;

import com.ad_samaria.commons.CommonSvc;
import com.ad_samaria.dto.CategoriaMiniRes;
import com.ad_samaria.dto.CrearCategoriaReq;
import com.ad_samaria.dto.TipoMovimientoMini;
import com.ad_samaria.models.Categoria;
import com.ad_samaria.projections.CategoriaMini;
import java.util.List;

/**
 *
 * @author Yocary
 */
public interface CategoriaSvc extends CommonSvc<Categoria> {

    public List<CategoriaMini> listarPorTipo(String tipo, Long tipoId);

    public List<TipoMovimientoMini> listarTipos();

    public List<CategoriaMiniRes> listarPorTipoCategoria(String tipoNombre);
    
    public CategoriaMiniRes crearCategoria(CrearCategoriaReq req);
}
