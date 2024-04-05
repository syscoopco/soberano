package co.syscoop.soberano.test.classes;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.zkoss.lang.Library;
import org.zkoss.web.Attributes;
import org.zkoss.zats.mimic.Zats;
import co.syscoop.soberano.test.helper.ActionTest;

@Order(29)

//TODO: enable test
//@Disabled

class OO29_ShiftClosureTest_create extends ActionTest {

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
		//TODO: 
	}
}
