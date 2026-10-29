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

import co.syscoop.soberano.test.helper.ServiceExpenseActionTest;
import co.syscoop.soberano.test.helper.TestUtilityCode;
import co.syscoop.soberano.util.SpringUtility;

@Order(9)

@Disabled

@TestMethodOrder(OrderAnnotation.class)
class O9_ServiceExpenseTest_cancel extends ServiceExpenseActionTest {
	
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
	@Order(1)
	final void testCase1() {

		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/service_expenses.zul");
		try {
			ComponentAgent expensesGridAgent = desktop.query("grid");
			Grid grd = expensesGridAgent.as(Grid.class);
			
			Button btnCancel = (Button) grd.getRows().getChildren().get(4).getChildren().get(5).query("vbox").getChildren().get(3);
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
		DesktopAgent desktop = Zats.newClient().connect("/service_expenses.zul");
		try {
			ComponentAgent expensesGridAgent = desktop.query("grid");
			Grid grd = expensesGridAgent.as(Grid.class);
			
			Button btnCancel = (Button) grd.getRows().getChildren().get(4).getChildren().get(5).query("vbox").getChildren().get(3);
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
		DesktopAgent desktop = Zats.newClient().connect("/service_expenses.zul");
		try {
			ComponentAgent expensesGridAgent = desktop.query("grid");
			Grid grd = expensesGridAgent.as(Grid.class);
			
			Button btnCancel = (Button) grd.getRows().getChildren().get(4).getChildren().get(5).query("vbox").getChildren().get(3);
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

		SpringUtility.setLoggedUserForTesting("user2@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/service_expenses.zul");
		try {
			ComponentAgent expensesGridAgent = desktop.query("grid");
			Grid grd = expensesGridAgent.as(Grid.class);
			
			Button btnCancel = (Button) grd.getRows().getChildren().get(4).getChildren().get(5).query("vbox").getChildren().get(3);
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
		DesktopAgent desktop = Zats.newClient().connect("/service_expenses.zul");
		try {
			ComponentAgent expensesGridAgent = desktop.query("grid");
			Grid grd = expensesGridAgent.as(Grid.class);
			assertEquals(4, grd.getRows().getChildren().size(), "Wrong count of recorded service expenses.");
			
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(0), 
										"mprov2",
										"",
										"mservice3 : ms3",
										1.0,
										"mc3",
										"abc123");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(1), 
										"mprov1",
										"",
										"mservice1 : ms1",
										1.000001,
										"mc2",
										"");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(2), 
										"mprov1",
										"",
										"mservice2 : ms2",
										3000000.0,
										"mc1",
										"");
			TestUtilityCode.testExpense((Row) grd.getRows().getChildren().get(3), 
										"mprov2",
										"",
										"mservice3 : ms3",
										2000.0,
										"mc2",
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