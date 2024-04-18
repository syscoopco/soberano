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

import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.ExchangeRateEqualsToZeroException;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.exception.OrdersOngoingException;
import co.syscoop.soberano.exception.PasswordsMustMatchException;
import co.syscoop.soberano.exception.ProcessRunningException;
import co.syscoop.soberano.exception.ShiftHasBeenClosedException;
import co.syscoop.soberano.exception.SoberanoLDAPException;
import co.syscoop.soberano.exception.SomeFieldsContainWrongValuesException;
import co.syscoop.soberano.exception.WeightsMustSum100;
import co.syscoop.soberano.exception.WorkerMustBeAssignedToAResponsibilityException;
import co.syscoop.soberano.exception.WrongDateTimeException;
import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;
import co.syscoop.soberano.vocabulary.Labels;

@SuppressWarnings({ "serial", "rawtypes" })
public class ModifyButtonComposer extends SelectorComposer {
	
	private TrackedObjectFormHelper trackedObjectFormHelper = null;;
	private Include incDetails = null;
	
	@Wire
	private Button btnApply;
	
	public ModifyButtonComposer(TrackedObjectFormHelper trackedObjectFormHelper) {
		this.trackedObjectFormHelper = trackedObjectFormHelper;
	}
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
          incDetails = (Include) btnApply.query("#incDetails");
    }
	
	@Listen("onClick = button#btnApply")
    public void btnApply_onClick() throws Throwable {
		
		try{
			if (trackedObjectFormHelper.modifyFromForm(incDetails) == -1) {
				throw new NotEnoughRightsException();						
			}
			else {
				//update object's treeitem label
				trackedObjectFormHelper.updateObjectTreeitemLabel(incDetails);

				Messagebox.show(Labels.getLabel("message.notification.ChangesWereApplied"), 
	  					Labels.getLabel("messageBoxTitle.Information"), 
						0, 
						Messagebox.INFORMATION);
			}
		}
		catch(DuplicateKeyException ex) {
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.validation.thereIsAlreadyAnObjectWithThatId"), 
										Labels.getLabel("messageBoxTitle.Validation"),
										Messagebox.EXCLAMATION);
		}
		catch(ExchangeRateEqualsToZeroException ex) {
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.validation.exchangeRateCannotBeZero"), 
										Labels.getLabel("messageBoxTitle.Validation"),
										Messagebox.EXCLAMATION);
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
		catch(OrdersOngoingException ex) {
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.validation.thereCanNotBeOngoingOrdersToProceed"), 
										Labels.getLabel("messageBoxTitle.Warning"),
										Messagebox.EXCLAMATION);
		}
		catch(PasswordsMustMatchException ex) {
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.validation.worker.PasswordsMustMatch"), 
										Labels.getLabel("messageBoxTitle.Validation"),
										Messagebox.EXCLAMATION);
		}
		catch(ProcessRunningException ex) {
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.validation.aProcessReferencingTheObjectIsRunning"), 
										Labels.getLabel("messageBoxTitle.Warning"),
										Messagebox.EXCLAMATION);
		}
		catch(ShiftHasBeenClosedException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.validation.shiftHasBeenClosed"), 
					Labels.getLabel("messageBoxTitle.Warning"),
					Messagebox.EXCLAMATION);
		}
		catch(SoberanoLDAPException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.error.LDAP.ErrorChangingPassword") + ": " + ex.getMessage(), 
					Labels.getLabel("messageBoxTitle.Error"),
					Messagebox.ERROR);
		}
		catch(SomeFieldsContainWrongValuesException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.validation.someFieldsContainWrongValues"), 
					Labels.getLabel("messageBoxTitle.Validation"),
					Messagebox.EXCLAMATION);
		}
		catch(WeightsMustSum100 ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.validation.weightsMustSum100"), 
					Labels.getLabel("messageBoxTitle.Validation"),
					Messagebox.EXCLAMATION);
		}
		catch(WorkerMustBeAssignedToAResponsibilityException ex) {
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.validation.worker.WorkerMustBeAssignedToAResponsibility"), 
										Labels.getLabel("messageBoxTitle.Validation"),
										Messagebox.EXCLAMATION);
		}
		catch(WrongDateTimeException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.validation.wrongBusinessEventOccurrenceDateTime"), 
					Labels.getLabel("messageBoxTitle.Validation"),
					Messagebox.EXCLAMATION);
		}
		catch(WrongValueException ex) {
			ExceptionTreatment.logAndShow(ex, 
										ex.getMessage(), 
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