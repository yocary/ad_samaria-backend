/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services.impl;

import com.ad_samaria.commons.CommonSvcImpl;
import com.ad_samaria.models.Usuario;
import com.ad_samaria.repositories.UsuarioRepository;
import com.ad_samaria.services.UsuarioSvc;
import org.springframework.stereotype.Service;

/**
 *
 * @author Yocary
 */
@Service
public class UsuarioSvcImpl extends CommonSvcImpl<Usuario, UsuarioRepository> implements UsuarioSvc {

}

