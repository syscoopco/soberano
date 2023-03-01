package co.syscoop.soberano.composers;

import org.springframework.dao.DuplicateKeyException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.domain.tracked.CostCenter;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.util.ExceptionTreatment;
import co.syscoop.soberano.util.ZKUtilitity;
import co.syscoop.soberano.vocabulary.Labels;

@SuppressWarnings("serial")
public class RecordCostCenterButtonComposer extends CostCenterFormComposer {
	
	@Wire
	private Button btnRecord;
	
	@Listen("onClick = button#btnRecord")
    public void btnRecord_onClick() throws Throwable {
		try{
			Include incDetails = (Include) btnRecord.getParent().getParent().getParent().query("#wndContentPanel").query("#incDetails");
			Comboitem iWarehouseItem = ((Combobox) incDetails.query("#cmbInputWarehouse")).getSelectedItem();
			Comboitem oWarehouseItem =((Combobox) incDetails.query("#cmbOutputWarehouse")).getSelectedItem();
			CostCenter newCostCenter = new CostCenter(0,
													0,
													((Textbox) incDetails.query("#txtName")).getValue(),
													iWarehouseItem == null ? null : ((DomainObject) iWarehouseItem.getValue()).getId(),
													oWarehouseItem == null ? null : ((DomainObject) oWarehouseItem.getValue()).getId());
			if (newCostCenter.record() == -1) {
				throw new NotEnoughRightsException();						
			}
			else {
				//clean form
				ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtName"), "");
				ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#cmbInputWarehouse"), "");
				ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#cmbOutputWarehouse"), "");
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