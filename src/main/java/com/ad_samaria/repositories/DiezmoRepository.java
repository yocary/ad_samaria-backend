/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.repositories;

import com.ad_samaria.models.Diezmo;
import com.ad_samaria.models.TipoSimple;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Yocary
 */
public interface DiezmoRepository extends JpaRepository<Diezmo, Long> {

    @Query("SELECT d "
            + "FROM Diezmo d "
            + "LEFT JOIN d.persona p " // 👈 antes era JOIN
            + "WHERE d.fecha BETWEEN :desde AND :hasta "
            + "  AND ( :q IS NULL OR :q = '' OR "
            + "        LOWER(COALESCE(p.nombres, ''))          LIKE LOWER(CONCAT('%', :q, '%')) OR "
            + "        LOWER(COALESCE(p.apellidoPaterno, ''))  LIKE LOWER(CONCAT('%', :q, '%')) OR "
            + "        LOWER(COALESCE(p.apellidoMaterno, ''))  LIKE LOWER(CONCAT('%', :q, '%')) "
            + "      ) "
            + "ORDER BY d.fecha DESC, d.id DESC")
    List<Diezmo> buscarPorRangoYPersona(
            @Param("desde") LocalDate desde,
            @Param("hasta") LocalDate hasta,
            @Param("q") String q);

    @Query("SELECT COALESCE(SUM(d.cantidad), 0) "
            + "FROM Diezmo d "
            + "WHERE d.tipo = :tipo AND d.fecha BETWEEN :desde AND :hasta")
    BigDecimal totalPorTipoYFecha(
            @Param("tipo") TipoSimple tipo,
            @Param("desde") LocalDate desde,
            @Param("hasta") LocalDate hasta);
}
