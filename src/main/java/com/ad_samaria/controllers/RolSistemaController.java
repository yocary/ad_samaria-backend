/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.controllers;

import com.ad_samaria.commons.CommonController;
import com.ad_samaria.models.RolSistema;
import com.ad_samaria.services.RolSistemaSvc;
import com.ad_samaria.validator.RolSistemaValidator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Yocary
 */
@RequestMapping("/rol-sistema")
@RestController
public class RolSistemaController extends CommonController<RolSistema, RolSistemaSvc, RolSistemaValidator> {

}
