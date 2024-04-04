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
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zats.mimic.operation.InputAgent;

import co.syscoop.soberano.test.helper.CostCenterActionTest;
import co.syscoop.soberano.test.helper.CostCenterForm;

@Order(9)

//TODO: enable test
////@Disabled

class O9_CostCenterTest_modify extends CostCenterActionTest {

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
	final void testCase1() {
	
		try {
			CostCenterForm costCenterForm = setFormComponents("user2@soberano.syscoop.co", "cost_centers.zul");			
			loadObjectDetails("cc2");			
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "mcc2");
			
			ComponentAgent cmbInputWarehouseAgent = cmbIntelliSearchAgent.query("#incDetails").query("#cmbInputWarehouse");
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw8");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), new Integer(1008));
			
			ComponentAgent cmbOutputWarehouseAgent = cmbIntelliSearchAgent.query("#incDetails").query("#cmbOutputWarehouse");
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw9");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), new Integer(1009));
			
			clickOnApplyButton(costCenterForm.getDesktop());
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
			CostCenterForm costCenterForm = setFormComponents("user3@soberano.syscoop.co", "cost_centers.zul");			
			loadObjectDetails("cc3");			
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "mcc3");
			
			ComponentAgent cmbInputWarehouseAgent = cmbIntelliSearchAgent.query("#incDetails").query("#cmbInputWarehouse");
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw9");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), new Integer(1009));
			
			ComponentAgent cmbOutputWarehouseAgent = cmbIntelliSearchAgent.query("#incDetails").query("#cmbOutputWarehouse");
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw10");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), new Integer(1010));
			
