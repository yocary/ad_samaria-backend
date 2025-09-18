/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.controllers;

import com.ad_samaria.commons.CommonController;
import com.ad_samaria.models.Evento;
import com.ad_samaria.services.EventoSvc;
import com.ad_samaria.validator.EventoValidator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Yocary
 */
@RequestMapping("/evento")
@RestController
public class EventoController extends CommonController<Evento, EventoSvc, EventoValidator> {

}
