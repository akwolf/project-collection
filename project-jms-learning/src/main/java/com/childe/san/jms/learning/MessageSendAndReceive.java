package com.childe.san.jms.learning;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 一个JMS的创建和使用demo
 * 服务提供：ActiveMQ
 * ConnectionFactory-->Connection-->Session-->Message
 * 
 * @author zhangh
 * @createTime 2013-6-5 上午11:44:42
 */

public class MessageSendAndReceive {

	public static void main(String[] args) throws Exception {
		Logger logger = LoggerFactory.getLogger(MessageSendAndReceive.class);
		// 1、创建ConnectionFactory
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
		// 2、创建Connection
		Connection connection = connectionFactory.createConnection();
		// 启动connection
		connection.start();

		// 3、创建消息队列mq，目标存放地址
		Queue queue = new ActiveMQQueue("mq");

		// 4、创建session,false表示非事务管理，为true忽略acknowledgeMode参数
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// 5、使用session创建message
		Message message = session.createTextMessage("Hello JMS");

		// 6、创建Producer=Session+Destination
		MessageProducer producer = session.createProducer(queue);

		// 发送message
		producer.send(message);

		logger.debug("message send!!");

		// 7、消息消费者Consumer=Session+Destination
		MessageConsumer consumer = session.createConsumer(queue);
		// 接收消息
		Message recvMessage = consumer.receive();

		logger.debug("recevice message : " + ((TextMessage) recvMessage).getText());
		
	}
}
