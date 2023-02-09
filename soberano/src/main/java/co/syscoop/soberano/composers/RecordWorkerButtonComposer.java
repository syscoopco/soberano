package co.syscoop.soberano.composers;

import org.springframework.dao.DuplicateKeyException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import co.syscoop.soberano.domain.tracked.Worker;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.exception.PasswordsMustMatchException;
import co.syscoop.soberano.exception.WorkerMustBeAssignedToAResponsibilityException;
import co.syscoop.soberano.ui.helper.WorkerFormHelper;
import co.syscoop.soberano.util.ExceptionTreatment;
import co.syscoop.soberano.vocabulary.Labels;

@SuppressWarnings({ "serial" })
public class RecordWorkerButtonComposer extends WorkerFormComposer {
	
	@Wire
	private Button btnRecord;
	
	@Listen("onClick = button#btnRecord")
    public void btnRecord_onClick() throws Throwable {
		try{
			Include incDetails = (Include) btnRecord.getParent().getParent().getParent().query("#wndContentPanel").query("#incDetails");		
			String pwd = WorkerFormHelper.passwordsMatch(incDetails);
			WorkerFormHelper.fillAssigmentArrays(responsibilities, authorities, incDetails);
			incContactData = (Include) incDetails.query("#incContactData");
			Worker newWorker = new Worker(0,
										0,
										((Textbox) incDetails.query("#txtUserName")).getValue(),
										((Textbox) incDetails.query("#txtFirstName")).getValue(),
										((Textbox) incDetails.query("#txtLastName")).getValue(),
										pwd,
										((Textbox) incContactData.query("#txtPhoneNumber")).getValue(),
										((DomainObject) (((Combobox) incContactData.query("#cmbCountry")).getSelectedItem().getValue())).getStringId(),
										((Textbox) incContactData.query("#txtAddress")).getValue(),
										((Textbox) incContactData.query("#txtPostalCode")).getValue(),
										((Textbox) incContactData.query("#txtTown")).getValue(),
										((Combobox) incContactData.query("#cmbMunicipality")).getSelectedItem().getValue(),
										((Textbox) incContactData.query("#txtCity")).getValue(),
										((Combobox) incContactData.query("#cmbProvince")).getSelectedItem().getValue(),
										((Doublebox) incContactData.query("#dblLatitude")).getValue(),
										((Doublebox) incContactData.query("#dblLongitude")).getValue(),
										responsibilities,
										authorities);
			if (newWorker.record() == -1) {
				throw new NotEnoughRightsException();						
			}
			else {
				Executions.sendRedirect("/new_worker.zul");
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
		catch(Exception ex)
		{
			ExceptionTreatment.logAndShow(ex, 
										ex.getMessage(), 
										Labels.getLabel("messageBoxTitle.Error"),
										Messagebox.ERROR);
		}
	}
}