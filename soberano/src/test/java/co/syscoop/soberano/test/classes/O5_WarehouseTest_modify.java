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
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zats.mimic.operation.CheckAgent;

import co.syscoop.soberano.test.helper.WarehouseActionTest;
import co.syscoop.soberano.test.helper.WarehouseForm;

@Order(5)

@Disabled

class O5_WarehouseTest_modify extends WarehouseActionTest {

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
			WarehouseForm warehouseForm = setFormComponents("user1@soberano.syscoop.co", "warehouses.zul");			
			loadObjectDetails("warehouse1 : w1");			
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "mwarehouse1");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "mw1");
			(warehouseForm.getDesktop().query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);
			(warehouseForm.getDesktop().query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);
			clickOnApplyButton(warehouseForm.getDesktop());
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
			WarehouseForm warehouseForm = setFormComponents("user2@soberano.syscoop.co", "warehouses.zul");			
			loadObjectDetails("warehouse2 : w2");			
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "mwarehouse2");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "mw2");
			(warehouseForm.getDesktop().query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(true);
			(warehouseForm.getDesktop().query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);
			clickOnApplyButton(warehouseForm.getDesktop());
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
			WarehouseForm warehouseForm = setFormComponents("user3@soberano.syscoop.co", "warehouses.zul");			
			loadObjectDetails("warehouse3 : w3");			
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "mwarehouse3");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "mw3");
			(warehouseForm.getDesktop().query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);
			(warehouseForm.getDesktop().query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(true);
			clickOnApplyButton(warehouseForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase4() {
	
		try {
			WarehouseForm warehouseForm = setFormComponents("user4@soberano.syscoop.co", "warehouses.zul");			
			loadObjectDetails("warehouse4 : w4");			
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "mwarehouse4");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "mw4");
			(warehouseForm.getDesktop().query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(true);
			(warehouseForm.getDesktop().query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(true);
			clickOnApplyButton(warehouseForm.getDesktop());

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
			WarehouseForm warehouseForm = setFormComponents("user5@soberano.syscoop.co", "warehouses.zul");			
			loadObjectDetails("warehouse4 : w4");			
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "mwarehouse4");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "mw4");
			(warehouseForm.getDesktop().query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(true);
			(warehouseForm.getDesktop().query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(true);
			clickOnApplyButton(warehouseForm.getDesktop());

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
			WarehouseForm warehouseForm = setFormComponents("user6@soberano.syscoop.co", "warehouses.zul");			
			loadObjectDetails("warehouse4 : w4");			
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "mwarehouse4");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "mw4");
			(warehouseForm.getDesktop().query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(true);
			(warehouseForm.getDesktop().query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(true);
			clickOnApplyButton(warehouseForm.getDesktop());
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
			WarehouseForm warehouseForm = setFormComponents("user7@soberano.syscoop.co", "warehouses.zul");			
			loadObjectDetails("warehouse5 : w5");			
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "mwarehouse5");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "mw5");
			(warehouseForm.getDesktop().query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);
			(warehouseForm.getDesktop().query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(true);
			clickOnApplyButton(warehouseForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase8() {
	
		try {
			WarehouseForm warehouseForm = setFormComponents("user8@soberano.syscoop.co", "warehouses.zul");			
			loadObjectDetails("warehouse6 : w6");			
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "mwarehouse6");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "mw6");
			(warehouseForm.getDesktop().query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(true);
			(warehouseForm.getDesktop().query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);
			clickOnApplyButton(warehouseForm.getDesktop());

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
	final void testCase9() {
	
		try {
			WarehouseForm warehouseForm = setFormComponents("user9@soberano.syscoop.co", "warehouses.zul");			
			loadObjectDetails("warehouse6 : w6");			
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "mwarehouse6");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "mw6");
			(warehouseForm.getDesktop().query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(true);
			(warehouseForm.getDesktop().query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);
			clickOnApplyButton(warehouseForm.getDesktop());
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
			WarehouseForm warehouseForm = setFormComponents("user10@soberano.syscoop.co", "warehouses.zul");			
			loadObjectDetails("warehouse7 : w7");			
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "mwarehouse7");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "mw7");
			(warehouseForm.getDesktop().query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(true);
			(warehouseForm.getDesktop().query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);
			clickOnApplyButton(warehouseForm.getDesktop());
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
			WarehouseForm warehouseForm = setFormComponents("user11@soberano.syscoop.co", "warehouses.zul");			
			loadObjectDetails("warehouse8 : w8");			
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "mwarehouse8");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "mw8");
			(warehouseForm.getDesktop().query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(true);
			(warehouseForm.getDesktop().query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(true);
			clickOnApplyButton(warehouseForm.getDesktop());
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
			WarehouseForm warehouseForm = setFormComponents("user12@soberano.syscoop.co", "warehouses.zul");			
			loadObjectDetails("warehouse9 : w9");			
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "mwarehouse9");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "mw9");
			(warehouseForm.getDesktop().query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);
			(warehouseForm.getDesktop().query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(true);
			clickOnApplyButton(warehouseForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase13() {
	
		try {
			WarehouseForm warehouseForm = setFormComponents("user13@soberano.syscoop.co", "warehouses.zul");			
			loadObjectDetails("warehouse10 : w10");			
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "mwarehouse10");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "mw10");
			(warehouseForm.getDesktop().query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);
			(warehouseForm.getDesktop().query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);
			clickOnApplyButton(warehouseForm.getDesktop());

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
			WarehouseForm warehouseForm = setFormComponents("user14@soberano.syscoop.co", "warehouses.zul");			
			loadObjectDetails("warehouse10 : w10");			
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "mwarehouse10");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "mw10");
			(warehouseForm.getDesktop().query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);
			(warehouseForm.getDesktop().query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);
			clickOnApplyButton(warehouseForm.getDesktop());

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
	final void testCase15() {
	
		try {
			WarehouseForm warehouseForm = setFormComponents("user15@soberano.syscoop.co", "warehouses.zul");			
			loadObjectDetails("warehouse10 : w10");			
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "mwarehouse10");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "mw10");
			(warehouseForm.getDesktop().query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(true);
			(warehouseForm.getDesktop().query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);
			clickOnApplyButton(warehouseForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase16() {
	
		try {
			WarehouseForm warehouseForm = setFormComponents("user1@soberano.syscoop.co", "warehouses.zul");			
			loadObjectDetails("mwarehouse10 : mw10");			
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "mwarehouse7");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "mw10");
			(warehouseForm.getDesktop().query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(true);
			(warehouseForm.getDesktop().query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(true);
			clickOnApplyButton(warehouseForm.getDesktop());
			
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
	final void testCase17() {
	
		try {
			WarehouseForm warehouseForm = setFormComponents("user1@soberano.syscoop.co", "warehouses.zul");			
			loadObjectDetails("mwarehouse10 : mw10");			
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "mwarehouse10");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "mw7");
			(warehouseForm.getDesktop().query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(true);
			(warehouseForm.getDesktop().query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(true);
			clickOnApplyButton(warehouseForm.getDesktop());
			
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
