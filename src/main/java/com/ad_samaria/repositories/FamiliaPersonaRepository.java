/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.repositories;

import com.ad_samaria.models.FamiliaPersona;
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
public interface FamiliaPersonaRepository extends CrudRepository<FamiliaPersona, Object> {

    @Query(value
            = "SELECT fp.id, fp.persona_id, "
            + "  TRIM(p.nombres || ' ' || p.apellido_paterno || COALESCE(' ' || p.apellido_materno, '')) AS nombre, "
            + "  rf.nombre AS rol, rf.id AS rol_id "
            + "FROM ad_samaria.familia_persona fp "
            + "JOIN ad_samaria.persona p ON p.id = fp.persona_id "
            + "JOIN ad_samaria.rol_familiar rf ON rf.id = fp.rol_fam_id "
            + "WHERE fp.familia_id = :familiaId "
            + "ORDER BY nombre", nativeQuery = true)
    List<Object[]> listarMiembros(@Param("familiaId") Long familiaId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ad_samaria.familia_persona WHERE id = :id", nativeQuery = true)
    int eliminarVinculo(@Param("id") Long id);

    boolean existsByFamiliaIdAndPersonaId(Long familiaId, Long personaId);

}
