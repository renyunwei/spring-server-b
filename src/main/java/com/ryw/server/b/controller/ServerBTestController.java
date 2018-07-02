package com.ryw.server.b.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("service-b")
public class ServerBTestController {

	private static final Logger logger = LoggerFactory.getLogger(ServerBTestController.class);
	
	@Autowired
	private Registration registration;
	
	@RequestMapping(value = "hello-test", method = RequestMethod.GET)
	public String helloWorldA() {
		logger.info("hello world B server");
		logger.info("/hello-test, host:" + registration.getHost() + ", service_id:" + registration.getServiceId());
		return "hello world B server";
	}
	
}
