package co.syscoop.soberano.test.helper;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;

public class PrintTicketForm extends ConstrainedForm {
	
	protected Intbox intOrderNumber;
	protected Button btnRetrieve;
	protected Textbox txtReport;
	protected Button btnReopen;

	public PrintTicketForm(DesktopAgent desktop,
							Intbox intOrderNumber,
							Button btnRetrieve,
							Textbox txtReport,
							Button btnReopen) {
		
		this.setDesktop(desktop);
		this.setIntOrderNumber(intOrderNumber);
		this.setBtnRetrieve(btnRetrieve);
		this.setTxtReport(txtReport);
		this.setBtnReopen(btnReopen);
	}
	
	public void setIntOrderNumber(Intbox intOrderNumber) {
		this.intOrderNumber = intOrderNumber;
	}
	
	public Intbox getIntOrderNumber() {
		return this.intOrderNumber;
	}
	
	public void setBtnRetrieve(Button btnRetrieve) {
		this.btnRetrieve = btnRetrieve;
	}
	
	public Button getBtnRetrieve() {
		return this.btnRetrieve;
	}
	
	public void setTxtReport(Textbox txtReport) {
		this.txtReport = txtReport;
	}
	
	public Textbox getTxtReport() {
		return this.txtReport;
	}
	
	public void setBtnReopen(Button btnReopen) {
		this.btnReopen = btnReopen;
	}
	
	public Button getBtnReopen() {
		return this.btnReopen;
	}
}
