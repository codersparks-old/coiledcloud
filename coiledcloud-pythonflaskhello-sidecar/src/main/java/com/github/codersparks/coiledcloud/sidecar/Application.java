package com.github.codersparks.coiledcloud.sidecar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.sidecar.EnableSidecar;

/**
 * Created by codersparks on 25/10/2015.
 */
@SpringBootApplication
@EnableSidecar
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
