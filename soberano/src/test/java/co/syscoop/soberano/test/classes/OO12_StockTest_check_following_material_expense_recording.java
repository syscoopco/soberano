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
import org.zkoss.zats.mimic.operation.InputAgent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Row;
import co.syscoop.soberano.test.helper.StockActionTest;
import co.syscoop.soberano.test.helper.StockForm;
import co.syscoop.soberano.test.helper.TestUtilityCode;
import co.syscoop.soberano.util.SpringUtility;

@Order(12)

//@Disabled

@TestMethodOrder(OrderAnnotation.class)
class OO12_StockTest_check_following_material_expenses_recording extends StockActionTest {
	
	protected StockForm stockForm = null;

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
	final void testCase1() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/stock.zul");
		try {
			ComponentAgent expensesGridAgent = desktop.query("grid");
			Grid grd = expensesGridAgent.as(Grid.class);
			assertEquals(4, grd.getRows().getChildren().size(), "Wrong count of stock entries.");
			
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(0), 
										"mm2",
										"mmaterial2",
										907.184001,
										"kg",										
										17677.81977231);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(1), 
										"mm4",
										"mmaterial4",
										1360776000000.0,
										"mg",
										0.0);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(2), 
										"mm6",
										"mmaterial6",
										1360776002001.0,
										"mg",
										0.0);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(3), 
										"mm7",
										"mmaterial7",
										1000.001,
										"ml",
										0.0);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase2() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/stock.zul");
		try {
			ComponentAgent cmbWarehouseAgent = desktop.query("#wndContentPanel").query("#cmbWarehouse");
			InputAgent cmbWarehouseInputAgent = cmbWarehouseAgent.as(InputAgent.class);
			cmbWarehouseInputAgent.typing("mw10");
			
			stockForm = new StockForm(desktop,
									(desktop.query("#wndContentPanel").query("#cmbWarehouse")).as(Combobox.class),
									(desktop.query("grid").query("#grd")).as(Grid.class));
			
			stockForm.setComponentValue(stockForm.getCmbWarehouse(), Integer.valueOf(1010));
			cmbWarehouseAgent.click(); 	//needed to force grid updating. 
										//cmbWarehouse's onChange event isn't triggered under testing
			
			ComponentAgent expensesGridAgent = desktop.query("grid");
			Grid grd = expensesGridAgent.as(Grid.class);
			assertEquals(4, grd.getRows().getChildren().size(), "Wrong count of stock entries.");
			
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(0), 
										"mm2",
										"mmaterial2",
										907.184001,
										"kg",										
										17677.81977231);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(1), 
										"mm4",
										"mmaterial4",
										1360776000000.0,
										"mg",
										0.0);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(2), 
										"mm6",
										"mmaterial6",
										1360776002001.0,
										"mg",
										0.0);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(3), 
										"mm7",
										"mmaterial7",
										1000.001,
										"ml",
										0.0);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase3() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/stock.zul");
		try {
			ComponentAgent cmbWarehouseAgent = desktop.query("#wndContentPanel").query("#cmbWarehouse");
			InputAgent cmbWarehouseInputAgent = cmbWarehouseAgent.as(InputAgent.class);
			cmbWarehouseInputAgent.typing("mw9");
			
			stockForm = new StockForm(desktop,
									(desktop.query("#wndContentPanel").query("#cmbWarehouse")).as(Combobox.class),
									(desktop.query("grid").query("#grd")).as(Grid.class));
			
			stockForm.setComponentValue(stockForm.getCmbWarehouse(), Integer.valueOf(1009));
			cmbWarehouseAgent.click(); 	//needed to force grid updating. 
										//cmbWarehouse's onChange event isn't triggered under testing
			
			ComponentAgent expensesGridAgent = desktop.query("grid");
			Grid grd = expensesGridAgent.as(Grid.class);
			assertEquals(0, grd.getRows().getChildren().size(), "Wrong count of stock entries.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase4() {

		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/stock.zul");
		try {
			ComponentAgent expensesGridAgent = desktop.query("grid");
			Grid grd = expensesGridAgent.as(Grid.class);
			assertEquals(0, grd.getRows().getChildren().size(), "Wrong count of stock entries.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
}