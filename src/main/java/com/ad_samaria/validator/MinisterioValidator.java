/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.validator;

import com.ad_samaria.commons.CommonValidatorSvc;
import com.ad_samaria.models.Ministerio;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Yocary
 */
@Component("MinisterioValidator")
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class MinisterioValidator implements CommonValidatorSvc<Ministerio> {
    
    @Override
    public Ministerio validate(Ministerio e) {
        return e;
    }
}
