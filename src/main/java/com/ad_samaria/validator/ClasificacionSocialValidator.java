/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.validator;

import com.ad_samaria.commons.CommonValidatorSvc;
import com.ad_samaria.models.ClasificacionSocial;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Yocary
 */
@Component("ClasificacionSocialValidator")
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class ClasificacionSocialValidator implements CommonValidatorSvc<ClasificacionSocial> {

    @Override
    public ClasificacionSocial validate(ClasificacionSocial e) {
        return e;
    }
}
