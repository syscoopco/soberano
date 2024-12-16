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
import org.zkoss.lang.Library;
import org.zkoss.web.Attributes;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zats.mimic.operation.CheckAgent;

import co.syscoop.soberano.test.helper.CounterActionTest;
import co.syscoop.soberano.test.helper.CounterForm;

@Order(5)

//@Disabled

class O5_CounterTest_modify extends CounterActionTest {
	
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
	
		try {
			CounterForm counterForm = setFormComponents("user1@soberano.syscoop.co", "counters.zul");			
			loadObjectDetails("c1");			
			counterForm.setComponentValue(counterForm.getTxtCode(), "mc1");
			counterForm.setComponentValue(counterForm.getIntNumberOfReceivers(), 10);
			(counterForm.getDesktop().query("checkbox").query("#chkIsSurcharged")).as(CheckAgent.class).check(true);
			(counterForm.getDesktop().query("checkbox").query("#chkDisabled")).as(CheckAgent.class).check(true);
			clickOnApplyButton(counterForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase2() {
	
		try {
			CounterForm counterForm = setFormComponents("user2@soberano.syscoop.co", "counters.zul");			
			loadObjectDetails("c2");			
			counterForm.setComponentValue(counterForm.getTxtCode(), "mc2");
			counterForm.setComponentValue(counterForm.getIntNumberOfReceivers(), 4);
			(counterForm.getDesktop().query("checkbox").query("#chkIsSurcharged")).as(CheckAgent.class).check(false);
			(counterForm.getDesktop().query("checkbox").query("#chkDisabled")).as(CheckAgent.class).check(false);
			clickOnApplyButton(counterForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}	
	}
	
	@Test
	final void testCase3() {
	
		try {
			CounterForm counterForm = setFormComponents("user3@soberano.syscoop.co", "counters.zul");			
			loadObjectDetails("c3");			
			counterForm.setComponentValue(counterForm.getTxtCode(), "mc3");
			counterForm.setComponentValue(counterForm.getIntNumberOfReceivers(), 5);
			(counterForm.getDesktop().query("checkbox").query("#chkIsSurcharged")).as(CheckAgent.class).check(false);
			(counterForm.getDesktop().query("checkbox").query("#chkDisabled")).as(CheckAgent.class).check(true);
			clickOnApplyButton(counterForm.getDesktop());
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
	
		try {
			CounterForm counterForm = setFormComponents("user4@soberano.syscoop.co", "counters.zul");			
			loadObjectDetails("c4");			
			counterForm.setComponentValue(counterForm.getTxtCode(), "mc4");
			counterForm.setComponentValue(counterForm.getIntNumberOfReceivers(), 6);
			(counterForm.getDesktop().query("checkbox").query("#chkIsSurcharged")).as(CheckAgent.class).check(true);
			(counterForm.getDesktop().query("checkbox").query("#chkDisabled")).as(CheckAgent.class).check(false);
			clickOnApplyButton(counterForm.getDesktop());
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
	
		try {
			CounterForm counterForm = setFormComponents("user5@soberano.syscoop.co", "counters.zul");			
			loadObjectDetails("c5");			
			counterForm.setComponentValue(counterForm.getTxtCode(), "mc5");
			counterForm.setComponentValue(counterForm.getIntNumberOfReceivers(), 7);
			(counterForm.getDesktop().query("checkbox").query("#chkIsSurcharged")).as(CheckAgent.class).check(true);
			(counterForm.getDesktop().query("checkbox").query("#chkDisabled")).as(CheckAgent.class).check(true);
			clickOnApplyButton(counterForm.getDesktop());
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
	
		try {
			CounterForm counterForm = setFormComponents("user6@soberano.syscoop.co", "counters.zul");			
			loadObjectDetails("c6");			
			counterForm.setComponentValue(counterForm.getTxtCode(), "mc6");
			counterForm.setComponentValue(counterForm.getIntNumberOfReceivers(), 8);
			(counterForm.getDesktop().query("checkbox").query("#chkIsSurcharged")).as(CheckAgent.class).check(false);
			(counterForm.getDesktop().query("checkbox").query("#chkDisabled")).as(CheckAgent.class).check(false);
			clickOnApplyButton(counterForm.getDesktop());
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
	
		try {
			CounterForm counterForm = setFormComponents("user13@soberano.syscoop.co", "counters.zul");			
			loadObjectDetails("mc1");
			clickOnApplyButton(counterForm.getDesktop());
			
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
	final void testCase8() {
		
		try {
			CounterForm counterForm = setFormComponents("user14@soberano.syscoop.co", "counters.zul");			
			loadObjectDetails("mc1");
			clickOnApplyButton(counterForm.getDesktop());
			
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
	
		try {
			CounterForm counterForm = setFormComponents("user1@soberano.syscoop.co", "counters.zul");			
			loadObjectDetails("mc1");			
			counterForm.setComponentValue(counterForm.getTxtCode(), "mc2");
			clickOnApplyButton(counterForm.getDesktop());
			
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
