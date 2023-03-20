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

import co.syscoop.soberano.test.helper.WarehouseActionTest;
import co.syscoop.soberano.test.helper.TestUtilityCode;

@Order(10)

//TODO: enable testCase
//@Disabled

class OO10_WarehouseTest_disable extends WarehouseActionTest{

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
		TestUtilityCode.testDisablingObject("/warehouses.zul", "user1@soberano.syscoop.co", "mw5", 19);
	}
	
	@Test
	final void testCase2() {
		TestUtilityCode.testDisablingObject("/warehouses.zul", "user2@soberano.syscoop.co", "mw2", 18);
	}
	
	@Test
	final void testCase3() {
		TestUtilityCode.testDisablingObject("/warehouses.zul", "user3@soberano.syscoop.co", "mw3", 17);
	}
	
	@Test
	final void testCase4() {
		try {
			TestUtilityCode.testDisablingObject("/warehouses.zul", "user4@soberano.syscoop.co", "mw9", 17);
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
			TestUtilityCode.testDisablingObject("/warehouses.zul", "user5@soberano.syscoop.co", "mw10", 17);
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
		TestUtilityCode.testDisablingObject("/warehouses.zul", "user6@soberano.syscoop.co", "mw4", 16);
	}
}
