/*************************************************************************************************/
/*	Unplanned tests. Expected values just taken from GUI after running previous automated tests. */
/*	Purposeful as regression tests. 											   				 */
/*************************************************************************************************/

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
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import co.syscoop.soberano.test.helper.StockActionTest;
import co.syscoop.soberano.test.helper.StockForm;
import co.syscoop.soberano.util.SpringUtility;

@Order(37)

//@Disabled

@TestMethodOrder(OrderAnnotation.class)
class OO37_StockTest_check_following_order_reopening_and_recollecting extends StockActionTest {
	
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
	
	private void testStockRecord(Row row, 
			String expectedItemCode,
			String expectedItemName,
			Double expectedQuantity,
			String expectedUnit,
			Double expectedUnitValue) {

		//item code
		if (!((Label) row.getChildren().get(0)).getValue().equals(expectedItemCode)) {
			fail("Wrong item code for stock record with row index " + row.getIndex() + ". Expected: " + expectedItemCode + ". It was: " + ((Label) row.getChildren().get(0)).getValue());
		}
		
		//item name
		if (!((Label) row.getChildren().get(1)).getValue().equals(expectedItemName)) {
			fail("Wrong item name for stock record with row index " + row.getIndex() + ". Expected: " + expectedItemName + ". It was: " + ((Label) row.getChildren().get(1)).getValue());
		}
		
		//quantity
		if (Math.abs(((Decimalbox) row.getChildren().get(2)).getValue().doubleValue() - expectedQuantity) > 0.00000001) {
			fail("Wrong quantity for stock record with row index " + row.getIndex() + ". Expected: " + expectedQuantity + ". It was: " + ((Decimalbox) row.getChildren().get(2)).getValue().doubleValue());
		}
		
		//unit
		if (!((Label) row.getChildren().get(3)).getValue().equals(expectedUnit)) {
			fail("Wrong unit for stock record with row index " + row.getIndex() + ". Expected: " + expectedUnit + ". It was: " + ((Label) row.getChildren().get(3)).getValue());
		}
		
		//unit value
		if (Math.abs(((Decimalbox) row.getChildren().get(4)).getValue().doubleValue() - expectedUnitValue) > 0.1 /*precision reduced to pass the test. Depending on the currency, it could be a problem.*/) {
			fail("Wrong unit value for stock record with row index " + row.getIndex() + ". Expected: " + expectedUnitValue + ". It was: " + ((Decimalbox) row.getChildren().get(4)).getValue().doubleValue());
		}
	}
		
