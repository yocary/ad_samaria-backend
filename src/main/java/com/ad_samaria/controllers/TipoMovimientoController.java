/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.controllers;

import com.ad_samaria.commons.CommonController;
import com.ad_samaria.models.TipoMovimiento;
import com.ad_samaria.services.TipoMovimientoSvc;
import com.ad_samaria.validator.TipoMovimientoValidator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Yocary
 */
@RequestMapping("/tipo-movimiento")
@RestController
public class TipoMovimientoController extends CommonController<TipoMovimiento, TipoMovimientoSvc, TipoMovimientoValidator> {

}
