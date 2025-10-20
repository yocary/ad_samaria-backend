/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.validator;

import com.ad_samaria.commons.CommonValidatorSvc;
import com.ad_samaria.models.PersonaRolSistema;
import org.springframework.stereotype.Component;

/**
 *
 * @author Yocary
 */
@Component("PersonaRolSistemaValidator")
public class PersonaRolSistemaValidator implements CommonValidatorSvc<PersonaRolSistema> {

    @Override
    public PersonaRolSistema validate(PersonaRolSistema e) {
        return e;
    }

}
