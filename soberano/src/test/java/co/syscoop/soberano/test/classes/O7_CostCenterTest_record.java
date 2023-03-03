package co.syscoop.soberano.test.classes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.opentest4j.AssertionFailedError;
import org.zkoss.zats.mimic.ComponentAgent;
import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zats.mimic.operation.InputAgent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.test.helper.CostCenterForm;
import co.syscoop.soberano.test.helper.ProductCategoryActionTest;
import co.syscoop.soberano.util.SpringUtility;

@Order(7)

//TODO: enable test
//@Disabled

@TestMethodOrder(OrderAnnotation.class)
class O7_CostCenterTest_record extends ProductCategoryActionTest {
	
	protected CostCenterForm costCenterForm = null;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
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
		DesktopAgent desktop = Zats.newClient().connect("/new_cost_center.zul");
		costCenterForm = new CostCenterForm(desktop,
									(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
									(desktop.query("combobox").query("#cmbInputWarehouse")).as(Combobox.class),
									(desktop.query("combobox").query("#cmbOutputWarehouse")).as(Combobox.class));
		try {
			//this is needed to execute only in the first test. it has to do with testing configuration.
			costCenterForm.testEachConstrainedObjectIsDeclared();
			costCenterForm.testEachDeclaredConstrainedObjectIsActuallyConstrained();
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
		DesktopAgent desktop = Zats.newClient().connect("/new_cost_center.zul");
		
		ComponentAgent cmbInputWarehouseAgent = desktop.query("combobox").query("#cmbInputWarehouse");
		ComponentAgent cmbOutputWarehouseAgent = desktop.query("combobox").query("#cmbOutputWarehouse");
		costCenterForm = new CostCenterForm(desktop,
										(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
										cmbInputWarehouseAgent.as(Combobox.class),
										cmbOutputWarehouseAgent.as(Combobox.class));
		try {
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "");
			
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw6");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), 1006);
			
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw7");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), 1007);		
						
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			costCenterForm.testWrongValueException(ex);
		}
	}
	
	@Test
	@Order(2)
	final void testCase2() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_cost_center.zul");
		
		ComponentAgent cmbInputWarehouseAgent = desktop.query("combobox").query("#cmbInputWarehouse");
		ComponentAgent cmbOutputWarehouseAgent = desktop.query("combobox").query("#cmbOutputWarehouse");
		costCenterForm = new CostCenterForm(desktop,
										(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
										cmbInputWarehouseAgent.as(Combobox.class),
										cmbOutputWarehouseAgent.as(Combobox.class));
		try {
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "cc1");
			
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw7");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), 1007);		
						
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			costCenterForm.testWrongValueException(ex);
		}
	}
	
	@Test
	@Order(3)
	final void testCase3() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_cost_center.zul");
		
