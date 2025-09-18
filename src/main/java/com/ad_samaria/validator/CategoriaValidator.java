/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.validator;

import com.ad_samaria.commons.CommonValidatorSvc;
import com.ad_samaria.models.Categoria;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Yocary
 */
@Component("CategoriaValidator")
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class CategoriaValidator implements CommonValidatorSvc<Categoria> {
    
    @Override
    public Categoria validate(Categoria e) {
        return e;
    }
}