/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.controllers;

import com.ad_samaria.commons.CommonController;
import com.ad_samaria.models.LiderazgoMiembro;
import com.ad_samaria.services.LiderazgoMiembroSvc;
import com.ad_samaria.validator.LiderazgoMiembroValidator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Yocary
 */
@RequestMapping("/liderazgo-miembro")
@RestController
public class LiderazgoMiembroController extends CommonController<LiderazgoMiembro, LiderazgoMiembroSvc, LiderazgoMiembroValidator> {

}