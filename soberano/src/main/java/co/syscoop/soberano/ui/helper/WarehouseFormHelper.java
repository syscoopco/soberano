package co.syscoop.soberano.ui.helper;

import java.sql.SQLException;

import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import co.syscoop.soberano.domain.tracked.Warehouse;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.util.ZKUtilitity;

public class WarehouseFormHelper extends TrackedObjectFormHelper {
	
	@Override
	public void fillForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException {
		
		Warehouse warehouse = new Warehouse(((DomainObject) data.getData().getValue()).getId());
		warehouse.get();
		
		//store in the form the ids of shown object for subsequent modification
		((Intbox) incDetails.getParent().query("#intId")).setValue(warehouse.getId());
		((Textbox) incDetails.getParent().query("#txtStringId")).setText(warehouse.getStringId());
		
		incDetails.setVisible(true);
		Clients.scrollIntoView(incDetails.query("#txtCode"));
		((Button) incDetails.getParent().query("#incSouth").query("#btnApply")).setDisabled(false);
		
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtName"), warehouse.getName());
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtCode"), warehouse.getStringId());
		
		((Checkbox) incDetails.query("#chkProcurementWarehouse")).setChecked(warehouse.getIsProcurementWarehouse());
		((Checkbox) incDetails.query("#chkSalesWarehouse")).setChecked(warehouse.getIsSalesWarehouse());
	}

	@Override
	public void cleanForm(Include incDetails) {
		
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtCode"), "");
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtName"), "");
		((Checkbox) incDetails.query("#chkProcurementWarehouse")).setChecked(false);
		((Checkbox) incDetails.query("#chkSalesWarehouse")).setChecked(false);
	}

	@Override
	public Integer recordFromForm(Include incDetails) throws Exception {
		
		return (new Warehouse(0,
							0,
							((Textbox) incDetails.query("#txtName")).getValue(),
							((Textbox) incDetails.query("#txtCode")).getValue(),
							((Checkbox) incDetails.query("#chkProcurementWarehouse")).isChecked(),
							((Checkbox) incDetails.query("#chkSalesWarehouse")).isChecked()))
					.record();
	}

	@Override
	public Integer modifyFromForm(Include incDetails) throws Exception {
		
		super.setTrackedObject(new Warehouse(((Intbox) incDetails.getParent().query("#intId")).getValue(),
											0,
											((Textbox) incDetails.query("#txtName")).getValue(),
											((Textbox) incDetails.query("#txtCode")).getValue(),
											((Checkbox) incDetails.query("#chkProcurementWarehouse")).isChecked(),
											((Checkbox) incDetails.query("#chkSalesWarehouse")).isChecked()));
		return super.getTrackedObject().modify();
	}
}
