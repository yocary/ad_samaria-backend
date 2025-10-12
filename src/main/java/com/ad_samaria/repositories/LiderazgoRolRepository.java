/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.repositories;

import com.ad_samaria.models.LiderazgoRol;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Yocary
 */
@Repository
public interface LiderazgoRolRepository extends CrudRepository<LiderazgoRol, Object> {

    @Query(value
            = "SELECT id, liderazgo_id, nombre "
            + "FROM ad_samaria.liderazgo_rol "
            + "WHERE liderazgo_id = :liderazgoId "
            + "ORDER BY nombre ASC",
            nativeQuery = true)
    List<Object[]> listarPorLiderazgo(@Param("liderazgoId") Long liderazgoId);

    @Modifying
    @Transactional
    @Query(value
            = "INSERT INTO ad_samaria.liderazgo_rol (liderazgo_id, nombre) VALUES (:liderazgoId, :nombre)",
            nativeQuery = true)
    void crear(@Param("liderazgoId") Long liderazgoId, @Param("nombre") String nombre);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ad_samaria.liderazgo_rol WHERE id = :rolId", nativeQuery = true)
    int eliminar(@Param("rolId") Long rolId);

}
