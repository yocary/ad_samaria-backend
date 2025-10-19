/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services.impl;

import com.ad_samaria.commons.CommonSvcImpl;
import com.ad_samaria.dto.RolDto;
import com.ad_samaria.models.RolFamiliar;
import com.ad_samaria.repositories.RolFamiliarRepository;
import com.ad_samaria.services.RolFamiliarSvc;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Yocary
 */
@Service
public class RolFamiliarSvcImpl extends CommonSvcImpl<RolFamiliar, RolFamiliarRepository> implements RolFamiliarSvc {

    @Override
    @Transactional(readOnly = true)
    public List<RolDto> listar() {
        return repository.listarComoFilas().stream()
                .map(r -> new RolDto(((Number) r[0]).shortValue(), (String) r[1]))
                .collect(Collectors.toList());
    }
}
