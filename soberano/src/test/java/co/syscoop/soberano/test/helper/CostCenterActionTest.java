package co.syscoop.soberano.test.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.util.SpringUtility;

public class CostCenterActionTest extends ActionTest {
	
	protected static Textbox txtName = null;
	protected static Combobox cmbInputWarehouse = null;
	protected static Combobox cmbOutputWarehouse = null;
	
	protected static CostCenterForm setFormComponents(String user, String formZulFilename) {
		
		SpringUtility.setLoggedUserForTesting(user);
		DesktopAgent desktop = Zats.newClient().connect("/" + formZulFilename);
		cmbIntelliSearchAgent = desktop.query("combobox");
		cmbIntelliSearch = cmbIntelliSearchAgent.as(Combobox.class);
		CostCenterForm costCenterForm = new CostCenterForm(desktop, 
												cmbIntelliSearchAgent.query("#incDetails").query("#txtName").as(Textbox.class), 
												cmbIntelliSearchAgent.query("#incDetails").query("#cmbInputWarehouse").as(Combobox.class),
												cmbIntelliSearchAgent.query("#incDetails").query("#cmbOutputWarehouse").as(Combobox.class));
		return costCenterForm;
	}
	
	protected void checkCostCenter(String name,
									String inputWarehouse,
									String outputWarehouse) {
		
		String qualifiedName = name;
		loadObjectDetails(qualifiedName);
		
		assertEquals(name.toLowerCase(), txtName.getText().toLowerCase(), "Wrong name shown for cost center " +  qualifiedName);
		assertEquals(inputWarehouse, cmbInputWarehouse.getText(), "Wrong input warehouse shown for cost center " +  qualifiedName);
		assertEquals(outputWarehouse, cmbOutputWarehouse.getText(), "Wrong output warehouse shown for cost center " +  qualifiedName);
	}
	
	protected void checkCostCenter(CostCenterForm costCenterForm,
					String name,
					String inputWarehouse,
					String outputWarehouse) {
		
		String qualifiedName = name;
		loadObjectDetails(qualifiedName);
		
		assertEquals(name.toLowerCase(), costCenterForm.getTxtName().getText().toLowerCase(), "Wrong name shown for cost center " +  qualifiedName);
		assertEquals(inputWarehouse, costCenterForm.getCmbInputWarehouse().getText(), "Wrong input warehouse shown for cost center " +  qualifiedName);
		assertEquals(outputWarehouse, costCenterForm.getCmbOutputWarehouse().getText(), "Wrong output warehouse shown for cost center " +  qualifiedName);
	}
}
