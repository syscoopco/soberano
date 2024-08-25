package co.syscoop.soberano.composers;

import org.springframework.dao.DuplicateKeyException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;

import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.exception.SomeFieldsContainWrongValuesException;
import co.syscoop.soberano.ui.helper.ConfigurationFormHelper;
import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;
import co.syscoop.soberano.vocabulary.Labels;

@SuppressWarnings("serial")
public class ModifyOtherConfigurationButtonComposer extends ModifyButtonComposer {
	
	@Wire
	private Button btnApply;

	public ModifyOtherConfigurationButtonComposer() {
		super((TrackedObjectFormHelper) new ConfigurationFormHelper());
	}
	
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
          incDetails = (Include) btnApply.query("#wndContentPanel").query("#incDetails");
    }
	
	@Override
	@Listen("onClick = button#btnApply")
    public void btnApply_onClick() throws Throwable {
		
		try{
			if (trackedObjectFormHelper.modifyFromForm(incDetails) == -1) {
				throw new NotEnoughRightsException();						
			}
			else {
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