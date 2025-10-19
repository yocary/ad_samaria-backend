/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.repositories;

import com.ad_samaria.models.RolFamiliar;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Yocary
 */
@Repository
public interface RolFamiliarRepository extends CrudRepository<RolFamiliar, Object> {

    @Query(value = "SELECT id, nombre FROM ad_samaria.rol_familiar ORDER BY nombre", nativeQuery = true)
    List<Object[]> listarComoFilas();
}
