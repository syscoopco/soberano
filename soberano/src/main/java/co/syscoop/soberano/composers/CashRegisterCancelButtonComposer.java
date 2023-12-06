package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Box;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;

import co.syscoop.soberano.database.relational.QueryResultWithReport;
import co.syscoop.soberano.exception.ConfirmationRequiredException;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.exception.ShiftHasBeenClosedException;
import co.syscoop.soberano.exception.SomeFieldsContainWrongValuesException;
import co.syscoop.soberano.ui.helper.BusinessActivityTrackedObjectFormHelper;
import co.syscoop.soberano.ui.helper.CashRegisterFormHelper;
import co.syscoop.soberano.util.ExceptionTreatment;
import co.syscoop.soberano.vocabulary.Labels;

@SuppressWarnings("serial")
public class CashRegisterCancelButtonComposer extends CashRegisterTrackedObjectRecordButtonComposer {
	
	@Wire
	private Button btnCancel;
	
	public CashRegisterCancelButtonComposer() {
		super((BusinessActivityTrackedObjectFormHelper) new CashRegisterFormHelper());
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
          boxDetails = (Box) btnCancel.query("#wndContentPanel").query("#boxDetails");
    }
	
	@Listen("onClick = button#btnCancel")
    public void btnCancel_onClick() throws Throwable {
		
		try{
			QueryResultWithReport qrwr = ((CashRegisterFormHelper) trackedObjectFormHelper).cancel(boxDetails);
			
			if (!qrwr.getReport().isEmpty()) {
				//TODO: print ticket
			}				
			
			Executions.sendRedirect("/cash_register.zul?id=" + 
					((Intbox) boxDetails.query("#intSelectedCashRegister")).getValue().toString() + "&oid=" +
					((Intbox) boxDetails.query("#intSelectedOrder")).getValue().toString());
		}
		catch(ConfirmationRequiredException ex) {
			return;
		}
		catch(NotEnoughRightsException ex) {
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.permissions.NotEnoughRights"), 
										Labels.getLabel("messageBoxTitle.Warning"),
										Messagebox.EXCLAMATION);
		}
		catch(NullPointerException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.validation.someFieldsContainWrongValues"), 
					Labels.getLabel("messageBoxTitle.Validation"),
					Messagebox.EXCLAMATION);
		}
		catch(ShiftHasBeenClosedException ex) {
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.validation.shiftHasBeenClosed"), 
										Labels.getLabel("messageBoxTitle.Warning"),
										Messagebox.EXCLAMATION);
		}
		catch(SomeFieldsContainWrongValuesException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.validation.someFieldsContainWrongValues"), 
					Labels.getLabel("messageBoxTitle.Validation"),
					Messagebox.EXCLAMATION);
		}
		catch(Exception ex)	{
			ExceptionTreatment.logAndShow(ex, 
										ex.getMessage(), 
										Labels.getLabel("messageBoxTitle.Error"),
										Messagebox.ERROR);
		}
	}
}
