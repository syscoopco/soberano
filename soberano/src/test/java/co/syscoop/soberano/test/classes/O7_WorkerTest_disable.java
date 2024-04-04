package co.syscoop.soberano.test.classes;

import static org.junit.jupiter.api.Assertions.fail;

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
import org.zkoss.zats.mimic.Zats;
import co.syscoop.soberano.test.helper.TestUtilityCode;
import co.syscoop.soberano.test.helper.WorkerActionTest;

@Order(7)

//TODO: enable testCase
////@Disabled

@TestMethodOrder(OrderAnnotation.class)
class O7_WorkerTest_disable extends WorkerActionTest {
	
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
	@Order(1)
	final void testCase1() {
		TestUtilityCode.testDisablingObject("/workers.zul", "user1@soberano.syscoop.co", "accounter@soberano.syscoop.co", 34);
	}
	
	@Test
	@Order(2)
	final void testCase2() {
		TestUtilityCode.testDisablingObject("/workers.zul", "user2@soberano.syscoop.co", "auditor@soberano.syscoop.co", 33);
	}
	
	@Test
	@Order(3)
	final void testCase3() {
		TestUtilityCode.testDisablingObject("/workers.zul", "user3@soberano.syscoop.co", "catalogMaintainer@soberano.syscoop.co", 32);
	}
	
	@Test
	@Order(4)
	final void testCase4() {
		TestUtilityCode.testDisablingObject("/workers.zul", "user4@soberano.syscoop.co", "checker@soberano.syscoop.co", 31);
	}
	
	@Test
	@Order(5)
	final void testCase5() {
		TestUtilityCode.testDisablingObject("/workers.zul", "user5@soberano.syscoop.co", "communityManager@soberano.syscoop.co", 30);
	}
	
	@Test
	@Order(6)
	final void testCase6() {
		try {
			TestUtilityCode.testDisablingObject("/workers.zul", "user6@soberano.syscoop.co", "manager@soberano.syscoop.co", 29);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			//an auditor can list the objects but not modifying nor disabling them
			testNotEnoughRightsException(ex);
		}
	}
	
	@Test
	@Order(7)
	final void testCase7() {
		TestUtilityCode.testDisablingObject("/workers.zul", "user8@soberano.syscoop.co", "manager@soberano.syscoop.co", 29);
	}
	
	@Test
	@Order(8)
	final void testCase8() {
		TestUtilityCode.testDisablingObject("/workers.zul", "user9@soberano.syscoop.co", "procurementWorker@soberano.syscoop.co", 28);
	}
	
	@Test
	@Order(9)
	final void testCase9() {
		TestUtilityCode.testDisablingObject("/workers.zul", "user11@soberano.syscoop.co", "salesClerk@soberano.syscoop.co", 27);
	}
	
	@Test
	@Order(10)
	final void testCase10() {
		try {
			TestUtilityCode.testDisablingObject("/workers.zul", "user12@soberano.syscoop.co", "shiftManager@soberano.syscoop.co", 26);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			//an auditor can list the objects but not modifying nor disabling them
			testNotEnoughRightsException(ex);
		}
	}
	
	@Test
	@Order(11)
	final void testCase11() {
		try {
			TestUtilityCode.testDisablingObject("/workers.zul", "user13@soberano.syscoop.co", "shiftManager@soberano.syscoop.co", 26);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			//an auditor can list the objects but not modifying nor disabling them
			testNotEnoughRightsException(ex);
		}
	}
	
	@Test
	@Order(12)
	final void testCase12() {
		try {
			TestUtilityCode.testDisablingObject("/workers.zul", "user14@soberano.syscoop.co", "shiftManager@soberano.syscoop.co", 26);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			//an auditor can list the objects but not modifying nor disabling them
			testNotEnoughRightsException(ex);
		}
	}
	
	@Test
	@Order(13)
	final void testCase13() {
		TestUtilityCode.testDisablingObject("/workers.zul", "user15@soberano.syscoop.co", "shiftManager@soberano.syscoop.co", 26);
	}
	
	@Test
	@Order(14)
	final void testCase14() {
		TestUtilityCode.testDisablingObject("/workers.zul", "systemAdmin@soberano.syscoop.co", "storekeeper@soberano.syscoop.co", 25);
	}
	
	@Test
	@Order(15)
	final void testCase15() {
		TestUtilityCode.testDisablingObject("/workers.zul", "systemAdmin@soberano.syscoop.co", "workshop1Worker@soberano.syscoop.co", 24);
	}
	
	@Test
	@Order(16)
	final void testCase16() {
		TestUtilityCode.testDisablingObject("/workers.zul", "systemAdmin@soberano.syscoop.co", "workshop2Worker@soberano.syscoop.co", 23);
	}
	
	@Test
	@Order(17)
	final void testCase17() {
		TestUtilityCode.testDisablingObject("/workers.zul", "user16@soberano.syscoop.co", "systemAdmin@soberano.syscoop.co", 22);
	}
}
