package co.syscoop.soberano.test.classes;

import static org.junit.jupiter.api.Assertions.*;

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
import org.zkoss.lang.Library;
import org.zkoss.web.Attributes;
import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.test.helper.ServiceActionTest;
import co.syscoop.soberano.test.helper.ServiceForm;
import co.syscoop.soberano.util.SpringUtility;

@Order(3)

@Disabled

@TestMethodOrder(OrderAnnotation.class)
class O3_ServiceTest_record extends ServiceActionTest {
	
	protected ServiceForm serviceForm = null;

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
	@Order(0)
	final void testCase0() {
		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_thirdparty_service.zul");
		serviceForm = new ServiceForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class));
		try {
			//this is needed to execute only in the first test. it has to do with testing configuration.
			serviceForm.testEachConstrainedObjectIsDeclared();
			serviceForm.testEachDeclaredConstrainedObjectIsActuallyConstrained();
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
		
	@Test
	@Order(1)
	final void testCase1() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_thirdparty_service.zul");
		
		serviceForm = new ServiceForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class));
		try {
			serviceForm.setComponentValue(serviceForm.getTxtCode(), "s1");
			serviceForm.setComponentValue(serviceForm.getTxtName(), "");
			
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			serviceForm.testWrongValueException(ex);
		}
	}
	
	@Test
	@Order(2)
	final void testCase2() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_thirdparty_service.zul");
		
		serviceForm = new ServiceForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class));
		try {
			serviceForm.setComponentValue(serviceForm.getTxtCode(), "s1");
			serviceForm.setComponentValue(serviceForm.getTxtName(), "service1");
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(3)
	final void testCase3() {

		SpringUtility.setLoggedUserForTesting("user2@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_thirdparty_service.zul");
		
		serviceForm = new ServiceForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class));
		try {
			serviceForm.setComponentValue(serviceForm.getTxtCode(), "s2");
			serviceForm.setComponentValue(serviceForm.getTxtName(), "service2");
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(4)
	final void testCase4() {

		SpringUtility.setLoggedUserForTesting("user3@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_thirdparty_service.zul");
		
		serviceForm = new ServiceForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class));
		try {
			serviceForm.setComponentValue(serviceForm.getTxtCode(), "s3");
			serviceForm.setComponentValue(serviceForm.getTxtName(), "service3");
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(5)
	final void testCase5() {

		SpringUtility.setLoggedUserForTesting("user4@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_thirdparty_service.zul");
		
		serviceForm = new ServiceForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class));
		try {
			serviceForm.setComponentValue(serviceForm.getTxtCode(), "s4");
			serviceForm.setComponentValue(serviceForm.getTxtName(), "service4");	
			
			clickOnRecordButton(desktop);
			
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
	@Order(6)
	final void testCase6() {

		SpringUtility.setLoggedUserForTesting("user5@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_thirdparty_service.zul");
		
		serviceForm = new ServiceForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class));
		try {
			serviceForm.setComponentValue(serviceForm.getTxtCode(), "s4");
			serviceForm.setComponentValue(serviceForm.getTxtName(), "service4");	
			
			clickOnRecordButton(desktop);
			
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
	@Order(7)
	final void testCase7() {

		SpringUtility.setLoggedUserForTesting("user6@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_thirdparty_service.zul");
		
		serviceForm = new ServiceForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class));
		try {
			serviceForm.setComponentValue(serviceForm.getTxtCode(), "s4");
			serviceForm.setComponentValue(serviceForm.getTxtName(), "service4");
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(8)
	final void testCase8() {

		SpringUtility.setLoggedUserForTesting("user7@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_thirdparty_service.zul");
		
		serviceForm = new ServiceForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class));
		try {
			serviceForm.setComponentValue(serviceForm.getTxtCode(), "s5");
			serviceForm.setComponentValue(serviceForm.getTxtName(), "service5");
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(9)
	final void testCase9() {

		SpringUtility.setLoggedUserForTesting("user8@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_thirdparty_service.zul");
		
		serviceForm = new ServiceForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class));
		try {
			serviceForm.setComponentValue(serviceForm.getTxtCode(), "s6");
			serviceForm.setComponentValue(serviceForm.getTxtName(), "service6");	
			
			clickOnRecordButton(desktop);
			
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
	@Order(10)
	final void testCase10() {

		SpringUtility.setLoggedUserForTesting("user9@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_thirdparty_service.zul");
		
		serviceForm = new ServiceForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class));
		try {
			serviceForm.setComponentValue(serviceForm.getTxtCode(), "s6");
			serviceForm.setComponentValue(serviceForm.getTxtName(), "service6");
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(11)
	final void testCase11() {

		SpringUtility.setLoggedUserForTesting("user10@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_thirdparty_service.zul");
		
		serviceForm = new ServiceForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class));
		try {
			serviceForm.setComponentValue(serviceForm.getTxtCode(), "s7");
			serviceForm.setComponentValue(serviceForm.getTxtName(), "service7");
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(12)
	final void testCase12() {

		SpringUtility.setLoggedUserForTesting("user11@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_thirdparty_service.zul");
		
		serviceForm = new ServiceForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class));
		try {
			serviceForm.setComponentValue(serviceForm.getTxtCode(), "s8");
			serviceForm.setComponentValue(serviceForm.getTxtName(), "service8");
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(13)
	final void testCase13() {

		SpringUtility.setLoggedUserForTesting("user12@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_thirdparty_service.zul");
		
		serviceForm = new ServiceForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class));
		try {
			serviceForm.setComponentValue(serviceForm.getTxtCode(), "s9");
			serviceForm.setComponentValue(serviceForm.getTxtName(), "service9");
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(14)
	final void testCase14() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_thirdparty_service.zul");
		
		serviceForm = new ServiceForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class));
		try {
			serviceForm.setComponentValue(serviceForm.getTxtCode(), "s1");
			serviceForm.setComponentValue(serviceForm.getTxtName(), "service93");	
			
			clickOnRecordButton(desktop);
			
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
	@Order(15)
	final void testCase15() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_thirdparty_service.zul");
		
		serviceForm = new ServiceForm(desktop,
				(desktop.query("textbox").query("#txtName")).as(Textbox.class), 
				(desktop.query("textbox").query("#txtCode")).as(Textbox.class));
		try {
			serviceForm.setComponentValue(serviceForm.getTxtCode(), "s93");
			serviceForm.setComponentValue(serviceForm.getTxtName(), "service1");	
			
			clickOnRecordButton(desktop);
			
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