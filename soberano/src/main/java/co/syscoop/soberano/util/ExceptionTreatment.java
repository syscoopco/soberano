package co.syscoop.soberano.util;

import java.util.Objects;

import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.exception.PasswordsMustMatchException;
import co.syscoop.soberano.exception.ShiftHasBeenClosedException;
import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.exception.SomeFieldsContainWrongValuesException;
import co.syscoop.soberano.exception.WorkerMustBeAssignedToAResponsibilityException;
import co.syscoop.soberano.exception.WrongDateTimeException;

import org.springframework.dao.DuplicateKeyException;

import org.zkoss.zk.ui.WrongValueException;

public class ExceptionTreatment {

	public static void logAndShow(Exception ex, String messageBody, String messageTitle, String messageIcon) throws SoberanoException {
		
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
			if (ex.getClass().getName().equals("org.zkoss.zk.ui.WrongValueException"))
				throw new WrongValueException(ex);
			else if (ex.getClass().getName().equals("co.syscoop.soberano.exception.PasswordsMustMatchException"))
				throw new PasswordsMustMatchException(ex);
			else if (ex.getClass().getName().equals("co.syscoop.soberano.exception.NotEnoughRightsException"))
				throw new NotEnoughRightsException(ex);
			else if (ex.getClass().getName().equals("co.syscoop.soberano.exception.WorkerMustBeAssignedToAResponsibilityException"))
				throw new WorkerMustBeAssignedToAResponsibilityException(ex);
			else if (ex.getClass().getName().equals("org.springframework.dao.DuplicateKeyException"))
				throw new DuplicateKeyException("");
			else if (ex.getClass().getName().equals("co.syscoop.soberano.exception.WrongDateTimeException"))
				throw new WrongDateTimeException(ex);
			else if (ex.getClass().getName().equals("co.syscoop.soberano.exception.ShiftHasBeenClosedException"))
				throw new ShiftHasBeenClosedException(ex);
			else if (ex.getClass().getName().equals("co.syscoop.soberano.exception.SomeFieldsContainWrongValuesException"))
				throw new SomeFieldsContainWrongValuesException(ex);
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
