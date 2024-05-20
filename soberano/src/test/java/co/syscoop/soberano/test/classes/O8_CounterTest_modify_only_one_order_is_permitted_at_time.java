package co.syscoop.soberano.test.classes;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.zkoss.zats.mimic.Zats;

import co.syscoop.soberano.test.helper.CounterActionTest;

@Order(8)

//TODO: enable testCase
//@Disabled

class O8_CounterTest_modify_only_one_order_is_permitted_at_time extends CounterActionTest {
	
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
		//TODO:
	}
}
