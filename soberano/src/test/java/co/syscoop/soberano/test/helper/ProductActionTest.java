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

public class ProductActionTest extends ActionTest {
	
	protected static Textbox txtName = null;
	protected static Textbox txtCode = null;
	protected static Combobox cmbCategory = null;
	protected static Combobox cmbUnit = null;
	protected static Decimalbox decMinimumInventoryLevel = null;
	protected static Decimalbox decPrice = null;
	protected static Decimalbox decReferencePriceExchangeRate = null;
	protected static Decimalbox decReferencePrice = null;
	protected static Combobox cmbCostCenter = null;
	
	protected static ProductForm setFormComponents(String user, String formZulFilename) {
		
		SpringUtility.setLoggedUserForTesting(user);
		DesktopAgent desktop = Zats.newClient().connect("/" + formZulFilename);
		cmbIntelliSearchAgent = desktop.query("combobox");
		cmbIntelliSearch = cmbIntelliSearchAgent.as(Combobox.class);
		ProductForm productForm = new ProductForm(desktop, 
												cmbIntelliSearchAgent.query("#incDetails").query("#txtName").as(Textbox.class), 
												cmbIntelliSearchAgent.query("#incDetails").query("#txtCode").as(Textbox.class),
												cmbIntelliSearchAgent.query("#incDetails").query("#cmbUnit").as(Combobox.class),
												cmbIntelliSearchAgent.query("#incDetails").query("#decMinimumInventoryLevel").as(Decimalbox.class),
												cmbIntelliSearchAgent.query("#incDetails").query("#cmbCategory").as(Combobox.class),
												cmbIntelliSearchAgent.query("#incDetails").query("#decPrice").as(Decimalbox.class),
												cmbIntelliSearchAgent.query("#incDetails").query("#decReferencePriceExchangeRate").as(Decimalbox.class),
												cmbIntelliSearchAgent.query("#incDetails").query("#decReferencePrice").as(Decimalbox.class),
												cmbIntelliSearchAgent.query("#incDetails").query("#cmbCostCenter").as(Combobox.class));
		return productForm;
	}
	
	protected void checkProduct(String name,
							String code,
							Integer unit,
							BigDecimal inventoryLevel,							
							Integer category,
							BigDecimal price,
							BigDecimal refPrice,
							BigDecimal refExchangeRate,
							Integer costCenter) {
		
		String qualifiedName = name + " : " + code;
		loadObjectDetails(qualifiedName);
		
		assertEquals(name.toLowerCase(), txtName.getText().toLowerCase(), "Wrong name shown for product " +  qualifiedName);
		assertEquals(code.toLowerCase(), txtCode.getText().toLowerCase(), "Wrong code shown for product " +  qualifiedName);
		assertEquals(unit, ((DomainObject) cmbUnit.getSelectedItem().getValue()).getId(), "Wrong unit shown for product " + qualifiedName);
		assertEquals(inventoryLevel.subtract(decMinimumInventoryLevel.getValue()).abs().doubleValue() < 0.00000001, true, "Wrong inventory level shown for product " + qualifiedName);
		
		assertEquals(category, ((DomainObject) cmbCategory.getSelectedItem().getValue()).getId(), "Wrong category shown for product " + qualifiedName);
		assertEquals(price.subtract(decPrice.getValue()).abs().doubleValue() < 0.00000001, true, "Wrong price shown for product " + qualifiedName);
		assertEquals(refExchangeRate.subtract(decReferencePriceExchangeRate.getValue()).abs().doubleValue() < 0.00000001, true, "Wrong reference price exchange rage shown for product " + qualifiedName);
		
		//TODO: this assert fails with java.lang.IllegalStateException 
		//assertEquals(refPrice.subtract(decReferencePrice.getValue()).abs().doubleValue() < 0.00000001, true, "Wrong reference price shown for product " + qualifiedName);
		//so, an invisible component is used for checking a proper reference price value is stored in database
		Decimalbox decReferencePriceForTesting = (Decimalbox) decReferencePrice.query("#decReferencePriceForTesting");
		assertEquals(refPrice.subtract(decReferencePriceForTesting.getValue()).abs().doubleValue() < 0.00000001, true, "Wrong reference price shown for product " + qualifiedName);
				
		assertEquals(costCenter, ((DomainObject) cmbCostCenter.getSelectedItem().getValue()).getId(), "Wrong cost center shown for product " + qualifiedName);
	}
}
