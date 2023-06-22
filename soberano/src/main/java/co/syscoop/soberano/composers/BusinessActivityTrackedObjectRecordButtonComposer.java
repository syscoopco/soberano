package co.syscoop.soberano.composers;

import org.springframework.dao.DuplicateKeyException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Box;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;

import co.syscoop.soberano.exception.AtLeastOneInventoryItemMustBeMovedException;
import co.syscoop.soberano.exception.ConfirmationRequiredException;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.exception.PasswordsMustMatchException;
import co.syscoop.soberano.exception.ShiftHasBeenClosedException;
import co.syscoop.soberano.exception.SoberanoLDAPException;
import co.syscoop.soberano.exception.SomeFieldsContainWrongValuesException;
import co.syscoop.soberano.exception.WorkerMustBeAssignedToAResponsibilityException;
import co.syscoop.soberano.exception.WrongDateTimeException;
import co.syscoop.soberano.util.ExceptionTreatment;
import co.syscoop.soberano.vocabulary.Labels;
import co.syscoop.soberano.ui.helper.BusinessActivityTrackedObjectFormHelper;

@SuppressWarnings({ "serial", "rawtypes" })
public class BusinessActivityTrackedObjectRecordButtonComposer extends SelectorComposer {

	private BusinessActivityTrackedObjectFormHelper trackedObjectFormHelper = null;;
	protected Box boxDetails = null;
	
	@Wire
	protected Button btnRecord;
	
	public BusinessActivityTrackedObjectRecordButtonComposer(BusinessActivityTrackedObjectFormHelper trackedObjectFormHelper) {
		this.trackedObjectFormHelper = trackedObjectFormHelper;
	}
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
          boxDetails = (Box) btnRecord.query("#boxDetails");
    }
	
	@Listen("onClick = button#btnRecord")
    public void btnRecord_onClick() throws Throwable {
		
		try{
			if (trackedObjectFormHelper.recordFromForm(boxDetails) == -1) {
				throw new NotEnoughRightsException();						
			}
			else {
				//clean form
				trackedObjectFormHelper.cleanForm(boxDetails);
			}
		}
		catch(ConfirmationRequiredException ex) {
			return;
		}
		catch(AtLeastOneInventoryItemMustBeMovedException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.validation.atLeastOneInventoryItemMustBeMoved"), 
					Labels.getLabel("messageBoxTitle.Validation"),
					Messagebox.EXCLAMATION);
		}
		catch(WrongDateTimeException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.validation.wrongBusinessEventOccurrenceDateTime"), 
					Labels.getLabel("messageBoxTitle.Validation"),
					Messagebox.EXCLAMATION);
		}
		catch(ShiftHasBeenClosedException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.validation.shiftHasBeenClosed"), 
					Labels.getLabel("messageBoxTitle.Validation"),
					Messagebox.EXCLAMATION);
		}
		catch(SomeFieldsContainWrongValuesException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.validation.someFieldsContainWrongValues"), 
					Labels.getLabel("messageBoxTitle.Validation"),
					Messagebox.EXCLAMATION);
		}
		catch(PasswordsMustMatchException ex) {
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.validation.worker.PasswordsMustMatch"), 
										Labels.getLabel("messageBoxTitle.Validation"),
										Messagebox.EXCLAMATION);
		}
		catch(WrongValueException ex) {
			ExceptionTreatment.logAndShow(ex, 
										ex.getMessage(), 
										Labels.getLabel("messageBoxTitle.Validation"),
										Messagebox.EXCLAMATION);
		}
		catch(NotEnoughRightsException ex) {
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.permissions.NotEnoughRights"), 
										Labels.getLabel("messageBoxTitle.Warning"),
										Messagebox.EXCLAMATION);
		}
		catch(WorkerMustBeAssignedToAResponsibilityException ex) {
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.validation.worker.WorkerMustBeAssignedToAResponsibility"), 
										Labels.getLabel("messageBoxTitle.Validation"),
										Messagebox.EXCLAMATION);
		}
		catch(DuplicateKeyException ex)
		{
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.validation.thereIsAlreadyAnObjectWithThatId"), 
										Labels.getLabel("messageBoxTitle.Validation"),
										Messagebox.EXCLAMATION);
		}
		catch(SoberanoLDAPException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.error.LDAP.ErrorChangingPassword") + ": " + ex.getMessage(), 
					Labels.getLabel("messageBoxTitle.Error"),
					Messagebox.ERROR);
		}
		catch(NullPointerException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.validation.someFieldsContainWrongValues"), 
					Labels.getLabel("messageBoxTitle.Validation"),
					Messagebox.EXCLAMATION);
		}
		catch(Exception ex)
		{
			ExceptionTreatment.logAndShow(ex, 
										ex.getMessage(), 
										Labels.getLabel("messageBoxTitle.Error"),
										Messagebox.ERROR);
		}
	}
}
