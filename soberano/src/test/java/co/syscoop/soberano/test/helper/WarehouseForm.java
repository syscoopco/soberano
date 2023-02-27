package co.syscoop.soberano.test.helper;

import java.util.Arrays;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Textbox;

public class WarehouseForm extends ConstrainedForm {
	
	private Textbox txtName;
	private Textbox txtCode;
	private Checkbox chkProcurementWarehouse;
	private Checkbox chkSalesWarehouse;

	public WarehouseForm(DesktopAgent desktop,
						Textbox txtName,
						Textbox txtCode,
						Checkbox chkProcurementWarehouse,
						Checkbox chkSalesWarehouse) {
		
		this.constrainedComponents = Arrays.asList("txtName",
													"txtCode");
		
		this.setDesktop(desktop);
		
		this.setTxtName(txtName);
		this.constrainableComponents.add(txtName);
		this.constrainableComponentById.put("txtName", txtName);
		
		this.setTxtCode(txtCode);
		this.constrainableComponents.add(txtCode);
		this.constrainableComponentById.put("txtCode", txtCode);
		
		this.setChkProcurementWarehouse(chkProcurementWarehouse);
		this.setChkSalesWarehouse(chkSalesWarehouse);
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

	public Checkbox getChkProcurementWarehouse() {
		return chkProcurementWarehouse;
	}

	public void setChkProcurementWarehouse(Checkbox chkProcurementWarehouse) {
		this.chkProcurementWarehouse = chkProcurementWarehouse;
	}

	public Checkbox getChkSalesWarehouse() {
		return chkSalesWarehouse;
	}

	public void setChkSalesWarehouse(Checkbox chkSalesWarehouse) {
		this.chkSalesWarehouse = chkSalesWarehouse;
	}
}
