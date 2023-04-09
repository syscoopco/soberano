package co.syscoop.soberano.test.helper;

import java.util.Arrays;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zul.Textbox;

public class ServiceForm extends ConstrainedForm {
	
	private Textbox txtName;
	private Textbox txtCode;

	public ServiceForm(DesktopAgent desktop,
						Textbox txtName,
						Textbox txtCode) {
		
		this.constrainedComponents = Arrays.asList("txtName",
													"txtCode");
		
		this.setDesktop(desktop);
		
		this.setTxtName(txtName);
		this.constrainableComponents.add(txtName);
		this.constrainableComponentById.put("txtName", txtName);
		
		this.setTxtCode(txtCode);
		this.constrainableComponents.add(txtCode);
		this.constrainableComponentById.put("txtCode", txtCode);
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
}
