/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.repositories;

import com.ad_samaria.models.Persona;
import com.ad_samaria.projections.FichaFamiliaProjection;
import com.ad_samaria.projections.FichaGrupoProjection;
import com.ad_samaria.projections.FichaLiderazgoProjection;
import com.ad_samaria.projections.PersonaFichaCabeceraProjection;
import com.ad_samaria.projections.PersonaMiniProjection;
import java.util.List;
import java.util.Optional;
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
public interface PersonaRepository extends CrudRepository<Persona, Object> {

    @Query(value
            = "SELECT p.id, "
            + "       TRIM(p.nombres || ' ' || p.apellido_paterno || ' ' || COALESCE(p.apellido_materno,'')) AS nombre "
            + "FROM ad_samaria.persona p "
            + "WHERE LOWER(TRIM(p.nombres || ' ' || p.apellido_paterno || ' ' || COALESCE(p.apellido_materno,''))) "
            + "      LIKE LOWER(CONCAT('%', :q, '%')) "
            + "ORDER BY p.apellido_paterno ASC, p.apellido_materno ASC "
            + "LIMIT 20",
            nativeQuery = true)
    @Transactional(readOnly = true)
    List<Object[]> buscarMin(@Param("q") String q);

    @Query(value
            = "SELECT \n"
            + "  p.id AS id,\n"
            + "  TRIM(p.nombres || ' ' || p.apellido_paterno || COALESCE(' ' || p.apellido_materno, '')) AS nombre\n"
            + "FROM \n"
            + "  ad_samaria.persona p\n"
            + "ORDER BY \n"
            + "  p.apellido_paterno, p.apellido_materno NULLS LAST, p.nombres",
            nativeQuery = true)
    @Transactional(readOnly = true)
    List<PersonaMiniProjection> listarPersonasMini();

    @Query(value
            = "SELECT p.id AS id, "
            + "       TRIM(p.nombres || ' ' || p.apellido_paterno || ' ' || COALESCE(p.apellido_materno,'')) AS nombreCompleto, "
            + "       em.nombre AS estatus, "
            + "       cs.nombre AS clasificacion, "
            + "       tp.nombre AS tipoPersona, "
            + "       ec.nombre AS estadoCivil, "
            + "       s.nombre  AS sexo, "
            + "       CASE WHEN p.fecha_nacimiento IS NULL THEN NULL "
            + "            ELSE CAST(EXTRACT(YEAR FROM AGE(current_date, p.fecha_nacimiento)) AS INT) END AS edad, "
            + "       p.telefono AS telefono, "
            + "        p.direccion AS direccion, "
            + "        TO_CHAR(p.fecha_nacimiento, 'DD/MM/YYYY') AS fechaNacimiento, "
            + "       m.nombre AS ministerio "
            + "FROM ad_samaria.persona p "
            + "LEFT JOIN ad_samaria.estatus_membresia em ON em.id = p.estatus_id "
            + "LEFT JOIN ad_samaria.clasificacion_social cs ON cs.id = p.clasif_social_id "
            + "LEFT JOIN ad_samaria.tipo_persona tp ON tp.id = p.tipo_persona_id "
            + "LEFT JOIN ad_samaria.estado_civil ec ON ec.id = p.estado_civil_id "
            + "LEFT JOIN ad_samaria.sexo s ON s.id = p.sexo_id "
            + "LEFT JOIN ad_samaria.ministerio m ON m.id = p.ministerio_id "
            + "WHERE p.id = :id", nativeQuery = true)
    @Transactional(readOnly = true)
    Optional<PersonaFichaCabeceraProjection> obtenerCabecera(@Param("id") Long id);

    @Query(value
            = "SELECT f.id AS familiaId, f.nombre AS familiaNombre, rf.nombre AS rolFamiliar "
            + "FROM ad_samaria.familia_persona fp "
            + "JOIN ad_samaria.familia f ON f.id = fp.familia_id "
            + "JOIN ad_samaria.rol_familiar rf ON rf.id = fp.rol_fam_id "
            + "WHERE fp.persona_id = :id "
            + "ORDER BY f.nombre", nativeQuery = true)
    @Transactional(readOnly = true)
    List<FichaFamiliaProjection> familiasDePersona(@Param("id") Long personaId);

    @Query(value
            = "SELECT g.id AS grupoId, g.nombre AS grupoNombre, COALESCE(m.nombre,'') AS ministerio "
            + "FROM ad_samaria.grupo_persona gp "
            + "JOIN ad_samaria.grupo g ON g.id = gp.grupo_id "
            + "LEFT JOIN ad_samaria.ministerio m ON m.id = g.ministerio_id "
            + "WHERE gp.persona_id = :id "
            + "ORDER BY g.nombre", nativeQuery = true)
    @Transactional(readOnly = true)
    List<FichaGrupoProjection> gruposDePersona(@Param("id") Long personaId);

    @Query(value
            = "SELECT lm.liderazgo_id AS liderazgoId, l.nombre AS liderazgo, lr.nombre AS rol, "
            + "       lm.desde AS desde, lm.hasta AS hasta "
            + "FROM ad_samaria.liderazgo_miembro lm "
            + "JOIN ad_samaria.liderazgo l ON l.id = lm.liderazgo_id "
            + "JOIN ad_samaria.liderazgo_rol lr ON lr.id = lm.liderazgo_rol_id "
            + "WHERE lm.persona_id = :id "
            + "ORDER BY lm.desde DESC", nativeQuery = true)
    @Transactional(readOnly = true)
    List<FichaLiderazgoProjection> liderazgosDePersona(@Param("id") Long personaId);

}
