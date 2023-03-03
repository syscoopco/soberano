package co.syscoop.soberano.test.helper;

import java.util.Arrays;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;

public class CostCenterForm extends ConstrainedForm {
	
	private Textbox txtName;
	private Combobox cmbInputWarehouse;
	private Combobox cmbOutputWarehouse;

	public CostCenterForm(DesktopAgent desktop,
						Textbox txtName,
						Combobox cmbInputWarehouse,
						Combobox cmbOutputWarehouse) {
		
		this.constrainedComponents = Arrays.asList("txtName",
													"cmbInputWarehouse",
													"cmbOutputWarehouse");
		
		this.setDesktop(desktop);
		
		this.setTxtName(txtName);
		this.constrainableComponents.add(txtName);
		this.constrainableComponentById.put("txtName", txtName);
		
		this.setCmbInputWarehouse(cmbInputWarehouse);
		this.constrainableComponents.add(cmbInputWarehouse);
		this.constrainableComponentById.put("cmbInputWarehouse", cmbInputWarehouse);
		
		this.setCmbOutputWarehouse(cmbOutputWarehouse);
		this.constrainableComponents.add(cmbOutputWarehouse);
		this.constrainableComponentById.put("cmbOutputWarehouse", cmbOutputWarehouse);
	}

	public Textbox getTxtName() {
		return txtName;
	}

	public void setTxtName(Textbox txtName) {
		this.txtName = txtName;
	}

	public Combobox getCmbInputWarehouse() {
		return cmbInputWarehouse;
	}

	public void setCmbInputWarehouse(Combobox cmbInputWarehouse) {
		this.cmbInputWarehouse = cmbInputWarehouse;
	}

	public Combobox getCmbOutputWarehouse() {
		return cmbOutputWarehouse;
	}

	public void setCmbOutputWarehouse(Combobox cmbOutputWarehouse) {
		this.cmbOutputWarehouse = cmbOutputWarehouse;
	}
}
