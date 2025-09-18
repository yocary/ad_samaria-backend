/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ad_samaria.utils.security;

/**
 *
 * @author Yocary
 */
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode("admin123");
        System.out.println("Contraseña encriptada: " + hashedPassword);
    }
}
