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
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Row;

import co.syscoop.soberano.test.helper.MaterialExpenseActionTest;
import co.syscoop.soberano.test.helper.TestUtilityCode;
import co.syscoop.soberano.util.SpringUtility;

@Order(17)

//TODO: enable test
//@Disabled

@TestMethodOrder(OrderAnnotation.class)
class OO17_MaterialExpenseTest_cancel_and_proper_inventory_recalculation extends MaterialExpenseActionTest {
	
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

		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		try {
			ComponentAgent expensesGridAgent = desktop.query("grid");
			Grid grd = expensesGridAgent.as(Grid.class);
			
			Button btnCancel = (Button) grd.getRows().getChildren().get(4).getChildren().get(8).query("vbox").getChildren().get(3);
			ComponentAgent btnCancelAgent = desktop.query("grid").query("#" + btnCancel.getId());
			btnCancelAgent.click();
			btnCancelAgent.click();
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(IndexOutOfBoundsException ex) {
			//User18 is a salesclerk. Grid empty. It can not even retrieve expenses.
		}
	}
	
	@Test
	@Order(2)
	final void testCase2() {

		SpringUtility.setLoggedUserForTesting("user17@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		try {
			ComponentAgent expensesGridAgent = desktop.query("grid");
			Grid grd = expensesGridAgent.as(Grid.class);
			
			Button btnCancel = (Button) grd.getRows().getChildren().get(4).getChildren().get(8).query("vbox").getChildren().get(3);
			ComponentAgent btnCancelAgent = desktop.query("grid").query("#" + btnCancel.getId());
			btnCancelAgent.click();
			btnCancelAgent.click();
			
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
	@Order(3)
	final void testCase3() {

		SpringUtility.setLoggedUserForTesting("user14@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		try {
			ComponentAgent expensesGridAgent = desktop.query("grid");
			Grid grd = expensesGridAgent.as(Grid.class);
			
			Button btnCancel = (Button) grd.getRows().getChildren().get(4).getChildren().get(8).query("vbox").getChildren().get(3);
			ComponentAgent btnCancelAgent = desktop.query("grid").query("#" + btnCancel.getId());
			btnCancelAgent.click();
			btnCancelAgent.click();
			
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
	@Order(4)
	final void testCase4() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		try {
			ComponentAgent expensesGridAgent = desktop.query("grid");
			Grid grd = expensesGridAgent.as(Grid.class);
			
			Button btnCancel = (Button) grd.getRows().getChildren().get(10).getChildren().get(8).query("vbox").getChildren().get(3);
			ComponentAgent btnCancelAgent = desktop.query("grid").query("#" + btnCancel.getId());
			btnCancelAgent.click();
			btnCancelAgent.click();
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	@Order(5)
	final void testCase5() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		try {
			ComponentAgent expensesGridAgent = desktop.query("grid");
			Grid grd = expensesGridAgent.as(Grid.class);
			assertEquals(12, grd.getRows().getChildren().size(), "Wrong count of recorded material expenses.");
			
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(0), 
										"mprov2",
										"mmaterial8",
										"100000.00000000 ml",
										20000.00000000,
										"mc2",
										"process run tests precond");			
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(1), 
										"mprov2",
										"mmaterial7",
										"1000.00100000 ml",
										500000.00000000,
										"mc2",
										"inventory operation tests precond");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(2), 
										"mprov2",
										"mmaterial6",
										"1360776002001.00000000 mg",
										800000000000000.0000000,
										"mc2",
										"inventory operation tests precond");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(3), 
										"mprov2",
										"mmaterial4",
										"1360776000000.00000000 mg",
										300000000000000.0000000,
										"mc2",
										"inventory operation tests precond");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(4), 
										"mprov2",
										"mmaterial2",
										"1000.00000000 kg",
										2000.00000000,
										"mc2",
										"inventory operation tests precond");			
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(5), 
										"mprov1",
										"mmaterial6",
										"2000.00000000 mg",
										0.00000100,
										"mc3",
										"abc123");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(6), 
										"mprov2",
										"mmaterial2",
										"0.00000100 mg",
										1.00000100,
										"mc1",
										"abc123");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(7), 
										"mprov1",
										"mmaterial2",
										"2000.00000000 lb",
										3000000.00000000,
										"mc3",
										"");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(8), 
										"mprov1",
										"mmaterial7",
										"1.00000100 l",
										0.00000100,
										"mc2",
										"abc123");			
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(9), 
										"mprov1",
										"mmaterial2",
										"1.00000000 mg",
										3000000.00000000,
										"mc2",
										"abc123");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(10), 
										"mprov1",
										"mmaterial4",
										"3000000.00000000 lb",
										1.000001,
										"mc2",
										"abc123");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(11), 
										"mprov2",
										"mmaterial6",
										"1.00000000 mg",
										0.000001,
										"mc3",
										"abc123");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(6)
	final void testCase6() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		try {
			ComponentAgent expensesGridAgent = desktop.query("grid");
			Grid grd = expensesGridAgent.as(Grid.class);
			
			Button btnCancel = (Button) grd.getRows().getChildren().get(8).getChildren().get(8).query("vbox").getChildren().get(3);
			ComponentAgent btnCancelAgent = desktop.query("grid").query("#" + btnCancel.getId());
			btnCancelAgent.click();
			btnCancelAgent.click();
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
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		try {
			ComponentAgent expensesGridAgent = desktop.query("grid");
			Grid grd = expensesGridAgent.as(Grid.class);
			assertEquals(11, grd.getRows().getChildren().size(), "Wrong count of recorded material expenses.");
			
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(0), 
										"mprov2",
										"mmaterial8",
										"100000.00000000 ml",
										20000.00000000,
										"mc2",
										"process run tests precond");			
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(1), 
										"mprov2",
										"mmaterial7",
										"1000.00100000 ml",
										500000.00000000,
										"mc2",
										"inventory operation tests precond");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(2), 
										"mprov2",
										"mmaterial6",
										"1360776002001.00000000 mg",
										800000000000000.0000000,
										"mc2",
										"inventory operation tests precond");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(3), 
										"mprov2",
										"mmaterial4",
										"1360776000000.00000000 mg",
										300000000000000.0000000,
										"mc2",
										"inventory operation tests precond");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(4), 
										"mprov2",
										"mmaterial2",
										"1000.00000000 kg",
										2000.00000000,
										"mc2",
										"inventory operation tests precond");			
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(5), 
										"mprov1",
										"mmaterial6",
										"2000.00000000 mg",
										0.00000100,
										"mc3",
										"abc123");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(6), 
										"mprov2",
										"mmaterial2",
										"0.00000100 mg",
										1.00000100,
										"mc1",
										"abc123");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(7), 
										"mprov1",
										"mmaterial2",
										"2000.00000000 lb",
										3000000.00000000,
										"mc3",
										"");	
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(8), 
										"mprov1",
										"mmaterial2",
										"1.00000000 mg",
										3000000.00000000,
										"mc2",
										"abc123");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(9), 
										"mprov1",
										"mmaterial4",
										"3000000.00000000 lb",
										1.000001,
										"mc2",
										"abc123");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(10), 
										"mprov2",
										"mmaterial6",
										"1.00000000 mg",
										0.000001,
										"mc3",
										"abc123");
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
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		try {
			ComponentAgent expensesGridAgent = desktop.query("grid");
			Grid grd = expensesGridAgent.as(Grid.class);
			
			Button btnCancel = (Button) grd.getRows().getChildren().get(7).getChildren().get(8).query("vbox").getChildren().get(3);
			ComponentAgent btnCancelAgent = desktop.query("grid").query("#" + btnCancel.getId());
			btnCancelAgent.click();
			btnCancelAgent.click();
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
		DesktopAgent desktop = Zats.newClient().connect("/material_expenses.zul");
		try {
			ComponentAgent expensesGridAgent = desktop.query("grid");
			Grid grd = expensesGridAgent.as(Grid.class);
			assertEquals(10, grd.getRows().getChildren().size(), "Wrong count of recorded material expenses.");
			
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(0), 
								"mprov2",
								"mmaterial8",
								"100000.00000000 ml",
								20000.00000000,
								"mc2",
								"process run tests precond");			
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(1), 
								"mprov2",
								"mmaterial7",
								"1000.00100000 ml",
								500000.00000000,
								"mc2",
								"inventory operation tests precond");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(2), 
								"mprov2",
								"mmaterial6",
								"1360776002001.00000000 mg",
								800000000000000.0000000,
								"mc2",
								"inventory operation tests precond");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(3), 
								"mprov2",
								"mmaterial4",
								"1360776000000.00000000 mg",
								300000000000000.0000000,
								"mc2",
								"inventory operation tests precond");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(4), 
								"mprov2",
								"mmaterial2",
								"1000.00000000 kg",
								2000.00000000,
								"mc2",
								"inventory operation tests precond");			
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(5), 
								"mprov1",
								"mmaterial6",
								"2000.00000000 mg",
								0.00000100,
								"mc3",
								"abc123");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(6), 
								"mprov2",
								"mmaterial2",
								"0.00000100 mg",
								1.00000100,
								"mc1",
								"abc123");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(7), 
								"mprov1",
								"mmaterial2",
								"1.00000000 mg",
								3000000.00000000,
								"mc2",
								"abc123");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(8), 
								"mprov1",
								"mmaterial4",
								"3000000.00000000 lb",
								1.000001,
								"mc2",
								"abc123");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(9), 
								"mprov2",
								"mmaterial6",
								"1.00000000 mg",
								0.000001,
								"mc3",
								"abc123");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
}