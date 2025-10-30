/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.dto;

import com.ad_samaria.models.MetodoPago;

/**
 *
 * @author Yocary
 */
public class MetodoPagoDto {

    private final Long id;
    private final String nombre;

    public MetodoPagoDto(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public static MetodoPagoDto of(MetodoPago m) {
        if (m == null) {
            return null;
        }
        return new MetodoPagoDto(m.getId(), m.getNombre());
    }

    @Override
    public String toString() {
        return "MetodoPagoDto{"
                + "id=" + id
                + ", nombre='" + nombre + '\''
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MetodoPagoDto that = (MetodoPagoDto) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        return nombre != null ? nombre.equals(that.nombre) : that.nombre == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        return result;
    }

}
