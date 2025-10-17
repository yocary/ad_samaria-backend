/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.repositories;

import com.ad_samaria.models.LiderazgoRol;
import com.ad_samaria.projections.RolCreadoProjection;
import com.ad_samaria.projections.RolListadoProjection;
import java.util.List;
import java.util.Optional;
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
public interface LiderazgoRolRepository extends CrudRepository<LiderazgoRol, Object> {

    @Query(value
            = "SELECT lr.id AS id, lr.nombre AS nombre "
            + "FROM ad_samaria.liderazgo_rol lr "
            + "WHERE lr.liderazgo_id = :liderazgoId "
            + "ORDER BY lr.nombre",
            nativeQuery = true)
    List<RolListadoProjection> listarRoles(@Param("liderazgoId") Long liderazgoId);

    @Query(value
            = "SELECT COUNT(*) FROM ad_samaria.liderazgo_rol "
            + "WHERE liderazgo_id = :liderazgoId AND lower(nombre) = lower(:nombre)",
            nativeQuery = true)
    long contarPorNombre(@Param("liderazgoId") Long liderazgoId,
            @Param("nombre") String nombre);

    @Query(value
            = "SELECT COUNT(*) FROM ad_samaria.liderazgo_rol "
            + "WHERE liderazgo_id = :liderazgoId AND lower(nombre) = lower(:nombre) AND id <> :excluirId",
            nativeQuery = true)
    long contarPorNombreExcluyendo(@Param("liderazgoId") Long liderazgoId,
            @Param("nombre") String nombre,
            @Param("excluirId") Long excluirId);

    @Modifying
    @Transactional
    @Query(value
            = "INSERT INTO ad_samaria.liderazgo_rol (liderazgo_id, nombre) "
            + "VALUES (:liderazgoId, :nombre)",
            nativeQuery = true)
    int crearRol(@Param("liderazgoId") Long liderazgoId,
            @Param("nombre") String nombre);

    @Modifying
    @Transactional
    @Query(value
            = "UPDATE ad_samaria.liderazgo_rol "
            + "SET nombre = COALESCE(:nombre, nombre) "
            + "WHERE id = :rolId AND liderazgo_id = :liderazgoId",
            nativeQuery = true)
    int editarRol(@Param("liderazgoId") Long liderazgoId,
            @Param("rolId") Long rolId,
            @Param("nombre") String nombre);

    @Modifying
    @Transactional
    @Query(value
            = "DELETE FROM ad_samaria.liderazgo_rol "
            + "WHERE id = :rolId AND liderazgo_id = :liderazgoId",
            nativeQuery = true)
    int eliminarRol(@Param("liderazgoId") Long liderazgoId,
            @Param("rolId") Long rolId);

}
