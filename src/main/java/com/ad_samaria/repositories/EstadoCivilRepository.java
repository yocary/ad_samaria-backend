/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.repositories;

import com.ad_samaria.models.EstadoCivil;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Yocary
 */
@Repository
public interface EstadoCivilRepository extends CrudRepository<EstadoCivil, Object> {

    List<EstadoCivil> findAllByOrderByNombreAsc();

}
