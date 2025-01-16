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
import co.syscoop.soberano.exception.SomeFieldsContainWrongValuesException;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.util.StringIdCodeGenerator;
import co.syscoop.soberano.util.ui.ZKUtility;
import co.syscoop.soberano.view.viewmodel.UnitSelectionViewModel;

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
		
		ZKUtility.setValueWOValidation((Textbox) incDetails.query("#txtCode"), acquirableMaterial.getStringId());
		ZKUtility.setValueWOValidation((Textbox) incDetails.query("#txtName"), acquirableMaterial.getName());
		
		Combobox cmbUnit = (Combobox) incDetails.query("#cmbUnit");
		UnitSelectionViewModel uSelectionViewModel = new UnitSelectionViewModel();
		cmbUnit.setModel(uSelectionViewModel.getModel());
		
		if (acquirableMaterial.getUnit() > 0) 
			ZKUtility.setValueWOValidation(cmbUnit, acquirableMaterial.getUnit());
		else
			(cmbUnit).setSelectedItem(null);
			
		ZKUtility.setValueWOValidation((Decimalbox) incDetails.query("#decMinimumInventoryLevel"), acquirableMaterial.getMinimumInventoryLevel());
	}

	@Override
	public void cleanForm(Include incDetails) {
		
		Clients.scrollIntoView(incDetails.query("#txtCode"));
		ZKUtility.setValueWOValidation((Textbox) incDetails.query("#txtCode"), new StringIdCodeGenerator().getTenCharsRandomString(""));
		ZKUtility.setValueWOValidation((Textbox) incDetails.query("#txtName"), "");
		ZKUtility.setValueWOValidation((Decimalbox) incDetails.query("#decMinimumInventoryLevel"), new BigDecimal(0.0));
		ZKUtility.setValueWOValidation((Textbox) incDetails.query("#cmbUnit"), "");		
	}

	@Override
	public Integer recordFromForm(Include incDetails) throws Exception {
		
		Comboitem iUnitItem = ((Combobox) incDetails.query("#cmbUnit")).getSelectedItem();
		Integer iUnitId = 0;
		if (iUnitItem != null) 
			iUnitId = ((DomainObject) iUnitItem.getValue()).getId();
		else
			throw new SomeFieldsContainWrongValuesException(); 
		return (new AcquirableMaterial(0,
										0,
										((Textbox) incDetails.query("#txtCode")).getValue(),
										((Textbox) incDetails.query("#txtName")).getValue(),
										((Decimalbox) incDetails.query("#decMinimumInventoryLevel")).getValue(),
										iUnitId))
									.record();
	}

	@Override
	public Integer modifyFromForm(Include incDetails) throws Exception {
		
		Comboitem iUnitItem = ((Combobox) incDetails.query("#cmbUnit")).getSelectedItem();
		Integer iUnitId = 0;
		if (iUnitItem != null) 
			iUnitId = ((DomainObject) iUnitItem.getValue()).getId();
		else
			throw new SomeFieldsContainWrongValuesException(); 
		super.setTrackedObject(new AcquirableMaterial(((Intbox) incDetails.getParent().query("#intId")).getValue(),
												0,
												((Textbox) incDetails.query("#txtCode")).getValue(),
												((Textbox) incDetails.query("#txtName")).getValue(),
												((Decimalbox) incDetails.query("#decMinimumInventoryLevel")).getValue(),
												iUnitId));
		return super.getTrackedObject().modify();
	}
}
