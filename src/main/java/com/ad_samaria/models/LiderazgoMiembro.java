/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Yocary
 */
@Entity
@Table(name = "liderazgo_miembro", schema = "ad_samaria")
public class LiderazgoMiembro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "liderazgo_id", nullable = false)
    private Long liderazgoId;

    @Column(name = "persona_id", nullable = false)
    private Long personaId;

    @Column(name = "liderazgo_rol_id", nullable = false)
    private Long liderazgoRolId;

    @Column(name = "desde", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date desde;

    @Column(name = "hasta")
    @Temporal(TemporalType.DATE)
    private Date hasta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLiderazgoId() {
        return liderazgoId;
    }

    public void setLiderazgoId(Long liderazgoId) {
        this.liderazgoId = liderazgoId;
    }

    public Long getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Long personaId) {
        this.personaId = personaId;
    }

    public Long getLiderazgoRolId() {
        return liderazgoRolId;
    }

    public void setLiderazgoRolId(Long liderazgoRolId) {
        this.liderazgoRolId = liderazgoRolId;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

}
