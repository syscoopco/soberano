/****************************************************************************************/
/* TODO:																				*/
/* IMPORTANT: This is an unplanned test. Test cases weren't selected nor calculated		*/
/*			in advanced. It is for regression testing. GUI content is compared with		*/
/*			the output resulting of a (not verified yet) human run following the 		*/
/*			previous automatic tests.													*/
/****************************************************************************************/

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

@Order(28)

//TODO: enable test
//@Disabled

@TestMethodOrder(OrderAnnotation.class)
class OO28_StockTest_check_following_material_expense_cancelation extends StockActionTest {
	
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
			assertEquals(9, grd.getRows().getChildren().size(), "Wrong count of stock entries.");
			
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(0), 
										"mm2",
										"mmaterial2",
										3816.368002,
										"kg",										
										2506967.91065426);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(1), 
										"mm4",
										"mmaterial4",
										2721552001000.0,
										"mg",
										16.56379044);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(2), 
										"mm5",
										"mmaterial5",
										-1.40924524,
										"lb",
										0.0);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(3), 
										"mm6",
										"mmaterial6",
										2721552096818.0,
										"mg",
										78.59299677);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(4), 
										"mm7",
										"mmaterial7",
										7000.002,
										"ml",
										25376602.80633985);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(5), 
										"mm8",
										"mmaterial8",
										97400.0,
										"ml",
										117676.97680086);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(6), 
										"mm9",
										"mmaterial9",
										7.0,
										"pcs",
										38998140.28080506);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(7), 
										"mp1",
										"mproduct1",
										0.0,
										"kg",
										0.0);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(8), 
										"mp7",
										"mproduct7",
										11.0,
										"pcs",
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
			ComponentAgent cmbWarehouseAgent = desktop.query("combobox").query("#cmbWarehouse");
			InputAgent cmbWarehouseInputAgent = cmbWarehouseAgent.as(InputAgent.class);
			cmbWarehouseInputAgent.typing("mw10");
			
			stockForm = new StockForm(desktop,
									(desktop.query("combobox").query("#cmbWarehouse")).as(Combobox.class),
									(desktop.query("grid").query("#grd")).as(Grid.class));
			
			stockForm.setComponentValue(stockForm.getCmbWarehouse(), new Integer(1010));
			cmbWarehouseAgent.click(); 	//needed to force grid updating. 
										//cmbWarehouse's onChange event isn't triggered under testing
			
			ComponentAgent expensesGridAgent = desktop.query("grid");
			Grid grd = expensesGridAgent.as(Grid.class);
			assertEquals(8, grd.getRows().getChildren().size(), "Wrong count of stock entries.");
			
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(0), 
										"mm2",
										"mmaterial2",
										3812.370002,
										"kg",										
										973.92672368);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(1), 
										"mm4",
										"mmaterial4",
										2721553000000.0,
										"mg",
										16.50661561);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(2), 
										"mm5",
										"mmaterial5",
										-26.43339924,
										"lb",
										0.0);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(3), 
										"mm6",
										"mmaterial6",
										2721578992726.0,
										"mg",
										44.01719921);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(4), 
										"mm7",
										"mmaterial7",
										3000.002,
										"ml",
										1197950.84576058);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(5), 
										"mm8",
										"mmaterial8",
										97400.0,
										"ml",
										117676.97680086);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(6), 
										"mm9",
										"mmaterial9",
										1.0,
										"pcs",
										11979513.24940921);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(7), 
										"mp7",
										"mproduct7",
										0.0,
										"pcs",
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
			ComponentAgent cmbWarehouseAgent = desktop.query("combobox").query("#cmbWarehouse");
			InputAgent cmbWarehouseInputAgent = cmbWarehouseAgent.as(InputAgent.class);
			cmbWarehouseInputAgent.typing("mw9");
			
			stockForm = new StockForm(desktop,
									(desktop.query("combobox").query("#cmbWarehouse")).as(Combobox.class),
									(desktop.query("grid").query("#grd")).as(Grid.class));
			
			stockForm.setComponentValue(stockForm.getCmbWarehouse(), new Integer(1009));
			cmbWarehouseAgent.click(); 	//needed to force grid updating. 
										//cmbWarehouse's onChange event isn't triggered under testing
			
			ComponentAgent expensesGridAgent = desktop.query("grid");
			Grid grd = expensesGridAgent.as(Grid.class);
			assertEquals(7, grd.getRows().getChildren().size(), "Wrong count of stock entries.");
			
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(0), 
											"mm2",
											"mmaterial2",
											2011.998,
											"kg",										
											52187.69751684);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(1), 
											"mm4",
											"mmaterial4",
											6000.0,
											"mg",
											155.58763247);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(2), 
											"mm5",
											"mmaterial5",
											7817.4015322,
											"lb",
											0.0);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(3), 
											"mm6",
											"mmaterial6",
											0.0,
											"mg",
											0.0);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(4), 
											"mm7",
											"mmaterial7",
											-999000.0,
											"ml",
											0.0);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(5), 
											"mp1",
											"mproduct1",
											6.0,
											"kg",
											0.0);
			TestUtilityCode.testStockRecord((Row) grd.getRows().getChildren().get(6), 
											"mp7",
											"mproduct7",
											11.0,
											"pcs",
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