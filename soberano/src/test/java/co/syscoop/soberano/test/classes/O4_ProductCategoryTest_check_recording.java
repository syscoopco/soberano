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
import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.test.helper.ProductCategoryActionTest;
import co.syscoop.soberano.util.SpringUtility;

@Order(4)

@Disabled

class O4_ProductCategoryTest_check_recording extends ProductCategoryActionTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		Library.setProperty(Attributes.PREFERRED_LOCALE, "en"); //needed due to translated captions according 
		//to runtime locale not available under 
		//testing environment
		
		Zats.init("./src/main/webapp");		
		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/product_categories.zul");
		
		cmbIntelliSearchAgent = desktop.query("center").query("combobox");
		cmbIntelliSearch = cmbIntelliSearchAgent.as(Combobox.class);		
		txtName = cmbIntelliSearchAgent.query("#incDetails").query("#txtName").as(Textbox.class);
		intPosition = cmbIntelliSearchAgent.query("#incDetails").query("#intPosition").as(Intbox.class);
		chkDisabled = cmbIntelliSearchAgent.query("#incDetails").query("#chkDisabled").as(Checkbox.class);
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
	final void testCase25() {
		
		try {
			checkProductCategory("cat1",
								1,
								false);
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
			checkProductCategory("cat2",
								2,
								false /*chkDisabled is not visible true*/);
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
			checkProductCategory("cat3",
								3,
								false);
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
			checkProductCategory("cat4",
								4,
								false /*chkDisabled is not visible true*/);
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
			checkProductCategory("cat5",
								5,
								false);
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
			checkProductCategory("cat6",
								6,
								false /*chkDisabled is not visible true*/);
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
			checkProductCategory("cat7",
								7,
								false);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase32() {
		
		try {
			checkProductCategory("cat8",
								8,
								false /*chkDisabled is not visible true*/);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase33() {
		
		try {
			checkProductCategory("cat9",
								9,
								false);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase34() {
		
		try {
			checkProductCategory("cat10",
								10,
								false /*chkDisabled is not visible true*/);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
}