		ComponentAgent cmbInputWarehouseAgent = desktop.query("combobox").query("#cmbInputWarehouse");
		ComponentAgent cmbOutputWarehouseAgent = desktop.query("combobox").query("#cmbOutputWarehouse");
		costCenterForm = new CostCenterForm(desktop,
										(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
										cmbInputWarehouseAgent.as(Combobox.class),
										cmbOutputWarehouseAgent.as(Combobox.class));
		try {
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "cc1");
			
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw6");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), 1006);		
						
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			costCenterForm.testWrongValueException(ex);
		}
	}
	
	@Test
	@Order(4)
	final void testCase4() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_cost_center.zul");
		
		ComponentAgent cmbInputWarehouseAgent = desktop.query("combobox").query("#cmbInputWarehouse");
		ComponentAgent cmbOutputWarehouseAgent = desktop.query("combobox").query("#cmbOutputWarehouse");
		costCenterForm = new CostCenterForm(desktop,
										(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
										cmbInputWarehouseAgent.as(Combobox.class),
										cmbOutputWarehouseAgent.as(Combobox.class));
		try {
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "cc1");
			
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw6");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), 1006);
			
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw7");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), 1007);		
						
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
	@Order(5)
	final void testCase5() {

		SpringUtility.setLoggedUserForTesting("user2@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_cost_center.zul");
		
		ComponentAgent cmbInputWarehouseAgent = desktop.query("combobox").query("#cmbInputWarehouse");
		ComponentAgent cmbOutputWarehouseAgent = desktop.query("combobox").query("#cmbOutputWarehouse");
		costCenterForm = new CostCenterForm(desktop,
										(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
										cmbInputWarehouseAgent.as(Combobox.class),
										cmbOutputWarehouseAgent.as(Combobox.class));
		try {
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "cc2");
			
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw7");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), 1007);
			
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw8");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), 1008);		
						
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
	@Order(6)
	final void testCase6() {

		SpringUtility.setLoggedUserForTesting("user3@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_cost_center.zul");
		
		ComponentAgent cmbInputWarehouseAgent = desktop.query("combobox").query("#cmbInputWarehouse");
		ComponentAgent cmbOutputWarehouseAgent = desktop.query("combobox").query("#cmbOutputWarehouse");
		costCenterForm = new CostCenterForm(desktop,
										(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
										cmbInputWarehouseAgent.as(Combobox.class),
										cmbOutputWarehouseAgent.as(Combobox.class));
		try {
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "cc3");
			
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw8");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), 1008);
			
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw9");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), 1009);		
						
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

		SpringUtility.setLoggedUserForTesting("user4@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_cost_center.zul");
		
		ComponentAgent cmbInputWarehouseAgent = desktop.query("combobox").query("#cmbInputWarehouse");
		ComponentAgent cmbOutputWarehouseAgent = desktop.query("combobox").query("#cmbOutputWarehouse");
		costCenterForm = new CostCenterForm(desktop,
										(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
										cmbInputWarehouseAgent.as(Combobox.class),
										cmbOutputWarehouseAgent.as(Combobox.class));
		try {
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "cc4");
			
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw9");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), 1009);
			
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw10");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), 1010);
			
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
	@Order(8)
	final void testCase8() {

		SpringUtility.setLoggedUserForTesting("user5@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_cost_center.zul");
		
		ComponentAgent cmbInputWarehouseAgent = desktop.query("combobox").query("#cmbInputWarehouse");
		ComponentAgent cmbOutputWarehouseAgent = desktop.query("combobox").query("#cmbOutputWarehouse");
		costCenterForm = new CostCenterForm(desktop,
										(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
										cmbInputWarehouseAgent.as(Combobox.class),
										cmbOutputWarehouseAgent.as(Combobox.class));
		try {
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "cc4");
			
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw9");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), 1009);
			
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw10");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), 1010);
			
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

		SpringUtility.setLoggedUserForTesting("user6@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_cost_center.zul");
		
		ComponentAgent cmbInputWarehouseAgent = desktop.query("combobox").query("#cmbInputWarehouse");
		ComponentAgent cmbOutputWarehouseAgent = desktop.query("combobox").query("#cmbOutputWarehouse");
		costCenterForm = new CostCenterForm(desktop,
										(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
										cmbInputWarehouseAgent.as(Combobox.class),
										cmbOutputWarehouseAgent.as(Combobox.class));
		try {
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "cc4");
			
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw9");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), 1009);
			
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw10");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), 1010);		
						
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

		SpringUtility.setLoggedUserForTesting("user7@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_cost_center.zul");
		
		ComponentAgent cmbInputWarehouseAgent = desktop.query("combobox").query("#cmbInputWarehouse");
		ComponentAgent cmbOutputWarehouseAgent = desktop.query("combobox").query("#cmbOutputWarehouse");
		costCenterForm = new CostCenterForm(desktop,
										(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
										cmbInputWarehouseAgent.as(Combobox.class),
										cmbOutputWarehouseAgent.as(Combobox.class));
		try {
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "cc5");
			
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw10");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), 1010);
			
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw10");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), 1010);		
						
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
	@Order(11)
	final void testCase11() {

		SpringUtility.setLoggedUserForTesting("user8@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_cost_center.zul");
		
		ComponentAgent cmbInputWarehouseAgent = desktop.query("combobox").query("#cmbInputWarehouse");
		ComponentAgent cmbOutputWarehouseAgent = desktop.query("combobox").query("#cmbOutputWarehouse");
		costCenterForm = new CostCenterForm(desktop,
										(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
										cmbInputWarehouseAgent.as(Combobox.class),
										cmbOutputWarehouseAgent.as(Combobox.class));
		try {
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "cc6");
			
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw10");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), 1010);
			
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw6");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), 1006);
			
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
	@Order(12)
	final void testCase12() {

		SpringUtility.setLoggedUserForTesting("user9@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_cost_center.zul");
		
		ComponentAgent cmbInputWarehouseAgent = desktop.query("combobox").query("#cmbInputWarehouse");
		ComponentAgent cmbOutputWarehouseAgent = desktop.query("combobox").query("#cmbOutputWarehouse");
		costCenterForm = new CostCenterForm(desktop,
										(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
										cmbInputWarehouseAgent.as(Combobox.class),
										cmbOutputWarehouseAgent.as(Combobox.class));
		try {
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "cc6");
			
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw10");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), 1010);
			
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw6");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), 1006);
						
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

		SpringUtility.setLoggedUserForTesting("user10@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_cost_center.zul");
		
		ComponentAgent cmbInputWarehouseAgent = desktop.query("combobox").query("#cmbInputWarehouse");
		ComponentAgent cmbOutputWarehouseAgent = desktop.query("combobox").query("#cmbOutputWarehouse");
		costCenterForm = new CostCenterForm(desktop,
										(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
										cmbInputWarehouseAgent.as(Combobox.class),
										cmbOutputWarehouseAgent.as(Combobox.class));
		try {
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "cc7");
			
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw6");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), 1006);
			
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw7");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), 1007);		
						
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

		SpringUtility.setLoggedUserForTesting("user11@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_cost_center.zul");
		
		ComponentAgent cmbInputWarehouseAgent = desktop.query("combobox").query("#cmbInputWarehouse");
		ComponentAgent cmbOutputWarehouseAgent = desktop.query("combobox").query("#cmbOutputWarehouse");
		costCenterForm = new CostCenterForm(desktop,
										(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
										cmbInputWarehouseAgent.as(Combobox.class),
										cmbOutputWarehouseAgent.as(Combobox.class));
		try {
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "cc8");
			
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw7");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), 1007);
			
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw8");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), 1008);		
						
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
	@Order(15)
	final void testCase15() {

		SpringUtility.setLoggedUserForTesting("user12@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_cost_center.zul");
		
		ComponentAgent cmbInputWarehouseAgent = desktop.query("combobox").query("#cmbInputWarehouse");
		ComponentAgent cmbOutputWarehouseAgent = desktop.query("combobox").query("#cmbOutputWarehouse");
		costCenterForm = new CostCenterForm(desktop,
										(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
										cmbInputWarehouseAgent.as(Combobox.class),
										cmbOutputWarehouseAgent.as(Combobox.class));
		try {
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "cc9");
			
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw8");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), 1008);
			
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw9");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), 1009);		
						
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

		SpringUtility.setLoggedUserForTesting("user13@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_cost_center.zul");
		
		ComponentAgent cmbInputWarehouseAgent = desktop.query("combobox").query("#cmbInputWarehouse");
		ComponentAgent cmbOutputWarehouseAgent = desktop.query("combobox").query("#cmbOutputWarehouse");
		costCenterForm = new CostCenterForm(desktop,
										(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
										cmbInputWarehouseAgent.as(Combobox.class),
										cmbOutputWarehouseAgent.as(Combobox.class));
		try {
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "cc10");
			
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw9");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), 1009);
			
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw9");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), 1009);
			
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

		SpringUtility.setLoggedUserForTesting("user14@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_cost_center.zul");
		
		ComponentAgent cmbInputWarehouseAgent = desktop.query("combobox").query("#cmbInputWarehouse");
		ComponentAgent cmbOutputWarehouseAgent = desktop.query("combobox").query("#cmbOutputWarehouse");
		costCenterForm = new CostCenterForm(desktop,
										(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
										cmbInputWarehouseAgent.as(Combobox.class),
										cmbOutputWarehouseAgent.as(Combobox.class));
		try {
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "cc10");
			
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw9");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), 1009);
			
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw9");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), 1009);
			
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

		SpringUtility.setLoggedUserForTesting("user15@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_cost_center.zul");
		
		ComponentAgent cmbInputWarehouseAgent = desktop.query("combobox").query("#cmbInputWarehouse");
		ComponentAgent cmbOutputWarehouseAgent = desktop.query("combobox").query("#cmbOutputWarehouse");
		costCenterForm = new CostCenterForm(desktop,
										(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
										cmbInputWarehouseAgent.as(Combobox.class),
										cmbOutputWarehouseAgent.as(Combobox.class));
		try {
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "cc10");
			
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw9");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), 1009);
			
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw9");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), 1009);		
						
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
	@Order(19)
	final void testCase19() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_cost_center.zul");
		
		ComponentAgent cmbInputWarehouseAgent = desktop.query("combobox").query("#cmbInputWarehouse");
		ComponentAgent cmbOutputWarehouseAgent = desktop.query("combobox").query("#cmbOutputWarehouse");
		costCenterForm = new CostCenterForm(desktop,
										(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
										cmbInputWarehouseAgent.as(Combobox.class),
										cmbOutputWarehouseAgent.as(Combobox.class));
		try {
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "cc1");
			
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw6");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), 1006);
			
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw6");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), 1006);
			
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
	@Order(20)
	final void testCase20() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_cost_center.zul");
		
		ComponentAgent cmbInputWarehouseAgent = desktop.query("combobox").query("#cmbInputWarehouse");
		ComponentAgent cmbOutputWarehouseAgent = desktop.query("combobox").query("#cmbOutputWarehouse");
		costCenterForm = new CostCenterForm(desktop,
										(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
										cmbInputWarehouseAgent.as(Combobox.class),
										cmbOutputWarehouseAgent.as(Combobox.class));
		try {
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "cc2");
			
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw6");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), 1006);
			
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw7");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), 1007);
			
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