package co.syscoop.soberano.ui.helper;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.A;

import co.syscoop.soberano.domain.tracked.Product;
import co.syscoop.soberano.domain.tracked.ProductCategory;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.SomeFieldsContainWrongValuesException;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.util.StringIdCodeGenerator;
import co.syscoop.soberano.util.ui.ZKUtility;
import co.syscoop.soberano.view.viewmodel.CostCenterSelectionViewModel;
import co.syscoop.soberano.view.viewmodel.ProductCategorySelectionViewModel;
import co.syscoop.soberano.view.viewmodel.UnitSelectionViewModel;

public class ProductFormHelper extends TrackedObjectFormHelper {

	@Override
	public void fillForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException {
		
		Product product = new Product(((DomainObject) data.getData().getValue()).getId());
		product.get();
		
		//store in the form the ids of shown object for subsequent modification
		((Intbox) incDetails.getParent().query("#intId")).setValue(product.getId());
		((Textbox) incDetails.getParent().query("#txtStringId")).setText(product.getStringId());
		
		incDetails.setVisible(true);
		Clients.scrollIntoView(incDetails.query("#cmbCategory"));
		((Button) incDetails.getParent().query("#incSouth").query("#btnApply")).setDisabled(false);
		((Button) incDetails.getParent().query("#incSouth").query("#btnProcess")).setDisabled(false);
		
		Combobox cmbCategory = (Combobox) incDetails.query("#cmbCategory");
		ProductCategorySelectionViewModel pcSelectionViewModel = new ProductCategorySelectionViewModel();
		cmbCategory.setModel(pcSelectionViewModel.getModel());
		
		if (product.getProductCategoryIds().length > 0)
			ZKUtility.setValueWOValidation(cmbCategory, product.getProductCategoryIds()[0]);
		else
			(cmbCategory).setSelectedItem(null);
		
		ZKUtility.setValueWOValidation((Textbox) incDetails.query("#txtCode"), product.getStringId());
		ZKUtility.setValueWOValidation((Textbox) incDetails.query("#txtName"), product.getName());
		
		ZKUtility.setValueWOValidation((Intbox) incDetails.query("#intPosition"), product.getPosition());
		
		Combobox cmbUnit = (Combobox) incDetails.query("#cmbUnit");
		UnitSelectionViewModel uSelectionViewModel = new UnitSelectionViewModel();
		cmbUnit.setModel(uSelectionViewModel.getModel());
		
		if (product.getUnit() > 0) 
			ZKUtility.setValueWOValidation(cmbUnit, product.getUnit());
		else
			(cmbUnit).setSelectedItem(null);
		
		ZKUtility.setValueWOValidation((Decimalbox) incDetails.query("#decMinimumInventoryLevel"), product.getMinimumInventoryLevel());
		ZKUtility.setValueWOValidation((Decimalbox) incDetails.query("#decPrice"), product.getPrice());
		ZKUtility.setValueWOValidation((Decimalbox) incDetails.query("#decReferencePrice"), product.getReferencePrice());
		
		//testing purpose. this control isn't visible
		ZKUtility.setValueWOValidation((Decimalbox) incDetails.query("#decReferencePriceForTesting"), product.getReferencePrice());
		
		Combobox cmbCostCenter = (Combobox) incDetails.query("#cmbCostCenter");
		CostCenterSelectionViewModel ccSelectionViewModel = new CostCenterSelectionViewModel();
		cmbCostCenter.setModel(ccSelectionViewModel.getModel());
		
		if (product.getCostCenter() > 0) 
			ZKUtility.setValueWOValidation(cmbCostCenter, product.getCostCenter());
		else
			cmbCostCenter.setSelectedItem(null);
		
		((Checkbox) incDetails.query("#chkIsAnAddition")).setChecked(product.getIsAnAddition());
		
		if (product.getPicture() == null) {
			((Label) incDetails.query("#lblNoPicture")).setVisible(true);
			((A) incDetails.query("#aDownload")).setVisible(false);
		}
		else {
			((Label) incDetails.query("#lblNoPicture")).setVisible(false);
			((A) incDetails.query("#aDownload")).setVisible(true);
		}
		
		((Intbox) incDetails.query("#intProcessId")).setValue(product.getProcess());
	}

