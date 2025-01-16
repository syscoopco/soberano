package co.syscoop.soberano.ui.helper;

import java.sql.SQLException;

import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import co.syscoop.soberano.domain.tracked.CostCenter;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.util.ui.ZKUtility;
import co.syscoop.soberano.view.viewmodel.WarehouseSelectionViewModel;

public class CostCenterFormHelper extends TrackedObjectFormHelper {
	
	@Override
	public void fillForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException {
		
		CostCenter costCenter = new CostCenter(((DomainObject) data.getData().getValue()).getId());
		costCenter.get();
		
		//store in the form the ids of shown object for subsequent modification
		((Intbox) incDetails.getParent().query("#intId")).setValue(costCenter.getId());
		((Textbox) incDetails.getParent().query("#txtStringId")).setText(costCenter.getStringId());
		
		incDetails.setVisible(true);
		Clients.scrollIntoView(incDetails.query("#txtName"));
		((Button) incDetails.getParent().query("#incSouth").query("#btnApply")).setDisabled(false);
		
		ZKUtility.setValueWOValidation((Textbox) incDetails.query("#txtName"), costCenter.getName());
		
		Combobox cmbInputWarehouse = (Combobox) incDetails.query("#cmbInputWarehouse");
		WarehouseSelectionViewModel wSelectionViewModel = new WarehouseSelectionViewModel();
		cmbInputWarehouse.setModel(wSelectionViewModel.getModel());
		
		if (costCenter.getInputWarehouse() > 0) 
			ZKUtility.setValueWOValidation(cmbInputWarehouse, costCenter.getInputWarehouse());
		else
			(cmbInputWarehouse).setSelectedItem(null);
		
		Combobox cmbOutputWarehouse = (Combobox) incDetails.query("#cmbOutputWarehouse");
		WarehouseSelectionViewModel owSelectionViewModel = new WarehouseSelectionViewModel();
		cmbOutputWarehouse.setModel(owSelectionViewModel.getModel());
			
		if (costCenter.getOutputWarehouse() > 0) 
			ZKUtility.setValueWOValidation(cmbOutputWarehouse, costCenter.getOutputWarehouse());
		else
			(cmbOutputWarehouse).setSelectedItem(null);
	}

	@Override
	public void cleanForm(Include incDetails) {
		
		Clients.scrollIntoView(incDetails.query("#txtName"));
		ZKUtility.setValueWOValidation((Textbox) incDetails.query("#txtName"), "");
		ZKUtility.setValueWOValidation((Textbox) incDetails.query("#cmbInputWarehouse"), "");
		ZKUtility.setValueWOValidation((Textbox) incDetails.query("#cmbOutputWarehouse"), "");
	}

	@Override
	public Integer recordFromForm(Include incDetails) throws Exception {
		
		Comboitem iWarehouseItem = ((Combobox) incDetails.query("#cmbInputWarehouse")).getSelectedItem();
		Comboitem oWarehouseItem =((Combobox) incDetails.query("#cmbOutputWarehouse")).getSelectedItem();
		return (new CostCenter(0,
								0,
								((Textbox) incDetails.query("#txtName")).getValue(),
								iWarehouseItem == null ? null : ((DomainObject) iWarehouseItem.getValue()).getId(),
								oWarehouseItem == null ? null : ((DomainObject) oWarehouseItem.getValue()).getId()))
								.record();
	}

	@Override
	public Integer modifyFromForm(Include incDetails) throws Exception {
		
		Comboitem iWarehouseItem = ((Combobox) incDetails.query("#cmbInputWarehouse")).getSelectedItem();
		Comboitem oWarehouseItem = ((Combobox) incDetails.query("#cmbOutputWarehouse")).getSelectedItem();
		Integer iWarehouseId = 0;
		Integer oWarehouseId = 0;
		if (iWarehouseItem != null) iWarehouseId = ((DomainObject) iWarehouseItem.getValue()).getId();
		if (oWarehouseItem != null) oWarehouseId = ((DomainObject) oWarehouseItem.getValue()).getId();		
		super.setTrackedObject(new CostCenter(((Intbox) incDetails.getParent().query("#intId")).getValue(),
												0,
												((Textbox) incDetails.query("#txtName")).getValue(),
												iWarehouseId,
												oWarehouseId));
		return super.getTrackedObject().modify();
	}
}
