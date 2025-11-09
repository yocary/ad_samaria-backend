/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.repositories;

import com.ad_samaria.models.Movimiento;
import com.ad_samaria.projections.MovimientoGeneralProjection;
import com.ad_samaria.projections.MovimientoProjection;
import com.ad_samaria.projections.ResumenProjection;
import com.ad_samaria.projections.TotalesGeneralesProjection;
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
            = "SELECT "
            + "  m.tesoreria_id AS tesoreriaId, "
            + "  COALESCE(SUM(CASE WHEN m.tipo_id = 1 THEN m.cantidad ELSE 0 END),0) AS ingresos, "
            + "  COALESCE(SUM(CASE WHEN m.tipo_id = 2 THEN m.cantidad ELSE 0 END),0) AS egresos "
            + "FROM ad_samaria.movimiento m "
            + "WHERE m.fecha BETWEEN COALESCE(CAST(:desde AS date), CAST('2000-01-01' AS date)) AND COALESCE(CAST(:hasta AS date), CAST('2100-12-31' AS date)) "
            + "GROUP BY m.tesoreria_id",
            nativeQuery = true)
    List<Object[]> totalesPorTesoreriaEntre(
            @Param("desde") LocalDate desde,
            @Param("hasta") LocalDate hasta
    );

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
            + "        OR m.concepto ILIKE CONCAT(:q, '%') " // ← Cambiado aquí
            + "        OR COALESCE(c.nombre, '')  ILIKE CONCAT(:q, '%') ) " // ← Y aquí
            + "ORDER BY m.fecha DESC, m.id DESC",
            nativeQuery = true)
    List<MovimientoProjection> findMovimientos(
            @Param("tesoreriaId") Long tesoreriaId,
            @Param("ini") LocalDate ini,
            @Param("fin") LocalDate fin,
            @Param("q") String q
    );

    @Query(value = "SELECT\n"
            + "    COALESCE(SUM(CASE WHEN tm.nombre = 'Ingreso' THEN m.cantidad ELSE 0 END), 0) AS totalIngresos,\n"
            + "    COALESCE(SUM(CASE WHEN tm.nombre = 'Egreso'  THEN m.cantidad ELSE 0 END), 0) AS totalEgresos\n"
            + "  FROM ad_samaria.movimiento m\n"
            + "  JOIN ad_samaria.tipo_movimiento tm ON tm.id = m.tipo_id\n"
            + "  WHERE m.tesoreria_id = :tesoreriaId\n"
            + "    AND m.fecha BETWEEN CAST(:ini AS date) AND CAST(:fin AS date)",
            nativeQuery = true)
    ResumenProjection resumenTesoreria(
            @Param("tesoreriaId") Long tesoreriaId,
            @Param("ini") LocalDate ini,
            @Param("fin") LocalDate fin
    );

    long countByTesoreriaId(Long tesoreriaId);

    @Query(" select m\n"
            + "      from Movimiento m\n"
            + "      where m.tesoreriaId = :tesId\n"
            + "        and m.fecha between :desde and :hasta\n"
            + "      order by m.fecha asc, m.id asc"
    )
    List<Movimiento> findByTesoreriaAndRango(
            @Param("tesId") Long tesoreriaId,
            @Param("desde") LocalDate desde,
            @Param("hasta") LocalDate hasta);

    @Query(value = "SELECT \n"
            + "            m.concepto AS concepto,\n"
            + "            tm.nombre AS tipo,\n"
            + "            c.nombre AS categoria,\n"
            + "            m.cantidad AS monto, \n"
            + "            m.fecha AS fecha\n"
            + "        FROM ad_samaria.movimiento m\n"
            + "        JOIN ad_samaria.tipo_movimiento tm ON tm.id = m.tipo_id\n"
            + "        JOIN ad_samaria.categoria c ON c.id = m.categoria_id\n"
            + "        WHERE m.tesoreria_id = :tesId\n"
            + "          AND m.fecha BETWEEN :desde AND :hasta\n and c.finanzas_generales "
            + "        ORDER BY m.fecha ASC, m.id ASC", nativeQuery = true)
    List<Object[]> findMovimientosConNombres(
            @Param("tesId") Long tesoreriaId,
            @Param("desde") LocalDate desde,
            @Param("hasta") LocalDate hasta);

    @Query(value
            = "SELECT "
            + "  t.nombre            AS tesoreria, "
            + "  m.concepto          AS concepto, "
            + "  tm.nombre           AS tipo, "
            + "  c.nombre            AS categoria, "
            + "  m.cantidad          AS cantidad, "
            + "  m.fecha             AS fecha "
            + "FROM ad_samaria.movimiento m "
            + "JOIN ad_samaria.tesoreria       t  ON t.id  = m.tesoreria_id "
            + "JOIN ad_samaria.tipo_movimiento tm ON tm.id = m.tipo_id "
            + "LEFT JOIN ad_samaria.categoria  c  ON c.id  = m.categoria_id "
            + "WHERE m.fecha BETWEEN :desde AND :hasta and c.finanzas_generales = true "
            + "ORDER BY m.fecha ASC, m.id ASC",
            nativeQuery = true)
    List<Object[]> findMovimientosConNombresTodas(
            @Param("desde") LocalDate desde,
            @Param("hasta") LocalDate hasta);

    @Query(value = "SELECT\n"
            + "  t.id     AS tesoreriaId,\n"
            + "  t.nombre AS tesoreria,\n"
            + "  c.id     AS categoriaId,\n"
            + "  c.nombre AS categoria,\n"
            + "  tm.nombre AS tipo,\n"
            + "  TO_CHAR(MAX(m.fecha), 'DD/MM/YYYY') AS fecha,\n"
            + "  COALESCE(SUM(m.cantidad),0) AS amount \n"
            + "FROM ad_samaria.movimiento m\n"
            + "JOIN ad_samaria.tesoreria       t  ON t.id  = m.tesoreria_id\n"
            + "JOIN ad_samaria.categoria       c  ON c.id  = m.categoria_id\n"
            + "JOIN ad_samaria.tipo_movimiento tm ON tm.id = m.tipo_id\n"
            + "WHERE c.finanzas_generales = true\n"
            + "  AND (CAST(:desde AS date) IS NULL OR m.fecha >= CAST(:desde AS date))\n"
            + "  AND (CAST(:hasta AS date) IS NULL OR m.fecha < CAST(:hasta AS date))\n"
            + "  AND (\n"
            + "       CAST(:q AS varchar) IS NULL\n"
            + "    OR LOWER(t.nombre) LIKE LOWER(CONCAT('%', CAST(:q AS varchar), '%'))\n"
            + "    OR LOWER(c.nombre) LIKE LOWER(CONCAT('%', CAST(:q AS varchar), '%'))\n"
            + "  )\n"
            + "GROUP BY t.id, t.nombre, c.id, c.nombre, tm.nombre, m.fecha\n"
            + "ORDER BY MAX(m.fecha) DESC, t.nombre, c.nombre",
            nativeQuery = true)
    List<MovimientoGeneralProjection> listarMovimientosGenerales(
            @Param("desde") LocalDate desde,
            @Param("hasta") LocalDate hasta,
            @Param("q") String q
    );

    @Query(value = "SELECT\n"
            + "    COALESCE(SUM(CASE WHEN tm.nombre = 'Ingreso' THEN m.cantidad ELSE 0 END), 0) AS ingresos,\n"
            + "    COALESCE(SUM(CASE WHEN tm.nombre = 'Egreso' THEN m.cantidad ELSE 0 END), 0) AS egresos\n"
            + "FROM ad_samaria.movimiento m\n"
            + "JOIN ad_samaria.categoria c ON c.id = m.categoria_id\n"
            + "JOIN ad_samaria.tipo_movimiento tm ON tm.id = m.tipo_id\n"
            + "WHERE c.finanzas_generales = true\n"
            + "  AND (CAST(:desde AS date) IS NULL OR m.fecha >= CAST(:desde AS date))\n"
            + "  AND (CAST(:hasta AS date) IS NULL OR m.fecha < CAST(:hasta AS date))\n"
            + "  AND (\n"
            + "    CAST(:q AS varchar) IS NULL OR\n"
            + "    EXISTS (\n"
            + "      SELECT 1\n"
            + "      FROM ad_samaria.tesoreria t\n"
            + "      WHERE t.id = m.tesoreria_id\n"
            + "        AND (LOWER(t.nombre) LIKE LOWER(CONCAT('%', CAST(:q AS varchar), '%')))\n"
            + "    )\n"
            + "    OR LOWER(c.nombre) LIKE LOWER(CONCAT('%', CAST(:q AS varchar), '%'))\n"
            + "  )", nativeQuery = true)
    TotalesGeneralesProjection totalesMovimientosGenerales(
            @Param("desde") LocalDate desde,
            @Param("hasta") LocalDate hasta,
            @Param("q") String q
    );
}
