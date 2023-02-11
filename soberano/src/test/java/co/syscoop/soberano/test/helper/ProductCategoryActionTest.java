package co.syscoop.soberano.test.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.util.SpringUtility;

public class ProductCategoryActionTest extends ActionTest {
	
	protected static Textbox txtName = null;
	protected static Intbox intPosition = null;
	protected static Checkbox chkDisabled = null;
	
	protected static ProductCategoryForm setFormComponents(String user, String formZulFilename) {
		
		SpringUtility.setLoggedUserForTesting(user);
		DesktopAgent desktop = Zats.newClient().connect("/" + formZulFilename);
		cmbIntelliSearchAgent = desktop.query("combobox");
		cmbIntelliSearch = cmbIntelliSearchAgent.as(Combobox.class);
		ProductCategoryForm productCategoryForm = new ProductCategoryForm(desktop, 
												cmbIntelliSearchAgent.query("#incDetails").query("#txtName").as(Textbox.class), 
												cmbIntelliSearchAgent.query("#incDetails").query("#intPosition").as(Intbox.class),
												cmbIntelliSearchAgent.query("#incDetails").query("#chkDisabled").as(Checkbox.class));
		return productCategoryForm;
	}
	
	protected void checkProductCategory(String name,
							Integer position,
							Boolean isDisabled) {
		
		String qualifiedName = name;
		loadObjectDetails(qualifiedName);
		
		assertEquals(name.toLowerCase(), txtName.getText().toLowerCase(), "Wrong name shown for product category " +  qualifiedName);
		assertEquals(position, intPosition.getValue(), "Wrong position for product category " +  qualifiedName);
		assertEquals(isDisabled, chkDisabled.isChecked(), "Product category " + qualifiedName + " is wrongly shown with isDisabled: " + chkDisabled.isChecked());
	}
}
