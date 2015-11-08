package com.github.codersparks.coiledcloud.services.rest2amqp.web;

import java.util.Optional;

import com.github.codersparks.coiledcloud.services.rest2amqp.interfaces.RabbitMQService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMQController {

	private static final Logger logger = LoggerFactory.getLogger(RabbitMQController.class);

	private TopicExchange exchange;

	private RabbitAdmin rabbitAdmin;


	private RabbitMQService rabbitMQService;

	@Autowired
	public RabbitMQController(RabbitAdmin rabbitAdmin, TopicExchange exchange, RabbitMQService rabbitMQService) {
		this.exchange = exchange;
		this.rabbitAdmin = rabbitAdmin;
		this.rabbitMQService = rabbitMQService;
	}

	@RequestMapping(value = "/queue/{name}/{routingKey}", method = RequestMethod.PUT)
	public ResponseEntity<Object> createQueue(@PathVariable String name, @PathVariable Optional<String> routingKey)
			throws Exception {
		logger.info("Create queue called, name: " + name);

		String rKey;
		if (routingKey.isPresent()) {
			rKey = routingKey.get();
			logger.debug("Found routing key in path: " + rKey);
		} else {
			logger.debug("No routing key found in path, setting to null");
			rKey = null;
		}
		rabbitMQService.createTopicQueue(name, rKey);

		return new ResponseEntity<Object>(HttpStatus.OK);

	}

	@RequestMapping(value = "/queue/{name}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteQueue(@PathVariable String name) throws Exception {

		logger.info("Delete queue called with name: " + name);

		rabbitMQService.removeQueue(name);

		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@RequestMapping(
			value = "/queue/{topic}", 
			consumes = { 
					MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE, 
					MediaType.TEXT_XML_VALUE 
			}, 
			method = RequestMethod.POST
		)
	public ResponseEntity<Object> publishMessage(@PathVariable String topic, @RequestBody String data)
			throws Exception {

		logger.info("Publishing message with topic: " + topic + " and data: " + data);

		rabbitMQService.publishMessage(topic, data);

		return new ResponseEntity<Object>(HttpStatus.OK);

	}

	

	@RequestMapping(value = "/queue/{name}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE

	}, method = RequestMethod.GET)
	public ResponseEntity<String> getItemFromQueue(@PathVariable String name)
			throws Exception {

		ResponseEntity<String> response;

		logger.info("Attempting to receive item from queue");

		String data = rabbitMQService.receiveData(name);

		response = new ResponseEntity<String>(data, HttpStatus.OK);
		return response;
	}
}
