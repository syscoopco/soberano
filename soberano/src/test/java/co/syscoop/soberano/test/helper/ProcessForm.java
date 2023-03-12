package co.syscoop.soberano.test.helper;

import java.util.Arrays;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Textbox;

public class ProcessForm extends ConstrainedForm {
	
	private Textbox txtName;
	private Decimalbox decFixedCost;

	public ProcessForm(DesktopAgent desktop,
						Textbox txtName,
						Decimalbox decFixedCost) {
		
		this.constrainedComponents = Arrays.asList("txtName",
													"decFixedCost");
		
		this.setDesktop(desktop);
		
		this.setTxtName(txtName);
		this.constrainableComponents.add(txtName);
		this.constrainableComponentById.put("txtName", txtName);
		
		this.setDecFixedCost(decFixedCost);
		this.constrainableComponents.add(decFixedCost);
		this.constrainableComponentById.put("decFixedCost", decFixedCost);
	}

	public Decimalbox getDecFixedCost() {
		return decFixedCost;
	}

	public void setDecFixedCost(Decimalbox decFixedCost) {
		this.decFixedCost = decFixedCost;
	}

	public Textbox getTxtName() {
		return txtName;
	}

	public void setTxtName(Textbox txtName) {
		this.txtName = txtName;
	}
}
