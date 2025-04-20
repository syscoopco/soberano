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

@Order(18)

@Disabled

@TestMethodOrder(OrderAnnotation.class)
class OO18_StockTest_check_following_material_expense_cancelation extends StockActionTest {
	
	protected StockForm stockForm = null;

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

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/stock.zul");
		try {
			ComponentAgent expensesGridAgent = desktop.query("grid");
			Grid grd = expensesGridAgent.as(Grid.class);
			assertEquals(6, grd.getRows().getChildren().size(), "Wrong count of stock entries.");
			
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(0), 
										"mm2",
										"mmaterial2",
										1000.000001,
										"kg",										
										3002.000997);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(1), 
										"mm4",
										"mmaterial4",
										2721552000000.0,
										"mg",
										110.23122101);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(2), 
										"mm5",
										"mmaterial5",
										0.0,
										"lb",
										0.0);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(3), 
										"mm6",
										"mmaterial6",
										1360776004002.0,
										"mg",
										587.89984365);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(4), 
										"mm7",
										"mmaterial7",
										1000.001,
										"ml",
										499.9995);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(5), 
										"mm8",
										"mmaterial8",
										100000.0,
										"ml",
										0.2);
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
			assertEquals(6, grd.getRows().getChildren().size(), "Wrong count of stock entries.");
			
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(0), 
										"mm2",
										"mmaterial2",
										1000.001001000000000,
										"kg",										
										1640.31572181);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(1), 
										"mm4",
										"mmaterial4",
										2721553000000.00000000,
										"mg",
										110.231180506130000);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(2), 
										"mm5",
										"mmaterial5",
										-22.02415400,
										"lb",
										0.0);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(3), 
										"mm6",
										"mmaterial6",
										1360804353502.00000000,
										"mg",
										587.887595995132000);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(4), 
										"mm7",
										"mmaterial7",
										1000.00100000,
										"ml",
										0.000000000000000);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(5), 
										"mm8",
										"mmaterial8",
										100000.00000000,
										"ml",
										0.200000000000000);
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
			assertEquals(5, grd.getRows().getChildren().size(), "Wrong count of stock entries.");
			
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(0), 
											"mm2",
											"mmaterial2",
											999.99900000,
											"kg",										
											0.00000000);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(1), 
											"mm4",
											"mmaterial4",
											0.00000000,
											"mg",
											0.00000000);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(2), 
											"mm5",
											"mmaterial5",
											7817.40153220,
											"lb",
											0.00000000);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(3), 
											"mm6",
											"mmaterial6",
											0.00000000,
											"mg",
											0.00000000);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(4), 
											"mm7",
											"mmaterial7",
											-999000.00000000,
											"ml",
											0.00000000);
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

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/stock.zul");
		try {
			ComponentAgent cmbWarehouseAgent = desktop.query("#wndContentPanel").query("#cmbWarehouse");
			InputAgent cmbWarehouseInputAgent = cmbWarehouseAgent.as(InputAgent.class);
			cmbWarehouseInputAgent.typing("mw8");
			
			stockForm = new StockForm(desktop,
									(desktop.query("#wndContentPanel").query("#cmbWarehouse")).as(Combobox.class),
									(desktop.query("grid").query("#grd")).as(Grid.class));
			
			stockForm.setComponentValue(stockForm.getCmbWarehouse(), Integer.valueOf(1008));
			cmbWarehouseAgent.click(); 	//needed to force grid updating. 
										//cmbWarehouse's onChange event isn't triggered under testing
			
			ComponentAgent expensesGridAgent = desktop.query("grid");
			Grid grd = expensesGridAgent.as(Grid.class);
			assertEquals(5, grd.getRows().getChildren().size(), "Wrong count of stock entries.");
			
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(0), 
											"mm2",
											"mmaterial2",
											-1454.59200000,
											"kg",										
											0.00000000);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(1), 
											"mm4",
											"mmaterial4",
											-1001000000.00000000,
											"mg",
											0.00000000);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(2), 
											"mm5",
											"mmaterial5",
											-10000.00000000,
											"lb",
											0.00000000);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(3), 
											"mm6",
											"mmaterial6",
											-481941500.00000000,
											"mg",
											0.00000000);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(4), 
											"mm7",
											"mmaterial7",
											-1000.00000000,
											"ml",
											0.00000000);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase5() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/stock.zul");
		try {
			ComponentAgent cmbWarehouseAgent = desktop.query("#wndContentPanel").query("#cmbWarehouse");
			InputAgent cmbWarehouseInputAgent = cmbWarehouseAgent.as(InputAgent.class);
			cmbWarehouseInputAgent.typing("mw6");
			
			stockForm = new StockForm(desktop,
									(desktop.query("#wndContentPanel").query("#cmbWarehouse")).as(Combobox.class),
									(desktop.query("grid").query("#grd")).as(Grid.class));
			
			stockForm.setComponentValue(stockForm.getCmbWarehouse(), Integer.valueOf(1006));
			cmbWarehouseAgent.click(); 	//needed to force grid updating. 
										//cmbWarehouse's onChange event isn't triggered under testing
			
			ComponentAgent expensesGridAgent = desktop.query("grid");
			Grid grd = expensesGridAgent.as(Grid.class);
			assertEquals(5, grd.getRows().getChildren().size(), "Wrong count of stock entries.");
			
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(0), 
											"mm2",
											"mmaterial2",
											454.59200000,
											"kg",										
											2995.39727103);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(1), 
											"mm4",
											"mmaterial4",
											1000000000.00000000,
											"mg",
											0.00000000);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(2), 
											"mm5",
											"mmaterial5",
											2204.62262180,
											"lb",
											0.00000000);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(3), 
											"mm6",
											"mmaterial6",
											453592000.00000000,
											"mg",
											0.00000000);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(4), 
											"mm7",
											"mmaterial7",
											1000000.00000000,
											"ml",
											0.50000000);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
}