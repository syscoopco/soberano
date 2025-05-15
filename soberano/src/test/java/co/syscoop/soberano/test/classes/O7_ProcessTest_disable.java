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

import co.syscoop.soberano.test.helper.ProcessActionTest;
import co.syscoop.soberano.test.helper.TestUtilityCode;

@Order(7)

@Disabled

class O7_ProcessTest_disable  extends ProcessActionTest {

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
		TestUtilityCode.testDisablingObject("/processes.zul", "user1@soberano.syscoop.co", "mpr10", 9);
	}
	
	@Test
	final void testCase2() {
		try {
			TestUtilityCode.testDisablingObject("/processes.zul", "user2@soberano.syscoop.co", "mpr2", 9);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			
			//a user authorized to make any decision on an object can 
			//retrieve it but not necessarily modifying nor disabling it
			testNotEnoughRightsException(ex);
		}
	}
	
	@Test
	final void testCase3() {
		TestUtilityCode.testDisablingObject("/processes.zul", "user3@soberano.syscoop.co", "mpr3", 8);
	}
	
	@Test
	final void testCase4() {
		try {
			TestUtilityCode.testDisablingObject("/processes.zul", "user4@soberano.syscoop.co", "mpr4", 8);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			
			//a user authorized to make any decision on an object can 
			//retrieve it but not necessarily modifying nor disabling it
			testNotEnoughRightsException(ex);
		}
	}
}
