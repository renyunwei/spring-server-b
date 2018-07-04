package com.ryw.server.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Lists;

@SpringBootConfiguration
public class RestTemplateConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(RestTemplateConfig.class);
	
	@Value("${connectTimeout:10000}")
	public String connectTimeoutStr;
	
	@Value("${readTimeout:10000}")
	public String readTimeoutStr;
	
	@Autowired
	private ClientHttpRequestInterceptor braveClientHttpRequestInterceptor;
	
	@Bean(value = "loadBalanced")
	@LoadBalanced
	public RestTemplate restTemplate() {
		logger.info("init requestFactory");
		RestTemplate restTemplate = new RestTemplate();
		int connectTimeout = Integer.parseInt(this.connectTimeoutStr);
		int readTimeout = Integer.parseInt(this.readTimeoutStr);
		
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		if ((connectTimeout > 0) && (readTimeout > 0)) {
			requestFactory.setConnectTimeout(connectTimeout);
			requestFactory.setReadTimeout(readTimeout);
		}else if ((connectTimeout == 0) && (readTimeout > 0)) {
			requestFactory.setReadTimeout(readTimeout);
		}else if ((connectTimeout > 0) && (readTimeout == 0)) {
			requestFactory.setConnectTimeout(connectTimeout);
		}
		
		restTemplate.setRequestFactory(requestFactory);
		
		if (null != this.braveClientHttpRequestInterceptor) {
			List<ClientHttpRequestInterceptor> interceptors = Lists.newArrayList();
			interceptors.add(this.braveClientHttpRequestInterceptor);
			restTemplate.setInterceptors(interceptors);
		}
		
		return restTemplate;
	}
}
