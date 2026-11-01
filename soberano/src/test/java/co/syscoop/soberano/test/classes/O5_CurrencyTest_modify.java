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
import org.zkoss.zats.mimic.ComponentAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zats.mimic.operation.CheckAgent;
import org.zkoss.zats.mimic.operation.InputAgent;

import co.syscoop.soberano.test.helper.CurrencyActionTest;
import co.syscoop.soberano.test.helper.CurrencyForm;

@Order(5)

//@Disabled

class O5_CurrencyTest_modify extends CurrencyActionTest{

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
	@Order(1)
	final void testCase1() {
	
		try {
			CurrencyForm currencyForm = setFormComponents("user1@soberano.syscoop.co", "currencies.zul");			
			loadObjectDetails("currency1 : c1");			
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "mc1");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "mcurrency1");
			
			currencyForm.getDesktop().query("checkbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("2.23456789");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), 2);
			currencyForm.getDesktop().query("checkbox").query("#chkIsSystemCurrency").as(CheckAgent.class).check(true);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsPriceReferenceCurrency").as(CheckAgent.class).check(true);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsCash").as(CheckAgent.class).check(false);
			ComponentAgent cmbPaymentProcessorAgent = currencyForm.getDesktop().query("checkbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Tropipay");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 2);
			
			clickOnApplyButton(currencyForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	@Order(2)
	final void testCase2() {
	
		try {
			CurrencyForm currencyForm = setFormComponents("user2@soberano.syscoop.co", "currencies.zul");			
			loadObjectDetails("currency2 : c2");			
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "mc2");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "mcurrency2");
			
			currencyForm.getDesktop().query("checkbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("3");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), 3);
			currencyForm.getDesktop().query("checkbox").query("#chkIsSystemCurrency").as(CheckAgent.class).check(true);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsPriceReferenceCurrency").as(CheckAgent.class).check(true);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsCash").as(CheckAgent.class).check(false);
			ComponentAgent cmbPaymentProcessorAgent = currencyForm.getDesktop().query("checkbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Opennode");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 1);
			
			clickOnApplyButton(currencyForm.getDesktop());
			
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
	@Order(3)
	final void testCase3() {
	
		try {
			CurrencyForm currencyForm = setFormComponents("user3@soberano.syscoop.co", "currencies.zul");			
			loadObjectDetails("currency2 : c2");			
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "mc2");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "mcurrency2");
			
			currencyForm.getDesktop().query("checkbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("3");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), 3);
			currencyForm.getDesktop().query("checkbox").query("#chkIsSystemCurrency").as(CheckAgent.class).check(true);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsPriceReferenceCurrency").as(CheckAgent.class).check(true);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsCash").as(CheckAgent.class).check(false);
			ComponentAgent cmbPaymentProcessorAgent = currencyForm.getDesktop().query("checkbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Opennode");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 1);
			
			clickOnApplyButton(currencyForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw(ex);
		}
	}
	
	@Test
	@Order(4)
	final void testCase4() {
	
		try {
			CurrencyForm currencyForm = setFormComponents("user4@soberano.syscoop.co", "currencies.zul");			
			loadObjectDetails("currency3 : c3");			
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "mc3");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "mcurrency3");
			
			currencyForm.getDesktop().query("checkbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("4.34567809");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), 4);
			currencyForm.getDesktop().query("checkbox").query("#chkIsSystemCurrency").as(CheckAgent.class).check(false);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsPriceReferenceCurrency").as(CheckAgent.class).check(true);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsCash").as(CheckAgent.class).check(true);
			ComponentAgent cmbPaymentProcessorAgent = currencyForm.getDesktop().query("checkbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Tropipay");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 2);
			
			clickOnApplyButton(currencyForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw(ex);
		}
	}
	
	@Test
	@Order(5)
	final void testCase5() {
	
		try {
			CurrencyForm currencyForm = setFormComponents("user5@soberano.syscoop.co", "currencies.zul");			
			loadObjectDetails("currency4 : c4");			
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "mc4");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "mcurrency4");
			
			currencyForm.getDesktop().query("checkbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("5");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), 5);
			currencyForm.getDesktop().query("checkbox").query("#chkIsSystemCurrency").as(CheckAgent.class).check(false);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsPriceReferenceCurrency").as(CheckAgent.class).check(false);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsCash").as(CheckAgent.class).check(false);
			ComponentAgent cmbPaymentProcessorAgent = currencyForm.getDesktop().query("checkbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Tropipay");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 2);
			
			clickOnApplyButton(currencyForm.getDesktop());
			
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
	
		try {
			CurrencyForm currencyForm = setFormComponents("user6@soberano.syscoop.co", "currencies.zul");			
			loadObjectDetails("currency4 : c4");			
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "mc4");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "mcurrency4");
			
			currencyForm.getDesktop().query("checkbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("5");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), 5);
			currencyForm.getDesktop().query("checkbox").query("#chkIsSystemCurrency").as(CheckAgent.class).check(false);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsPriceReferenceCurrency").as(CheckAgent.class).check(false);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsCash").as(CheckAgent.class).check(false);
			ComponentAgent cmbPaymentProcessorAgent = currencyForm.getDesktop().query("checkbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Tropipay");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 2);
			
			clickOnApplyButton(currencyForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw(ex);
		}
	}
	
	@Test
	@Order(7)
	final void testCase7() {
	
		try {
			CurrencyForm currencyForm = setFormComponents("user7@soberano.syscoop.co", "currencies.zul");			
			loadObjectDetails("currency5 : c5");			
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "mc5");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "mcurrency5");
			
			currencyForm.getDesktop().query("checkbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("6.67800009");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), 6);
			currencyForm.getDesktop().query("checkbox").query("#chkIsSystemCurrency").as(CheckAgent.class).check(false);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsPriceReferenceCurrency").as(CheckAgent.class).check(false);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsCash").as(CheckAgent.class).check(true);
			ComponentAgent cmbPaymentProcessorAgent = currencyForm.getDesktop().query("checkbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Opennode");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 1);
			
			clickOnApplyButton(currencyForm.getDesktop());
			
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
	@Order(8)
	final void testCase8() {
	
		try {
			CurrencyForm currencyForm = setFormComponents("user8@soberano.syscoop.co", "currencies.zul");			
			loadObjectDetails("currency5 : c5");			
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "mc5");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "mcurrency5");
			
			currencyForm.getDesktop().query("checkbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("6.67800009");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), 6);
			currencyForm.getDesktop().query("checkbox").query("#chkIsSystemCurrency").as(CheckAgent.class).check(false);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsPriceReferenceCurrency").as(CheckAgent.class).check(false);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsCash").as(CheckAgent.class).check(true);
			ComponentAgent cmbPaymentProcessorAgent = currencyForm.getDesktop().query("checkbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Opennode");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 1);
			
			clickOnApplyButton(currencyForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw(ex);
		}
	}
	
	@Test
	@Order(9)
	final void testCase9() {
	
		try {
			CurrencyForm currencyForm = setFormComponents("user9@soberano.syscoop.co", "currencies.zul");			
			loadObjectDetails("currency6 : c6");			
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "mc6");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "mcurrency6");
			
			currencyForm.getDesktop().query("checkbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("7");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), 7);
			currencyForm.getDesktop().query("checkbox").query("#chkIsSystemCurrency").as(CheckAgent.class).check(false);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsPriceReferenceCurrency").as(CheckAgent.class).check(false);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsCash").as(CheckAgent.class).check(true);
			ComponentAgent cmbPaymentProcessorAgent = currencyForm.getDesktop().query("checkbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Tropipay");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 2);
			
			clickOnApplyButton(currencyForm.getDesktop());
			
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
	
		try {
			CurrencyForm currencyForm = setFormComponents("user10@soberano.syscoop.co", "currencies.zul");			
			loadObjectDetails("currency6 : c6");			
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "mc6");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "mcurrency6");
			
			currencyForm.getDesktop().query("checkbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("7");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), 7);
			currencyForm.getDesktop().query("checkbox").query("#chkIsSystemCurrency").as(CheckAgent.class).check(false);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsPriceReferenceCurrency").as(CheckAgent.class).check(false);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsCash").as(CheckAgent.class).check(true);
			ComponentAgent cmbPaymentProcessorAgent = currencyForm.getDesktop().query("checkbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Tropipay");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 2);
			
			clickOnApplyButton(currencyForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw(ex);
		}
	}
	
	@Test
	@Order(11)
	final void testCase11() {
	
		try {
			CurrencyForm currencyForm = setFormComponents("user11@soberano.syscoop.co", "currencies.zul");			
			loadObjectDetails("currency7 : c7");			
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "mc7");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "mcurrency7");
			
			currencyForm.getDesktop().query("checkbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("8.00000089");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), 8);
			currencyForm.getDesktop().query("checkbox").query("#chkIsSystemCurrency").as(CheckAgent.class).check(false);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsPriceReferenceCurrency").as(CheckAgent.class).check(false);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsCash").as(CheckAgent.class).check(false);
			ComponentAgent cmbPaymentProcessorAgent = currencyForm.getDesktop().query("checkbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Opennode");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 1);
			
			clickOnApplyButton(currencyForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw(ex);
		}
	}
	
	@Test
	@Order(12)
	final void testCase12() {
	
		try {
			CurrencyForm currencyForm = setFormComponents("user12@soberano.syscoop.co", "currencies.zul");			
			loadObjectDetails("currency8 : c8");			
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "mc8");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "mcurrency8");
			
			currencyForm.getDesktop().query("checkbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("9");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), 9);
			currencyForm.getDesktop().query("checkbox").query("#chkIsSystemCurrency").as(CheckAgent.class).check(false);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsPriceReferenceCurrency").as(CheckAgent.class).check(false);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsCash").as(CheckAgent.class).check(true);
			ComponentAgent cmbPaymentProcessorAgent = currencyForm.getDesktop().query("checkbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Tropipay");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 2);
			
			clickOnApplyButton(currencyForm.getDesktop());
			
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
	@Order(13)
	final void testCase13() {
	
		try {
			CurrencyForm currencyForm = setFormComponents("user13@soberano.syscoop.co", "currencies.zul");			
			loadObjectDetails("currency8 : c8");			
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "mc8");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "mcurrency8");
			
			currencyForm.getDesktop().query("checkbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("9");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), 9);
			currencyForm.getDesktop().query("checkbox").query("#chkIsSystemCurrency").as(CheckAgent.class).check(false);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsPriceReferenceCurrency").as(CheckAgent.class).check(false);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsCash").as(CheckAgent.class).check(true);
			ComponentAgent cmbPaymentProcessorAgent = currencyForm.getDesktop().query("checkbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Tropipay");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 2);
			
			clickOnApplyButton(currencyForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw(ex);
		}
	}
	
	@Test
	@Order(14)
	final void testCase14() {
	
		try {
			CurrencyForm currencyForm = setFormComponents("user14@soberano.syscoop.co", "currencies.zul");			
			loadObjectDetails("currency9 : c9");			
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "mc9");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "mcurrency9");
			
			currencyForm.getDesktop().query("checkbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("10");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), 10);
			currencyForm.getDesktop().query("checkbox").query("#chkIsSystemCurrency").as(CheckAgent.class).check(false);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsPriceReferenceCurrency").as(CheckAgent.class).check(false);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsCash").as(CheckAgent.class).check(false);
			ComponentAgent cmbPaymentProcessorAgent = currencyForm.getDesktop().query("checkbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Tropipay");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 2);
			
			clickOnApplyButton(currencyForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw(ex);
		}
	}
	
	@Test
	@Order(15)
	final void testCase15() {
	
		try {
			CurrencyForm currencyForm = setFormComponents("user12@soberano.syscoop.co", "currencies.zul");			
			loadObjectDetails("mcurrency8 : mc8");			
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "mc91");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "mcurrency91");
			
			currencyForm.getDesktop().query("checkbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("11");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), 11);
			currencyForm.getDesktop().query("checkbox").query("#chkIsSystemCurrency").as(CheckAgent.class).check(false);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsPriceReferenceCurrency").as(CheckAgent.class).check(false);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsCash").as(CheckAgent.class).check(true);
			ComponentAgent cmbPaymentProcessorAgent = currencyForm.getDesktop().query("checkbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Opennode");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 1);
			
			clickOnApplyButton(currencyForm.getDesktop());
			
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
	@Order(16)
	final void testCase16() {
	
		try {
			CurrencyForm currencyForm = setFormComponents("user1@soberano.syscoop.co", "currencies.zul");			
			loadObjectDetails("mcurrency1 : mc1");			
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "mc91");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "mcurrency9");
			
			currencyForm.getDesktop().query("checkbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("12");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), 10);
			currencyForm.getDesktop().query("checkbox").query("#chkIsSystemCurrency").as(CheckAgent.class).check(false);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsPriceReferenceCurrency").as(CheckAgent.class).check(false);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsCash").as(CheckAgent.class).check(true);
			ComponentAgent cmbPaymentProcessorAgent = currencyForm.getDesktop().query("checkbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Opennode");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 1);
			
			clickOnApplyButton(currencyForm.getDesktop());
			
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
	@Order(17)
	final void testCase17() {
	
		try {
			CurrencyForm currencyForm = setFormComponents("user1@soberano.syscoop.co", "currencies.zul");			
			loadObjectDetails("mcurrency1 : mc1");			
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "mc9");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "mcurrency91");
			
			currencyForm.getDesktop().query("checkbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("12");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), 11);
			currencyForm.getDesktop().query("checkbox").query("#chkIsSystemCurrency").as(CheckAgent.class).check(false);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsPriceReferenceCurrency").as(CheckAgent.class).check(false);	
			currencyForm.getDesktop().query("checkbox").query("#chkIsCash").as(CheckAgent.class).check(true);
			ComponentAgent cmbPaymentProcessorAgent = currencyForm.getDesktop().query("checkbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Opennode");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 1);
			
			clickOnApplyButton(currencyForm.getDesktop());
			
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
