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
import co.syscoop.soberano.test.helper.ServiceActionTest;
import co.syscoop.soberano.test.helper.ServiceForm;

@Order(5)

//TODO: enable test
////@Disabled

class O5_ServiceTest_modify extends ServiceActionTest{

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
	
		try {
			ServiceForm serviceForm = setFormComponents("user1@soberano.syscoop.co", "thirdparty_services.zul");			
			loadObjectDetails("service1 : s1");			
			serviceForm.setComponentValue(serviceForm.getTxtCode(), "ms1");
			serviceForm.setComponentValue(serviceForm.getTxtName(), "mservice1");
			
			clickOnApplyButton(serviceForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase2() {
	
		try {
			ServiceForm serviceForm = setFormComponents("user2@soberano.syscoop.co", "thirdparty_services.zul");			
			loadObjectDetails("service2 : s2");				
			serviceForm.setComponentValue(serviceForm.getTxtCode(), "ms2");
			serviceForm.setComponentValue(serviceForm.getTxtName(), "mservice2");
			
			clickOnApplyButton(serviceForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase3() {
	
		try {
			ServiceForm serviceForm = setFormComponents("user3@soberano.syscoop.co", "thirdparty_services.zul");			
			loadObjectDetails("service3 : s3");			
			serviceForm.setComponentValue(serviceForm.getTxtCode(), "ms3");
			serviceForm.setComponentValue(serviceForm.getTxtName(), "mservice3");
			
			clickOnApplyButton(serviceForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase4() {
	
		try {
			ServiceForm serviceForm = setFormComponents("user4@soberano.syscoop.co", "thirdparty_services.zul");			
			loadObjectDetails("service4 : s4");				
			serviceForm.setComponentValue(serviceForm.getTxtCode(), "ms4");
			serviceForm.setComponentValue(serviceForm.getTxtName(), "mservice4");
			
			clickOnApplyButton(serviceForm.getDesktop());
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testNotEnoughRightsException(ex);
		}
	}
	
	@Test
	final void testCase5() {
	
		try {
			ServiceForm serviceForm = setFormComponents("user5@soberano.syscoop.co", "thirdparty_services.zul");			
			loadObjectDetails("service4 : s4");				
			serviceForm.setComponentValue(serviceForm.getTxtCode(), "ms4");
			serviceForm.setComponentValue(serviceForm.getTxtName(), "mservice4");
			
			clickOnApplyButton(serviceForm.getDesktop());
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testNotEnoughRightsException(ex);
		}
	}
	
	@Test
	final void testCase6() {
	
		try {
			ServiceForm serviceForm = setFormComponents("user6@soberano.syscoop.co", "thirdparty_services.zul");			
			loadObjectDetails("service4 : s4");			
			serviceForm.setComponentValue(serviceForm.getTxtCode(), "ms4");
			serviceForm.setComponentValue(serviceForm.getTxtName(), "mservice4");
			
			clickOnApplyButton(serviceForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase7() {
	
		try {
			ServiceForm serviceForm = setFormComponents("user7@soberano.syscoop.co", "thirdparty_services.zul");			
			loadObjectDetails("service5 : s5");			
			serviceForm.setComponentValue(serviceForm.getTxtCode(), "ms5");
			serviceForm.setComponentValue(serviceForm.getTxtName(), "mservice5");
			
			clickOnApplyButton(serviceForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase8() {
	
		try {
			ServiceForm serviceForm = setFormComponents("user8@soberano.syscoop.co", "thirdparty_services.zul");			
			loadObjectDetails("service6 : s6");		
			serviceForm.setComponentValue(serviceForm.getTxtCode(), "ms6");
			serviceForm.setComponentValue(serviceForm.getTxtName(), "mservice6");
			
			clickOnApplyButton(serviceForm.getDesktop());
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testNotEnoughRightsException(ex);
		}
	}
	
	@Test
	final void testCase9() {
	
		try {
			ServiceForm serviceForm = setFormComponents("user9@soberano.syscoop.co", "thirdparty_services.zul");			
			loadObjectDetails("service6 : s6");			
			serviceForm.setComponentValue(serviceForm.getTxtCode(), "ms6");
			serviceForm.setComponentValue(serviceForm.getTxtName(), "mservice6");
			
			clickOnApplyButton(serviceForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase10() {
	
		try {
			ServiceForm serviceForm = setFormComponents("user10@soberano.syscoop.co", "thirdparty_services.zul");			
			loadObjectDetails("service7 : s7");			
			serviceForm.setComponentValue(serviceForm.getTxtCode(), "ms7");
			serviceForm.setComponentValue(serviceForm.getTxtName(), "mservice7");
			
			clickOnApplyButton(serviceForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase11() {
	
		try {
			ServiceForm serviceForm = setFormComponents("user11@soberano.syscoop.co", "thirdparty_services.zul");			
			loadObjectDetails("service8 : s8");			
			serviceForm.setComponentValue(serviceForm.getTxtCode(), "ms8");
			serviceForm.setComponentValue(serviceForm.getTxtName(), "mservice8");
			
			clickOnApplyButton(serviceForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase12() {
	
		try {
			ServiceForm serviceForm = setFormComponents("user12@soberano.syscoop.co", "thirdparty_services.zul");			
			loadObjectDetails("service9 : s9");			
			serviceForm.setComponentValue(serviceForm.getTxtCode(), "ms9");
			serviceForm.setComponentValue(serviceForm.getTxtName(), "mservice9");
			
			clickOnApplyButton(serviceForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase13() {
	
		try {
			ServiceForm serviceForm = setFormComponents("user1@soberano.syscoop.co", "thirdparty_services.zul");			
			loadObjectDetails("mservice9 : ms9");			
			serviceForm.setComponentValue(serviceForm.getTxtCode(), "ms1");
			serviceForm.setComponentValue(serviceForm.getTxtName(), "mservice93");
			
			clickOnApplyButton(serviceForm.getDesktop());
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testDuplicateKeyException(ex);
		}
	}
	
	@Test
	final void testCase14() {
	
		try {
			ServiceForm serviceForm = setFormComponents("user1@soberano.syscoop.co", "thirdparty_services.zul");			
			loadObjectDetails("mservice9 : ms9");			
			serviceForm.setComponentValue(serviceForm.getTxtCode(), "ms93");
			serviceForm.setComponentValue(serviceForm.getTxtName(), "mservice1");
			
			clickOnApplyButton(serviceForm.getDesktop());
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testDuplicateKeyException(ex);
		}
	}
}
