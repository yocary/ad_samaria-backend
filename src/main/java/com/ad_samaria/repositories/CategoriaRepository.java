/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.repositories;

import com.ad_samaria.models.Categoria;
import com.ad_samaria.projections.CategoriaMini;
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
public interface CategoriaRepository extends CrudRepository<Categoria, Object> {

    @Query(value
            = "SELECT c.id AS id, c.nombre AS nombre "
            + "FROM ad_samaria.categoria c "
            + "WHERE (c.aplica_a = :tipoId) "
            + "ORDER BY c.nombre ASC",
            nativeQuery = true)
    List<CategoriaMini> listarPorTipo(@Param("tipoId") Long tipoId);
}
