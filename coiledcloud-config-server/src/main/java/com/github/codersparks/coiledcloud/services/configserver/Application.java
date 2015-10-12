package com.github.codersparks.coiledcloud.services.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;

/**
 * Created by codersparks on 11/10/2015.
 */
@Configuration
@EnableAutoConfiguration
@EnableEurekaClient
@EnableConfigServer
public class Application {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "configserver");
        SpringApplication.run(Application.class, args);
    }
}
