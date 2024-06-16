package co.syscoop.soberano.test.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import co.syscoop.soberano.util.SpringUtility;

public class ProcessActionTest extends ActionTest {
	
	protected static Textbox txtName = null;
	protected static Decimalbox decFixedCost = null;
	
	protected static ProcessForm setFormComponents(String user, String formZulFilename) {
		
		SpringUtility.setLoggedUserForTesting(user);
		DesktopAgent desktop = Zats.newClient().connect("/" + formZulFilename);
		cmbIntelliSearchAgent = desktop.query("center").query("combobox");
		cmbIntelliSearch = cmbIntelliSearchAgent.as(Combobox.class);
		ProcessForm processForm = new ProcessForm(desktop, 
												cmbIntelliSearchAgent.query("#incDetails").query("#txtName").as(Textbox.class), 
												cmbIntelliSearchAgent.query("#incDetails").query("#decFixedCost").as(Decimalbox.class));
		return processForm;
	}
	
	protected void checkProcess(String name,
								BigDecimal fixedCost) {
		
		String qualifiedName = name;
		loadObjectDetails(qualifiedName);
		
		assertEquals(name.toLowerCase(), txtName.getText().toLowerCase(), "Wrong name shown for process " +  qualifiedName);
		assertEquals((fixedCost.subtract(decFixedCost.getValue().abs()).compareTo(new BigDecimal(0.00000001)) <= 0), true, "Wrong fixed cost shown for process " +  qualifiedName);
	}
	
	protected void checkProcess(ProcessForm processForm,
								String name,
								BigDecimal fixedCost) {

		String qualifiedName = name;
		loadObjectDetails(qualifiedName);
	
		assertEquals(name.toLowerCase(), processForm.getTxtName().getText().toLowerCase(), "Wrong name shown for process " +  qualifiedName);
		assertEquals(fixedCost.subtract(processForm.getDecFixedCost().getValue()).abs().doubleValue() < 0.00000001, true, "Wrong fixed cost shown for process " +  qualifiedName);
	}
	
	protected void selectComboitemByLabel(Combobox comp, String label) {
		
		try {
			for (Component co : comp.getChildren()) {
				Comboitem item = (Comboitem) co;
				if (item.getLabel().equals(label)) {
					comp.setSelectedItem(item);
					break;
				}
			}
		} 
		catch(Exception ex) 
		{
			/*This is to, under testing, avoid halting cause java.lang.IllegalStateException
			with detailMessage: Components can be accessed only in event listeners.
			Line 305 in ZK UiEngineImpl.java file*/
		}
	}
	
	protected void setComponentValue(Decimalbox comp, BigDecimal value) {
		
		try {
			comp.setValue(value);
		} 
		catch(Exception ex) 
		{
			/*This is to, under testing, avoid halting cause java.lang.IllegalStateException
			with detailMessage: Components can be accessed only in event listeners.
			Line 305 in ZK UiEngineImpl.java file*/
		}
	}
	
	protected void selectComboitemByValueForcingLabel(Combobox comp, String value, String label) {
		
		try {
			for (Component co : comp.getChildren()) {
				Comboitem item = (Comboitem) co;
				if (item.getValue().toString().equals(value)) {
					try {item.setLabel(label);}catch(Exception ex){} //important to set the label. under testing, ZK Labels artifact
																		//isn't available when iterating comboitems with translated labels.
					
					try{comp.setSelectedItem(item);}catch(Exception ex){} //if not within try catch block, under testing
																		//next line isn't executed since java.lang.IllegalStateException
					break;
				}
			}
		} 
		catch(Exception ex) 
		{
			/*This is to, under testing, avoid halting cause java.lang.IllegalStateException
			with detailMessage: Components can be accessed only in event listeners.
			Line 305 in ZK UiEngineImpl.java file*/
		}
	}
	
	protected void setComponentValue(Intbox comp, Integer value) {
		
		try {
			comp.setValue(value);
		} 
		catch(Exception ex) 
		{
			/*This is to, under testing, avoid halting cause java.lang.IllegalStateException
			with detailMessage: Components can be accessed only in event listeners.
			Line 305 in ZK UiEngineImpl.java file*/
		}
	}
}
