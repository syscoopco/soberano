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

@Order(6)

//@Disabled

class O6_CustomerTest_check_modifying extends CustomerActionTest {
	
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
	final void testCase11() {
		
		try {
			checkCustomer("Customer1fnmod",
						"Customer1lnmod",
						new BigDecimal(0),
						"5355555556",
						"c1mod@soberano.syscoop.co",
						"Cuba",
						"Pinar del Río",
						"Viñales",
						"Addressmod 1",
						"Pcmod 1",
						"Townmod 1",
						"Citymod 1",
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
	final void testCase12() {
		
		try {
			checkCustomer("Customer2fnmod",
						"Customer2lnmod",
						new BigDecimal(15),
						"5355555556",
						"c2mod@soberano.syscoop.co",
						"Cuba",
						"La Habana",
						"Plaza de la Revolución",
						"Addressmod 2",
						"Pcmod 2",
						"Townmod 2",
						"Citymod 2",
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
	final void testCase13() {
		
		try {
			checkCustomer("Customer3fnmod",
						"Customer3lnmod",
						new BigDecimal(0),
						"5355555557",
						"c3mod@soberano.syscoop.co",
						"Cuba",
						"Pinar del Río",
						"Viñales",
						"Addressmod 3",
						"Pcmod 3",
						"Townmod 3",
						"Citymod 3",
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
	final void testCase14() {
		
		try {
			checkCustomer("Customer4fnmod",
						"Customer4lnmod",
						new BigDecimal(5.6),
						"5355555558",
						"c4mod@soberano.syscoop.co",
						"Cuba",
						"Matanzas",
						"Jagüey Grande",
						"Addressmod 4",
						"Pcmod 4",
						"Townmod 4",
						"Citymod 4",
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
	final void testCase15() {
		
		try {
			checkCustomer("Customer5fnmod",
						"Customer5lnmod",
						new BigDecimal(0),
						"5355555559",
						"c5mod@soberano.syscoop.co",
						"Cuba",
						"Villa Clara",
						"Santa Clara",
						"Addressmod 5",
						"Pcmod 5",
						"Townmod 5",
						"Citymod 5",
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
	final void testCase16() {
		
		try {
			checkCustomer("Customer6fnmod",
						"Customer6lnmod",
						new BigDecimal(10.5),
						"5355555560",
						"c6mod@soberano.syscoop.co",
						"Cuba",
						"Isla de la Juventud",
						"Isla de la Juventud",
						"Addressmod 6",
						"Pcmod 6",
						"Townmod 6",
						"Citymod 6",
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
	final void testCase17() {
		
		try {
			checkCustomer("Customer7fnmod",
						"Customer7lnmod",
						new BigDecimal(0),
						"5355555561",
						"c7mod@soberano.syscoop.co",
						"Cuba",
						"Artemisa",
						"Artemisa",
						"Addressmod 7",
						"Pcmod 7",
						"Townmod 7",
						"Citymod 7",
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
	
	@Test
	final void testCase18() {
		
		try {
			checkCustomer("Customer8fnmod",
						"Customer8lnmod",
						new BigDecimal(0),
						"5355555562",
						"c8mod@soberano.syscoop.co",
						"Cuba",
						"La Habana",
						"Plaza de la Revolución",
						"Addressmod 8",
						"Pcmod 8",
						"Townmod 8",
						"Citymod 8",
						10.00,
						-10.00);
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
			checkCustomer("Customer9fnmod",
						"Customer9lnmod",
						new BigDecimal(10),
						"5355555563",
						"c9mod@soberano.syscoop.co",
						"Cuba",
						"Mayabeque",
						"Santa Cruz del Norte",
						"Addressmod 9",
						"Pcmod 9",
						"Townmod 9",
						"Citymod 9",
						11.00,
						-11.00);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
}
