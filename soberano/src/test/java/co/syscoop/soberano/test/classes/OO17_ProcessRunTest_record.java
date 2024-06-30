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
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import co.syscoop.soberano.test.helper.ProcessRunActionTest;
import co.syscoop.soberano.test.helper.ProcessRunForm;
import co.syscoop.soberano.util.SpringUtility;

@Order(17)

@Disabled

@TestMethodOrder(OrderAnnotation.class)
class OO17_ProcessRunTest_record extends ProcessRunActionTest {
	
	protected ProcessRunForm processRunForm = null;

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
	@Order(0)
	final void testCase0() {
		SpringUtility.setLoggedUserForTesting("user2@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		processRunForm = new ProcessRunForm(desktop,
											desktop.query("textbox").query("#cmbProcess").as(Combobox.class),
											desktop.query("textbox").query("#cmbCostCenter").as(Combobox.class),
											desktop.query("intbox").query("#intRuns").as(Intbox.class),																					
											desktop.query("south").query("button").query("#btnRecord").as(Button.class),
											desktop.query("grid").query("#grd").as(Grid.class));
		try {
			//this is needed to execute only in the first test. it has to do with testing configuration.
			processRunForm.testEachConstrainedObjectIsDeclared();
			processRunForm.testEachDeclaredConstrainedObjectIsActuallyConstrained();
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	@Order(1)
	final void testCase1() {

		SpringUtility.setLoggedUserForTesting("user2@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		ProcessRunForm processRunForm = new ProcessRunForm(desktop,
															(desktop.query("textbox").query("#cmbProcess")).as(Combobox.class),
															(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class),
															(desktop.query("intbox").query("#intRuns")).as(Intbox.class),																					
															desktop.query("south").query("button").query("#btnRecord").as(Button.class),
															desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			processRunForm.setComponentValue(processRunForm.getIntRuns(), 1);	
			
			ComponentAgent cmbProcessAgent = desktop.query("textbox").query("#cmbProcess");
			InputAgent cmbProcessInputAgent = cmbProcessAgent.as(InputAgent.class);
			cmbProcessInputAgent.typing("mpr7");
			selectComboitemByLabel(processRunForm.getCmbProcess(), "mpr7");
			cmbProcessAgent.click();			
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc4");
			selectComboitemByLabel(processRunForm.getCmbCostCenter(), "mcc4");
			cmbCostCenterAgent.click();			
			
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
	@Order(2)
	final void testCase2() {

		SpringUtility.setLoggedUserForTesting("user2@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		ProcessRunForm processRunForm = new ProcessRunForm(desktop,
															(desktop.query("textbox").query("#cmbProcess")).as(Combobox.class),
															(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class),
															(desktop.query("intbox").query("#intRuns")).as(Intbox.class),																					
															desktop.query("south").query("button").query("#btnRecord").as(Button.class),
															desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			processRunForm.setComponentValue(processRunForm.getIntRuns(), 3);
			
			ComponentAgent cmbProcessAgent = desktop.query("textbox").query("#cmbProcess");
			InputAgent cmbProcessInputAgent = cmbProcessAgent.as(InputAgent.class);
			cmbProcessInputAgent.typing("mpr4");
			selectComboitemByLabel(processRunForm.getCmbProcess(), "mpr4");
			cmbProcessAgent.click();
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc5");
			selectComboitemByLabel(processRunForm.getCmbCostCenter(), "mcc5");
			cmbCostCenterAgent.click();	
						
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
	@Order(3)
	final void testCase3() {

		SpringUtility.setLoggedUserForTesting("user8@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		ProcessRunForm processRunForm = new ProcessRunForm(desktop,
															(desktop.query("textbox").query("#cmbProcess")).as(Combobox.class),
															(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class),
															(desktop.query("intbox").query("#intRuns")).as(Intbox.class),																					
															desktop.query("south").query("button").query("#btnRecord").as(Button.class),
															desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			processRunForm.setComponentValue(processRunForm.getIntRuns(), 3);	
			
			ComponentAgent cmbProcessAgent = desktop.query("textbox").query("#cmbProcess");
			InputAgent cmbProcessInputAgent = cmbProcessAgent.as(InputAgent.class);
			cmbProcessInputAgent.typing("mpr5");
			selectComboitemByLabel(processRunForm.getCmbProcess(), "mpr5");
			cmbProcessAgent.click();
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc4");
			selectComboitemByLabel(processRunForm.getCmbCostCenter(), "mcc4");
			cmbCostCenterAgent.click();		
						
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
	@Order(4)
	final void testCase4() {

		SpringUtility.setLoggedUserForTesting("user3@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		ProcessRunForm processRunForm = new ProcessRunForm(desktop,
															(desktop.query("textbox").query("#cmbProcess")).as(Combobox.class),
															(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class),
															(desktop.query("intbox").query("#intRuns")).as(Intbox.class),																					
															desktop.query("south").query("button").query("#btnRecord").as(Button.class),
															desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			processRunForm.setComponentValue(processRunForm.getIntRuns(), 3);	
			
			ComponentAgent cmbProcessAgent = desktop.query("textbox").query("#cmbProcess");
			InputAgent cmbProcessInputAgent = cmbProcessAgent.as(InputAgent.class);
			cmbProcessInputAgent.typing("mpr7");
			selectComboitemByLabel(processRunForm.getCmbProcess(), "mpr7");
			cmbProcessAgent.click();
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc6");
			selectComboitemByLabel(processRunForm.getCmbCostCenter(), "mcc6");
			cmbCostCenterAgent.click();		
						
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
	@Order(5)
	final void testCase5() {

		SpringUtility.setLoggedUserForTesting("user9@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		ProcessRunForm processRunForm = new ProcessRunForm(desktop,
															(desktop.query("textbox").query("#cmbProcess")).as(Combobox.class),
															(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class),
															(desktop.query("intbox").query("#intRuns")).as(Intbox.class),																					
															desktop.query("south").query("button").query("#btnRecord").as(Button.class),
															desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			processRunForm.setComponentValue(processRunForm.getIntRuns(), 1);		
			
			ComponentAgent cmbProcessAgent = desktop.query("textbox").query("#cmbProcess");
			InputAgent cmbProcessInputAgent = cmbProcessAgent.as(InputAgent.class);
			cmbProcessInputAgent.typing("mpr4");
			selectComboitemByLabel(processRunForm.getCmbProcess(), "mpr4");
			cmbProcessAgent.click();
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc6");
			selectComboitemByLabel(processRunForm.getCmbCostCenter(), "mcc6");
			cmbCostCenterAgent.click();	
						
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

		SpringUtility.setLoggedUserForTesting("user3@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		ProcessRunForm processRunForm = new ProcessRunForm(desktop,
															(desktop.query("textbox").query("#cmbProcess")).as(Combobox.class),
															(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class),
															(desktop.query("intbox").query("#intRuns")).as(Intbox.class),																					
															desktop.query("south").query("button").query("#btnRecord").as(Button.class),
															desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			processRunForm.setComponentValue(processRunForm.getIntRuns(), 1);	
			
			ComponentAgent cmbProcessAgent = desktop.query("textbox").query("#cmbProcess");
			InputAgent cmbProcessInputAgent = cmbProcessAgent.as(InputAgent.class);
			cmbProcessInputAgent.typing("mpr5");
			selectComboitemByLabel(processRunForm.getCmbProcess(), "mpr5");
			cmbProcessAgent.click();
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc5");
			selectComboitemByLabel(processRunForm.getCmbCostCenter(), "mcc5");
			cmbCostCenterAgent.click();		
						
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

		SpringUtility.setLoggedUserForTesting("user2@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		ProcessRunForm processRunForm = new ProcessRunForm(desktop,
															(desktop.query("textbox").query("#cmbProcess")).as(Combobox.class),
															(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class),
															(desktop.query("intbox").query("#intRuns")).as(Intbox.class),																					
															desktop.query("south").query("button").query("#btnRecord").as(Button.class),
															desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			processRunForm.setComponentValue(processRunForm.getIntRuns(), 3);		
			
			ComponentAgent cmbProcessAgent = desktop.query("textbox").query("#cmbProcess");
			InputAgent cmbProcessInputAgent = cmbProcessAgent.as(InputAgent.class);
			cmbProcessInputAgent.typing("mpr5");
			selectComboitemByLabel(processRunForm.getCmbProcess(), "mpr5");
			cmbProcessAgent.click();
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc6");
			selectComboitemByLabel(processRunForm.getCmbCostCenter(), "mcc6");
			cmbCostCenterAgent.click();	
						
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
	@Order(8)
	final void testCase8() {

		SpringUtility.setLoggedUserForTesting("user17@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		ProcessRunForm processRunForm = new ProcessRunForm(desktop,
															(desktop.query("textbox").query("#cmbProcess")).as(Combobox.class),
															(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class),
															(desktop.query("intbox").query("#intRuns")).as(Intbox.class),																					
															desktop.query("south").query("button").query("#btnRecord").as(Button.class),
															desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			processRunForm.setComponentValue(processRunForm.getIntRuns(), 1);			
			
			ComponentAgent cmbProcessAgent = desktop.query("textbox").query("#cmbProcess");
			InputAgent cmbProcessInputAgent = cmbProcessAgent.as(InputAgent.class);
			cmbProcessInputAgent.typing("mpr2");
			selectComboitemByLabel(processRunForm.getCmbProcess(), "mpr2");
			cmbProcessAgent.click();
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc5");
			selectComboitemByLabel(processRunForm.getCmbCostCenter(), "mcc5");
			cmbCostCenterAgent.click();
						
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
	@Order(9)
	final void testCase9() {

		SpringUtility.setLoggedUserForTesting("user19@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		ProcessRunForm processRunForm = new ProcessRunForm(desktop,
															(desktop.query("textbox").query("#cmbProcess")).as(Combobox.class),
															(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class),
															(desktop.query("intbox").query("#intRuns")).as(Intbox.class),																					
															desktop.query("south").query("button").query("#btnRecord").as(Button.class),
															desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			processRunForm.setComponentValue(processRunForm.getIntRuns(), 3);		
			
			ComponentAgent cmbProcessAgent = desktop.query("textbox").query("#cmbProcess");
			InputAgent cmbProcessInputAgent = cmbProcessAgent.as(InputAgent.class);
			cmbProcessInputAgent.typing("mpr7");
			selectComboitemByLabel(processRunForm.getCmbProcess(), "mpr7");
			cmbProcessAgent.click();
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc5");
			selectComboitemByLabel(processRunForm.getCmbCostCenter(), "mcc5");
			cmbCostCenterAgent.click();	
						
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
	@Order(10)
	final void testCase10() {

		SpringUtility.setLoggedUserForTesting("user3@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		ProcessRunForm processRunForm = new ProcessRunForm(desktop,
															(desktop.query("textbox").query("#cmbProcess")).as(Combobox.class),
															(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class),
															(desktop.query("intbox").query("#intRuns")).as(Intbox.class),																					
															desktop.query("south").query("button").query("#btnRecord").as(Button.class),
															desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			processRunForm.setComponentValue(processRunForm.getIntRuns(), 3);	
			
			ComponentAgent cmbProcessAgent = desktop.query("textbox").query("#cmbProcess");
			InputAgent cmbProcessInputAgent = cmbProcessAgent.as(InputAgent.class);
			cmbProcessInputAgent.typing("mpr2");
			selectComboitemByLabel(processRunForm.getCmbProcess(), "mpr2");
			cmbProcessAgent.click();
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc4");
			selectComboitemByLabel(processRunForm.getCmbCostCenter(), "mcc4");
			cmbCostCenterAgent.click();		
						
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
	@Order(11)
	final void testCase11() {

		SpringUtility.setLoggedUserForTesting("user3@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		ProcessRunForm processRunForm = new ProcessRunForm(desktop,
															(desktop.query("textbox").query("#cmbProcess")).as(Combobox.class),
															(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class),
															(desktop.query("intbox").query("#intRuns")).as(Intbox.class),																					
															desktop.query("south").query("button").query("#btnRecord").as(Button.class),
															desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			processRunForm.setComponentValue(processRunForm.getIntRuns(), 3);
			
			ComponentAgent cmbProcessAgent = desktop.query("textbox").query("#cmbProcess");
			InputAgent cmbProcessInputAgent = cmbProcessAgent.as(InputAgent.class);
			cmbProcessInputAgent.typing("mpr4");
			selectComboitemByLabel(processRunForm.getCmbProcess(), "mpr4");
			cmbProcessAgent.click();
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc4");
			selectComboitemByLabel(processRunForm.getCmbCostCenter(), "mcc4");
			cmbCostCenterAgent.click();			
						
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
	@Order(12)
	final void testCase12() {

		SpringUtility.setLoggedUserForTesting("user2@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		ProcessRunForm processRunForm = new ProcessRunForm(desktop,
															(desktop.query("textbox").query("#cmbProcess")).as(Combobox.class),
															(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class),
															(desktop.query("intbox").query("#intRuns")).as(Intbox.class),																					
															desktop.query("south").query("button").query("#btnRecord").as(Button.class),
															desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			processRunForm.setComponentValue(processRunForm.getIntRuns(), 1);
			
			ComponentAgent cmbProcessAgent = desktop.query("textbox").query("#cmbProcess");
			InputAgent cmbProcessInputAgent = cmbProcessAgent.as(InputAgent.class);
			cmbProcessInputAgent.typing("mpr2");
			selectComboitemByLabel(processRunForm.getCmbProcess(), "mpr2");
			cmbProcessAgent.click();
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc6");
			selectComboitemByLabel(processRunForm.getCmbCostCenter(), "mcc6");
			cmbCostCenterAgent.click();			
						
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
	@Order(13)
	final void testCase13() {

		SpringUtility.setLoggedUserForTesting("user3@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		ProcessRunForm processRunForm = new ProcessRunForm(desktop,
															(desktop.query("textbox").query("#cmbProcess")).as(Combobox.class),
															(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class),
															(desktop.query("intbox").query("#intRuns")).as(Intbox.class),																					
															desktop.query("south").query("button").query("#btnRecord").as(Button.class),
															desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			processRunForm.setComponentValue(processRunForm.getIntRuns(), 2);
			
			ComponentAgent cmbProcessAgent = desktop.query("textbox").query("#cmbProcess");
			InputAgent cmbProcessInputAgent = cmbProcessAgent.as(InputAgent.class);
			cmbProcessInputAgent.typing("mpr2");
			selectComboitemByLabel(processRunForm.getCmbProcess(), "mpr2");
			cmbProcessAgent.click();
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc5");
			selectComboitemByLabel(processRunForm.getCmbCostCenter(), "mcc5");
			cmbCostCenterAgent.click();			
						
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

		SpringUtility.setLoggedUserForTesting("user3@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		ProcessRunForm processRunForm = new ProcessRunForm(desktop,
															(desktop.query("textbox").query("#cmbProcess")).as(Combobox.class),
															(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class),
															(desktop.query("intbox").query("#intRuns")).as(Intbox.class),																					
															desktop.query("south").query("button").query("#btnRecord").as(Button.class),
															desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			processRunForm.setComponentValue(processRunForm.getIntRuns(), 2);
			
			ComponentAgent cmbProcessAgent = desktop.query("textbox").query("#cmbProcess");
			InputAgent cmbProcessInputAgent = cmbProcessAgent.as(InputAgent.class);
			cmbProcessInputAgent.typing("mpr4");
			selectComboitemByLabel(processRunForm.getCmbProcess(), "mpr4");
			cmbProcessAgent.click();
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc5");
			selectComboitemByLabel(processRunForm.getCmbCostCenter(), "mcc5");
			cmbCostCenterAgent.click();			
						
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

		SpringUtility.setLoggedUserForTesting("user3@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		ProcessRunForm processRunForm = new ProcessRunForm(desktop,
															(desktop.query("textbox").query("#cmbProcess")).as(Combobox.class),
															(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class),
															(desktop.query("intbox").query("#intRuns")).as(Intbox.class),																					
															desktop.query("south").query("button").query("#btnRecord").as(Button.class),
															desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			processRunForm.setComponentValue(processRunForm.getIntRuns(), 2);	
			
			ComponentAgent cmbProcessAgent = desktop.query("textbox").query("#cmbProcess");
			InputAgent cmbProcessInputAgent = cmbProcessAgent.as(InputAgent.class);
			cmbProcessInputAgent.typing("mpr7");
			selectComboitemByLabel(processRunForm.getCmbProcess(), "mpr7");
			cmbProcessAgent.click();
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc5");
			selectComboitemByLabel(processRunForm.getCmbCostCenter(), "mcc5");
			cmbCostCenterAgent.click();		
						
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

		SpringUtility.setLoggedUserForTesting("user2@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		ProcessRunForm processRunForm = new ProcessRunForm(desktop,
															(desktop.query("textbox").query("#cmbProcess")).as(Combobox.class),
															(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class),
															(desktop.query("intbox").query("#intRuns")).as(Intbox.class),																					
															desktop.query("south").query("button").query("#btnRecord").as(Button.class),
															desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			processRunForm.setComponentValue(processRunForm.getIntRuns(), 1);			
			
			ComponentAgent cmbProcessAgent = desktop.query("textbox").query("#cmbProcess");
			InputAgent cmbProcessInputAgent = cmbProcessAgent.as(InputAgent.class);
			cmbProcessInputAgent.typing("mpr2");
			selectComboitemByLabel(processRunForm.getCmbProcess(), "mpr2");
			cmbProcessAgent.click();
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc4");
			selectComboitemByLabel(processRunForm.getCmbCostCenter(), "mcc4");
			cmbCostCenterAgent.click();
						
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
	@Order(17)
	final void testCase17() {

		SpringUtility.setLoggedUserForTesting("user2@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		ProcessRunForm processRunForm = new ProcessRunForm(desktop,
															(desktop.query("textbox").query("#cmbProcess")).as(Combobox.class),
															(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class),
															(desktop.query("intbox").query("#intRuns")).as(Intbox.class),																					
															desktop.query("south").query("button").query("#btnRecord").as(Button.class),
															desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			processRunForm.setComponentValue(processRunForm.getIntRuns(), 1);	
			
			ComponentAgent cmbProcessAgent = desktop.query("textbox").query("#cmbProcess");
			InputAgent cmbProcessInputAgent = cmbProcessAgent.as(InputAgent.class);
			cmbProcessInputAgent.typing("mpr4");
			selectComboitemByLabel(processRunForm.getCmbProcess(), "mpr4");
			cmbProcessAgent.click();
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc4");
			selectComboitemByLabel(processRunForm.getCmbCostCenter(), "mcc4");
			cmbCostCenterAgent.click();		
						
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
	@Order(18)
	final void testCase18() {

		SpringUtility.setLoggedUserForTesting("user2@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/process_runs.zul");
		ProcessRunForm processRunForm = new ProcessRunForm(desktop,
															(desktop.query("textbox").query("#cmbProcess")).as(Combobox.class),
															(desktop.query("textbox").query("#cmbCostCenter")).as(Combobox.class),
															(desktop.query("intbox").query("#intRuns")).as(Intbox.class),																					
															desktop.query("south").query("button").query("#btnRecord").as(Button.class),
															desktop.query("grid").query("#grd").as(Grid.class));		
		try {
			processRunForm.setComponentValue(processRunForm.getIntRuns(), 1);	
			
			ComponentAgent cmbProcessAgent = desktop.query("textbox").query("#cmbProcess");
			InputAgent cmbProcessInputAgent = cmbProcessAgent.as(InputAgent.class);
			cmbProcessInputAgent.typing("mpr5");
			selectComboitemByLabel(processRunForm.getCmbProcess(), "mpr5");
			cmbProcessAgent.click();
			
			ComponentAgent cmbCostCenterAgent = desktop.query("textbox").query("#cmbCostCenter");
			InputAgent cmbCostCenterInputAgent = cmbCostCenterAgent.as(InputAgent.class);
			cmbCostCenterInputAgent.typing("mcc4");
			selectComboitemByLabel(processRunForm.getCmbCostCenter(), "mcc4");
			cmbCostCenterAgent.click();		
						
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