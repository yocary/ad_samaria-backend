/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.repositories;

import com.ad_samaria.models.LiderazgoMiembro;
import java.time.LocalDate;
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
public interface LiderazgoMiembroRepository extends CrudRepository<LiderazgoMiembro, Object> {

    @Query(value
            = "SELECT lm.id, lm.liderazgo_id, lm.persona_id, lm.liderazgo_rol_id, lm.desde, lm.hasta, "
            + "       CONCAT(p.nombres, ' ', p.apellido_paterno, ' ', COALESCE(p.apellido_materno, '')) AS nombre_persona, "
            + "       lr.nombre AS nombre_rol "
            + "FROM ad_samaria.liderazgo_miembro lm "
            + "JOIN ad_samaria.persona p ON p.id = lm.persona_id "
            + "LEFT JOIN ad_samaria.liderazgo_rol lr ON lr.id = lm.liderazgo_rol_id "
            + "WHERE lm.liderazgo_id = :liderazgoId "
            + "ORDER BY lm.desde DESC",
            nativeQuery = true)
    List<Object[]> listarMiembros(@Param("liderazgoId") Long liderazgoId);

    @Modifying
    @Transactional
    @Query(value
            = "INSERT INTO ad_samaria.liderazgo_miembro "
            + "(liderazgo_id, persona_id, liderazgo_rol_id, desde, hasta) "
            + "VALUES (:liderazgoId, :personaId, :rolId, CURRENT_DATE, NULL)",
            nativeQuery = true)
    int agregarMiembro(@Param("liderazgoId") long liderazgoId,
            @Param("personaId") long personaId,
            @Param("rolId") long rolId);

    @Modifying
    @Transactional
    @Query(value
            = "UPDATE ad_samaria.liderazgo_miembro SET hasta = CURRENT_DATE "
            + "WHERE id = :id AND hasta IS NULL",
            nativeQuery = true)
    int desactivarMiembro(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ad_samaria.liderazgo_miembro "
            + "WHERE id = :miembroId AND liderazgo_id = :liderazgoId",
            nativeQuery = true)
    int eliminarMiembro(@Param("liderazgoId") long liderazgoId,
            @Param("miembroId") long miembroId);

}
