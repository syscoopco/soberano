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
import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

import co.syscoop.soberano.test.helper.ProcessRunActionTest;
import co.syscoop.soberano.test.helper.ProcessRunForm;
import co.syscoop.soberano.util.SpringUtility;

@Order(18)

@Disabled

@TestMethodOrder(OrderAnnotation.class)
class OO18_ProcessRunTest_cancel extends ProcessRunActionTest {
	
	protected ProcessRunForm processRunForm = null;

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

		SpringUtility.setLoggedUserForTesting("user2@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		Grid grd = desktop.query("grid").as(Grid.class);
		Row runRow = (Row) grd.getRows().getChildren().get(2);
		String runId = ((Label) runRow.getChildren().get(0)).getValue(); 
		try {
			desktop = Zats.newClient().connect("/process_run.zul?id=" + runId);			
			clickOnCancelButton(desktop);
			clickOnCancelButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(2)
	final void testCase2() {

		SpringUtility.setLoggedUserForTesting("user5@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		Grid grd = desktop.query("grid").as(Grid.class);
		Row runRow = (Row) grd.getRows().getChildren().get(1);
		String runId = ((Label) runRow.getChildren().get(0)).getValue(); 
		try {
			desktop = Zats.newClient().connect("/process_run.zul?id=" + runId);			
			clickOnCancelButton(desktop);
			clickOnCancelButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(3)
	final void testCase3() {

		SpringUtility.setLoggedUserForTesting("user14@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		Grid grd = desktop.query("grid").as(Grid.class);
		Row runRow = (Row) grd.getRows().getChildren().get(0);
		String runId = ((Label) runRow.getChildren().get(0)).getValue(); 
		try {
			desktop = Zats.newClient().connect("/process_run.zul?id=" + runId);			
			clickOnCancelButton(desktop);
			clickOnCancelButton(desktop);
			
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

		SpringUtility.setLoggedUserForTesting("user13@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		Grid grd = desktop.query("grid").as(Grid.class);
		Row runRow = (Row) grd.getRows().getChildren().get(0);
		String runId = ((Label) runRow.getChildren().get(0)).getValue(); 
		try {
			desktop = Zats.newClient().connect("/process_run.zul?id=" + runId);			
			clickOnCancelButton(desktop);
			clickOnCancelButton(desktop);
			
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
	@Order(5)
	final void testCase5() {

		SpringUtility.setLoggedUserForTesting("user4@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		Grid grd = desktop.query("grid").as(Grid.class);
		Row runRow = (Row) grd.getRows().getChildren().get(0);
		String runId = ((Label) runRow.getChildren().get(0)).getValue(); 
		try {
			desktop = Zats.newClient().connect("/process_run.zul?id=" + runId);			
			clickOnCancelButton(desktop);
			clickOnCancelButton(desktop);
			
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

		SpringUtility.setLoggedUserForTesting("user2@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		Grid grd = desktop.query("grid").as(Grid.class);
		Row runRow = (Row) grd.getRows().getChildren().get(0);
		String runId = ((Label) runRow.getChildren().get(0)).getValue(); 
		try {
			desktop = Zats.newClient().connect("/process_run.zul?id=" + runId);			
			clickOnCancelButton(desktop);
			clickOnCancelButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testNotEnoughRightsException(ex);
		}
	}
}