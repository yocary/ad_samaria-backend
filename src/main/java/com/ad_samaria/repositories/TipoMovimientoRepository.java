/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.repositories;

import com.ad_samaria.models.TipoMovimiento;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Yocary
 */
@Repository
public interface TipoMovimientoRepository extends CrudRepository<TipoMovimiento, Object> {

    @Query("SELECT t.id FROM TipoMovimiento t WHERE LOWER(t.nombre) LIKE LOWER(CONCAT(:nombre, '%'))")
    Optional<Long> findIdByNombreILike(@Param("nombre") String nombre);
}
