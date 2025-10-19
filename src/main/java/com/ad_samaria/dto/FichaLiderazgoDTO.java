/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.dto;

import java.time.LocalDate;

/**
 *
 * @author Pablo
 */
public class FichaLiderazgoDTO {

    public Long liderazgoId;
    public String liderazgo;
    public String rol;
    public LocalDate desde;
    public LocalDate hasta;

    public FichaLiderazgoDTO() {
    }

    public FichaLiderazgoDTO(Long liderazgoId, String liderazgo, String rol,
            LocalDate desde, LocalDate hasta) {
        this.liderazgoId = liderazgoId;
        this.liderazgo = liderazgo;
        this.rol = rol;
        this.desde = desde;
        this.hasta = hasta;
    }
}
