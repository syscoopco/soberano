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
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.test.helper.ProductActionTest;
import co.syscoop.soberano.util.SpringUtility;

@Order(15)

//TODO: enable test
////@Disabled

class OO15_ProductTest_check_modifying extends ProductActionTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		Library.setProperty(Attributes.PREFERRED_LOCALE, "en"); //needed due to translated captions according 
		//to runtime locale not available under 
		//testing environment
		
		Zats.init("./src/main/webapp");		
		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/products.zul");
		
		cmbIntelliSearchAgent = desktop.query("combobox");
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
		Zats.end();
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	final void testCase17() {
		
		try {
			checkProduct("mproduct1",
						"mp1",
						2,
						new BigDecimal(2000),
						1003,
						new BigDecimal(6.789).multiply(new BigDecimal(4.34567809)),
						new BigDecimal(6.789),
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
	final void testCase18() {
		
		try {
			checkProduct("mproduct2",
						"mp2",
						1,
						new BigDecimal(34.85746),
						1004,
						new BigDecimal(5.0980001).multiply(new BigDecimal(4.34567809)),
						new BigDecimal(5.0980001),
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
	final void testCase19() {
		
		try {
			checkProduct("mproduct3",
						"mp3",
						8,
						new BigDecimal(3.096886),
						1004,
						new BigDecimal(0.0006).multiply(new BigDecimal(4.34567809)),
						new BigDecimal(0.0006),
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
	final void testCase20() {
		
		try {
			checkProduct("mproduct4",
						"mp4",
						5,
						new BigDecimal(0.000567),
						1004,
						new BigDecimal(45.6789),
						new BigDecimal(45.6789).divide(new BigDecimal(4.34567809), 23, RoundingMode.HALF_DOWN),						
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
	final void testCase21() {
		
		try {
			checkProduct("mproduct5",
						"mp5",
						1,
						new BigDecimal(5),
						1007,
						new BigDecimal(876.123),
						new BigDecimal(876.123).divide(new BigDecimal(4.34567809), 23, RoundingMode.HALF_DOWN),						
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
	final void testCase22() {
		
		try {
			checkProduct("mproduct6",
						"mp6",
						2,
						new BigDecimal(345),
						1007,
						new BigDecimal(3.45),
						new BigDecimal(3.45).divide(new BigDecimal(4.34567809), 23, RoundingMode.HALF_DOWN),						
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
	final void testCase23() {
		
		try {
			checkProduct("mproduct7",
						"mp7",
						1,
						new BigDecimal(6),
						1008,
						new BigDecimal(8.00998877).multiply(new BigDecimal(4.34567809)),
						new BigDecimal(8.00998877),						
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
			checkProduct("mproduct8",
						"mp8",
						6,
						new BigDecimal(45656),
						1008,
						new BigDecimal(0.0005).multiply(new BigDecimal(4.34567809)),
						new BigDecimal(0.0005),						
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
			checkProduct("mproduct9",
						"mp9",
						1,
						new BigDecimal(8),
						1008,
						new BigDecimal(1.09878901).multiply(new BigDecimal(4.34567809)),
						new BigDecimal(1.09878901),						
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
}
