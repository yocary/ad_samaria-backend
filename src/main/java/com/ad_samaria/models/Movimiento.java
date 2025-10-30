/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Yocary
 */
@Entity
@Table(name = "movimiento", schema = "ad_samaria")
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tesoreria_id", nullable = false)
    private Long tesoreriaId;

    @Column(name = "tipo_id", nullable = false)
    private Long tipoId;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(length = 200, nullable = false)
    private String concepto;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal cantidad;

    @Column(name = "metodo_pago_id", nullable = false)
    private Long metodoPagoId;

    @Column(name = "categoria_id", nullable = false)
    private Long categoriaId;

    @Column(name = "persona_id")
    private Long personaId;

    @Column(columnDefinition = "text")
    private String observaciones;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTesoreriaId() {
        return tesoreriaId;
    }

    public void setTesoreriaId(Long tesoreriaId) {
        this.tesoreriaId = tesoreriaId;
    }

    public Long getTipoId() {
        return tipoId;
    }

    public void setTipoId(Long tipoId) {
        this.tipoId = tipoId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public Long getMetodoPagoId() {
        return metodoPagoId;
    }

    public void setMetodoPagoId(Long metodoPagoId) {
        this.metodoPagoId = metodoPagoId;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Long getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Long personaId) {
        this.personaId = personaId;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
