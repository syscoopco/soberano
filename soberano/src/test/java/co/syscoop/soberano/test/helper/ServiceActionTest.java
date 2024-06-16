package co.syscoop.soberano.test.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.util.SpringUtility;

public class ServiceActionTest extends ActionTest {
	
	protected static Textbox txtName = null;
	protected static Textbox txtCode = null;
	
	protected static ServiceForm setFormComponents(String user, String formZulFilename) {
		
		SpringUtility.setLoggedUserForTesting(user);
		DesktopAgent desktop = Zats.newClient().connect("/" + formZulFilename);
		cmbIntelliSearchAgent = desktop.query("center").query("combobox");
		cmbIntelliSearch = cmbIntelliSearchAgent.as(Combobox.class);
		ServiceForm serviceForm = new ServiceForm(desktop, 
												cmbIntelliSearchAgent.query("#incDetails").query("#txtName").as(Textbox.class), 
												cmbIntelliSearchAgent.query("#incDetails").query("#txtCode").as(Textbox.class));
		return serviceForm;
	}
	
	protected void checkService(String name,
							String code) {
		
		String qualifiedName = name + " : " + code;
		loadObjectDetails(qualifiedName);
		
		assertEquals(name.toLowerCase(), txtName.getText().toLowerCase(), "Wrong name shown for service " +  qualifiedName);
		assertEquals(code.toLowerCase(), txtCode.getText().toLowerCase(), "Wrong code shown for service " +  qualifiedName);
	}
}
