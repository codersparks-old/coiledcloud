package com.github.codersparks.coiledcloud.services.rest2amqp.utils;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class RabbitMQUtils {

	private RabbitAdmin rabbitAdmin;
	
	private TopicExchange topicExchange;

	@Autowired
	public RabbitMQUtils(RabbitAdmin rabbitAdmin, TopicExchange topicExchange) {
		this.rabbitAdmin = rabbitAdmin;
		this.topicExchange = topicExchange;
	}

	public RabbitAdmin getRabbitAdmin() {
		return rabbitAdmin;
	}

	public void setRabbitAdmin(RabbitAdmin rabbitAdmin) {
		this.rabbitAdmin = rabbitAdmin;
	}

	public TopicExchange getTopicExchange() {
		return topicExchange;
	}

	public void setTopicExchange(TopicExchange topicExchange) {
		this.topicExchange = topicExchange;
	}
	
	public Queue getQueue(String name, boolean durable, boolean exclusive, boolean autoDelete) {
		return new Queue(name, durable, exclusive, autoDelete);
	}
	
	public Binding generateBinding(TopicExchange exchange, String routingKey, Queue queue) {
		return BindingBuilder.bind(queue).to(exchange).with(routingKey);
	}
	
	public RabbitTemplate getRabbitTemplate() {
		return rabbitAdmin.getRabbitTemplate();
	}
	

}
