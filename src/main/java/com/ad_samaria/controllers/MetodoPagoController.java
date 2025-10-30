/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.controllers;

import com.ad_samaria.commons.CommonController;
import com.ad_samaria.dto.MetodoPagoDto;
import com.ad_samaria.models.MetodoPago;
import com.ad_samaria.services.MetodoPagoSvc;
import com.ad_samaria.validator.MetodoPagoValidator;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Yocary
 */
@RequestMapping("/metodo-pago")
@RestController
public class MetodoPagoController extends CommonController<MetodoPago, MetodoPagoSvc, MetodoPagoValidator> {

    @GetMapping("/metodos-pago")
    public List<MetodoPagoDto> getMetodosPago() {
        return service.listarActivos();
    }
}
