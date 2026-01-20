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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Textbox;
import co.syscoop.soberano.test.helper.CustomerActionTest;
import co.syscoop.soberano.util.SpringUtility;

@Order(4)

@Disabled

class O4_CustomerTest_check_recording extends CustomerActionTest {
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		Library.setProperty(Attributes.PREFERRED_LOCALE, "en"); //needed due to translated captions according 
		//to runtime locale not available under 
		//testing environment
		
		//Zats.init("./src/main/webapp");		
		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/customers.zul");
		
		cmbIntelliSearchAgent = desktop.query("center").query("combobox");
		cmbIntelliSearch = cmbIntelliSearchAgent.as(Combobox.class);		
		txtFirstName = cmbIntelliSearchAgent.query("#incDetails").query("#txtFirstName").as(Textbox.class);
		txtLastName = cmbIntelliSearchAgent.query("#incDetails").query("#txtLastName").as(Textbox.class);
		decDiscount = cmbIntelliSearchAgent.query("#incDetails").query("#decDiscount").as(Decimalbox.class);
		txtPhoneNumber = cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#txtPhoneNumber").as(Textbox.class);
		txtEmailAddress = cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#txtEmailAddress").as(Textbox.class);
		txtAddress = cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#txtAddress").as(Textbox.class);
		cmbPostalCode = cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#cmbPostalCode").as(Combobox.class);
		txtTown = cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#txtTown").as(Textbox.class);
		txtCity = cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#txtCity").as(Textbox.class);
		cmbCountry = cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#cmbCountry").as(Combobox.class);
		cmbProvince = cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#cmbProvince").as(Combobox.class);
		cmbMunicipality = cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#cmbMunicipality").as(Combobox.class);
		dblLatitude = cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#dblLatitude").as(Doublebox.class);
		dblLongitude = cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#dblLongitude").as(Doublebox.class);
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
	final void testCase15() {
		
		try {
			checkCustomer("Customer1fn",
						"Customer1ln",
						new BigDecimal(0),
						"55555556",
						"c1@soberano.syscoop.co",
						"Cuba",
						"La Habana",
						"Plaza de la Revolución",
						"Address 1",
						"Pc 1",
						"Town 1",
						"City 1",
						1.00,
						-1.00);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase16() {
		
		try {
			checkCustomer("Customer2fn",
						"Customer2ln",
						new BigDecimal(10),
						"55555556",
						"c2@soberano.syscoop.co",
						"Cuba",
						"La Habana",
						"Plaza de la Revolución",
						"Address 2",
						"Pc 2",
						"Town 2",
						"City 2",
						2.00,
						-2.00);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase17() {
		
		try {
			checkCustomer("Customer3fn",
						"Customer3ln",
						new BigDecimal(0),
						"55555557",
						"c3@soberano.syscoop.co",
						"Cuba",
						"Pinar del Río",
						"Viñales",
						"Address 3",
						"Pc 3",
						"Town 3",
						"City 3",
						3.00,
						-3.00);
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
			checkCustomer("Customer4fn",
						"Customer4ln",
						new BigDecimal(10.5),
						"55555558",
						"c4@soberano.syscoop.co",
						"Cuba",
						"Matanzas",
						"Jagüey Grande",
						"Address 4",
						"Pc 4",
						"Town 4",
						"City 4",
						4.00,
						-4.00);
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
			checkCustomer("Customer5fn",
						"Customer5ln",
						new BigDecimal(0),
						"55555559",
						"c5@soberano.syscoop.co",
						"Cuba",
						"Villa Clara",
						"Santa Clara",
						"Address 5",
						"Pc 5",
						"Town 5",
						"City 5",
						5.00,
						-5.00);
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
			checkCustomer("Customer6fn",
						"Customer6ln",
						new BigDecimal(12.5),
						"55555560",
						"c6@soberano.syscoop.co",
						"Cuba",
						"Isla de la Juventud",
						"Isla de la Juventud",
						"Address 6",
						"Pc 6",
						"Town 6",
						"City 6",
						6.00,
						-6.00);
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
			checkCustomer("Customer7fn",
						"Customer7ln",
						new BigDecimal(0),
						"55555561",
						"c7@soberano.syscoop.co",
						"Cuba",
						"Artemisa",
						"Artemisa",
						"Address 7",
						"Pc 7",
						"Town 7",
						"City 7",
						7.00,
						-7.00);
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
			checkCustomer("Customer8fn",
						"Customer8ln",
						new BigDecimal(0),
						"55555562",
						"c8@soberano.syscoop.co",
						"Cuba",
						"Artemisa",
						"Artemisa",
						"Address 8",
						"Pc 8",
						"Town 8",
						"City 8",
						8.00,
						-8.00);
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
			checkCustomer("Customer9fn",
						"Customer9ln",
						new BigDecimal(0),
						"55555563",
						"c9@soberano.syscoop.co",
						"Cuba",
						"Mayabeque",
						"Santa Cruz del Norte",
						"Address 9",
						"Pc 9",
						"Town 9",
						"City 9",
						9.00,
						-9.00);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
}
