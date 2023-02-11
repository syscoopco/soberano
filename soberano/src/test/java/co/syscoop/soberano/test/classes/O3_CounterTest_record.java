package co.syscoop.soberano.test.classes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zats.mimic.operation.CheckAgent;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Intbox;

import co.syscoop.soberano.test.helper.CounterActionTest;
import co.syscoop.soberano.test.helper.CounterForm;
import co.syscoop.soberano.util.SpringUtility;

@Order(3)

//TODO: enable test
@Disabled

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
			counterForm.setComponentValue(counterForm.getTxtCode(), "c1");
			counterForm.setComponentValue(counterForm.getIntNumberOfReceivers(), 4);	
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
		DesktopAgent desktop = Zats.newClient().connect("/new_counter.zul");
		
		counterForm = new CounterForm(desktop,
									(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
									(desktop.query("intbox").query("#intNumberOfReceivers")).as(Intbox.class), 
									(desktop.query("checkbox").query("#chkIsSurcharged")).as(Checkbox.class), 
									(desktop.query("checkbox").query("#chkDisabled")).as(Checkbox.class));
		try {
			counterForm.setComponentValue(counterForm.getTxtCode(), "c2");
			counterForm.setComponentValue(counterForm.getIntNumberOfReceivers(), 5);
			(desktop.query("checkbox").query("#chkDisabled")).as(CheckAgent.class).check(true);				
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
	final void testCase5() {

		SpringUtility.setLoggedUserForTesting("user3@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_counter.zul");
		
		counterForm = new CounterForm(desktop,
									(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
									(desktop.query("intbox").query("#intNumberOfReceivers")).as(Intbox.class), 
									(desktop.query("checkbox").query("#chkIsSurcharged")).as(Checkbox.class), 
									(desktop.query("checkbox").query("#chkDisabled")).as(Checkbox.class));
		try {
			counterForm.setComponentValue(counterForm.getTxtCode(), "c3");
			counterForm.setComponentValue(counterForm.getIntNumberOfReceivers(), 6);
			(desktop.query("checkbox").query("#chkIsSurcharged")).as(CheckAgent.class).check(true);
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
		DesktopAgent desktop = Zats.newClient().connect("/new_counter.zul");
		
		counterForm = new CounterForm(desktop,
									(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
									(desktop.query("intbox").query("#intNumberOfReceivers")).as(Intbox.class), 
									(desktop.query("checkbox").query("#chkIsSurcharged")).as(Checkbox.class), 
									(desktop.query("checkbox").query("#chkDisabled")).as(Checkbox.class));
		try {
			counterForm.setComponentValue(counterForm.getTxtCode(), "c4");
			counterForm.setComponentValue(counterForm.getIntNumberOfReceivers(), 7);			
			(desktop.query("checkbox").query("#chkIsSurcharged")).as(CheckAgent.class).check(true);
			(desktop.query("checkbox").query("#chkDisabled")).as(CheckAgent.class).check(true);
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
	final void testCase7() {

		SpringUtility.setLoggedUserForTesting("user5@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_counter.zul");
		
		counterForm = new CounterForm(desktop,
									(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
									(desktop.query("intbox").query("#intNumberOfReceivers")).as(Intbox.class), 
									(desktop.query("checkbox").query("#chkIsSurcharged")).as(Checkbox.class), 
									(desktop.query("checkbox").query("#chkDisabled")).as(Checkbox.class));
		try {
			counterForm.setComponentValue(counterForm.getTxtCode(), "c5");
			counterForm.setComponentValue(counterForm.getIntNumberOfReceivers(), 8);		
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
		DesktopAgent desktop = Zats.newClient().connect("/new_counter.zul");
		
		counterForm = new CounterForm(desktop,
									(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
									(desktop.query("intbox").query("#intNumberOfReceivers")).as(Intbox.class), 
									(desktop.query("checkbox").query("#chkIsSurcharged")).as(Checkbox.class), 
									(desktop.query("checkbox").query("#chkDisabled")).as(Checkbox.class));
		try {
			counterForm.setComponentValue(counterForm.getTxtCode(), "c6");
			counterForm.setComponentValue(counterForm.getIntNumberOfReceivers(), 9);	
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
	final void testCase9() {

		SpringUtility.setLoggedUserForTesting("user13@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_counter.zul");
		
		counterForm = new CounterForm(desktop,
									(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
									(desktop.query("intbox").query("#intNumberOfReceivers")).as(Intbox.class), 
									(desktop.query("checkbox").query("#chkIsSurcharged")).as(Checkbox.class), 
									(desktop.query("checkbox").query("#chkDisabled")).as(Checkbox.class));
		try {
			counterForm.setComponentValue(counterForm.getTxtCode(), "c7");
			counterForm.setComponentValue(counterForm.getIntNumberOfReceivers(), 10);	
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
	final void testCase10() {

		SpringUtility.setLoggedUserForTesting("user14@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_counter.zul");
		
		counterForm = new CounterForm(desktop,
									(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
									(desktop.query("intbox").query("#intNumberOfReceivers")).as(Intbox.class), 
									(desktop.query("checkbox").query("#chkIsSurcharged")).as(Checkbox.class), 
									(desktop.query("checkbox").query("#chkDisabled")).as(Checkbox.class));
		try {
			counterForm.setComponentValue(counterForm.getTxtCode(), "c8");
			counterForm.setComponentValue(counterForm.getIntNumberOfReceivers(), 11);(desktop.query("checkbox").query("#chkIsSurcharged")).click();
			(desktop.query("checkbox").query("#chkIsSurcharged")).as(CheckAgent.class).check(true);
			(desktop.query("checkbox").query("#chkDisabled")).as(CheckAgent.class).check(true);
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

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_counter.zul");
		
		counterForm = new CounterForm(desktop,
									(desktop.query("textbox").query("#txtCode")).as(Textbox.class), 
									(desktop.query("intbox").query("#intNumberOfReceivers")).as(Intbox.class), 
									(desktop.query("checkbox").query("#chkIsSurcharged")).as(Checkbox.class), 
									(desktop.query("checkbox").query("#chkDisabled")).as(Checkbox.class));
		try {
			counterForm.setComponentValue(counterForm.getTxtCode(), "c1");
			counterForm.setComponentValue(counterForm.getIntNumberOfReceivers(), 5);
			(desktop.query("checkbox").query("#chkDisabled")).as(CheckAgent.class).check(true);	
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
