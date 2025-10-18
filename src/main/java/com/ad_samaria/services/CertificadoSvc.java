/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.services;

import com.ad_samaria.commons.CommonSvc;
import com.ad_samaria.dto.CertificadoDTO;
import com.ad_samaria.models.Certificado;
import com.ad_samaria.services.impl.CertificadoSvcImpl;
import com.ad_samaria.services.impl.CertificadoSvcImpl.ResultadoPdf;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Yocary
 */
public interface CertificadoSvc extends CommonSvc<Certificado> {

    public byte[] generarMembresiaPdf(Map<String, Object> params);

    public Certificado crearCertificadoMembresia(String nombreMiembro, String fechaIso);

    public List<CertificadoDTO> listar(String q);

    public ResultadoPdf generarPdfPorId(Long id);

    void eliminar(Long id);

    byte[] generarBautismoPdf(Map<String, Object> params);

    Certificado crearCertificadoBautismo(String nombreMiembro, String fechaBautismoIso, String fechaExpedicionIso);

    public Certificado crearCertificadoNinos(
            String nombreMiembro,
            String nombrePadre,
            String nombreMadre,
            String lugarFechaNacimiento,
            String fechaExpedicionIso
    );

    public byte[] generarNinosPdf(Map<String, Object> params);

    public byte[] generarMatrimonioPdf(Map<String, Object> params);

    public Certificado crearCertificadoMatrimonio(String esposo, String esposa, String fechaExpedicionIso);
}
