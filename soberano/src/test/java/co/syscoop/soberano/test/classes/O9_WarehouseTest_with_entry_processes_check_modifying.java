package co.syscoop.soberano.test.classes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Treechildren;

import co.syscoop.soberano.test.helper.WarehouseActionTest;
import co.syscoop.soberano.util.SpringUtility;

@Order(9)

//@Disabled

class O9_WarehouseTest_with_entry_processes_check_modifying extends WarehouseActionTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		Zats.init("./src/main/webapp");		
		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/warehouses.zul");
		
		cmbIntelliSearchAgent = desktop.query("center").query("combobox");
		cmbIntelliSearch = cmbIntelliSearchAgent.as(Combobox.class);		
		txtName = cmbIntelliSearchAgent.query("#incDetails").query("#txtName").as(Textbox.class);
		txtCode = cmbIntelliSearchAgent.query("#incDetails").query("#txtCode").as(Textbox.class);
		chkProcurementWarehouse = cmbIntelliSearchAgent.query("#incDetails").query("#chkProcurementWarehouse").as(Checkbox.class);
		chkSalesWarehouse = cmbIntelliSearchAgent.query("#incDetails").query("#chkSalesWarehouse").as(Checkbox.class);
		cmbEntryProcesses = cmbIntelliSearchAgent.query("#incDetails").query("#cmbEntryProcesses").as(Combobox.class);
		tchdnEntryProcesses = cmbIntelliSearchAgent.query("#incDetails").query("#tchdnEntryProcesses").as(Treechildren.class);
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
	final void testCase16() {
		
		try {
			checkWarehouse("mwarehouse21",
					"mw21",
					false,
					false,
					new String[] {"mpr2", "mpr4"});
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase17() {
		
		try {
			checkWarehouse("mwarehouse22",
					"mw22",
					false,
					false,
					new String[] {"mpr2", "mpr4"});
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase18() {
		
		try {
			checkWarehouse("mwarehouse23",
					"mw23",
					false,
					false,
					new String[] {"mpr4", "mpr5", "mpr7"});
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase19() {
		
		try {
			checkWarehouse("mwarehouse24",
					"mw24",
					false,
					false,
					new String[] {"mpr8", "mpr9", "mpr10"});
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase20() {
		
		try {
			checkWarehouse("mwarehouse25",
					"mw25",
					false,
					false,
					new String[] {});
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase21() {
		
		try {
			checkWarehouse("mwarehouse26",
					"mw26",
					false,
					false,
					new String[] {"mpr2", "mpr3", "mpr4"});
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase22() {
		
		try {
			checkWarehouse("mwarehouse27",
					"mw27",
					false,
					false,
					new String[] {"mpr4", "mpr5"});
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase23() {
		
		try {
			checkWarehouse("mwarehouse28",
					"mw28",
					false,
					false,
					new String[] {"mpr6", "mpr7", "mpr8", "mpr9"});
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase24() {
		
		try {
			checkWarehouse("mwarehouse29",
					"mw29",
					false,
					false,
					new String[] {"mpr4"});
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase25() {
		
		try {
			checkWarehouse("mwarehouse30",
					"mw30",
					false,
					false,
					new String[] {"mpr3", "mpr4", "mpr5"});
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
}
