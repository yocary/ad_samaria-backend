/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.repositories;

import com.ad_samaria.models.Familia;
import com.ad_samaria.projections.FamiliaMiembroProjection;
import com.ad_samaria.projections.FamiliaMiniProjection;
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
public interface FamiliaRepository extends CrudRepository<Familia, Object> {

    @Query(value = ""
            + "SELECT f.id AS id, f.nombre AS nombre "
            + "FROM ad_samaria.familia f "
            + "WHERE (COALESCE(CAST(:q AS text), '') = '' OR "
            + "       unaccent(lower(f.nombre)) LIKE '%' || unaccent(lower(CAST(:q AS text))) || '%') "
            + "ORDER BY f.nombre",
            nativeQuery = true)
    List<FamiliaMiniProjection> listar(@Param("q") String q);

    @Modifying
    @Transactional
    @Query(value = "UPDATE ad_samaria.familia SET nombre = :nombre, actualizado_en = now() WHERE id = :id", nativeQuery = true)
    int actualizarNombre(@Param("id") Long id, @Param("nombre") String nombre);

    @Query(value = "SELECT id, nombre FROM ad_samaria.familia ORDER BY nombre", nativeQuery = true)
    List<Object[]> listarComoFilas();

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

    @Query(value = ""
            + "SELECT fp.id AS id, "
            + "       p.id AS personaId, "
            + "       TRIM(p.nombres || ' ' || p.apellido_paterno || COALESCE(' ' || p.apellido_materno, '')) AS nombre, "
            + "       rf.nombre AS rol "
            + "FROM ad_samaria.familia_persona fp "
            + "JOIN ad_samaria.persona p ON p.id = fp.persona_id "
            + "JOIN ad_samaria.rol_familiar rf ON rf.id = fp.rol_fam_id "
            + "WHERE fp.familia_id = :famId "
            + "ORDER BY p.apellido_paterno, p.apellido_materno NULLS LAST, p.nombres",
            nativeQuery = true)
    List<FamiliaMiembroProjection> listarMiembrosList(@Param("famId") Long famId);

    @Query(value = "SELECT f.id AS id, f.nombre AS nombre FROM ad_samaria.familia f WHERE f.id = :id", nativeQuery = true)
    FamiliaMiniProjection obtenerPorId(@Param("id") Long id);

}
