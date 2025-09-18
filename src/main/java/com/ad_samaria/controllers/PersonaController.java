/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.controllers;

import com.ad_samaria.commons.CommonController;
import com.ad_samaria.models.Persona;
import com.ad_samaria.services.PersonaSvc;
import com.ad_samaria.validator.PersonaValidator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Yocary
 */
@RequestMapping("/persona")
@RestController
public class PersonaController extends CommonController<Persona, PersonaSvc, PersonaValidator> {

}
