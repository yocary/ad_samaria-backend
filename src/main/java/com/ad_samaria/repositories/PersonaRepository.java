/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.repositories;

import com.ad_samaria.models.Persona;
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

    Optional<Persona> findByDpi(String dpi);

    @Query(value
            = "SELECT p.id, "
            + "       TRIM(p.nombres || ' ' || p.apellido_paterno || ' ' || COALESCE(p.apellido_materno,'')) AS nombre "
            + "FROM ad_samaria.persona p "
            + "WHERE "
            + "  LOWER(TRIM(p.nombres || ' ' || p.apellido_paterno || ' ' || COALESCE(p.apellido_materno,''))) LIKE LOWER(CONCAT('%', :q, '%')) "
            + "  OR LOWER(COALESCE(p.dpi, '')) LIKE LOWER(CONCAT('%', :q, '%')) "
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

}
