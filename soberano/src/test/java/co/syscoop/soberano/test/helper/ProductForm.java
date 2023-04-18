package co.syscoop.soberano.test.helper;

import java.util.Arrays;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Textbox;

public class ProductForm extends ConstrainedForm {
	
	private Textbox txtName;
	private Textbox txtCode;
	private Combobox cmbUnit;
	private Decimalbox decMinimumInventoryLevel;
	private Combobox cmbCategory;	
	private Decimalbox decPrice;
	private Decimalbox decReferencePriceExchangeRate;
	private Decimalbox decReferencePrice;
	private Combobox cmbCostCenter;

	public ProductForm(DesktopAgent desktop,
						Textbox txtName,
						Textbox txtCode,
						Combobox cmbUnit,
						Decimalbox decMinimumInventoryLevel,
						Combobox cmbCategory,
						Decimalbox decPrice,
						Decimalbox decReferencePriceExchangeRate,
						Decimalbox decReferencePrice,
						Combobox cmbCostCenter) {
		
		this.constrainedComponents = Arrays.asList("txtName",
													"txtCode",
													"cmbUnit",
													"decMinimumInventoryLevel",
													"cmbCategory",
													"decPrice",
													"decReferencePrice",
													"cmbCostCenter");
		
		this.setDesktop(desktop);
		
		this.setTxtName(txtName);
		this.constrainableComponents.add(txtName);
		this.constrainableComponentById.put("txtName", txtName);
		
		this.setTxtCode(txtCode);
		this.constrainableComponents.add(txtCode);
		this.constrainableComponentById.put("txtCode", txtCode);
		
		this.setCmbUnit(cmbUnit);
		this.constrainableComponents.add(cmbUnit);
		this.constrainableComponentById.put("cmbUnit", cmbUnit);
		
		this.setDecMinimumInventoryLevel(decMinimumInventoryLevel);
		this.constrainableComponents.add(decMinimumInventoryLevel);
		this.constrainableComponentById.put("decMinimumInventoryLevel", decMinimumInventoryLevel);
		
		this.setCmbCategory(cmbCategory);
		this.constrainableComponents.add(cmbCategory);
		this.constrainableComponentById.put("cmbCategory", cmbCategory);
		
		this.setDecPrice(decPrice);
		this.constrainableComponents.add(decPrice);
		this.constrainableComponentById.put("decPrice", decPrice);
		
		this.setDecReferencePrice(decReferencePrice);
		this.constrainableComponents.add(decReferencePrice);
		this.constrainableComponentById.put("decReferencePrice", decReferencePrice);
		
		this.setCmbCostCenter(cmbCostCenter);
		this.constrainableComponents.add(cmbCostCenter);
		this.constrainableComponentById.put("cmbCostCenter", cmbCostCenter);
		
		this.setDecReferencePriceExchangeRate(decReferencePriceExchangeRate);
	}

	public Textbox getTxtName() {
		return txtName;
	}

	public void setTxtName(Textbox txtName) {
		this.txtName = txtName;
	}

	public Textbox getTxtCode() {
		return txtCode;
	}

	public void setTxtCode(Textbox txtCode) {
		this.txtCode = txtCode;
	}

	public Decimalbox getDecMinimumInventoryLevel() {
		return decMinimumInventoryLevel;
	}

	public void setDecMinimumInventoryLevel(Decimalbox decMinimumInventoryLevel) {
		this.decMinimumInventoryLevel = decMinimumInventoryLevel;
	}

	public Combobox getCmbUnit() {
		return cmbUnit;
	}

	public void setCmbUnit(Combobox cmbUnit) {
		this.cmbUnit = cmbUnit;
	}

	public Combobox getCmbCategory() {
		return cmbCategory;
	}

	public void setCmbCategory(Combobox cmbCategory) {
		this.cmbCategory = cmbCategory;
	}

	public Decimalbox getDecPrice() {
		return decPrice;
	}

	public void setDecPrice(Decimalbox decPrice) {
		this.decPrice = decPrice;
	}

	public Decimalbox getDecReferencePriceExchangeRate() {
		return decReferencePriceExchangeRate;
	}

	public void setDecReferencePriceExchangeRate(Decimalbox decReferencePriceExchangeRate) {
		this.decReferencePriceExchangeRate = decReferencePriceExchangeRate;
	}

	public Decimalbox getDecReferencePrice() {
		return decReferencePrice;
	}

	public void setDecReferencePrice(Decimalbox decReferencePrice) {
		this.decReferencePrice = decReferencePrice;
	}

	public Combobox getCmbCostCenter() {
		return cmbCostCenter;
	}

	public void setCmbCostCenter(Combobox cmbCostCenter) {
		this.cmbCostCenter = cmbCostCenter;
	}
}
