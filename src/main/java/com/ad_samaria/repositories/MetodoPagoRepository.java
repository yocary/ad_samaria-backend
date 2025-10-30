/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.repositories;

import com.ad_samaria.models.MetodoPago;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Yocary
 */
@Repository
public interface MetodoPagoRepository extends CrudRepository<MetodoPago, Object> {

    // Lista solo activos ordenados por nombre
    List<MetodoPago> findAllByOrderByNombreAsc();

    // Útil si alguna vez necesitas resolver por nombre (edición/compatibilidad)
    @Query("select m from MetodoPago m where lower(m.nombre) = lower(?1)")
    Optional<MetodoPago> findByNombreIgnoreCase(String nombre);

    // Si solo quieres el id por nombre:
    @Query("select m.id from MetodoPago m where lower(m.nombre) = lower(?1)")
    Optional<Long> findIdByNombreIgnoreCase(String nombre);
}
