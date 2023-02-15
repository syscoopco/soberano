package co.syscoop.soberano.composers;

import org.springframework.dao.DuplicateKeyException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.domain.tracked.Provider;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.util.ExceptionTreatment;
import co.syscoop.soberano.util.ZKUtilitity;
import co.syscoop.soberano.vocabulary.Labels;

@SuppressWarnings("serial")
public class RecordProviderButtonComposer extends ProviderFormComposer {
	
	@Wire
	private Button btnRecord;
	
	@Listen("onClick = button#btnRecord")
    public void btnRecord_onClick() throws Throwable {
		try{
			Include incDetails = (Include) btnRecord.getParent().getParent().getParent().query("#wndContentPanel").query("#incDetails");		
			Provider newCategory = new Provider(0,
											0,
											((Textbox) incDetails.query("#txtName")).getValue());
			if (newCategory.record() == -1) {
				throw new NotEnoughRightsException();						
			}
			else {
				//clean form
				ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtName"), "");
			}
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