/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services.impl;

import com.ad_samaria.commons.CommonSvcImpl;
import com.ad_samaria.models.Categoria;
import com.ad_samaria.projections.CategoriaMini;
import com.ad_samaria.repositories.CategoriaRepository;
import com.ad_samaria.services.CategoriaSvc;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author Yocary
 */
@Service
public class CategoriaSvcImpl extends CommonSvcImpl<Categoria, CategoriaRepository> implements CategoriaSvc {

    @Autowired
    private JdbcTemplate jdbc;

    private Long resolveTipoId(String tipo, Long tipoId) {
        if (tipoId != null) {
            return tipoId;
        }
        String q = (tipo == null ? "" : tipo.trim().toLowerCase());
        String like = q.startsWith("ingreso") ? "ingreso%" : "egreso%";
        return jdbc.queryForObject(
                "SELECT id FROM ad_samaria.tipo_movimiento WHERE lower(nombre) LIKE ? LIMIT 1",
                new Object[]{like}, Long.class
        );
    }

    @Override
    public List<CategoriaMini> listarPorTipo(String tipo, Long tipoId) {
        Long id = resolveTipoId(tipo, tipoId);
        return repository.listarPorTipo(id);
    }

}
