/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.controllers;

import com.ad_samaria.commons.CommonController;
import com.ad_samaria.models.EstadoCivil;
import com.ad_samaria.services.EstadoCivilSvc;
import com.ad_samaria.validator.EstadoCivilValidator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Yocary
 */
@RequestMapping("/estado-civil")
@RestController
public class EstadoCivilController extends CommonController<EstadoCivil, EstadoCivilSvc, EstadoCivilValidator> {

}

