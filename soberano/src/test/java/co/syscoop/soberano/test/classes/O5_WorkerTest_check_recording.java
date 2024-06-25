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
import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Treechildren;

import co.syscoop.soberano.test.helper.WorkerActionTest;
import co.syscoop.soberano.util.SpringUtility;

@Order(5)

//@Disabled

class O5_WorkerTest_check_recording extends WorkerActionTest {
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		Zats.init("./src/main/webapp");		
		SpringUtility.setLoggedUserForTesting("systemadmin@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/workers.zul");
		
		cmbIntelliSearchAgent = desktop.query("center").query("combobox");
		cmbIntelliSearch = cmbIntelliSearchAgent.as(Combobox.class);		
		txtUserName = cmbIntelliSearchAgent.query("#incDetails").query("#txtUserName").as(Textbox.class);
		txtFirstName = cmbIntelliSearchAgent.query("#incDetails").query("#txtFirstName").as(Textbox.class);
		txtLastName = cmbIntelliSearchAgent.query("#incDetails").query("#txtLastName").as(Textbox.class);
		tchdnResponsibilities = cmbIntelliSearchAgent.query("#incDetails").query("#tchdnResponsibilities").as(Treechildren.class);
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
		Zats.end();
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	final void testCase33() {
		
		try {
			checkUser("accountant@soberano.syscoop.co",
						"accountant",
						"accountant",
						new String[] {"Accountant"},
						"50155555",
						"accountant@soberano.syscoop.co",
						"Cuba",
						"La Habana",
						"Plaza de la Revolución",
						"Address 1",
						"Pc 1",
						"Town 1",
						"City 1",
						1.00,
						1.00);
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
			checkUser("auditor@soberano.syscoop.co",
						"auditor",
						"auditor",
						new String[] {"Auditor"},
						"50255555",
						"auditor@soberano.syscoop.co",
						"Cuba",
						"Pinar del Río",
						"Viñales",
						"Address 2",
						"Pc 2",
						"Town 2",
						"City 2",
						2.00,
						2.00);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase35() {
		
		try {
			checkUser("catalogMaintainer@soberano.syscoop.co",
						"catalogMaintainer",
						"catalogMaintainer",
						new String[] {"Catalog maintainer"},
						"50355555",
						"catalogMaintainer@soberano.syscoop.co",
						"Cuba",
						"Matanzas",
						"Jagüey Grande",
						"Address 3",
						"Pc 3",
						"Town 3",
						"City 3",
						3.00,
						3.00);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase36() {
		
		try {
			checkUser("checker@soberano.syscoop.co",
						"checker",
						"checker",
						new String[] {"Checker"},
						"50455555",
						"checker@soberano.syscoop.co",
						"Cuba",
						"Villa Clara",
						"Santa Clara",
						"Address 4",
						"Pc 4",
						"Town 4",
						"City 4",
						4.00,
						4.00);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase37() {
		
		try {
			checkUser("communityManager@soberano.syscoop.co",
						"communityManager",
						"communityManager",
						new String[] {"Community manager"},
						"50555555",
						"communityManager@soberano.syscoop.co",
						"Cuba",
						"Isla de la Juventud",
						"Isla de la Juventud",
						"Address 5",
						"Pc 5",
						"Town 5",
						"City 5",
						5.00,
						5.00);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase38() {
		
		try {
			checkUser("manager@soberano.syscoop.co",
						"manager",
						"manager",
						new String[] {"Manager"},
						"50655555",
						"manager@soberano.syscoop.co",
						"Cuba",
						"Artemisa",
						"Artemisa",
						"Address 6",
						"Pc 6",
						"Town 6",
						"City 6",
						6.00,
						6.00);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase39() {
		
		try {
			checkUser("procurementWorker@soberano.syscoop.co",
						"procurementWorker",
						"procurementWorker",
						new String[] {"Procurement worker"},
						"50755555",
						"procurementWorker@soberano.syscoop.co",
						"Cuba",
						"Mayabeque",
						"Santa Cruz del Norte",
						"Address 7",
						"Pc 7",
						"Town 7",
						"City 7",
						7.00,
						7.00);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase40() {
		
		try {
			checkUser("salesclerk@soberano.syscoop.co",
						"salesclerk",
						"salesclerk",
						new String[] {"Salesclerk"},
						"50855555",
						"salesclerk@soberano.syscoop.co",
						"Cuba",
						"Guantánamo",
						"Guantánamo",
						"Address 8",
						"Pc 8",
						"Town 8",
						"City 8",
						8.00,
						8.00);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase41() {
		
		try {
			checkUser("shiftManager@soberano.syscoop.co",
						"shiftManager",
						"shiftManager",
						new String[] {"Shift manager"},
						"50955555",
						"shiftManager@soberano.syscoop.co",
						"Cuba",
						"Santiago de Cuba",
						"Santiago de Cuba",
						"Address 9",
						"Pc 9",
						"Town 9",
						"City 9",
						9.00,
						9.00);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase42() {
		
		try {
			checkUser("storekeeper@soberano.syscoop.co",
						"storekeeper",
						"storekeeper",
						new String[] {"Storekeeper"},
						"51055555",
						"storekeeper@soberano.syscoop.co",
						"Cuba",
						"Granma",
						"Pilón",
						"Address 10",
						"Pc 10",
						"Town 10",
						"City 10",
						10.00,
						10.00);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase43() {
		
		try {
			checkUser("systemAdmin@soberano.syscoop.co",
						"systemAdmin",
						"systemAdmin",
						new String[] {"System admin"},
						"51155555",
						"systemAdmin@soberano.syscoop.co",
						"Cuba",
						"Holguín",
						"Holguín",
						"Address 11",
						"Pc 11",
						"Town 11",
						"City 11",
						11.00,
						11.00);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase44() {
		
		try {
			checkUser("workshop1Worker@soberano.syscoop.co",
						"workshop1Worker",
						"workshop1Worker",
						new String[] {"Workshop 1 worker"},
						"51255555",
						"workshop1Worker@soberano.syscoop.co",
						"Cuba",
						"Las Tunas",
						"Majibacoa",
						"Address 12",
						"Pc 12",
						"Town 12",
						"City 12",
						12.00,
						12.00);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase45() {
		
		try {
			checkUser("workshop2Worker@soberano.syscoop.co",
						"workshop2Worker",
						"workshop2Worker",
						new String[] {"Workshop 2 worker"},
						"51355555",
						"workshop2Worker@soberano.syscoop.co",
						"Cuba",
						"Camagüey",
						"Camagüey",
						"Address 13",
						"Pc 13",
						"Town 13",
						"City 13",
						13.00,
						13.00);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
}
