package com.github.codersparks.coiledcloud.services.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Created by codersparks on 11/10/2015.
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableConfigServer
public class Application {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "config-server");
        SpringApplication.run(Application.class, args);
    }
}
