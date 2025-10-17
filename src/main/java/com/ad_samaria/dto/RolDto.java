/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.dto;

import lombok.Data;

/**
 *
 * @author Yocary
 */
@Data
public class RolDto {

    private Long id;
    private String nombre;

    public RolDto(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
