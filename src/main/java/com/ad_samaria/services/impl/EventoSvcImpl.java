/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services.impl;

import com.ad_samaria.commons.CommonSvcImpl;
import com.ad_samaria.models.Evento;
import com.ad_samaria.repositories.EventoRepository;
import com.ad_samaria.services.EventoSvc;
import org.springframework.stereotype.Service;

/**
 *
 * @author Yocary
 */
@Service
public class EventoSvcImpl extends CommonSvcImpl<Evento, EventoRepository> implements EventoSvc {

}
