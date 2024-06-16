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
import co.syscoop.soberano.test.helper.CustomerActionTest;

@Order(7)

@Disabled

@TestMethodOrder(OrderAnnotation.class)
class O7_CustomerTest_disable extends CustomerActionTest {
	
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
		TestUtilityCode.testDisablingObject("/customers.zul", "user1@soberano.syscoop.co", "c7mod@soberano.syscoop.co", 8);
	}
	
		
	@Test
	@Order(2)
	final void testCase2() {
		try {
			TestUtilityCode.testDisablingObject("/customers.zul", "user2@soberano.syscoop.co", "c7mod@soberano.syscoop.co", 8);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testNotEnoughRightsException(ex);
		}
	}
}
