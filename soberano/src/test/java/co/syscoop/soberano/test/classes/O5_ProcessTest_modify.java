package co.syscoop.soberano.test.classes;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

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

import co.syscoop.soberano.test.helper.ProcessActionTest;
import co.syscoop.soberano.test.helper.ProcessForm;

@Order(5)

@Disabled

class O5_ProcessTest_modify extends ProcessActionTest{

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		Library.setProperty(Attributes.PREFERRED_LOCALE, "en"); //needed due to translated captions according 
		//to runtime locale not available under 
		//testing environment
		
		//Zats.init("./src/main/webapp");	
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
	final void testCase1() {
	
		try {
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("pr1");			
			processForm.setComponentValue(processForm.getTxtName(), "mpr1");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(2.123));
			
			clickOnApplyButton(processForm.getDesktop());
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
			ProcessForm processForm = setFormComponents("user2@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("pr2");			
			processForm.setComponentValue(processForm.getTxtName(), "mpr2");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(3.456));
			
			clickOnApplyButton(processForm.getDesktop());
			
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
	final void testCase3() {
	
		try {
			ProcessForm processForm = setFormComponents("user3@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("pr2");			
			processForm.setComponentValue(processForm.getTxtName(), "mpr2");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(3.456));
			
			clickOnApplyButton(processForm.getDesktop());
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
			ProcessForm processForm = setFormComponents("user4@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("pr3");			
			processForm.setComponentValue(processForm.getTxtName(), "mpr3");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(4.789));
			
			clickOnApplyButton(processForm.getDesktop());
			
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
			ProcessForm processForm = setFormComponents("user5@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("pr3");			
			processForm.setComponentValue(processForm.getTxtName(), "mpr3");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(4.789));
			
			clickOnApplyButton(processForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase6() {
	
		try {
			ProcessForm processForm = setFormComponents("user6@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("pr4");			
			processForm.setComponentValue(processForm.getTxtName(), "mpr4");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(5.987));
			
			clickOnApplyButton(processForm.getDesktop());
			
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
	final void testCase7() {
	
		try {
			ProcessForm processForm = setFormComponents("user7@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("pr4");			
			processForm.setComponentValue(processForm.getTxtName(), "mpr4");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(5.987));
			
			clickOnApplyButton(processForm.getDesktop());
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
			ProcessForm processForm = setFormComponents("user8@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("pr5");			
			processForm.setComponentValue(processForm.getTxtName(), "mpr5");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(6.654));
			
			clickOnApplyButton(processForm.getDesktop());
			
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
			ProcessForm processForm = setFormComponents("user9@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("pr5");			
			processForm.setComponentValue(processForm.getTxtName(), "mpr5");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(6.654));
			
			clickOnApplyButton(processForm.getDesktop());
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
			ProcessForm processForm = setFormComponents("user10@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("pr6");			
			processForm.setComponentValue(processForm.getTxtName(), "mpr6");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(7.321));
			
			clickOnApplyButton(processForm.getDesktop());
			
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
	final void testCase11() {
	
		try {
			ProcessForm processForm = setFormComponents("user11@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("pr6");			
			processForm.setComponentValue(processForm.getTxtName(), "mpr6");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(7.321));
			
			clickOnApplyButton(processForm.getDesktop());
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
			ProcessForm processForm = setFormComponents("user12@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("pr7");			
			processForm.setComponentValue(processForm.getTxtName(), "mpr7");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(8.111));
			
			clickOnApplyButton(processForm.getDesktop());
			
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
	final void testCase13() {
	
		try {
			ProcessForm processForm = setFormComponents("user13@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("pr7");			
			processForm.setComponentValue(processForm.getTxtName(), "mpr7");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(8.111));
			
			clickOnApplyButton(processForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase14() {
	
		try {
			ProcessForm processForm = setFormComponents("user14@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("pr8");			
			processForm.setComponentValue(processForm.getTxtName(), "mpr8");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(9.222));
			
			clickOnApplyButton(processForm.getDesktop());
			
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
	final void testCase15() {
	
		try {
			ProcessForm processForm = setFormComponents("user15@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("pr8");			
			processForm.setComponentValue(processForm.getTxtName(), "mpr8");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(9.222));
			
			clickOnApplyButton(processForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase16() {
	
		try {
			ProcessForm processForm = setFormComponents("user16@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("pr9");			
			processForm.setComponentValue(processForm.getTxtName(), "mpr9");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(10.333));
			
			clickOnApplyButton(processForm.getDesktop());
			
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
	final void testCase17() {
	
		try {
			ProcessForm processForm = setFormComponents("user17@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("pr9");			
			processForm.setComponentValue(processForm.getTxtName(), "mpr9");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(10.333));
			
			clickOnApplyButton(processForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
			
	@Test
	final void testCase18() {
	
		try {
			ProcessForm processForm = setFormComponents("user19@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("pr10");			
			processForm.setComponentValue(processForm.getTxtName(), "mpr10");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(11.444));
			
			clickOnApplyButton(processForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
		
	@Test
	final void testCase19() {
	
		try {
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");			
			loadObjectDetails("mpr2");			
			processForm.setComponentValue(processForm.getTxtName(), "mpr10");
			processForm.setComponentValue(processForm.getDecFixedCost(), new BigDecimal(11.666));
			
			clickOnApplyButton(processForm.getDesktop());
			
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
