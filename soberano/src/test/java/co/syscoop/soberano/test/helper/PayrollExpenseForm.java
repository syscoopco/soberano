package co.syscoop.soberano.test.helper;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Textbox;

public class PayrollExpenseForm extends ConstrainedForm {
	
	private Datebox dateExpenseDate;
	private Combobox cmbWorker;
	private Decimalbox decAmount;
	private Combobox cmbCurrency;
	private Textbox txtReference;
	private Button btnRecord;
	private Grid grd;

	public PayrollExpenseForm(DesktopAgent desktop,
							Datebox dateExpenseDate,
							Combobox cmbWorker,
							Decimalbox decAmount,
							Combobox cmbCurrency,
							Textbox txtReference,
							Button btnRecord,
							Grid grd) {
		
		this.setDesktop(desktop);
		this.dateExpenseDate = dateExpenseDate;
		this.cmbWorker = cmbWorker;
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

	public Combobox getCmbWorker() {
		return cmbWorker;
	}

	public void setCmbWorker(Combobox cmbWorker) {
		this.cmbWorker = cmbWorker;
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
