package co.syscoop.soberano.test.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import co.syscoop.soberano.util.SpringUtility;

public class CounterActionTest extends ActionTest {
	
	protected static Textbox txtCode = null;
	protected static Intbox intNumberOfReceivers = null;
	protected static Checkbox chkIsSurcharged = null;
	protected static Checkbox chkDisabled = null;
	
	protected static CounterForm setFormComponents(String user, String formZulFilename) {
		
		SpringUtility.setLoggedUserForTesting(user);
		DesktopAgent desktop = Zats.newClient().connect("/" + formZulFilename);
		cmbIntelliSearchAgent = desktop.query("combobox");
		cmbIntelliSearch = cmbIntelliSearchAgent.as(Combobox.class);
		CounterForm counterForm = new CounterForm(desktop, 
												cmbIntelliSearchAgent.query("#incDetails").query("#txtCode").as(Textbox.class), 
												cmbIntelliSearchAgent.query("#incDetails").query("#intNumberOfReceivers").as(Intbox.class), 
												cmbIntelliSearchAgent.query("#incDetails").query("#chkIsSurcharged").as(Checkbox.class), 
												cmbIntelliSearchAgent.query("#incDetails").query("#chkDisabled").as(Checkbox.class));
		return counterForm;
	}
	
	protected void checkCounter(String code,
							Integer numberOdReceivers,
							Boolean isSurcharged,
							Boolean isDisabled) {
		
		String qualifiedName = code;
		loadObjectDetails(qualifiedName);
		
		assertEquals(code.toLowerCase(), txtCode.getText().toLowerCase(), "Wrong code shown for counter " +  qualifiedName);
		assertEquals(numberOdReceivers, intNumberOfReceivers.getValue(), "Wrong number of receivers for counter " +  qualifiedName);
		assertEquals(isSurcharged, chkIsSurcharged.isChecked(), "Counter " + qualifiedName + " is wrongly shown with isSurcharged: " + chkIsSurcharged.isChecked());
		assertEquals(isDisabled, chkDisabled.isChecked(), "Counter " + qualifiedName + " is wrongly shown with isDisabled: " + chkDisabled.isChecked());
	}
}
