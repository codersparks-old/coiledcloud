package com.github.codersparks.coiledcloud.services.rest2amqp.service;

import java.util.Properties;


import com.github.codersparks.coiledcloud.services.rest2amqp.exception.NoMessageException;
import com.github.codersparks.coiledcloud.services.rest2amqp.exception.QueueAlreadyExistsException;
import com.github.codersparks.coiledcloud.services.rest2amqp.exception.QueueNameException;
import com.github.codersparks.coiledcloud.services.rest2amqp.exception.QueueNotFoundException;
import com.github.codersparks.coiledcloud.services.rest2amqp.exception.TopicValueException;
import com.github.codersparks.coiledcloud.services.rest2amqp.exception.UnknowMessageTypeException;
import com.github.codersparks.coiledcloud.services.rest2amqp.interfaces.RabbitMQService;
import com.github.codersparks.coiledcloud.services.rest2amqp.utils.RabbitMQUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class RabbitMQServiceImpl implements RabbitMQService {

	private static Logger logger = LoggerFactory.getLogger(RabbitMQServiceImpl.class);
	
	private RabbitMQUtils rabbitUtils;

	

	@Autowired
	public RabbitMQServiceImpl(RabbitMQUtils rabbitUtils) {
		this.rabbitUtils = rabbitUtils;
	}

	@Override
	public void createTopicQueue(String name, String routingKey)
			throws QueueNameException, QueueAlreadyExistsException {
		if (name == null || name.length() < 3) {
			String error = "Queue name is null or less than 3 characters, name: " + name;
			logger.error(error);
			throw new QueueNameException(error);
		}
		
		RabbitAdmin rabbitAdmin = rabbitUtils.getRabbitAdmin();

		Properties properties = rabbitAdmin.getQueueProperties(name);

		if (properties != null) {
			String error = "Queue with name: " + name + " already exists";
			logger.error(error);
			throw new QueueAlreadyExistsException(error);
		}

		Queue queue = rabbitUtils.getQueue(name, true, false, false);

		if (routingKey == null || routingKey.length() < 1) {
			logger.info("Topic supplied (" + routingKey + ") is null or empty, using # (i.e. all) as routing key");
			routingKey = "#";
		}
		Binding binding = rabbitUtils.generateBinding(rabbitUtils.getTopicExchange(), routingKey, queue);
		rabbitAdmin.declareQueue(queue);
		rabbitAdmin.declareBinding(binding);
	}

	

	@Override
	public void removeQueue(String queueName) throws QueueNotFoundException  {
		
		RabbitAdmin rabbitAdmin = rabbitUtils.getRabbitAdmin();
		Properties properties = rabbitAdmin.getQueueProperties(queueName);

		if (properties == null) {
			String error = "Queue with name: " + queueName + " does not exist";
			logger.error(error);
			throw new QueueNotFoundException(error);
		}

		rabbitAdmin.deleteQueue(queueName);

	}
	
	@Override
	public void publishMessage(String topic, String message) throws Exception{
		if (topic == null || topic.length() < 1) {
			String error = "Topic is null or empty, topic: " + topic;
			logger.error(error);
			throw new TopicValueException(error);
		}

		TopicExchange exchange = rabbitUtils.getTopicExchange();
		
		logger.info("Sending data to exchange: " + exchange.getName() + " with data: " + message);

		rabbitUtils.getRabbitTemplate().convertAndSend(exchange.getName(), topic, message);
	}

	@Override
	public String receiveData(String queueName) throws Exception {
		
		RabbitAdmin rabbitAdmin = rabbitUtils.getRabbitAdmin();
		
		Properties properties = rabbitAdmin.getQueueProperties(queueName);
		if (properties == null) {
			String error = "Cannot find queue with name: " + queueName;
			logger.error(error);
			throw new QueueNotFoundException(error);
		}

		int messageCount = Integer.parseInt(properties.get("QUEUE_MESSAGE_COUNT").toString());

		if (messageCount > 0) {
			RabbitTemplate rabbitTemplate = rabbitAdmin.getRabbitTemplate();
			rabbitTemplate.setQueue(queueName);
			Object message = rabbitTemplate.receiveAndConvert();

			if (message instanceof String) {
				logger.debug("Message is of type string");
				String dataString = (String) message;

				logger.debug("Value of string: " + dataString);


				return dataString;
			} else {
				throw new UnknowMessageTypeException("Message is of unknown type: " + message.getClass());
			}
		} else {
			logger.warn("No messages to receive, throwing NoMessageException");
			throw new NoMessageException("No messages to receive");
		}
	}
}
