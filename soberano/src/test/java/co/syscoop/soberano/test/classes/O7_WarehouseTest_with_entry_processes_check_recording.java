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
import org.zkoss.lang.Library;
import org.zkoss.web.Attributes;
import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Treechildren;

import co.syscoop.soberano.test.helper.WarehouseActionTest;
import co.syscoop.soberano.util.SpringUtility;

@Order(7)

//@Disabled

class O7_WarehouseTest_with_entry_processes_check_recording extends WarehouseActionTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		Library.setProperty(Attributes.PREFERRED_LOCALE, "en"); //needed due to translated captions according 
		//to runtime locale not available under 
		//testing environment
		
		//Zats.init("./src/main/webapp");		
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
		//Zats.end();
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
			checkWarehouse("warehouse21",
							"w21",
							false,
							false,
							new String[] {"mpr2"});
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
			checkWarehouse("warehouse22",
							"w22",
							false,
							false,
							new String[] {"mpr2", "mpr3"});
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
			checkWarehouse("warehouse23",
							"w23",
							false,
							false,
							new String[] {"mpr4", "mpr5", "mpr6"});
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
			checkWarehouse("warehouse24",
							"w24",
							false,
							false,
							new String[] {"mpr7", "mpr8", "mpr9", "mpr10"});
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
			checkWarehouse("warehouse25",
							"w25",
							false,
							false,
							new String[] {"mpr2"});
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
			checkWarehouse("warehouse26",
							"w26",
							false,
							false,
							new String[] {"mpr2", "mpr3"});
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
			checkWarehouse("warehouse27",
							"w27",
							false,
							false,
							new String[] {"mpr4", "mpr5", "mpr6"});
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
			checkWarehouse("warehouse28",
							"w28",
							false,
							false,
							new String[] {"mpr7", "mpr8", "mpr9", "mpr10"});
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
			checkWarehouse("warehouse29",
							"w29",
							false,
							false,
							new String[] {"mpr2"});
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
			checkWarehouse("warehouse30",
							"w30",
							false,
							false,
							new String[] {"mpr2", "mpr3"});
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
}
