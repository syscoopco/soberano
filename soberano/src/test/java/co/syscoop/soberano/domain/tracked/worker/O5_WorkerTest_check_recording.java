package co.syscoop.soberano.domain.tracked.worker;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.zkoss.zats.mimic.ComponentAgent;
import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zats.mimic.operation.InputAgent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;

import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.util.SpringUtility;

@Order(4)
class O5_WorkerTest_check_recording {
	
	private static ComponentAgent cmbIntelliSearchAgent = null;
	private static Combobox cmbIntelliSearch = null;
	private static Textbox txtUserName = null;
	private static Textbox txtFirstName = null;
	private static Textbox txtLastName = null;
	private static Treechildren tchdnResponsibilities = null;
	private static Textbox txtPhoneNumber = null;
	private static Textbox txtEmailAddress = null;
	private static Textbox txtAddress = null;
	private static Textbox txtPostalCode = null;
	private static Textbox txtTown = null;
	private static Textbox txtCity = null;
	private static Combobox cmbCountry = null;
	private static Combobox cmbProvince = null;
	private static Combobox cmbMunicipality = null;
	private static Doublebox dblLatitude = null;
	private static Doublebox dblLongitude = null;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		Zats.init("./src/main/webapp");
		SpringUtility.setLoggedUserForTesting("systemadmin@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/workers.zul");
		cmbIntelliSearchAgent = desktop.query("combobox");
		cmbIntelliSearch = cmbIntelliSearchAgent.as(Combobox.class);		
		txtUserName = cmbIntelliSearchAgent.query("#incDetails").query("#txtUserName").as(Textbox.class);
		txtFirstName = cmbIntelliSearchAgent.query("#incDetails").query("#txtFirstName").as(Textbox.class);
		txtLastName = cmbIntelliSearchAgent.query("#incDetails").query("#txtLastName").as(Textbox.class);
		tchdnResponsibilities = cmbIntelliSearchAgent.query("#incDetails").query("#tchdnResponsibilities").as(Treechildren.class);
		txtPhoneNumber = cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#txtPhoneNumber").as(Textbox.class);
		txtEmailAddress = cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#txtEmailAddress").as(Textbox.class);
		txtAddress = cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#txtAddress").as(Textbox.class);
		txtPostalCode = cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#txtPostalCode").as(Textbox.class);
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
	
	private void selectComboitemByLabel(Combobox comp, String label) {
		
		try {
			for (Component co : comp.getChildren()) {
				Comboitem item = (Comboitem) co;
				if (((DomainObject) item.getValue()).getName().toLowerCase().equals(label.toLowerCase())) {
					comp.setSelectedItem(item);
					break;
				}
			}
		} 
		catch(Exception ex) 
		{
			/*This is to, under testing, avoid halting cause java.lang.IllegalStateException
			with detailMessage: Components can be accessed only in event listeners.
			Line 305 in ZK UiEngineImpl.java file*/
		}
	}
	
	private void checkUser(String userName,
							String firstName,
							String lastName,
							String responsibility,
							String phoneNumber,
							String emailAddress,
							String country,
							String province,
							String municipality,
							String address,
							String postalCode,
							String town,
							String city,
							Double latitude,
							Double longitude) {
		
		InputAgent cmbIntelliSearchInputAgent = cmbIntelliSearchAgent.as(InputAgent.class);
		String qualifiedName = firstName + " " + lastName + " : " + userName;
		cmbIntelliSearchInputAgent.typing(qualifiedName);
		selectComboitemByLabel(cmbIntelliSearch, qualifiedName);		
		cmbIntelliSearchAgent.click(); 	//needed to trigger cmbIntelliSearch's onClick event under testing
		
		assertEquals(txtUserName.getText().toLowerCase(), userName.toLowerCase(), "Wrong username shown for user " +  qualifiedName);
		assertEquals(txtFirstName.getText().toLowerCase(), firstName.toLowerCase(), "Wrong firstName shown for user " +  qualifiedName);
		assertEquals(txtLastName.getText().toLowerCase(), lastName.toLowerCase(), "Wrong lastName shown for user " +  qualifiedName);
		
		assertEquals(tchdnResponsibilities.getChildren().size(), 1, "None or more than one assigments for user " +  qualifiedName + " One and only one is exṕected.");
		assertEquals(((Treeitem) tchdnResponsibilities.getChildren().get(0)).getLabel(), responsibility, "Wrong responsibility shown for user " +  qualifiedName);
				
		assertEquals(txtPhoneNumber.getText(), phoneNumber, "Wrong phoneNumber shown for user " +  qualifiedName);
		assertEquals(txtEmailAddress.getText().toLowerCase(), emailAddress.toLowerCase(), "Wrong emailAddress shown for user " +  qualifiedName);
		assertEquals(cmbCountry.getText(), country, "Wrong country shown for user " +  qualifiedName);
		assertEquals(cmbProvince.getText(), province, "Wrong province shown for user " +  qualifiedName);
		assertEquals(cmbMunicipality.getText(), municipality, "Wrong municipality shown for user " +  qualifiedName);
		assertEquals(txtAddress.getText(), address, "Wrong address shown for user " +  qualifiedName);
		assertEquals(txtPostalCode.getText(), postalCode, "Wrong postalCode shown for user " +  qualifiedName);
		assertEquals(txtTown.getText(), town, "Wrong town shown for user " +  qualifiedName);
		assertEquals(txtCity.getText(), city, "Wrong city shown for user " +  qualifiedName);
		assertEquals(dblLatitude.getValue(), latitude, "Wrong latitude shown for user " +  qualifiedName);
		assertEquals(dblLongitude.getValue(), longitude, "Wrong longitude shown for user " +  qualifiedName);			
	}

	@Test
	final void testCase33() {
		
		try {
			checkUser("accounter@soberano.syscoop.co",
						"accounter",
						"accounter",
						"Accounter",
						"50155555",
						"accounter@soberano.syscoop.co",
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
						"Auditor",
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
						"Catalog maintainer",
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
						"Checker",
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
						"Community manager",
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
						"Manager",
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
						"Procurement worker",
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
						"Salesclerk",
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
						"Shift manager",
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
						"Storekeeper",
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
						"System admin",
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
						"Workshop 1 worker",
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
						"Workshop 2 worker",
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
