/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author Yocary
 */
public class MovimientoRowDTO {

    public Long id;
    public Long treasuryId;
    public String type;       // 'Ingreso' | 'Egreso'
    public LocalDate date;
    public String concept;
    public String category;
    public BigDecimal amount;
    public String method;
    public String memberName;
    public String notes;

    public MovimientoRowDTO(Long id, Long treasuryId, String type, LocalDate date,
            String concept, String category, BigDecimal amount,
            String method, String memberName, String notes) {
        this.id = id;
        this.treasuryId = treasuryId;
        this.type = type;
        this.date = date;
        this.concept = concept;
        this.category = category;
        this.amount = amount;
        this.method = method;
        this.memberName = memberName;
        this.notes = notes;
    }
}
