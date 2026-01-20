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
import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.test.helper.ServiceActionTest;
import co.syscoop.soberano.util.SpringUtility;

@Order(6)

//@Disabled

class O6_ServiceTest_check_modifying extends ServiceActionTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		Library.setProperty(Attributes.PREFERRED_LOCALE, "en"); //needed due to translated captions according 
		//to runtime locale not available under 
		//testing environment
		
		//Zats.init("./src/main/webapp");		
		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/thirdparty_services.zul");
		
		cmbIntelliSearchAgent = desktop.query("center").query("combobox");
		cmbIntelliSearch = cmbIntelliSearchAgent.as(Combobox.class);		
		txtName = cmbIntelliSearchAgent.query("#incDetails").query("#txtName").as(Textbox.class);
		txtCode = cmbIntelliSearchAgent.query("#incDetails").query("#txtCode").as(Textbox.class);
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
	final void testCase15() {
		
		try {
			checkService("mservice1",
						"ms1");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase16() {
		
		try {
			checkService("mservice2",
						"ms2");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase17() {
		
		try {
			checkService("mservice3",
						"ms3");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase18() {
		
		try {
			checkService("mservice4",
						"ms4");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase19() {
		
		try {
			checkService("mservice5",
						"ms5");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase20() {
		
		try {
			checkService("mservice6",
						"ms6");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase21() {
		
		try {
			checkService("mservice7",
						"ms7");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase22() {
		
		try {
			checkService("mservice8",
						"ms8");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase23() {
		
		try {
			checkService("mservice9",
						"ms9");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
}
