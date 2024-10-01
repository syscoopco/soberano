package co.syscoop.soberano.test.classes;

import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.zkoss.zats.mimic.ComponentAgent;
import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zats.mimic.operation.InputAgent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.test.helper.CustomerActionTest;
import co.syscoop.soberano.test.helper.CustomerForm;
import co.syscoop.soberano.util.SpringUtility;

@Order(3)

//@Disabled

class O3_CustomerTest_record extends CustomerActionTest {
	
	protected CustomerForm customerForm = null;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		Zats.init("./src/main/webapp");
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
	final void testCase0() {
		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_customer.zul");
		customerForm = new CustomerForm(desktop, 
										(desktop.query("textbox").query("#txtFirstName")).as(Textbox.class), 
										(desktop.query("textbox").query("#txtLastName")).as(Textbox.class), 
										(desktop.query("decimalbox").query("#decDiscount")).as(Decimalbox.class),
										(desktop.query("textbox").query("#incContactData").query("#txtPhoneNumber")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtEmailAddress")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtAddress")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbPostalCode")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtTown")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtCity")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbCountry")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbProvince")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbMunicipality")).as(Combobox.class), 
										(desktop.query("doublebox").query("#incContactData").query("#dblLatitude")).as(Doublebox.class), 
										(desktop.query("doublebox").query("#incContactData").query("#dblLongitude")).as(Doublebox.class));		
		try {
			//this is needed to execute only in the first test. it has to do with testing configuration.
			customerForm.testEachConstrainedObjectIsDeclared();
			customerForm.testEachDeclaredConstrainedObjectIsActuallyConstrained();
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	final void testCase1() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_customer.zul");
		
		ComponentAgent cmbCountryAgent = desktop.query("textbox").query("#incContactData").query("#cmbCountry");
		ComponentAgent cmbProvinceAgent = desktop.query("textbox").query("#incContactData").query("#cmbProvince");
		ComponentAgent cmbMunicipalityAgent = desktop.query("textbox").query("#incContactData").query("#cmbMunicipality");
		customerForm = new CustomerForm(desktop, 
										(desktop.query("textbox").query("#txtFirstName")).as(Textbox.class), 
										(desktop.query("textbox").query("#txtLastName")).as(Textbox.class), 
										(desktop.query("decimalbox").query("#decDiscount")).as(Decimalbox.class),
										(desktop.query("textbox").query("#incContactData").query("#txtPhoneNumber")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtEmailAddress")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtAddress")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbPostalCode")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtTown")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtCity")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbCountry")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbProvince")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbMunicipality")).as(Combobox.class), 
										(desktop.query("doublebox").query("#incContactData").query("#dblLatitude")).as(Doublebox.class), 
										(desktop.query("doublebox").query("#incContactData").query("#dblLongitude")).as(Doublebox.class));
		try {
			customerForm.setComponentValue(customerForm.getTxtFirstName(), "");
			customerForm.setComponentValue(customerForm.getTxtLastName(), "Customer1ln");
			customerForm.setComponentValue(customerForm.getDecDiscount(), new BigDecimal(0.0));			
			
			customerForm.setComponentValue(customerForm.getTxtPhoneNumber(), "55555556");
			customerForm.setComponentValue(customerForm.getTxtEmailAddress(), "c1@soberano.syscoop.co");
			customerForm.setComponentValue(customerForm.getTxtAddress(), "Address 1");
			customerForm.setComponentValue(customerForm.getCmbPostalCode(), "Pc 1");
			customerForm.setComponentValue(customerForm.getTxtTown(), "Town 1");
			customerForm.setComponentValue(customerForm.getTxtCity(), "City 1");
						
			//country combobox
			InputAgent cmbCountryInputAgent = cmbCountryAgent.as(InputAgent.class);
			cmbCountryInputAgent.typing("Cuba");
			customerForm.setComponentValue(customerForm.getCmbCountry(), "CU");
			cmbCountryAgent.click(); 	//needed to force province combo population. 
										//cmbCountry's onSelect event isn't triggered under testing
			
			//province combobox
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("La Habana");
			customerForm.selectComboitemByLabel(customerForm.getCmbProvince(), "La Habana");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing("Plaza de la Revolución");
			customerForm.selectComboitemByLabel(customerForm.getCmbMunicipality(), "Plaza de la Revolución");
			customerForm.setComponentValue(customerForm.getDblLatitude(), 1.0);
			customerForm.setComponentValue(customerForm.getDblLongitude(), -1.0);			
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			customerForm.testWrongValueException(ex);
		}
	}
	
	@Test
	final void testCase2() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_customer.zul");
		
		ComponentAgent cmbCountryAgent = desktop.query("textbox").query("#incContactData").query("#cmbCountry");
		ComponentAgent cmbProvinceAgent = desktop.query("textbox").query("#incContactData").query("#cmbProvince");
		ComponentAgent cmbMunicipalityAgent = desktop.query("textbox").query("#incContactData").query("#cmbMunicipality");
		customerForm = new CustomerForm(desktop, 
										(desktop.query("textbox").query("#txtFirstName")).as(Textbox.class), 
										(desktop.query("textbox").query("#txtLastName")).as(Textbox.class), 
										(desktop.query("decimalbox").query("#decDiscount")).as(Decimalbox.class),
										(desktop.query("textbox").query("#incContactData").query("#txtPhoneNumber")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtEmailAddress")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtAddress")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbPostalCode")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtTown")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtCity")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbCountry")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbProvince")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbMunicipality")).as(Combobox.class), 
										(desktop.query("doublebox").query("#incContactData").query("#dblLatitude")).as(Doublebox.class), 
										(desktop.query("doublebox").query("#incContactData").query("#dblLongitude")).as(Doublebox.class));
		try {
			customerForm.setComponentValue(customerForm.getTxtFirstName(), "Customer1fn");
			customerForm.setComponentValue(customerForm.getTxtLastName(), "");
			customerForm.setComponentValue(customerForm.getDecDiscount(), new BigDecimal(0.0));			
			
			customerForm.setComponentValue(customerForm.getTxtPhoneNumber(), "55555556");
			customerForm.setComponentValue(customerForm.getTxtEmailAddress(), "c1@soberano.syscoop.co");
			customerForm.setComponentValue(customerForm.getTxtAddress(), "Address 1");
			customerForm.setComponentValue(customerForm.getCmbPostalCode(), "Pc 1");
			customerForm.setComponentValue(customerForm.getTxtTown(), "Town 1");
			customerForm.setComponentValue(customerForm.getTxtCity(), "City 1");
						
			//country combobox
			InputAgent cmbCountryInputAgent = cmbCountryAgent.as(InputAgent.class);
			cmbCountryInputAgent.typing("Cuba");
			customerForm.setComponentValue(customerForm.getCmbCountry(), "CU");
			cmbCountryAgent.click(); 	//needed to force province combo population. 
										//cmbCountry's onSelect event isn't triggered under testing
			
			//province combobox
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("La Habana");
			customerForm.selectComboitemByLabel(customerForm.getCmbProvince(), "La Habana");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing("Plaza de la Revolución");
			customerForm.selectComboitemByLabel(customerForm.getCmbMunicipality(), "Plaza de la Revolución");
			customerForm.setComponentValue(customerForm.getDblLatitude(), 1.0);
			customerForm.setComponentValue(customerForm.getDblLongitude(), -1.0);			
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			customerForm.testWrongValueException(ex);
		}
	}
	
	@Test
	final void testCase3() {
		//reserved
	}
	
	@Test
	final void testCase4() {
		//reserved
	}
	
	@Test
	final void testCase5() {

		SpringUtility.setLoggedUserForTesting("user2@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_customer.zul");
		
		ComponentAgent cmbCountryAgent = desktop.query("textbox").query("#incContactData").query("#cmbCountry");
		ComponentAgent cmbProvinceAgent = desktop.query("textbox").query("#incContactData").query("#cmbProvince");
		ComponentAgent cmbMunicipalityAgent = desktop.query("textbox").query("#incContactData").query("#cmbMunicipality");
		customerForm = new CustomerForm(desktop, 
										(desktop.query("textbox").query("#txtFirstName")).as(Textbox.class), 
										(desktop.query("textbox").query("#txtLastName")).as(Textbox.class), 
										(desktop.query("decimalbox").query("#decDiscount")).as(Decimalbox.class),
										(desktop.query("textbox").query("#incContactData").query("#txtPhoneNumber")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtEmailAddress")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtAddress")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbPostalCode")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtTown")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtCity")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbCountry")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbProvince")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbMunicipality")).as(Combobox.class), 
										(desktop.query("doublebox").query("#incContactData").query("#dblLatitude")).as(Doublebox.class), 
										(desktop.query("doublebox").query("#incContactData").query("#dblLongitude")).as(Doublebox.class));
		try {
			customerForm.setComponentValue(customerForm.getTxtFirstName(), "Customer1fn");
			customerForm.setComponentValue(customerForm.getTxtLastName(), "Customer1ln");
			customerForm.setComponentValue(customerForm.getDecDiscount(), new BigDecimal(0));			
			
			customerForm.setComponentValue(customerForm.getTxtPhoneNumber(), "55555556");
			customerForm.setComponentValue(customerForm.getTxtEmailAddress(), "c1@soberano.syscoop.co");
			customerForm.setComponentValue(customerForm.getTxtAddress(), "Address 1");
			customerForm.setComponentValue(customerForm.getCmbPostalCode(), "Pc 1");
			customerForm.setComponentValue(customerForm.getTxtTown(), "Town 1");
			customerForm.setComponentValue(customerForm.getTxtCity(), "City 1");
						
			//country combobox
			InputAgent cmbCountryInputAgent = cmbCountryAgent.as(InputAgent.class);
			cmbCountryInputAgent.typing("Cuba");
			customerForm.setComponentValue(customerForm.getCmbCountry(), "CU");
			cmbCountryAgent.click(); 	//needed to force province combo population. 
										//cmbCountry's onSelect event isn't triggered under testing
			
			//province combobox
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("La Habana");
			customerForm.selectComboitemByLabel(customerForm.getCmbProvince(), "La Habana");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing("Plaza de la Revolución");
			customerForm.selectComboitemByLabel(customerForm.getCmbMunicipality(), "Plaza de la Revolución");
			customerForm.setComponentValue(customerForm.getDblLatitude(), 1.0);
			customerForm.setComponentValue(customerForm.getDblLongitude(), -1.0);			
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testNotEnoughRightsException(ex);
		}
	}
	
	@Test
	final void testCase6() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_customer.zul");
		
		ComponentAgent cmbCountryAgent = desktop.query("textbox").query("#incContactData").query("#cmbCountry");
		ComponentAgent cmbProvinceAgent = desktop.query("textbox").query("#incContactData").query("#cmbProvince");
		ComponentAgent cmbMunicipalityAgent = desktop.query("textbox").query("#incContactData").query("#cmbMunicipality");
		customerForm = new CustomerForm(desktop, 
										(desktop.query("textbox").query("#txtFirstName")).as(Textbox.class), 
										(desktop.query("textbox").query("#txtLastName")).as(Textbox.class), 
										(desktop.query("decimalbox").query("#decDiscount")).as(Decimalbox.class),
										(desktop.query("textbox").query("#incContactData").query("#txtPhoneNumber")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtEmailAddress")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtAddress")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbPostalCode")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtTown")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtCity")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbCountry")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbProvince")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbMunicipality")).as(Combobox.class), 
										(desktop.query("doublebox").query("#incContactData").query("#dblLatitude")).as(Doublebox.class), 
										(desktop.query("doublebox").query("#incContactData").query("#dblLongitude")).as(Doublebox.class));
		try {
			customerForm.setComponentValue(customerForm.getTxtFirstName(), "Customer1fn");
			customerForm.setComponentValue(customerForm.getTxtLastName(), "Customer1ln");
			customerForm.setComponentValue(customerForm.getDecDiscount(), new BigDecimal(0));			
			
			customerForm.setComponentValue(customerForm.getTxtPhoneNumber(), "55555556");
			customerForm.setComponentValue(customerForm.getTxtEmailAddress(), "c1@soberano.syscoop.co");
			customerForm.setComponentValue(customerForm.getTxtAddress(), "Address 1");
			customerForm.setComponentValue(customerForm.getCmbPostalCode(), "Pc 1");
			customerForm.setComponentValue(customerForm.getTxtTown(), "Town 1");
			customerForm.setComponentValue(customerForm.getTxtCity(), "City 1");
						
			//country combobox
			InputAgent cmbCountryInputAgent = cmbCountryAgent.as(InputAgent.class);
			cmbCountryInputAgent.typing("Cuba");
			customerForm.setComponentValue(customerForm.getCmbCountry(), "CU");
			cmbCountryAgent.click(); 	//needed to force province combo population. 
										//cmbCountry's onSelect event isn't triggered under testing
			
			//province combobox
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("La Habana");
			customerForm.selectComboitemByLabel(customerForm.getCmbProvince(), "La Habana");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing("Plaza de la Revolución");
			customerForm.selectComboitemByLabel(customerForm.getCmbMunicipality(), "Plaza de la Revolución");
			customerForm.setComponentValue(customerForm.getDblLatitude(), 1.0);
			customerForm.setComponentValue(customerForm.getDblLongitude(), -1.0);			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase7() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_customer.zul");
		
		ComponentAgent cmbCountryAgent = desktop.query("textbox").query("#incContactData").query("#cmbCountry");
		ComponentAgent cmbProvinceAgent = desktop.query("textbox").query("#incContactData").query("#cmbProvince");
		ComponentAgent cmbMunicipalityAgent = desktop.query("textbox").query("#incContactData").query("#cmbMunicipality");
		customerForm = new CustomerForm(desktop, 
										(desktop.query("textbox").query("#txtFirstName")).as(Textbox.class), 
										(desktop.query("textbox").query("#txtLastName")).as(Textbox.class), 
										(desktop.query("decimalbox").query("#decDiscount")).as(Decimalbox.class),
										(desktop.query("textbox").query("#incContactData").query("#txtPhoneNumber")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtEmailAddress")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtAddress")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbPostalCode")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtTown")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtCity")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbCountry")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbProvince")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbMunicipality")).as(Combobox.class), 
										(desktop.query("doublebox").query("#incContactData").query("#dblLatitude")).as(Doublebox.class), 
										(desktop.query("doublebox").query("#incContactData").query("#dblLongitude")).as(Doublebox.class));
		try {
			customerForm.setComponentValue(customerForm.getTxtFirstName(), "Customer2fn");
			customerForm.setComponentValue(customerForm.getTxtLastName(), "Customer2ln");
			customerForm.setComponentValue(customerForm.getDecDiscount(), new BigDecimal(10));			
			
			customerForm.setComponentValue(customerForm.getTxtPhoneNumber(), "55555556");
			customerForm.setComponentValue(customerForm.getTxtEmailAddress(), "c2@soberano.syscoop.co");
			customerForm.setComponentValue(customerForm.getTxtAddress(), "Address 2");
			customerForm.setComponentValue(customerForm.getCmbPostalCode(), "Pc 2");
			customerForm.setComponentValue(customerForm.getTxtTown(), "Town 2");
			customerForm.setComponentValue(customerForm.getTxtCity(), "City 2");
						
			//country combobox
			InputAgent cmbCountryInputAgent = cmbCountryAgent.as(InputAgent.class);
			cmbCountryInputAgent.typing("Cuba");
			customerForm.setComponentValue(customerForm.getCmbCountry(), "CU");
			cmbCountryAgent.click(); 	//needed to force province combo population. 
										//cmbCountry's onSelect event isn't triggered under testing
			
			//province combobox
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("La Habana");
			customerForm.selectComboitemByLabel(customerForm.getCmbProvince(), "La Habana");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing("Plaza de la Revolución");
			customerForm.selectComboitemByLabel(customerForm.getCmbMunicipality(), "Plaza de la Revolución");
			customerForm.setComponentValue(customerForm.getDblLatitude(), 2.0);
			customerForm.setComponentValue(customerForm.getDblLongitude(), -2.0);			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase8() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_customer.zul");
		
		ComponentAgent cmbCountryAgent = desktop.query("textbox").query("#incContactData").query("#cmbCountry");
		ComponentAgent cmbProvinceAgent = desktop.query("textbox").query("#incContactData").query("#cmbProvince");
		ComponentAgent cmbMunicipalityAgent = desktop.query("textbox").query("#incContactData").query("#cmbMunicipality");
		customerForm = new CustomerForm(desktop, 
										(desktop.query("textbox").query("#txtFirstName")).as(Textbox.class), 
										(desktop.query("textbox").query("#txtLastName")).as(Textbox.class), 
										(desktop.query("decimalbox").query("#decDiscount")).as(Decimalbox.class),
										(desktop.query("textbox").query("#incContactData").query("#txtPhoneNumber")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtEmailAddress")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtAddress")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbPostalCode")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtTown")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtCity")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbCountry")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbProvince")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbMunicipality")).as(Combobox.class), 
										(desktop.query("doublebox").query("#incContactData").query("#dblLatitude")).as(Doublebox.class), 
										(desktop.query("doublebox").query("#incContactData").query("#dblLongitude")).as(Doublebox.class));
		try {
			customerForm.setComponentValue(customerForm.getTxtFirstName(), "Customer3fn");
			customerForm.setComponentValue(customerForm.getTxtLastName(), "Customer3ln");
			customerForm.setComponentValue(customerForm.getDecDiscount(), new BigDecimal(0));			
			
			customerForm.setComponentValue(customerForm.getTxtPhoneNumber(), "55555557");
			customerForm.setComponentValue(customerForm.getTxtEmailAddress(), "c3@soberano.syscoop.co");
			customerForm.setComponentValue(customerForm.getTxtAddress(), "Address 3");
			customerForm.setComponentValue(customerForm.getCmbPostalCode(), "Pc 3");
			customerForm.setComponentValue(customerForm.getTxtTown(), "Town 3");
			customerForm.setComponentValue(customerForm.getTxtCity(), "City 3");
						
			//country combobox
			InputAgent cmbCountryInputAgent = cmbCountryAgent.as(InputAgent.class);
			cmbCountryInputAgent.typing("Cuba");
			customerForm.setComponentValue(customerForm.getCmbCountry(), "CU");
			cmbCountryAgent.click(); 	//needed to force province combo population. 
										//cmbCountry's onSelect event isn't triggered under testing
			
			//province combobox
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("La Habana");
			customerForm.selectComboitemByLabel(customerForm.getCmbProvince(), "Pinar del Río");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing("Plaza de la Revolución");
			customerForm.selectComboitemByLabel(customerForm.getCmbMunicipality(), "Viñales");
			customerForm.setComponentValue(customerForm.getDblLatitude(), 3.0);
			customerForm.setComponentValue(customerForm.getDblLongitude(), -3.0);			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase9() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_customer.zul");
		
		ComponentAgent cmbCountryAgent = desktop.query("textbox").query("#incContactData").query("#cmbCountry");
		ComponentAgent cmbProvinceAgent = desktop.query("textbox").query("#incContactData").query("#cmbProvince");
		ComponentAgent cmbMunicipalityAgent = desktop.query("textbox").query("#incContactData").query("#cmbMunicipality");
		customerForm = new CustomerForm(desktop, 
										(desktop.query("textbox").query("#txtFirstName")).as(Textbox.class), 
										(desktop.query("textbox").query("#txtLastName")).as(Textbox.class), 
										(desktop.query("decimalbox").query("#decDiscount")).as(Decimalbox.class),
										(desktop.query("textbox").query("#incContactData").query("#txtPhoneNumber")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtEmailAddress")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtAddress")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbPostalCode")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtTown")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtCity")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbCountry")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbProvince")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbMunicipality")).as(Combobox.class), 
										(desktop.query("doublebox").query("#incContactData").query("#dblLatitude")).as(Doublebox.class), 
										(desktop.query("doublebox").query("#incContactData").query("#dblLongitude")).as(Doublebox.class));
		try {
			customerForm.setComponentValue(customerForm.getTxtFirstName(), "Customer4fn");
			customerForm.setComponentValue(customerForm.getTxtLastName(), "Customer4ln");
			customerForm.setComponentValue(customerForm.getDecDiscount(), new BigDecimal(10.5));			
			
			customerForm.setComponentValue(customerForm.getTxtPhoneNumber(), "55555558");
			customerForm.setComponentValue(customerForm.getTxtEmailAddress(), "c4@soberano.syscoop.co");
			customerForm.setComponentValue(customerForm.getTxtAddress(), "Address 4");
			customerForm.setComponentValue(customerForm.getCmbPostalCode(), "Pc 4");
			customerForm.setComponentValue(customerForm.getTxtTown(), "Town 4");
			customerForm.setComponentValue(customerForm.getTxtCity(), "City 4");
						
			//country combobox
			InputAgent cmbCountryInputAgent = cmbCountryAgent.as(InputAgent.class);
			cmbCountryInputAgent.typing("Cuba");
			customerForm.setComponentValue(customerForm.getCmbCountry(), "CU");
			cmbCountryAgent.click(); 	//needed to force province combo population. 
										//cmbCountry's onSelect event isn't triggered under testing
			
			//province combobox
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("La Habana");
			customerForm.selectComboitemByLabel(customerForm.getCmbProvince(), "Matanzas");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing("Plaza de la Revolución");
			customerForm.selectComboitemByLabel(customerForm.getCmbMunicipality(), "Jagüey Grande");
			customerForm.setComponentValue(customerForm.getDblLatitude(), 4.0);
			customerForm.setComponentValue(customerForm.getDblLongitude(), -4.0);			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase10() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_customer.zul");
		
		ComponentAgent cmbCountryAgent = desktop.query("textbox").query("#incContactData").query("#cmbCountry");
		ComponentAgent cmbProvinceAgent = desktop.query("textbox").query("#incContactData").query("#cmbProvince");
		ComponentAgent cmbMunicipalityAgent = desktop.query("textbox").query("#incContactData").query("#cmbMunicipality");
		customerForm = new CustomerForm(desktop, 
										(desktop.query("textbox").query("#txtFirstName")).as(Textbox.class), 
										(desktop.query("textbox").query("#txtLastName")).as(Textbox.class), 
										(desktop.query("decimalbox").query("#decDiscount")).as(Decimalbox.class),
										(desktop.query("textbox").query("#incContactData").query("#txtPhoneNumber")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtEmailAddress")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtAddress")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbPostalCode")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtTown")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtCity")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbCountry")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbProvince")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbMunicipality")).as(Combobox.class), 
										(desktop.query("doublebox").query("#incContactData").query("#dblLatitude")).as(Doublebox.class), 
										(desktop.query("doublebox").query("#incContactData").query("#dblLongitude")).as(Doublebox.class));
		try {
			customerForm.setComponentValue(customerForm.getTxtFirstName(), "Customer5fn");
			customerForm.setComponentValue(customerForm.getTxtLastName(), "Customer5ln");
			customerForm.setComponentValue(customerForm.getDecDiscount(), new BigDecimal(0));			
			
			customerForm.setComponentValue(customerForm.getTxtPhoneNumber(), "55555559");
			customerForm.setComponentValue(customerForm.getTxtEmailAddress(), "c5@soberano.syscoop.co");
			customerForm.setComponentValue(customerForm.getTxtAddress(), "Address 5");
			customerForm.setComponentValue(customerForm.getCmbPostalCode(), "Pc 5");
			customerForm.setComponentValue(customerForm.getTxtTown(), "Town 5");
			customerForm.setComponentValue(customerForm.getTxtCity(), "City 5");
						
			//country combobox
			InputAgent cmbCountryInputAgent = cmbCountryAgent.as(InputAgent.class);
			cmbCountryInputAgent.typing("Cuba");
			customerForm.setComponentValue(customerForm.getCmbCountry(), "CU");
			cmbCountryAgent.click(); 	//needed to force province combo population. 
										//cmbCountry's onSelect event isn't triggered under testing
			
			//province combobox
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("La Habana");
			customerForm.selectComboitemByLabel(customerForm.getCmbProvince(), "Villa Clara");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing("Plaza de la Revolución");
			customerForm.selectComboitemByLabel(customerForm.getCmbMunicipality(), "Santa Clara");
			customerForm.setComponentValue(customerForm.getDblLatitude(), 5.0);
			customerForm.setComponentValue(customerForm.getDblLongitude(), -5.0);			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase11() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_customer.zul");
		
		ComponentAgent cmbCountryAgent = desktop.query("textbox").query("#incContactData").query("#cmbCountry");
		ComponentAgent cmbProvinceAgent = desktop.query("textbox").query("#incContactData").query("#cmbProvince");
		ComponentAgent cmbMunicipalityAgent = desktop.query("textbox").query("#incContactData").query("#cmbMunicipality");
		customerForm = new CustomerForm(desktop, 
										(desktop.query("textbox").query("#txtFirstName")).as(Textbox.class), 
										(desktop.query("textbox").query("#txtLastName")).as(Textbox.class), 
										(desktop.query("decimalbox").query("#decDiscount")).as(Decimalbox.class),
										(desktop.query("textbox").query("#incContactData").query("#txtPhoneNumber")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtEmailAddress")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtAddress")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbPostalCode")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtTown")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtCity")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbCountry")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbProvince")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbMunicipality")).as(Combobox.class), 
										(desktop.query("doublebox").query("#incContactData").query("#dblLatitude")).as(Doublebox.class), 
										(desktop.query("doublebox").query("#incContactData").query("#dblLongitude")).as(Doublebox.class));
		try {
			customerForm.setComponentValue(customerForm.getTxtFirstName(), "Customer6fn");
			customerForm.setComponentValue(customerForm.getTxtLastName(), "Customer6ln");
			customerForm.setComponentValue(customerForm.getDecDiscount(), new BigDecimal(12.5));			
			
			customerForm.setComponentValue(customerForm.getTxtPhoneNumber(), "55555560");
			customerForm.setComponentValue(customerForm.getTxtEmailAddress(), "c6@soberano.syscoop.co");
			customerForm.setComponentValue(customerForm.getTxtAddress(), "Address 6");
			customerForm.setComponentValue(customerForm.getCmbPostalCode(), "Pc 6");
			customerForm.setComponentValue(customerForm.getTxtTown(), "Town 6");
			customerForm.setComponentValue(customerForm.getTxtCity(), "City 6");
						
			//country combobox
			InputAgent cmbCountryInputAgent = cmbCountryAgent.as(InputAgent.class);
			cmbCountryInputAgent.typing("Cuba");
			customerForm.setComponentValue(customerForm.getCmbCountry(), "CU");
			cmbCountryAgent.click(); 	//needed to force province combo population. 
										//cmbCountry's onSelect event isn't triggered under testing
			
			//province combobox
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("La Habana");
			customerForm.selectComboitemByLabel(customerForm.getCmbProvince(), "Isla de la Juventud");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing("Plaza de la Revolución");
			customerForm.selectComboitemByLabel(customerForm.getCmbMunicipality(), "Isla de la Juventud");
			customerForm.setComponentValue(customerForm.getDblLatitude(), 6.0);
			customerForm.setComponentValue(customerForm.getDblLongitude(), -6.0);			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase12() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_customer.zul");
		
		ComponentAgent cmbCountryAgent = desktop.query("textbox").query("#incContactData").query("#cmbCountry");
		ComponentAgent cmbProvinceAgent = desktop.query("textbox").query("#incContactData").query("#cmbProvince");
		ComponentAgent cmbMunicipalityAgent = desktop.query("textbox").query("#incContactData").query("#cmbMunicipality");
		customerForm = new CustomerForm(desktop, 
										(desktop.query("textbox").query("#txtFirstName")).as(Textbox.class), 
										(desktop.query("textbox").query("#txtLastName")).as(Textbox.class), 
										(desktop.query("decimalbox").query("#decDiscount")).as(Decimalbox.class),
										(desktop.query("textbox").query("#incContactData").query("#txtPhoneNumber")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtEmailAddress")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtAddress")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbPostalCode")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtTown")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtCity")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbCountry")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbProvince")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbMunicipality")).as(Combobox.class), 
										(desktop.query("doublebox").query("#incContactData").query("#dblLatitude")).as(Doublebox.class), 
										(desktop.query("doublebox").query("#incContactData").query("#dblLongitude")).as(Doublebox.class));
		try {
			customerForm.setComponentValue(customerForm.getTxtFirstName(), "Customer7fn");
			customerForm.setComponentValue(customerForm.getTxtLastName(), "Customer7ln");
			customerForm.setComponentValue(customerForm.getDecDiscount(), new BigDecimal(0));			
			
			customerForm.setComponentValue(customerForm.getTxtPhoneNumber(), "55555561");
			customerForm.setComponentValue(customerForm.getTxtEmailAddress(), "c7@soberano.syscoop.co");
			customerForm.setComponentValue(customerForm.getTxtAddress(), "Address 7");
			customerForm.setComponentValue(customerForm.getCmbPostalCode(), "Pc 7");
			customerForm.setComponentValue(customerForm.getTxtTown(), "Town 7");
			customerForm.setComponentValue(customerForm.getTxtCity(), "City 7");
						
			//country combobox
			InputAgent cmbCountryInputAgent = cmbCountryAgent.as(InputAgent.class);
			cmbCountryInputAgent.typing("Cuba");
			customerForm.setComponentValue(customerForm.getCmbCountry(), "CU");
			cmbCountryAgent.click(); 	//needed to force province combo population. 
										//cmbCountry's onSelect event isn't triggered under testing
			
			//province combobox
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("La Habana");
			customerForm.selectComboitemByLabel(customerForm.getCmbProvince(), "Artemisa");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing("Plaza de la Revolución");
			customerForm.selectComboitemByLabel(customerForm.getCmbMunicipality(), "Artemisa");
			customerForm.setComponentValue(customerForm.getDblLatitude(), 7.0);
			customerForm.setComponentValue(customerForm.getDblLongitude(), -7.0);			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase13() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_customer.zul");
		
		ComponentAgent cmbCountryAgent = desktop.query("textbox").query("#incContactData").query("#cmbCountry");
		ComponentAgent cmbProvinceAgent = desktop.query("textbox").query("#incContactData").query("#cmbProvince");
		ComponentAgent cmbMunicipalityAgent = desktop.query("textbox").query("#incContactData").query("#cmbMunicipality");
		customerForm = new CustomerForm(desktop, 
										(desktop.query("textbox").query("#txtFirstName")).as(Textbox.class), 
										(desktop.query("textbox").query("#txtLastName")).as(Textbox.class), 
										(desktop.query("decimalbox").query("#decDiscount")).as(Decimalbox.class),
										(desktop.query("textbox").query("#incContactData").query("#txtPhoneNumber")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtEmailAddress")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtAddress")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbPostalCode")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtTown")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtCity")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbCountry")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbProvince")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbMunicipality")).as(Combobox.class), 
										(desktop.query("doublebox").query("#incContactData").query("#dblLatitude")).as(Doublebox.class), 
										(desktop.query("doublebox").query("#incContactData").query("#dblLongitude")).as(Doublebox.class));
		try {
			customerForm.setComponentValue(customerForm.getTxtFirstName(), "Customer8fn");
			customerForm.setComponentValue(customerForm.getTxtLastName(), "Customer8ln");
			customerForm.setComponentValue(customerForm.getDecDiscount(), new BigDecimal(0));			
			
			customerForm.setComponentValue(customerForm.getTxtPhoneNumber(), "55555562");
			customerForm.setComponentValue(customerForm.getTxtEmailAddress(), "c8@soberano.syscoop.co");
			customerForm.setComponentValue(customerForm.getTxtAddress(), "Address 8");
			customerForm.setComponentValue(customerForm.getCmbPostalCode(), "Pc 8");
			customerForm.setComponentValue(customerForm.getTxtTown(), "Town 8");
			customerForm.setComponentValue(customerForm.getTxtCity(), "City 8");
						
			//country combobox
			InputAgent cmbCountryInputAgent = cmbCountryAgent.as(InputAgent.class);
			cmbCountryInputAgent.typing("Cuba");
			customerForm.setComponentValue(customerForm.getCmbCountry(), "CU");
			cmbCountryAgent.click(); 	//needed to force province combo population. 
										//cmbCountry's onSelect event isn't triggered under testing
			
			//province combobox
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("La Habana");
			customerForm.selectComboitemByLabel(customerForm.getCmbProvince(), "Artemisa");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing("Plaza de la Revolución");
			customerForm.selectComboitemByLabel(customerForm.getCmbMunicipality(), "Artemisa");
			customerForm.setComponentValue(customerForm.getDblLatitude(), 8.0);
			customerForm.setComponentValue(customerForm.getDblLongitude(), -8.0);			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	@Test
	final void testCase14() {

		SpringUtility.setLoggedUserForTesting("user1@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_customer.zul");
		
		ComponentAgent cmbCountryAgent = desktop.query("textbox").query("#incContactData").query("#cmbCountry");
		ComponentAgent cmbProvinceAgent = desktop.query("textbox").query("#incContactData").query("#cmbProvince");
		ComponentAgent cmbMunicipalityAgent = desktop.query("textbox").query("#incContactData").query("#cmbMunicipality");
		customerForm = new CustomerForm(desktop, 
										(desktop.query("textbox").query("#txtFirstName")).as(Textbox.class), 
										(desktop.query("textbox").query("#txtLastName")).as(Textbox.class), 
										(desktop.query("decimalbox").query("#decDiscount")).as(Decimalbox.class),
										(desktop.query("textbox").query("#incContactData").query("#txtPhoneNumber")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtEmailAddress")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtAddress")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbPostalCode")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtTown")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#txtCity")).as(Textbox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbCountry")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbProvince")).as(Combobox.class), 
										(desktop.query("textbox").query("#incContactData").query("#cmbMunicipality")).as(Combobox.class), 
										(desktop.query("doublebox").query("#incContactData").query("#dblLatitude")).as(Doublebox.class), 
										(desktop.query("doublebox").query("#incContactData").query("#dblLongitude")).as(Doublebox.class));
		try {
			customerForm.setComponentValue(customerForm.getTxtFirstName(), "Customer9fn");
			customerForm.setComponentValue(customerForm.getTxtLastName(), "Customer9ln");
			customerForm.setComponentValue(customerForm.getDecDiscount(), new BigDecimal(0));			
			
			customerForm.setComponentValue(customerForm.getTxtPhoneNumber(), "55555563");
			customerForm.setComponentValue(customerForm.getTxtEmailAddress(), "c9@soberano.syscoop.co");
			customerForm.setComponentValue(customerForm.getTxtAddress(), "Address 9");
			customerForm.setComponentValue(customerForm.getCmbPostalCode(), "Pc 9");
			customerForm.setComponentValue(customerForm.getTxtTown(), "Town 9");
			customerForm.setComponentValue(customerForm.getTxtCity(), "City 9");
						
			//country combobox
			InputAgent cmbCountryInputAgent = cmbCountryAgent.as(InputAgent.class);
			cmbCountryInputAgent.typing("Cuba");
			customerForm.setComponentValue(customerForm.getCmbCountry(), "CU");
			cmbCountryAgent.click(); 	//needed to force province combo population. 
										//cmbCountry's onSelect event isn't triggered under testing
			
			//province combobox
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("La Habana");
			customerForm.selectComboitemByLabel(customerForm.getCmbProvince(), "Mayabeque");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing("Plaza de la Revolución");
			customerForm.selectComboitemByLabel(customerForm.getCmbMunicipality(), "Santa Cruz del Norte");
			customerForm.setComponentValue(customerForm.getDblLatitude(), 9.0);
			customerForm.setComponentValue(customerForm.getDblLongitude(), -9.0);			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
}
