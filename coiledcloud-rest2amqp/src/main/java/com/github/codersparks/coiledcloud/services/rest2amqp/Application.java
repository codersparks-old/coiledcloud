package com.github.codersparks.coiledcloud.services.rest2amqp;

import com.github.codersparks.coiledcloud.services.rest2amqp.interfaces.RabbitMQService;
import com.github.codersparks.coiledcloud.services.rest2amqp.service.RabbitMQServiceImpl;
import com.github.codersparks.coiledcloud.services.rest2amqp.utils.RabbitMQUtils;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

/**
 * Created by codersparks on 25/10/2015.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Application {

    @Autowired
    private ConnectionFactory connectionFactory;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("amq.topic");
    }

    @Bean
    public RabbitMQUtils rabbitUtils() {
        return new RabbitMQUtils(rabbitAdmin(), topicExchange());
    }

    @Bean
    public RabbitMQService rabbitMQService() {
        return new RabbitMQServiceImpl(rabbitUtils());
    }
}
