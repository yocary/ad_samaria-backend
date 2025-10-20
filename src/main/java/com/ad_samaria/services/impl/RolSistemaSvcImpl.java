/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services.impl;

import com.ad_samaria.commons.CommonSvcImpl;
import com.ad_samaria.dto.RolSistemaDTO;
import com.ad_samaria.models.RolSistema;
import com.ad_samaria.repositories.RolSistemaRepository;
import com.ad_samaria.services.RolSistemaSvc;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Yocary
 */
@Service
public class RolSistemaSvcImpl extends CommonSvcImpl<RolSistema, RolSistemaRepository> implements RolSistemaSvc {

    @Override
    @Transactional(readOnly = true)
    public List<RolSistemaDTO> listar() {
        List<RolSistema> roles = repository.listarOrdenado();
        return roles.stream()
                .map(r -> new RolSistemaDTO(r.getId(), r.getNombre()))
                .collect(Collectors.toList());
    }
}
