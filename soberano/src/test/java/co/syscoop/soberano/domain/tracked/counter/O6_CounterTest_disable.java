package co.syscoop.soberano.domain.tracked.counter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.zkoss.zats.mimic.Zats;

import co.syscoop.soberano.test.helper.CounterActionTest;
import co.syscoop.soberano.test.helper.TestUtilityCode;

@Order(6)

//TODO: enable test
//@Disabled

class O6_CounterTest_disable extends CounterActionTest {
	
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
	final void test1() {
		TestUtilityCode.testDisablingObject("/counters.zul", "user6@soberano.syscoop.co", "mc5", 5);
	}
	
	@Test
	@Order(2)
	final void test2() {
		TestUtilityCode.testDisablingObject("/counters.zul", "user8@soberano.syscoop.co", "mc6", 4);
	}
	
	@Test
	@Order(3)
	final void test3() {
		try {
			TestUtilityCode.testDisablingObject("/counters.zul", "user13@soberano.syscoop.co", "mc1", 4);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			
			//an auditor can list the objects but not modifying nor disabling them
			testNotEnoughRightsException(ex);
		}
	}
}