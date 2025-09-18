/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.controllers;

import com.ad_samaria.commons.CommonController;
import com.ad_samaria.models.EstatusMembresia;
import com.ad_samaria.services.EstatusMembresiaSvc;
import com.ad_samaria.validator.EstatusMembresiaValidator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Yocary
 */
@RequestMapping("/estatus-membresia")
@RestController
public class EstatusMembresiaController extends CommonController<EstatusMembresia, EstatusMembresiaSvc, EstatusMembresiaValidator> {

}
