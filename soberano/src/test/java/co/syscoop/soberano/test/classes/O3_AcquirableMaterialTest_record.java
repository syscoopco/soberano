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

import co.syscoop.soberano.test.helper.AcquirableMaterialActionTest;
import co.syscoop.soberano.test.helper.AcquirableMaterialForm;
import co.syscoop.soberano.util.SpringUtility;

@Order(3)
@Disabled
@TestMethodOrder(OrderAnnotation.class)
class O3_AcquirableMaterialTest_record extends AcquirableMaterialActionTest {
	
	protected AcquirableMaterialForm acquirableMaterialForm = null;

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
		DesktopAgent desktop = Zats.newClient().connect("/new_acquirable_material.zul");
		acquirableMaterialForm = new AcquirableMaterialForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class));
		try {
			//this is needed to execute only in the first test. it has to do with testing configuration.
			acquirableMaterialForm.testEachConstrainedObjectIsDeclared();
			acquirableMaterialForm.testEachDeclaredConstrainedObjectIsActuallyConstrained();
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
		DesktopAgent desktop = Zats.newClient().connect("/new_acquirable_material.zul");
		
		acquirableMaterialForm = new AcquirableMaterialForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class));
		try {
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "m1");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(1.0));
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("piece");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), 1);		
			
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			acquirableMaterialForm.testWrongValueException(ex);
		}
	}
	
	@Test
	@Order(2)
	final void testCase2() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_acquirable_material.zul");
		
		acquirableMaterialForm = new AcquirableMaterialForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class));
		try {
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "m1");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "material1");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(1.0));
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("");		
			
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			acquirableMaterialForm.testWrongValueException(ex);
		}
	}
	
	@Test
	@Order(3)
	final void testCase3() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_acquirable_material.zul");
		
		acquirableMaterialForm = new AcquirableMaterialForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class));
		try {
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "m1");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "material1");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(1.0));
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("piece");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), new Integer(1));		
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(4)
	final void testCase4() {

		SpringUtility.setLoggedUserForTesting("user2@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_acquirable_material.zul");
		
		acquirableMaterialForm = new AcquirableMaterialForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class));
		try {
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "m2");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "material2");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(1.5));
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("kilogram");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), new Integer(2));		
			
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
	@Order(5)
	final void testCase5() {

		SpringUtility.setLoggedUserForTesting("user3@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_acquirable_material.zul");
		
		acquirableMaterialForm = new AcquirableMaterialForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class));
		try {
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "m2");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "material2");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(1.5));
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("kilogram");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), new Integer(2));		
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(6)
	final void testCase6() {

		SpringUtility.setLoggedUserForTesting("user4@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_acquirable_material.zul");
		
		acquirableMaterialForm = new AcquirableMaterialForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class));
		try {
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "m3");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "material3");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(2));
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("pound");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), new Integer(5));		
			
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
	@Order(7)
	final void testCase7() {

		SpringUtility.setLoggedUserForTesting("user5@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_acquirable_material.zul");
		
		acquirableMaterialForm = new AcquirableMaterialForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class));
		try {
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "m3");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "material3");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(2));
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("pound");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), new Integer(5));		
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(8)
	final void testCase8() {

		SpringUtility.setLoggedUserForTesting("user6@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_acquirable_material.zul");
		
		acquirableMaterialForm = new AcquirableMaterialForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class));
		try {
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "m4");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "material4");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(2.5));
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("milligram");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), new Integer(4));		
			
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

		SpringUtility.setLoggedUserForTesting("user7@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_acquirable_material.zul");
		
		acquirableMaterialForm = new AcquirableMaterialForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class));
		try {
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "m4");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "material4");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(2.5));
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("milligram");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), new Integer(4));		
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(10)
	final void testCase10() {

		SpringUtility.setLoggedUserForTesting("user8@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_acquirable_material.zul");
		
		acquirableMaterialForm = new AcquirableMaterialForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class));
		try {
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "m5");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "material5");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(3));
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("pound");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), new Integer(5));		
			
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

		SpringUtility.setLoggedUserForTesting("user9@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_acquirable_material.zul");
		
		acquirableMaterialForm = new AcquirableMaterialForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class));
		try {
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "m5");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "material5");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(3));
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("pound");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), new Integer(5));		
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(12)
	final void testCase12() {

		SpringUtility.setLoggedUserForTesting("user10@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_acquirable_material.zul");
		
		acquirableMaterialForm = new AcquirableMaterialForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class));
		try {
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "m6");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "material6");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(4));
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("ounce");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), new Integer(6));		
			
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
	@Order(13)
	final void testCase13() {

		SpringUtility.setLoggedUserForTesting("user11@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_acquirable_material.zul");
		
		acquirableMaterialForm = new AcquirableMaterialForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class));
		try {
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "m6");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "material6");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(4));
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("ounce");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), new Integer(6));		
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(14)
	final void testCase14() {

		SpringUtility.setLoggedUserForTesting("user12@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_acquirable_material.zul");
		
		acquirableMaterialForm = new AcquirableMaterialForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class));
		try {
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "m7");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "material7");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(100));
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("milliliter");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), new Integer(8));		
			
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

		SpringUtility.setLoggedUserForTesting("user13@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_acquirable_material.zul");
		
		acquirableMaterialForm = new AcquirableMaterialForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class));
		try {
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "m7");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "material7");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(100));
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("milliliter");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), new Integer(8));		
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(16)
	final void testCase16() {

		SpringUtility.setLoggedUserForTesting("user14@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_acquirable_material.zul");
		
		acquirableMaterialForm = new AcquirableMaterialForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class));
		try {
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "m8");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "material8");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(100.00001));
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("milliliter");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), new Integer(8));		
			
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
	@Order(17)
	final void testCase17() {

		SpringUtility.setLoggedUserForTesting("user15@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_acquirable_material.zul");
		
		acquirableMaterialForm = new AcquirableMaterialForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class));
		try {
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "m8");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "material8");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(100.00001));
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("milliliter");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), new Integer(8));		
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(18)
	final void testCase18() {

		SpringUtility.setLoggedUserForTesting("user16@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_acquirable_material.zul");
		
		acquirableMaterialForm = new AcquirableMaterialForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class));
		try {
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "m9");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "material9");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(400.00004));
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("piece");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), new Integer(1));		
			
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

		SpringUtility.setLoggedUserForTesting("user17@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_acquirable_material.zul");
		
		acquirableMaterialForm = new AcquirableMaterialForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class));
		try {
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "m9");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "material9");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(400.00004));
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("piece");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), new Integer(1));		
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(20)
	final void testCase20() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_acquirable_material.zul");
		
		acquirableMaterialForm = new AcquirableMaterialForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class));
		try {
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "m1");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "material93");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(12800.00128));
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("milligram");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), new Integer(4));		
			
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
	@Order(21)
	final void testCase21() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_acquirable_material.zul");
		
		acquirableMaterialForm = new AcquirableMaterialForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("textbox").query("#cmbUnit")).as(Combobox.class),
				(desktop.query("decimalbox").query("#decMinimumInventoryLevel")).as(Decimalbox.class));
		try {
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtCode(), "m93");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getTxtName(), "material1");			
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getDecMinimumInventoryLevel(), new BigDecimal(12800.00128));
			ComponentAgent cmbUnitAgent = desktop.query("textbox").query("#cmbUnit");
			InputAgent cmbUnitInputAgent = cmbUnitAgent.as(InputAgent.class);
			cmbUnitInputAgent.typing("milligram");
			acquirableMaterialForm.setComponentValue(acquirableMaterialForm.getCmbUnit(), new Integer(4));		
			
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