/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.controllers;

import com.ad_samaria.commons.CommonController;
import com.ad_samaria.models.Sexo;
import com.ad_samaria.services.SexoSvc;
import com.ad_samaria.validator.SexoValidator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Yocary
 */
@RequestMapping("/sexo")
@RestController
public class SexoController extends CommonController<Sexo, SexoSvc, SexoValidator> {

}
