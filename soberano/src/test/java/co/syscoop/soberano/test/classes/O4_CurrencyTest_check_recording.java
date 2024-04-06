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
import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Intbox;
import co.syscoop.soberano.test.helper.CurrencyActionTest;
import co.syscoop.soberano.util.SpringUtility;

@Order(4)

//TODO: enable test
@Disabled

class O4_CurrencyTest_check_recording extends CurrencyActionTest {
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		Zats.init("./src/main/webapp");		
		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/currencies.zul");
		
		cmbIntelliSearchAgent = desktop.query("combobox");
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
	final void testCase23() {
		
		try {
			checkCurrency("currency1",
						"c1",
						false,
						false,
						true,
						1,
						
						//it was created as system currency, so exchange rate is forcedly set to 1
						new BigDecimal(1),
						1);
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
			checkCurrency("currency2",
						"c2",
						false,
						false,
						true,
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
	final void testCase25() {
		
		try {
			checkCurrency("currency3",
						"c3",
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
	final void testCase26() {
		
		try {
			checkCurrency("currency4",
						"c4",
						false,
						false,
						true,
						1,						
						new BigDecimal(4),
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
	final void testCase27() {
		
		try {
			checkCurrency("currency5",
						"c5",
						false,
						true,
						false,
						2,
						new BigDecimal(5.60708009),
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
	final void testCase28() {
		
		try {
			checkCurrency("currency6",
						"c6",
						false,
						false,
						false,
						1,
						new BigDecimal(6),
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
	final void testCase29() {
		
		try {
			checkCurrency("currency7",
						"c7",
						false,
						false,
						true,
						2,
						new BigDecimal(7.00080009),
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
	final void testCase30() {
		
		try {
			checkCurrency("currency8",
						"c8",
						false,
						false,
						false,
						1,
						new BigDecimal(8),
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
	final void testCase31() {
		
		try {
			checkCurrency("currency9",
						"c9",
						false,
						false,
						true,
						1,
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
}
