package com.childe.san.jms.learning;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangh
 * @createTime 2013-6-5 下午2:57:00
 */
public class MessageHeaderDemo {

	public static void main(String[] args) throws Exception {
		final Logger logger = LoggerFactory.getLogger(MessageHeaderDemo.class);

		Queue queue = new ActiveMQQueue("mq");

		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");

		Connection connection = connectionFactory.createConnection();
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Message message = session.createTextMessage("JMS Message Header");

		MessageProducer messageProducer = session.createProducer(queue);
		messageProducer.send(message);

		MessageConsumer consumer = session.createConsumer(queue);
		Message textMessage = consumer.receive();

		// 通常用来关联多个Message。例如需要回复一个消息，可以把JMSCorrelationID设置为所收到的消息的JMSMessageID。
		logger.debug("JMSCorrelationID {}", textMessage.getJMSCorrelationID());
		//消息的发送模式：persistent或nonpersistent。前者表示消息在被消费之前，如果JMS提供者DOWN了，重新启动后消息仍然存在。后者在这种情况下表示消息会被丢失。可以通过下面的方式设置：
		//   Producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);[PERSISTENT 2,NON_PERSISTENT 1]
		logger.debug("getJMSDeliveryMode {}", textMessage.getJMSDeliveryMode());
		// 消息的目的地，Topic或者是Queue。
		logger.debug("getJMSDestination {}", textMessage.getJMSDestination());
		//表示一个消息的有效期。只有在这个有效期内，消息消费者才可以消费这个消息。默认值为0，表示消息永不过期。可以通过下面的方式设置：
		//	       producer.setTimeToLive(3600000); 
		logger.debug("getJMSExpiration {}", textMessage.getJMSExpiration());
		// 一个字符串用来唯一标示一个消息。
		logger.debug("getJMSMessageID {}", textMessage.getJMSMessageID());
		//消息的优先级。0-4为正常的优先级，5-9为高优先级。可以通过下面方式设置：
		//	       producer.setPriority(9);
		logger.debug("getJMSPriority {}", textMessage.getJMSPriority());
		//如果这个值为true，表示消息是被重新发送了。因为有时消费者没有确认他已经收到消息或者JMS提供者不确定消费者是否已经收到。
		logger.debug("getJMSRedelivered {}", textMessage.getJMSRedelivered());
		//有时消息生产者希望消费者回复一个消息，JMSReplyTo为一个Destination，表示需要回复的目的地。当然消费者可以不理会它。
		logger.debug("getJMSReplyTo {}", textMessage.getJMSReplyTo());
		//当调用send()方法的时候，JMSTimestamp会被自动设置为当前时间。
		logger.debug("getJMSTimestamp {}", textMessage.getJMSTimestamp());
		//表示消息体的结构，和JMS提供者有关。
		logger.debug("getJMSType {}", textMessage.getJMSType());

	}
}
