/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services;

import com.ad_samaria.commons.CommonSvc;
import com.ad_samaria.dto.CambiarPasswordDTO;
import com.ad_samaria.dto.UsuarioCreateDTO;
import com.ad_samaria.dto.UsuarioItemDTO;
import com.ad_samaria.models.Usuario;
import java.util.List;

/**
 *
 * @author Yocary
 */
public interface UsuarioSvc extends CommonSvc<Usuario> {

    Usuario crear(UsuarioCreateDTO req);

    List<UsuarioItemDTO> listar();

    String sugerirUsername(Long personaId);

    public void cambiarPassword(CambiarPasswordDTO req);
}
