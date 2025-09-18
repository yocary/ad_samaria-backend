/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.validator;

import com.ad_samaria.commons.CommonValidatorSvc;
import com.ad_samaria.models.RolFamiliar;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Yocary
 */
@Component("RolFamiliarValidator")
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class RolFamiliarValidator implements CommonValidatorSvc<RolFamiliar> {
    
    @Override
    public RolFamiliar validate(RolFamiliar e) {
        return e;
    }
}