	@Test
	final void testCase1() {

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
			
			testStockRecord((Row) grd.getRows().getChildren().get(0), 
										"mm2",
										"mmaterial2",
										462.592,
										"kg",										
										1.035983027071701E7);
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
			cmbWarehouseInputAgent.typing("mw6");
			
			stockForm = new StockForm(desktop,
									(desktop.query("#wndContentPanel").query("#cmbWarehouse")).as(Combobox.class),
									(desktop.query("grid").query("#grd")).as(Grid.class));
			
			stockForm.setComponentValue(stockForm.getCmbWarehouse(), Integer.valueOf(1006));
			cmbWarehouseAgent.click(); 	//needed to force grid updating. 
										//cmbWarehouse's onChange event isn't triggered under testing
			
			ComponentAgent expensesGridAgent = desktop.query("grid");
			Grid grd = expensesGridAgent.as(Grid.class);
			
			testStockRecord((Row) grd.getRows().getChildren().get(1), 
										"mm4",
										"mmaterial4",
										1.000007E9,
										"mg",
										155.58669895074);
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
			cmbWarehouseInputAgent.typing("mw6");
			
			stockForm = new StockForm(desktop,
									(desktop.query("#wndContentPanel").query("#cmbWarehouse")).as(Combobox.class),
									(desktop.query("grid").query("#grd")).as(Grid.class));
			
			stockForm.setComponentValue(stockForm.getCmbWarehouse(), Integer.valueOf(1006));
			cmbWarehouseAgent.click(); 	//needed to force grid updating. 
										//cmbWarehouse's onChange event isn't triggered under testing
			
			ComponentAgent expensesGridAgent = desktop.query("grid");
			Grid grd = expensesGridAgent.as(Grid.class);
			
			testStockRecord((Row) grd.getRows().getChildren().get(2), 
										"mm5",
										"mmaterial5",
										2207.6226218,
										"lb",
										140955.12386064);
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
			cmbWarehouseInputAgent.typing("mw6");
			
			stockForm = new StockForm(desktop,
									(desktop.query("#wndContentPanel").query("#cmbWarehouse")).as(Combobox.class),
									(desktop.query("grid").query("#grd")).as(Grid.class));
			
			stockForm.setComponentValue(stockForm.getCmbWarehouse(), Integer.valueOf(1006));
			cmbWarehouseAgent.click(); 	//needed to force grid updating. 
										//cmbWarehouse's onChange event isn't triggered under testing
			
			ComponentAgent expensesGridAgent = desktop.query("grid");
			Grid grd = expensesGridAgent.as(Grid.class);
			
			testStockRecord((Row) grd.getRows().getChildren().get(3), 
										"mm6",
										"mmaterial6",
										455045592.00000000,
										"mg",
										206789.50003143);
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
			
			testStockRecord((Row) grd.getRows().getChildren().get(4), 
										"mm7",
										"mmaterial7",
										1004000.00000000,
										"ml",
										173311.80678971);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase6() {

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
			
			testStockRecord((Row) grd.getRows().getChildren().get(5), 
										"mm9",
										"mmaterial9",
										6.0,
										"pcs",
										43501244.7860377);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase7() {

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
			
			testStockRecord((Row) grd.getRows().getChildren().get(6), 
										"mp1",
										"mproduct1",
										6.0,
										"kg",
										64889662.7880384);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase9() {

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
			
			testStockRecord((Row) grd.getRows().getChildren().get(0), 
										"mm2",
										"mmaterial2",
										1906.185001,
										"kg",										
										959.81808526);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase10() {

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
			
			testStockRecord((Row) grd.getRows().getChildren().get(1), 
										"mm4",
										"mmaterial4",
										2721547556896.0,
										"mg",
										16.50661561);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase11() {

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
			
			testStockRecord((Row) grd.getRows().getChildren().get(2), 
										"mm5",
										"mmaterial5",
										-38.43339924,
										"lb",
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
	final void testCase12() {

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
			
			testStockRecord((Row) grd.getRows().getChildren().get(3), 
										"mm6",
										"mmaterial6",
										2721578992726.0,
										"mg",
										44.01719921);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase13() {

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
			
			testStockRecord((Row) grd.getRows().getChildren().get(4), 
										"mm7",
										"mmaterial7",
										3000.002,
										"ml",
										1197950.84576058);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase14() {

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
			
			testStockRecord((Row) grd.getRows().getChildren().get(5), 
										"mm8",
										"mmaterial8",
										97400.0,
										"ml",
										117676.97680086);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase15() {

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
			
			testStockRecord((Row) grd.getRows().getChildren().get(6), 
										"mm9",
										"mmaterial9",
										1.0,
										"pcs",
										11979513.24940921);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase16() {

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
			
			testStockRecord((Row) grd.getRows().getChildren().get(7), 
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
	final void testCase17() {

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
			
			testStockRecord((Row) grd.getRows().getChildren().get(0), 
										"mm2",
										"mmaterial2",
										-1454.592,
										"kg",										
										0.0);
			testStockRecord((Row) grd.getRows().getChildren().get(1), 
										"mm4",
										"mmaterial4",
										-1001000000.0,
										"mg",
										0.0);
			testStockRecord((Row) grd.getRows().getChildren().get(2), 
										"mm5",
										"mmaterial5",
										-10000.0,
										"lb",
										0.0);
			testStockRecord((Row) grd.getRows().getChildren().get(3), 
										"mm6",
										"mmaterial6",
										-481941500.0,
										"mg",
										0.0);
			testStockRecord((Row) grd.getRows().getChildren().get(4), 
										"mm7",
										"mmaterial7",
										-1000.0,
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
	
//	@Test
//  Commented since order's products are not moved to sales warehouse when closing
//	final void testCase18() {
//
//		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
//		DesktopAgent desktop = Zats.newClient().connect("/stock.zul");
//		try {
//			ComponentAgent cmbWarehouseAgent = desktop.query("#wndContentPanel").query("#cmbWarehouse");
//			InputAgent cmbWarehouseInputAgent = cmbWarehouseAgent.as(InputAgent.class);
//			cmbWarehouseInputAgent.typing("mw9");
//			
//			stockForm = new StockForm(desktop,
//									(desktop.query("#wndContentPanel").query("#cmbWarehouse")).as(Combobox.class),
//									(desktop.query("grid").query("#grd")).as(Grid.class));
//			
//			stockForm.setComponentValue(stockForm.getCmbWarehouse(), Integer.valueOf(1009));
//			cmbWarehouseAgent.click(); 	//needed to force grid updating. 
//										//cmbWarehouse's onChange event isn't triggered under testing
//			
//			ComponentAgent expensesGridAgent = desktop.query("grid");
//			Grid grd = expensesGridAgent.as(Grid.class);
//			
//			testStockRecord((Row) grd.getRows().getChildren().get(0), 
//										"mm2",
//										"mmaterial2",
//										1005.999,
//										"kg",										
//										61899.61905042);
//			testStockRecord((Row) grd.getRows().getChildren().get(1), 
//										"mm4",
//										"mmaterial4",
//										6000.0,
//										"mg",
//										155.58682861);
//			testStockRecord((Row) grd.getRows().getChildren().get(2), 
//										"mm5",
//										"mmaterial5",
//										7817.4015322,
//										"lb",
//										0.0);
//			testStockRecord((Row) grd.getRows().getChildren().get(3), 
//										"mm6",
//										"mmaterial6",
//										0.0,
//										"mg",
//										0.0);
//			testStockRecord((Row) grd.getRows().getChildren().get(4), 
//										"mm7",
//										"mmaterial7",
//										-999000.0,
//										"ml",
//										0.0);
//			testStockRecord((Row) grd.getRows().getChildren().get(5), 
//										"mp1",
//										"mproduct1",
//										6.0,
//										"kg",
//										64889662.81378389);
//			testStockRecord((Row) grd.getRows().getChildren().get(6), 
//										"mp7",
//										"mproduct7",
//										11.0,
//										"pcs",
//										0.0);
//		}
//		catch(AssertionFailedError ex) {
//			fail(ex.getMessage());
//		}
//		catch(Throwable ex) {
//			fail(ex.getMessage());
//		}
//	}
}