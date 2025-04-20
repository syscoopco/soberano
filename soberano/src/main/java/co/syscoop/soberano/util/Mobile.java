package co.syscoop.soberano.util;

import org.zkoss.zk.ui.Executions;

public class Mobile {

	public static boolean isMobile() {
		
		String userAgent = Executions.getCurrent().getHeader("user-agent");
		return userAgent.contains("Mobile") && userAgent.contains("Safari") ||
				userAgent.contains("Mobile") && userAgent.contains("Firefox") ||
				userAgent.contains("Android");
	}
	
	/*
	public static String getBrowser() {
		
		String userAgent = Executions.getCurrent().getHeader("user-agent");
		if (userAgent.contains("Firefox")) {
			return "Firefox";
		}
		if (userAgent.contains("Safari")) {
			return "Safari";
		}
		else {
			return "Unkown";
		}
	}
	*/
	
	public static String getUserAgent() {
		
		return Executions.getCurrent().getHeader("user-agent");
	}
}
