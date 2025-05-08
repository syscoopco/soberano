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
import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.test.helper.CurrencyActionTest;
import co.syscoop.soberano.util.SpringUtility;

@Order(6)

//@Disabled

class O6_CurrencyTest_check_modifying extends CurrencyActionTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		Library.setProperty(Attributes.PREFERRED_LOCALE, "en"); //needed due to translated captions according 
		//to runtime locale not available under 
		//testing environment
		
		Zats.init("./src/main/webapp");		
		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/currencies.zul");
		
		cmbIntelliSearchAgent = desktop.query("center").query("combobox");
		cmbIntelliSearch = cmbIntelliSearchAgent.as(Combobox.class);
		txtCode = cmbIntelliSearchAgent.query("#incDetails").query("#txtCode").as(Textbox.class);
		txtName = cmbIntelliSearchAgent.query("#incDetails").query("#txtName").as(Textbox.class);		
		chkIsSystemCurrency = cmbIntelliSearchAgent.query("#incDetails").query("#chkIsSystemCurrency").as(Checkbox.class);
		chkIsPriceReferenceCurrency = cmbIntelliSearchAgent.query("#incDetails").query("#chkIsPriceReferenceCurrency").as(Checkbox.class);
		chkIsCash = cmbIntelliSearchAgent.query("#incDetails").query("#chkIsCash").as(Checkbox.class);
		cmbPaymentProcessor = cmbIntelliSearchAgent.query("#incDetails").query("#cmbPaymentProcessor").as(Combobox.class);
		txtExchangeRateExpression = cmbIntelliSearchAgent.query("#incDetails").query("#txtExchangeRateExpression").as(Textbox.class);
		decExchangeRate = cmbIntelliSearchAgent.query("#incDetails").query("#decExchangeRate").as(Decimalbox.class);		
		intPosition = cmbIntelliSearchAgent.query("#incDetails").query("#intPosition").as(Intbox.class);
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
	final void testCase18() {
		
		try {
			checkCurrency("mcurrency1",
						"mc1",
						false,
						false,
						false,
						2,
						
						//it was created as system currency, so exchange rate is forcedly set to 1
						new BigDecimal(1),
						2);
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
			checkCurrency("mcurrency2",
						"mc2",
						true,
						false,
						false,
						1,
						
						//it was created as system currency, so exchange rate is forcedly set to 1
						new BigDecimal(1),
						3);
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
			checkCurrency("mcurrency3",
						"mc3",
						false,
						true,
						true,
						2,
						new BigDecimal(4.34567809),
						4);
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
			checkCurrency("mcurrency4",
						"mc4",
						false,
						false,
						false,
						2,						
						new BigDecimal(5),
						5);
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
			checkCurrency("mcurrency5",
						"mc5",
						false,
						false,
						true,
						1,
						new BigDecimal(6.67800009),
						6);
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
			checkCurrency("mcurrency6",
						"mc6",
						false,
						false,
						true,
						2,
						new BigDecimal(7),
						7);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase24() {
		
		try {
			checkCurrency("mcurrency7",
						"mc7",
						false,
						false,
						false,
						1,
						new BigDecimal(8.00000089),
						8);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase25() {
		
		try {
			checkCurrency("mcurrency8",
						"mc8",
						false,
						false,
						true,
						2,
						new BigDecimal(9),
						9);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase26() {
		
		try {
			checkCurrency("mcurrency9",
						"mc9",
						false,
						false,
						false,
						2,
						new BigDecimal(10),
						10);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
}
