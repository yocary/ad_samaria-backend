/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.repositories;

import com.ad_samaria.models.RolSistema;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Yocary
 */
@Repository
public interface RolSistemaRepository extends CrudRepository<RolSistema, Object> {

    @Query(value = "SELECT rs FROM RolSistema rs ORDER BY rs.nombre")
    List<RolSistema> listarOrdenado();
}
