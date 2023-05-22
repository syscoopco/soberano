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
import org.zkoss.zats.mimic.ComponentAgent;
import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zats.mimic.operation.InputAgent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.test.helper.CostCenterActionTest;
import co.syscoop.soberano.test.helper.CostCenterForm;
import co.syscoop.soberano.test.helper.TestUtilityCode;
import co.syscoop.soberano.util.SpringUtility;

@Order(10)

//TODO: enable test
//@Disabled

class OO10_CostCenterTest_check_modifying extends CostCenterActionTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		Zats.init("./src/main/webapp");		
		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/cost_centers.zul");
		
		cmbIntelliSearchAgent = desktop.query("combobox");
		cmbIntelliSearch = cmbIntelliSearchAgent.as(Combobox.class);		
		txtName = cmbIntelliSearchAgent.query("#incDetails").query("#txtName").as(Textbox.class);
		cmbInputWarehouse = cmbIntelliSearchAgent.query("#incDetails").query("#cmbInputWarehouse").as(Combobox.class);
		cmbOutputWarehouse = cmbIntelliSearchAgent.query("#incDetails").query("#cmbOutputWarehouse").as(Combobox.class);
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
	final void testCase17() {
		
		try {
			CostCenterForm costCenterForm = setFormComponents("user1@soberano.syscoop.co", "cost_centers.zul");		
			checkCostCenter(costCenterForm,
							"cc1",
							"mwarehouse6 : mw6",
							"mwarehouse7 : mw7");
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
			CostCenterForm costCenterForm = setFormComponents("user1@soberano.syscoop.co", "cost_centers.zul");		
			checkCostCenter(costCenterForm,
							"mcc2",
							"mwarehouse8 : mw8",
							"mwarehouse9 : mw9");
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
			CostCenterForm costCenterForm = setFormComponents("user1@soberano.syscoop.co", "cost_centers.zul");		
			checkCostCenter(costCenterForm,
							"mcc3",
							"mwarehouse9 : mw9",
							"mwarehouse10 : mw10");
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
			CostCenterForm costCenterForm = setFormComponents("user1@soberano.syscoop.co", "cost_centers.zul");		
			checkCostCenter(costCenterForm,
							"mcc4",
							"mwarehouse10 : mw10",
							"mwarehouse10 : mw10");
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
			CostCenterForm costCenterForm = setFormComponents("user1@soberano.syscoop.co", "cost_centers.zul");		
			checkCostCenter(costCenterForm,
							"mcc5",
							"mwarehouse10 : mw10",
							"mwarehouse6 : mw6");
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
			CostCenterForm costCenterForm = setFormComponents("user1@soberano.syscoop.co", "cost_centers.zul");		
			checkCostCenter(costCenterForm,
							"mcc6",
							"mwarehouse6 : mw6",
							"mwarehouse7 : mw7");
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
			CostCenterForm costCenterForm = setFormComponents("user1@soberano.syscoop.co", "cost_centers.zul");		
			checkCostCenter(costCenterForm,
							"mcc7",
							"mwarehouse7 : mw7",
							"mwarehouse8 : mw8");
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
			CostCenterForm costCenterForm = setFormComponents("user1@soberano.syscoop.co", "cost_centers.zul");		
			checkCostCenter(costCenterForm,
							"mcc8",
							"mwarehouse8 : mw8",
							"mwarehouse9 : mw9");
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
			CostCenterForm costCenterForm = setFormComponents("user1@soberano.syscoop.co", "cost_centers.zul");		
			checkCostCenter(costCenterForm,
							"mcc9",
							"mwarehouse9 : mw9",
							"mwarehouse10 : mw10");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase26() {
		
		try {
			CostCenterForm costCenterForm = setFormComponents("user1@soberano.syscoop.co", "cost_centers.zul");		
			checkCostCenter(costCenterForm,
							"mcc10",
							"mwarehouse10 : mw10",
							"mwarehouse6 : mw6");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
}