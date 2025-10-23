/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.repositories;

import com.ad_samaria.models.Ofrenda;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Yocary
 */
public interface OfrendaRepository extends CrudRepository<Ofrenda, Object> {

    List<Ofrenda> findByEventoIdOrderByFechaDesc(Long eventoId);
}
