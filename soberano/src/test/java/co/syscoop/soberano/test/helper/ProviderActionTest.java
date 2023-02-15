package co.syscoop.soberano.test.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.util.SpringUtility;

public class ProviderActionTest extends ActionTest {
	
	protected static Textbox txtName = null;
	
	protected static ProviderForm setFormComponents(String user, String formZulFilename) {
		
		SpringUtility.setLoggedUserForTesting(user);
		DesktopAgent desktop = Zats.newClient().connect("/" + formZulFilename);
		cmbIntelliSearchAgent = desktop.query("combobox");
		cmbIntelliSearch = cmbIntelliSearchAgent.as(Combobox.class);
		ProviderForm providerForm = new ProviderForm(desktop, 
												cmbIntelliSearchAgent.query("#incDetails").query("#txtName").as(Textbox.class));
		return providerForm;
	}
	
	protected void checkProvider(String name) {
		
		String qualifiedName = name;
		loadObjectDetails(qualifiedName);
		
		assertEquals(name.toLowerCase(), txtName.getText().toLowerCase(), "Wrong name shown for provider " +  qualifiedName);
	}
}
