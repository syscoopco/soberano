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
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;

import co.syscoop.soberano.test.helper.InventoryOperationActionTest;
import co.syscoop.soberano.test.helper.InventoryOperationForm;
import co.syscoop.soberano.util.SpringUtility;

@Order(14)

//TODO: enable test
//@Disabled

@TestMethodOrder(OrderAnnotation.class)
class OO14_InventoryOperationTest_record extends InventoryOperationActionTest {
	
	protected InventoryOperationForm inventoryOperationForm = null;

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
	@Order(1)
	final void testCase1() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/inventory_operations.zul");
		InventoryOperationForm inventoryOperationForm = new InventoryOperationForm(desktop,
																					desktop.query("combobox").query("#cmbFromWarehouse").as(Combobox.class),
																					desktop.query("combobox").query("#cmbToWarehouse").as(Combobox.class),
																					desktop.query("combobox").query("#cmbWorker").as(Combobox.class),
																					desktop.query("combobox").query("#cmbMaterial").as(Combobox.class),
																					desktop.query("decimalbox").query("#decQuantity").as(Decimalbox.class),
																					desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
																					desktop.query("south").query("button").query("#btnRecord").as(Button.class),
																					desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			ComponentAgent cmbToWarehouseAgent = desktop.query("combobox").query("#cmbToWarehouse");
			InputAgent cmbToWarehouseInputAgent = cmbToWarehouseAgent.as(InputAgent.class);
			cmbToWarehouseInputAgent.typing("mwarehouse6 : mw6");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbToWarehouse(), new Integer(1006));
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user2@");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbWorker(), new Integer(1002));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mmaterial2 : mm2");
			inventoryOperationForm.selectComboitemByLabel(inventoryOperationForm.getCmbMaterial(), "mmaterial2 : mm2");
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getDecQuantity(), new BigDecimal(1000));			
			inventoryOperationForm.selectComboitemByValueForcingLabel(inventoryOperationForm.getCmbUnit(), "5", "lb : pound");
			ComponentAgent btnAddItemToMoveRow = desktop.query("treecol").query("#btnAddItemToMoveRow");
			btnAddItemToMoveRow.click();
			
			cmbMaterialInputAgent.typing("mmaterial7 : mm7");
			inventoryOperationForm.selectComboitemByLabel(inventoryOperationForm.getCmbMaterial(), "mmaterial7 : mm7");
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getDecQuantity(), new BigDecimal(1000));
			inventoryOperationForm.selectComboitemByValueForcingLabel(inventoryOperationForm.getCmbUnit(), "7", "l : liter");
			btnAddItemToMoveRow.click();
			
			clickOnRecordButton(desktop);
			clickOnRecordButton(desktop);
						
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testSomeFieldsContainWrongValuesException(ex);
		}
	}
	
	@Test
	@Order(2)
	final void testCase2() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/inventory_operations.zul");
		InventoryOperationForm inventoryOperationForm = new InventoryOperationForm(desktop,
																					desktop.query("combobox").query("#cmbFromWarehouse").as(Combobox.class),
																					desktop.query("combobox").query("#cmbToWarehouse").as(Combobox.class),
																					desktop.query("combobox").query("#cmbWorker").as(Combobox.class),
																					desktop.query("combobox").query("#cmbMaterial").as(Combobox.class),
																					desktop.query("decimalbox").query("#decQuantity").as(Decimalbox.class),
																					desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
																					desktop.query("south").query("button").query("#btnRecord").as(Button.class),
																					desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			ComponentAgent cmbFromWarehouseAgent = desktop.query("combobox").query("#cmbFromWarehouse");
			InputAgent cmbFromWarehouseInputAgent = cmbFromWarehouseAgent.as(InputAgent.class);
			cmbFromWarehouseInputAgent.typing("mwarehouse10 : mw10");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbFromWarehouse(), new Integer(1010));
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user2@");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbWorker(), new Integer(1002));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mmaterial2 : mm2");
			inventoryOperationForm.selectComboitemByLabel(inventoryOperationForm.getCmbMaterial(), "mmaterial2 : mm2");
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getDecQuantity(), new BigDecimal(1000));			
			inventoryOperationForm.selectComboitemByValueForcingLabel(inventoryOperationForm.getCmbUnit(), "5", "lb : pound");
			ComponentAgent btnAddItemToMoveRow = desktop.query("treecol").query("#btnAddItemToMoveRow");
			btnAddItemToMoveRow.click();
			
			cmbMaterialInputAgent.typing("mmaterial7 : mm7");
			inventoryOperationForm.selectComboitemByLabel(inventoryOperationForm.getCmbMaterial(), "mmaterial7 : mm7");
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getDecQuantity(), new BigDecimal(1000));
			inventoryOperationForm.selectComboitemByValueForcingLabel(inventoryOperationForm.getCmbUnit(), "7", "l : liter");
			btnAddItemToMoveRow.click();
			
			clickOnRecordButton(desktop);
			clickOnRecordButton(desktop);
						
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testSomeFieldsContainWrongValuesException(ex);
		}
	}
	
	@Test
	@Order(3)
	final void testCase3() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/inventory_operations.zul");
		InventoryOperationForm inventoryOperationForm = new InventoryOperationForm(desktop,
																					desktop.query("combobox").query("#cmbFromWarehouse").as(Combobox.class),
																					desktop.query("combobox").query("#cmbToWarehouse").as(Combobox.class),
																					desktop.query("combobox").query("#cmbWorker").as(Combobox.class),
																					desktop.query("combobox").query("#cmbMaterial").as(Combobox.class),
																					desktop.query("decimalbox").query("#decQuantity").as(Decimalbox.class),
																					desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
																					desktop.query("south").query("button").query("#btnRecord").as(Button.class),
																					desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			ComponentAgent cmbFromWarehouseAgent = desktop.query("combobox").query("#cmbFromWarehouse");
			InputAgent cmbFromWarehouseInputAgent = cmbFromWarehouseAgent.as(InputAgent.class);
			cmbFromWarehouseInputAgent.typing("mwarehouse10 : mw10");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbFromWarehouse(), new Integer(1010));
			
			ComponentAgent cmbToWarehouseAgent = desktop.query("combobox").query("#cmbToWarehouse");
			InputAgent cmbToWarehouseInputAgent = cmbToWarehouseAgent.as(InputAgent.class);
			cmbToWarehouseInputAgent.typing("mwarehouse6 : mw6");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbToWarehouse(), new Integer(1006));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mmaterial2 : mm2");
			inventoryOperationForm.selectComboitemByLabel(inventoryOperationForm.getCmbMaterial(), "mmaterial2 : mm2");
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getDecQuantity(), new BigDecimal(1000));			
			inventoryOperationForm.selectComboitemByValueForcingLabel(inventoryOperationForm.getCmbUnit(), "5", "lb : pound");
			ComponentAgent btnAddItemToMoveRow = desktop.query("treecol").query("#btnAddItemToMoveRow");
			btnAddItemToMoveRow.click();
			
			cmbMaterialInputAgent.typing("mmaterial7 : mm7");
			inventoryOperationForm.selectComboitemByLabel(inventoryOperationForm.getCmbMaterial(), "mmaterial7 : mm7");
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getDecQuantity(), new BigDecimal(1000));
			inventoryOperationForm.selectComboitemByValueForcingLabel(inventoryOperationForm.getCmbUnit(), "7", "l : liter");
			btnAddItemToMoveRow.click();
			
			clickOnRecordButton(desktop);
			clickOnRecordButton(desktop);
						
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testSomeFieldsContainWrongValuesException(ex);
		}
	}
	
	@Test
	@Order(4)
	final void testCase4() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/inventory_operations.zul");
		InventoryOperationForm inventoryOperationForm = new InventoryOperationForm(desktop,
																					desktop.query("combobox").query("#cmbFromWarehouse").as(Combobox.class),
																					desktop.query("combobox").query("#cmbToWarehouse").as(Combobox.class),
																					desktop.query("combobox").query("#cmbWorker").as(Combobox.class),
																					desktop.query("combobox").query("#cmbMaterial").as(Combobox.class),
																					desktop.query("decimalbox").query("#decQuantity").as(Decimalbox.class),
																					desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
																					desktop.query("south").query("button").query("#btnRecord").as(Button.class),
																					desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			ComponentAgent cmbFromWarehouseAgent = desktop.query("combobox").query("#cmbFromWarehouse");
			InputAgent cmbFromWarehouseInputAgent = cmbFromWarehouseAgent.as(InputAgent.class);
			cmbFromWarehouseInputAgent.typing("mwarehouse10 : mw10");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbFromWarehouse(), new Integer(1010));
			
			ComponentAgent cmbToWarehouseAgent = desktop.query("combobox").query("#cmbToWarehouse");
			InputAgent cmbToWarehouseInputAgent = cmbToWarehouseAgent.as(InputAgent.class);
			cmbToWarehouseInputAgent.typing("mwarehouse6 : mw6");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbToWarehouse(), new Integer(1006));
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user2@");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbWorker(), new Integer(1002));
			
			clickOnRecordButton(desktop);
			clickOnRecordButton(desktop);
						
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testAtLeastOneInventoryItemMustBeMovedException(ex);
		}
	}
	
	@Test
	@Order(5)
	final void testCase5() {

		SpringUtility.setLoggedUserForTesting("user17@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/inventory_operations.zul");
		InventoryOperationForm inventoryOperationForm = new InventoryOperationForm(desktop,
																					desktop.query("combobox").query("#cmbFromWarehouse").as(Combobox.class),
																					desktop.query("combobox").query("#cmbToWarehouse").as(Combobox.class),
																					desktop.query("combobox").query("#cmbWorker").as(Combobox.class),
																					desktop.query("combobox").query("#cmbMaterial").as(Combobox.class),
																					desktop.query("decimalbox").query("#decQuantity").as(Decimalbox.class),
																					desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
																					desktop.query("south").query("button").query("#btnRecord").as(Button.class),
																					desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			ComponentAgent cmbFromWarehouseAgent = desktop.query("combobox").query("#cmbFromWarehouse");
			InputAgent cmbFromWarehouseInputAgent = cmbFromWarehouseAgent.as(InputAgent.class);
			cmbFromWarehouseInputAgent.typing("mwarehouse10 : mw10");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbFromWarehouse(), new Integer(1010));
			
			ComponentAgent cmbToWarehouseAgent = desktop.query("combobox").query("#cmbToWarehouse");
			InputAgent cmbToWarehouseInputAgent = cmbToWarehouseAgent.as(InputAgent.class);
			cmbToWarehouseInputAgent.typing("mwarehouse6 : mw6");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbToWarehouse(), new Integer(1006));
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user2@");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbWorker(), new Integer(1002));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mmaterial2 : mm2");
			inventoryOperationForm.selectComboitemByLabel(inventoryOperationForm.getCmbMaterial(), "mmaterial2 : mm2");
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getDecQuantity(), new BigDecimal(1000));			
			inventoryOperationForm.selectComboitemByValueForcingLabel(inventoryOperationForm.getCmbUnit(), "5", "lb : pound");
			ComponentAgent btnAddItemToMoveRow = desktop.query("treecol").query("#btnAddItemToMoveRow");
			btnAddItemToMoveRow.click();
			
			cmbMaterialInputAgent.typing("mmaterial7 : mm7");
			inventoryOperationForm.selectComboitemByLabel(inventoryOperationForm.getCmbMaterial(), "mmaterial7 : mm7");
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getDecQuantity(), new BigDecimal(1000));
			inventoryOperationForm.selectComboitemByValueForcingLabel(inventoryOperationForm.getCmbUnit(), "7", "l : liter");
			btnAddItemToMoveRow.click();
			
			clickOnRecordButton(desktop);
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

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/inventory_operations.zul");
		InventoryOperationForm inventoryOperationForm = new InventoryOperationForm(desktop,
																					desktop.query("combobox").query("#cmbFromWarehouse").as(Combobox.class),
																					desktop.query("combobox").query("#cmbToWarehouse").as(Combobox.class),
																					desktop.query("combobox").query("#cmbWorker").as(Combobox.class),
																					desktop.query("combobox").query("#cmbMaterial").as(Combobox.class),
																					desktop.query("decimalbox").query("#decQuantity").as(Decimalbox.class),
																					desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
																					desktop.query("south").query("button").query("#btnRecord").as(Button.class),
																					desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			ComponentAgent cmbFromWarehouseAgent = desktop.query("combobox").query("#cmbFromWarehouse");
			InputAgent cmbFromWarehouseInputAgent = cmbFromWarehouseAgent.as(InputAgent.class);
			cmbFromWarehouseInputAgent.typing("mwarehouse10 : mw10");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbFromWarehouse(), new Integer(1010));
			
			ComponentAgent cmbToWarehouseAgent = desktop.query("combobox").query("#cmbToWarehouse");
			InputAgent cmbToWarehouseInputAgent = cmbToWarehouseAgent.as(InputAgent.class);
			cmbToWarehouseInputAgent.typing("mwarehouse6 : mw6");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbToWarehouse(), new Integer(1006));
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user2@");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbWorker(), new Integer(1002));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mmaterial2 : mm2");
			inventoryOperationForm.selectComboitemByLabel(inventoryOperationForm.getCmbMaterial(), "mmaterial2 : mm2");
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getDecQuantity(), new BigDecimal(1000));			
			inventoryOperationForm.selectComboitemByValueForcingLabel(inventoryOperationForm.getCmbUnit(), "5", "lb : pound");
			ComponentAgent btnAddItemToMoveRow = desktop.query("treecol").query("#btnAddItemToMoveRow");
			btnAddItemToMoveRow.click();
			
			cmbMaterialInputAgent.typing("mmaterial7 : mm7");
			inventoryOperationForm.selectComboitemByLabel(inventoryOperationForm.getCmbMaterial(), "mmaterial7 : mm7");
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getDecQuantity(), new BigDecimal(1000));
			inventoryOperationForm.selectComboitemByValueForcingLabel(inventoryOperationForm.getCmbUnit(), "7", "l : liter");
			btnAddItemToMoveRow.click();
			
			clickOnRecordButton(desktop);
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

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/inventory_operations.zul");
		InventoryOperationForm inventoryOperationForm = new InventoryOperationForm(desktop,
																					desktop.query("combobox").query("#cmbFromWarehouse").as(Combobox.class),
																					desktop.query("combobox").query("#cmbToWarehouse").as(Combobox.class),
																					desktop.query("combobox").query("#cmbWorker").as(Combobox.class),
																					desktop.query("combobox").query("#cmbMaterial").as(Combobox.class),
																					desktop.query("decimalbox").query("#decQuantity").as(Decimalbox.class),
																					desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
																					desktop.query("south").query("button").query("#btnRecord").as(Button.class),
																					desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			ComponentAgent cmbFromWarehouseAgent = desktop.query("combobox").query("#cmbFromWarehouse");
			InputAgent cmbFromWarehouseInputAgent = cmbFromWarehouseAgent.as(InputAgent.class);
			cmbFromWarehouseInputAgent.typing("mwarehouse8 : mw8");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbFromWarehouse(), new Integer(1008));
			
			ComponentAgent cmbToWarehouseAgent = desktop.query("combobox").query("#cmbToWarehouse");
			InputAgent cmbToWarehouseInputAgent = cmbToWarehouseAgent.as(InputAgent.class);
			cmbToWarehouseInputAgent.typing("mwarehouse9 : mw9");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbToWarehouse(), new Integer(1009));
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user3@");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbWorker(), new Integer(1003));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mmaterial2 : mm2");
			inventoryOperationForm.selectComboitemByLabel(inventoryOperationForm.getCmbMaterial(), "mmaterial2 : mm2");
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getDecQuantity(), new BigDecimal(1000));			
			inventoryOperationForm.selectComboitemByValueForcingLabel(inventoryOperationForm.getCmbUnit(), "2", "kg : kilogram");
			ComponentAgent btnAddItemToMoveRow = desktop.query("treecol").query("#btnAddItemToMoveRow");
			btnAddItemToMoveRow.click();
			
			cmbMaterialInputAgent.typing("mmaterial7 : mm7");
			inventoryOperationForm.selectComboitemByLabel(inventoryOperationForm.getCmbMaterial(), "mmaterial7 : mm7");
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getDecQuantity(), new BigDecimal(1000));
			inventoryOperationForm.selectComboitemByValueForcingLabel(inventoryOperationForm.getCmbUnit(), "8", "ml : milliliter");
			btnAddItemToMoveRow.click();
			
			clickOnRecordButton(desktop);
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
	@Order(8)
	final void testCase8() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/inventory_operations.zul");
		InventoryOperationForm inventoryOperationForm = new InventoryOperationForm(desktop,
																					desktop.query("combobox").query("#cmbFromWarehouse").as(Combobox.class),
																					desktop.query("combobox").query("#cmbToWarehouse").as(Combobox.class),
																					desktop.query("combobox").query("#cmbWorker").as(Combobox.class),
																					desktop.query("combobox").query("#cmbMaterial").as(Combobox.class),
																					desktop.query("decimalbox").query("#decQuantity").as(Decimalbox.class),
																					desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
																					desktop.query("south").query("button").query("#btnRecord").as(Button.class),
																					desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			ComponentAgent cmbFromWarehouseAgent = desktop.query("combobox").query("#cmbFromWarehouse");
			InputAgent cmbFromWarehouseInputAgent = cmbFromWarehouseAgent.as(InputAgent.class);
			cmbFromWarehouseInputAgent.typing("mwarehouse9 : mw9");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbFromWarehouse(), new Integer(1009));
			
			ComponentAgent cmbToWarehouseAgent = desktop.query("combobox").query("#cmbToWarehouse");
			InputAgent cmbToWarehouseInputAgent = cmbToWarehouseAgent.as(InputAgent.class);
			cmbToWarehouseInputAgent.typing("mwarehouse9 : mw9");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbToWarehouse(), new Integer(1009));
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user2@");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbWorker(), new Integer(1002));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mmaterial2 : mm2");
			inventoryOperationForm.selectComboitemByLabel(inventoryOperationForm.getCmbMaterial(), "mmaterial2 : mm2");
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getDecQuantity(), new BigDecimal(1000));			
			inventoryOperationForm.selectComboitemByValueForcingLabel(inventoryOperationForm.getCmbUnit(), "2", "kg : kilogram");
			ComponentAgent btnAddItemToMoveRow = desktop.query("treecol").query("#btnAddItemToMoveRow");
			btnAddItemToMoveRow.click();
			
			cmbMaterialInputAgent.typing("mmaterial4 : mm4");
			inventoryOperationForm.selectComboitemByLabel(inventoryOperationForm.getCmbMaterial(), "mmaterial4 : mm4");
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getDecQuantity(), new BigDecimal(1000));
			inventoryOperationForm.selectComboitemByValueForcingLabel(inventoryOperationForm.getCmbUnit(), "4", "mg : milligram");
			btnAddItemToMoveRow.click();
			
			cmbMaterialInputAgent.typing("mmaterial6 : mm6");
			inventoryOperationForm.selectComboitemByLabel(inventoryOperationForm.getCmbMaterial(), "mmaterial6 : mm6");
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getDecQuantity(), new BigDecimal(1000));
			inventoryOperationForm.selectComboitemByValueForcingLabel(inventoryOperationForm.getCmbUnit(), "4", "mg : milligram");
			btnAddItemToMoveRow.click();
			
			clickOnRecordButton(desktop);
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
	@Order(9)
	final void testCase9() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/inventory_operations.zul");
		InventoryOperationForm inventoryOperationForm = new InventoryOperationForm(desktop,
																					desktop.query("combobox").query("#cmbFromWarehouse").as(Combobox.class),
																					desktop.query("combobox").query("#cmbToWarehouse").as(Combobox.class),
																					desktop.query("combobox").query("#cmbWorker").as(Combobox.class),
																					desktop.query("combobox").query("#cmbMaterial").as(Combobox.class),
																					desktop.query("decimalbox").query("#decQuantity").as(Decimalbox.class),
																					desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
																					desktop.query("south").query("button").query("#btnRecord").as(Button.class),
																					desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			ComponentAgent cmbFromWarehouseAgent = desktop.query("combobox").query("#cmbFromWarehouse");
			InputAgent cmbFromWarehouseInputAgent = cmbFromWarehouseAgent.as(InputAgent.class);
			cmbFromWarehouseInputAgent.typing("mwarehouse9 : mw9");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbFromWarehouse(), new Integer(1009));
			
			ComponentAgent cmbToWarehouseAgent = desktop.query("combobox").query("#cmbToWarehouse");
			InputAgent cmbToWarehouseInputAgent = cmbToWarehouseAgent.as(InputAgent.class);
			cmbToWarehouseInputAgent.typing("mwarehouse10 : mw10");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbToWarehouse(), new Integer(1010));
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user3@");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbWorker(), new Integer(1003));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mmaterial5 : mm5");
			inventoryOperationForm.selectComboitemByLabel(inventoryOperationForm.getCmbMaterial(), "mmaterial5 : mm5");
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getDecQuantity(), new BigDecimal(10000));			
			inventoryOperationForm.selectComboitemByValueForcingLabel(inventoryOperationForm.getCmbUnit(), "4", "mg : milligram");
			ComponentAgent btnAddItemToMoveRow = desktop.query("treecol").query("#btnAddItemToMoveRow");
			btnAddItemToMoveRow.click();
			
			clickOnRecordButton(desktop);
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

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/inventory_operations.zul");
		InventoryOperationForm inventoryOperationForm = new InventoryOperationForm(desktop,
																					desktop.query("combobox").query("#cmbFromWarehouse").as(Combobox.class),
																					desktop.query("combobox").query("#cmbToWarehouse").as(Combobox.class),
																					desktop.query("combobox").query("#cmbWorker").as(Combobox.class),
																					desktop.query("combobox").query("#cmbMaterial").as(Combobox.class),
																					desktop.query("decimalbox").query("#decQuantity").as(Decimalbox.class),
																					desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
																					desktop.query("south").query("button").query("#btnRecord").as(Button.class),
																					desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			ComponentAgent cmbFromWarehouseAgent = desktop.query("combobox").query("#cmbFromWarehouse");
			InputAgent cmbFromWarehouseInputAgent = cmbFromWarehouseAgent.as(InputAgent.class);
			cmbFromWarehouseInputAgent.typing("mwarehouse10 : mw10");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbFromWarehouse(), new Integer(1010));
			
			ComponentAgent cmbToWarehouseAgent = desktop.query("combobox").query("#cmbToWarehouse");
			InputAgent cmbToWarehouseInputAgent = cmbToWarehouseAgent.as(InputAgent.class);
			cmbToWarehouseInputAgent.typing("mwarehouse9 : mw9");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbToWarehouse(), new Integer(1009));
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user2@");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbWorker(), new Integer(1002));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mmaterial5 : mm5");
			inventoryOperationForm.selectComboitemByLabel(inventoryOperationForm.getCmbMaterial(), "mmaterial5 : mm5");
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getDecQuantity(), new BigDecimal(10000));			
			inventoryOperationForm.selectComboitemByValueForcingLabel(inventoryOperationForm.getCmbUnit(), "3", "g : gram");
			ComponentAgent btnAddItemToMoveRow = desktop.query("treecol").query("#btnAddItemToMoveRow");
			btnAddItemToMoveRow.click();
			
			clickOnRecordButton(desktop);
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

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/inventory_operations.zul");
		InventoryOperationForm inventoryOperationForm = new InventoryOperationForm(desktop,
																					desktop.query("combobox").query("#cmbFromWarehouse").as(Combobox.class),
																					desktop.query("combobox").query("#cmbToWarehouse").as(Combobox.class),
																					desktop.query("combobox").query("#cmbWorker").as(Combobox.class),
																					desktop.query("combobox").query("#cmbMaterial").as(Combobox.class),
																					desktop.query("decimalbox").query("#decQuantity").as(Decimalbox.class),
																					desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
																					desktop.query("south").query("button").query("#btnRecord").as(Button.class),
																					desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			ComponentAgent cmbFromWarehouseAgent = desktop.query("combobox").query("#cmbFromWarehouse");
			InputAgent cmbFromWarehouseInputAgent = cmbFromWarehouseAgent.as(InputAgent.class);
			cmbFromWarehouseInputAgent.typing("mwarehouse8 : mw8");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbFromWarehouse(), new Integer(1008));
			
			ComponentAgent cmbToWarehouseAgent = desktop.query("combobox").query("#cmbToWarehouse");
			InputAgent cmbToWarehouseInputAgent = cmbToWarehouseAgent.as(InputAgent.class);
			cmbToWarehouseInputAgent.typing("mwarehouse10 : mw10");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbToWarehouse(), new Integer(1010));
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user3@");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbWorker(), new Integer(1003));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mmaterial2 : mm2");
			inventoryOperationForm.selectComboitemByLabel(inventoryOperationForm.getCmbMaterial(), "mmaterial2 : mm2");
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getDecQuantity(), new BigDecimal(1000));			
			inventoryOperationForm.selectComboitemByValueForcingLabel(inventoryOperationForm.getCmbUnit(), "5", "lb : pound");
			ComponentAgent btnAddItemToMoveRow = desktop.query("treecol").query("#btnAddItemToMoveRow");
			btnAddItemToMoveRow.click();
			
			cmbMaterialInputAgent.typing("mmaterial4 : mm4");
			inventoryOperationForm.selectComboitemByLabel(inventoryOperationForm.getCmbMaterial(), "mmaterial4 : mm4");
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getDecQuantity(), new BigDecimal(1000));
			inventoryOperationForm.selectComboitemByValueForcingLabel(inventoryOperationForm.getCmbUnit(), "3", "g : gram");
			btnAddItemToMoveRow.click();
			
			cmbMaterialInputAgent.typing("mmaterial6 : mm6");
			inventoryOperationForm.selectComboitemByLabel(inventoryOperationForm.getCmbMaterial(), "mmaterial6 : mm6");
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getDecQuantity(), new BigDecimal(1000));
			inventoryOperationForm.selectComboitemByValueForcingLabel(inventoryOperationForm.getCmbUnit(), "6", "oz : ounce");
			btnAddItemToMoveRow.click();
			
			clickOnRecordButton(desktop);
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
	@Order(12)
	final void testCase12() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/inventory_operations.zul");
		InventoryOperationForm inventoryOperationForm = new InventoryOperationForm(desktop,
																					desktop.query("combobox").query("#cmbFromWarehouse").as(Combobox.class),
																					desktop.query("combobox").query("#cmbToWarehouse").as(Combobox.class),
																					desktop.query("combobox").query("#cmbWorker").as(Combobox.class),
																					desktop.query("combobox").query("#cmbMaterial").as(Combobox.class),
																					desktop.query("decimalbox").query("#decQuantity").as(Decimalbox.class),
																					desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
																					desktop.query("south").query("button").query("#btnRecord").as(Button.class),
																					desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			ComponentAgent cmbFromWarehouseAgent = desktop.query("combobox").query("#cmbFromWarehouse");
			InputAgent cmbFromWarehouseInputAgent = cmbFromWarehouseAgent.as(InputAgent.class);
			cmbFromWarehouseInputAgent.typing("mwarehouse8 : mw8");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbFromWarehouse(), new Integer(1008));
			
			ComponentAgent cmbToWarehouseAgent = desktop.query("combobox").query("#cmbToWarehouse");
			InputAgent cmbToWarehouseInputAgent = cmbToWarehouseAgent.as(InputAgent.class);
			cmbToWarehouseInputAgent.typing("mwarehouse6 : mw6");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbToWarehouse(), new Integer(1006));
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user2@");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbWorker(), new Integer(1002));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mmaterial2 : mm2");
			inventoryOperationForm.selectComboitemByLabel(inventoryOperationForm.getCmbMaterial(), "mmaterial2 : mm2");
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getDecQuantity(), new BigDecimal(1000));			
			inventoryOperationForm.selectComboitemByValueForcingLabel(inventoryOperationForm.getCmbUnit(), "3", "g : gram");
			ComponentAgent btnAddItemToMoveRow = desktop.query("treecol").query("#btnAddItemToMoveRow");
			btnAddItemToMoveRow.click();
			
			cmbMaterialInputAgent.typing("mmaterial4 : mm4");
			inventoryOperationForm.selectComboitemByLabel(inventoryOperationForm.getCmbMaterial(), "mmaterial4 : mm4");
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getDecQuantity(), new BigDecimal(1000));
			inventoryOperationForm.selectComboitemByValueForcingLabel(inventoryOperationForm.getCmbUnit(), "2", "kg : kilogram");
			btnAddItemToMoveRow.click();
			
			cmbMaterialInputAgent.typing("mmaterial6 : mm6");
			inventoryOperationForm.selectComboitemByLabel(inventoryOperationForm.getCmbMaterial(), "mmaterial6 : mm6");
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getDecQuantity(), new BigDecimal(1000));
			inventoryOperationForm.selectComboitemByValueForcingLabel(inventoryOperationForm.getCmbUnit(), "5", "lb : pound");
			btnAddItemToMoveRow.click();
			
			clickOnRecordButton(desktop);
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

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/inventory_operations.zul");
		InventoryOperationForm inventoryOperationForm = new InventoryOperationForm(desktop,
																					desktop.query("combobox").query("#cmbFromWarehouse").as(Combobox.class),
																					desktop.query("combobox").query("#cmbToWarehouse").as(Combobox.class),
																					desktop.query("combobox").query("#cmbWorker").as(Combobox.class),
																					desktop.query("combobox").query("#cmbMaterial").as(Combobox.class),
																					desktop.query("decimalbox").query("#decQuantity").as(Decimalbox.class),
																					desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
																					desktop.query("south").query("button").query("#btnRecord").as(Button.class),
																					desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			ComponentAgent cmbFromWarehouseAgent = desktop.query("combobox").query("#cmbFromWarehouse");
			InputAgent cmbFromWarehouseInputAgent = cmbFromWarehouseAgent.as(InputAgent.class);
			cmbFromWarehouseInputAgent.typing("mwarehouse9 : mw9");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbFromWarehouse(), new Integer(1009));
			
			ComponentAgent cmbToWarehouseAgent = desktop.query("combobox").query("#cmbToWarehouse");
			InputAgent cmbToWarehouseInputAgent = cmbToWarehouseAgent.as(InputAgent.class);
			cmbToWarehouseInputAgent.typing("mwarehouse10 : mw10");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbToWarehouse(), new Integer(1010));
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user3@");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbWorker(), new Integer(1003));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mmaterial2 : mm2");
			inventoryOperationForm.selectComboitemByLabel(inventoryOperationForm.getCmbMaterial(), "mmaterial2 : mm2");
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getDecQuantity(), new BigDecimal(1000));			
			inventoryOperationForm.selectComboitemByValueForcingLabel(inventoryOperationForm.getCmbUnit(), "4", "mg : milligram");
			ComponentAgent btnAddItemToMoveRow = desktop.query("treecol").query("#btnAddItemToMoveRow");
			btnAddItemToMoveRow.click();
			
			cmbMaterialInputAgent.typing("mmaterial7 : mm7");
			inventoryOperationForm.selectComboitemByLabel(inventoryOperationForm.getCmbMaterial(), "mmaterial7 : mm7");
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getDecQuantity(), new BigDecimal(1000));
			inventoryOperationForm.selectComboitemByValueForcingLabel(inventoryOperationForm.getCmbUnit(), "7", "l : liter");
			btnAddItemToMoveRow.click();
			
			clickOnRecordButton(desktop);
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

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/inventory_operations.zul");
		InventoryOperationForm inventoryOperationForm = new InventoryOperationForm(desktop,
																					desktop.query("combobox").query("#cmbFromWarehouse").as(Combobox.class),
																					desktop.query("combobox").query("#cmbToWarehouse").as(Combobox.class),
																					desktop.query("combobox").query("#cmbWorker").as(Combobox.class),
																					desktop.query("combobox").query("#cmbMaterial").as(Combobox.class),
																					desktop.query("decimalbox").query("#decQuantity").as(Decimalbox.class),
																					desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
																					desktop.query("south").query("button").query("#btnRecord").as(Button.class),
																					desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			ComponentAgent cmbFromWarehouseAgent = desktop.query("combobox").query("#cmbFromWarehouse");
			InputAgent cmbFromWarehouseInputAgent = cmbFromWarehouseAgent.as(InputAgent.class);
			cmbFromWarehouseInputAgent.typing("mwarehouse10 : mw10");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbFromWarehouse(), new Integer(1010));
			
			ComponentAgent cmbToWarehouseAgent = desktop.query("combobox").query("#cmbToWarehouse");
			InputAgent cmbToWarehouseInputAgent = cmbToWarehouseAgent.as(InputAgent.class);
			cmbToWarehouseInputAgent.typing("mwarehouse10 : mw10");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbToWarehouse(), new Integer(1010));
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user2@");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbWorker(), new Integer(1002));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mmaterial2 : mm2");
			inventoryOperationForm.selectComboitemByLabel(inventoryOperationForm.getCmbMaterial(), "mmaterial2 : mm2");
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getDecQuantity(), new BigDecimal(1000));			
			inventoryOperationForm.selectComboitemByValueForcingLabel(inventoryOperationForm.getCmbUnit(), "2", "kg : kilogram");
			ComponentAgent btnAddItemToMoveRow = desktop.query("treecol").query("#btnAddItemToMoveRow");
			btnAddItemToMoveRow.click();
			
			cmbMaterialInputAgent.typing("mmaterial4 : mm4");
			inventoryOperationForm.selectComboitemByLabel(inventoryOperationForm.getCmbMaterial(), "mmaterial4 : mm4");
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getDecQuantity(), new BigDecimal(1000));
			inventoryOperationForm.selectComboitemByValueForcingLabel(inventoryOperationForm.getCmbUnit(), "4", "mg : milligram");
			btnAddItemToMoveRow.click();
			
			cmbMaterialInputAgent.typing("mmaterial6 : mm6");
			inventoryOperationForm.selectComboitemByLabel(inventoryOperationForm.getCmbMaterial(), "mmaterial6 : mm6");
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getDecQuantity(), new BigDecimal(1000));
			inventoryOperationForm.selectComboitemByValueForcingLabel(inventoryOperationForm.getCmbUnit(), "4", "mg : milligram");
			btnAddItemToMoveRow.click();
			
			clickOnRecordButton(desktop);
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

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/inventory_operations.zul");
		InventoryOperationForm inventoryOperationForm = new InventoryOperationForm(desktop,
																					desktop.query("combobox").query("#cmbFromWarehouse").as(Combobox.class),
																					desktop.query("combobox").query("#cmbToWarehouse").as(Combobox.class),
																					desktop.query("combobox").query("#cmbWorker").as(Combobox.class),
																					desktop.query("combobox").query("#cmbMaterial").as(Combobox.class),
																					desktop.query("decimalbox").query("#decQuantity").as(Decimalbox.class),
																					desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
																					desktop.query("south").query("button").query("#btnRecord").as(Button.class),
																					desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			ComponentAgent cmbFromWarehouseAgent = desktop.query("combobox").query("#cmbFromWarehouse");
			InputAgent cmbFromWarehouseInputAgent = cmbFromWarehouseAgent.as(InputAgent.class);
			cmbFromWarehouseInputAgent.typing("mwarehouse9 : mw9");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbFromWarehouse(), new Integer(1009));
			
			ComponentAgent cmbToWarehouseAgent = desktop.query("combobox").query("#cmbToWarehouse");
			InputAgent cmbToWarehouseInputAgent = cmbToWarehouseAgent.as(InputAgent.class);
			cmbToWarehouseInputAgent.typing("mwarehouse6 : mw6");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbToWarehouse(), new Integer(1006));
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user3@");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbWorker(), new Integer(1003));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mmaterial5 : mm5");
			inventoryOperationForm.selectComboitemByLabel(inventoryOperationForm.getCmbMaterial(), "mmaterial5 : mm5");
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getDecQuantity(), new BigDecimal(1000));			
			inventoryOperationForm.selectComboitemByValueForcingLabel(inventoryOperationForm.getCmbUnit(), "2", "kg : kilogram");
			ComponentAgent btnAddItemToMoveRow = desktop.query("treecol").query("#btnAddItemToMoveRow");
			btnAddItemToMoveRow.click();
			
			clickOnRecordButton(desktop);
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

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/inventory_operations.zul");
		InventoryOperationForm inventoryOperationForm = new InventoryOperationForm(desktop,
																					desktop.query("combobox").query("#cmbFromWarehouse").as(Combobox.class),
																					desktop.query("combobox").query("#cmbToWarehouse").as(Combobox.class),
																					desktop.query("combobox").query("#cmbWorker").as(Combobox.class),
																					desktop.query("combobox").query("#cmbMaterial").as(Combobox.class),
																					desktop.query("decimalbox").query("#decQuantity").as(Decimalbox.class),
																					desktop.query("combobox").query("#cmbUnit").as(Combobox.class),
																					desktop.query("south").query("button").query("#btnRecord").as(Button.class),
																					desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			ComponentAgent cmbFromWarehouseAgent = desktop.query("combobox").query("#cmbFromWarehouse");
			InputAgent cmbFromWarehouseInputAgent = cmbFromWarehouseAgent.as(InputAgent.class);
			cmbFromWarehouseInputAgent.typing("mwarehouse8 : mw8");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbFromWarehouse(), new Integer(1008));
			
			ComponentAgent cmbToWarehouseAgent = desktop.query("combobox").query("#cmbToWarehouse");
			InputAgent cmbToWarehouseInputAgent = cmbToWarehouseAgent.as(InputAgent.class);
			cmbToWarehouseInputAgent.typing("mwarehouse9 : mw9");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbToWarehouse(), new Integer(1009));
			
			ComponentAgent cmbWorkerAgent = desktop.query("combobox").query("#cmbWorker");
			InputAgent cmbWorkerInputAgent = cmbWorkerAgent.as(InputAgent.class);
			cmbWorkerInputAgent.typing("user2@");
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getCmbWorker(), new Integer(1002));
			
			ComponentAgent cmbMaterialAgent = desktop.query("combobox").query("#cmbMaterial");
			InputAgent cmbMaterialInputAgent = cmbMaterialAgent.as(InputAgent.class);
			cmbMaterialInputAgent.typing("mmaterial5 : mm5");
			inventoryOperationForm.selectComboitemByLabel(inventoryOperationForm.getCmbMaterial(), "mmaterial5 : mm5");
			cmbMaterialAgent.click(); 	//needed to force cmbUnit population. 
										//cmbMaterial's onChange event isn't triggered under testing
			inventoryOperationForm.setComponentValue(inventoryOperationForm.getDecQuantity(), new BigDecimal(10000));			
			inventoryOperationForm.selectComboitemByValueForcingLabel(inventoryOperationForm.getCmbUnit(), "5", "lb : pound");
			ComponentAgent btnAddItemToMoveRow = desktop.query("treecol").query("#btnAddItemToMoveRow");
			btnAddItemToMoveRow.click();
			
			clickOnRecordButton(desktop);
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
}