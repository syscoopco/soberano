package co.syscoop.soberano.test.helper;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Textbox;

public class ServiceExpenseForm extends ConstrainedForm {
	
	private Datebox dateExpenseDate;
	private Combobox cmbProvider;
	private Combobox cmbService;
	private Decimalbox decAmount;
	private Combobox cmbCurrency;
	private Textbox txtReference;
	private Button btnRecord;
	private Grid grd;

	public ServiceExpenseForm(DesktopAgent desktop,
							Datebox dateExpenseDate,
							Combobox cmbProvider,
							Combobox cmbService,
							Decimalbox decAmount,
							Combobox cmbCurrency,
							Textbox txtReference,
							Button btnRecord,
							Grid grd) {
		
		this.setDesktop(desktop);
		this.dateExpenseDate = dateExpenseDate;
		this.cmbProvider = cmbProvider;
		this.cmbService = cmbService;
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

	public Combobox getcmbService() {
		return cmbService;
	}

	public void setcmbService(Combobox cmbService) {
		this.cmbService = cmbService;
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
