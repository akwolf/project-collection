package com.childe.san.jms.learning;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangh
 * @createTime 2013-6-5 下午2:24:32
 */
public class TopicDemo {

	public static void main(String[] args) throws Exception {
		final Logger logger = LoggerFactory.getLogger(TopicDemo.class);

		Topic topic = new ActiveMQTopic("mp");

		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");

		Connection connection = connectionFactory.createConnection();
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		MessageConsumer consumer1 = session.createConsumer(topic);
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

		MessageConsumer consumer2 = session.createConsumer(topic);
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

		MessageProducer producer = session.createProducer(topic) ;
		for (int i = 0; i < 10; i++) {
			Message message = session.createTextMessage("JMS Topic " + i);
			producer.send((message));
			logger.debug("Message Send {}!!", i);
		}
	}
}
