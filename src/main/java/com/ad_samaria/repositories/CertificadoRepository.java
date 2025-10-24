/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.repositories;

import com.ad_samaria.models.Certificado;
import com.ad_samaria.projections.CertificadoPdfBaseProjection;
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
public interface CertificadoRepository extends CrudRepository<Certificado, Object> {

    // Borrar por id
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ad_samaria.certificado WHERE id = :id", nativeQuery = true)
    int eliminar(@Param("id") Long id);

    // Trae info base para PDF por id (tipo y nombres)
    @Query(value
            = "SELECT "
            + "  c.id AS id, "
            + "  tc.nombre AS tipoNombre, "
            + "  c.fecha AS fecha, "
            + "  COALESCE(p.nombres || ' ' || p.apellido_paterno || ' ' || COALESCE(p.apellido_materno,''), '') AS miembroNombre, "
            + "  COALESCE(pas.nombres || ' ' || pas.apellido_paterno || ' ' || COALESCE(pas.apellido_materno,''), '') AS pastorNombre "
            + "FROM ad_samaria.certificado c "
            + "JOIN ad_samaria.tipo_certificado tc ON tc.id = c.tipo_id "
            + "LEFT JOIN ad_samaria.persona p ON p.id = c.miembro_id "
            + "LEFT JOIN ad_samaria.persona pas ON pas.id = c.pastor_id "
            + "WHERE c.id = :id",
            nativeQuery = true)
    @Transactional(readOnly = true)
    CertificadoPdfBaseProjection datosPdfPorId(@Param("id") Long id);

    @Query(value
            = "SELECT c.id                                      AS id, "
            + "       COALESCE((c.observaciones::jsonb)->>'nombreMiembro','') AS miembro, "
            + "       t.nombre                                 AS tipo, "
            + "       c.fecha                                   AS fecha "
            + "FROM ad_samaria.certificado c "
            + "JOIN ad_samaria.tipo_certificado t ON t.id = c.tipo_id "
            + "WHERE (:q IS NULL "
            + "   OR COALESCE((c.observaciones::jsonb)->>'nombreMiembro','') ILIKE CONCAT('%', :q, '%') "
            + "   OR t.nombre ILIKE CONCAT('%', :q, '%')) "
            + "ORDER BY c.fecha DESC, c.id DESC",
            nativeQuery = true)
    List<Object[]> listarComoFilas(@Param("q") String q);
}
