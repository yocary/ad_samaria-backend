/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.repositories;

import com.ad_samaria.models.Liderazgo;
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
public interface LiderazgoRepository extends CrudRepository<Liderazgo, Object> {

    // Listado con total de miembros activos (hasta IS NULL)
    @Query(value
            = "SELECT l.id, l.nombre, "
            + "       COALESCE(COUNT(lm.id), 0) AS total_miembros "
            + "FROM ad_samaria.liderazgo l "
            + "LEFT JOIN ad_samaria.liderazgo_miembro lm "
            + "  ON lm.liderazgo_id = l.id AND lm.hasta IS NULL "
            + "GROUP BY l.id, l.nombre "
            + "ORDER BY l.nombre ASC",
            nativeQuery = true)
    List<Object[]> listarLiderazgosConTotales();

    // Crear
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO ad_samaria.liderazgo (nombre) VALUES (:nombre)", nativeQuery = true)
    void crear(@Param("nombre") String nombre);

    // Renombrar
    @Modifying
    @Transactional
    @Query(value = "UPDATE ad_samaria.liderazgo SET nombre = :nombre WHERE id = :id", nativeQuery = true)
    int renombrar(@Param("id") Long id, @Param("nombre") String nombre);

    // Eliminar
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ad_samaria.liderazgo WHERE id = :id", nativeQuery = true)
    int eliminar(@Param("id") Long id);

}
