package co.syscoop.soberano.composers;

import org.springframework.dao.DuplicateKeyException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.exception.PasswordsMustMatchException;
import co.syscoop.soberano.exception.SoberanoLDAPException;
import co.syscoop.soberano.exception.WorkerMustBeAssignedToAResponsibilityException;
import co.syscoop.soberano.util.ExceptionTreatment;
import co.syscoop.soberano.vocabulary.Labels;
import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;

@SuppressWarnings({ "serial", "rawtypes" })
public class RecordButtonComposer extends SelectorComposer {

	private TrackedObjectFormHelper trackedObjectFormHelper = null;;
	private Include incDetails = null;
	
	@Wire
	private Button btnRecord;
	
	public RecordButtonComposer(TrackedObjectFormHelper trackedObjectFormHelper) {
		this.trackedObjectFormHelper = trackedObjectFormHelper;
	}
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
          incDetails = (Include) btnRecord.query("#wndContentPanel").query("#incDetails");
    }
	
	@Listen("onClick = button#btnRecord")
    public void btnRecord_onClick() throws Throwable {
		
		try{
			if (trackedObjectFormHelper.recordFromForm(incDetails) == -1) {
				throw new NotEnoughRightsException();						
			}
			else {
				//clean form
				trackedObjectFormHelper.cleanForm(incDetails);
			}
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
