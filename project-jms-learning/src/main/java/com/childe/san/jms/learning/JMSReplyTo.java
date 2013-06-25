package com.childe.san.jms.learning;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
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
 * @createTime 2013-6-5 下午5:43:14
 */
public class JMSReplyTo {

	public static void main(String[] args) throws Exception {
		final Logger logger = LoggerFactory.getLogger(JMSReplyTo.class);

		Queue sendQueue = new ActiveMQQueue("sq");
		Queue replyQueue = new ActiveMQQueue("rq");

		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");

		Connection connection = connectionFactory.createConnection();
		connection.start();

		final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Message sendMessage = session.createTextMessage("andy");
		sendMessage.setJMSReplyTo(replyQueue);

		MessageProducer sendProducer = session.createProducer(sendQueue);
		sendProducer.send(sendMessage);
		logger.debug("send Queue send !! ");

		// 中间接受者
		MessageConsumer sendConsumer = session.createConsumer(sendQueue);
		sendConsumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message msg) {
				try {
					Destination replyQueue = msg.getJMSReplyTo();
					MessageProducer producer = session.createProducer(replyQueue);
					String text = ((TextMessage) msg).getText();
					logger.debug("recevice message : " + text);
					text = "hello " + text;
					producer.send(session.createTextMessage(text));
					logger.debug("send Queue to replyQueue : " + text);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});

		// 最终消费者
		MessageConsumer replyConsumer = session.createConsumer(replyQueue);
		replyConsumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message msg) {
				try {
					String text = ((TextMessage) msg).getText();
					logger.debug("recevice message from send queue : " + text);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
