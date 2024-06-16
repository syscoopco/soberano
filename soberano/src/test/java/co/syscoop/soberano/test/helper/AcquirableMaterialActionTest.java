package co.syscoop.soberano.test.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.util.SpringUtility;

public class AcquirableMaterialActionTest extends ActionTest {
	
	protected static Textbox txtName = null;
	protected static Textbox txtCode = null;
	protected static Combobox cmbUnit = null;
	protected static Decimalbox decMinimumInventoryLevel = null;
	
	protected static AcquirableMaterialForm setFormComponents(String user, String formZulFilename) {
		
		SpringUtility.setLoggedUserForTesting(user);
		DesktopAgent desktop = Zats.newClient().connect("/" + formZulFilename);
		cmbIntelliSearchAgent = desktop.query("center").query("combobox");
		cmbIntelliSearch = cmbIntelliSearchAgent.as(Combobox.class);
		AcquirableMaterialForm acquirableMaterialForm = new AcquirableMaterialForm(desktop, 
												cmbIntelliSearchAgent.query("#incDetails").query("#txtName").as(Textbox.class), 
												cmbIntelliSearchAgent.query("#incDetails").query("#txtCode").as(Textbox.class),
												cmbIntelliSearchAgent.query("#incDetails").query("#cmbUnit").as(Combobox.class),
												cmbIntelliSearchAgent.query("#incDetails").query("#decMinimumInventoryLevel").as(Decimalbox.class));
		return acquirableMaterialForm;
	}
	
	protected void checkAcquirableMaterial(String name,
							String code,
							Integer unit,
							BigDecimal inventoryLevel) {
		
		String qualifiedName = name + " : " + code;
		loadObjectDetails(qualifiedName);
		
		assertEquals(name.toLowerCase(), txtName.getText().toLowerCase(), "Wrong name shown for acquirable material " +  qualifiedName);
		assertEquals(code.toLowerCase(), txtCode.getText().toLowerCase(), "Wrong code shown for acquirable material " +  qualifiedName);
		assertEquals(unit, ((DomainObject) cmbUnit.getSelectedItem().getValue()).getId(), "Wrong unit shown for acquirable material " + qualifiedName);
		assertEquals(inventoryLevel.subtract(decMinimumInventoryLevel.getValue()).abs().doubleValue() < 0.00000001, true, "Wrong inventory level shown for acquirable material " + qualifiedName);
	}
}
