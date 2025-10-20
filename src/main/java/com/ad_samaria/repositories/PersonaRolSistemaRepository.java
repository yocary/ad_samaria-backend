/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.repositories;

import com.ad_samaria.models.PersonaRolSistema;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Yocary
 */
public interface PersonaRolSistemaRepository extends JpaRepository<PersonaRolSistema, Object> {

    boolean existsByPersonaIdAndRolIdAndActivoTrue(Long personaId, Short rolId);

    List<PersonaRolSistema> findByPersonaIdAndActivoTrue(Long personaId);

    @Modifying
    @Transactional
    @Query("UPDATE PersonaRolSistema prs SET prs.activo=false, prs.hasta = CURRENT_DATE WHERE prs.id = :id AND prs.activo=true")
    int desactivar(@Param("id") Long id);

    @Query(value
            = "SELECT prs.id, prs.rol_id AS rolId, rs.nombre AS rolNombre, "
            + "to_char(prs.desde, 'DD/MM/YYYY') AS desde, "
            + "CASE WHEN prs.hasta IS NULL THEN NULL ELSE to_char(prs.hasta, 'DD/MM/YYYY') END AS hasta "
            + "FROM ad_samaria.persona_rol_sistema prs "
            + "JOIN ad_samaria.rol_sistema rs ON rs.id = prs.rol_id "
            + "WHERE prs.persona_id = :personaId AND prs.activo = true "
            + "ORDER BY rs.nombre",
            nativeQuery = true)
    List<Object[]> listarActivosPorPersona(@Param("personaId") Long personaId);
}
