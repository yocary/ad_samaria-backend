/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Yocary
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LiderazgoMiembroDTO {

    private Long id;
    private Long liderazgoId;
    private Long personaId;
    private Long rolId;
    private String desde;  // dd/MM/yyyy (para mostrar)
    private String hasta;  // dd/MM/yyyy o null
    private String nombrePersona;
    private String nombreRol;

}
