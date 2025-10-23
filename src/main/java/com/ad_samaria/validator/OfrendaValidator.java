/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.validator;

import com.ad_samaria.commons.CommonValidatorSvc;
import com.ad_samaria.models.Ofrenda;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Yocary
 */
@Component("OfrendaValidator")
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class OfrendaValidator implements CommonValidatorSvc<Ofrenda> {

    @Override
    public Ofrenda validate(Ofrenda e) {
        return e;
    }

}
