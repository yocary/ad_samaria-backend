/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.controllers;

import com.ad_samaria.commons.CommonController;
import com.ad_samaria.models.Usuario;
import com.ad_samaria.services.UsuarioSvc;
import com.ad_samaria.validator.UsuarioValidator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Yocary
 */
@RequestMapping("/usuario")
@RestController
public class UsuarioController extends CommonController<Usuario, UsuarioSvc, UsuarioValidator> {

}
