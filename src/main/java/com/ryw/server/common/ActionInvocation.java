package com.ryw.server.common;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public class ActionInvocation {
	
	private String actionName;
	
	private String logId;
	
	private int invokeNum = 1;
	
	ActionInvocation(String actionName,String logId) {
		this.actionName = actionName;
		if (StringUtils.isEmpty(logId)) {
			this.logId = UUID.randomUUID().toString();
		}else {
			this.logId = logId;
		}
	}
	
	public void invoke() {
		invokeNum++;
	}

	public String getActionName() {
		return actionName;
	}

	public String getLogId() {
		return logId;
	}


	public int getInvokeNum() {
		return invokeNum;
	}
}
