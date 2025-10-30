/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.repositories;

import com.ad_samaria.models.Tesoreria;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Yocary
 */
@Repository
public interface TesoreriaRepository extends CrudRepository<Tesoreria, Object> {

    boolean existsByNombreIgnoreCase(String nombre);

    @Query("SELECT t FROM Tesoreria t "
            + "WHERE (:estado = 'todas' "
            + "   OR (:estado = 'activas' AND t.estado = true) "
            + "   OR (:estado = 'inactivas' AND t.estado = false)) "
            + "AND ( :q IS NULL "
            + "      OR LOWER(t.nombre) LIKE CONCAT('%', LOWER(COALESCE(CAST(:q as string), '')), '%') ) "
            + "ORDER BY t.nombre ASC")
    List<Tesoreria> search(@Param("estado") String estado, @Param("q") String q);

    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long excludeId);


}
