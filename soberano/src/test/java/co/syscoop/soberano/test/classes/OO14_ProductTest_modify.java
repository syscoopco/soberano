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
import org.opentest4j.AssertionFailedError;
import org.zkoss.lang.Library;
import org.zkoss.web.Attributes;
import org.zkoss.zats.mimic.ComponentAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zats.mimic.operation.InputAgent;

import co.syscoop.soberano.test.helper.ProductActionTest;
import co.syscoop.soberano.test.helper.ProductForm;

@Order(14)

//TODO: enable test
//@Disabled

class OO14_ProductTest_modify extends ProductActionTest{

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
	final void testCase1() {
	
		try {
			ProductForm productForm = setFormComponents("user1@soberano.syscoop.co", "products.zul");			
			loadObjectDetails("product1 : p1");			
			productForm.setComponentValue(productForm.getTxtCode(), "mp1");
			productForm.setComponentValue(productForm.getTxtName(), "mproduct1");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(2000));
			
			ComponentAgent cmbUnitAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("kilogram");
			productForm.setComponentValue(productForm.getCmbUnit(), new Integer(2));
			
			ComponentAgent decReferencePriceAgent = productForm.getDesktop().query("vbox").query("decimalbox").query("#decReferencePrice");
			InputAgent decReferencePriceInputAgent = decReferencePriceAgent.as(InputAgent.class);
			decReferencePriceInputAgent.type("6.789");
			productForm.setComponentValue(productForm.getDecReferencePrice(), new BigDecimal(6.789));
			
			ComponentAgent cmbCategoryAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat3");
			productForm.setComponentValue(productForm.getCmbCategory(), new Integer(1003));
			
			ComponentAgent cmbCostCenterAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc5");
			productForm.setComponentValue(productForm.getCmbCostCenter(), new Integer(1005));	
			
			clickOnApplyButton(productForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase2() {
	
		try {
			ProductForm productForm = setFormComponents("user2@soberano.syscoop.co", "products.zul");			
			loadObjectDetails("product2 : p2");			
			productForm.setComponentValue(productForm.getTxtCode(), "mp2");
			productForm.setComponentValue(productForm.getTxtName(), "mproduct2");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(34.85746));
			
			ComponentAgent cmbUnitAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("piece");
			productForm.setComponentValue(productForm.getCmbUnit(), new Integer(1));
			
			ComponentAgent decReferencePriceAgent = productForm.getDesktop().query("vbox").query("decimalbox").query("#decReferencePrice");
			InputAgent decReferencePriceInputAgent = decReferencePriceAgent.as(InputAgent.class);
			decReferencePriceInputAgent.type("5.0980001");
			productForm.setComponentValue(productForm.getDecReferencePrice(), new BigDecimal(5.0980001));
			
			ComponentAgent cmbCategoryAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat4");
			productForm.setComponentValue(productForm.getCmbCategory(), new Integer(1004));
			
			ComponentAgent cmbCostCenterAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc6");
			productForm.setComponentValue(productForm.getCmbCostCenter(), new Integer(1006));
			
			clickOnApplyButton(productForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase3() {
	
		try {
			ProductForm productForm = setFormComponents("user3@soberano.syscoop.co", "products.zul");			
			loadObjectDetails("product3 : p3");			
			productForm.setComponentValue(productForm.getTxtCode(), "mp3");
			productForm.setComponentValue(productForm.getTxtName(), "mproduct3");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(3.096886));
			
			ComponentAgent cmbUnitAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("milliliter");
			productForm.setComponentValue(productForm.getCmbUnit(), new Integer(8));
			
			ComponentAgent decReferencePriceAgent = productForm.getDesktop().query("vbox").query("decimalbox").query("#decReferencePrice");
			InputAgent decReferencePriceInputAgent = decReferencePriceAgent.as(InputAgent.class);
			decReferencePriceInputAgent.type("0.0006");
			productForm.setComponentValue(productForm.getDecReferencePrice(), new BigDecimal(0.0006));
			
			ComponentAgent cmbCategoryAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat4");
			productForm.setComponentValue(productForm.getCmbCategory(), new Integer(1004));
			
			ComponentAgent cmbCostCenterAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc7");
			productForm.setComponentValue(productForm.getCmbCostCenter(), new Integer(1007));
			
			clickOnApplyButton(productForm.getDesktop());
			
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
	final void testCase4() {
	
		try {
			ProductForm productForm = setFormComponents("user4@soberano.syscoop.co", "products.zul");			
			loadObjectDetails("product3 : p3");			
			productForm.setComponentValue(productForm.getTxtCode(), "mp3");
			productForm.setComponentValue(productForm.getTxtName(), "mproduct3");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(3.096886));
			
			ComponentAgent cmbUnitAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("milliliter");
			productForm.setComponentValue(productForm.getCmbUnit(), new Integer(8));
			
			ComponentAgent decReferencePriceAgent = productForm.getDesktop().query("vbox").query("decimalbox").query("#decReferencePrice");
			InputAgent decReferencePriceInputAgent = decReferencePriceAgent.as(InputAgent.class);
			decReferencePriceInputAgent.type("0.0006");
			productForm.setComponentValue(productForm.getDecReferencePrice(), new BigDecimal(0.0006));
			
			ComponentAgent cmbCategoryAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat4");
			productForm.setComponentValue(productForm.getCmbCategory(), new Integer(1004));
			
			ComponentAgent cmbCostCenterAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc7");
			productForm.setComponentValue(productForm.getCmbCostCenter(), new Integer(1007));
			
			clickOnApplyButton(productForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase5() {
	
		try {
			ProductForm productForm = setFormComponents("user5@soberano.syscoop.co", "products.zul");			
			loadObjectDetails("product4 : p4");			
			productForm.setComponentValue(productForm.getTxtCode(), "mp4");
			productForm.setComponentValue(productForm.getTxtName(), "mproduct4");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(0.000567));
			
			ComponentAgent cmbUnitAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("pound");
			productForm.setComponentValue(productForm.getCmbUnit(), new Integer(5));
			
			ComponentAgent decPriceAgent = productForm.getDesktop().query("vbox").query("decimalbox").query("#decPrice");
			InputAgent decPriceInputAgent = decPriceAgent.as(InputAgent.class);
			decPriceInputAgent.type("45.6789");
			productForm.setComponentValue(productForm.getDecPrice(), new BigDecimal(45.6789));
			
			ComponentAgent cmbCategoryAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat4");
			productForm.setComponentValue(productForm.getCmbCategory(), new Integer(1004));
			
			ComponentAgent cmbCostCenterAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc8");
			productForm.setComponentValue(productForm.getCmbCostCenter(), new Integer(1008));
			
			clickOnApplyButton(productForm.getDesktop());
			
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
	final void testCase6() {
	
		try {
			ProductForm productForm = setFormComponents("user6@soberano.syscoop.co", "products.zul");			
			loadObjectDetails("product4 : p4");			
			productForm.setComponentValue(productForm.getTxtCode(), "mp4");
			productForm.setComponentValue(productForm.getTxtName(), "mproduct4");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(0.000567));
			
			ComponentAgent cmbUnitAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("pound");
			productForm.setComponentValue(productForm.getCmbUnit(), new Integer(5));
			
			ComponentAgent decPriceAgent = productForm.getDesktop().query("vbox").query("decimalbox").query("#decPrice");
			InputAgent decPriceInputAgent = decPriceAgent.as(InputAgent.class);
			decPriceInputAgent.type("45.6789");
			productForm.setComponentValue(productForm.getDecPrice(), new BigDecimal(45.6789));
			
			ComponentAgent cmbCategoryAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat4");
			productForm.setComponentValue(productForm.getCmbCategory(), new Integer(1004));
			
			ComponentAgent cmbCostCenterAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc8");
			productForm.setComponentValue(productForm.getCmbCostCenter(), new Integer(1008));
			
			clickOnApplyButton(productForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase7() {
	
		try {
			ProductForm productForm = setFormComponents("user7@soberano.syscoop.co", "products.zul");			
			loadObjectDetails("product5 : p5");			
			productForm.setComponentValue(productForm.getTxtCode(), "mp5");
			productForm.setComponentValue(productForm.getTxtName(), "mproduct5");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(5));
			
			ComponentAgent cmbUnitAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("piece");
			productForm.setComponentValue(productForm.getCmbUnit(), new Integer(1));
			
			ComponentAgent decPriceAgent = productForm.getDesktop().query("vbox").query("decimalbox").query("#decPrice");
			InputAgent decPriceInputAgent = decPriceAgent.as(InputAgent.class);
			decPriceInputAgent.type("876.123");
			productForm.setComponentValue(productForm.getDecPrice(), new BigDecimal(876.123));
			
			ComponentAgent cmbCategoryAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat7");
			productForm.setComponentValue(productForm.getCmbCategory(), new Integer(1007));
			
			ComponentAgent cmbCostCenterAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc9");
			productForm.setComponentValue(productForm.getCmbCostCenter(), new Integer(1009));
			
			clickOnApplyButton(productForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase8() {
	
		try {
			ProductForm productForm = setFormComponents("user8@soberano.syscoop.co", "products.zul");			
			loadObjectDetails("product6 : p6");			
			productForm.setComponentValue(productForm.getTxtCode(), "mp6");
			productForm.setComponentValue(productForm.getTxtName(), "mproduct6");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(345));
			
			ComponentAgent cmbUnitAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("kilogram");
			productForm.setComponentValue(productForm.getCmbUnit(), new Integer(2));
			
			ComponentAgent decPriceAgent = productForm.getDesktop().query("vbox").query("decimalbox").query("#decPrice");
			InputAgent decPriceInputAgent = decPriceAgent.as(InputAgent.class);
			decPriceInputAgent.type("3.45");
			productForm.setComponentValue(productForm.getDecPrice(), new BigDecimal(3.45));
			
			ComponentAgent cmbCategoryAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat7");
			productForm.setComponentValue(productForm.getCmbCategory(), new Integer(1007));
			
			ComponentAgent cmbCostCenterAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc10");
			productForm.setComponentValue(productForm.getCmbCostCenter(), new Integer(1010));
			
			clickOnApplyButton(productForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase9() {
	
		try {
			ProductForm productForm = setFormComponents("user9@soberano.syscoop.co", "products.zul");	
			loadObjectDetails("product7 : p7");			
			productForm.setComponentValue(productForm.getTxtCode(), "mp7");
			productForm.setComponentValue(productForm.getTxtName(), "mproduct7");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(6));
			
			ComponentAgent cmbUnitAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("piece");
			productForm.setComponentValue(productForm.getCmbUnit(), new Integer(1));
			
			ComponentAgent decReferencePriceAgent = productForm.getDesktop().query("vbox").query("decimalbox").query("#decReferencePrice");
			InputAgent decReferencePriceInputAgent = decReferencePriceAgent.as(InputAgent.class);
			decReferencePriceInputAgent.type("8.00998877");
			productForm.setComponentValue(productForm.getDecReferencePrice(), new BigDecimal(8.00998877));
			
			ComponentAgent cmbCategoryAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat8");
			productForm.setComponentValue(productForm.getCmbCategory(), new Integer(1008));
			
			ComponentAgent cmbCostCenterAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc4");
			productForm.setComponentValue(productForm.getCmbCostCenter(), new Integer(1004));
			
			clickOnApplyButton(productForm.getDesktop());
			
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
	final void testCase10() {
	
		try {
			ProductForm productForm = setFormComponents("user10@soberano.syscoop.co", "products.zul");	
			loadObjectDetails("product7 : p7");			
			productForm.setComponentValue(productForm.getTxtCode(), "mp7");
			productForm.setComponentValue(productForm.getTxtName(), "mproduct7");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(6));
			
			ComponentAgent cmbUnitAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("piece");
			productForm.setComponentValue(productForm.getCmbUnit(), new Integer(1));
			
			ComponentAgent decReferencePriceAgent = productForm.getDesktop().query("vbox").query("decimalbox").query("#decReferencePrice");
			InputAgent decReferencePriceInputAgent = decReferencePriceAgent.as(InputAgent.class);
			decReferencePriceInputAgent.type("8.00998877");
			productForm.setComponentValue(productForm.getDecReferencePrice(), new BigDecimal(8.00998877));
			
			ComponentAgent cmbCategoryAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat8");
			productForm.setComponentValue(productForm.getCmbCategory(), new Integer(1008));
			
			ComponentAgent cmbCostCenterAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc4");
			productForm.setComponentValue(productForm.getCmbCostCenter(), new Integer(1004));
			
			clickOnApplyButton(productForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase11() {
	
		try {
			ProductForm productForm = setFormComponents("user11@soberano.syscoop.co", "products.zul");	
			loadObjectDetails("product8 : p8");			
			productForm.setComponentValue(productForm.getTxtCode(), "mp8");
			productForm.setComponentValue(productForm.getTxtName(), "mproduct8");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(45656));
			
			ComponentAgent cmbUnitAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("ounce");
			productForm.setComponentValue(productForm.getCmbUnit(), new Integer(6));
			
			ComponentAgent decReferencePriceAgent = productForm.getDesktop().query("vbox").query("decimalbox").query("#decReferencePrice");
			InputAgent decReferencePriceInputAgent = decReferencePriceAgent.as(InputAgent.class);
			decReferencePriceInputAgent.type("0.0005");
			productForm.setComponentValue(productForm.getDecReferencePrice(), new BigDecimal(0.0005));
			
			ComponentAgent cmbCategoryAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat8");
			productForm.setComponentValue(productForm.getCmbCategory(), new Integer(1008));
			
			ComponentAgent cmbCostCenterAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc5");
			productForm.setComponentValue(productForm.getCmbCostCenter(), new Integer(1005));
			
			clickOnApplyButton(productForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase12() {
	
		try {
			ProductForm productForm = setFormComponents("user12@soberano.syscoop.co", "products.zul");	
			loadObjectDetails("product9 : p9");
			productForm.setComponentValue(productForm.getTxtCode(), "mp9");
			productForm.setComponentValue(productForm.getTxtName(), "mproduct9");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(8));
			
			ComponentAgent cmbUnitAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("piece");
			productForm.setComponentValue(productForm.getCmbUnit(), new Integer(1));
			
			ComponentAgent decReferencePriceAgent = productForm.getDesktop().query("vbox").query("decimalbox").query("#decReferencePrice");
			InputAgent decReferencePriceInputAgent = decReferencePriceAgent.as(InputAgent.class);
			decReferencePriceInputAgent.type("1.09878901");
			productForm.setComponentValue(productForm.getDecReferencePrice(), new BigDecimal(1.09878901));
			
			ComponentAgent cmbCategoryAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat8");
			productForm.setComponentValue(productForm.getCmbCategory(), new Integer(1008));
			
			ComponentAgent cmbCostCenterAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc6");
			productForm.setComponentValue(productForm.getCmbCostCenter(), new Integer(1006));
			
			clickOnApplyButton(productForm.getDesktop());
			
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
	final void testCase13() {
	
		try {
			ProductForm productForm = setFormComponents("user13@soberano.syscoop.co", "products.zul");	
			loadObjectDetails("product9 : p9");
			productForm.setComponentValue(productForm.getTxtCode(), "mp9");
			productForm.setComponentValue(productForm.getTxtName(), "mproduct9");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(8));
			
			ComponentAgent cmbUnitAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("piece");
			productForm.setComponentValue(productForm.getCmbUnit(), new Integer(1));
			
			ComponentAgent decReferencePriceAgent = productForm.getDesktop().query("vbox").query("decimalbox").query("#decReferencePrice");
			InputAgent decReferencePriceInputAgent = decReferencePriceAgent.as(InputAgent.class);
			decReferencePriceInputAgent.type("1.09878901");
			productForm.setComponentValue(productForm.getDecReferencePrice(), new BigDecimal(1.09878901));
			
			ComponentAgent cmbCategoryAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat8");
			productForm.setComponentValue(productForm.getCmbCategory(), new Integer(1008));
			
			ComponentAgent cmbCostCenterAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc6");
			productForm.setComponentValue(productForm.getCmbCostCenter(), new Integer(1006));
			
			clickOnApplyButton(productForm.getDesktop());
			
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
	final void testCase14() {
	
		try {
			ProductForm productForm = setFormComponents("user14@soberano.syscoop.co", "products.zul");	
			loadObjectDetails("product9 : p9");
			productForm.setComponentValue(productForm.getTxtCode(), "mp9");
			productForm.setComponentValue(productForm.getTxtName(), "mproduct9");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(8));
			
			ComponentAgent cmbUnitAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("piece");
			productForm.setComponentValue(productForm.getCmbUnit(), new Integer(1));
			
			ComponentAgent decReferencePriceAgent = productForm.getDesktop().query("vbox").query("decimalbox").query("#decReferencePrice");
			InputAgent decReferencePriceInputAgent = decReferencePriceAgent.as(InputAgent.class);
			decReferencePriceInputAgent.type("1.09878901");
			productForm.setComponentValue(productForm.getDecReferencePrice(), new BigDecimal(1.09878901));
			
			ComponentAgent cmbCategoryAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat8");
			productForm.setComponentValue(productForm.getCmbCategory(), new Integer(1008));
			
			ComponentAgent cmbCostCenterAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc6");
			productForm.setComponentValue(productForm.getCmbCostCenter(), new Integer(1006));
			
			clickOnApplyButton(productForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase15() {
	
		try {
			ProductForm productForm = setFormComponents("user1@soberano.syscoop.co", "products.zul");			
			loadObjectDetails("mproduct2 : mp2");			
			productForm.setComponentValue(productForm.getTxtCode(), "mp1");
			productForm.setComponentValue(productForm.getTxtName(), "mproduct10");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(567));
			
			ComponentAgent cmbUnitAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("milliliter");
			productForm.setComponentValue(productForm.getCmbUnit(), new Integer(8));
			
			ComponentAgent decPriceAgent = productForm.getDesktop().query("vbox").query("decimalbox").query("#decPrice");
			InputAgent decPriceInputAgent = decPriceAgent.as(InputAgent.class);
			decPriceInputAgent.type("1234");
			productForm.setComponentValue(productForm.getDecPrice(), new BigDecimal(1234));
			
			ComponentAgent cmbCategoryAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat8");
			productForm.setComponentValue(productForm.getCmbCategory(), new Integer(1008));
			
			ComponentAgent cmbCostCenterAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc6");
			productForm.setComponentValue(productForm.getCmbCostCenter(), new Integer(1006));
			
			clickOnApplyButton(productForm.getDesktop());
			
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
	final void testCase16() {
	
		try {
			ProductForm productForm = setFormComponents("user1@soberano.syscoop.co", "products.zul");			
			loadObjectDetails("mproduct2 : mp2");			
			productForm.setComponentValue(productForm.getTxtCode(), "mp10");
			productForm.setComponentValue(productForm.getTxtName(), "mproduct1");			
			productForm.setComponentValue(productForm.getDecMinimumInventoryLevel(), new BigDecimal(567));
			
			ComponentAgent cmbUnitAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("milliliter");
			productForm.setComponentValue(productForm.getCmbUnit(), new Integer(8));
			
			ComponentAgent decPriceAgent = productForm.getDesktop().query("vbox").query("decimalbox").query("#decPrice");
			InputAgent decPriceInputAgent = decPriceAgent.as(InputAgent.class);
			decPriceInputAgent.type("1234");
			productForm.setComponentValue(productForm.getDecPrice(), new BigDecimal(1234));
			
			ComponentAgent cmbCategoryAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbCategory");
			InputAgent cmbCategoryInputAgent = cmbCategoryAgent.as(InputAgent.class);
			cmbCategoryInputAgent.typing("mcat8");
			productForm.setComponentValue(productForm.getCmbCategory(), new Integer(1008));
			
			ComponentAgent cmbCostCenterAgent = productForm.getDesktop().query("vbox").query("combobox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc6");
			productForm.setComponentValue(productForm.getCmbCostCenter(), new Integer(1006));
			
			clickOnApplyButton(productForm.getDesktop());
			
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
