package com.childe.san.jms.learning;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangh
 * @createTime 2013-6-5 下午2:00:53
 */
public class QueueDemo {
	public static void main(String[] args) throws Exception {
		final Logger logger = LoggerFactory.getLogger(QueueDemo.class);

		Queue queue = new ActiveMQQueue("mq");

		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");

		Connection connection = connectionFactory.createConnection();
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		MessageConsumer consumer1 = session.createConsumer(queue);
		consumer1.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message msg) {
				try {
					logger.debug("consumer1 recevice message : " + ((TextMessage) msg).getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});

		MessageConsumer consumer2 = session.createConsumer(queue);
		consumer2.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message msg) {
				try {
					logger.debug("consumer2 recevice message : " + ((TextMessage) msg).getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});

		MessageProducer producer = session.createProducer(queue);
		for (int i = 0; i < 10; i++) {
			Message message = session.createTextMessage("JMS Queue " + i);
			producer.send((message));
			logger.debug("Message Send {}!!", i);
		}

	}

}
