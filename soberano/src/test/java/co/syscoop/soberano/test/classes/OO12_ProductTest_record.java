package co.syscoop.soberano.test.classes;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.opentest4j.AssertionFailedError;
import org.zkoss.lang.Library;
import org.zkoss.web.Attributes;
import org.zkoss.zats.mimic.ComponentAgent;
import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zats.mimic.operation.InputAgent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.test.helper.ProductActionTest;
import co.syscoop.soberano.test.helper.ProductForm;
import co.syscoop.soberano.util.SpringUtility;

@Order(12)

@Disabled

@TestMethodOrder(OrderAnnotation.class)
class OO12_ProductTest_record extends ProductActionTest {
	
	protected ProductForm productForm = null;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		Library.setProperty(Attributes.PREFERRED_LOCALE, "en"); //needed due to translated captions according 
																//to runtime locale not available under 
																//testing environment
		
		Zats.init("./src/main/webapp");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		Zats.cleanup();
		Zats.end();
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	@Order(0)
	final void testCase0() {
		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_product.zul");
		productForm = new ProductForm(desktop, 
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class),
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCategory")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decPrice")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePriceExchangeRate")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePrice")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class));
		try {
			//this is needed to execute only in the first test. it has to do with testing configuration.
			productForm.testEachConstrainedObjectIsDeclared();
			productForm.testEachDeclaredConstrainedObjectIsActuallyConstrained();
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
		
	@Test
	@Order(1)
	final void testCase1() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_product.zul");
		productForm = new ProductForm(desktop, 
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class),
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCategory")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decPrice")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePriceExchangeRate")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePrice")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class));
		try {
			productForm.setComponentValue(productForm.getTxtCode(), "p1");
			productForm.setComponentValue(productForm.getTxtName(), "");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(1000.0));
			productForm.setComponentValue(productForm.getDecPrice(), new BigDecimal(1.001));
						
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("piece");
			productForm.setComponentValue(productForm.getCmbUnit(), Integer.valueOf(1));
			
			ComponentAgent cmbCategoryAgent = desktop.query("textbox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat1");
			productForm.setComponentValue(productForm.getCmbCategory(), Integer.valueOf(1001));
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc4");
			productForm.setComponentValue(productForm.getCmbCostCenter(), Integer.valueOf(1004));			
			
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			productForm.testWrongValueException(ex);
		}
	}
	
