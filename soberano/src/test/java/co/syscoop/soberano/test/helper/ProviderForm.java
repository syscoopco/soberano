package co.syscoop.soberano.test.helper;

import java.util.Arrays;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zul.Textbox;

public class ProviderForm extends ConstrainedForm {
	
	private Textbox txtName;

	public ProviderForm(DesktopAgent desktop,
						Textbox txtName) {
		
		this.constrainedComponents = Arrays.asList("txtName");
		
		this.setDesktop(desktop);
		
		this.setTxtName(txtName);
		this.constrainableComponents.add(txtName);
		this.constrainableComponentById.put("txtName", txtName);
	}

	public Textbox getTxtName() {
		return txtName;
	}

	public void setTxtName(Textbox txtName) {
		this.txtName = txtName;
	}
}
