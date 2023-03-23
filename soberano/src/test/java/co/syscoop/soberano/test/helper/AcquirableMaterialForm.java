package co.syscoop.soberano.test.helper;

import java.util.Arrays;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Textbox;

public class AcquirableMaterialForm extends ConstrainedForm {
	
	private Textbox txtName;
	private Textbox txtCode;
	private Combobox cmbUnit;
	private Decimalbox decMinimumInventoryLevel;

	public AcquirableMaterialForm(DesktopAgent desktop,
						Textbox txtName,
						Textbox txtCode,
						Combobox cmbUnit,
						Decimalbox decMinimumInventoryLevel) {
		
		this.constrainedComponents = Arrays.asList("txtName",
													"txtCode",
													"cmbUnit",
													"decMinimumInventoryLevel");
		
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
}
