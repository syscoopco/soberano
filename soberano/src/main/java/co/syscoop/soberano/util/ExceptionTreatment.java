package co.syscoop.soberano.util;

import java.util.Objects;

import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zk.ui.WrongValueException;

public class ExceptionTreatment {

	public static void logAndShow(Exception ex, String messageBody, String messageTitle, String messageIcon) {
		
		String componentMessage = "";
		if (ex.getClass().getName().equals("org.zkoss.zk.ui.WrongValueException")) {
			try {
				String placeholder = ((Textbox) ((WrongValueException) ex).getComponent()).getPlaceholder();
				if (placeholder != null) componentMessage = " " + placeholder;
			}
			catch(Exception e) {}
		}		
		ex.printStackTrace();
		ex.fillInStackTrace();
		Messagebox.show(messageBody + componentMessage, 
						messageTitle, 
						0, 
						messageIcon);
		if (SpringUtility.underTesting()) 
			throw new WrongValueException(ex);
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
