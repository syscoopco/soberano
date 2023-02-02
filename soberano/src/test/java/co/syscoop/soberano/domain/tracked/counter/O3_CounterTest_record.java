package co.syscoop.soberano.domain.tracked.counter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Intbox;

import co.syscoop.soberano.test.helper.CounterActionTest;
import co.syscoop.soberano.test.helper.CounterForm;
import co.syscoop.soberano.util.SpringUtility;

@Order(3)

//TODO: enable test
//@Disabled

class O3_CounterTest_record extends CounterActionTest {
	
	protected CounterForm counterForm = null;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
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
		DesktopAgent desktop = Zats.newClient().connect("/new_counter.zul");
		counterForm = new CounterForm(desktop,
									(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
									(desktop.query("intbox").query("#intNumberOfReceivers")).as(Intbox.class), 
									(desktop.query("checkbox").query("#chkIsSurcharged")).as(Checkbox.class), 
									(desktop.query("checkbox").query("#chkDisabled")).as(Checkbox.class));
		try {
			//this is needed to execute only in the first test. it has to do with testing configuration.
			counterForm.testEachConstrainedObjectIsDeclared();
			counterForm.testEachDeclaredConstrainedObjectIsActuallyConstrained();
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
		DesktopAgent desktop = Zats.newClient().connect("/new_counter.zul");
		
		counterForm = new CounterForm(desktop,
									(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
									(desktop.query("intbox").query("#intNumberOfReceivers")).as(Intbox.class), 
									(desktop.query("checkbox").query("#chkIsSurcharged")).as(Checkbox.class), 
									(desktop.query("checkbox").query("#chkDisabled")).as(Checkbox.class));
		try {
			counterForm.setComponentValue(counterForm.getTxtCode(), "");
			counterForm.setComponentValue(counterForm.getIntNumberOfReceivers(), 4);
			counterForm.setComponentValue(counterForm.getChkIsSurcharged(), false);
			counterForm.setComponentValue(counterForm.getChkDisabled(), false);				
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			counterForm.testWrongValueException(ex);
		}
	}
	
	@Test
	final void testCase2() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_counter.zul");
		
		counterForm = new CounterForm(desktop,
									(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
									(desktop.query("intbox").query("#intNumberOfReceivers")).as(Intbox.class), 
									(desktop.query("checkbox").query("#chkIsSurcharged")).as(Checkbox.class), 
									(desktop.query("checkbox").query("#chkDisabled")).as(Checkbox.class));
		try {
			counterForm.setComponentValue(counterForm.getTxtCode(), "1");
			counterForm.setComponentValue(counterForm.getIntNumberOfReceivers(), "");
			counterForm.setComponentValue(counterForm.getChkIsSurcharged(), false);
			counterForm.setComponentValue(counterForm.getChkDisabled(), false);				
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			counterForm.testWrongValueException(ex);
		}
	}
	
	@Test
	final void testCase3() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_counter.zul");
		
		counterForm = new CounterForm(desktop,
									(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
									(desktop.query("intbox").query("#intNumberOfReceivers")).as(Intbox.class), 
									(desktop.query("checkbox").query("#chkIsSurcharged")).as(Checkbox.class), 
									(desktop.query("checkbox").query("#chkDisabled")).as(Checkbox.class));
		try {
			counterForm.setComponentValue(counterForm.getTxtCode(), "1");
			counterForm.setComponentValue(counterForm.getIntNumberOfReceivers(), 4);
			counterForm.setComponentValue(counterForm.getChkIsSurcharged(), false);
			counterForm.setComponentValue(counterForm.getChkDisabled(), false);				
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

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_counter.zul");
		
		counterForm = new CounterForm(desktop,
									(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
									(desktop.query("intbox").query("#intNumberOfReceivers")).as(Intbox.class), 
									(desktop.query("checkbox").query("#chkIsSurcharged")).as(Checkbox.class), 
									(desktop.query("checkbox").query("#chkDisabled")).as(Checkbox.class));
		try {
			counterForm.setComponentValue(counterForm.getTxtCode(), "1");
			counterForm.setComponentValue(counterForm.getIntNumberOfReceivers(), 5);
			counterForm.setComponentValue(counterForm.getChkIsSurcharged(), false);
			counterForm.setComponentValue(counterForm.getChkDisabled(), true);				
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
	
	@Test
	final void testCase10() {

		SpringUtility.setLoggedUserForTesting("user13@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_counter.zul");
		
		counterForm = new CounterForm(desktop,
									(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
									(desktop.query("intbox").query("#intNumberOfReceivers")).as(Intbox.class), 
									(desktop.query("checkbox").query("#chkIsSurcharged")).as(Checkbox.class), 
									(desktop.query("checkbox").query("#chkDisabled")).as(Checkbox.class));
		try {
			counterForm.setComponentValue(counterForm.getTxtCode(), "7");
			counterForm.setComponentValue(counterForm.getIntNumberOfReceivers(), 10);
			counterForm.setComponentValue(counterForm.getChkIsSurcharged(), false);
			counterForm.setComponentValue(counterForm.getChkDisabled(), false);				
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
}
