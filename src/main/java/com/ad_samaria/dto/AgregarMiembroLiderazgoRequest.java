/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.dto;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Yocary
 */
@Getter
@Setter
public class AgregarMiembroLiderazgoRequest {

    private Long personaId;
    private Long rolId;
    private String desde; // yyyy-MM-dd
}
