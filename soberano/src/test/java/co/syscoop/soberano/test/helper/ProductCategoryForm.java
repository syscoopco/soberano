package co.syscoop.soberano.test.helper;

import java.util.Arrays;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;

public class ProductCategoryForm extends ConstrainedForm {
	
	private Textbox txtName;
	private Intbox intPosition;
	private Checkbox chkDisabled;

	public ProductCategoryForm(DesktopAgent desktop,
						Textbox txtName,
						Intbox intPosition,
						Checkbox chkDisabled) {
		
		this.constrainedComponents = Arrays.asList("txtName",
													"intPosition");
		
		this.setDesktop(desktop);
		
		this.setTxtName(txtName);
		this.constrainableComponents.add(txtName);
		this.constrainableComponentById.put("txtName", txtName);
		
		this.setIntPosition(intPosition);
		this.constrainableComponents.add(intPosition);
		this.constrainableComponentById.put("intPosition", intPosition);
		
		this.setChkDisabled(chkDisabled);
	}

	public Textbox getTxtName() {
		return txtName;
	}

	public void setTxtName(Textbox txtName) {
		this.txtName = txtName;
	}

	public Intbox getIntPosition() {
		return intPosition;
	}

	public void setIntPosition(Intbox intPosition) {
		this.intPosition = intPosition;
	}

	public Checkbox getChkDisabled() {
		return chkDisabled;
	}

	public void setChkDisabled(Checkbox chkDisabled) {
		this.chkDisabled = chkDisabled;
	}
}
