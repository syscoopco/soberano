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
import org.zkoss.zats.mimic.Zats;

import co.syscoop.soberano.test.helper.ServiceActionTest;
import co.syscoop.soberano.test.helper.TestUtilityCode;

@Order(7)

@Disabled

class O7_ServiceTest_disable  extends ServiceActionTest {

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
	final void testCase1() {
		TestUtilityCode.testDisablingObject("/thirdparty_services.zul", "user1@soberano.syscoop.co", "ms9", 8);
	}
	
	@Test
	final void testCase2() {
		TestUtilityCode.testDisablingObject("/thirdparty_services.zul", "user2@soberano.syscoop.co", "ms8", 7);
	}
	
	@Test
	final void testCase3() {
		TestUtilityCode.testDisablingObject("/thirdparty_services.zul", "user3@soberano.syscoop.co", "ms7", 6);
	}
	
	@Test
	final void testCase4() {
		try {
			TestUtilityCode.testDisablingObject("/thirdparty_services.zul", "user4@soberano.syscoop.co", "ms6", 6);
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
	final void testCase5() {
		try {
			TestUtilityCode.testDisablingObject("/thirdparty_services.zul", "user5@soberano.syscoop.co", "ms6", 6);
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
	final void testCase6() {
		TestUtilityCode.testDisablingObject("/thirdparty_services.zul", "user6@soberano.syscoop.co", "ms6", 5);
	}
}
