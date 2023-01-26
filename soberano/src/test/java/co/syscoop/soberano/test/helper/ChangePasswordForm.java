package co.syscoop.soberano.test.helper;

import java.util.Arrays;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zul.Textbox;

public class ChangePasswordForm extends ConstrainedForm {
	
	private Textbox txtPassword;
	private Textbox txtConfirmPassword;

	public ChangePasswordForm(DesktopAgent desktop,
							Textbox txtPassword,
							Textbox txtConfirmPassword) {

		this.constrainedComponents = Arrays.asList("txtPassword", "txtConfirmPassword");
		
		this.setDesktop(desktop);
		
		this.setTxtPassword(txtPassword);
		this.constrainableComponents.add(txtPassword);
		this.constrainableComponentById.put("txtPassword", txtPassword);
		
		this.setTxtConfirmPassword(txtConfirmPassword);
		this.constrainableComponents.add(txtConfirmPassword);
		this.constrainableComponentById.put("txtConfirmPassword", txtConfirmPassword);
	}

	public Textbox getTxtPassword() {
		return txtPassword;
	}

	public void setTxtPassword(Textbox txtPassword) {
		this.txtPassword = txtPassword;
	}

	public Textbox getTxtConfirmPassword() {
		return txtConfirmPassword;
	}

	public void setTxtConfirmPassword(Textbox txtConfirmPassword) {
		this.txtConfirmPassword = txtConfirmPassword;
	}
}
