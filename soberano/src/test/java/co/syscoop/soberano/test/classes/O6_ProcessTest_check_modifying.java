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

import co.syscoop.soberano.test.helper.ProcessActionTest;
import co.syscoop.soberano.test.helper.ProcessForm;
import co.syscoop.soberano.util.SpringUtility;

@Order(6)

//TODO: enable test
////@Disabled

class O6_ProcessTest_check_modifying extends ProcessActionTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		Zats.init("./src/main/webapp");		
		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/processes.zul");
		
		cmbIntelliSearchAgent = desktop.query("combobox");
		cmbIntelliSearch = cmbIntelliSearchAgent.as(Combobox.class);		
		txtName = cmbIntelliSearchAgent.query("#incDetails").query("#txtName").as(Textbox.class);
		decFixedCost = cmbIntelliSearchAgent.query("#incDetails").query("#decFixedCost").as(Decimalbox.class);
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
	final void testCase20() {
		
		try {
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");		
			checkProcess(processForm,
							"mpr1",
							new BigDecimal(2.123));
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
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");		
			checkProcess(processForm,
							"mpr2",
							new BigDecimal(3.456));
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
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");		
			checkProcess(processForm,
							"mpr3",
							new BigDecimal(4.789));
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
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");		
			checkProcess(processForm,
							"mpr4",
							new BigDecimal(5.987));
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
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");		
			checkProcess(processForm,
							"mpr5",
							new BigDecimal(6.654));
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
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");		
			checkProcess(processForm,
							"mpr6",
							new BigDecimal(7.321));
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
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");		
			checkProcess(processForm,
							"mpr7",
							new BigDecimal(8.111));
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
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");		
			checkProcess(processForm,
							"mpr8",
							new BigDecimal(9.222));
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
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");		
			checkProcess(processForm,
							"mpr9",
							new BigDecimal(10.333));
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
			ProcessForm processForm = setFormComponents("user1@soberano.syscoop.co", "processes.zul");		
			checkProcess(processForm,
							"mpr10",
							new BigDecimal(11.444));
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
}