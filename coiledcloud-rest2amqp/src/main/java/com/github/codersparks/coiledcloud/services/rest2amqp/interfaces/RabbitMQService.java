package com.github.codersparks.coiledcloud.services.rest2amqp.interfaces;


public interface RabbitMQService {

	public void createTopicQueue(String queueName, String routingKey) throws Exception;
	public void removeQueue(String queueName) throws Exception;
	public void publishMessage(String topic, String message) throws Exception;
	public String receiveData(String queueName) throws Exception;
}
