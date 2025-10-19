/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.projections;

/**
 *
 * @author Yocary
 */
public interface FamiliaMiembroProjection {

    Long getId();        
    Long getPersonaId();   

    String getNombre();  

    String getRol();  

    Short getRolFamId(); 

}
