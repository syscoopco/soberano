package co.syscoop.soberano.test.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Textbox;
import co.syscoop.soberano.util.SpringUtility;

public class ProcessActionTest extends ActionTest {
	
	protected static Textbox txtName = null;
	protected static Decimalbox decFixedCost = null;
	
	protected static ProcessForm setFormComponents(String user, String formZulFilename) {
		
		SpringUtility.setLoggedUserForTesting(user);
		DesktopAgent desktop = Zats.newClient().connect("/" + formZulFilename);
		cmbIntelliSearchAgent = desktop.query("combobox");
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
		assertEquals(fixedCost, decFixedCost.getValue(), "Wrong fixed cost shown for process " +  qualifiedName);
	}
}
