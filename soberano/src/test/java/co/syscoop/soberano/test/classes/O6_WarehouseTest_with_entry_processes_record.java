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
import org.zkoss.zats.mimic.ComponentAgent;
import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zats.mimic.operation.CheckAgent;
import org.zkoss.zats.mimic.operation.InputAgent;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.test.helper.WarehouseActionTest;
import co.syscoop.soberano.test.helper.WarehouseForm;
import co.syscoop.soberano.util.SpringUtility;

@Order(6)

@Disabled

@TestMethodOrder(OrderAnnotation.class)
class O6_WarehouseTest_with_entry_processes_record extends WarehouseActionTest {
	
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
	@Order(1)
	final void testCase1() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class),
				(desktop.query("textbox").query("#cmbEntryProcesses")).as(Combobox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "warehouse21");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w21");
			(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);		
			(desktop.query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);
			
			ComponentAgent cmbEntryProcessesAgent = desktop.query("textbox").query("#cmbEntryProcesses");
			InputAgent cmbEntryProcessesInputAgent = cmbEntryProcessesAgent.as(InputAgent.class);
			cmbEntryProcessesInputAgent.typing("mpr2");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr2");
			ComponentAgent btnAddEntryProcess = desktop.query("textbox").query("#cmbEntryProcesses").getNextSibling();
			btnAddEntryProcess.click();
			
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
	@Order(2)
	final void testCase2() {

		SpringUtility.setLoggedUserForTesting("user2@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class),
				(desktop.query("textbox").query("#cmbEntryProcesses")).as(Combobox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "warehouse22");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w22");
			(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);		
			(desktop.query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);		
			
			ComponentAgent cmbEntryProcessesAgent = desktop.query("textbox").query("#cmbEntryProcesses");
			InputAgent cmbEntryProcessesInputAgent = cmbEntryProcessesAgent.as(InputAgent.class);
			cmbEntryProcessesInputAgent.typing("mpr2");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr2");
			ComponentAgent btnAddEntryProcess = desktop.query("textbox").query("#cmbEntryProcesses").getNextSibling();
			btnAddEntryProcess.click();
			
			cmbEntryProcessesInputAgent.typing("mpr3");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr3");
			btnAddEntryProcess.click();
			
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
	@Order(3)
	final void testCase3() {

		SpringUtility.setLoggedUserForTesting("user3@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class),
				(desktop.query("textbox").query("#cmbEntryProcesses")).as(Combobox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "warehouse23");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w23");
			(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);		
			(desktop.query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);		
			
			ComponentAgent cmbEntryProcessesAgent = desktop.query("textbox").query("#cmbEntryProcesses");
			InputAgent cmbEntryProcessesInputAgent = cmbEntryProcessesAgent.as(InputAgent.class);
			cmbEntryProcessesInputAgent.typing("mpr4");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr4");
			ComponentAgent btnAddEntryProcess = desktop.query("textbox").query("#cmbEntryProcesses").getNextSibling();
			btnAddEntryProcess.click();
			
			cmbEntryProcessesInputAgent.typing("mpr5");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr5");
			btnAddEntryProcess.click();
			
			cmbEntryProcessesInputAgent.typing("mpr6");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr6");
			btnAddEntryProcess.click();
			
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

		SpringUtility.setLoggedUserForTesting("user4@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class),
				(desktop.query("textbox").query("#cmbEntryProcesses")).as(Combobox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "warehouse24");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w24");
			(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);		
			(desktop.query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);	
			
			ComponentAgent cmbEntryProcessesAgent = desktop.query("textbox").query("#cmbEntryProcesses");
			InputAgent cmbEntryProcessesInputAgent = cmbEntryProcessesAgent.as(InputAgent.class);
			cmbEntryProcessesInputAgent.typing("mpr7");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr7");
			ComponentAgent btnAddEntryProcess = desktop.query("textbox").query("#cmbEntryProcesses").getNextSibling();
			btnAddEntryProcess.click();
			
			cmbEntryProcessesInputAgent.typing("mpr8");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr8");
			btnAddEntryProcess.click();
			
			cmbEntryProcessesInputAgent.typing("mpr9");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr9");
			btnAddEntryProcess.click();
			
			cmbEntryProcessesInputAgent.typing("mpr10");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr10");
			btnAddEntryProcess.click();
			
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

		SpringUtility.setLoggedUserForTesting("user5@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class),
				(desktop.query("textbox").query("#cmbEntryProcesses")).as(Combobox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "warehouse24");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w24");
			(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);		
			(desktop.query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);	
			
			ComponentAgent cmbEntryProcessesAgent = desktop.query("textbox").query("#cmbEntryProcesses");
			InputAgent cmbEntryProcessesInputAgent = cmbEntryProcessesAgent.as(InputAgent.class);
			cmbEntryProcessesInputAgent.typing("mpr7");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr7");
			ComponentAgent btnAddEntryProcess = desktop.query("textbox").query("#cmbEntryProcesses").getNextSibling();
			btnAddEntryProcess.click();
			
			cmbEntryProcessesInputAgent.typing("mpr8");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr8");
			btnAddEntryProcess.click();
			
			cmbEntryProcessesInputAgent.typing("mpr9");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr9");
			btnAddEntryProcess.click();
			
			cmbEntryProcessesInputAgent.typing("mpr10");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr10");
			btnAddEntryProcess.click();
			
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
	@Order(6)
	final void testCase6() {

		SpringUtility.setLoggedUserForTesting("user6@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class),
				(desktop.query("textbox").query("#cmbEntryProcesses")).as(Combobox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "warehouse24");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w24");
			(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);		
			(desktop.query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);		
			
			ComponentAgent cmbEntryProcessesAgent = desktop.query("textbox").query("#cmbEntryProcesses");
			InputAgent cmbEntryProcessesInputAgent = cmbEntryProcessesAgent.as(InputAgent.class);
			cmbEntryProcessesInputAgent.typing("mpr7");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr7");
			ComponentAgent btnAddEntryProcess = desktop.query("textbox").query("#cmbEntryProcesses").getNextSibling();
			btnAddEntryProcess.click();
			
			cmbEntryProcessesInputAgent.typing("mpr8");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr8");
			btnAddEntryProcess.click();
			
			cmbEntryProcessesInputAgent.typing("mpr9");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr9");
			btnAddEntryProcess.click();
			
			cmbEntryProcessesInputAgent.typing("mpr10");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr10");
			btnAddEntryProcess.click();
			
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
	@Order(7)
	final void testCase7() {

		SpringUtility.setLoggedUserForTesting("user7@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class),
				(desktop.query("textbox").query("#cmbEntryProcesses")).as(Combobox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "warehouse25");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w25");
			(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);		
			(desktop.query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);		
			
			ComponentAgent cmbEntryProcessesAgent = desktop.query("textbox").query("#cmbEntryProcesses");
			InputAgent cmbEntryProcessesInputAgent = cmbEntryProcessesAgent.as(InputAgent.class);
			cmbEntryProcessesInputAgent.typing("mpr2");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr2");
			ComponentAgent btnAddEntryProcess = desktop.query("textbox").query("#cmbEntryProcesses").getNextSibling();
			btnAddEntryProcess.click();
			
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

		SpringUtility.setLoggedUserForTesting("user8@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class),
				(desktop.query("textbox").query("#cmbEntryProcesses")).as(Combobox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "warehouse26");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w26");
			(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);		
			(desktop.query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);		
			
			ComponentAgent cmbEntryProcessesAgent = desktop.query("textbox").query("#cmbEntryProcesses");
			InputAgent cmbEntryProcessesInputAgent = cmbEntryProcessesAgent.as(InputAgent.class);
			cmbEntryProcessesInputAgent.typing("mpr2");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr2");
			ComponentAgent btnAddEntryProcess = desktop.query("textbox").query("#cmbEntryProcesses").getNextSibling();
			btnAddEntryProcess.click();
			
			cmbEntryProcessesInputAgent.typing("mpr3");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr3");
			btnAddEntryProcess.click();
			
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

		SpringUtility.setLoggedUserForTesting("user9@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class),
				(desktop.query("textbox").query("#cmbEntryProcesses")).as(Combobox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "warehouse26");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w26");
			(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);		
			(desktop.query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);		
			
			ComponentAgent cmbEntryProcessesAgent = desktop.query("textbox").query("#cmbEntryProcesses");
			InputAgent cmbEntryProcessesInputAgent = cmbEntryProcessesAgent.as(InputAgent.class);
			cmbEntryProcessesInputAgent.typing("mpr2");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr2");
			ComponentAgent btnAddEntryProcess = desktop.query("textbox").query("#cmbEntryProcesses").getNextSibling();
			btnAddEntryProcess.click();
			
			cmbEntryProcessesInputAgent.typing("mpr3");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr3");
			btnAddEntryProcess.click();
			
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

		SpringUtility.setLoggedUserForTesting("user10@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class),
				(desktop.query("textbox").query("#cmbEntryProcesses")).as(Combobox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "warehouse27");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w27");
			(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);		
			(desktop.query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);		
			
			ComponentAgent cmbEntryProcessesAgent = desktop.query("textbox").query("#cmbEntryProcesses");
			InputAgent cmbEntryProcessesInputAgent = cmbEntryProcessesAgent.as(InputAgent.class);
			cmbEntryProcessesInputAgent.typing("mpr4");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr4");
			ComponentAgent btnAddEntryProcess = desktop.query("textbox").query("#cmbEntryProcesses").getNextSibling();
			btnAddEntryProcess.click();
			
			cmbEntryProcessesInputAgent.typing("mpr5");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr5");
			btnAddEntryProcess.click();
			
			cmbEntryProcessesInputAgent.typing("mpr6");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr6");
			btnAddEntryProcess.click();
			
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
	@Order(11)
	final void testCase11() {

		SpringUtility.setLoggedUserForTesting("user11@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class),
				(desktop.query("textbox").query("#cmbEntryProcesses")).as(Combobox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "warehouse28");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w28");
			(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);		
			(desktop.query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);	
			
			ComponentAgent cmbEntryProcessesAgent = desktop.query("textbox").query("#cmbEntryProcesses");
			InputAgent cmbEntryProcessesInputAgent = cmbEntryProcessesAgent.as(InputAgent.class);
			cmbEntryProcessesInputAgent.typing("mpr7");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr7");
			ComponentAgent btnAddEntryProcess = desktop.query("textbox").query("#cmbEntryProcesses").getNextSibling();
			btnAddEntryProcess.click();
			
			cmbEntryProcessesInputAgent.typing("mpr8");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr8");
			btnAddEntryProcess.click();
			
			cmbEntryProcessesInputAgent.typing("mpr9");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr9");
			btnAddEntryProcess.click();
			
			cmbEntryProcessesInputAgent.typing("mpr10");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr10");
			btnAddEntryProcess.click();
			
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

		SpringUtility.setLoggedUserForTesting("user12@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class),
				(desktop.query("textbox").query("#cmbEntryProcesses")).as(Combobox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "warehouse29");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w29");
			(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);		
			(desktop.query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);		
			
			ComponentAgent cmbEntryProcessesAgent = desktop.query("textbox").query("#cmbEntryProcesses");
			InputAgent cmbEntryProcessesInputAgent = cmbEntryProcessesAgent.as(InputAgent.class);
			cmbEntryProcessesInputAgent.typing("mpr2");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr2");
			ComponentAgent btnAddEntryProcess = desktop.query("textbox").query("#cmbEntryProcesses").getNextSibling();
			btnAddEntryProcess.click();
			
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

		SpringUtility.setLoggedUserForTesting("user13@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class),
				(desktop.query("textbox").query("#cmbEntryProcesses")).as(Combobox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "warehouse30");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w30");
			(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);		
			(desktop.query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);	
			
			ComponentAgent cmbEntryProcessesAgent = desktop.query("textbox").query("#cmbEntryProcesses");
			InputAgent cmbEntryProcessesInputAgent = cmbEntryProcessesAgent.as(InputAgent.class);
			cmbEntryProcessesInputAgent.typing("mpr2");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr2");
			ComponentAgent btnAddEntryProcess = desktop.query("textbox").query("#cmbEntryProcesses").getNextSibling();
			btnAddEntryProcess.click();
			
			cmbEntryProcessesInputAgent.typing("mpr3");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr3");
			btnAddEntryProcess.click();
			
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
	@Order(14)
	final void testCase14() {

		SpringUtility.setLoggedUserForTesting("user14@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class),
				(desktop.query("textbox").query("#cmbEntryProcesses")).as(Combobox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "warehouse30");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w30");
			(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);		
			(desktop.query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);	
			
			ComponentAgent cmbEntryProcessesAgent = desktop.query("textbox").query("#cmbEntryProcesses");
			InputAgent cmbEntryProcessesInputAgent = cmbEntryProcessesAgent.as(InputAgent.class);
			cmbEntryProcessesInputAgent.typing("mpr2");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr2");
			ComponentAgent btnAddEntryProcess = desktop.query("textbox").query("#cmbEntryProcesses").getNextSibling();
			btnAddEntryProcess.click();
			
			cmbEntryProcessesInputAgent.typing("mpr3");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr3");
			btnAddEntryProcess.click();
			
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

		SpringUtility.setLoggedUserForTesting("user15@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_warehouse.zul");
		
		warehouseForm = new WarehouseForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
				(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(Checkbox.class),
				(desktop.query("checkbox").query("#chkSalesWarehouse")).as(Checkbox.class),
				(desktop.query("textbox").query("#cmbEntryProcesses")).as(Combobox.class));
		try {
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "warehouse30");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "w30");
			(desktop.query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);		
			(desktop.query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);		
			
			ComponentAgent cmbEntryProcessesAgent = desktop.query("textbox").query("#cmbEntryProcesses");
			InputAgent cmbEntryProcessesInputAgent = cmbEntryProcessesAgent.as(InputAgent.class);
			cmbEntryProcessesInputAgent.typing("mpr2");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr2");
			ComponentAgent btnAddEntryProcess = desktop.query("textbox").query("#cmbEntryProcesses").getNextSibling();
			btnAddEntryProcess.click();
			
			cmbEntryProcessesInputAgent.typing("mpr3");
			warehouseForm.selectComboitemByLabel(warehouseForm.getCmbEntryProcesses(), "mpr3");
			btnAddEntryProcess.click();
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
}