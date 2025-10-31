/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Yocary
 */
public final class NumeroALetrasEs {

    private static final String[] UNIDADES = {
        "cero", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve",
        "diez", "once", "doce", "trece", "catorce", "quince", "dieciséis", "diecisiete", "dieciocho", "diecinueve"
    };
    private static final String[] DECENAS = {
        "", "", "veinte", "treinta", "cuarenta", "cincuenta", "sesenta", "setenta", "ochenta", "noventa"
    };
    private static final String[] CENTENAS = {
        "", "ciento", "doscientos", "trescientos", "cuatrocientos", "quinientos",
        "seiscientos", "setecientos", "ochocientos", "novecientos"
    };

    private NumeroALetrasEs() {
    }

    public static String montoEnQuetzales(BigDecimal monto) {
        if (monto == null) {
            return "";
        }
        BigDecimal v = monto.setScale(2, RoundingMode.HALF_UP);
        long enteros = v.longValue();
        int centavos = v.remainder(BigDecimal.ONE).movePointRight(2).abs().intValue();

        String cuerpo = numeroALetras(enteros).toUpperCase();
        String sufijoQ = (enteros == 1) ? " QUETZAL" : " QUETZALES";
        String sufijoC = String.format(" CON %02d/100", centavos);

        return cuerpo + sufijoQ + sufijoC;
    }

    private static String numeroALetras(long n) {
        if (n < 0) {
            return "menos " + numeroALetras(-n);
        }
        if (n < 20) {
            return UNIDADES[(int) n];
        }
        if (n < 30) {
            return n == 20 ? "veinte" : "veinti" + numeroALetras(n - 20);
        }
        if (n < 100) {
            long d = n / 10, r = n % 10;
            return r == 0 ? DECENAS[(int) d] : DECENAS[(int) d] + " y " + numeroALetras(r);
        }
        if (n == 100) {
            return "cien";
        }
        if (n < 1000) {
            long c = n / 100, r = n % 100;
            return (CENTENAS[(int) c] + (r == 0 ? "" : " " + numeroALetras(r))).trim();
        }
        if (n < 2000) {
            return "mil" + (n % 1000 == 0 ? "" : " " + numeroALetras(n % 1000));
        }
        if (n < 1000000) {
            long miles = n / 1000, r = n % 1000;
            String pref = numeroALetras(miles) + " mil";
            return pref + (r == 0 ? "" : " " + numeroALetras(r));
        }
        if (n < 2000000) {
            long r = n % 1000000;
            return "un millón" + (r == 0 ? "" : " " + numeroALetras(r));
        }
        if (n < 1000000000000L) {
            long mill = n / 1000000, r = n % 1000000;
            String pref = numeroALetras(mill) + " millones";
            return pref + (r == 0 ? "" : " " + numeroALetras(r));
        }
        // Para cifras mayores, puedes extender si lo necesitas
        return Long.toString(n);
    }
}
