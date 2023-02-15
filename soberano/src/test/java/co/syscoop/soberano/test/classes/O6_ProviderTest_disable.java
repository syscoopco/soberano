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

import co.syscoop.soberano.test.helper.ProviderActionTest;
import co.syscoop.soberano.test.helper.TestUtilityCode;

@Order(6)

//TODO: enable test
//@Disabled

class O6_ProviderTest_disable extends ProviderActionTest{

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
	final void test1() {
		TestUtilityCode.testDisablingObject("/providers.zul", "user1@soberano.syscoop.co", "mprov8", 7);
	}
	
	@Test
	final void test2() {
		try {
			TestUtilityCode.testDisablingObject("/providers.zul", "user2@soberano.syscoop.co", "mcat7", 7);
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
