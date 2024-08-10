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
import org.zkoss.zats.mimic.operation.CheckAgent;

import co.syscoop.soberano.test.helper.ProductCategoryActionTest;
import co.syscoop.soberano.test.helper.ProductCategoryForm;

@Order(5)

//@Disabled

class O5_ProductCategoryTest_modify extends ProductCategoryActionTest{

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
	
		try {
			ProductCategoryForm productCategoryForm = setFormComponents("user1@soberano.syscoop.co", "product_categories.zul");			
			loadObjectDetails("cat1");			
			productCategoryForm.setComponentValue(productCategoryForm.getTxtName(), "mcat1");
			productCategoryForm.setComponentValue(productCategoryForm.getIntPosition(), 2);
			(productCategoryForm.getDesktop().query("checkbox").query("#chkDisabled")).as(CheckAgent.class).check(true);
			clickOnApplyButton(productCategoryForm.getDesktop());
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
			ProductCategoryForm productCategoryForm = setFormComponents("user2@soberano.syscoop.co", "product_categories.zul");			
			loadObjectDetails("cat2");			
			productCategoryForm.setComponentValue(productCategoryForm.getTxtName(), "mcat2");
			productCategoryForm.setComponentValue(productCategoryForm.getIntPosition(), 3);
			(productCategoryForm.getDesktop().query("checkbox").query("#chkDisabled")).as(CheckAgent.class).check(false);
			clickOnApplyButton(productCategoryForm.getDesktop());
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
			ProductCategoryForm productCategoryForm = setFormComponents("user3@soberano.syscoop.co", "product_categories.zul");			
			loadObjectDetails("cat3");			
			productCategoryForm.setComponentValue(productCategoryForm.getTxtName(), "mcat3");
			productCategoryForm.setComponentValue(productCategoryForm.getIntPosition(), 4);
			(productCategoryForm.getDesktop().query("checkbox").query("#chkDisabled")).as(CheckAgent.class).check(true);
			clickOnApplyButton(productCategoryForm.getDesktop());
			
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
	final void testCase4() {
	
		try {
			ProductCategoryForm productCategoryForm = setFormComponents("user4@soberano.syscoop.co", "product_categories.zul");			
			loadObjectDetails("cat3");			
			productCategoryForm.setComponentValue(productCategoryForm.getTxtName(), "mcat3");
			productCategoryForm.setComponentValue(productCategoryForm.getIntPosition(), 4);
			(productCategoryForm.getDesktop().query("checkbox").query("#chkDisabled")).as(CheckAgent.class).check(true);
			clickOnApplyButton(productCategoryForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase5() {
	
		try {
			ProductCategoryForm productCategoryForm = setFormComponents("user5@soberano.syscoop.co", "product_categories.zul");			
			loadObjectDetails("cat4");			
			productCategoryForm.setComponentValue(productCategoryForm.getTxtName(), "mcat4");
			productCategoryForm.setComponentValue(productCategoryForm.getIntPosition(), 5);
			(productCategoryForm.getDesktop().query("checkbox").query("#chkDisabled")).as(CheckAgent.class).check(false);
			clickOnApplyButton(productCategoryForm.getDesktop());
			
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
			ProductCategoryForm productCategoryForm = setFormComponents("user6@soberano.syscoop.co", "product_categories.zul");			
			loadObjectDetails("cat4");			
			productCategoryForm.setComponentValue(productCategoryForm.getTxtName(), "mcat4");
			productCategoryForm.setComponentValue(productCategoryForm.getIntPosition(), 5);
			(productCategoryForm.getDesktop().query("checkbox").query("#chkDisabled")).as(CheckAgent.class).check(false);
			clickOnApplyButton(productCategoryForm.getDesktop());
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
			ProductCategoryForm productCategoryForm = setFormComponents("user7@soberano.syscoop.co", "product_categories.zul");			
			loadObjectDetails("cat5");			
			productCategoryForm.setComponentValue(productCategoryForm.getTxtName(), "mcat5");
			productCategoryForm.setComponentValue(productCategoryForm.getIntPosition(), 6);
			(productCategoryForm.getDesktop().query("checkbox").query("#chkDisabled")).as(CheckAgent.class).check(true);
			clickOnApplyButton(productCategoryForm.getDesktop());
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
			ProductCategoryForm productCategoryForm = setFormComponents("user8@soberano.syscoop.co", "product_categories.zul");			
			loadObjectDetails("cat6");			
			productCategoryForm.setComponentValue(productCategoryForm.getTxtName(), "mcat6");
			productCategoryForm.setComponentValue(productCategoryForm.getIntPosition(), 7);
			(productCategoryForm.getDesktop().query("checkbox").query("#chkDisabled")).as(CheckAgent.class).check(false);
			clickOnApplyButton(productCategoryForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase9() {
	
		try {
			ProductCategoryForm productCategoryForm = setFormComponents("user9@soberano.syscoop.co", "product_categories.zul");			
			loadObjectDetails("cat7");			
			productCategoryForm.setComponentValue(productCategoryForm.getTxtName(), "mcat7");
			productCategoryForm.setComponentValue(productCategoryForm.getIntPosition(), 8);
			(productCategoryForm.getDesktop().query("checkbox").query("#chkDisabled")).as(CheckAgent.class).check(true);
			clickOnApplyButton(productCategoryForm.getDesktop());
			
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
	final void testCase10() {
	
		try {
			ProductCategoryForm productCategoryForm = setFormComponents("user10@soberano.syscoop.co", "product_categories.zul");			
			loadObjectDetails("cat7");			
			productCategoryForm.setComponentValue(productCategoryForm.getTxtName(), "mcat7");
			productCategoryForm.setComponentValue(productCategoryForm.getIntPosition(), 8);
			(productCategoryForm.getDesktop().query("checkbox").query("#chkDisabled")).as(CheckAgent.class).check(true);
			clickOnApplyButton(productCategoryForm.getDesktop());
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
			ProductCategoryForm productCategoryForm = setFormComponents("user11@soberano.syscoop.co", "product_categories.zul");			
			loadObjectDetails("cat8");			
			productCategoryForm.setComponentValue(productCategoryForm.getTxtName(), "mcat8");
			productCategoryForm.setComponentValue(productCategoryForm.getIntPosition(), 9);
			(productCategoryForm.getDesktop().query("checkbox").query("#chkDisabled")).as(CheckAgent.class).check(false);
			clickOnApplyButton(productCategoryForm.getDesktop());
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
			ProductCategoryForm productCategoryForm = setFormComponents("user12@soberano.syscoop.co", "product_categories.zul");			
			loadObjectDetails("cat9");			
			productCategoryForm.setComponentValue(productCategoryForm.getTxtName(), "mcat9");
			productCategoryForm.setComponentValue(productCategoryForm.getIntPosition(), 10);
			(productCategoryForm.getDesktop().query("checkbox").query("#chkDisabled")).as(CheckAgent.class).check(true);
			clickOnApplyButton(productCategoryForm.getDesktop());
			
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
			ProductCategoryForm productCategoryForm = setFormComponents("user13@soberano.syscoop.co", "product_categories.zul");			
			loadObjectDetails("cat9");			
			productCategoryForm.setComponentValue(productCategoryForm.getTxtName(), "mcat9");
			productCategoryForm.setComponentValue(productCategoryForm.getIntPosition(), 10);
			(productCategoryForm.getDesktop().query("checkbox").query("#chkDisabled")).as(CheckAgent.class).check(true);
			clickOnApplyButton(productCategoryForm.getDesktop());
			
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
	final void testCase14() {
	
		try {
			ProductCategoryForm productCategoryForm = setFormComponents("user14@soberano.syscoop.co", "product_categories.zul");			
			loadObjectDetails("cat9");			
			productCategoryForm.setComponentValue(productCategoryForm.getTxtName(), "mcat9");
			productCategoryForm.setComponentValue(productCategoryForm.getIntPosition(), 10);
			(productCategoryForm.getDesktop().query("checkbox").query("#chkDisabled")).as(CheckAgent.class).check(true);
			clickOnApplyButton(productCategoryForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase15() {
	
		try {
			ProductCategoryForm productCategoryForm = setFormComponents("user15@soberano.syscoop.co", "product_categories.zul");			
			loadObjectDetails("cat10");			
			productCategoryForm.setComponentValue(productCategoryForm.getTxtName(), "mcat10");
			productCategoryForm.setComponentValue(productCategoryForm.getIntPosition(), 11);
			(productCategoryForm.getDesktop().query("checkbox").query("#chkDisabled")).as(CheckAgent.class).check(false);
			clickOnApplyButton(productCategoryForm.getDesktop());
			
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
	final void testCase16() {
	
		try {
			ProductCategoryForm productCategoryForm = setFormComponents("user16@soberano.syscoop.co", "product_categories.zul");			
			loadObjectDetails("cat10");			
			productCategoryForm.setComponentValue(productCategoryForm.getTxtName(), "mcat10");
			productCategoryForm.setComponentValue(productCategoryForm.getIntPosition(), 11);
			(productCategoryForm.getDesktop().query("checkbox").query("#chkDisabled")).as(CheckAgent.class).check(false);
			clickOnApplyButton(productCategoryForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase17() {
	
		try {
			ProductCategoryForm productCategoryForm = setFormComponents("user1@soberano.syscoop.co", "product_categories.zul");			
			loadObjectDetails("mcat10");			
			productCategoryForm.setComponentValue(productCategoryForm.getTxtName(), "mcat1");
			productCategoryForm.setComponentValue(productCategoryForm.getIntPosition(), 11);
			(productCategoryForm.getDesktop().query("checkbox").query("#chkDisabled")).as(CheckAgent.class).check(false);
			clickOnApplyButton(productCategoryForm.getDesktop());
			
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
