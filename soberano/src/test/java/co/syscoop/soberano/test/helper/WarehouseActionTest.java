package co.syscoop.soberano.test.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;

import co.syscoop.soberano.util.SpringUtility;

public class WarehouseActionTest extends ActionTest {
	
	protected static Textbox txtName = null;
	protected static Textbox txtCode = null;
	protected static Checkbox chkProcurementWarehouse = null;
	protected static Checkbox chkSalesWarehouse = null;
	protected static Combobox cmbEntryProcesses = null;
	protected static Treechildren tchdnEntryProcesses = null;
	
	protected static WarehouseForm setFormComponents(String user, String formZulFilename) {
		
		SpringUtility.setLoggedUserForTesting(user);
		DesktopAgent desktop = Zats.newClient().connect("/" + formZulFilename);
		cmbIntelliSearchAgent = desktop.query("combobox");
		cmbIntelliSearch = cmbIntelliSearchAgent.as(Combobox.class);
		WarehouseForm WarehouseForm = new WarehouseForm(desktop, 
												cmbIntelliSearchAgent.query("#incDetails").query("#txtName").as(Textbox.class), 
												cmbIntelliSearchAgent.query("#incDetails").query("#txtCode").as(Textbox.class),
												cmbIntelliSearchAgent.query("#incDetails").query("#chkProcurementWarehouse").as(Checkbox.class),
												cmbIntelliSearchAgent.query("#incDetails").query("#chkSalesWarehouse").as(Checkbox.class));
		return WarehouseForm;
	}
	
	protected void checkWarehouse(String name,
							String code,
							Boolean isProcurementWarehouse,
							Boolean isSalesWarehouse) {
		
		String qualifiedName = name + " : " + code;
		loadObjectDetails(qualifiedName);
		
		assertEquals(name.toLowerCase(), txtName.getText().toLowerCase(), "Wrong name shown for warehouse " +  qualifiedName);
		assertEquals(code.toLowerCase(), txtCode.getText().toLowerCase(), "Wrong code shown for warehouse " +  qualifiedName);
		assertEquals(isProcurementWarehouse, chkProcurementWarehouse.isChecked(), "Warehouse " + qualifiedName + " is wrongly shown as procurement warehouse: " + chkProcurementWarehouse.isChecked());
		assertEquals(isSalesWarehouse, chkSalesWarehouse.isChecked(), "Warehouse " + qualifiedName + " is wrongly shown as sales warehouse: " + chkSalesWarehouse.isChecked());
	}
	
	protected void checkWarehouse(String name,
			String code,
			Boolean isProcurementWarehouse,
			Boolean isSalesWarehouse,
			String entryProcesses[]) {
		
		checkWarehouse(name,
						code,
						isProcurementWarehouse,
						isSalesWarehouse);

		for (int i = 0; i < entryProcesses.length; i++) {
			assertEquals(entryProcesses[i], ((Treeitem) tchdnEntryProcesses.getChildren().get(i)).getLabel(), "Wrong entry process shown for warehouse " +  name + " : " + code);
		}
	}
}
