/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services;

import com.ad_samaria.commons.CommonSvc;
import com.ad_samaria.models.Ofrenda;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Yocary
 */
public interface OfrendaSvc extends CommonSvc<Ofrenda> {

    List<Ofrenda> listarPorEvento(Long eventoId);

    Ofrenda crearParaEvento(Long eventoId,
            Date fecha,
            BigDecimal cantidad,
            String descripcion);

    public Optional<Ofrenda> findById(Long id);
}
