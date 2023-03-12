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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Checkbox;

import co.syscoop.soberano.test.helper.CounterActionTest;
import co.syscoop.soberano.test.helper.TestUtilityCode;
import co.syscoop.soberano.util.SpringUtility;

@Order(4)

//TODO: enable test
@Disabled

class O4_CounterTest_check_recording extends CounterActionTest {
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		Zats.init("./src/main/webapp");		
		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/counters.zul");
		
		cmbIntelliSearchAgent = desktop.query("combobox");
		cmbIntelliSearch = cmbIntelliSearchAgent.as(Combobox.class);		
		txtCode = cmbIntelliSearchAgent.query("#incDetails").query("#txtCode").as(Textbox.class);
		intNumberOfReceivers = cmbIntelliSearchAgent.query("#incDetails").query("#intNumberOfReceivers").as(Intbox.class);
		chkIsSurcharged = cmbIntelliSearchAgent.query("#incDetails").query("#chkIsSurcharged").as(Checkbox.class);
		chkDisabled = cmbIntelliSearchAgent.query("#incDetails").query("#chkDisabled").as(Checkbox.class);
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
	final void testCase12() {
		
		try {
			checkCounter("c1",
						4,
						false,
						false);
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
		
		try {
			checkCounter("c2",
						5,
						false,
						true);
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
		
		try {
			checkCounter("c3",
						6,
						true,
						false);
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
		
		try {
			checkCounter("c4",
						7,
						true,
						true);
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
		
		try {
			checkCounter("c5",
						8,
						false,
						false);
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
		
		try {
			checkCounter("c6",
						9,
						false,
						false);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
}
