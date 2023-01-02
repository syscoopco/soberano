package co.syscoop.soberano.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class SpringUtility {
	
	private static String loggedUserForTesting = "UndeterminedUser";
	
	public static Boolean underTesting() {
		return ContextLoader.getCurrentWebApplicationContext() == null;
	}

	public static String loggedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			return authentication.getName().toLowerCase();
		}
		else {
			return loggedUserForTesting;
		}		
	}
	
	public static WebApplicationContext webApplicationContext() {
		
		return ContextLoader.getCurrentWebApplicationContext();
	}
	
	public static ApplicationContext applicationContext() {
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		if (webApplicationContext != null) {
			
			//for web application
			return webApplicationContext;
		}
		else {
			
			//for testing
			return new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		}
	}
	
	public static String getPath(String webAppClassPath) throws UnsupportedEncodingException {
		String fullPath = URLDecoder.decode(webAppClassPath, "UTF-8");
		String pathArr[] = fullPath.split("/WEB-INF/");
		fullPath = pathArr[0];
		String reponsePath = "";
		reponsePath = new File(fullPath).getPath() + File.separatorChar;
		return reponsePath;
	}

	public String getLoggedUserForTesting() {
		return loggedUserForTesting;
	}

	public static void setLoggedUserForTesting(String loggedUserForTesting) {
		SpringUtility.loggedUserForTesting = loggedUserForTesting;
	}
}