			clickOnApplyButton(costCenterForm.getDesktop());
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
			CostCenterForm costCenterForm = setFormComponents("user4@soberano.syscoop.co", "cost_centers.zul");			
			loadObjectDetails("cc4");			
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "mcc4");
			
			ComponentAgent cmbInputWarehouseAgent = cmbIntelliSearchAgent.query("#incDetails").query("#cmbInputWarehouse");
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw10");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), new Integer(1010));
			
			ComponentAgent cmbOutputWarehouseAgent = cmbIntelliSearchAgent.query("#incDetails").query("#cmbOutputWarehouse");
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw10");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), new Integer(1010));
			
			clickOnApplyButton(costCenterForm.getDesktop());
			
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
			CostCenterForm costCenterForm = setFormComponents("user5@soberano.syscoop.co", "cost_centers.zul");			
			loadObjectDetails("cc4");			
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "mcc4");
			
			ComponentAgent cmbInputWarehouseAgent = cmbIntelliSearchAgent.query("#incDetails").query("#cmbInputWarehouse");
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw10");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), new Integer(1010));
			
			ComponentAgent cmbOutputWarehouseAgent = cmbIntelliSearchAgent.query("#incDetails").query("#cmbOutputWarehouse");
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw10");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), new Integer(1010));
			
			clickOnApplyButton(costCenterForm.getDesktop());
			
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
	final void testCase5() {
	
		try {
			CostCenterForm costCenterForm = setFormComponents("user6@soberano.syscoop.co", "cost_centers.zul");			
			loadObjectDetails("cc4");			
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "mcc4");
			
			ComponentAgent cmbInputWarehouseAgent = cmbIntelliSearchAgent.query("#incDetails").query("#cmbInputWarehouse");
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw10");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), new Integer(1010));
			
			ComponentAgent cmbOutputWarehouseAgent = cmbIntelliSearchAgent.query("#incDetails").query("#cmbOutputWarehouse");
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw10");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), new Integer(1010));
			
			clickOnApplyButton(costCenterForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase6() {
	
		try {
			CostCenterForm costCenterForm = setFormComponents("user7@soberano.syscoop.co", "cost_centers.zul");			
			loadObjectDetails("cc5");			
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "mcc5");
			
			ComponentAgent cmbInputWarehouseAgent = cmbIntelliSearchAgent.query("#incDetails").query("#cmbInputWarehouse");
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw10");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), new Integer(1010));
			
			ComponentAgent cmbOutputWarehouseAgent = cmbIntelliSearchAgent.query("#incDetails").query("#cmbOutputWarehouse");
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw6");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), new Integer(1006));
			
			clickOnApplyButton(costCenterForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase7() {
	
		try {
			CostCenterForm costCenterForm = setFormComponents("user8@soberano.syscoop.co", "cost_centers.zul");			
			loadObjectDetails("cc6");			
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "mcc6");
			
			ComponentAgent cmbInputWarehouseAgent = cmbIntelliSearchAgent.query("#incDetails").query("#cmbInputWarehouse");
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw6");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), new Integer(1006));
			
			ComponentAgent cmbOutputWarehouseAgent = cmbIntelliSearchAgent.query("#incDetails").query("#cmbOutputWarehouse");
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw7");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), new Integer(1007));
			
			clickOnApplyButton(costCenterForm.getDesktop());
			
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
	final void testCase8() {
	
		try {
			CostCenterForm costCenterForm = setFormComponents("user9@soberano.syscoop.co", "cost_centers.zul");			
			loadObjectDetails("cc6");			
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "mcc6");
			
			ComponentAgent cmbInputWarehouseAgent = cmbIntelliSearchAgent.query("#incDetails").query("#cmbInputWarehouse");
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw6");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), new Integer(1006));
			
			ComponentAgent cmbOutputWarehouseAgent = cmbIntelliSearchAgent.query("#incDetails").query("#cmbOutputWarehouse");
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw7");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), new Integer(1007));
			
			clickOnApplyButton(costCenterForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase9() {
	
		try {
			CostCenterForm costCenterForm = setFormComponents("user10@soberano.syscoop.co", "cost_centers.zul");			
			loadObjectDetails("cc7");			
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "mcc7");
			
			ComponentAgent cmbInputWarehouseAgent = cmbIntelliSearchAgent.query("#incDetails").query("#cmbInputWarehouse");
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw7");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), new Integer(1007));
			
			ComponentAgent cmbOutputWarehouseAgent = cmbIntelliSearchAgent.query("#incDetails").query("#cmbOutputWarehouse");
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw8");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), new Integer(1008));
			
			clickOnApplyButton(costCenterForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase10() {
	
		try {
			CostCenterForm costCenterForm = setFormComponents("user11@soberano.syscoop.co", "cost_centers.zul");			
			loadObjectDetails("cc8");			
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "mcc8");
			
			ComponentAgent cmbInputWarehouseAgent = cmbIntelliSearchAgent.query("#incDetails").query("#cmbInputWarehouse");
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw8");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), new Integer(1008));
			
			ComponentAgent cmbOutputWarehouseAgent = cmbIntelliSearchAgent.query("#incDetails").query("#cmbOutputWarehouse");
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw9");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), new Integer(1009));
			
			clickOnApplyButton(costCenterForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase11() {
	
		try {
			CostCenterForm costCenterForm = setFormComponents("user12@soberano.syscoop.co", "cost_centers.zul");			
			loadObjectDetails("cc9");			
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "mcc9");
			
			ComponentAgent cmbInputWarehouseAgent = cmbIntelliSearchAgent.query("#incDetails").query("#cmbInputWarehouse");
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw9");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), new Integer(1009));
			
			ComponentAgent cmbOutputWarehouseAgent = cmbIntelliSearchAgent.query("#incDetails").query("#cmbOutputWarehouse");
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw10");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), new Integer(1010));
			
			clickOnApplyButton(costCenterForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase12() {
	
		try {
			CostCenterForm costCenterForm = setFormComponents("user13@soberano.syscoop.co", "cost_centers.zul");			
			loadObjectDetails("cc10");			
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "mcc10");
			
			ComponentAgent cmbInputWarehouseAgent = cmbIntelliSearchAgent.query("#incDetails").query("#cmbInputWarehouse");
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw10");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), new Integer(1010));
			
			ComponentAgent cmbOutputWarehouseAgent = cmbIntelliSearchAgent.query("#incDetails").query("#cmbOutputWarehouse");
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw6");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), new Integer(1006));
			
			clickOnApplyButton(costCenterForm.getDesktop());
			
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
			CostCenterForm costCenterForm = setFormComponents("user14@soberano.syscoop.co", "cost_centers.zul");			
			loadObjectDetails("cc10");			
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "mcc10");
			
			ComponentAgent cmbInputWarehouseAgent = cmbIntelliSearchAgent.query("#incDetails").query("#cmbInputWarehouse");
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw10");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), new Integer(1010));
			
			ComponentAgent cmbOutputWarehouseAgent = cmbIntelliSearchAgent.query("#incDetails").query("#cmbOutputWarehouse");
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw6");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), new Integer(1006));
			
			clickOnApplyButton(costCenterForm.getDesktop());
			
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
			CostCenterForm costCenterForm = setFormComponents("user15@soberano.syscoop.co", "cost_centers.zul");			
			loadObjectDetails("cc10");			
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "mcc10");
			
			ComponentAgent cmbInputWarehouseAgent = cmbIntelliSearchAgent.query("#incDetails").query("#cmbInputWarehouse");
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw10");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), new Integer(1010));
			
			ComponentAgent cmbOutputWarehouseAgent = cmbIntelliSearchAgent.query("#incDetails").query("#cmbOutputWarehouse");
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw6");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), new Integer(1006));
			
			clickOnApplyButton(costCenterForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase15() {
	
		try {
			CostCenterForm costCenterForm = setFormComponents("user1@soberano.syscoop.co", "cost_centers.zul");			
			loadObjectDetails("mcc4");			
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "mcc2");
			
			ComponentAgent cmbInputWarehouseAgent = cmbIntelliSearchAgent.query("#incDetails").query("#cmbInputWarehouse");
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw10");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), new Integer(1010));
			
			ComponentAgent cmbOutputWarehouseAgent = cmbIntelliSearchAgent.query("#incDetails").query("#cmbOutputWarehouse");
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw10");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), new Integer(1010));
			
			clickOnApplyButton(costCenterForm.getDesktop());
			
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
			CostCenterForm costCenterForm = setFormComponents("user2@soberano.syscoop.co", "cost_centers.zul");			
			loadObjectDetails("mcc4");			
			costCenterForm.setComponentValue(costCenterForm.getTxtName(), "mcc3");
			
			ComponentAgent cmbInputWarehouseAgent = cmbIntelliSearchAgent.query("#incDetails").query("#cmbInputWarehouse");
			InputAgent cmbInputWarehouseInputAgent = cmbInputWarehouseAgent.as(InputAgent.class);
			cmbInputWarehouseInputAgent.typing("mw10");
			costCenterForm.setComponentValue(costCenterForm.getCmbInputWarehouse(), new Integer(1010));
			
			ComponentAgent cmbOutputWarehouseAgent = cmbIntelliSearchAgent.query("#incDetails").query("#cmbOutputWarehouse");
			InputAgent cmbOutputWarehouseInputAgent = cmbOutputWarehouseAgent.as(InputAgent.class);
			cmbOutputWarehouseInputAgent.typing("mw10");
			costCenterForm.setComponentValue(costCenterForm.getCmbOutputWarehouse(), new Integer(1010));
			
			clickOnApplyButton(costCenterForm.getDesktop());
			
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