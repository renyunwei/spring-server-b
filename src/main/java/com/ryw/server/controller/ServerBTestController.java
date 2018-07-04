package com.ryw.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerBTestController {

	private static final Logger logger = LoggerFactory.getLogger(ServerBTestController.class);
	
	@Autowired
	private Registration registration;
	
	@Value("${server.port}")
	private String serverPort;
	
	@RequestMapping(value = "hello-test", method = RequestMethod.GET)
	public String helloWorldA(String username) {
		logger.info("hello world Bserver,this service's port is {}",serverPort);
		logger.info("/hello-test, host:" + registration.getHost() + ", service_id:" + registration.getServiceId());
		return "hello world B server,I am " + username;
	}
	
}
