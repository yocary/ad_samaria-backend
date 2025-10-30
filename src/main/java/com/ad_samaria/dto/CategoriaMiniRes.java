/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.dto;

/**
 *
 * @author Yocary
 */
public class CategoriaMiniRes {

    public Long id;
    public String nombre;
    public String tipo; // "Ingreso" | "Egreso"

    public CategoriaMiniRes(Long id, String nombre, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
    }
}
