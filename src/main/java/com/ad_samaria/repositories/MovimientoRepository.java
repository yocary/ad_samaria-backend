/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.repositories;

import com.ad_samaria.models.Movimiento;
import com.ad_samaria.projections.MovimientoProjection;
import com.ad_samaria.projections.ResumenProjection;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Yocary
 */
@Repository
public interface MovimientoRepository extends CrudRepository<Movimiento, Object> {

    @Query(value
            = "SELECT m.tesoreria_id AS tesoreriaId, "
            + "       COALESCE(SUM(CASE WHEN m.tipo_id = 1 THEN m.cantidad ELSE 0 END),0) AS ingresos, "
            + "       COALESCE(SUM(CASE WHEN m.tipo_id = 2 THEN m.cantidad ELSE 0 END),0) AS egresos "
            + "FROM ad_samaria.movimiento m "
            + "WHERE m.fecha BETWEEN :desde AND :hasta "
            + "GROUP BY m.tesoreria_id",
            nativeQuery = true)
    List<Object[]> totalesPorTesoreriaEntre(@Param("desde") LocalDate desde,
            @Param("hasta") LocalDate hasta);

    @Query(value = ""
            + "SELECT "
            + "  m.id                    AS id, "
            + "  m.tesoreria_id         AS treasuryId, "
            + "  CASE "
            + "    WHEN LOWER(tm.nombre) LIKE 'ingreso%%' THEN 'Ingreso' "
            + "    ELSE 'Egreso' "
            + "  END                    AS type, "
            + "  m.fecha                AS date, "
            + "  m.concepto             AS concept, "
            + "  COALESCE(c.nombre, m.concepto, '') AS category, "
            + "  m.cantidad             AS amount, "
            + "  mp.nombre              AS method, "
            + "  (COALESCE(p.nombres,'') "
            + "    || CASE WHEN COALESCE(p.apellido_paterno,'') <> '' THEN ' ' || p.apellido_paterno ELSE '' END "
            + "    || CASE WHEN COALESCE(p.apellido_materno,'') <> '' THEN ' ' || p.apellido_materno ELSE '' END) "
            + "    AS memberName, "
            + "  COALESCE(m.observaciones, '') AS notes "
            + "FROM ad_samaria.movimiento m "
            + "JOIN ad_samaria.tipo_movimiento tm ON tm.id = m.tipo_id "
            + "JOIN ad_samaria.metodo_pago mp     ON mp.id = m.metodo_pago_id "
            + "LEFT JOIN ad_samaria.categoria c   ON c.id = m.categoria_id "
            + "LEFT JOIN ad_samaria.persona p     ON p.id = m.persona_id "
            + "WHERE m.tesoreria_id = :tesoreriaId "
            + "  AND m.fecha BETWEEN :ini AND :fin "
            + "  AND ( :q = '' "
            + "        OR m.concepto ILIKE CONCAT('%', :q, '%') "
            + "        OR COALESCE(c.nombre, '')  ILIKE CONCAT('%', :q, '%') ) "
            + "ORDER BY m.fecha DESC, m.id DESC",
            nativeQuery = true)
    List<MovimientoProjection> findMovimientos(
            @Param("tesoreriaId") Long tesoreriaId,
            @Param("ini") LocalDate ini,
            @Param("fin") LocalDate fin,
            @Param("q") String q
    );

    @Query(value = "SELECT "
            + "  COALESCE(SUM(CASE WHEN LOWER(tm.nombre) LIKE 'ingreso%%' THEN m.cantidad ELSE 0 END),0) AS totalIngresos, "
            + "  COALESCE(SUM(CASE WHEN LOWER(tm.nombre) LIKE 'egreso%%'  THEN m.cantidad ELSE 0 END),0) AS totalEgresos "
            + "FROM ad_samaria.movimiento m "
            + "JOIN ad_samaria.tipo_movimiento tm ON tm.id = m.tipo_id "
            + "WHERE m.tesoreria_id = :tesoreriaId "
            + "  AND m.fecha BETWEEN :ini AND :fin",
            nativeQuery = true)
    ResumenProjection resumenTesoreria(
            @Param("tesoreriaId") Long tesoreriaId,
            @Param("ini") LocalDate ini,
            @Param("fin") LocalDate fin
    );
}
