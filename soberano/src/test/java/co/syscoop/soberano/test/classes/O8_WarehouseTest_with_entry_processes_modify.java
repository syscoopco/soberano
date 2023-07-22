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
import org.zkoss.zats.mimic.operation.CheckAgent;
import org.zkoss.zats.mimic.operation.InputAgent;
import org.zkoss.zul.Combobox;

import co.syscoop.soberano.test.helper.WarehouseActionTest;
import co.syscoop.soberano.test.helper.WarehouseForm;

@Order(8)

//TODO: enable test
@Disabled

class O8_WarehouseTest_with_entry_processes_modify extends WarehouseActionTest {

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
			WarehouseForm warehouseForm = setFormComponents("user1@soberano.syscoop.co", "warehouses.zul");			
			loadObjectDetails("warehouse21 : w21");			
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "mwarehouse21");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "mw21");
			(warehouseForm.getDesktop().query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);
			(warehouseForm.getDesktop().query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);
			
			ComponentAgent cmbEntryProcessesAgent = warehouseForm.getDesktop().query("vbox").query("combobox").query("#cmbEntryProcesses");
			InputAgent cmbEntryProcessesInputAgent = cmbEntryProcessesAgent.as(InputAgent.class);
			cmbEntryProcessesInputAgent.typing("mpr4");
			
			warehouseForm.selectComboitemByLabel(cmbEntryProcessesAgent.as(Combobox.class), "mpr4");
			ComponentAgent btnAddEntryProcess = warehouseForm.getDesktop().query("vbox").query("combobox").query("#cmbEntryProcesses").getNextSibling();
			btnAddEntryProcess.click();
			
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
			loadObjectDetails("warehouse22 : w22");			
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "mwarehouse22");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "mw22");
			(warehouseForm.getDesktop().query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);
			(warehouseForm.getDesktop().query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);
			
			ComponentAgent btnRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#btnRowDeletion1003");
			btnRowDeletion.click();
			
			ComponentAgent cmbEntryProcessesAgent = warehouseForm.getDesktop().query("vbox").query("combobox").query("#cmbEntryProcesses");
			InputAgent cmbEntryProcessesInputAgent = cmbEntryProcessesAgent.as(InputAgent.class);
			cmbEntryProcessesInputAgent.typing("mpr4");
			warehouseForm.selectComboitemByLabel(cmbEntryProcessesAgent.as(Combobox.class), "mpr4");
			ComponentAgent btnAddEntryProcess = warehouseForm.getDesktop().query("vbox").query("combobox").query("#cmbEntryProcesses").getNextSibling();
			btnAddEntryProcess.click();
			
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
			loadObjectDetails("warehouse23 : w23");			
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "mwarehouse23");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "mw23");
			(warehouseForm.getDesktop().query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);
			(warehouseForm.getDesktop().query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);
			
			ComponentAgent btnRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#btnRowDeletion1006");
			btnRowDeletion.click();
			
			ComponentAgent cmbEntryProcessesAgent = warehouseForm.getDesktop().query("vbox").query("combobox").query("#cmbEntryProcesses");
			InputAgent cmbEntryProcessesInputAgent = cmbEntryProcessesAgent.as(InputAgent.class);
			cmbEntryProcessesInputAgent.typing("mpr7");
			warehouseForm.selectComboitemByLabel(cmbEntryProcessesAgent.as(Combobox.class), "mpr7");
			ComponentAgent btnAddEntryProcess = warehouseForm.getDesktop().query("vbox").query("combobox").query("#cmbEntryProcesses").getNextSibling();
			btnAddEntryProcess.click();
			
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
			loadObjectDetails("warehouse24 : w24");			
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "mwarehouse24");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "mw24");
			(warehouseForm.getDesktop().query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);
			(warehouseForm.getDesktop().query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);
			
			ComponentAgent btnRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#btnRowDeletion1007");
			btnRowDeletion.click();
			
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
			loadObjectDetails("warehouse24 : w24");			
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "mwarehouse24");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "mw24");
			(warehouseForm.getDesktop().query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);
			(warehouseForm.getDesktop().query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);
			
			ComponentAgent btnRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#btnRowDeletion1007");
			btnRowDeletion.click();
			
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
			loadObjectDetails("warehouse24 : w24");			
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "mwarehouse24");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "mw24");
			(warehouseForm.getDesktop().query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);
			(warehouseForm.getDesktop().query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);
			
			ComponentAgent btnRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#btnRowDeletion1007");
			btnRowDeletion.click();
			
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
			loadObjectDetails("warehouse25 : w25");			
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "mwarehouse25");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "mw25");
			(warehouseForm.getDesktop().query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);
			(warehouseForm.getDesktop().query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);
			
			ComponentAgent btnRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#btnRowDeletion1002");
			btnRowDeletion.click();
			
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
			loadObjectDetails("warehouse26 : w26");			
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "mwarehouse26");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "mw26");
			(warehouseForm.getDesktop().query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);
			(warehouseForm.getDesktop().query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);
			
			ComponentAgent cmbEntryProcessesAgent = warehouseForm.getDesktop().query("vbox").query("combobox").query("#cmbEntryProcesses");
			InputAgent cmbEntryProcessesInputAgent = cmbEntryProcessesAgent.as(InputAgent.class);
			cmbEntryProcessesInputAgent.typing("mpr4");
			warehouseForm.selectComboitemByLabel(cmbEntryProcessesAgent.as(Combobox.class), "mpr4");
			ComponentAgent btnAddEntryProcess = warehouseForm.getDesktop().query("vbox").query("combobox").query("#cmbEntryProcesses").getNextSibling();
			btnAddEntryProcess.click();
			
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
			loadObjectDetails("warehouse26 : w26");			
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "mwarehouse26");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "mw26");
			(warehouseForm.getDesktop().query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);
			(warehouseForm.getDesktop().query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);
			
			ComponentAgent cmbEntryProcessesAgent = warehouseForm.getDesktop().query("vbox").query("combobox").query("#cmbEntryProcesses");
			InputAgent cmbEntryProcessesInputAgent = cmbEntryProcessesAgent.as(InputAgent.class);
			cmbEntryProcessesInputAgent.typing("mpr4");
			warehouseForm.selectComboitemByLabel(cmbEntryProcessesAgent.as(Combobox.class), "mpr4");
			ComponentAgent btnAddEntryProcess = warehouseForm.getDesktop().query("vbox").query("combobox").query("#cmbEntryProcesses").getNextSibling();
			btnAddEntryProcess.click();
			
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
			loadObjectDetails("warehouse27 : w27");			
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "mwarehouse27");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "mw27");
			(warehouseForm.getDesktop().query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);
			(warehouseForm.getDesktop().query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);
			
			ComponentAgent btnRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#btnRowDeletion1006");
			btnRowDeletion.click();
			
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
			loadObjectDetails("warehouse28 : w28");			
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "mwarehouse28");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "mw28");
			(warehouseForm.getDesktop().query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);
			(warehouseForm.getDesktop().query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);
			
			ComponentAgent btnRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#btnRowDeletion1010");
			btnRowDeletion.click();
			
			ComponentAgent cmbEntryProcessesAgent = warehouseForm.getDesktop().query("vbox").query("combobox").query("#cmbEntryProcesses");
			InputAgent cmbEntryProcessesInputAgent = cmbEntryProcessesAgent.as(InputAgent.class);
			cmbEntryProcessesInputAgent.typing("mpr6");
			warehouseForm.selectComboitemByLabel(cmbEntryProcessesAgent.as(Combobox.class), "mpr6");
			ComponentAgent btnAddEntryProcess = warehouseForm.getDesktop().query("vbox").query("combobox").query("#cmbEntryProcesses").getNextSibling();
			btnAddEntryProcess.click();
			
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
			loadObjectDetails("warehouse29 : w29");			
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "mwarehouse29");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "mw29");
			(warehouseForm.getDesktop().query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);
			(warehouseForm.getDesktop().query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);
			
			ComponentAgent btnRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#btnRowDeletion1002");
			btnRowDeletion.click();
			
			ComponentAgent cmbEntryProcessesAgent = warehouseForm.getDesktop().query("vbox").query("combobox").query("#cmbEntryProcesses");
			InputAgent cmbEntryProcessesInputAgent = cmbEntryProcessesAgent.as(InputAgent.class);
			cmbEntryProcessesInputAgent.typing("mpr4");
			warehouseForm.selectComboitemByLabel(cmbEntryProcessesAgent.as(Combobox.class), "mpr4");
			ComponentAgent btnAddEntryProcess = warehouseForm.getDesktop().query("vbox").query("combobox").query("#cmbEntryProcesses").getNextSibling();
			btnAddEntryProcess.click();
			
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
			loadObjectDetails("warehouse30 : w30");			
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "mwarehouse30");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "mw30");
			(warehouseForm.getDesktop().query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);
			(warehouseForm.getDesktop().query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);
			
			ComponentAgent btnRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#btnRowDeletion1002");
			btnRowDeletion.click();
			
			ComponentAgent cmbEntryProcessesAgent = warehouseForm.getDesktop().query("vbox").query("combobox").query("#cmbEntryProcesses");
			InputAgent cmbEntryProcessesInputAgent = cmbEntryProcessesAgent.as(InputAgent.class);
			cmbEntryProcessesInputAgent.typing("mpr4");
			warehouseForm.selectComboitemByLabel(cmbEntryProcessesAgent.as(Combobox.class), "mpr4");
			ComponentAgent btnAddEntryProcess = warehouseForm.getDesktop().query("vbox").query("combobox").query("#cmbEntryProcesses").getNextSibling();
			btnAddEntryProcess.click();
			
			cmbEntryProcessesInputAgent.typing("mpr5");
			warehouseForm.selectComboitemByLabel(cmbEntryProcessesAgent.as(Combobox.class), "mpr5");
			btnAddEntryProcess.click();
			
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
			loadObjectDetails("warehouse30 : w30");			
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "mwarehouse30");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "mw30");
			(warehouseForm.getDesktop().query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);
			(warehouseForm.getDesktop().query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);
			
			ComponentAgent btnRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#btnRowDeletion1002");
			btnRowDeletion.click();
			
			ComponentAgent cmbEntryProcessesAgent = warehouseForm.getDesktop().query("vbox").query("combobox").query("#cmbEntryProcesses");
			InputAgent cmbEntryProcessesInputAgent = cmbEntryProcessesAgent.as(InputAgent.class);
			cmbEntryProcessesInputAgent.typing("mpr4");
			warehouseForm.selectComboitemByLabel(cmbEntryProcessesAgent.as(Combobox.class), "mpr4");
			ComponentAgent btnAddEntryProcess = warehouseForm.getDesktop().query("vbox").query("combobox").query("#cmbEntryProcesses").getNextSibling();
			btnAddEntryProcess.click();
			
			cmbEntryProcessesInputAgent.typing("mpr5");
			warehouseForm.selectComboitemByLabel(cmbEntryProcessesAgent.as(Combobox.class), "mpr5");
			btnAddEntryProcess.click();
			
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
			loadObjectDetails("warehouse30 : w30");			
			warehouseForm.setComponentValue(warehouseForm.getTxtName(), "mwarehouse30");
			warehouseForm.setComponentValue(warehouseForm.getTxtCode(), "mw30");
			(warehouseForm.getDesktop().query("checkbox").query("#chkProcurementWarehouse")).as(CheckAgent.class).check(false);
			(warehouseForm.getDesktop().query("checkbox").query("#chkSalesWarehouse")).as(CheckAgent.class).check(false);
			
			ComponentAgent btnRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#btnRowDeletion1002");
			btnRowDeletion.click();
			
			ComponentAgent cmbEntryProcessesAgent = warehouseForm.getDesktop().query("vbox").query("combobox").query("#cmbEntryProcesses");
			InputAgent cmbEntryProcessesInputAgent = cmbEntryProcessesAgent.as(InputAgent.class);
			cmbEntryProcessesInputAgent.typing("mpr4");
			warehouseForm.selectComboitemByLabel(cmbEntryProcessesAgent.as(Combobox.class), "mpr4");
			ComponentAgent btnAddEntryProcess = warehouseForm.getDesktop().query("vbox").query("combobox").query("#cmbEntryProcesses").getNextSibling();
			btnAddEntryProcess.click();
			
			cmbEntryProcessesInputAgent.typing("mpr5");
			warehouseForm.selectComboitemByLabel(cmbEntryProcessesAgent.as(Combobox.class), "mpr5");
			btnAddEntryProcess.click();
			
			clickOnApplyButton(warehouseForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
}
