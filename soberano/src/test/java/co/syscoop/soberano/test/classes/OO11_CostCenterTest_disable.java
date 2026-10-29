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

import co.syscoop.soberano.test.helper.CostCenterActionTest;
import co.syscoop.soberano.test.helper.TestUtilityCode;

@Order(11)

@Disabled

class OO11_CostCenterTest_disable  extends CostCenterActionTest {

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
	final void testCase1() {
		TestUtilityCode.testDisablingObject("/cost_centers.zul", "user1@soberano.syscoop.co", "cc1", 9);
	}
	
	@Test
	final void testCase2() {
		TestUtilityCode.testDisablingObject("/cost_centers.zul", "user2@soberano.syscoop.co", "mcc2", 8);
	}
	
	@Test
	final void testCase3() {
		TestUtilityCode.testDisablingObject("/cost_centers.zul", "user3@soberano.syscoop.co", "mcc3", 7);
	}
	
	@Test
	final void testCase4() {
		try {
			TestUtilityCode.testDisablingObject("/cost_centers.zul", "user4@soberano.syscoop.co", "mcc4", 7);
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
			TestUtilityCode.testDisablingObject("/cost_centers.zul", "user5@soberano.syscoop.co", "mcc5", 7);
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
		try {
			TestUtilityCode.testDisablingObject("/cost_centers.zul", "user19@soberano.syscoop.co", "mcc6", 7);
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
