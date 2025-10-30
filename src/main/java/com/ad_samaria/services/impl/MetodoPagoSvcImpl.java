/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services.impl;

import com.ad_samaria.commons.CommonSvcImpl;
import com.ad_samaria.dto.MetodoPagoDto;
import com.ad_samaria.models.MetodoPago;
import com.ad_samaria.repositories.MetodoPagoRepository;
import com.ad_samaria.services.MetodoPagoSvc;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 *
 * @author Yocary
 */
@Service
public class MetodoPagoSvcImpl extends CommonSvcImpl<MetodoPago, MetodoPagoRepository> implements MetodoPagoSvc {

    public List<MetodoPagoDto> listarActivos() {
        return repository.findAllByOrderByNombreAsc()
                .stream()
                .map(MetodoPagoDto::of)
                .collect(Collectors.toList());
    }
}
