/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.repositories;

import com.ad_samaria.models.Asistencia;
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
public interface AsistenciaRepository extends CrudRepository<Asistencia, Object> {

    List<Asistencia> findByEventoId(Long eventoId);

    @Query(value
            = "SELECT p.id            AS personaId, "
            + "       TRIM(p.nombres || ' ' || p.apellido_paterno || COALESCE(' ' || p.apellido_materno,'')) AS nombre, "
            + "       a.presente      AS presente, "
            + "       a.id            AS asistenciaId "
            + "FROM ad_samaria.liderazgo_miembro lm "
            + "JOIN ad_samaria.persona p ON p.id = lm.persona_id "
            + "LEFT JOIN ad_samaria.asistencia a ON a.persona_id = p.id AND a.evento_id = :eventoId "
            + "WHERE lm.liderazgo_id = :liderazgoId "
            + "ORDER BY p.apellido_paterno, p.apellido_materno NULLS LAST, p.nombres",
            nativeQuery = true)
    List<Object[]> listarAsistencia(@Param("liderazgoId") Long liderazgoId,
            @Param("eventoId") Long eventoId);

    // UPSERT por par (evento_id, persona_id)
    @Modifying
    @Transactional
    @Query(value
            = "INSERT INTO ad_samaria.asistencia (evento_id, persona_id, presente, observacion) "
            + "VALUES (:eventoId, :personaId, :presente, :observacion) "
            + "ON CONFLICT (evento_id, persona_id) DO UPDATE "
            + "SET presente = EXCLUDED.presente, observacion = EXCLUDED.observacion",
            nativeQuery = true)
    void upsert(@Param("eventoId") Long eventoId,
            @Param("personaId") Long personaId,
            @Param("presente") Boolean presente,
            @Param("observacion") String observacion);
}
