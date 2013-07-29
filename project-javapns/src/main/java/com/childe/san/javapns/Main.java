package com.childe.san.javapns;

import javapns.communication.exceptions.CommunicationException;

/**
 * @author zhangh
 * @createTime 2013-7-29 上午11:56:45
 */
public class Main {

	public static void main(String[] args) throws CommunicationException {
		PushByAPNS.pushMessageToAPNS("fb9ec2b85387cfa45168b7cad969274228eced5b48f967925dfefd4269756f56", "hello111", 1000,
				1);
	}

}
