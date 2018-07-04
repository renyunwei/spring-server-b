package com.ryw.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * 从外部配置文件读取的相关配置
 * @author ryw
 *
 */
@Configuration
@ConfigurationProperties
public class ContextConfig {

	private String springClientA;
	
	private String springServerA;

	public String getSpringClientA() {
		return springClientA;
	}

	public void setSpringClientA(String springClientA) {
		this.springClientA = springClientA;
	}

	public String getSpringServerA() {
		return springServerA;
	}

	public void setSpringServerA(String springServerA) {
		this.springServerA = springServerA;
	}

	@Override
	public String toString() {
		return "ContextConfig [springClientA=" + springClientA
				+ ", springServerA=" + springServerA + "]";
	}
}