	@Override
	public void cleanForm(Include incDetails) {
		
		Clients.scrollIntoView(incDetails.query("#txtCode"));
		ZKUtility.setValueWOValidation((Textbox) incDetails.query("#txtCode"), new StringIdCodeGenerator().getTenCharsRandomString(""));
		ZKUtility.setValueWOValidation((Textbox) incDetails.query("#txtName"), "");
		ZKUtility.setValueWOValidation((Decimalbox) incDetails.query("#decPrice"), new BigDecimal(0.0));
		ZKUtility.setValueWOValidation((Decimalbox) incDetails.query("#decReferencePrice"), new BigDecimal(0.0));
	}

	@Override
	public Integer recordFromForm(Include incDetails) throws Exception {
		
		Comboitem iCategoryItem = ((Combobox) incDetails.query("#cmbCategory")).getSelectedItem();
		ProductCategory productCategory = new ProductCategory(((DomainObject) iCategoryItem.getValue()).getId());
		ArrayList<ProductCategory> productCategories = new ArrayList<ProductCategory>();		
		productCategories.add(productCategory);		
		Comboitem iUnitItem = ((Combobox) incDetails.query("#cmbUnit")).getSelectedItem();
		Integer iUnitId = 0;
		if (iUnitItem != null) 
			iUnitId = ((DomainObject) iUnitItem.getValue()).getId();
		else
			throw new SomeFieldsContainWrongValuesException(); 		
		Comboitem iCostCenterItem = ((Combobox) incDetails.query("#cmbCostCenter")).getSelectedItem();		
		return (new Product(0,
							0,
							((Textbox) incDetails.query("#txtCode")).getValue(),
							((Textbox) incDetails.query("#txtName")).getValue(),
							((Decimalbox) incDetails.query("#decPrice")).getValue(),
							((Decimalbox) incDetails.query("#decReferencePrice")).getValue(),							
							((Decimalbox) incDetails.query("#decMinimumInventoryLevel")).getValue(),
							iUnitId,
							iCostCenterItem == null ? null : ((DomainObject) iCostCenterItem.getValue()).getId(),
							true,
							0,
							((Intbox) incDetails.query("#intPosition")).getValue(),
							((Checkbox) incDetails.query("#chkIsAnAddition")).isChecked(),
							null,
							productCategories))
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
		Comboitem iCostCenterItem = ((Combobox) incDetails.query("#cmbCostCenter")).getSelectedItem();
		Integer iCostCenterId = 0;
		if (iCostCenterItem != null) iCostCenterId = ((DomainObject) iCostCenterItem.getValue()).getId();	
		
		Comboitem iCategoryItem = ((Combobox) incDetails.query("#cmbCategory")).getSelectedItem();
		ProductCategory productCategory = new ProductCategory(((DomainObject) iCategoryItem.getValue()).getId());
		ArrayList<ProductCategory> productCategories = new ArrayList<ProductCategory>();		
		productCategories.add(productCategory);	
		
		super.setTrackedObject(new Product(((Intbox) incDetails.getParent().query("#intId")).getValue(),
												0,
												((Textbox) incDetails.query("#txtCode")).getValue(),
												((Textbox) incDetails.query("#txtName")).getValue(),
												((Decimalbox) incDetails.query("#decPrice")).getValue(),
												((Decimalbox) incDetails.query("#decReferencePrice")).getValue(),							
												((Decimalbox) incDetails.query("#decMinimumInventoryLevel")).getValue(),
												iUnitId,
												iCostCenterId,
												true,
												0,
												((Intbox) incDetails.query("#intPosition")).getValue(),
												((Checkbox) incDetails.query("#chkIsAnAddition")).isChecked(),
												null,
												productCategories));
		return super.getTrackedObject().modify();
	}
}
