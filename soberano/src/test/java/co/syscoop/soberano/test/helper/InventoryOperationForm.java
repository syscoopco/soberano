package co.syscoop.soberano.test.helper;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;

public class InventoryOperationForm extends ConstrainedForm {
	
	private Combobox cmbFromWarehouse;
	private Combobox cmbToWarehouse;
	private Combobox cmbWorker;
	private Combobox cmbMaterial;
	private Decimalbox decQuantity;
	private Combobox cmbUnit;
	private Button btnRecord;
	private Grid grd;

	public InventoryOperationForm(DesktopAgent desktop,
									Combobox cmbFromWarehouse,
									Combobox cmbToWarehouse,
									Combobox cmbWorker,
									Combobox cmbMaterial,
									Decimalbox decQuantity,
									Combobox cmbUnit,
									Button btnRecord,
									Grid grd) {
		
		this.setDesktop(desktop);
		this.setCmbFromWarehouse(cmbFromWarehouse);
		this.setCmbToWarehouse(cmbToWarehouse);
		this.setCmbWorker(cmbWorker);
		this.setCmbMaterial(cmbMaterial);
		this.setDecQuantity(decQuantity);
		this.setCmbUnit(cmbUnit);
		this.btnRecord = btnRecord;
		this.grd = grd;
	}

	public Grid getGrd() {
		return grd;
	}

	public void setGrd(Grid grd) {
		this.grd = grd;
	}

	public Combobox getCmbFromWarehouse() {
		return cmbFromWarehouse;
	}

	public void setCmbFromWarehouse(Combobox cmbFromWarehouse) {
		this.cmbFromWarehouse = cmbFromWarehouse;
	}

	public Combobox getCmbToWarehouse() {
		return cmbToWarehouse;
	}

	public void setCmbToWarehouse(Combobox cmbToWarehouse) {
		this.cmbToWarehouse = cmbToWarehouse;
	}

	public Combobox getCmbWorker() {
		return cmbWorker;
	}

	public void setCmbWorker(Combobox cmbWorker) {
		this.cmbWorker = cmbWorker;
	}

	public Button getBtnRecord() {
		return btnRecord;
	}

	public void setBtnRecord(Button btnRecord) {
		this.btnRecord = btnRecord;
	}

	public Combobox getCmbMaterial() {
		return cmbMaterial;
	}

	public void setCmbMaterial(Combobox cmbMaterial) {
		this.cmbMaterial = cmbMaterial;
	}

	public Decimalbox getDecQuantity() {
		return decQuantity;
	}

	public void setDecQuantity(Decimalbox decQuantity) {
		this.decQuantity = decQuantity;
	}

	public Combobox getCmbUnit() {
		return cmbUnit;
	}

	public void setCmbUnit(Combobox cmbUnit) {
		this.cmbUnit = cmbUnit;
	}
}
