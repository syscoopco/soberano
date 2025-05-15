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
import org.opentest4j.AssertionFailedError;
import org.zkoss.lang.Library;
import org.zkoss.web.Attributes;
import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Decimalbox;

import co.syscoop.soberano.test.helper.ProcessActionTest;
import co.syscoop.soberano.test.helper.ProcessForm;
import co.syscoop.soberano.util.SpringUtility;

@Order(3)

@Disabled

class O3_ProcessTest_record extends ProcessActionTest {
	
	protected ProcessForm processForm = null;

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
	final void testCase0() {
		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_process.zul");
		processForm = new ProcessForm(desktop,
									(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
									(desktop.query("decimalbox").query("#decFixedCost")).as(Decimalbox.class));
		try {
			//this is needed to execute only in the first test. it has to do with testing configuration.
			processForm.testEachConstrainedObjectIsDeclared();
			processForm.testEachDeclaredConstrainedObjectIsActuallyConstrained();
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase1() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_process.zul");
		
		processForm = new ProcessForm(desktop,
									(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
									(desktop.query("decimalbox").query("#decFixedCost")).as(Decimalbox.class));
		try {
			processForm.setComponentValue(processForm.getTxtName(), "");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(5));		
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			processForm.testWrongValueException(ex);
		}
	}
	
	@Test
	final void testCase2() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_process.zul");
		
		processForm = new ProcessForm(desktop,
									(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
									(desktop.query("decimalbox").query("#decFixedCost")).as(Decimalbox.class));
		try {
			processForm.setComponentValue(processForm.getTxtName(), "pr1");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(-5.0));		
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			processForm.testWrongValueException(ex);
		}
	}
	
	@Test
	final void testCase3() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_process.zul");
		
		processForm = new ProcessForm(desktop,
									(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
									(desktop.query("decimalbox").query("#decFixedCost")).as(Decimalbox.class));
		try {
			processForm.setComponentValue(processForm.getTxtName(), "pr1");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(1));		
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase4() {

		SpringUtility.setLoggedUserForTesting("user2@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_process.zul");
		
		processForm = new ProcessForm(desktop,
									(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
									(desktop.query("decimalbox").query("#decFixedCost")).as(Decimalbox.class));
		try {
			processForm.setComponentValue(processForm.getTxtName(), "pr2");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(2));		
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
	final void testCase5() {

		SpringUtility.setLoggedUserForTesting("user3@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_process.zul");
		
		processForm = new ProcessForm(desktop,
									(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
									(desktop.query("decimalbox").query("#decFixedCost")).as(Decimalbox.class));
		try {
			processForm.setComponentValue(processForm.getTxtName(), "pr2");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(2));		
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase6() {

		SpringUtility.setLoggedUserForTesting("user4@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_process.zul");
		
		processForm = new ProcessForm(desktop,
									(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
									(desktop.query("decimalbox").query("#decFixedCost")).as(Decimalbox.class));
		try {
			processForm.setComponentValue(processForm.getTxtName(), "pr3");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(3));		
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
	final void testCase7() {

		SpringUtility.setLoggedUserForTesting("user5@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_process.zul");
		
		processForm = new ProcessForm(desktop,
									(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
									(desktop.query("decimalbox").query("#decFixedCost")).as(Decimalbox.class));
		try {
			processForm.setComponentValue(processForm.getTxtName(), "pr3");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(3));		
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase8() {

		SpringUtility.setLoggedUserForTesting("user6@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_process.zul");
		
		processForm = new ProcessForm(desktop,
									(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
									(desktop.query("decimalbox").query("#decFixedCost")).as(Decimalbox.class));
		try {
			processForm.setComponentValue(processForm.getTxtName(), "pr4");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(4));		
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
	final void testCase9() {

		SpringUtility.setLoggedUserForTesting("user7@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_process.zul");
		
		processForm = new ProcessForm(desktop,
									(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
									(desktop.query("decimalbox").query("#decFixedCost")).as(Decimalbox.class));
		try {
			processForm.setComponentValue(processForm.getTxtName(), "pr4");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(4));		
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase10() {

		SpringUtility.setLoggedUserForTesting("user8@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_process.zul");
		
		processForm = new ProcessForm(desktop,
									(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
									(desktop.query("decimalbox").query("#decFixedCost")).as(Decimalbox.class));
		try {
			processForm.setComponentValue(processForm.getTxtName(), "pr6");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(6));		
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
	final void testCase11() {

		SpringUtility.setLoggedUserForTesting("user9@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_process.zul");
		
		processForm = new ProcessForm(desktop,
									(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
									(desktop.query("decimalbox").query("#decFixedCost")).as(Decimalbox.class));
		try {
			processForm.setComponentValue(processForm.getTxtName(), "pr5");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(5));		
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase12() {

		SpringUtility.setLoggedUserForTesting("user10@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_process.zul");
		
		processForm = new ProcessForm(desktop,
									(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
									(desktop.query("decimalbox").query("#decFixedCost")).as(Decimalbox.class));
		try {
			processForm.setComponentValue(processForm.getTxtName(), "pr8");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(8));		
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
	final void testCase13() {

		SpringUtility.setLoggedUserForTesting("user11@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_process.zul");
		
		processForm = new ProcessForm(desktop,
									(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
									(desktop.query("decimalbox").query("#decFixedCost")).as(Decimalbox.class));
		try {
			processForm.setComponentValue(processForm.getTxtName(), "pr6");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(6));		
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase14() {

		SpringUtility.setLoggedUserForTesting("user12@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_process.zul");
		
		processForm = new ProcessForm(desktop,
									(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
									(desktop.query("decimalbox").query("#decFixedCost")).as(Decimalbox.class));
		try {
			processForm.setComponentValue(processForm.getTxtName(), "pr7");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(7));		
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
	final void testCase15() {

		SpringUtility.setLoggedUserForTesting("user13@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_process.zul");
		
		processForm = new ProcessForm(desktop,
									(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
									(desktop.query("decimalbox").query("#decFixedCost")).as(Decimalbox.class));
		try {
			processForm.setComponentValue(processForm.getTxtName(), "pr7");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(7));		
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase16() {

		SpringUtility.setLoggedUserForTesting("user14@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_process.zul");
		
		processForm = new ProcessForm(desktop,
									(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
									(desktop.query("decimalbox").query("#decFixedCost")).as(Decimalbox.class));
		try {
			processForm.setComponentValue(processForm.getTxtName(), "pr8");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(8));		
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
	final void testCase17() {

		SpringUtility.setLoggedUserForTesting("user15@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_process.zul");
		
		processForm = new ProcessForm(desktop,
									(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
									(desktop.query("decimalbox").query("#decFixedCost")).as(Decimalbox.class));
		try {
			processForm.setComponentValue(processForm.getTxtName(), "pr8");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(8));		
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase18() {

		SpringUtility.setLoggedUserForTesting("user16@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_process.zul");
		
		processForm = new ProcessForm(desktop,
									(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
									(desktop.query("decimalbox").query("#decFixedCost")).as(Decimalbox.class));
		try {
			processForm.setComponentValue(processForm.getTxtName(), "pr9");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(9));		
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
	final void testCase19() {

		SpringUtility.setLoggedUserForTesting("user17@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_process.zul");
		
		processForm = new ProcessForm(desktop,
									(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
									(desktop.query("decimalbox").query("#decFixedCost")).as(Decimalbox.class));
		try {
			processForm.setComponentValue(processForm.getTxtName(), "pr9");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(9));		
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase20() {

		SpringUtility.setLoggedUserForTesting("user18@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_process.zul");
		
		processForm = new ProcessForm(desktop,
									(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
									(desktop.query("decimalbox").query("#decFixedCost")).as(Decimalbox.class));
		try {
			processForm.setComponentValue(processForm.getTxtName(), "pr10");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(91));		
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
	final void testCase21() {

		SpringUtility.setLoggedUserForTesting("user19@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_process.zul");
		
		processForm = new ProcessForm(desktop,
									(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
									(desktop.query("decimalbox").query("#decFixedCost")).as(Decimalbox.class));
		try {
			processForm.setComponentValue(processForm.getTxtName(), "pr10");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(91));		
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase22() {

		SpringUtility.setLoggedUserForTesting("user20@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_process.zul");
		
		processForm = new ProcessForm(desktop,
									(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
									(desktop.query("decimalbox").query("#decFixedCost")).as(Decimalbox.class));
		try {
			processForm.setComponentValue(processForm.getTxtName(), "pr92");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(92));		
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
	final void testCase23() {

		SpringUtility.setLoggedUserForTesting("user21@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_process.zul");
		
		processForm = new ProcessForm(desktop,
									(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
									(desktop.query("decimalbox").query("#decFixedCost")).as(Decimalbox.class));
		try {
			processForm.setComponentValue(processForm.getTxtName(), "pr92");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(92));		
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
	final void testCase24() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_process.zul");
		
		processForm = new ProcessForm(desktop,
									(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
									(desktop.query("decimalbox").query("#decFixedCost")).as(Decimalbox.class));
		try {
			processForm.setComponentValue(processForm.getTxtName(), "pr10");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(91));		
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testDuplicateKeyException(ex);
		}
	}
}
