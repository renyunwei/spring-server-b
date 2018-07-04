package com.ryw.server.common;


public class InvocationHolder {
	
	private static final ThreadLocal<ActionInvocation> invocationHolder = new ThreadLocal<ActionInvocation>();
	
	public static void start(String actionName,String logId) {
		ActionInvocation actionInvocation = new ActionInvocation(actionName, logId);
		invocationHolder.set(actionInvocation);
	}
	
	public static void invoke() {
		check();
		invocationHolder.get().invoke();
	}
	
	public static int getInvokeNum() {
		check();
		return invocationHolder.get().getInvokeNum();
	}
	
	public static String getInvocationName() {
		check();
		return invocationHolder.get().getActionName();
	}
	
	public static void end() {
		invocationHolder.remove();
	}
	
	public static String getLogId() {
		if (invocationHolder.get() != null) {
			return invocationHolder.get().getLogId();
		}
		return null;
	}
	
	
	public static void check() {
		if (invocationHolder.get() == null) {
			throw new RuntimeException("invocation not start yet");
		}
	}

}
