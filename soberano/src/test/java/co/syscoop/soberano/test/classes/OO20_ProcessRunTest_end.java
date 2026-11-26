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
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

import co.syscoop.soberano.test.helper.ConstrainedForm;
import co.syscoop.soberano.test.helper.ProcessRunActionTest;
import co.syscoop.soberano.test.helper.ProcessRunForm;
import co.syscoop.soberano.util.SpringUtility;

@Order(20)

//@Disabled

@TestMethodOrder(OrderAnnotation.class)
class OO20_ProcessRunTest_end extends ProcessRunActionTest {
	
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
		Row runRow = (Row) grd.getRows().getChildren().get(4);
		String runId = ((Label) runRow.getChildren().get(0)).getValue(); 
		try {
			desktop = Zats.newClient().connect("/process_run.zul?id=" + runId);			
			clickOnEndButton(desktop);
			clickOnEndButton(desktop);
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

		SpringUtility.setLoggedUserForTesting("user3@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		Grid grd = desktop.query("grid").as(Grid.class);
		Row runRow = (Row) grd.getRows().getChildren().get(3);
		String runId = ((Label) runRow.getChildren().get(0)).getValue(); 
		try {
			desktop = Zats.newClient().connect("/process_run.zul?id=" + runId);			
			clickOnEndButton(desktop);
			clickOnEndButton(desktop);
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

		SpringUtility.setLoggedUserForTesting("user17@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		Grid grd = desktop.query("grid").as(Grid.class);
		Row runRow = (Row) grd.getRows().getChildren().get(2);
		String runId = ((Label) runRow.getChildren().get(0)).getValue(); 
		try {
			desktop = Zats.newClient().connect("/process_run.zul?id=" + runId);			
			clickOnEndButton(desktop);
			clickOnEndButton(desktop);
			
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

		SpringUtility.setLoggedUserForTesting("user3@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		Grid grd = desktop.query("grid").as(Grid.class);
		Row runRow = (Row) grd.getRows().getChildren().get(2);
		String runId = ((Label) runRow.getChildren().get(0)).getValue(); 
		try {
			desktop = Zats.newClient().connect("/process_run.zul?id=" + runId);			
			clickOnEndButton(desktop);
			clickOnEndButton(desktop);
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

		SpringUtility.setLoggedUserForTesting("user2@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		Grid grd = desktop.query("grid").as(Grid.class);
		Row runRow = (Row) grd.getRows().getChildren().get(1);
		String runId = ((Label) runRow.getChildren().get(0)).getValue(); 
		try {
			desktop = Zats.newClient().connect("/process_run.zul?id=" + runId);			
			clickOnEndButton(desktop);
			clickOnEndButton(desktop);
			
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

		SpringUtility.setLoggedUserForTesting("user3@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		Grid grd = desktop.query("grid").as(Grid.class);
		Row runRow = (Row) grd.getRows().getChildren().get(1);
		String runId = ((Label) runRow.getChildren().get(0)).getValue(); 
		try {
			desktop = Zats.newClient().connect("/process_run.zul?id=" + runId);			
			clickOnEndButton(desktop);
			clickOnEndButton(desktop);
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

		SpringUtility.setLoggedUserForTesting("user19@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		Grid grd = desktop.query("grid").as(Grid.class);
		Row runRow = (Row) grd.getRows().getChildren().get(0);
		String runId = ((Label) runRow.getChildren().get(0)).getValue(); 
		try {
			desktop = Zats.newClient().connect("/process_run.zul?id=" + runId);			
			clickOnEndButton(desktop);
			clickOnEndButton(desktop);
			
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
	@Order(8)
	final void testCase8() {

		SpringUtility.setLoggedUserForTesting("user3@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		Grid grd = desktop.query("grid").as(Grid.class);
		Row runRow = (Row) grd.getRows().getChildren().get(0);
		String runId = ((Label) runRow.getChildren().get(0)).getValue(); 
		try {
			desktop = Zats.newClient().connect("/process_run.zul?id=" + runId);
			
			ComponentAgent intWeightAgent = desktop.query("#wndContentPanel").
					query("#incProcessIOs").
					query("#intWeight" + "mm7");			
			Intbox intWeight = intWeightAgent.as(Intbox.class);			
			ConstrainedForm constrainedForm = new ConstrainedForm();
			constrainedForm.setComponentValue(intWeight, 20);
			
			intWeightAgent = desktop.query("#wndContentPanel").
					query("#incProcessIOs").
					query("#intWeight" + "mm8");			
			intWeight = intWeightAgent.as(Intbox.class);			
			constrainedForm.setComponentValue(intWeight, 30);
			
			intWeightAgent = desktop.query("#wndContentPanel").
					query("#incProcessIOs").
					query("#intWeight" + "mm9");			
			intWeight = intWeightAgent.as(Intbox.class);			
			constrainedForm.setComponentValue(intWeight, 60);
			
			clickOnEndButton(desktop);
			clickOnEndButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testWeightsMustSum100(ex);
		}
	}
	
	@Test
	@Order(9)
	final void testCase9() {

		SpringUtility.setLoggedUserForTesting("user3@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		Grid grd = desktop.query("grid").as(Grid.class);
		Row runRow = (Row) grd.getRows().getChildren().get(0);
		String runId = ((Label) runRow.getChildren().get(0)).getValue(); 
		try {
			desktop = Zats.newClient().connect("/process_run.zul?id=" + runId);
			
			ComponentAgent decOutputQuantityAgent = desktop.query("#wndContentPanel").
					query("#incProcessIOs").
					query("#decOutputQuantity" + "mm7");			
			Decimalbox decOutputQuantity = decOutputQuantityAgent.as(Decimalbox.class);
			ConstrainedForm constrainedForm = new ConstrainedForm();
			constrainedForm.setComponentValue(decOutputQuantity, new BigDecimal(4000));
			
			decOutputQuantityAgent = desktop.query("#wndContentPanel").
					query("#incProcessIOs").
					query("#decOutputQuantity" + "mm8");			
			decOutputQuantity = decOutputQuantityAgent.as(Decimalbox.class);
			constrainedForm = new ConstrainedForm();
			constrainedForm.setComponentValue(decOutputQuantity, new BigDecimal(0));
			
			decOutputQuantityAgent = desktop.query("#wndContentPanel").
					query("#incProcessIOs").
					query("#decOutputQuantity" + "mm9");			
			decOutputQuantity = decOutputQuantityAgent.as(Decimalbox.class);
			constrainedForm = new ConstrainedForm();
			constrainedForm.setComponentValue(decOutputQuantity, new BigDecimal(6));
			
			ComponentAgent intWeightAgent = desktop.query("#wndContentPanel").
					query("#incProcessIOs").
					query("#intWeight" + "mm7");			
			Intbox intWeight = intWeightAgent.as(Intbox.class);			
			constrainedForm.setComponentValue(intWeight, 20);
			
			intWeightAgent = desktop.query("#wndContentPanel").
					query("#incProcessIOs").
					query("#intWeight" + "mm8");			
			intWeight = intWeightAgent.as(Intbox.class);			
			constrainedForm.setComponentValue(intWeight, 60);
			
			intWeightAgent = desktop.query("#wndContentPanel").
					query("#incProcessIOs").
					query("#intWeight" + "mm9");			
			intWeight = intWeightAgent.as(Intbox.class);			
			constrainedForm.setComponentValue(intWeight, 20);
			
			clickOnEndButton(desktop);
			clickOnEndButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
}