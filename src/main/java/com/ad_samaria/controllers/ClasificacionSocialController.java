/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.controllers;

import com.ad_samaria.commons.CommonController;
import com.ad_samaria.models.ClasificacionSocial;
import com.ad_samaria.services.ClasificacionSocialSvc;
import com.ad_samaria.validator.ClasificacionSocialValidator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Yocary
 */
@RequestMapping("/clasificacion-social")
@RestController
public class ClasificacionSocialController extends CommonController<ClasificacionSocial, ClasificacionSocialSvc, ClasificacionSocialValidator> {

}

