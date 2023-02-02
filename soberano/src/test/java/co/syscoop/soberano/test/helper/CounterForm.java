package co.syscoop.soberano.test.helper;

import java.util.Arrays;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;

public class CounterForm extends ConstrainedForm {
	
	private Textbox txtCode;
	private Intbox intNumberOfReceivers;
	private Checkbox chkIsSurcharged;
	private Checkbox chkDisabled;

	public CounterForm(DesktopAgent desktop,
						Textbox txtCode,
						Intbox intNumberOfReceivers,
						Checkbox chkIsSurcharged,
						Checkbox chkDisabled) {
		
		this.constrainedComponents = Arrays.asList("txtCode",
													"intNumberOfReceivers");
		
		this.setDesktop(desktop);
		
		this.setTxtCode(txtCode);
		this.constrainableComponents.add(txtCode);
		this.constrainableComponentById.put("txtCode", txtCode);
		
		this.setIntNumberOfReceivers(intNumberOfReceivers);
		this.constrainableComponents.add(intNumberOfReceivers);
		this.constrainableComponentById.put("intNumberOfReceivers", intNumberOfReceivers);
		
		this.setChkIsSurcharged(chkIsSurcharged);
		this.setChkDisabled(chkDisabled);
	}

	public Textbox getTxtCode() {
		return txtCode;
	}

	public void setTxtCode(Textbox txtCode) {
		this.txtCode = txtCode;
	}

	public Intbox getIntNumberOfReceivers() {
		return intNumberOfReceivers;
	}

	public void setIntNumberOfReceivers(Intbox intNumberOfReceivers) {
		this.intNumberOfReceivers = intNumberOfReceivers;
	}

	public Checkbox getChkIsSurcharged() {
		return chkIsSurcharged;
	}

	public void setChkIsSurcharged(Checkbox chkIsSurcharged) {
		this.chkIsSurcharged = chkIsSurcharged;
	}

	public Checkbox getChkDisabled() {
		return chkDisabled;
	}

	public void setChkDisabled(Checkbox chkDisabled) {
		this.chkDisabled = chkDisabled;
	}
}
