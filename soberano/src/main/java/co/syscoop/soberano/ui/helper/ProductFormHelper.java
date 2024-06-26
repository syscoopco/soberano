package co.syscoop.soberano.ui.helper;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.domain.tracked.Product;
import co.syscoop.soberano.domain.tracked.ProductCategory;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.util.StringIdCodeGenerator;
import co.syscoop.soberano.util.ui.ZKUtilitity;

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
		
		if (product.getProductCategoryIds().length > 0)
			ZKUtilitity.setValueWOValidation((Combobox) incDetails.query("#cmbCategory"), product.getProductCategoryIds()[0]);
		else
			((Combobox) incDetails.query("#cmbCategory")).setSelectedItem(null);
		
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtCode"), product.getStringId());
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtName"), product.getName());
		
		if (product.getUnit() > 0) 
			ZKUtilitity.setValueWOValidation((Combobox) incDetails.query("#cmbUnit"), product.getUnit());
		else
			((Combobox) incDetails.query("#cmbUnit")).setSelectedItem(null);
		
		ZKUtilitity.setValueWOValidation((Decimalbox) incDetails.query("#decMinimumInventoryLevel"), product.getMinimumInventoryLevel());
		ZKUtilitity.setValueWOValidation((Decimalbox) incDetails.query("#decPrice"), product.getPrice());
		ZKUtilitity.setValueWOValidation((Decimalbox) incDetails.query("#decReferencePrice"), product.getReferencePrice());
		
		//testing purpose. this control isn't visible
		ZKUtilitity.setValueWOValidation((Decimalbox) incDetails.query("#decReferencePriceForTesting"), product.getReferencePrice());
				
		if (product.getCostCenter() > 0) 
			ZKUtilitity.setValueWOValidation((Combobox) incDetails.query("#cmbCostCenter"), product.getCostCenter());
		else
			((Combobox) incDetails.query("#cmbCostCenter")).setSelectedItem(null);
		
		((Intbox) incDetails.query("#intProcessId")).setValue(product.getProcess());
	}

	@Override
	public void cleanForm(Include incDetails) {
		
		Clients.scrollIntoView(incDetails.query("#txtCode"));
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtCode"), new StringIdCodeGenerator().getTenCharsRandomString(""));
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtName"), "");
		ZKUtilitity.setValueWOValidation((Decimalbox) incDetails.query("#decPrice"), new BigDecimal(0.0));
		ZKUtilitity.setValueWOValidation((Decimalbox) incDetails.query("#decReferencePrice"), new BigDecimal(0.0));
	}

	@Override
	public Integer recordFromForm(Include incDetails) throws Exception {
		
		Comboitem iCategoryItem = ((Combobox) incDetails.query("#cmbCategory")).getSelectedItem();
		ProductCategory productCategory = new ProductCategory(((DomainObject) iCategoryItem.getValue()).getId());
		ArrayList<ProductCategory> productCategories = new ArrayList<ProductCategory>();		
		productCategories.add(productCategory);		
		Comboitem iUnitItem = ((Combobox) incDetails.query("#cmbUnit")).getSelectedItem();
		Comboitem iCostCenterItem = ((Combobox) incDetails.query("#cmbCostCenter")).getSelectedItem();		
		return (new Product(0,
							0,
							((Textbox) incDetails.query("#txtCode")).getValue(),
							((Textbox) incDetails.query("#txtName")).getValue(),
							((Decimalbox) incDetails.query("#decPrice")).getValue(),
							((Decimalbox) incDetails.query("#decReferencePrice")).getValue(),							
							((Decimalbox) incDetails.query("#decMinimumInventoryLevel")).getValue(),
							iUnitItem == null ? null : ((DomainObject) iUnitItem.getValue()).getId(),
							iCostCenterItem == null ? null : ((DomainObject) iCostCenterItem.getValue()).getId(),
							true,
							0,
							productCategories))
						.record();
	}

	@Override
	public Integer modifyFromForm(Include incDetails) throws Exception {
		
		Comboitem iUnitItem = ((Combobox) incDetails.query("#cmbUnit")).getSelectedItem();
		Integer iUnitId = 0;
		if (iUnitItem != null) iUnitId = ((DomainObject) iUnitItem.getValue()).getId();	
		
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
												productCategories));
		return super.getTrackedObject().modify();
	}
}
