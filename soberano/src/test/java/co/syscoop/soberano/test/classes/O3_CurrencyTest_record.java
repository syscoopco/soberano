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
import org.zkoss.zats.mimic.ComponentAgent;
import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zats.mimic.operation.CheckAgent;
import org.zkoss.zats.mimic.operation.InputAgent;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.test.helper.CurrencyActionTest;
import co.syscoop.soberano.test.helper.CurrencyForm;
import co.syscoop.soberano.util.SpringUtility;

@Order(3)

//@Disabled

@TestMethodOrder(OrderAnnotation.class)
class O3_CurrencyTest_record extends CurrencyActionTest {
	
	protected CurrencyForm currencyForm = null;

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
	@Order(0)
	final void testCase0() {
		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_currency.zul");
		currencyForm = new CurrencyForm(desktop, 
										desktop.query("textbox").query("#txtName").as(Textbox.class), 
										desktop.query("textbox").query("#txtCode").as(Textbox.class),
										desktop.query("checkbox").query("#chkIsSystemCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsPriceReferenceCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsCash").as(Checkbox.class),
										desktop.query("textbox").query("#cmbPaymentProcessor").as(Combobox.class),
										desktop.query("textbox").query("#txtExchangeRateExpression").as(Textbox.class),
										desktop.query("decimalbox").query("#decExchangeRate").as(Decimalbox.class),
										desktop.query("intbox").query("#intPosition").as(Intbox.class));
		try {
			//this is needed to execute only in the first test. it has to do with testing configuration.
			currencyForm.testEachConstrainedObjectIsDeclared();
			currencyForm.testEachDeclaredConstrainedObjectIsActuallyConstrained();
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
		DesktopAgent desktop = Zats.newClient().connect("/new_currency.zul");
		currencyForm = new CurrencyForm(desktop, 
										desktop.query("textbox").query("#txtName").as(Textbox.class), 
										desktop.query("textbox").query("#txtCode").as(Textbox.class),
										desktop.query("checkbox").query("#chkIsSystemCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsPriceReferenceCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsCash").as(Checkbox.class),
										desktop.query("textbox").query("#cmbPaymentProcessor").as(Combobox.class),
										desktop.query("textbox").query("#txtExchangeRateExpression").as(Textbox.class),
										desktop.query("decimalbox").query("#decExchangeRate").as(Decimalbox.class),
										desktop.query("intbox").query("#intPosition").as(Intbox.class));
		try {
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "c1");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "");
			
			desktop.query("textbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("1.23456789");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), 1);
			(desktop.query("checkbox").query("#chkIsSystemCurrency")).as(CheckAgent.class).check(true);	
			(desktop.query("checkbox").query("#chkIsPriceReferenceCurrency")).as(CheckAgent.class).check(true);	
			(desktop.query("checkbox").query("#chkIsCash")).as(CheckAgent.class).check(true);
			ComponentAgent cmbPaymentProcessorAgent = desktop.query("textbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Opennode");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 1);		
			
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			currencyForm.testWrongValueException(ex);
		}
	}
	
	@Test
	@Order(2)
	final void testCase2() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_currency.zul");
		currencyForm = new CurrencyForm(desktop, 
										desktop.query("textbox").query("#txtName").as(Textbox.class), 
										desktop.query("textbox").query("#txtCode").as(Textbox.class),
										desktop.query("checkbox").query("#chkIsSystemCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsPriceReferenceCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsCash").as(Checkbox.class),
										desktop.query("textbox").query("#cmbPaymentProcessor").as(Combobox.class),
										desktop.query("textbox").query("#txtExchangeRateExpression").as(Textbox.class),
										desktop.query("decimalbox").query("#decExchangeRate").as(Decimalbox.class),
										desktop.query("intbox").query("#intPosition").as(Intbox.class));
		try {
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "currency1");
			

			desktop.query("textbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("1.23456789");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), 1);
			(desktop.query("checkbox").query("#chkIsSystemCurrency")).as(CheckAgent.class).check(true);	
			(desktop.query("checkbox").query("#chkIsPriceReferenceCurrency")).as(CheckAgent.class).check(true);	
			(desktop.query("checkbox").query("#chkIsCash")).as(CheckAgent.class).check(true);
			ComponentAgent cmbPaymentProcessorAgent = desktop.query("textbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Opennode");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 1);		
			
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			currencyForm.testWrongValueException(ex);
		}
	}
	
	@Test
	@Order(3)
	final void testCase3() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_currency.zul");
		currencyForm = new CurrencyForm(desktop, 
										desktop.query("textbox").query("#txtName").as(Textbox.class), 
										desktop.query("textbox").query("#txtCode").as(Textbox.class),
										desktop.query("checkbox").query("#chkIsSystemCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsPriceReferenceCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsCash").as(Checkbox.class),
										desktop.query("textbox").query("#cmbPaymentProcessor").as(Combobox.class),
										desktop.query("textbox").query("#txtExchangeRateExpression").as(Textbox.class),
										desktop.query("decimalbox").query("#decExchangeRate").as(Decimalbox.class),
										desktop.query("intbox").query("#intPosition").as(Intbox.class));
		try {
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "c1");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "currency1");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), 1);
			(desktop.query("checkbox").query("#chkIsSystemCurrency")).as(CheckAgent.class).check(true);	
			(desktop.query("checkbox").query("#chkIsPriceReferenceCurrency")).as(CheckAgent.class).check(true);	
			(desktop.query("checkbox").query("#chkIsCash")).as(CheckAgent.class).check(true);
			ComponentAgent cmbPaymentProcessorAgent = desktop.query("textbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Opennode");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 1);		
			
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			currencyForm.testWrongValueException(ex);
		}
	}
	
	@Test
	@Order(4)
	final void testCase4() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_currency.zul");
		currencyForm = new CurrencyForm(desktop, 
										desktop.query("textbox").query("#txtName").as(Textbox.class), 
										desktop.query("textbox").query("#txtCode").as(Textbox.class),
										desktop.query("checkbox").query("#chkIsSystemCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsPriceReferenceCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsCash").as(Checkbox.class),
										desktop.query("textbox").query("#cmbPaymentProcessor").as(Combobox.class),
										desktop.query("textbox").query("#txtExchangeRateExpression").as(Textbox.class),
										desktop.query("decimalbox").query("#decExchangeRate").as(Decimalbox.class),
										desktop.query("intbox").query("#intPosition").as(Intbox.class));
		try {
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "c1");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "currency1");
			
			desktop.query("textbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("-1.23456789");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), 1);
			(desktop.query("checkbox").query("#chkIsSystemCurrency")).as(CheckAgent.class).check(true);	
			(desktop.query("checkbox").query("#chkIsPriceReferenceCurrency")).as(CheckAgent.class).check(true);	
			(desktop.query("checkbox").query("#chkIsCash")).as(CheckAgent.class).check(true);
			ComponentAgent cmbPaymentProcessorAgent = desktop.query("textbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Opennode");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 1);		
			
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			currencyForm.testWrongValueException(ex);
		}
	}
	
	@Test
	@Order(5)
	final void testCase5() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_currency.zul");
		currencyForm = new CurrencyForm(desktop, 
										desktop.query("textbox").query("#txtName").as(Textbox.class), 
										desktop.query("textbox").query("#txtCode").as(Textbox.class),
										desktop.query("checkbox").query("#chkIsSystemCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsPriceReferenceCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsCash").as(Checkbox.class),
										desktop.query("textbox").query("#cmbPaymentProcessor").as(Combobox.class),
										desktop.query("textbox").query("#txtExchangeRateExpression").as(Textbox.class),
										desktop.query("decimalbox").query("#decExchangeRate").as(Decimalbox.class),
										desktop.query("intbox").query("#intPosition").as(Intbox.class));
		try {
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "c1");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "currency1");
			
			desktop.query("textbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("1.23456789");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), "");
			(desktop.query("checkbox").query("#chkIsSystemCurrency")).as(CheckAgent.class).check(true);	
			(desktop.query("checkbox").query("#chkIsPriceReferenceCurrency")).as(CheckAgent.class).check(true);	
			(desktop.query("checkbox").query("#chkIsCash")).as(CheckAgent.class).check(true);
			ComponentAgent cmbPaymentProcessorAgent = desktop.query("textbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Opennode");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 1);		
			
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			currencyForm.testWrongValueException(ex);
		}
	}
	
	@Test
	@Order(6)
	final void testCase6() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_currency.zul");
		currencyForm = new CurrencyForm(desktop, 
										desktop.query("textbox").query("#txtName").as(Textbox.class), 
										desktop.query("textbox").query("#txtCode").as(Textbox.class),
										desktop.query("checkbox").query("#chkIsSystemCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsPriceReferenceCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsCash").as(Checkbox.class),
										desktop.query("textbox").query("#cmbPaymentProcessor").as(Combobox.class),
										desktop.query("textbox").query("#txtExchangeRateExpression").as(Textbox.class),
										desktop.query("decimalbox").query("#decExchangeRate").as(Decimalbox.class),
										desktop.query("intbox").query("#intPosition").as(Intbox.class));
		try {
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "c1");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "currency1");
			
			desktop.query("textbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("1.23456789");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), "1");
			(desktop.query("checkbox").query("#chkIsSystemCurrency")).as(CheckAgent.class).check(true);	
			(desktop.query("checkbox").query("#chkIsPriceReferenceCurrency")).as(CheckAgent.class).check(true);	
			(desktop.query("checkbox").query("#chkIsCash")).as(CheckAgent.class).check(true);
			ComponentAgent cmbPaymentProcessorAgent = desktop.query("textbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Opennode");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 1);		
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(7)
	final void testCase7() {

		SpringUtility.setLoggedUserForTesting("user2@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_currency.zul");
		currencyForm = new CurrencyForm(desktop, 
										desktop.query("textbox").query("#txtName").as(Textbox.class), 
										desktop.query("textbox").query("#txtCode").as(Textbox.class),
										desktop.query("checkbox").query("#chkIsSystemCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsPriceReferenceCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsCash").as(Checkbox.class),
										desktop.query("textbox").query("#cmbPaymentProcessor").as(Combobox.class),
										desktop.query("textbox").query("#txtExchangeRateExpression").as(Textbox.class),
										desktop.query("decimalbox").query("#decExchangeRate").as(Decimalbox.class),
										desktop.query("intbox").query("#intPosition").as(Intbox.class));
		try {
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "c2");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "currency2");
			
			desktop.query("textbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("2");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), "2");
			(desktop.query("checkbox").query("#chkIsSystemCurrency")).as(CheckAgent.class).check(true);	
			(desktop.query("checkbox").query("#chkIsPriceReferenceCurrency")).as(CheckAgent.class).check(true);	
			(desktop.query("checkbox").query("#chkIsCash")).as(CheckAgent.class).check(true);
			ComponentAgent cmbPaymentProcessorAgent = desktop.query("textbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Tropipay");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 2);		
			
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
	@Order(8)
	final void testCase8() {

		SpringUtility.setLoggedUserForTesting("user3@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_currency.zul");
		currencyForm = new CurrencyForm(desktop, 
										desktop.query("textbox").query("#txtName").as(Textbox.class), 
										desktop.query("textbox").query("#txtCode").as(Textbox.class),
										desktop.query("checkbox").query("#chkIsSystemCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsPriceReferenceCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsCash").as(Checkbox.class),
										desktop.query("textbox").query("#cmbPaymentProcessor").as(Combobox.class),
										desktop.query("textbox").query("#txtExchangeRateExpression").as(Textbox.class),
										desktop.query("decimalbox").query("#decExchangeRate").as(Decimalbox.class),
										desktop.query("intbox").query("#intPosition").as(Intbox.class));
		try {
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "c2");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "currency2");
			
			desktop.query("textbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("2");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), "2");
			(desktop.query("checkbox").query("#chkIsSystemCurrency")).as(CheckAgent.class).check(true);	
			(desktop.query("checkbox").query("#chkIsPriceReferenceCurrency")).as(CheckAgent.class).check(true);	
			(desktop.query("checkbox").query("#chkIsCash")).as(CheckAgent.class).check(true);
			ComponentAgent cmbPaymentProcessorAgent = desktop.query("textbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Tropipay");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 2);		
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(9)
	final void testCase9() {

		SpringUtility.setLoggedUserForTesting("user4@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_currency.zul");
		currencyForm = new CurrencyForm(desktop, 
										desktop.query("textbox").query("#txtName").as(Textbox.class), 
										desktop.query("textbox").query("#txtCode").as(Textbox.class),
										desktop.query("checkbox").query("#chkIsSystemCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsPriceReferenceCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsCash").as(Checkbox.class),
										desktop.query("textbox").query("#cmbPaymentProcessor").as(Combobox.class),
										desktop.query("textbox").query("#txtExchangeRateExpression").as(Textbox.class),
										desktop.query("decimalbox").query("#decExchangeRate").as(Decimalbox.class),
										desktop.query("intbox").query("#intPosition").as(Intbox.class));
		try {
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "c3");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "currency3");
			
			desktop.query("textbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("3.3456789");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), "3");
			(desktop.query("checkbox").query("#chkIsSystemCurrency")).as(CheckAgent.class).check(true);	
			(desktop.query("checkbox").query("#chkIsPriceReferenceCurrency")).as(CheckAgent.class).check(true);	
			(desktop.query("checkbox").query("#chkIsCash")).as(CheckAgent.class).check(false);
			ComponentAgent cmbPaymentProcessorAgent = desktop.query("textbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Opennode");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 1);		
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(10)
	final void testCase10() {

		SpringUtility.setLoggedUserForTesting("user5@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_currency.zul");
		currencyForm = new CurrencyForm(desktop, 
										desktop.query("textbox").query("#txtName").as(Textbox.class), 
										desktop.query("textbox").query("#txtCode").as(Textbox.class),
										desktop.query("checkbox").query("#chkIsSystemCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsPriceReferenceCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsCash").as(Checkbox.class),
										desktop.query("textbox").query("#cmbPaymentProcessor").as(Combobox.class),
										desktop.query("textbox").query("#txtExchangeRateExpression").as(Textbox.class),
										desktop.query("decimalbox").query("#decExchangeRate").as(Decimalbox.class),
										desktop.query("intbox").query("#intPosition").as(Intbox.class));
		try {
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "c4");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "currency4");
			
			desktop.query("textbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("4");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), "4");
			(desktop.query("checkbox").query("#chkIsSystemCurrency")).as(CheckAgent.class).check(false);	
			(desktop.query("checkbox").query("#chkIsPriceReferenceCurrency")).as(CheckAgent.class).check(true);	
			(desktop.query("checkbox").query("#chkIsCash")).as(CheckAgent.class).check(true);
			ComponentAgent cmbPaymentProcessorAgent = desktop.query("textbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Opennode");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 1);		
			
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
	@Order(11)
	final void testCase11() {

		SpringUtility.setLoggedUserForTesting("user6@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_currency.zul");
		currencyForm = new CurrencyForm(desktop, 
										desktop.query("textbox").query("#txtName").as(Textbox.class), 
										desktop.query("textbox").query("#txtCode").as(Textbox.class),
										desktop.query("checkbox").query("#chkIsSystemCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsPriceReferenceCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsCash").as(Checkbox.class),
										desktop.query("textbox").query("#cmbPaymentProcessor").as(Combobox.class),
										desktop.query("textbox").query("#txtExchangeRateExpression").as(Textbox.class),
										desktop.query("decimalbox").query("#decExchangeRate").as(Decimalbox.class),
										desktop.query("intbox").query("#intPosition").as(Intbox.class));
		try {
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "c4");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "currency4");
			
			desktop.query("textbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("4");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), "4");
			(desktop.query("checkbox").query("#chkIsSystemCurrency")).as(CheckAgent.class).check(false);	
			(desktop.query("checkbox").query("#chkIsPriceReferenceCurrency")).as(CheckAgent.class).check(true);	
			(desktop.query("checkbox").query("#chkIsCash")).as(CheckAgent.class).check(true);
			ComponentAgent cmbPaymentProcessorAgent = desktop.query("textbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Opennode");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 1);		
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(12)
	final void testCase12() {

		SpringUtility.setLoggedUserForTesting("user7@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_currency.zul");
		currencyForm = new CurrencyForm(desktop, 
										desktop.query("textbox").query("#txtName").as(Textbox.class), 
										desktop.query("textbox").query("#txtCode").as(Textbox.class),
										desktop.query("checkbox").query("#chkIsSystemCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsPriceReferenceCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsCash").as(Checkbox.class),
										desktop.query("textbox").query("#cmbPaymentProcessor").as(Combobox.class),
										desktop.query("textbox").query("#txtExchangeRateExpression").as(Textbox.class),
										desktop.query("decimalbox").query("#decExchangeRate").as(Decimalbox.class),
										desktop.query("intbox").query("#intPosition").as(Intbox.class));
		try {
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "c5");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "currency5");
			
			desktop.query("textbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("5.60708009");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), "5");
			(desktop.query("checkbox").query("#chkIsSystemCurrency")).as(CheckAgent.class).check(false);	
			(desktop.query("checkbox").query("#chkIsPriceReferenceCurrency")).as(CheckAgent.class).check(true);	
			(desktop.query("checkbox").query("#chkIsCash")).as(CheckAgent.class).check(false);
			ComponentAgent cmbPaymentProcessorAgent = desktop.query("textbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Tropipay");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 2);		
			
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
	@Order(13)
	final void testCase13() {

		SpringUtility.setLoggedUserForTesting("user8@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_currency.zul");
		currencyForm = new CurrencyForm(desktop, 
										desktop.query("textbox").query("#txtName").as(Textbox.class), 
										desktop.query("textbox").query("#txtCode").as(Textbox.class),
										desktop.query("checkbox").query("#chkIsSystemCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsPriceReferenceCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsCash").as(Checkbox.class),
										desktop.query("textbox").query("#cmbPaymentProcessor").as(Combobox.class),
										desktop.query("textbox").query("#txtExchangeRateExpression").as(Textbox.class),
										desktop.query("decimalbox").query("#decExchangeRate").as(Decimalbox.class),
										desktop.query("intbox").query("#intPosition").as(Intbox.class));
		try {
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "c5");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "currency5");
			
			desktop.query("textbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("5.60708009");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), "5");
			(desktop.query("checkbox").query("#chkIsSystemCurrency")).as(CheckAgent.class).check(false);	
			(desktop.query("checkbox").query("#chkIsPriceReferenceCurrency")).as(CheckAgent.class).check(true);	
			(desktop.query("checkbox").query("#chkIsCash")).as(CheckAgent.class).check(false);
			ComponentAgent cmbPaymentProcessorAgent = desktop.query("textbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Tropipay");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 2);		
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(14)
	final void testCase14() {

		SpringUtility.setLoggedUserForTesting("user9@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_currency.zul");
		currencyForm = new CurrencyForm(desktop, 
										desktop.query("textbox").query("#txtName").as(Textbox.class), 
										desktop.query("textbox").query("#txtCode").as(Textbox.class),
										desktop.query("checkbox").query("#chkIsSystemCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsPriceReferenceCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsCash").as(Checkbox.class),
										desktop.query("textbox").query("#cmbPaymentProcessor").as(Combobox.class),
										desktop.query("textbox").query("#txtExchangeRateExpression").as(Textbox.class),
										desktop.query("decimalbox").query("#decExchangeRate").as(Decimalbox.class),
										desktop.query("intbox").query("#intPosition").as(Intbox.class));
		try {
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "c6");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "currency6");
			
			desktop.query("textbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("6");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), "6");
			(desktop.query("checkbox").query("#chkIsSystemCurrency")).as(CheckAgent.class).check(false);	
			(desktop.query("checkbox").query("#chkIsPriceReferenceCurrency")).as(CheckAgent.class).check(false);	
			(desktop.query("checkbox").query("#chkIsCash")).as(CheckAgent.class).check(false);
			ComponentAgent cmbPaymentProcessorAgent = desktop.query("textbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Opennode");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 1);		
			
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
	@Order(15)
	final void testCase15() {

		SpringUtility.setLoggedUserForTesting("user10@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_currency.zul");
		currencyForm = new CurrencyForm(desktop, 
										desktop.query("textbox").query("#txtName").as(Textbox.class), 
										desktop.query("textbox").query("#txtCode").as(Textbox.class),
										desktop.query("checkbox").query("#chkIsSystemCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsPriceReferenceCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsCash").as(Checkbox.class),
										desktop.query("textbox").query("#cmbPaymentProcessor").as(Combobox.class),
										desktop.query("textbox").query("#txtExchangeRateExpression").as(Textbox.class),
										desktop.query("decimalbox").query("#decExchangeRate").as(Decimalbox.class),
										desktop.query("intbox").query("#intPosition").as(Intbox.class));
		try {
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "c6");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "currency6");
			
			desktop.query("textbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("6");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), "6");
			(desktop.query("checkbox").query("#chkIsSystemCurrency")).as(CheckAgent.class).check(false);	
			(desktop.query("checkbox").query("#chkIsPriceReferenceCurrency")).as(CheckAgent.class).check(false);	
			(desktop.query("checkbox").query("#chkIsCash")).as(CheckAgent.class).check(false);
			ComponentAgent cmbPaymentProcessorAgent = desktop.query("textbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Opennode");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 1);		
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(16)
	final void testCase16() {

		SpringUtility.setLoggedUserForTesting("user11@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_currency.zul");
		currencyForm = new CurrencyForm(desktop, 
										desktop.query("textbox").query("#txtName").as(Textbox.class), 
										desktop.query("textbox").query("#txtCode").as(Textbox.class),
										desktop.query("checkbox").query("#chkIsSystemCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsPriceReferenceCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsCash").as(Checkbox.class),
										desktop.query("textbox").query("#cmbPaymentProcessor").as(Combobox.class),
										desktop.query("textbox").query("#txtExchangeRateExpression").as(Textbox.class),
										desktop.query("decimalbox").query("#decExchangeRate").as(Decimalbox.class),
										desktop.query("intbox").query("#intPosition").as(Intbox.class));
		try {
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "c7");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "currency7");
			
			desktop.query("textbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("7.00080009");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), "7");
			(desktop.query("checkbox").query("#chkIsSystemCurrency")).as(CheckAgent.class).check(false);	
			(desktop.query("checkbox").query("#chkIsPriceReferenceCurrency")).as(CheckAgent.class).check(false);	
			(desktop.query("checkbox").query("#chkIsCash")).as(CheckAgent.class).check(true);
			ComponentAgent cmbPaymentProcessorAgent = desktop.query("textbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Tropipay");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 2);		
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(17)
	final void testCase17() {

		SpringUtility.setLoggedUserForTesting("user12@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_currency.zul");
		currencyForm = new CurrencyForm(desktop, 
										desktop.query("textbox").query("#txtName").as(Textbox.class), 
										desktop.query("textbox").query("#txtCode").as(Textbox.class),
										desktop.query("checkbox").query("#chkIsSystemCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsPriceReferenceCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsCash").as(Checkbox.class),
										desktop.query("textbox").query("#cmbPaymentProcessor").as(Combobox.class),
										desktop.query("textbox").query("#txtExchangeRateExpression").as(Textbox.class),
										desktop.query("decimalbox").query("#decExchangeRate").as(Decimalbox.class),
										desktop.query("intbox").query("#intPosition").as(Intbox.class));
		try {
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "c8");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "currency8");
			
			desktop.query("textbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("8");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), "8");
			(desktop.query("checkbox").query("#chkIsSystemCurrency")).as(CheckAgent.class).check(false);	
			(desktop.query("checkbox").query("#chkIsPriceReferenceCurrency")).as(CheckAgent.class).check(false);	
			(desktop.query("checkbox").query("#chkIsCash")).as(CheckAgent.class).check(false);
			ComponentAgent cmbPaymentProcessorAgent = desktop.query("textbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Opennode");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 1);		
			
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
	@Order(18)
	final void testCase18() {

		SpringUtility.setLoggedUserForTesting("user13@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_currency.zul");
		currencyForm = new CurrencyForm(desktop, 
										desktop.query("textbox").query("#txtName").as(Textbox.class), 
										desktop.query("textbox").query("#txtCode").as(Textbox.class),
										desktop.query("checkbox").query("#chkIsSystemCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsPriceReferenceCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsCash").as(Checkbox.class),
										desktop.query("textbox").query("#cmbPaymentProcessor").as(Combobox.class),
										desktop.query("textbox").query("#txtExchangeRateExpression").as(Textbox.class),
										desktop.query("decimalbox").query("#decExchangeRate").as(Decimalbox.class),
										desktop.query("intbox").query("#intPosition").as(Intbox.class));
		try {
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "c8");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "currency8");
			
			desktop.query("textbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("8");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), "8");
			(desktop.query("checkbox").query("#chkIsSystemCurrency")).as(CheckAgent.class).check(false);	
			(desktop.query("checkbox").query("#chkIsPriceReferenceCurrency")).as(CheckAgent.class).check(false);	
			(desktop.query("checkbox").query("#chkIsCash")).as(CheckAgent.class).check(false);
			ComponentAgent cmbPaymentProcessorAgent = desktop.query("textbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Opennode");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 1);		
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(19)
	final void testCase19() {

		SpringUtility.setLoggedUserForTesting("user14@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_currency.zul");
		currencyForm = new CurrencyForm(desktop, 
										desktop.query("textbox").query("#txtName").as(Textbox.class), 
										desktop.query("textbox").query("#txtCode").as(Textbox.class),
										desktop.query("checkbox").query("#chkIsSystemCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsPriceReferenceCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsCash").as(Checkbox.class),
										desktop.query("textbox").query("#cmbPaymentProcessor").as(Combobox.class),
										desktop.query("textbox").query("#txtExchangeRateExpression").as(Textbox.class),
										desktop.query("decimalbox").query("#decExchangeRate").as(Decimalbox.class),
										desktop.query("intbox").query("#intPosition").as(Intbox.class));
		try {
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "c9");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "currency9");
			
			desktop.query("textbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("9");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), "9");
			(desktop.query("checkbox").query("#chkIsSystemCurrency")).as(CheckAgent.class).check(false);	
			(desktop.query("checkbox").query("#chkIsPriceReferenceCurrency")).as(CheckAgent.class).check(false);	
			(desktop.query("checkbox").query("#chkIsCash")).as(CheckAgent.class).check(true);
			ComponentAgent cmbPaymentProcessorAgent = desktop.query("textbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Opennode");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 1);		
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	@Order(20)
	final void testCase20() {

		SpringUtility.setLoggedUserForTesting("user16@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_currency.zul");
		currencyForm = new CurrencyForm(desktop, 
										desktop.query("textbox").query("#txtName").as(Textbox.class), 
										desktop.query("textbox").query("#txtCode").as(Textbox.class),
										desktop.query("checkbox").query("#chkIsSystemCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsPriceReferenceCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsCash").as(Checkbox.class),
										desktop.query("textbox").query("#cmbPaymentProcessor").as(Combobox.class),
										desktop.query("textbox").query("#txtExchangeRateExpression").as(Textbox.class),
										desktop.query("decimalbox").query("#decExchangeRate").as(Decimalbox.class),
										desktop.query("intbox").query("#intPosition").as(Intbox.class));
		try {
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "c91");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "currency91");
			
			desktop.query("textbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("10");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), "91");
			(desktop.query("checkbox").query("#chkIsSystemCurrency")).as(CheckAgent.class).check(false);	
			(desktop.query("checkbox").query("#chkIsPriceReferenceCurrency")).as(CheckAgent.class).check(false);	
			(desktop.query("checkbox").query("#chkIsCash")).as(CheckAgent.class).check(false);
			ComponentAgent cmbPaymentProcessorAgent = desktop.query("textbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Tropipay");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 2);		
			
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
	@Order(21)
	final void testCase21() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_currency.zul");
		currencyForm = new CurrencyForm(desktop, 
										desktop.query("textbox").query("#txtName").as(Textbox.class), 
										desktop.query("textbox").query("#txtCode").as(Textbox.class),
										desktop.query("checkbox").query("#chkIsSystemCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsPriceReferenceCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsCash").as(Checkbox.class),
										desktop.query("textbox").query("#cmbPaymentProcessor").as(Combobox.class),
										desktop.query("textbox").query("#txtExchangeRateExpression").as(Textbox.class),
										desktop.query("decimalbox").query("#decExchangeRate").as(Decimalbox.class),
										desktop.query("intbox").query("#intPosition").as(Intbox.class));
		try {
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "c91");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "currency9");
			
			desktop.query("textbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("11");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), "9");
			(desktop.query("checkbox").query("#chkIsSystemCurrency")).as(CheckAgent.class).check(false);	
			(desktop.query("checkbox").query("#chkIsPriceReferenceCurrency")).as(CheckAgent.class).check(false);	
			(desktop.query("checkbox").query("#chkIsCash")).as(CheckAgent.class).check(false);
			ComponentAgent cmbPaymentProcessorAgent = desktop.query("textbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Tropipay");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 2);		
			
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
	@Order(22)
	final void testCase22() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_currency.zul");
		currencyForm = new CurrencyForm(desktop, 
										desktop.query("textbox").query("#txtName").as(Textbox.class), 
										desktop.query("textbox").query("#txtCode").as(Textbox.class),
										desktop.query("checkbox").query("#chkIsSystemCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsPriceReferenceCurrency").as(Checkbox.class),
										desktop.query("checkbox").query("#chkIsCash").as(Checkbox.class),
										desktop.query("textbox").query("#cmbPaymentProcessor").as(Combobox.class),
										desktop.query("textbox").query("#txtExchangeRateExpression").as(Textbox.class),
										desktop.query("decimalbox").query("#decExchangeRate").as(Decimalbox.class),
										desktop.query("intbox").query("#intPosition").as(Intbox.class));
		try {
			currencyForm.setComponentValue(currencyForm.getTxtCode(), "c9");
			currencyForm.setComponentValue(currencyForm.getTxtName(), "currency91");
			
			desktop.query("textbox").query("#txtExchangeRateExpression").as(InputAgent.class).type("11");
			
			currencyForm.setComponentValue(currencyForm.getIntPosition(), "91");
			(desktop.query("checkbox").query("#chkIsSystemCurrency")).as(CheckAgent.class).check(false);	
			(desktop.query("checkbox").query("#chkIsPriceReferenceCurrency")).as(CheckAgent.class).check(false);	
			(desktop.query("checkbox").query("#chkIsCash")).as(CheckAgent.class).check(false);
			ComponentAgent cmbPaymentProcessorAgent = desktop.query("textbox").query("#cmbPaymentProcessor");
			InputAgent cmbPaymentProcessorInputAgent = cmbPaymentProcessorAgent.as(InputAgent.class);
			cmbPaymentProcessorInputAgent.typing("Tropipay");
			currencyForm.setComponentValue(currencyForm.getCmbPaymentProcessor(), 2);		
			
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