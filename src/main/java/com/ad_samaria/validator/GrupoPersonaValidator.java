/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.validator;

import com.ad_samaria.commons.CommonValidatorSvc;
import com.ad_samaria.models.GrupoPersona;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Yocary
 */
@Component("GrupoPersonaValidator")
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class GrupoPersonaValidator implements CommonValidatorSvc<GrupoPersona> {
    
    @Override
    public GrupoPersona validate(GrupoPersona e) {
        return e;
    }
}

