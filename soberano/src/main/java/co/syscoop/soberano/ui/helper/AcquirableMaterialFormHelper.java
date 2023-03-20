package co.syscoop.soberano.ui.helper;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.domain.tracked.AcquirableMaterial;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.util.StringIdCodeGenerator;
import co.syscoop.soberano.util.ZKUtilitity;

public class AcquirableMaterialFormHelper extends TrackedObjectFormHelper {

	@Override
	public void fillForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException {
		
		AcquirableMaterial acquirableMaterial = new AcquirableMaterial(((DomainObject) data.getData().getValue()).getId());
		acquirableMaterial.get();
		
		//store in the form the ids of shown object for subsequent modification
		((Intbox) incDetails.getParent().query("#intId")).setValue(acquirableMaterial.getId());
		((Textbox) incDetails.getParent().query("#txtStringId")).setText(acquirableMaterial.getStringId());
		
		incDetails.setVisible(true);
		Clients.scrollIntoView(incDetails.query("#txtCode"));
		((Button) incDetails.getParent().query("#incSouth").query("#btnApply")).setDisabled(false);
		
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtCode"), acquirableMaterial.getStringId());
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtName"), acquirableMaterial.getName());
		
		if (acquirableMaterial.getUnit() > 0) 
			ZKUtilitity.setValueWOValidation((Combobox) incDetails.query("#cmbUnit"), acquirableMaterial.getUnit());
		else
			((Combobox) incDetails.query("#cmbUnit")).setSelectedItem(null);
			
		ZKUtilitity.setValueWOValidation((Decimalbox) incDetails.query("#decMinimumInventoryLevel"), acquirableMaterial.getMinimumInventoryLevel());
	}

	@Override
	public void cleanForm(Include incDetails) {
		
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtCode"), new StringIdCodeGenerator().getTenCharsRandomString(""));
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtName"), "");
		ZKUtilitity.setValueWOValidation((Decimalbox) incDetails.query("#decMinimumInventoryLevel"), new BigDecimal(0.0));
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#cmbUnit"), "");		
	}

	@Override
	public Integer recordFromForm(Include incDetails) throws Exception {
		
		Comboitem iUnitItem = ((Combobox) incDetails.query("#cmbUnit")).getSelectedItem();
		return (new AcquirableMaterial(0,
										0,
										((Textbox) incDetails.query("#txtCode")).getValue(),
										((Textbox) incDetails.query("#txtName")).getValue(),
										((Decimalbox) incDetails.query("#decMinimumInventoryLevel")).getValue(),
										iUnitItem == null ? null : ((DomainObject) iUnitItem.getValue()).getId()))
									.record();
	}

	@Override
	public Integer modifyFromForm(Include incDetails) throws Exception {
		
		Comboitem iUnitItem = ((Combobox) incDetails.query("#cmbUnit")).getSelectedItem();
		Integer iUnitId = 0;
		if (iUnitItem != null) iUnitId = ((DomainObject) iUnitItem.getValue()).getId();	
		super.setTrackedObject(new AcquirableMaterial(((Intbox) incDetails.getParent().query("#intId")).getValue(),
												0,
												((Textbox) incDetails.query("#txtCode")).getValue(),
												((Textbox) incDetails.query("#txtName")).getValue(),
												((Decimalbox) incDetails.query("#decMinimumInventoryLevel")).getValue(),
												iUnitId));
		return super.getTrackedObject().modify();
	}
}
