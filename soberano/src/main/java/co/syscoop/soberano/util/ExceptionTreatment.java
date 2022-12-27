package co.syscoop.soberano.util;

import java.util.Objects;

import org.zkoss.zul.Messagebox;

public class ExceptionTreatment {

	public static void logAndShow(Exception ex, String messageBody, String messageTitle, String messageIcon) {
		ex.printStackTrace();
		ex.fillInStackTrace();
		Messagebox.show(messageBody, 
						messageTitle, 
						0, 
						messageIcon);	
	}
	
	public static Throwable getRootCause(Throwable throwable) {
	    Objects.requireNonNull(throwable);
	    Throwable rootCause = throwable;
	    while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
	        rootCause = rootCause.getCause();
	    }
	    return rootCause;
	}
}
