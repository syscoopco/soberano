package co.syscoop.soberano.test.classes;

import static org.junit.jupiter.api.Assertions.*;

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
import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zats.mimic.operation.CheckAgent;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.test.helper.WarehouseActionTest;
import co.syscoop.soberano.test.helper.WarehouseForm;
import co.syscoop.soberano.util.SpringUtility;

@Order(3)

@Disabled

@TestMethodOrder(OrderAnnotation.class)
class O3_WarehouseTest_record extends WarehouseActionTest {
	
	protected WarehouseForm warehouseForm = null;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		Library.setProperty(Attributes.PREFERRED_LOCALE, "en"); //needed due to translated captions according 
		//to runtime locale not available under 
		//testing environment
		
		//Zats.init("./src/main/webapp");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		Zats.cleanup();
		//Zats.end();
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
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		warehouseForm = new WarehouseForm(desktop,
									(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
									(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
									(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class));
		try {
			//this is needed to execute only in the first test. it has to do with testing configuration.
			warehouseForm.testEachConstrainedObjectIsDeclared();
			warehouseForm.testEachDeclaredConstrainedObjectIsActuallyConstrained();
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
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w1");		
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			warehouseForm.testWrongValueException(ex);
		}
	}
	
	@Test
	@Order(2)
	final void testCase2() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "warehouse1");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "");		
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			warehouseForm.testWrongValueException(ex);
		}
	}
		
	@Test
	@Order(3)
	final void testCase3() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "warehouse1");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w1");
			(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(true);		
			(desktop.query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(true);		
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
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "warehouse2");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w2");
			(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);		
			(desktop.query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(true);		
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
	@Order(5)
	final void testCase5() {

		SpringUtility.setLoggedUserForTesting("user3@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "warehouse3");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w3");
			(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(true);		
			(desktop.query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);		
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
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "warehouse4");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w4");
			(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);		
			(desktop.query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);		
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
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "warehouse4");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w4");
			(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);		
			(desktop.query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);		
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

		SpringUtility.setLoggedUserForTesting("user6@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "warehouse4");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w4");
			(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);		
			(desktop.query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);		
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
	@Order(9)
	final void testCase9() {

		SpringUtility.setLoggedUserForTesting("user7@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "warehouse5");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w5");
			(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(true);		
			(desktop.query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);		
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
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "warehouse6");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w6");
			(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);		
			(desktop.query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(true);		
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
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "warehouse6");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w6");
			(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);		
			(desktop.query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(true);		
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
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "warehouse7");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w7");
			(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);		
			(desktop.query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(true);		
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
	@Order(13)
	final void testCase13() {

		SpringUtility.setLoggedUserForTesting("user11@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "warehouse8");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w8");
			(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);		
			(desktop.query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);		
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
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "warehouse9");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w9");
			(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);		
			(desktop.query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);		
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
	@Order(15)
	final void testCase15() {

		SpringUtility.setLoggedUserForTesting("user13@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "warehouse10");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w10");
			(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(true);		
			(desktop.query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(true);		
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
	@Order(16)
	final void testCase16() {

		SpringUtility.setLoggedUserForTesting("user14@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "warehouse10");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w10");
			(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(true);		
			(desktop.query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(true);		
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
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "warehouse10");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w10");
			(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);		
			(desktop.query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);		
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

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "warehouse7");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w11");
			(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(true);		
			(desktop.query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(true);	
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
	@Order(19)
	final void testCase19() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "procurement");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w5");
			(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(true);		
			(desktop.query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(true);	
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