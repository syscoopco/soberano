package co.syscoop.soberano.exception;

import java.util.Objects;

import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.util.SpringUtility;

import org.springframework.dao.DuplicateKeyException;

import org.zkoss.zk.ui.WrongValueException;

public class ExceptionTreatment {
	
	public static void log(Exception ex) {
		ex.printStackTrace();
		ex.fillInStackTrace();
	}

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
			if (ex.getClass().getName().equals("co.syscoop.soberano.exception.AtLeastOneInventoryItemMustBeMovedException"))
				throw new AtLeastOneInventoryItemMustBeMovedException(ex);
			else if (ex.getClass().getName().equals("co.syscoop.soberano.exception.CurrencyHasBalanceException"))
				throw new CurrencyHasBalanceException(ex);
			else if (ex.getClass().getName().equals("co.syscoop.soberano.exception.CustomerRequiredException"))
				throw new CustomerRequiredException(ex);
			else if (ex.getClass().getName().equals("co.syscoop.soberano.exception.DebtorRequiredException"))
				throw new DebtorRequiredException(ex);
			else if (ex.getClass().getName().equals("co.syscoop.soberano.exception.DisabledCurrencyException"))
				throw new DisabledCurrencyException(ex);
			else if (ex.getClass().getName().equals("org.springframework.dao.DuplicateKeyException"))
				throw new DuplicateKeyException("");		
			else if (ex.getClass().getName().equals("co.syscoop.soberano.exception.ExchangeRateEqualsToZeroException"))
				throw new ExchangeRateEqualsToZeroException(ex);		
			else if (ex.getClass().getName().equals("co.syscoop.soberano.exception.FirstOrderRequiresCashOperationException"))
				throw new FirstOrderRequiresCashOperationException(ex);		
			else if (ex.getClass().getName().equals("co.syscoop.soberano.exception.NotEnoughRightsException"))
				throw new NotEnoughRightsException(ex);
			else if (ex.getClass().getName().equals("co.syscoop.soberano.exception.OnlyOneOrderPerCounterIsPermittedException"))
				throw new OnlyOneOrderPerCounterIsPermittedException(ex);
			else if (ex.getClass().getName().equals("co.syscoop.soberano.exception.OrderAlreadyCollectedException"))
				throw new OrderAlreadyCollectedException(ex);
			else if (ex.getClass().getName().equals("co.syscoop.soberano.exception.OrderCanceledException"))
				throw new OrderCanceledException(ex);	
			else if (ex.getClass().getName().equals("co.syscoop.soberano.exception.OrdersOngoingException"))
				throw new OrdersOngoingException(ex);	
			else if (ex.getClass().getName().equals("co.syscoop.soberano.exception.PasswordsMustMatchException"))
				throw new PasswordsMustMatchException(ex);
			else if (ex.getClass().getName().equals("co.syscoop.soberano.exception.ProcessRunningException"))
				throw new ProcessRunningException(ex);			
			else if (ex.getClass().getName().equals("co.syscoop.soberano.exception.RunningOutOfInventoryException"))
				throw new RunningOutOfInventoryException(ex);		
			else if (ex.getClass().getName().equals("co.syscoop.soberano.exception.SameWarehouseException"))
				throw new SameWarehouseException(ex);		
			else if (ex.getClass().getName().equals("co.syscoop.soberano.exception.ShiftHasBeenClosedException"))
				throw new ShiftHasBeenClosedException(ex);
			else if (ex.getClass().getName().equals("co.syscoop.soberano.exception.SomeFieldsContainWrongValuesException"))
				throw new SomeFieldsContainWrongValuesException(ex);
			else if (ex.getClass().getName().equals("co.syscoop.soberano.exception.WeightsMustSum100"))
				throw new WeightsMustSum100(ex);
			else if (ex.getClass().getName().equals("co.syscoop.soberano.exception.WorkerMustBeAssignedToAResponsibilityException"))
				throw new WorkerMustBeAssignedToAResponsibilityException(ex);
			else if (ex.getClass().getName().equals("co.syscoop.soberano.exception.WrongDateTimeException"))
				throw new WrongDateTimeException(ex);
			else if (ex.getClass().getName().equals("co.syscoop.soberano.exception.WrongProcessSpecification"))
				throw new WrongProcessSpecificationException(ex);		
			else if (ex.getClass().getName().equals("org.zkoss.zk.ui.WrongValueException"))
				throw new WrongValueException(ex);
			else if (ex.getClass().getName().equals("co.syscoop.soberano.exception.ZoneNotCoveredByDeliveryProviderException"))
				throw new ZoneNotCoveredByDeliveryProviderException(ex);
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
