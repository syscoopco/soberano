package co.syscoop.soberano.test.classes;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Textbox;
import co.syscoop.soberano.test.helper.ProductActionTest;
import co.syscoop.soberano.util.SpringUtility;

@Order(13)

@Disabled

class OO13_ProductTest_check_recording extends ProductActionTest {
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		Library.setProperty(Attributes.PREFERRED_LOCALE, "en"); //needed due to translated captions according 
		//to runtime locale not available under 
		//testing environment
		
		//Zats.init("./src/main/webapp");		
		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/products.zul");
		
		cmbIntelliSearchAgent = desktop.query("center").query("combobox");
		cmbIntelliSearch = cmbIntelliSearchAgent.as(Combobox.class);
		txtCode = cmbIntelliSearchAgent.query("#incDetails").query("#txtCode").as(Textbox.class);
		txtName = cmbIntelliSearchAgent.query("#incDetails").query("#txtName").as(Textbox.class);
		cmbUnit = cmbIntelliSearchAgent.query("#incDetails").query("#cmbUnit").as(Combobox.class);
		decMinimumInventoryLevel = cmbIntelliSearchAgent.query("#incDetails").query("#decMinimumInventoryLevel").as(Decimalbox.class);
		cmbCategory = cmbIntelliSearchAgent.query("#incDetails").query("#cmbCategory").as(Combobox.class);
		decPrice = cmbIntelliSearchAgent.query("#incDetails").query("#decPrice").as(Decimalbox.class);
		decReferencePrice = cmbIntelliSearchAgent.query("#incDetails").query("#decReferencePrice").as(Decimalbox.class);
		decReferencePriceExchangeRate = cmbIntelliSearchAgent.query("#incDetails").query("#decReferencePriceExchangeRate").as(Decimalbox.class);
		cmbCostCenter = cmbIntelliSearchAgent.query("#incDetails").query("#cmbCostCenter").as(Combobox.class);
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
	final void testCase23() {
		
		try {
			checkProduct("product1",
						"p1",
						1,
						new BigDecimal(1000),
						1001,
						new BigDecimal(1.001),
						new BigDecimal(1.001).divide(new BigDecimal(4.34567809), 23, RoundingMode.HALF_DOWN),
						new BigDecimal(4.34567809),
						1004);
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
			checkProduct("product2",
						"p2",
						2,
						new BigDecimal(50.01234567),
						1002,
						new BigDecimal(2.00000002),
						new BigDecimal(2.00000002).divide(new BigDecimal(4.34567809), 23, RoundingMode.HALF_DOWN),
						new BigDecimal(4.34567809),
						1005);
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
			checkProduct("product3",
						"p3",
						5,
						new BigDecimal(1234.5678),
						1003,
						new BigDecimal(3.0123),
						new BigDecimal(3.0123).divide(new BigDecimal(4.34567809), 23, RoundingMode.HALF_DOWN),
						new BigDecimal(4.34567809),
						1006);
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
			checkProduct("product4",
						"p4",
						8,
						new BigDecimal(6543.21098),
						1004,
						new BigDecimal(987.654).multiply(new BigDecimal(4.34567809)),
						new BigDecimal(987.654),
						new BigDecimal(4.34567809),
						1007);
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
			checkProduct("product5",
						"p5",
						7,
						new BigDecimal(7654321),
						1005,
						new BigDecimal(876.123).multiply(new BigDecimal(4.34567809)),
						new BigDecimal(876.123),
						new BigDecimal(4.34567809),
						1008);
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
			checkProduct("product6",
						"p6",
						1,
						new BigDecimal(12),
						1006,
						new BigDecimal(3.45).multiply(new BigDecimal(4.34567809)),
						new BigDecimal(3.45),
						new BigDecimal(4.34567809),
						1009);
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
			checkProduct("product7",
						"p7",
						6,
						new BigDecimal(2),
						1007,
						new BigDecimal(8.00998877),
						new BigDecimal(8.00998877).divide(new BigDecimal(4.34567809), 23, RoundingMode.HALF_DOWN),
						new BigDecimal(4.34567809),
						1010);
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
			checkProduct("product8",
						"p8",
						3,
						new BigDecimal(43526100),
						1008,
						new BigDecimal(0.0005),
						new BigDecimal(0.0005).divide(new BigDecimal(4.34567809), 23, RoundingMode.HALF_DOWN),
						new BigDecimal(4.34567809),
						1004);
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
			checkProduct("product9",
						"p9",
						1,
						new BigDecimal(12),
						1008,
						new BigDecimal(1.09878901),
						new BigDecimal(1.09878901).divide(new BigDecimal(4.34567809), 23, RoundingMode.HALF_DOWN),
						new BigDecimal(4.34567809),
						1005);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
}
