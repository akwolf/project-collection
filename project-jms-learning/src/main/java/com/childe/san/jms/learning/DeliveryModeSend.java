package com.childe.san.jms.learning;

import javax.jms.DeliveryMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangh
 * @createTime 2013-6-5 下午4:33:49
 */
public class DeliveryModeSend {

	public static void main(String[] args) {
		final Logger logger = LoggerFactory.getLogger(MessageHeaderDemo.class);

		logger.debug("PERSISTENT Delivery Model {}", DeliveryMode.PERSISTENT);
		logger.debug("NON_PERSISTENT Delivery Model {}", DeliveryMode.NON_PERSISTENT);
	}
}
