package co.syscoop.soberano.test.helper;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Textbox;

public class MaterialExpenseForm extends ConstrainedForm {
	
	private Datebox dateExpenseDate;
	private Combobox cmbProvider;
	private Combobox cmbMaterial;
	private Decimalbox decQuantity;
	private Combobox cmbUnit;	
	private Decimalbox decAmount;
	private Combobox cmbCurrency;
	private Textbox txtReference;
	private Button btnRecord;
	private Grid grd;

	public MaterialExpenseForm(DesktopAgent desktop,
							Datebox dateExpenseDate,
							Combobox cmbProvider,
							Combobox cmbMaterial,
							Decimalbox decQuantity,
							Combobox cmbUnit,
							Decimalbox decAmount,
							Combobox cmbCurrency,
							Textbox txtReference,
							Button btnRecord,
							Grid grd) {
		
		this.setDesktop(desktop);
		this.dateExpenseDate = dateExpenseDate;
		this.cmbProvider = cmbProvider;
		this.cmbMaterial = cmbMaterial;
		this.decQuantity = decQuantity;
		this.cmbUnit = cmbUnit;
		this.decAmount = decAmount;
		this.cmbCurrency = cmbCurrency;
		this.txtReference = txtReference;
		this.btnRecord = btnRecord;
		this.grd = grd;
	}

	public Datebox getDateExpenseDate() {
		return dateExpenseDate;
	}

	public void setDateExpenseDate(Datebox dateExpenseDate) {
		this.dateExpenseDate = dateExpenseDate;
	}

	public Combobox getCmbProvider() {
		return cmbProvider;
	}

	public void setCmbProvider(Combobox cmbProvider) {
		this.cmbProvider = cmbProvider;
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

	public Decimalbox getDecAmount() {
		return decAmount;
	}

	public void setDecAmount(Decimalbox decAmount) {
		this.decAmount = decAmount;
	}

	public Combobox getCmbCurrency() {
		return cmbCurrency;
	}

	public void setCmbCurrency(Combobox cmbCurrency) {
		this.cmbCurrency = cmbCurrency;
	}

	public Textbox getTxtReference() {
		return txtReference;
	}

	public void setTxtReference(Textbox txtReference) {
		this.txtReference = txtReference;
	}

	public Button getBtnRecord() {
		return btnRecord;
	}

	public void setBtnRecord(Button btnRecord) {
		this.btnRecord = btnRecord;
	}

	public Grid getGrd() {
		return grd;
	}

	public void setGrd(Grid grd) {
		this.grd = grd;
	}
}
