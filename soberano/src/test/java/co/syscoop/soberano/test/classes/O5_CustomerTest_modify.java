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
import org.zkoss.zats.mimic.ComponentAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zats.mimic.operation.InputAgent;
import co.syscoop.soberano.test.helper.CustomerActionTest;
import co.syscoop.soberano.test.helper.CustomerForm;

@Order(5)

//@Disabled

class O5_CustomerTest_modify extends CustomerActionTest {
		
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		Library.setProperty(Attributes.PREFERRED_LOCALE, "en"); //needed due to translated captions according 
		//to runtime locale not available under 
		//testing environment
		
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
	final void testCase1() {

		CustomerForm customerForm = null;
		try {
			customerForm = setFormComponents("user2@soberano.syscoop.co", "customers.zul");			
			loadObjectDetails("Customer1fn Customer1ln : c1@soberano.syscoop.co");
			
			customerForm.setComponentValue(customerForm.getTxtFirstName(), "Customer1fnmod");
			customerForm.setComponentValue(customerForm.getTxtLastName(), "Customer1lnmod");
			customerForm.setComponentValue(customerForm.getDecDiscount(), new BigDecimal(0));			
			
			customerForm.setComponentValue(customerForm.getTxtPhoneNumber(), "5355555556");
			customerForm.setComponentValue(customerForm.getTxtEmailAddress(), "c1mod@soberano.syscoop.co");
			customerForm.setComponentValue(customerForm.getTxtAddress(), "Addressmod 1");
			customerForm.setComponentValue(customerForm.getCmbPostalCode(), "Pcmod 1");
			customerForm.setComponentValue(customerForm.getTxtTown(), "Townmod 1");
			customerForm.setComponentValue(customerForm.getTxtCity(), "Citymod 1");
			
			//country is the same
			
			//province combobox
			ComponentAgent cmbProvinceAgent = cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#cmbProvince");
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("La Habana");
			customerForm.selectComboitemByLabel(customerForm.getCmbProvince(), "La Habana");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			ComponentAgent cmbMunicipalityAgent = cmbProvinceAgent.query("#cmbMunicipality");
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing("Plaza de la Revolución");
			customerForm.selectComboitemByLabel(customerForm.getCmbMunicipality(), "Plaza de la Revolución");
			
			customerForm.setComponentValue(customerForm.getDblLatitude(), 1.0);
			customerForm.setComponentValue(customerForm.getDblLongitude(), -1.0);	
			clickOnApplyButton(customerForm.getDesktop());
			
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
	final void testCase2() {

		CustomerForm customerForm = null;
		try {
			customerForm = setFormComponents("user1@soberano.syscoop.co", "customers.zul");			
			loadObjectDetails("Customer1fn Customer1ln : c1@soberano.syscoop.co");
			
			customerForm.setComponentValue(customerForm.getTxtFirstName(), "Customer1fnmod");
			customerForm.setComponentValue(customerForm.getTxtLastName(), "Customer1lnmod");
			customerForm.setComponentValue(customerForm.getDecDiscount(), new BigDecimal(0));			
			
			customerForm.setComponentValue(customerForm.getTxtPhoneNumber(), "5355555556");
			customerForm.setComponentValue(customerForm.getTxtEmailAddress(), "c1mod@soberano.syscoop.co");
			customerForm.setComponentValue(customerForm.getTxtAddress(), "Addressmod 1");
			customerForm.setComponentValue(customerForm.getCmbPostalCode(), "Pcmod 1");
			customerForm.setComponentValue(customerForm.getTxtTown(), "Townmod 1");
			customerForm.setComponentValue(customerForm.getTxtCity(), "Citymod 1");
			
			//country is the same
			
			//province combobox
			ComponentAgent cmbProvinceAgent = cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#cmbProvince");
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("Pinar del Río");
			customerForm.selectComboitemByLabel(customerForm.getCmbProvince(), "Pinar del Río");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			ComponentAgent cmbMunicipalityAgent = cmbProvinceAgent.query("#cmbMunicipality");
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing("Viñales");
			customerForm.selectComboitemByLabel(customerForm.getCmbMunicipality(), "Viñales");
			
			customerForm.setComponentValue(customerForm.getDblLatitude(), 3.0);
			customerForm.setComponentValue(customerForm.getDblLongitude(), -3.0);	
			clickOnApplyButton(customerForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}		
	}
	
	@Test
	final void testCase3() {

		CustomerForm customerForm = null;
		try {
			customerForm = setFormComponents("user1@soberano.syscoop.co", "customers.zul");			
			loadObjectDetails("Customer2fn Customer2ln : c2@soberano.syscoop.co");
			
			customerForm.setComponentValue(customerForm.getTxtFirstName(), "Customer2fnmod");
			customerForm.setComponentValue(customerForm.getTxtLastName(), "Customer2lnmod");
			customerForm.setComponentValue(customerForm.getDecDiscount(), new BigDecimal(15));			
			
			customerForm.setComponentValue(customerForm.getTxtPhoneNumber(), "5355555556");
			customerForm.setComponentValue(customerForm.getTxtEmailAddress(), "c2mod@soberano.syscoop.co");
			customerForm.setComponentValue(customerForm.getTxtAddress(), "Addressmod 2");
			customerForm.setComponentValue(customerForm.getCmbPostalCode(), "Pcmod 2");
			customerForm.setComponentValue(customerForm.getTxtTown(), "Townmod 2");
			customerForm.setComponentValue(customerForm.getTxtCity(), "Citymod 2");
			
			//country is the same
			
			//province combobox
			ComponentAgent cmbProvinceAgent = cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#cmbProvince");
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("La Habana");
			customerForm.selectComboitemByLabel(customerForm.getCmbProvince(), "La Habana");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			ComponentAgent cmbMunicipalityAgent = cmbProvinceAgent.query("#cmbMunicipality");
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing("Plaza de la Revolución");
			customerForm.selectComboitemByLabel(customerForm.getCmbMunicipality(), "Plaza de la Revolución");
			
			customerForm.setComponentValue(customerForm.getDblLatitude(), 4.0);
			customerForm.setComponentValue(customerForm.getDblLongitude(), -4.0);	
			clickOnApplyButton(customerForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}		
	}
	
	@Test
	final void testCase4() {

		CustomerForm customerForm = null;
		try {
			customerForm = setFormComponents("user1@soberano.syscoop.co", "customers.zul");			
			loadObjectDetails("Customer3fn Customer3ln : c3@soberano.syscoop.co");
			
			customerForm.setComponentValue(customerForm.getTxtFirstName(), "Customer3fnmod");
			customerForm.setComponentValue(customerForm.getTxtLastName(), "Customer3lnmod");
			customerForm.setComponentValue(customerForm.getDecDiscount(), new BigDecimal(0));			
			
			customerForm.setComponentValue(customerForm.getTxtPhoneNumber(), "5355555557");
			customerForm.setComponentValue(customerForm.getTxtEmailAddress(), "c3mod@soberano.syscoop.co");
			customerForm.setComponentValue(customerForm.getTxtAddress(), "Addressmod 3");
			customerForm.setComponentValue(customerForm.getCmbPostalCode(), "Pcmod 3");
			customerForm.setComponentValue(customerForm.getTxtTown(), "Townmod 3");
			customerForm.setComponentValue(customerForm.getTxtCity(), "Citymod 3");
			
			//country is the same
			
			//province combobox
			ComponentAgent cmbProvinceAgent = cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#cmbProvince");
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("Pinar del Río");
			customerForm.selectComboitemByLabel(customerForm.getCmbProvince(), "Pinar del Río");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			ComponentAgent cmbMunicipalityAgent = cmbProvinceAgent.query("#cmbMunicipality");
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing("Viñales");
			customerForm.selectComboitemByLabel(customerForm.getCmbMunicipality(), "Viñales");
			
			customerForm.setComponentValue(customerForm.getDblLatitude(), 5.0);
			customerForm.setComponentValue(customerForm.getDblLongitude(), -5.0);	
			clickOnApplyButton(customerForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}		
	}
	
	@Test
	final void testCase5() {

		CustomerForm customerForm = null;
		try {
			customerForm = setFormComponents("user1@soberano.syscoop.co", "customers.zul");			
			loadObjectDetails("Customer4fn Customer4ln : c4@soberano.syscoop.co");
			
			customerForm.setComponentValue(customerForm.getTxtFirstName(), "Customer4fnmod");
			customerForm.setComponentValue(customerForm.getTxtLastName(), "Customer4lnmod");
			customerForm.setComponentValue(customerForm.getDecDiscount(), new BigDecimal(5.6));			
			
			customerForm.setComponentValue(customerForm.getTxtPhoneNumber(), "5355555558");
			customerForm.setComponentValue(customerForm.getTxtEmailAddress(), "c4mod@soberano.syscoop.co");
			customerForm.setComponentValue(customerForm.getTxtAddress(), "Addressmod 4");
			customerForm.setComponentValue(customerForm.getCmbPostalCode(), "Pcmod 4");
			customerForm.setComponentValue(customerForm.getTxtTown(), "Townmod 4");
			customerForm.setComponentValue(customerForm.getTxtCity(), "Citymod 4");
			
			//country is the same
			
			//province combobox
			ComponentAgent cmbProvinceAgent = cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#cmbProvince");
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("Matanzas");
			customerForm.selectComboitemByLabel(customerForm.getCmbProvince(), "Matanzas");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			ComponentAgent cmbMunicipalityAgent = cmbProvinceAgent.query("#cmbMunicipality");
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing("Jagüey Grande");
			customerForm.selectComboitemByLabel(customerForm.getCmbMunicipality(), "Jagüey Grande");
			
			customerForm.setComponentValue(customerForm.getDblLatitude(), 6.0);
			customerForm.setComponentValue(customerForm.getDblLongitude(), -6.0);	
			clickOnApplyButton(customerForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}		
	}
	
	@Test
	final void testCase6() {

		CustomerForm customerForm = null;
		try {
			customerForm = setFormComponents("user1@soberano.syscoop.co", "customers.zul");			
			loadObjectDetails("Customer5fn Customer5ln : c5@soberano.syscoop.co");
			
			customerForm.setComponentValue(customerForm.getTxtFirstName(), "Customer5fnmod");
			customerForm.setComponentValue(customerForm.getTxtLastName(), "Customer5lnmod");
			customerForm.setComponentValue(customerForm.getDecDiscount(), new BigDecimal(0));			
			
			customerForm.setComponentValue(customerForm.getTxtPhoneNumber(), "5355555559");
			customerForm.setComponentValue(customerForm.getTxtEmailAddress(), "c5mod@soberano.syscoop.co");
			customerForm.setComponentValue(customerForm.getTxtAddress(), "Addressmod 5");
			customerForm.setComponentValue(customerForm.getCmbPostalCode(), "Pcmod 5");
			customerForm.setComponentValue(customerForm.getTxtTown(), "Townmod 5");
			customerForm.setComponentValue(customerForm.getTxtCity(), "Citymod 5");
			
			//country is the same
			
			//province combobox
			ComponentAgent cmbProvinceAgent = cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#cmbProvince");
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("Villa Clara");
			customerForm.selectComboitemByLabel(customerForm.getCmbProvince(), "Villa Clara");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			ComponentAgent cmbMunicipalityAgent = cmbProvinceAgent.query("#cmbMunicipality");
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing("Santa Clara");
			customerForm.selectComboitemByLabel(customerForm.getCmbMunicipality(), "Santa Clara");
			
			customerForm.setComponentValue(customerForm.getDblLatitude(), 7.0);
			customerForm.setComponentValue(customerForm.getDblLongitude(), -7.0);	
			clickOnApplyButton(customerForm.getDesktop());
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

		CustomerForm customerForm = null;
		try {
			customerForm = setFormComponents("user1@soberano.syscoop.co", "customers.zul");			
			loadObjectDetails("Customer6fn Customer6ln : c6@soberano.syscoop.co");
			
			customerForm.setComponentValue(customerForm.getTxtFirstName(), "Customer6fnmod");
			customerForm.setComponentValue(customerForm.getTxtLastName(), "Customer6lnmod");
			customerForm.setComponentValue(customerForm.getDecDiscount(), new BigDecimal(10.5));			
			
			customerForm.setComponentValue(customerForm.getTxtPhoneNumber(), "5355555560");
			customerForm.setComponentValue(customerForm.getTxtEmailAddress(), "c6mod@soberano.syscoop.co");
			customerForm.setComponentValue(customerForm.getTxtAddress(), "Addressmod 6");
			customerForm.setComponentValue(customerForm.getCmbPostalCode(), "Pcmod 6");
			customerForm.setComponentValue(customerForm.getTxtTown(), "Townmod 6");
			customerForm.setComponentValue(customerForm.getTxtCity(), "Citymod 6");
			
			//country is the same
			
			//province combobox
			ComponentAgent cmbProvinceAgent = cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#cmbProvince");
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("Isla de la Juventud");
			customerForm.selectComboitemByLabel(customerForm.getCmbProvince(), "Isla de la Juventud");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			ComponentAgent cmbMunicipalityAgent = cmbProvinceAgent.query("#cmbMunicipality");
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing("Isla de la Juventud");
			customerForm.selectComboitemByLabel(customerForm.getCmbMunicipality(), "Isla de la Juventud");
			
			customerForm.setComponentValue(customerForm.getDblLatitude(), 8.0);
			customerForm.setComponentValue(customerForm.getDblLongitude(), -8.0);	
			clickOnApplyButton(customerForm.getDesktop());
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

		CustomerForm customerForm = null;
		try {
			customerForm = setFormComponents("user1@soberano.syscoop.co", "customers.zul");			
			loadObjectDetails("Customer7fn Customer7ln : c7@soberano.syscoop.co");
			
			customerForm.setComponentValue(customerForm.getTxtFirstName(), "Customer7fnmod");
			customerForm.setComponentValue(customerForm.getTxtLastName(), "Customer7lnmod");
			customerForm.setComponentValue(customerForm.getDecDiscount(), new BigDecimal(0));			
			
			customerForm.setComponentValue(customerForm.getTxtPhoneNumber(), "5355555561");
			customerForm.setComponentValue(customerForm.getTxtEmailAddress(), "c7mod@soberano.syscoop.co");
			customerForm.setComponentValue(customerForm.getTxtAddress(), "Addressmod 7");
			customerForm.setComponentValue(customerForm.getCmbPostalCode(), "Pcmod 7");
			customerForm.setComponentValue(customerForm.getTxtTown(), "Townmod 7");
			customerForm.setComponentValue(customerForm.getTxtCity(), "Citymod 7");
			
			//country is the same
			
			//province combobox
			ComponentAgent cmbProvinceAgent = cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#cmbProvince");
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("Artemisa");
			customerForm.selectComboitemByLabel(customerForm.getCmbProvince(), "Artemisa");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			ComponentAgent cmbMunicipalityAgent = cmbProvinceAgent.query("#cmbMunicipality");
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing("Artemisa");
			customerForm.selectComboitemByLabel(customerForm.getCmbMunicipality(), "Artemisa");
			
			customerForm.setComponentValue(customerForm.getDblLatitude(), 9.0);
			customerForm.setComponentValue(customerForm.getDblLongitude(), -9.0);	
			clickOnApplyButton(customerForm.getDesktop());
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

		CustomerForm customerForm = null;
		try {
			customerForm = setFormComponents("user1@soberano.syscoop.co", "customers.zul");			
			loadObjectDetails("Customer8fn Customer8ln : c8@soberano.syscoop.co");
			
			customerForm.setComponentValue(customerForm.getTxtFirstName(), "Customer8fnmod");
			customerForm.setComponentValue(customerForm.getTxtLastName(), "Customer8lnmod");
			customerForm.setComponentValue(customerForm.getDecDiscount(), new BigDecimal(0));			
			
			customerForm.setComponentValue(customerForm.getTxtPhoneNumber(), "5355555562");
			customerForm.setComponentValue(customerForm.getTxtEmailAddress(), "c8mod@soberano.syscoop.co");
			customerForm.setComponentValue(customerForm.getTxtAddress(), "Addressmod 8");
			customerForm.setComponentValue(customerForm.getCmbPostalCode(), "Pcmod 8");
			customerForm.setComponentValue(customerForm.getTxtTown(), "Townmod 8");
			customerForm.setComponentValue(customerForm.getTxtCity(), "Citymod 8");
			
			//country is the same
			
			//province combobox
			ComponentAgent cmbProvinceAgent = cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#cmbProvince");
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("La Habana");
			customerForm.selectComboitemByLabel(customerForm.getCmbProvince(), "La Habana");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			ComponentAgent cmbMunicipalityAgent = cmbProvinceAgent.query("#cmbMunicipality");
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing("Plaza de la Revolución");
			customerForm.selectComboitemByLabel(customerForm.getCmbMunicipality(), "Plaza de la Revolución");
			
			customerForm.setComponentValue(customerForm.getDblLatitude(), 10.0);
			customerForm.setComponentValue(customerForm.getDblLongitude(), -10.0);	
			clickOnApplyButton(customerForm.getDesktop());
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

		CustomerForm customerForm = null;
		try {
			customerForm = setFormComponents("user1@soberano.syscoop.co", "customers.zul");			
			loadObjectDetails("Customer9fn Customer9ln : c9@soberano.syscoop.co");
			
			customerForm.setComponentValue(customerForm.getTxtFirstName(), "Customer9fnmod");
			customerForm.setComponentValue(customerForm.getTxtLastName(), "Customer9lnmod");
			customerForm.setComponentValue(customerForm.getDecDiscount(), new BigDecimal(10));			
			
			customerForm.setComponentValue(customerForm.getTxtPhoneNumber(), "5355555563");
			customerForm.setComponentValue(customerForm.getTxtEmailAddress(), "c9mod@soberano.syscoop.co");
			customerForm.setComponentValue(customerForm.getTxtAddress(), "Addressmod 9");
			customerForm.setComponentValue(customerForm.getCmbPostalCode(), "Pcmod 9");
			customerForm.setComponentValue(customerForm.getTxtTown(), "Townmod 9");
			customerForm.setComponentValue(customerForm.getTxtCity(), "Citymod 9");
			
			//country is the same
			
			//province combobox
			ComponentAgent cmbProvinceAgent = cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#cmbProvince");
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("Mayabeque");
			customerForm.selectComboitemByLabel(customerForm.getCmbProvince(), "Mayabeque");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			ComponentAgent cmbMunicipalityAgent = cmbProvinceAgent.query("#cmbMunicipality");
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing("Santa Cruz del Norte");
			customerForm.selectComboitemByLabel(customerForm.getCmbMunicipality(), "Santa Cruz del Norte");
			
			customerForm.setComponentValue(customerForm.getDblLatitude(), 11.0);
			customerForm.setComponentValue(customerForm.getDblLongitude(), -11.0);	
			clickOnApplyButton(customerForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}		
	}
}
