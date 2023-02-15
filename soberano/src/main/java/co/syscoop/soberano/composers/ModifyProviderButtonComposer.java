package co.syscoop.soberano.composers;

import org.springframework.dao.DuplicateKeyException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;

import co.syscoop.soberano.domain.tracked.Provider;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.util.ExceptionTreatment;
import co.syscoop.soberano.vocabulary.Labels;

@SuppressWarnings({ "serial", "rawtypes" })
public class ModifyProviderButtonComposer extends SelectorComposer {
	
	@Wire
	private Button btnApply;
	
	@Listen("onClick = button#btnApply")
    public void btnApply_onClick() throws Throwable {
		try{
			Include incDetails = (Include) btnApply.getParent().getParent().getParent().query("#incDetails");		
			
			Provider provider = new Provider(((Intbox) incDetails.getParent().query("#intId")).getValue(),
															0,
															((Textbox) incDetails.query("#txtName")).getValue());
			if (provider.modify() == -1) {
				throw new NotEnoughRightsException();						
			}
			else {
				//update object's treeitem label
				Tree treeObjects = (Tree) incDetails.getParent().query("#wndShowingAll").query("#treeObjects");
				treeObjects.getSelectedItem().setLabel(provider.getQualifiedName());
				
				Messagebox.show(Labels.getLabel("message.notification.ChangesWereApplied"), 
			  					Labels.getLabel("messageBoxTitle.Information"), 
								0, 
								Messagebox.INFORMATION);
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
		catch(DuplicateKeyException ex) {
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