// commented since product's unit defaults to U 
//
//	@Test
//	@Order(2)
//	final void testCase2() {
//
//		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
//		DesktopAgent desktop = Zats.newClient().connect("/new_product.zul");
//		productForm = new ProductForm(desktop, 
//				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
//				(desktop.query("textbox").query("#txtCode")).as(Textbox.class),
//				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
//				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class),
//				(desktop.query("textbox").query("#cmbCategory")).as(Combobox.class),
//				(desktop.query("decimalbox").query("#decPrice")).as(Decimalbox.class),
//				(desktop.query("decimalbox").query("#decReferencePriceExchangeRate")).as(Decimalbox.class),
//				(desktop.query("decimalbox").query("#decReferencePrice")).as(Decimalbox.class),
//				(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class));
//		try {
//			productForm.setComponentValue(productForm.getTxtCode(), "p1");
//			productForm.setComponentValue(productForm.getTxtName(), "product1");		
//			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(1000.0));
//			productForm.setComponentValue(productForm.getDecPrice(), new BigDecimal(1.001));
//						
//			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
//			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
//			cmbUnitInputAgent.typing("");
//			
//			ComponentAgent cmbCategoryAgent = desktop.query("textbox").query("#cmbCategory");
//			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
//			cmbCategoryInputAgent.typing("mcat1");
//			productForm.setComponentValue(productForm.getCmbCategory(), Integer.valueOf(1001));
//			
//			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
//			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
//			cmbCostCenterInputAgent.typing("mcc4");
//			productForm.setComponentValue(productForm.getCmbCostCenter(), Integer.valueOf(1004));			
//			
//			clickOnRecordButton(desktop);
//			
//			fail("None exception was thrown when it should.");
//		}
//		catch(AssertionFailedError ex) {
//			fail(ex.getMessage());
//		}
//		catch(Throwable ex) {
//			productForm.testWrongValueException(ex);
//		}
//	}
	
	@Test
	@Order(3)
	final void testCase3() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_product.zul");
		productForm = new ProductForm(desktop, 
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class),
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCategory")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decPrice")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePriceExchangeRate")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePrice")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class));
		try {
			productForm.setComponentValue(productForm.getTxtCode(), "p1");
			productForm.setComponentValue(productForm.getTxtName(), "product1");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(1000.0));
			productForm.setComponentValue(productForm.getDecPrice(), new BigDecimal(1.001));
						
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("piece");
			productForm.setComponentValue(productForm.getCmbUnit(), Integer.valueOf(1));
			
			ComponentAgent cmbCategoryAgent = desktop.query("textbox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("");
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc4");
			productForm.setComponentValue(productForm.getCmbCostCenter(), Integer.valueOf(1004));			
			
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			productForm.testWrongValueException(ex);
		}
	}
	
	@Test
	@Order(4)
	final void testCase4() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_product.zul");
		productForm = new ProductForm(desktop, 
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class),
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCategory")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decPrice")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePriceExchangeRate")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePrice")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class));
		try {
			productForm.setComponentValue(productForm.getTxtCode(), "p1");
			productForm.setComponentValue(productForm.getTxtName(), "product1");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(1000.0));
			productForm.setComponentValue(productForm.getDecPrice(), new BigDecimal(1.001));
						
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("piece");
			productForm.setComponentValue(productForm.getCmbUnit(), Integer.valueOf(1));
			
			ComponentAgent cmbCategoryAgent = desktop.query("textbox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat1");
			productForm.setComponentValue(productForm.getCmbCategory(), Integer.valueOf(1001));
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("");		
			
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			productForm.testWrongValueException(ex);
		}
	}
	
	@Test
	@Order(5)
	final void testCase5() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_product.zul");
		productForm = new ProductForm(desktop, 
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class),
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCategory")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decPrice")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePriceExchangeRate")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePrice")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class));
		try {
			productForm.setComponentValue(productForm.getTxtCode(), "p1");
			productForm.setComponentValue(productForm.getTxtName(), "product1");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(1000.0));
						
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("piece");
			productForm.setComponentValue(productForm.getCmbUnit(), Integer.valueOf(1));
			
			ComponentAgent cmbCategoryAgent = desktop.query("textbox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat1");
			productForm.setComponentValue(productForm.getCmbCategory(), Integer.valueOf(1001));
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc4");
			productForm.setComponentValue(productForm.getCmbCostCenter(), Integer.valueOf(1004));			
			
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			productForm.testWrongValueException(ex);
		}
	}
	
	@Test
	@Order(6)
	final void testCase6() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_product.zul");
		productForm = new ProductForm(desktop, 
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class),
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCategory")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decPrice")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePriceExchangeRate")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePrice")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class));
		try {
			productForm.setComponentValue(productForm.getTxtCode(), "p1");
			productForm.setComponentValue(productForm.getTxtName(), "product1");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(1000.0));
			
			ComponentAgent decPriceAgent = desktop.query("decimalbox").query("#decPrice");
			InputAgent decPriceInputAgent = decPriceAgent.as(InputAgent.class);
			decPriceInputAgent.type("1.001");
			productForm.setComponentValue(productForm.getDecPrice(), new BigDecimal(1.001));
						
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("piece");
			productForm.setComponentValue(productForm.getCmbUnit(), Integer.valueOf(1));
			
			ComponentAgent cmbCategoryAgent = desktop.query("textbox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat1");
			productForm.setComponentValue(productForm.getCmbCategory(), Integer.valueOf(1001));
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc4");
			productForm.setComponentValue(productForm.getCmbCostCenter(), Integer.valueOf(1004));			
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(7)
	final void testCase7() {

		SpringUtility.setLoggedUserForTesting("user2@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_product.zul");
		productForm = new ProductForm(desktop, 
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class),
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCategory")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decPrice")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePriceExchangeRate")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePrice")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class));
		try {
			productForm.setComponentValue(productForm.getTxtCode(), "p2");
			productForm.setComponentValue(productForm.getTxtName(), "product2");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(50.01234567));
			
			ComponentAgent decPriceAgent = desktop.query("decimalbox").query("#decPrice");
			InputAgent decPriceInputAgent = decPriceAgent.as(InputAgent.class);
			decPriceInputAgent.type("2.00000002");
			productForm.setComponentValue(productForm.getDecPrice(), new BigDecimal(2.00000002));
						
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("kilogram");
			productForm.setComponentValue(productForm.getCmbUnit(), Integer.valueOf(2));
			
			ComponentAgent cmbCategoryAgent = desktop.query("textbox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat2");
			productForm.setComponentValue(productForm.getCmbCategory(), Integer.valueOf(1002));
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc5");
			productForm.setComponentValue(productForm.getCmbCostCenter(), Integer.valueOf(1005));			
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(8)
	final void testCase8() {

		SpringUtility.setLoggedUserForTesting("user3@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_product.zul");
		productForm = new ProductForm(desktop, 
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class),
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCategory")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decPrice")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePriceExchangeRate")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePrice")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class));
		try {
			productForm.setComponentValue(productForm.getTxtCode(), "p3");
			productForm.setComponentValue(productForm.getTxtName(), "product3");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(1234.5678));
			
			ComponentAgent decPriceAgent = desktop.query("decimalbox").query("#decPrice");
			InputAgent decPriceInputAgent = decPriceAgent.as(InputAgent.class);
			decPriceInputAgent.type("3.0123");
			productForm.setComponentValue(productForm.getDecPrice(), new BigDecimal(3.0123));
						
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("pound");
			productForm.setComponentValue(productForm.getCmbUnit(), Integer.valueOf(5));
			
			ComponentAgent cmbCategoryAgent = desktop.query("textbox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat3");
			productForm.setComponentValue(productForm.getCmbCategory(), Integer.valueOf(1003));
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc6");
			productForm.setComponentValue(productForm.getCmbCostCenter(), Integer.valueOf(1006));			
			
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testNotEnoughRightsException(ex);
		}
	}
	
	@Test
	@Order(9)
	final void testCase9() {

		SpringUtility.setLoggedUserForTesting("user4@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_product.zul");
		productForm = new ProductForm(desktop, 
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class),
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCategory")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decPrice")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePriceExchangeRate")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePrice")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class));
		try {
			productForm.setComponentValue(productForm.getTxtCode(), "p3");
			productForm.setComponentValue(productForm.getTxtName(), "product3");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(1234.5678));
			
			ComponentAgent decPriceAgent = desktop.query("decimalbox").query("#decPrice");
			InputAgent decPriceInputAgent = decPriceAgent.as(InputAgent.class);
			decPriceInputAgent.type("3.0123");
			productForm.setComponentValue(productForm.getDecPrice(), new BigDecimal(3.0123));
						
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("pound");
			productForm.setComponentValue(productForm.getCmbUnit(), Integer.valueOf(5));
			
			ComponentAgent cmbCategoryAgent = desktop.query("textbox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat3");
			productForm.setComponentValue(productForm.getCmbCategory(), Integer.valueOf(1003));
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc6");
			productForm.setComponentValue(productForm.getCmbCostCenter(), Integer.valueOf(1006));			
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(10)
	final void testCase10() {

		SpringUtility.setLoggedUserForTesting("user5@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_product.zul");
		productForm = new ProductForm(desktop, 
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class),
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCategory")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decPrice")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePriceExchangeRate")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePrice")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class));
		try {
			productForm.setComponentValue(productForm.getTxtCode(), "p4");
			productForm.setComponentValue(productForm.getTxtName(), "product4");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(6543.21098));
			
			ComponentAgent decReferencePriceAgent = desktop.query("decimalbox").query("#decReferencePrice");
			InputAgent decReferencePriceInputAgent = decReferencePriceAgent.as(InputAgent.class);
			decReferencePriceInputAgent.type("987.654");
			productForm.setComponentValue(productForm.getDecReferencePrice(), new BigDecimal(987.654));
						
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("milliliter");
			productForm.setComponentValue(productForm.getCmbUnit(), Integer.valueOf(8));
			
			ComponentAgent cmbCategoryAgent = desktop.query("textbox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat4");
			productForm.setComponentValue(productForm.getCmbCategory(), Integer.valueOf(1004));
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc7");
			productForm.setComponentValue(productForm.getCmbCostCenter(), Integer.valueOf(1007));			
			
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testNotEnoughRightsException(ex);
		}
	}
	
	@Test
	@Order(11)
	final void testCase11() {

		SpringUtility.setLoggedUserForTesting("user6@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_product.zul");
		productForm = new ProductForm(desktop, 
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class),
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCategory")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decPrice")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePriceExchangeRate")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePrice")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class));
		try {
			productForm.setComponentValue(productForm.getTxtCode(), "p4");
			productForm.setComponentValue(productForm.getTxtName(), "product4");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(6543.21098));
			
			ComponentAgent decReferencePriceAgent = desktop.query("decimalbox").query("#decReferencePrice");
			InputAgent decReferencePriceInputAgent = decReferencePriceAgent.as(InputAgent.class);
			decReferencePriceInputAgent.type("987.654");
			productForm.setComponentValue(productForm.getDecReferencePrice(), new BigDecimal(987.654));
						
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("milliliter");
			productForm.setComponentValue(productForm.getCmbUnit(), Integer.valueOf(8));
			
			ComponentAgent cmbCategoryAgent = desktop.query("textbox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat4");
			productForm.setComponentValue(productForm.getCmbCategory(), Integer.valueOf(1004));
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc7");
			productForm.setComponentValue(productForm.getCmbCostCenter(), Integer.valueOf(1007));	
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(12)
	final void testCase12() {

		SpringUtility.setLoggedUserForTesting("user7@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_product.zul");
		productForm = new ProductForm(desktop, 
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class),
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCategory")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decPrice")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePriceExchangeRate")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePrice")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class));
		try {
			productForm.setComponentValue(productForm.getTxtCode(), "p5");
			productForm.setComponentValue(productForm.getTxtName(), "product5");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(7654321));
			
			ComponentAgent decReferencePriceAgent = desktop.query("decimalbox").query("#decReferencePrice");
			InputAgent decReferencePriceInputAgent = decReferencePriceAgent.as(InputAgent.class);
			decReferencePriceInputAgent.type("876.123");
			productForm.setComponentValue(productForm.getDecReferencePrice(), new BigDecimal(876.123));
						
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("liter");
			productForm.setComponentValue(productForm.getCmbUnit(), Integer.valueOf(7));
			
			ComponentAgent cmbCategoryAgent = desktop.query("textbox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat5");
			productForm.setComponentValue(productForm.getCmbCategory(), Integer.valueOf(1005));
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc8");
			productForm.setComponentValue(productForm.getCmbCostCenter(), Integer.valueOf(1008));			
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(13)
	final void testCase13() {

		SpringUtility.setLoggedUserForTesting("user8@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_product.zul");
		productForm = new ProductForm(desktop, 
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class),
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCategory")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decPrice")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePriceExchangeRate")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePrice")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class));
		try {
			productForm.setComponentValue(productForm.getTxtCode(), "p6");
			productForm.setComponentValue(productForm.getTxtName(), "product6");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(12));
			
			ComponentAgent decReferencePriceAgent = desktop.query("decimalbox").query("#decReferencePrice");
			InputAgent decReferencePriceInputAgent = decReferencePriceAgent.as(InputAgent.class);
			decReferencePriceInputAgent.type("3.45");
			productForm.setComponentValue(productForm.getDecReferencePrice(), new BigDecimal(3.45));
						
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("piece");
			productForm.setComponentValue(productForm.getCmbUnit(), Integer.valueOf(1));
			
			ComponentAgent cmbCategoryAgent = desktop.query("textbox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat6");
			productForm.setComponentValue(productForm.getCmbCategory(), Integer.valueOf(1006));
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc9");
			productForm.setComponentValue(productForm.getCmbCostCenter(), Integer.valueOf(1009));			
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(14)
	final void testCase14() {

		SpringUtility.setLoggedUserForTesting("user9@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_product.zul");
		productForm = new ProductForm(desktop, 
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class),
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCategory")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decPrice")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePriceExchangeRate")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePrice")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class));
		try {
			productForm.setComponentValue(productForm.getTxtCode(), "p7");
			productForm.setComponentValue(productForm.getTxtName(), "product7");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(2));
			
			ComponentAgent decPriceAgent = desktop.query("decimalbox").query("#decPrice");
			InputAgent decPriceInputAgent = decPriceAgent.as(InputAgent.class);
			decPriceInputAgent.type("8.00998877");
			productForm.setComponentValue(productForm.getDecPrice(), new BigDecimal(8.00998877));
						
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("ounce");
			productForm.setComponentValue(productForm.getCmbUnit(), Integer.valueOf(6));
			
			ComponentAgent cmbCategoryAgent = desktop.query("textbox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat7");
			productForm.setComponentValue(productForm.getCmbCategory(), Integer.valueOf(1007));
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc10");
			productForm.setComponentValue(productForm.getCmbCostCenter(), Integer.valueOf(1010));			
			
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testNotEnoughRightsException(ex);
		}
	}
	
	@Test
	@Order(15)
	final void testCase15() {

		SpringUtility.setLoggedUserForTesting("user10@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_product.zul");
		productForm = new ProductForm(desktop, 
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class),
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCategory")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decPrice")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePriceExchangeRate")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePrice")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class));
		try {
			productForm.setComponentValue(productForm.getTxtCode(), "p7");
			productForm.setComponentValue(productForm.getTxtName(), "product7");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(2));
			
			ComponentAgent decPriceAgent = desktop.query("decimalbox").query("#decPrice");
			InputAgent decPriceInputAgent = decPriceAgent.as(InputAgent.class);
			decPriceInputAgent.type("8.00998877");
			productForm.setComponentValue(productForm.getDecPrice(), new BigDecimal(8.00998877));
						
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("ounce");
			productForm.setComponentValue(productForm.getCmbUnit(), Integer.valueOf(6));
			
			ComponentAgent cmbCategoryAgent = desktop.query("textbox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat7");
			productForm.setComponentValue(productForm.getCmbCategory(), Integer.valueOf(1007));
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc10");
			productForm.setComponentValue(productForm.getCmbCostCenter(), Integer.valueOf(1010));			
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(16)
	final void testCase16() {

		SpringUtility.setLoggedUserForTesting("user11@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_product.zul");
		productForm = new ProductForm(desktop, 
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class),
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCategory")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decPrice")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePriceExchangeRate")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePrice")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class));
		try {
			productForm.setComponentValue(productForm.getTxtCode(), "p8");
			productForm.setComponentValue(productForm.getTxtName(), "product8");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(43526100));
			
			ComponentAgent decPriceAgent = desktop.query("decimalbox").query("#decPrice");
			InputAgent decPriceInputAgent = decPriceAgent.as(InputAgent.class);
			decPriceInputAgent.type("0.0005");
			productForm.setComponentValue(productForm.getDecPrice(), new BigDecimal(0.0005));
						
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("gram");
			productForm.setComponentValue(productForm.getCmbUnit(), Integer.valueOf(3));
			
			ComponentAgent cmbCategoryAgent = desktop.query("textbox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat8");
			productForm.setComponentValue(productForm.getCmbCategory(), Integer.valueOf(1008));
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc4");
			productForm.setComponentValue(productForm.getCmbCostCenter(), Integer.valueOf(1004));			
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(17)
	final void testCase17() {

		SpringUtility.setLoggedUserForTesting("user12@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_product.zul");
		productForm = new ProductForm(desktop, 
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class),
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCategory")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decPrice")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePriceExchangeRate")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePrice")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class));
		try {
			productForm.setComponentValue(productForm.getTxtCode(), "p9");
			productForm.setComponentValue(productForm.getTxtName(), "product9");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(12));
			
			ComponentAgent decPriceAgent = desktop.query("decimalbox").query("#decPrice");
			InputAgent decPriceInputAgent = decPriceAgent.as(InputAgent.class);
			decPriceInputAgent.type("1.09878901");
			productForm.setComponentValue(productForm.getDecPrice(), new BigDecimal(1.09878901));
						
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("piece");
			productForm.setComponentValue(productForm.getCmbUnit(), Integer.valueOf(1));
			
			ComponentAgent cmbCategoryAgent = desktop.query("textbox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat8");
			productForm.setComponentValue(productForm.getCmbCategory(), Integer.valueOf(1008));
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc5");
			productForm.setComponentValue(productForm.getCmbCostCenter(), Integer.valueOf(1005));			
			
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testNotEnoughRightsException(ex);
		}
	}
	
	@Test
	@Order(18)
	final void testCase18() {

		SpringUtility.setLoggedUserForTesting("user13@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_product.zul");
		productForm = new ProductForm(desktop, 
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class),
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCategory")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decPrice")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePriceExchangeRate")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePrice")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class));
		try {
			productForm.setComponentValue(productForm.getTxtCode(), "p9");
			productForm.setComponentValue(productForm.getTxtName(), "product9");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(12));
			
			ComponentAgent decPriceAgent = desktop.query("decimalbox").query("#decPrice");
			InputAgent decPriceInputAgent = decPriceAgent.as(InputAgent.class);
			decPriceInputAgent.type("1.09878901");
			productForm.setComponentValue(productForm.getDecPrice(), new BigDecimal(1.09878901));
						
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("piece");
			productForm.setComponentValue(productForm.getCmbUnit(), Integer.valueOf(1));
			
			ComponentAgent cmbCategoryAgent = desktop.query("textbox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat8");
			productForm.setComponentValue(productForm.getCmbCategory(), Integer.valueOf(1008));
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc5");
			productForm.setComponentValue(productForm.getCmbCostCenter(), Integer.valueOf(1005));			
			
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testNotEnoughRightsException(ex);
		}
	}
	
	@Test
	@Order(19)
	final void testCase19() {

		SpringUtility.setLoggedUserForTesting("user14@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_product.zul");
		productForm = new ProductForm(desktop, 
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class),
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCategory")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decPrice")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePriceExchangeRate")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePrice")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class));
		try {
			productForm.setComponentValue(productForm.getTxtCode(), "p9");
			productForm.setComponentValue(productForm.getTxtName(), "product9");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(12));
			
			ComponentAgent decPriceAgent = desktop.query("decimalbox").query("#decPrice");
			InputAgent decPriceInputAgent = decPriceAgent.as(InputAgent.class);
			decPriceInputAgent.type("1.09878901");
			productForm.setComponentValue(productForm.getDecPrice(), new BigDecimal(1.09878901));
						
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("piece");
			productForm.setComponentValue(productForm.getCmbUnit(), Integer.valueOf(1));
			
			ComponentAgent cmbCategoryAgent = desktop.query("textbox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat8");
			productForm.setComponentValue(productForm.getCmbCategory(), Integer.valueOf(1008));
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc5");
			productForm.setComponentValue(productForm.getCmbCostCenter(), Integer.valueOf(1005));			
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(20)
	final void testCase20() {

		SpringUtility.setLoggedUserForTesting("user15@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_product.zul");
		productForm = new ProductForm(desktop, 
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class),
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCategory")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decPrice")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePriceExchangeRate")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePrice")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class));
		try {
			productForm.setComponentValue(productForm.getTxtCode(), "p10");
			productForm.setComponentValue(productForm.getTxtName(), "product10");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(234));
			
			ComponentAgent decReferencePriceAgent = desktop.query("decimalbox").query("#decReferencePrice");
			InputAgent decReferencePriceInputAgent = decReferencePriceAgent.as(InputAgent.class);
			decReferencePriceInputAgent.type("1234");
			productForm.setComponentValue(productForm.getDecReferencePrice(), new BigDecimal(1234));
						
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("ounce");
			productForm.setComponentValue(productForm.getCmbUnit(), Integer.valueOf(6));
			
			ComponentAgent cmbCategoryAgent = desktop.query("textbox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat2");
			productForm.setComponentValue(productForm.getCmbCategory(), Integer.valueOf(1002));
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc5");
			productForm.setComponentValue(productForm.getCmbCostCenter(), Integer.valueOf(1005));			
			
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testNotEnoughRightsException(ex);
		}
	}
	
	@Test
	@Order(21)
	final void testCase21() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_product.zul");
		productForm = new ProductForm(desktop, 
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class),
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCategory")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decPrice")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePriceExchangeRate")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePrice")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class));
		try {
			productForm.setComponentValue(productForm.getTxtCode(), "p1");
			productForm.setComponentValue(productForm.getTxtName(), "product10");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(234));

			ComponentAgent decReferencePriceAgent = desktop.query("decimalbox").query("#decReferencePrice");
			InputAgent decReferencePriceInputAgent = decReferencePriceAgent.as(InputAgent.class);
			decReferencePriceInputAgent.type("1234");
			productForm.setComponentValue(productForm.getDecReferencePrice(), new BigDecimal(1234));
						
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("ounce");
			productForm.setComponentValue(productForm.getCmbUnit(), Integer.valueOf(6));
			
			ComponentAgent cmbCategoryAgent = desktop.query("textbox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat2");
			productForm.setComponentValue(productForm.getCmbCategory(), Integer.valueOf(1002));
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc5");
			productForm.setComponentValue(productForm.getCmbCostCenter(), Integer.valueOf(1005));			
			
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testDuplicateKeyException(ex);
		}
	}
	
	@Test
	@Order(22)
	final void testCase22() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_product.zul");
		productForm = new ProductForm(desktop, 
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class),
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCategory")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decPrice")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePriceExchangeRate")).as(Decimalbox.class),
				(desktop.query("decimalbox").query("#decReferencePrice")).as(Decimalbox.class),
				(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class));
		try {
			productForm.setComponentValue(productForm.getTxtCode(), "p10");
			productForm.setComponentValue(productForm.getTxtName(), "product9");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(234));

			ComponentAgent decReferencePriceAgent = desktop.query("decimalbox").query("#decReferencePrice");
			InputAgent decReferencePriceInputAgent = decReferencePriceAgent.as(InputAgent.class);
			decReferencePriceInputAgent.type("1234");
			productForm.setComponentValue(productForm.getDecReferencePrice(), new BigDecimal(1234));
						
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("ounce");
			productForm.setComponentValue(productForm.getCmbUnit(), Integer.valueOf(6));
			
			ComponentAgent cmbCategoryAgent = desktop.query("textbox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat2");
			productForm.setComponentValue(productForm.getCmbCategory(), Integer.valueOf(1002));
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc5");
			productForm.setComponentValue(productForm.getCmbCostCenter(), Integer.valueOf(1005));			
			
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testDuplicateKeyException(ex);
		}
	}
}