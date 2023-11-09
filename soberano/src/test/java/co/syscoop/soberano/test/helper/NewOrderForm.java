package co.syscoop.soberano.test.helper;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Textbox;

public class NewOrderForm extends ConstrainedForm {
	
	private Textbox txtLabel;
	private Combobox cmbCustomer;
	private Grid grdCounters;
	private Button btnRecord;
	private Grid grd;

	public NewOrderForm(DesktopAgent desktop,
						Textbox txtLabel,
						Combobox cmbCustomer,
						Grid grdCounters,
						Button btnRecord,
						Grid grd) {
		
		this.setDesktop(desktop);		
		this.setTxtLabel(txtLabel);
		this.setCmbCustomer(cmbCustomer);
		this.setGrdCounters(grdCounters);
		this.setBtnRecord(btnRecord);
		this.setGrd(grd);
	}

	public Textbox getTxtLabel() {
		return txtLabel;
	}

	public void setTxtLabel(Textbox txtLabel) {
		this.txtLabel = txtLabel;
	}

	public Combobox getCmbCustomer() {
		return cmbCustomer;
	}

	public void setCmbCustomer(Combobox cmbCustomer) {
		this.cmbCustomer = cmbCustomer;
	}

	public Grid getGrdCounters() {
		return grdCounters;
	}

	public void setGrdCounters(Grid grdCounters) {
		this.grdCounters = grdCounters;
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
