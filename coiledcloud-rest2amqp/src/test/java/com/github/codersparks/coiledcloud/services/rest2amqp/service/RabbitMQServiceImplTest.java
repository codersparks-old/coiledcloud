package com.github.codersparks.coiledcloud.services.rest2amqp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Properties;

import com.github.codersparks.coiledcloud.services.rest2amqp.exception.NoMessageException;
import com.github.codersparks.coiledcloud.services.rest2amqp.exception.QueueAlreadyExistsException;
import com.github.codersparks.coiledcloud.services.rest2amqp.exception.QueueNameException;
import com.github.codersparks.coiledcloud.services.rest2amqp.exception.QueueNotFoundException;
import com.github.codersparks.coiledcloud.services.rest2amqp.exception.TopicValueException;
import com.github.codersparks.coiledcloud.services.rest2amqp.exception.UnknowMessageTypeException;
import com.github.codersparks.coiledcloud.services.rest2amqp.utils.RabbitMQUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InOrder;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class RabbitMQServiceImplTest {

	private RabbitMQServiceImpl impl;
	private RabbitMQUtils rabbitUtils;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

		rabbitUtils = mock(RabbitMQUtils.class);


		impl = new RabbitMQServiceImpl(rabbitUtils);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void createTopicQueueDeclaresQueueAndBindingRoutingKeyDefined()
			throws QueueNameException, QueueAlreadyExistsException {
		String queueName = "queuename";
		String routingKey = "test.key";

		// We need a mock queue to return for getQueue method
		Queue queue = mock(Queue.class);
		when(queue.getName()).thenReturn(queueName);
		when(rabbitUtils.getQueue(any(String.class), any(boolean.class), any(boolean.class), any(boolean.class)))
				.thenReturn(queue);
		// We need a mock exchange to return getExchange
		TopicExchange exchange = mock(TopicExchange.class);
		when(rabbitUtils.getTopicExchange()).thenReturn(exchange);
		// We need bindig for getBinding
		Binding binding = mock(Binding.class);
		when(rabbitUtils.generateBinding(exchange, routingKey, queue)).thenReturn(binding);

		// Rabbit admin to be returned from RabbitUtils
		RabbitAdmin rabbitAdmin = mock(RabbitAdmin.class);
		when(rabbitAdmin.getQueueProperties(queueName)).thenReturn(null);
		when(rabbitUtils.getRabbitAdmin()).thenReturn(rabbitAdmin);
		when(rabbitAdmin.getQueueProperties(queueName)).thenReturn(null);

		impl.createTopicQueue(queueName, routingKey);

		verify(rabbitUtils, times(1)).getRabbitAdmin();
		verify(rabbitAdmin, times(1)).getQueueProperties(queueName);
		verify(rabbitUtils, times(1)).getQueue(queueName, true, false, false);
		verify(rabbitUtils, times(1)).generateBinding(exchange, routingKey, queue);
		verify(rabbitUtils, times(1)).getTopicExchange();
		verify(rabbitAdmin, times(1)).declareQueue(queue);
		verify(rabbitAdmin, times(1)).declareBinding(binding);
		verifyNoMoreInteractions(rabbitUtils);
		verifyNoMoreInteractions(queue);
		verifyNoMoreInteractions(exchange);
		verifyNoMoreInteractions(binding);

	}

	@Test
	public void createTopicQueueDeclaresQueueAndBindingRoutingKeyUnDefined()
			throws QueueNameException, QueueAlreadyExistsException {
		String queueName = "queuename";
		String routingKey = null;

		// We need a mock queue to return for getQueue method
		Queue queue = mock(Queue.class);
		when(queue.getName()).thenReturn(queueName);
		when(rabbitUtils.getQueue(any(String.class), any(boolean.class), any(boolean.class), any(boolean.class)))
				.thenReturn(queue);
		// We need a mock exchange to return getExchange
		TopicExchange exchange = mock(TopicExchange.class);
		when(rabbitUtils.getTopicExchange()).thenReturn(exchange);
		// We need bindig for getBinding
		Binding binding = mock(Binding.class);
		when(rabbitUtils.generateBinding(exchange, "#", queue)).thenReturn(binding);

		// Rabbit admin to be returned from RabbitUtils
		RabbitAdmin rabbitAdmin = mock(RabbitAdmin.class);
		when(rabbitAdmin.getQueueProperties(queueName)).thenReturn(null);
		when(rabbitUtils.getRabbitAdmin()).thenReturn(rabbitAdmin);
		when(rabbitAdmin.getQueueProperties(queueName)).thenReturn(null);

		impl.createTopicQueue(queueName, routingKey);

		verify(rabbitUtils, times(1)).getRabbitAdmin();
		verify(rabbitAdmin, times(1)).getQueueProperties(queueName);
		verify(rabbitUtils, times(1)).getQueue(queueName, true, false, false);
		verify(rabbitUtils, times(1)).generateBinding(exchange, "#", queue);
		verify(rabbitUtils, times(1)).getTopicExchange();
		verify(rabbitAdmin, times(1)).declareQueue(queue);
		verify(rabbitAdmin, times(1)).declareBinding(binding);
		verifyNoMoreInteractions(rabbitUtils);
		verifyNoMoreInteractions(queue);
		verifyNoMoreInteractions(exchange);
		verifyNoMoreInteractions(binding);

	}

	@Test
	public void createTopicQueueDeclaresQueueAndBindingRoutingKeyEmpty()
			throws QueueNameException, QueueAlreadyExistsException {
		String queueName = "queuename";
		String routingKey = "";

		// We need a mock queue to return for getQueue method
		Queue queue = mock(Queue.class);
		when(queue.getName()).thenReturn(queueName);
		when(rabbitUtils.getQueue(any(String.class), any(boolean.class), any(boolean.class), any(boolean.class)))
				.thenReturn(queue);
		// We need a mock exchange to return getExchange
		TopicExchange exchange = mock(TopicExchange.class);
		when(rabbitUtils.getTopicExchange()).thenReturn(exchange);
		// We need bindig for getBinding
		Binding binding = mock(Binding.class);
		when(rabbitUtils.generateBinding(exchange, "#", queue)).thenReturn(binding);

		// Rabbit admin to be returned from RabbitUtils
		RabbitAdmin rabbitAdmin = mock(RabbitAdmin.class);
		when(rabbitAdmin.getQueueProperties(queueName)).thenReturn(null);
		when(rabbitUtils.getRabbitAdmin()).thenReturn(rabbitAdmin);

		impl.createTopicQueue(queueName, routingKey);

		verify(rabbitUtils, times(1)).getRabbitAdmin();
		verify(rabbitAdmin, times(1)).getQueueProperties(queueName);
		verify(rabbitUtils, times(1)).getQueue(queueName, true, false, false);
		verify(rabbitUtils, times(1)).generateBinding(exchange, "#", queue);
		verify(rabbitUtils, times(1)).getTopicExchange();
		verify(rabbitAdmin, times(1)).declareQueue(queue);
		verify(rabbitAdmin, times(1)).declareBinding(binding);
		verifyNoMoreInteractions(rabbitUtils);
		verifyNoMoreInteractions(queue);
		verifyNoMoreInteractions(exchange);
		verifyNoMoreInteractions(binding);

	}

	@Test(expected = QueueNameException.class)
	public void throwExceptionWhenQueueNameTooShort() throws QueueNameException, QueueAlreadyExistsException {
		String queueName = "ab";

		impl.createTopicQueue(queueName, "#");
		verifyNoMoreInteractions(rabbitUtils);
	}

	@Test(expected = QueueNameException.class)
	public void throwExceptionWhenQueueNameNull() throws QueueNameException, QueueAlreadyExistsException {
		String queueName = null;

		impl.createTopicQueue(queueName, "#");
		verifyNoMoreInteractions(rabbitUtils);
	}

	@Test(expected = QueueAlreadyExistsException.class)
	public void throwExceptionWhenQueueAlreadyExist() throws QueueNameException, QueueAlreadyExistsException {
		String queueName = "testQueue";
		String routingKey = "test.key";
		// Rabbit admin to be returned from RabbitUtils
		RabbitAdmin rabbitAdmin = mock(RabbitAdmin.class);
		Properties properties = new Properties();
		when(rabbitAdmin.getQueueProperties(queueName)).thenReturn(properties);
		when(rabbitUtils.getRabbitAdmin()).thenReturn(rabbitAdmin);

		impl.createTopicQueue(queueName, routingKey);
		verify(rabbitUtils, times(1)).getRabbitAdmin();
		verify(rabbitAdmin, times(1)).getQueueProperties(queueName);

		verifyNoMoreInteractions(rabbitUtils, rabbitAdmin);

	}

	@Test
	public void removeExistingQueue() throws QueueNotFoundException {
		String queueName = "testQueue";

		RabbitAdmin rabbitAdmin = mock(RabbitAdmin.class);
		Properties properties = new Properties();
		when(rabbitAdmin.getQueueProperties(queueName)).thenReturn(properties);
		when(rabbitUtils.getRabbitAdmin()).thenReturn(rabbitAdmin);

		impl.removeQueue(queueName);

		verify(rabbitUtils, times(1)).getRabbitAdmin();
		verify(rabbitAdmin, times(1)).getQueueProperties(queueName);
		verify(rabbitAdmin, times(1)).deleteQueue(queueName);

		verifyNoMoreInteractions(rabbitUtils, rabbitAdmin);
	}

	@Test(expected = QueueNotFoundException.class)
	public void removeExistingQueueThrowsExceptionWithNoQueue() throws QueueNotFoundException {
		String queueName = "testQueue";

		RabbitAdmin rabbitAdmin = mock(RabbitAdmin.class);
		Properties properties = null;
		when(rabbitAdmin.getQueueProperties(queueName)).thenReturn(properties);
		when(rabbitUtils.getRabbitAdmin()).thenReturn(rabbitAdmin);

		impl.removeQueue(queueName);

		verify(rabbitUtils, times(1)).getRabbitAdmin();
		verify(rabbitAdmin, times(1)).getQueueProperties(queueName);

		verifyNoMoreInteractions(rabbitUtils, rabbitAdmin);

	}

	@Test
	public void publishMessageOK() throws Exception {
		String topic = "test.route";
		String exchangeName = "amq.direct";
		TopicExchange exchange = mock(TopicExchange.class);
		when(exchange.getName()).thenReturn(exchangeName);

		String data = "<data>test</data>";
		when(rabbitUtils.getTopicExchange()).thenReturn(exchange);

		RabbitTemplate template = mock(RabbitTemplate.class);
		when(rabbitUtils.getRabbitTemplate()).thenReturn(template);

		impl.publishMessage(topic, data);

		verify(rabbitUtils, times(1)).getTopicExchange();
		verify(exchange, times(2)).getName();
		verify(rabbitUtils, times(1)).getRabbitTemplate();
		verify(template, times(1)).convertAndSend(exchangeName, topic, data);

	}

	@Test(expected = TopicValueException.class)
	public void verifyExceptionThrownWithNullTopicWhenPublishing() throws Exception {

		String topic = null;
		String data = "testString";

		impl.publishMessage(topic, data);

		verifyNoMoreInteractions(rabbitUtils);

	}

	@Test(expected = TopicValueException.class)
	public void verifyExceptionThrownWithEmptyTopicWhenPublishing() throws Exception {

		String topic = "";
		String  data = "dataString";

		impl.publishMessage(topic, data);

		verifyNoMoreInteractions(rabbitUtils);

	}

	@Test
	public void verifyReceiveDataSunnyTest() throws Exception {

		String queueName = "queuename";

		String data = "testString";

		RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);
		when(rabbitTemplate.receiveAndConvert()).thenReturn(data);

		Properties properties = mock(Properties.class);
		when(properties.get("QUEUE_MESSAGE_COUNT")).thenReturn(1);

		RabbitAdmin rabbitAdmin = mock(RabbitAdmin.class);
		when(rabbitAdmin.getQueueProperties(queueName)).thenReturn(properties);
		when(rabbitAdmin.getRabbitTemplate()).thenReturn(rabbitTemplate);

		when(rabbitUtils.getRabbitAdmin()).thenReturn(rabbitAdmin);

		// Actually call the method :)
		String actualData = impl.receiveData(queueName);

		InOrder inOrder = inOrder(rabbitUtils, rabbitAdmin, properties, rabbitTemplate);
		inOrder.verify(rabbitUtils).getRabbitAdmin();
		inOrder.verify(rabbitAdmin).getQueueProperties(queueName);
		inOrder.verify(properties).get("QUEUE_MESSAGE_COUNT");
		inOrder.verify(rabbitAdmin).getRabbitTemplate();
		inOrder.verify(rabbitTemplate).setQueue(queueName);
		inOrder.verify(rabbitTemplate).receiveAndConvert();

		assertEquals("Data returned not as expected", data, actualData);
		verifyNoMoreInteractions(rabbitUtils, rabbitAdmin, properties, rabbitTemplate);

	}

	@Test(expected = QueueNotFoundException.class)
	public void testReceiveDataNoQueue() throws Exception {

		String queueName = "queueName";

		RabbitAdmin rabbitAdmin = mock(RabbitAdmin.class);
		when(rabbitAdmin.getQueueProperties(queueName)).thenReturn(null);

		when(rabbitUtils.getRabbitAdmin()).thenReturn(rabbitAdmin);
		try {
			impl.receiveData(queueName);
			fail("Exception should have been thrown");
		} catch (Exception e) {

			verify(rabbitUtils, times(1)).getRabbitAdmin();
			verify(rabbitAdmin, times(1)).getQueueProperties(queueName);
			throw e;
		}

	}

	@Test(expected = NoMessageException.class)
	public void testReceiveDataNoMessage() throws Exception {
		String queueName = "queuename";
		String messageContent = "testing";

		Properties properties = mock(Properties.class);
		when(properties.get("QUEUE_MESSAGE_COUNT")).thenReturn(0);

		RabbitAdmin rabbitAdmin = mock(RabbitAdmin.class);
		when(rabbitAdmin.getQueueProperties(queueName)).thenReturn(properties);

		when(rabbitUtils.getRabbitAdmin()).thenReturn(rabbitAdmin);

		// Actually call the method :)
		try {
			String actualData = impl.receiveData(queueName);
			fail("Exception should have been thrown");
		} catch (Exception e) {
			InOrder inOrder = inOrder(rabbitUtils, rabbitAdmin, properties);
			inOrder.verify(rabbitUtils).getRabbitAdmin();
			inOrder.verify(rabbitAdmin).getQueueProperties(queueName);
			inOrder.verify(properties).get("QUEUE_MESSAGE_COUNT");
			throw e;
		}

	}

	@Test(expected = UnknowMessageTypeException.class)
	public void testReceiveDataUnknownMessageType() throws Exception {
		String queueName = "queuename";
		Object messageContent = 121312;

		String data = "testString";

		
		RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);
		when(rabbitTemplate.receiveAndConvert()).thenReturn(messageContent);

		Properties properties = mock(Properties.class);
		when(properties.get("QUEUE_MESSAGE_COUNT")).thenReturn(1);

		RabbitAdmin rabbitAdmin = mock(RabbitAdmin.class);
		when(rabbitAdmin.getQueueProperties(queueName)).thenReturn(properties);
		when(rabbitAdmin.getRabbitTemplate()).thenReturn(rabbitTemplate);

		when(rabbitUtils.getRabbitAdmin()).thenReturn(rabbitAdmin);

		// Actually call the method :)
		try {
			String actualData = impl.receiveData(queueName);
		} catch (Exception e) {

			InOrder inOrder = inOrder(rabbitUtils, rabbitAdmin, properties, rabbitTemplate);
			inOrder.verify(rabbitUtils).getRabbitAdmin();
			inOrder.verify(rabbitAdmin).getQueueProperties(queueName);
			inOrder.verify(properties).get("QUEUE_MESSAGE_COUNT");
			inOrder.verify(rabbitAdmin).getRabbitTemplate();
			inOrder.verify(rabbitTemplate).setQueue(queueName);
			inOrder.verify(rabbitTemplate).receiveAndConvert();

			verifyNoMoreInteractions(rabbitUtils, rabbitAdmin, properties, rabbitTemplate);

			throw e;
		}

	}
}
