package com.ad_samaria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@EnableScheduling
@ComponentScan("com.ad_samaria")
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Guatemala"));
        logger.info("La zona horaria por defecto se ha establecido a: America/Guatemala");
        imprimirHoraActual();
    }

    private void imprimirHoraActual() {
        long horaActual = System.currentTimeMillis();
        logger.info("La hora actual en Guatemala (en milisegundos) es: " + horaActual);
    }
}
