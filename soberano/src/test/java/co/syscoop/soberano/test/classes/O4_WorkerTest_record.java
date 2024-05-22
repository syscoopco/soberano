package co.syscoop.soberano.test.classes;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

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
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.domain.tracked.Worker;
import co.syscoop.soberano.domain.untracked.Authority;
import co.syscoop.soberano.domain.untracked.Responsibility;
import co.syscoop.soberano.test.helper.WorkerActionTest;
import co.syscoop.soberano.test.helper.WorkerForm;
import co.syscoop.soberano.util.SpringUtility;

@Order(4)

//TODO: enable test
@Disabled

class O4_WorkerTest_record extends WorkerActionTest {
	
	protected WorkerForm workerForm = null;

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
		SpringUtility.setLoggedUserForTesting("user6@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_worker.zul");
		workerForm = new WorkerForm(desktop,
									(desktop.query("textbox").query("#txtUserName")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtPassword")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtConfirmPassword")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtFirstName")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtLastName")).as(Textbox.class), 
									(desktop.query("combobox").query("#cmbResponsibilities")).as(Combobox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtPhoneNumber")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtEmailAddress")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtAddress")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#cmbPostalCode")).as(Combobox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtTown")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtCity")).as(Textbox.class), 
									(desktop.query("combobox").query("#incContactData").query("#cmbCountry")).as(Combobox.class), 
									(desktop.query("combobox").query("#incContactData").query("#cmbProvince")).as(Combobox.class), 
									(desktop.query("combobox").query("#incContactData").query("#cmbMunicipality")).as(Combobox.class), 
									(desktop.query("doublebox").query("#incContactData").query("#dblLatitude")).as(Doublebox.class), 
									(desktop.query("doublebox").query("#incContactData").query("#dblLongitude")).as(Doublebox.class));
		try {
			//this is needed to execute only in the first test. it has to do with testing configuration.
			workerForm.testEachConstrainedObjectIsDeclared();
			workerForm.testEachDeclaredConstrainedObjectIsActuallyConstrained();
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

		SpringUtility.setLoggedUserForTesting("user6@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_worker.zul");
		
		ComponentAgent cmbCountryAgent = desktop.query("combobox").query("#incContactData").query("#cmbCountry");
		ComponentAgent cmbProvinceAgent = desktop.query("combobox").query("#incContactData").query("#cmbProvince");
		ComponentAgent cmbMunicipalityAgent = desktop.query("combobox").query("#incContactData").query("#cmbMunicipality");
		workerForm = new WorkerForm(desktop,
									(desktop.query("textbox").query("#txtUserName")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtPassword")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtConfirmPassword")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtFirstName")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtLastName")).as(Textbox.class), 
									(desktop.query("combobox").query("#cmbResponsibilities")).as(Combobox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtPhoneNumber")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtEmailAddress")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtAddress")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#cmbPostalCode")).as(Combobox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtTown")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtCity")).as(Textbox.class), 
									cmbCountryAgent.as(Combobox.class), 
									cmbProvinceAgent.as(Combobox.class), 
									cmbMunicipalityAgent.as(Combobox.class), 
									(desktop.query("doublebox").query("#incContactData").query("#dblLatitude")).as(Doublebox.class), 
									(desktop.query("doublebox").query("#incContactData").query("#dblLongitude")).as(Doublebox.class));
		try {
			workerForm.setComponentValue(workerForm.getTxtUserName(), "");
			workerForm.setComponentValue(workerForm.getTxtPassword(), "12345");
			workerForm.setComponentValue(workerForm.getTxtConfirmPassword(), "12345");
			workerForm.setComponentValue(workerForm.getTxtFirstName(), "Manager");
			workerForm.setComponentValue(workerForm.getTxtLastName(), "Last Name");			
			
			//responsibilities combobox
			for (Component cmbItem : workerForm.getCmbResponsibilities().getChildren()) {
				if (Integer.parseInt(((Comboitem) cmbItem).getValue()) == 2 /*Manager*/) {
					ComponentAgent btnAssignResponsibility = desktop.query("combobox").query("#cmbResponsibilities").getNextSibling();
					btnAssignResponsibility.click();
					break;
				}
			}			
			
			workerForm.setComponentValue(workerForm.getTxtPhoneNumber(), "55555555");
			workerForm.setComponentValue(workerForm.getTxtEmailAddress(), "");
			workerForm.setComponentValue(workerForm.getTxtAddress(), "Manager Address");
			workerForm.setComponentValue(workerForm.getCmbPostalCode(), "54321");
			workerForm.setComponentValue(workerForm.getTxtTown(), "Manager Town");
			workerForm.setComponentValue(workerForm.getTxtCity(), "Manager City");
						
			//country combobox
			InputAgent cmbCountryInputAgent = cmbCountryAgent.as(InputAgent.class);
			cmbCountryInputAgent.typing("Cuba");
			workerForm.setComponentValue(workerForm.getCmbCountry(), "CU");
			cmbCountryAgent.click(); 	//needed to force province combo population. 
										//cmbCountry's onSelect event isn't triggered under testing
			
			//province combobox
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("La Habana");
			workerForm.selectComboitemByLabel(workerForm.getCmbProvince(), "La Habana");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing("Plaza de la Revolución");
			workerForm.selectComboitemByLabel(workerForm.getCmbMunicipality(), "Plaza de la Revolución");
			workerForm.setComponentValue(workerForm.getDblLatitude(), 21.0);
			workerForm.setComponentValue(workerForm.getDblLongitude(), -81.0);			
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			workerForm.testWrongValueException(ex);
		}
	}
	
	@Test
	final void testCase2() {

		SpringUtility.setLoggedUserForTesting("user6@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_worker.zul");
		
		ComponentAgent cmbCountryAgent = desktop.query("combobox").query("#incContactData").query("#cmbCountry");
		ComponentAgent cmbProvinceAgent = desktop.query("combobox").query("#incContactData").query("#cmbProvince");
		ComponentAgent cmbMunicipalityAgent = desktop.query("combobox").query("#incContactData").query("#cmbMunicipality");
		workerForm = new WorkerForm(desktop,
									(desktop.query("textbox").query("#txtUserName")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtPassword")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtConfirmPassword")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtFirstName")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtLastName")).as(Textbox.class), 
									(desktop.query("combobox").query("#cmbResponsibilities")).as(Combobox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtPhoneNumber")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtEmailAddress")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtAddress")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#cmbPostalCode")).as(Combobox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtTown")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtCity")).as(Textbox.class), 
									cmbCountryAgent.as(Combobox.class), 
									cmbProvinceAgent.as(Combobox.class), 
									cmbMunicipalityAgent.as(Combobox.class), 
									(desktop.query("doublebox").query("#incContactData").query("#dblLatitude")).as(Doublebox.class), 
									(desktop.query("doublebox").query("#incContactData").query("#dblLongitude")).as(Doublebox.class));
		try {
			workerForm.setComponentValue(workerForm.getTxtUserName(), "a");
			workerForm.setComponentValue(workerForm.getTxtPassword(), "12345");
			workerForm.setComponentValue(workerForm.getTxtConfirmPassword(), "12345");
			workerForm.setComponentValue(workerForm.getTxtFirstName(), "Manager");
			workerForm.setComponentValue(workerForm.getTxtLastName(), "Last Name");			
			
			//responsibilities combobox
			for (Component cmbItem : workerForm.getCmbResponsibilities().getChildren()) {
				if (Integer.parseInt(((Comboitem) cmbItem).getValue()) == 2 /*Manager*/) {
					ComponentAgent btnAssignResponsibility = desktop.query("combobox").query("#cmbResponsibilities").getNextSibling();
					btnAssignResponsibility.click();
					break;
				}
			}			
			
			workerForm.setComponentValue(workerForm.getTxtPhoneNumber(), "55555555");
			workerForm.setComponentValue(workerForm.getTxtEmailAddress(), "");
			workerForm.setComponentValue(workerForm.getTxtAddress(), "Manager Address");
			workerForm.setComponentValue(workerForm.getCmbPostalCode(), "54321");
			workerForm.setComponentValue(workerForm.getTxtTown(), "Manager Town");
			workerForm.setComponentValue(workerForm.getTxtCity(), "Manager City");
						
			//country combobox
			InputAgent cmbCountryInputAgent = cmbCountryAgent.as(InputAgent.class);
			cmbCountryInputAgent.typing("Cuba");
			workerForm.setComponentValue(workerForm.getCmbCountry(), "CU");
			cmbCountryAgent.click(); 	//needed to force province combo population. 
										//cmbCountry's onSelect event isn't triggered under testing
			
			//province combobox
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("La Habana");
			workerForm.selectComboitemByLabel(workerForm.getCmbProvince(), "La Habana");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing("Plaza de la Revolución");
			workerForm.selectComboitemByLabel(workerForm.getCmbMunicipality(), "Plaza de la Revolución");
			workerForm.setComponentValue(workerForm.getDblLatitude(), 21.0);
			workerForm.setComponentValue(workerForm.getDblLongitude(), -81.0);			
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			workerForm.testWrongValueException(ex);
		}
	}
	
	@Test
	final void testCase3() {

		SpringUtility.setLoggedUserForTesting("user6@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_worker.zul");
		
		ComponentAgent cmbCountryAgent = desktop.query("combobox").query("#incContactData").query("#cmbCountry");
		ComponentAgent cmbProvinceAgent = desktop.query("combobox").query("#incContactData").query("#cmbProvince");
		ComponentAgent cmbMunicipalityAgent = desktop.query("combobox").query("#incContactData").query("#cmbMunicipality");
		workerForm = new WorkerForm(desktop,
									(desktop.query("textbox").query("#txtUserName")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtPassword")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtConfirmPassword")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtFirstName")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtLastName")).as(Textbox.class), 
									(desktop.query("combobox").query("#cmbResponsibilities")).as(Combobox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtPhoneNumber")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtEmailAddress")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtAddress")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#cmbPostalCode")).as(Combobox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtTown")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtCity")).as(Textbox.class), 
									cmbCountryAgent.as(Combobox.class), 
									cmbProvinceAgent.as(Combobox.class), 
									cmbMunicipalityAgent.as(Combobox.class), 
									(desktop.query("doublebox").query("#incContactData").query("#dblLatitude")).as(Doublebox.class), 
									(desktop.query("doublebox").query("#incContactData").query("#dblLongitude")).as(Doublebox.class));
		try {
			workerForm.setComponentValue(workerForm.getTxtUserName(), "manager@soberano.syscoop.co");
			workerForm.setComponentValue(workerForm.getTxtPassword(), "");
			workerForm.setComponentValue(workerForm.getTxtConfirmPassword(), "12345");
			workerForm.setComponentValue(workerForm.getTxtFirstName(), "Manager");
			workerForm.setComponentValue(workerForm.getTxtLastName(), "Last Name");			
			
			//responsibilities combobox
			for (Component cmbItem : workerForm.getCmbResponsibilities().getChildren()) {
				if (Integer.parseInt(((Comboitem) cmbItem).getValue()) == 2 /*Manager*/) {
					ComponentAgent btnAssignResponsibility = desktop.query("combobox").query("#cmbResponsibilities").getNextSibling();
					btnAssignResponsibility.click();
					break;
				}
			}			
			
			workerForm.setComponentValue(workerForm.getTxtPhoneNumber(), "55555555");
			workerForm.setComponentValue(workerForm.getTxtEmailAddress(), "");
			workerForm.setComponentValue(workerForm.getTxtAddress(), "Manager Address");
			workerForm.setComponentValue(workerForm.getCmbPostalCode(), "54321");
			workerForm.setComponentValue(workerForm.getTxtTown(), "Manager Town");
			workerForm.setComponentValue(workerForm.getTxtCity(), "Manager City");
						
			//country combobox
			InputAgent cmbCountryInputAgent = cmbCountryAgent.as(InputAgent.class);
			cmbCountryInputAgent.typing("Cuba");
			workerForm.setComponentValue(workerForm.getCmbCountry(), "CU");
			cmbCountryAgent.click(); 	//needed to force province combo population. 
										//cmbCountry's onSelect event isn't triggered under testing
			
			//province combobox
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("La Habana");
			workerForm.selectComboitemByLabel(workerForm.getCmbProvince(), "La Habana");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing("Plaza de la Revolución");
			workerForm.selectComboitemByLabel(workerForm.getCmbMunicipality(), "Plaza de la Revolución");
			workerForm.setComponentValue(workerForm.getDblLatitude(), 21.0);
			workerForm.setComponentValue(workerForm.getDblLongitude(), -81.0);			
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			workerForm.testWrongValueException(ex);
		}
	}
	
	@Test
	final void testCase4() {

		SpringUtility.setLoggedUserForTesting("user6@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_worker.zul");
		
		ComponentAgent cmbCountryAgent = desktop.query("combobox").query("#incContactData").query("#cmbCountry");
		ComponentAgent cmbProvinceAgent = desktop.query("combobox").query("#incContactData").query("#cmbProvince");
		ComponentAgent cmbMunicipalityAgent = desktop.query("combobox").query("#incContactData").query("#cmbMunicipality");
		workerForm = new WorkerForm(desktop,
									(desktop.query("textbox").query("#txtUserName")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtPassword")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtConfirmPassword")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtFirstName")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtLastName")).as(Textbox.class), 
									(desktop.query("combobox").query("#cmbResponsibilities")).as(Combobox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtPhoneNumber")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtEmailAddress")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtAddress")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#cmbPostalCode")).as(Combobox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtTown")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtCity")).as(Textbox.class), 
									cmbCountryAgent.as(Combobox.class), 
									cmbProvinceAgent.as(Combobox.class), 
									cmbMunicipalityAgent.as(Combobox.class), 
									(desktop.query("doublebox").query("#incContactData").query("#dblLatitude")).as(Doublebox.class), 
									(desktop.query("doublebox").query("#incContactData").query("#dblLongitude")).as(Doublebox.class));
		try {
			workerForm.setComponentValue(workerForm.getTxtUserName(), "manager@soberano.syscoop.co");
			workerForm.setComponentValue(workerForm.getTxtPassword(), "12345");
			workerForm.setComponentValue(workerForm.getTxtConfirmPassword(), "");
			workerForm.setComponentValue(workerForm.getTxtFirstName(), "Manager");
			workerForm.setComponentValue(workerForm.getTxtLastName(), "Last Name");			
			
			//responsibilities combobox
			for (Component cmbItem : workerForm.getCmbResponsibilities().getChildren()) {
				if (Integer.parseInt(((Comboitem) cmbItem).getValue()) == 2 /*Manager*/) {
					ComponentAgent btnAssignResponsibility = desktop.query("combobox").query("#cmbResponsibilities").getNextSibling();
					btnAssignResponsibility.click();
					break;
				}
			}			
			
			workerForm.setComponentValue(workerForm.getTxtPhoneNumber(), "55555555");
			workerForm.setComponentValue(workerForm.getTxtEmailAddress(), "");
			workerForm.setComponentValue(workerForm.getTxtAddress(), "Manager Address");
			workerForm.setComponentValue(workerForm.getCmbPostalCode(), "54321");
			workerForm.setComponentValue(workerForm.getTxtTown(), "Manager Town");
			workerForm.setComponentValue(workerForm.getTxtCity(), "Manager City");
						
			//country combobox
			InputAgent cmbCountryInputAgent = cmbCountryAgent.as(InputAgent.class);
			cmbCountryInputAgent.typing("Cuba");
			workerForm.setComponentValue(workerForm.getCmbCountry(), "CU");
			cmbCountryAgent.click(); 	//needed to force province combo population. 
										//cmbCountry's onSelect event isn't triggered under testing
			
			//province combobox
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("La Habana");
			workerForm.selectComboitemByLabel(workerForm.getCmbProvince(), "La Habana");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing("Plaza de la Revolución");
			workerForm.selectComboitemByLabel(workerForm.getCmbMunicipality(), "Plaza de la Revolución");
			workerForm.setComponentValue(workerForm.getDblLatitude(), 21.0);
			workerForm.setComponentValue(workerForm.getDblLongitude(), -81.0);			
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			workerForm.testWrongValueException(ex);
		}
	}
	
	@Test
	final void testCase5() {

		SpringUtility.setLoggedUserForTesting("user6@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_worker.zul");
		
		ComponentAgent cmbCountryAgent = desktop.query("combobox").query("#incContactData").query("#cmbCountry");
		ComponentAgent cmbProvinceAgent = desktop.query("combobox").query("#incContactData").query("#cmbProvince");
		ComponentAgent cmbMunicipalityAgent = desktop.query("combobox").query("#incContactData").query("#cmbMunicipality");
		workerForm = new WorkerForm(desktop,
									(desktop.query("textbox").query("#txtUserName")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtPassword")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtConfirmPassword")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtFirstName")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtLastName")).as(Textbox.class), 
									(desktop.query("combobox").query("#cmbResponsibilities")).as(Combobox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtPhoneNumber")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtEmailAddress")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtAddress")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#cmbPostalCode")).as(Combobox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtTown")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtCity")).as(Textbox.class), 
									cmbCountryAgent.as(Combobox.class), 
									cmbProvinceAgent.as(Combobox.class), 
									cmbMunicipalityAgent.as(Combobox.class), 
									(desktop.query("doublebox").query("#incContactData").query("#dblLatitude")).as(Doublebox.class), 
									(desktop.query("doublebox").query("#incContactData").query("#dblLongitude")).as(Doublebox.class));
		try {
			workerForm.setComponentValue(workerForm.getTxtUserName(), "manager@soberano.syscoop.co");
			workerForm.setComponentValue(workerForm.getTxtPassword(), "12345");
			workerForm.setComponentValue(workerForm.getTxtConfirmPassword(), "12345");
			workerForm.setComponentValue(workerForm.getTxtFirstName(), "");
			workerForm.setComponentValue(workerForm.getTxtLastName(), "Last Name");			
			
			//responsibilities combobox
			for (Component cmbItem : workerForm.getCmbResponsibilities().getChildren()) {
				if (Integer.parseInt(((Comboitem) cmbItem).getValue()) == 2 /*Manager*/) {
					ComponentAgent btnAssignResponsibility = desktop.query("combobox").query("#cmbResponsibilities").getNextSibling();
					btnAssignResponsibility.click();
					break;
				}
			}			
			
			workerForm.setComponentValue(workerForm.getTxtPhoneNumber(), "55555555");
			workerForm.setComponentValue(workerForm.getTxtEmailAddress(), "");
			workerForm.setComponentValue(workerForm.getTxtAddress(), "Manager Address");
			workerForm.setComponentValue(workerForm.getCmbPostalCode(), "54321");
			workerForm.setComponentValue(workerForm.getTxtTown(), "Manager Town");
			workerForm.setComponentValue(workerForm.getTxtCity(), "Manager City");
						
			//country combobox
			InputAgent cmbCountryInputAgent = cmbCountryAgent.as(InputAgent.class);
			cmbCountryInputAgent.typing("Cuba");
			workerForm.setComponentValue(workerForm.getCmbCountry(), "CU");
			cmbCountryAgent.click(); 	//needed to force province combo population. 
										//cmbCountry's onSelect event isn't triggered under testing
			
			//province combobox
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("La Habana");
			workerForm.selectComboitemByLabel(workerForm.getCmbProvince(), "La Habana");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing("Plaza de la Revolución");
			workerForm.selectComboitemByLabel(workerForm.getCmbMunicipality(), "Plaza de la Revolución");
			workerForm.setComponentValue(workerForm.getDblLatitude(), 21.0);
			workerForm.setComponentValue(workerForm.getDblLongitude(), -81.0);			
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			workerForm.testWrongValueException(ex);
		}
	}
	
	@Test
	final void testCase6() {

		SpringUtility.setLoggedUserForTesting("user6@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_worker.zul");
		
		ComponentAgent cmbCountryAgent = desktop.query("combobox").query("#incContactData").query("#cmbCountry");
		ComponentAgent cmbProvinceAgent = desktop.query("combobox").query("#incContactData").query("#cmbProvince");
		ComponentAgent cmbMunicipalityAgent = desktop.query("combobox").query("#incContactData").query("#cmbMunicipality");
		workerForm = new WorkerForm(desktop,
									(desktop.query("textbox").query("#txtUserName")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtPassword")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtConfirmPassword")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtFirstName")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtLastName")).as(Textbox.class), 
									(desktop.query("combobox").query("#cmbResponsibilities")).as(Combobox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtPhoneNumber")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtEmailAddress")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtAddress")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#cmbPostalCode")).as(Combobox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtTown")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtCity")).as(Textbox.class), 
									cmbCountryAgent.as(Combobox.class), 
									cmbProvinceAgent.as(Combobox.class), 
									cmbMunicipalityAgent.as(Combobox.class), 
									(desktop.query("doublebox").query("#incContactData").query("#dblLatitude")).as(Doublebox.class), 
									(desktop.query("doublebox").query("#incContactData").query("#dblLongitude")).as(Doublebox.class));
		try {
			workerForm.setComponentValue(workerForm.getTxtUserName(), "manager@soberano.syscoop.co");
			workerForm.setComponentValue(workerForm.getTxtPassword(), "12345");
			workerForm.setComponentValue(workerForm.getTxtConfirmPassword(), "12345");
			workerForm.setComponentValue(workerForm.getTxtFirstName(), "Manager");
			workerForm.setComponentValue(workerForm.getTxtLastName(), "");			
			
			//responsibilities combobox
			for (Component cmbItem : workerForm.getCmbResponsibilities().getChildren()) {
				if (Integer.parseInt(((Comboitem) cmbItem).getValue()) == 2 /*Manager*/) {
					ComponentAgent btnAssignResponsibility = desktop.query("combobox").query("#cmbResponsibilities").getNextSibling();
					btnAssignResponsibility.click();
					break;
				}
			}			
			
			workerForm.setComponentValue(workerForm.getTxtPhoneNumber(), "55555555");
			workerForm.setComponentValue(workerForm.getTxtEmailAddress(), "");
			workerForm.setComponentValue(workerForm.getTxtAddress(), "Manager Address");
			workerForm.setComponentValue(workerForm.getCmbPostalCode(), "54321");
			workerForm.setComponentValue(workerForm.getTxtTown(), "Manager Town");
			workerForm.setComponentValue(workerForm.getTxtCity(), "Manager City");
						
			//country combobox
			InputAgent cmbCountryInputAgent = cmbCountryAgent.as(InputAgent.class);
			cmbCountryInputAgent.typing("Cuba");
			workerForm.setComponentValue(workerForm.getCmbCountry(), "CU");
			cmbCountryAgent.click(); 	//needed to force province combo population. 
										//cmbCountry's onSelect event isn't triggered under testing
			
			//province combobox
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("La Habana");
			workerForm.selectComboitemByLabel(workerForm.getCmbProvince(), "La Habana");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing("Plaza de la Revolución");
			workerForm.selectComboitemByLabel(workerForm.getCmbMunicipality(), "Plaza de la Revolución");
			workerForm.setComponentValue(workerForm.getDblLatitude(), 21.0);
			workerForm.setComponentValue(workerForm.getDblLongitude(), -81.0);			
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			workerForm.testWrongValueException(ex);
		}
	}
	
	@Test
	final void testCase7() {

		SpringUtility.setLoggedUserForTesting("user6@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_worker.zul");
		
		ComponentAgent cmbCountryAgent = desktop.query("combobox").query("#incContactData").query("#cmbCountry");
		ComponentAgent cmbProvinceAgent = desktop.query("combobox").query("#incContactData").query("#cmbProvince");
		ComponentAgent cmbMunicipalityAgent = desktop.query("combobox").query("#incContactData").query("#cmbMunicipality");
		workerForm = new WorkerForm(desktop,
									(desktop.query("textbox").query("#txtUserName")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtPassword")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtConfirmPassword")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtFirstName")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtLastName")).as(Textbox.class), 
									(desktop.query("combobox").query("#cmbResponsibilities")).as(Combobox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtPhoneNumber")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtEmailAddress")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtAddress")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#cmbPostalCode")).as(Combobox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtTown")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtCity")).as(Textbox.class), 
									cmbCountryAgent.as(Combobox.class), 
									cmbProvinceAgent.as(Combobox.class), 
									cmbMunicipalityAgent.as(Combobox.class), 
									(desktop.query("doublebox").query("#incContactData").query("#dblLatitude")).as(Doublebox.class), 
									(desktop.query("doublebox").query("#incContactData").query("#dblLongitude")).as(Doublebox.class));
		try {
			workerForm.setComponentValue(workerForm.getTxtUserName(), "manager@soberano.syscoop.co");
			workerForm.setComponentValue(workerForm.getTxtPassword(), "12345");
			workerForm.setComponentValue(workerForm.getTxtConfirmPassword(), "12345");
			workerForm.setComponentValue(workerForm.getTxtFirstName(), "Manager");
			workerForm.setComponentValue(workerForm.getTxtLastName(), "Last Name");			
			
			//responsibilities combobox
			for (Component cmbItem : workerForm.getCmbResponsibilities().getChildren()) {
				if (Integer.parseInt(((Comboitem) cmbItem).getValue()) == 2 /*Manager*/) {
					ComponentAgent btnAssignResponsibility = desktop.query("combobox").query("#cmbResponsibilities").getNextSibling();
					btnAssignResponsibility.click();
					break;
				}
			}			
			
			workerForm.setComponentValue(workerForm.getTxtPhoneNumber(), "55555555");
			workerForm.setComponentValue(workerForm.getTxtEmailAddress(), "");
			workerForm.setComponentValue(workerForm.getTxtAddress(), "Manager Address");
			workerForm.setComponentValue(workerForm.getCmbPostalCode(), "54321");
			workerForm.setComponentValue(workerForm.getTxtTown(), "Manager Town");
			workerForm.setComponentValue(workerForm.getTxtCity(), "Manager City");		
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			workerForm.testWrongValueException(ex);
		}
	}
	
	@Test
	final void testCase8() {

		SpringUtility.setLoggedUserForTesting("user6@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_worker.zul");
		
		ComponentAgent cmbCountryAgent = desktop.query("combobox").query("#incContactData").query("#cmbCountry");
		ComponentAgent cmbProvinceAgent = desktop.query("combobox").query("#incContactData").query("#cmbProvince");
		ComponentAgent cmbMunicipalityAgent = desktop.query("combobox").query("#incContactData").query("#cmbMunicipality");
		workerForm = new WorkerForm(desktop,
									(desktop.query("textbox").query("#txtUserName")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtPassword")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtConfirmPassword")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtFirstName")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtLastName")).as(Textbox.class), 
									(desktop.query("combobox").query("#cmbResponsibilities")).as(Combobox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtPhoneNumber")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtEmailAddress")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtAddress")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#cmbPostalCode")).as(Combobox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtTown")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtCity")).as(Textbox.class), 
									cmbCountryAgent.as(Combobox.class), 
									cmbProvinceAgent.as(Combobox.class), 
									cmbMunicipalityAgent.as(Combobox.class), 
									(desktop.query("doublebox").query("#incContactData").query("#dblLatitude")).as(Doublebox.class), 
									(desktop.query("doublebox").query("#incContactData").query("#dblLongitude")).as(Doublebox.class));
		try {
			workerForm.setComponentValue(workerForm.getTxtUserName(), "manager@soberano.syscoop.co");
			workerForm.setComponentValue(workerForm.getTxtPassword(), "12345");
			workerForm.setComponentValue(workerForm.getTxtConfirmPassword(), "12345");
			workerForm.setComponentValue(workerForm.getTxtFirstName(), "Manager");
			workerForm.setComponentValue(workerForm.getTxtLastName(), "Last Name");			
			
			//responsibilities combobox
			for (Component cmbItem : workerForm.getCmbResponsibilities().getChildren()) {
				if (Integer.parseInt(((Comboitem) cmbItem).getValue()) == 2 /*Manager*/) {
					ComponentAgent btnAssignResponsibility = desktop.query("combobox").query("#cmbResponsibilities").getNextSibling();
					btnAssignResponsibility.click();
					break;
				}
			}			
			
			workerForm.setComponentValue(workerForm.getTxtPhoneNumber(), "55555555");
			workerForm.setComponentValue(workerForm.getTxtEmailAddress(), "");
			workerForm.setComponentValue(workerForm.getTxtAddress(), "Manager Address");
			workerForm.setComponentValue(workerForm.getCmbPostalCode(), "54321");
			workerForm.setComponentValue(workerForm.getTxtTown(), "Manager Town");
			workerForm.setComponentValue(workerForm.getTxtCity(), "Manager City");
						
			//country combobox
			InputAgent cmbCountryInputAgent = cmbCountryAgent.as(InputAgent.class);
			cmbCountryInputAgent.typing("Cuba");
			workerForm.setComponentValue(workerForm.getCmbCountry(), "CU");
			cmbCountryAgent.click(); 	//needed to force province combo population. 
										//cmbCountry's onSelect event isn't triggered under testing		
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			workerForm.testWrongValueException(ex);
		}
	}
	
	@Test
	final void testCase9() {

		SpringUtility.setLoggedUserForTesting("user6@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_worker.zul");
		
		ComponentAgent cmbCountryAgent = desktop.query("combobox").query("#incContactData").query("#cmbCountry");
		ComponentAgent cmbProvinceAgent = desktop.query("combobox").query("#incContactData").query("#cmbProvince");
		ComponentAgent cmbMunicipalityAgent = desktop.query("combobox").query("#incContactData").query("#cmbMunicipality");
		workerForm = new WorkerForm(desktop,
									(desktop.query("textbox").query("#txtUserName")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtPassword")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtConfirmPassword")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtFirstName")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtLastName")).as(Textbox.class), 
									(desktop.query("combobox").query("#cmbResponsibilities")).as(Combobox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtPhoneNumber")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtEmailAddress")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtAddress")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#cmbPostalCode")).as(Combobox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtTown")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtCity")).as(Textbox.class), 
									cmbCountryAgent.as(Combobox.class), 
									cmbProvinceAgent.as(Combobox.class), 
									cmbMunicipalityAgent.as(Combobox.class), 
									(desktop.query("doublebox").query("#incContactData").query("#dblLatitude")).as(Doublebox.class), 
									(desktop.query("doublebox").query("#incContactData").query("#dblLongitude")).as(Doublebox.class));
		try {
			workerForm.setComponentValue(workerForm.getTxtUserName(), "manager@soberano.syscoop.co");
			workerForm.setComponentValue(workerForm.getTxtPassword(), "12345");
			workerForm.setComponentValue(workerForm.getTxtConfirmPassword(), "12345");
			workerForm.setComponentValue(workerForm.getTxtFirstName(), "Manager");
			workerForm.setComponentValue(workerForm.getTxtLastName(), "Last Name");			
			
			//responsibilities combobox
			for (Component cmbItem : workerForm.getCmbResponsibilities().getChildren()) {
				if (Integer.parseInt(((Comboitem) cmbItem).getValue()) == 2 /*Manager*/) {
					ComponentAgent btnAssignResponsibility = desktop.query("combobox").query("#cmbResponsibilities").getNextSibling();
					btnAssignResponsibility.click();
					break;
				}
			}			
			
			workerForm.setComponentValue(workerForm.getTxtPhoneNumber(), "55555555");
			workerForm.setComponentValue(workerForm.getTxtEmailAddress(), "");
			workerForm.setComponentValue(workerForm.getTxtAddress(), "Manager Address");
			workerForm.setComponentValue(workerForm.getCmbPostalCode(), "54321");
			workerForm.setComponentValue(workerForm.getTxtTown(), "Manager Town");
			workerForm.setComponentValue(workerForm.getTxtCity(), "Manager City");
						
			//country combobox
			InputAgent cmbCountryInputAgent = cmbCountryAgent.as(InputAgent.class);
			cmbCountryInputAgent.typing("Cuba");
			workerForm.setComponentValue(workerForm.getCmbCountry(), "CU");
			cmbCountryAgent.click(); 	//needed to force province combo population. 
										//cmbCountry's onSelect event isn't triggered under testing
			
			//province combobox
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("La Habana");
			workerForm.selectComboitemByLabel(workerForm.getCmbProvince(), "La Habana");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			workerForm.testWrongValueException(ex);
		}
	}
	
	@Test
	final void testCase10() {

		SpringUtility.setLoggedUserForTesting("user6@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_worker.zul");
		
		ComponentAgent cmbCountryAgent = desktop.query("combobox").query("#incContactData").query("#cmbCountry");
		ComponentAgent cmbProvinceAgent = desktop.query("combobox").query("#incContactData").query("#cmbProvince");
		ComponentAgent cmbMunicipalityAgent = desktop.query("combobox").query("#incContactData").query("#cmbMunicipality");
		workerForm = new WorkerForm(desktop,
									(desktop.query("textbox").query("#txtUserName")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtPassword")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtConfirmPassword")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtFirstName")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtLastName")).as(Textbox.class), 
									(desktop.query("combobox").query("#cmbResponsibilities")).as(Combobox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtPhoneNumber")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtEmailAddress")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtAddress")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#cmbPostalCode")).as(Combobox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtTown")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtCity")).as(Textbox.class), 
									cmbCountryAgent.as(Combobox.class), 
									cmbProvinceAgent.as(Combobox.class), 
									cmbMunicipalityAgent.as(Combobox.class), 
									(desktop.query("doublebox").query("#incContactData").query("#dblLatitude")).as(Doublebox.class), 
									(desktop.query("doublebox").query("#incContactData").query("#dblLongitude")).as(Doublebox.class));
		try {
			workerForm.setComponentValue(workerForm.getTxtUserName(), "manager@soberano.syscoop.co");
			workerForm.setComponentValue(workerForm.getTxtPassword(), "12345");
			workerForm.setComponentValue(workerForm.getTxtConfirmPassword(), "1234");
			workerForm.setComponentValue(workerForm.getTxtFirstName(), "Manager");
			workerForm.setComponentValue(workerForm.getTxtLastName(), "Last Name");			
			
			//responsibilities combobox
			for (Component cmbItem : workerForm.getCmbResponsibilities().getChildren()) {
				if (Integer.parseInt(((Comboitem) cmbItem).getValue()) == 2 /*Manager*/) {
					ComponentAgent btnAssignResponsibility = desktop.query("combobox").query("#cmbResponsibilities").getNextSibling();
					btnAssignResponsibility.click();
					break;
				}
			}			
			
			workerForm.setComponentValue(workerForm.getTxtPhoneNumber(), "55555555");
			workerForm.setComponentValue(workerForm.getTxtEmailAddress(), "");
			workerForm.setComponentValue(workerForm.getTxtAddress(), "Manager Address");
			workerForm.setComponentValue(workerForm.getCmbPostalCode(), "54321");
			workerForm.setComponentValue(workerForm.getTxtTown(), "Manager Town");
			workerForm.setComponentValue(workerForm.getTxtCity(), "Manager City");
						
			//country combobox
			InputAgent cmbCountryInputAgent = cmbCountryAgent.as(InputAgent.class);
			cmbCountryInputAgent.typing("Cuba");
			workerForm.setComponentValue(workerForm.getCmbCountry(), "CU");
			cmbCountryAgent.click(); 	//needed to force province combo population. 
										//cmbCountry's onSelect event isn't triggered under testing
			
			//province combobox
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("La Habana");
			workerForm.selectComboitemByLabel(workerForm.getCmbProvince(), "La Habana");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing("Plaza de la Revolución");
			workerForm.selectComboitemByLabel(workerForm.getCmbMunicipality(), "Plaza de la Revolución");
			workerForm.setComponentValue(workerForm.getDblLatitude(), 21.0);
			workerForm.setComponentValue(workerForm.getDblLongitude(), -81.0);
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testPasswordsMustMatchException(ex);
		}
	}
	
	@Test
	final void testCase11() {

		SpringUtility.setLoggedUserForTesting("user6@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_worker.zul");
		
		ComponentAgent cmbCountryAgent = desktop.query("combobox").query("#incContactData").query("#cmbCountry");
		ComponentAgent cmbProvinceAgent = desktop.query("combobox").query("#incContactData").query("#cmbProvince");
		ComponentAgent cmbMunicipalityAgent = desktop.query("combobox").query("#incContactData").query("#cmbMunicipality");
		workerForm = new WorkerForm(desktop,
									(desktop.query("textbox").query("#txtUserName")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtPassword")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtConfirmPassword")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtFirstName")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtLastName")).as(Textbox.class), 
									(desktop.query("combobox").query("#cmbResponsibilities")).as(Combobox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtPhoneNumber")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtEmailAddress")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtAddress")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#cmbPostalCode")).as(Combobox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtTown")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtCity")).as(Textbox.class), 
									cmbCountryAgent.as(Combobox.class), 
									cmbProvinceAgent.as(Combobox.class), 
									cmbMunicipalityAgent.as(Combobox.class), 
									(desktop.query("doublebox").query("#incContactData").query("#dblLatitude")).as(Doublebox.class), 
									(desktop.query("doublebox").query("#incContactData").query("#dblLongitude")).as(Doublebox.class));
		try {
			workerForm.setComponentValue(workerForm.getTxtUserName(), "manager@soberano.syscoop.co");
			workerForm.setComponentValue(workerForm.getTxtPassword(), "12345");
			workerForm.setComponentValue(workerForm.getTxtConfirmPassword(), "12345");
			workerForm.setComponentValue(workerForm.getTxtFirstName(), "Manager");
			workerForm.setComponentValue(workerForm.getTxtLastName(), "Last Name");			
			
			//responsibilities combobox
			for (Component cmbItem : workerForm.getCmbResponsibilities().getChildren()) {
				if (Integer.parseInt(((Comboitem) cmbItem).getValue()) == 2 /*Manager*/) {
					//ComponentAgent btnAssignResponsibility = desktop.query("combobox").query("#cmbResponsibilities").getNextSibling();
					//btnAssignResponsibility.click();
					break;
				}
			}			
			
			workerForm.setComponentValue(workerForm.getTxtPhoneNumber(), "55555555");
			workerForm.setComponentValue(workerForm.getTxtEmailAddress(), "");
			workerForm.setComponentValue(workerForm.getTxtAddress(), "Manager Address");
			workerForm.setComponentValue(workerForm.getCmbPostalCode(), "54321");
			workerForm.setComponentValue(workerForm.getTxtTown(), "Manager Town");
			workerForm.setComponentValue(workerForm.getTxtCity(), "Manager City");
						
			//country combobox
			InputAgent cmbCountryInputAgent = cmbCountryAgent.as(InputAgent.class);
			cmbCountryInputAgent.typing("Cuba");
			workerForm.setComponentValue(workerForm.getCmbCountry(), "CU");
			cmbCountryAgent.click(); 	//needed to force province combo population. 
										//cmbCountry's onSelect event isn't triggered under testing
			
			//province combobox
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("La Habana");
			workerForm.selectComboitemByLabel(workerForm.getCmbProvince(), "La Habana");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing("Plaza de la Revolución");
			workerForm.selectComboitemByLabel(workerForm.getCmbMunicipality(), "Plaza de la Revolución");
			workerForm.setComponentValue(workerForm.getDblLatitude(), 21.0);
			workerForm.setComponentValue(workerForm.getDblLongitude(), -81.0);
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testWorkerMustBeAssignedToAResponsibilityException(ex);
		}
	}
	
	@Test
	final void testCase12() {

		SpringUtility.setLoggedUserForTesting("user6@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_worker.zul");
		
		ComponentAgent cmbCountryAgent = desktop.query("combobox").query("#incContactData").query("#cmbCountry");
		ComponentAgent cmbProvinceAgent = desktop.query("combobox").query("#incContactData").query("#cmbProvince");
		ComponentAgent cmbMunicipalityAgent = desktop.query("combobox").query("#incContactData").query("#cmbMunicipality");
		workerForm = new WorkerForm(desktop,
									(desktop.query("textbox").query("#txtUserName")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtPassword")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtConfirmPassword")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtFirstName")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtLastName")).as(Textbox.class), 
									(desktop.query("combobox").query("#cmbResponsibilities")).as(Combobox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtPhoneNumber")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtEmailAddress")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtAddress")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#cmbPostalCode")).as(Combobox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtTown")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtCity")).as(Textbox.class), 
									cmbCountryAgent.as(Combobox.class), 
									cmbProvinceAgent.as(Combobox.class), 
									cmbMunicipalityAgent.as(Combobox.class), 
									(desktop.query("doublebox").query("#incContactData").query("#dblLatitude")).as(Doublebox.class), 
									(desktop.query("doublebox").query("#incContactData").query("#dblLongitude")).as(Doublebox.class));
		try {
			workerForm.setComponentValue(workerForm.getTxtUserName(), "manager@soberano.syscoop.co");
			workerForm.setComponentValue(workerForm.getTxtPassword(), "12345");
			workerForm.setComponentValue(workerForm.getTxtConfirmPassword(), "12345");
			workerForm.setComponentValue(workerForm.getTxtFirstName(), "Manager");
			workerForm.setComponentValue(workerForm.getTxtLastName(), "Last Name");			
			
			//responsibilities combobox
			for (Component cmbItem : workerForm.getCmbResponsibilities().getChildren()) {
				if (Integer.parseInt(((Comboitem) cmbItem).getValue()) == 2 /*Manager*/) {
					ComponentAgent btnAssignResponsibility = desktop.query("combobox").query("#cmbResponsibilities").getNextSibling();
					btnAssignResponsibility.click();
					break;
				}
			}			
			
			workerForm.setComponentValue(workerForm.getTxtPhoneNumber(), "55555555");
			workerForm.setComponentValue(workerForm.getTxtEmailAddress(), "");
			workerForm.setComponentValue(workerForm.getTxtAddress(), "Manager Address");
			workerForm.setComponentValue(workerForm.getCmbPostalCode(), "54321");
			workerForm.setComponentValue(workerForm.getTxtTown(), "Manager Town");
			workerForm.setComponentValue(workerForm.getTxtCity(), "Manager City");
						
			//country combobox
			InputAgent cmbCountryInputAgent = cmbCountryAgent.as(InputAgent.class);
			cmbCountryInputAgent.typing("Cuba");
			workerForm.setComponentValue(workerForm.getCmbCountry(), "CU");
			cmbCountryAgent.click(); 	//needed to force province combo population. 
										//cmbCountry's onSelect event isn't triggered under testing
			
			//province combobox
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("La Habana");
			workerForm.selectComboitemByLabel(workerForm.getCmbProvince(), "La Habana");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing("Plaza de la Revolución");
			workerForm.selectComboitemByLabel(workerForm.getCmbMunicipality(), "Plaza de la Revolución");
			workerForm.setComponentValue(workerForm.getDblLatitude(), 21.0);
			workerForm.setComponentValue(workerForm.getDblLongitude(), -81.0);
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
	final void testCase13() {

		SpringUtility.setLoggedUserForTesting("user6@soberano.syscoop.co");
		DesktopAgent desktop = Zats.newClient().connect("/new_worker.zul");
		
		ComponentAgent cmbCountryAgent = desktop.query("combobox").query("#incContactData").query("#cmbCountry");
		ComponentAgent cmbProvinceAgent = desktop.query("combobox").query("#incContactData").query("#cmbProvince");
		ComponentAgent cmbMunicipalityAgent = desktop.query("combobox").query("#incContactData").query("#cmbMunicipality");
		workerForm = new WorkerForm(desktop,
									(desktop.query("textbox").query("#txtUserName")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtPassword")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtConfirmPassword")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtFirstName")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtLastName")).as(Textbox.class), 
									(desktop.query("combobox").query("#cmbResponsibilities")).as(Combobox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtPhoneNumber")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtEmailAddress")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtAddress")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#cmbPostalCode")).as(Combobox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtTown")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtCity")).as(Textbox.class), 
									cmbCountryAgent.as(Combobox.class), 
									cmbProvinceAgent.as(Combobox.class), 
									cmbMunicipalityAgent.as(Combobox.class), 
									(desktop.query("doublebox").query("#incContactData").query("#dblLatitude")).as(Doublebox.class), 
									(desktop.query("doublebox").query("#incContactData").query("#dblLongitude")).as(Doublebox.class));
		try {
			workerForm.setComponentValue(workerForm.getTxtUserName(), "manager@soberano.syscoop.co");
			workerForm.setComponentValue(workerForm.getTxtPassword(), "12345");
			workerForm.setComponentValue(workerForm.getTxtConfirmPassword(), "12345");
			workerForm.setComponentValue(workerForm.getTxtFirstName(), "Manager");
			workerForm.setComponentValue(workerForm.getTxtLastName(), "Last Name");			
			
			//responsibilities combobox
			for (Component cmbItem : workerForm.getCmbResponsibilities().getChildren()) {
				if (Integer.parseInt(((Comboitem) cmbItem).getValue()) == 2 /*Manager*/) {
					ComponentAgent btnAssignResponsibility = desktop.query("combobox").query("#cmbResponsibilities").getNextSibling();
					btnAssignResponsibility.click();
					break;
				}
			}			
			
			workerForm.setComponentValue(workerForm.getTxtPhoneNumber(), "");
			workerForm.setComponentValue(workerForm.getTxtEmailAddress(), "");
			workerForm.setComponentValue(workerForm.getTxtAddress(), "Manager Address");
			workerForm.setComponentValue(workerForm.getCmbPostalCode(), "54321");
			workerForm.setComponentValue(workerForm.getTxtTown(), "Manager Town");
			workerForm.setComponentValue(workerForm.getTxtCity(), "Manager City");
						
			//country combobox
			InputAgent cmbCountryInputAgent = cmbCountryAgent.as(InputAgent.class);
			cmbCountryInputAgent.typing("Cuba");
			workerForm.setComponentValue(workerForm.getCmbCountry(), "CU");
			cmbCountryAgent.click(); 	//needed to force province combo population. 
										//cmbCountry's onSelect event isn't triggered under testing
			
			//province combobox
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing("La Habana");
			workerForm.selectComboitemByLabel(workerForm.getCmbProvince(), "La Habana");
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing("Plaza de la Revolución");
			workerForm.selectComboitemByLabel(workerForm.getCmbMunicipality(), "Plaza de la Revolución");
			workerForm.setComponentValue(workerForm.getDblLatitude(), 21.0);
			workerForm.setComponentValue(workerForm.getDblLongitude(), -81.0);
			clickOnRecordButton(desktop);
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			workerForm.testWrongValueException(ex);
		}
	}
	
	private void recordWorker(Worker newWorkerData, 
							Integer responsibilityId,
							String responsibilityName,
							String countryName, 
							String provinceName, 
							String municipalityName, 
							DesktopAgent desktop) {
		
		ComponentAgent cmbCountryAgent = desktop.query("combobox").query("#incContactData").query("#cmbCountry");
		ComponentAgent cmbProvinceAgent = desktop.query("combobox").query("#incContactData").query("#cmbProvince");
		ComponentAgent cmbMunicipalityAgent = desktop.query("combobox").query("#incContactData").query("#cmbMunicipality");
		ComponentAgent cmbResponsibilities = desktop.query("combobox").query("#cmbResponsibilities");
		workerForm = new WorkerForm(desktop,
									(desktop.query("textbox").query("#txtUserName")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtPassword")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtConfirmPassword")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtFirstName")).as(Textbox.class), 
									(desktop.query("textbox").query("#txtLastName")).as(Textbox.class), 
									cmbResponsibilities.as(Combobox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtPhoneNumber")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtEmailAddress")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtAddress")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#cmbPostalCode")).as(Combobox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtTown")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtCity")).as(Textbox.class), 
									cmbCountryAgent.as(Combobox.class), 
									cmbProvinceAgent.as(Combobox.class), 
									cmbMunicipalityAgent.as(Combobox.class), 
									(desktop.query("doublebox").query("#incContactData").query("#dblLatitude")).as(Doublebox.class), 
									(desktop.query("doublebox").query("#incContactData").query("#dblLongitude")).as(Doublebox.class));
		try {
			workerForm.setComponentValue(workerForm.getTxtUserName(), newWorkerData.getLoginName());
			workerForm.setComponentValue(workerForm.getTxtPassword(), newWorkerData.getPassword());
			workerForm.setComponentValue(workerForm.getTxtConfirmPassword(), newWorkerData.getPassword());
			workerForm.setComponentValue(workerForm.getTxtFirstName(), newWorkerData.getFirstName());
			workerForm.setComponentValue(workerForm.getTxtLastName(), newWorkerData.getLastName());			
			
			//responsibilities combobox
			assignResponsibility(cmbResponsibilities, responsibilityId, responsibilityName);		
			
			workerForm.setComponentValue(workerForm.getTxtPhoneNumber(), newWorkerData.getContactData().getMobilePhoneNumber());
			workerForm.setComponentValue(workerForm.getTxtEmailAddress(), newWorkerData.getContactData().getEmailAddress());
			workerForm.setComponentValue(workerForm.getTxtAddress(), newWorkerData.getContactData().getAddress());
			workerForm.setComponentValue(workerForm.getCmbPostalCode(), newWorkerData.getContactData().getPostalCode());
			workerForm.setComponentValue(workerForm.getTxtTown(), newWorkerData.getContactData().getTown());
			workerForm.setComponentValue(workerForm.getTxtCity(), newWorkerData.getContactData().getCity());
						
			//country combobox
			InputAgent cmbCountryInputAgent = cmbCountryAgent.as(InputAgent.class);
			cmbCountryInputAgent.typing(countryName);
			workerForm.setComponentValue(workerForm.getCmbCountry(), newWorkerData.getContactData().getCountryCode());
			cmbCountryAgent.click(); 	//needed to force province combo population. 
										//cmbCountry's onSelect event isn't triggered under testing
			
			//province combobox
			InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
			cmbProvinceInputAgent.typing(provinceName);
			workerForm.selectComboitemByValue(workerForm.getCmbProvince(), newWorkerData.getContactData().getProvinceId().toString());
			cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
										//cmbProvince's onSelect event isn't triggered under testing
			
			//municipality combobox
			InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
			cmbMunicipalityInputAgent.typing(municipalityName);
			workerForm.selectComboitemByValue(workerForm.getCmbMunicipality(), newWorkerData.getContactData().getMunicipalityId().toString());
			workerForm.setComponentValue(workerForm.getDblLatitude(), newWorkerData.getContactData().getLatitude());
			workerForm.setComponentValue(workerForm.getDblLongitude(), newWorkerData.getContactData().getLongitude());
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			throw ex;
		}
	}
	
	private void testForAllowedUser(String user,
										Worker newWorkerData, 
										Integer responsibilityId,
										String responsibilityName,
										String countryName, 
										String provinceName, 
										String municipalityName) {
		
		SpringUtility.setLoggedUserForTesting(user);
		DesktopAgent desktop = Zats.newClient().connect("/new_worker.zul");
		try {
			recordWorker(newWorkerData, 
						responsibilityId,
						responsibilityName,
						countryName, 
						provinceName, 
						municipalityName, 
						desktop);
		}
		catch(Throwable ex) {
			throw ex;
		}
	};
	
	@Test
	final void testCase14() {
		
		try {
			ArrayList<Responsibility> responsibilities = new ArrayList<Responsibility>();
			ArrayList<Authority> authorities = new ArrayList<Authority>();			
			responsibilities.add(new Responsibility(3, "Accounter"));
			for (int i = 1; i <= 1; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			Worker newWorkerData = new Worker(0,
										0,
										"accounter@soberano.syscoop.co",
										"accounter",
										"accounter",
										"accounter",
										"50155555",
										"CU",
										"Address 1",
										"Pc 1",
										"Town 1",
										1013,
										"City 1",
										2,
										1.0,
										1.0,
										responsibilities,
										authorities);
			testForAllowedUser("user1@soberano.syscoop.co",
								newWorkerData, 
								3,
								"Accounter",
								"Cuba",
								"La Habana",
								"Plaza de la Revolución");
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
			ArrayList<Responsibility> responsibilities = new ArrayList<Responsibility>();
			ArrayList<Authority> authorities = new ArrayList<Authority>();			
			responsibilities.add(new Responsibility(7, "Auditor"));
			for (int i = 1; i <= 1; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			Worker newWorkerData = new Worker(0,
										0,
										"auditor@soberano.syscoop.co",
										"auditor",
										"auditor",
										"auditor",
										"50255555",
										"CU",
										"Address 2",
										"Pc 2",
										"Town 2",
										1003,
										"City 2",
										1,
										2.0,
										2.0,
										responsibilities,
										authorities);
			testForAllowedUser("user2@soberano.syscoop.co",
								newWorkerData, 
								7, 
								"Auditor",
								"Cuba",
								"Pinar del Río",
								"Viñales");
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
			ArrayList<Responsibility> responsibilities = new ArrayList<Responsibility>();
			ArrayList<Authority> authorities = new ArrayList<Authority>();			
			responsibilities.add(new Responsibility(11, "Catalog maintainer"));
			for (int i = 1; i <= 1; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			Worker newWorkerData = new Worker(0,
										0,
										"catalogMaintainer@soberano.syscoop.co",
										"catalogMaintainer",
										"catalogMaintainer",
										"catalogMaintainer",
										"50355555",
										"CU",
										"Address 3",
										"Pc 3",
										"Town 3",
										1037,
										"City 3",
										3,
										3.0,
										3.0,
										responsibilities,
										authorities);
			testForAllowedUser("user3@soberano.syscoop.co",
								newWorkerData, 
								11, 
								"Catalog maintainer",
								"Cuba",
								"Matanzas",
								"Jagüey Grande");
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
			ArrayList<Responsibility> responsibilities = new ArrayList<Responsibility>();
			ArrayList<Authority> authorities = new ArrayList<Authority>();			
			responsibilities.add(new Responsibility(6, "Checker"));
			for (int i = 1; i <= 1; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			Worker newWorkerData = new Worker(0,
										0,
										"checker@soberano.syscoop.co",
										"checker",
										"checker",
										"checker",
										"50455555",
										"CU",
										"Address 4",
										"Pc 4",
										"Town 4",
										1049,
										"City 4",
										4,
										4.0,
										4.0,
										responsibilities,
										authorities);
			testForAllowedUser("user4@soberano.syscoop.co",
								newWorkerData, 
								6, 
								"Checker",
								"Cuba",
								"Villa Clara",
								"Santa Clara");
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
			ArrayList<Responsibility> responsibilities = new ArrayList<Responsibility>();
			ArrayList<Authority> authorities = new ArrayList<Authority>();			
			responsibilities.add(new Responsibility(12, "Community manager"));
			for (int i = 1; i <= 1; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			Worker newWorkerData = new Worker(0,
										0,
										"communityManager@soberano.syscoop.co",
										"communityManager",
										"communityManager",
										"communityManager",
										"50555555",
										"CU",
										"Address 5",
										"Pc 5",
										"Town 5",
										1147,
										"City 5",
										5,
										5.0,
										5.0,
										responsibilities,
										authorities);
			testForAllowedUser("user5@soberano.syscoop.co",
								newWorkerData, 
								12, 
								"Community manager",
								"Cuba",
								"Isla de la Juventud",
								"Isla de la Juventud");
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
			ArrayList<Responsibility> responsibilities = new ArrayList<Responsibility>();
			ArrayList<Authority> authorities = new ArrayList<Authority>();			
			responsibilities.add(new Responsibility(2, "Manager"));
			for (int i = 1; i <= 1; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			Worker newWorkerData = new Worker(0,
										0,
										"manager@soberano.syscoop.co",
										"manager",
										"manager",
										"manager",
										"50655555",
										"CU",
										"Address 6",
										"Pc 6",
										"Town 6",
										1156,
										"City 6",
										6,
										6.0,
										6.0,
										responsibilities,
										authorities);
			testForAllowedUser("user6@soberano.syscoop.co",
								newWorkerData, 
								2, 
								"Manager",
								"Cuba",
								"Artemisa",
								"Artemisa");
			
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
	final void testCase20() {
		
		try {
			ArrayList<Responsibility> responsibilities = new ArrayList<Responsibility>();
			ArrayList<Authority> authorities = new ArrayList<Authority>();			
			responsibilities.add(new Responsibility(2, "Manager"));
			for (int i = 1; i <= 1; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			Worker newWorkerData = new Worker(0,
										0,
										"manager@soberano.syscoop.co",
										"manager",
										"manager",
										"manager",
										"50655555",
										"CU",
										"Address 6",
										"Pc 6",
										"Town 6",
										1156,
										"City 6",
										6,
										6.0,
										6.0,
										responsibilities,
										authorities);
			testForAllowedUser("user7@soberano.syscoop.co",
								newWorkerData, 
								2, 
								"Manager",
								"Cuba",
								"Artemisa",
								"Artemisa");
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
			ArrayList<Responsibility> responsibilities = new ArrayList<Responsibility>();
			ArrayList<Authority> authorities = new ArrayList<Authority>();			
			responsibilities.add(new Responsibility(13, "Procurement worker"));
			for (int i = 1; i <= 1; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			Worker newWorkerData = new Worker(0,
										0,
										"procurementWorker@soberano.syscoop.co",
										"procurementWorker",
										"procurementWorker",
										"procurementWorker",
										"50755555",
										"CU",
										"Address 7",
										"Pc 7",
										"Town 7",
										1162,
										"City 7",
										7,
										7.0,
										7.0,
										responsibilities,
										authorities);
			testForAllowedUser("user8@soberano.syscoop.co",
								newWorkerData, 
								13, 
								"Procurement worker",
								"Cuba",
								"Mayabeque",
								"Santa Cruz del Norte");
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
			ArrayList<Responsibility> responsibilities = new ArrayList<Responsibility>();
			ArrayList<Authority> authorities = new ArrayList<Authority>();			
			responsibilities.add(new Responsibility(4, "Salesclerk"));
			for (int i = 1; i <= 1; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			Worker newWorkerData = new Worker(0,
										0,
										"salesclerk@soberano.syscoop.co",
										"salesclerk",
										"salesclerk",
										"salesclerk",
										"50855555",
										"CU",
										"Address 8",
										"Pc 8",
										"Town 8",
										1138,
										"City 8",
										8,
										8.0,
										8.0,
										responsibilities,
										authorities);
			testForAllowedUser("user9@soberano.syscoop.co",
								newWorkerData, 
								4, 
								"Salesclerk",
								"Cuba",
								"Guantánamo",
								"Guantánamo");
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
			ArrayList<Responsibility> responsibilities = new ArrayList<Responsibility>();
			ArrayList<Authority> authorities = new ArrayList<Authority>();			
			responsibilities.add(new Responsibility(5, "Shift manager"));
			for (int i = 1; i <= 1; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			Worker newWorkerData = new Worker(0,
										0,
										"shiftManager@soberano.syscoop.co",
										"shiftManager",
										"shiftManager",
										"shiftManager",
										"50955555",
										"CU",
										"Address 9",
										"Pc 9",
										"Town 9",
										1133,
										"City 9",
										9,
										9.0,
										9.0,
										responsibilities,
										authorities);
			testForAllowedUser("user10@soberano.syscoop.co",
								newWorkerData, 
								5, 
								"Shift manager",
								"Cuba",
								"Santiago de Cuba",
								"Santiago de Cuba");
			
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
	final void testCase24() {
		
		try {
			ArrayList<Responsibility> responsibilities = new ArrayList<Responsibility>();
			ArrayList<Authority> authorities = new ArrayList<Authority>();			
			responsibilities.add(new Responsibility(5, "Shift manager"));
			for (int i = 1; i <= 1; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			Worker newWorkerData = new Worker(0,
										0,
										"shiftManager@soberano.syscoop.co",
										"shiftManager",
										"shiftManager",
										"shiftManager",
										"50955555",
										"CU",
										"Address 9",
										"Pc 9",
										"Town 9",
										1133,
										"City 9",
										9,
										9.0,
										9.0,
										responsibilities,
										authorities);
			testForAllowedUser("user11@soberano.syscoop.co",
								newWorkerData, 
								5, 
								"Shift manager",
								"Cuba",
								"Santiago de Cuba",
								"Santiago de Cuba");
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
			ArrayList<Responsibility> responsibilities = new ArrayList<Responsibility>();
			ArrayList<Authority> authorities = new ArrayList<Authority>();			
			responsibilities.add(new Responsibility(10, "Storekeeper"));
			for (int i = 1; i <= 1; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			Worker newWorkerData = new Worker(0,
										0,
										"storekeeper@soberano.syscoop.co",
										"storekeeper",
										"storekeeper",
										"storekeeper",
										"51055555",
										"CU",
										"Address 10",
										"Pc 10",
										"Town 10",
										1122,
										"City 10",
										10,
										10.0,
										10.0,
										responsibilities,
										authorities);
			testForAllowedUser("user12@soberano.syscoop.co",
								newWorkerData, 
								10, 
								"Storekeeper",
								"Cuba",
								"Granma",
								"Pilón");
			
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
	final void testCase26() {
		
		try {
			ArrayList<Responsibility> responsibilities = new ArrayList<Responsibility>();
			ArrayList<Authority> authorities = new ArrayList<Authority>();			
			responsibilities.add(new Responsibility(10, "Storekeeper"));
			for (int i = 1; i <= 1; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			Worker newWorkerData = new Worker(0,
										0,
										"storekeeper@soberano.syscoop.co",
										"storekeeper",
										"storekeeper",
										"storekeeper",
										"51055555",
										"CU",
										"Address 10",
										"Pc 10",
										"Town 10",
										1122,
										"City 10",
										10,
										10.0,
										10.0,
										responsibilities,
										authorities);
			testForAllowedUser("user13@soberano.syscoop.co",
								newWorkerData, 
								10, 
								"Storekeeper",
								"Cuba",
								"Granma",
								"Pilón");
			
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
	final void testCase27() {
		
		try {
			ArrayList<Responsibility> responsibilities = new ArrayList<Responsibility>();
			ArrayList<Authority> authorities = new ArrayList<Authority>();			
			responsibilities.add(new Responsibility(10, "Storekeeper"));
			for (int i = 1; i <= 1; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			Worker newWorkerData = new Worker(0,
										0,
										"storekeeper@soberano.syscoop.co",
										"storekeeper",
										"storekeeper",
										"storekeeper",
										"51055555",
										"CU",
										"Address 10",
										"Pc 10",
										"Town 10",
										1122,
										"City 10",
										10,
										10.0,
										10.0,
										responsibilities,
										authorities);
			testForAllowedUser("user14@soberano.syscoop.co",
								newWorkerData, 
								10, 
								"Storekeeper",
								"Cuba",
								"Granma",
								"Pilón");
			
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
	final void testCase28() {
		
		try {
			ArrayList<Responsibility> responsibilities = new ArrayList<Responsibility>();
			ArrayList<Authority> authorities = new ArrayList<Authority>();			
			responsibilities.add(new Responsibility(10, "Storekeeper"));
			for (int i = 1; i <= 1; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			Worker newWorkerData = new Worker(0,
										0,
										"storekeeper@soberano.syscoop.co",
										"storekeeper",
										"storekeeper",
										"storekeeper",
										"51055555",
										"CU",
										"Address 10",
										"Pc 10",
										"Town 10",
										1122,
										"City 10",
										10,
										10.0,
										10.0,
										responsibilities,
										authorities);
			testForAllowedUser("user15@soberano.syscoop.co",
								newWorkerData, 
								10, 
								"Storekeeper",
								"Cuba",
								"Granma",
								"Pilón");
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
			ArrayList<Responsibility> responsibilities = new ArrayList<Responsibility>();
			ArrayList<Authority> authorities = new ArrayList<Authority>();			
			responsibilities.add(new Responsibility(14, "System admin"));
			for (int i = 1; i <= 1; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			Worker newWorkerData = new Worker(0,
										0,
										"systemadmin@soberano.syscoop.co",
										"systemadmin",
										"systemadmin",
										"systemadmin",
										"51155555",
										"CU",
										"Address 11",
										"Pc 11",
										"Town 11",
										1106,
										"City 11",
										11,
										11.0,
										11.0,
										responsibilities,
										authorities);
			testForAllowedUser("user16@soberano.syscoop.co",
								newWorkerData, 
								14, 
								"System admin",
								"Cuba",
								"Holguín",
								"Holguín");
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
			ArrayList<Responsibility> responsibilities = new ArrayList<Responsibility>();
			ArrayList<Authority> authorities = new ArrayList<Authority>();			
			responsibilities.add(new Responsibility(8, "Workshop 1 worker"));
			for (int i = 1; i <= 1; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			Worker newWorkerData = new Worker(0,
										0,
										"workshop1Worker@soberano.syscoop.co",
										"workshop1Worker",
										"workshop1Worker",
										"workshop1Worker",
										"51255555",
										"CU",
										"Address 12",
										"Pc 12",
										"Town 12",
										1095,
										"City 12",
										12,
										12.0,
										12.0,
										responsibilities,
										authorities);
			testForAllowedUser("user16@soberano.syscoop.co",
								newWorkerData, 
								8, 
								"Workshop 1 worker",
								"Cuba",
								"Las Tunas",
								"Las Tunas");
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
			ArrayList<Responsibility> responsibilities = new ArrayList<Responsibility>();
			ArrayList<Authority> authorities = new ArrayList<Authority>();			
			responsibilities.add(new Responsibility(9, "Workshop 2 worker"));
			for (int i = 1; i <= 1; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			Worker newWorkerData = new Worker(0,
										0,
										"workshop2Worker@soberano.syscoop.co",
										"workshop2Worker",
										"workshop2Worker",
										"workshop2Worker",
										"51355555",
										"CU",
										"Address 13",
										"Pc 13",
										"Town 13",
										1086,
										"City 13",
										13,
										13.0,
										13.0,
										responsibilities,
										authorities);
			testForAllowedUser("user16@soberano.syscoop.co",
								newWorkerData, 
								9, 
								"Workshop 2 worker",
								"Cuba",
								"Camagüey",
								"Camagüey");
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
			ArrayList<Responsibility> responsibilities = new ArrayList<Responsibility>();
			ArrayList<Authority> authorities = new ArrayList<Authority>();			
			responsibilities.add(new Responsibility(9, "Workshop 2 worker"));
			for (int i = 1; i <= 1; i++) authorities.add(new Authority(1, "soberano.authority.top"));
			Worker newWorkerData = new Worker(0,
										0,
										"workshop2Worker@soberano.syscoop.co",
										"workshop2Worker",
										"workshop2Worker",
										"workshop2Worker",
										"51355555",
										"CU",
										"Address 13",
										"Pc 13",
										"Town 13",
										1086,
										"City 13",
										13,
										13.0,
										13.0,
										responsibilities,
										authorities);
			testForAllowedUser("user16@soberano.syscoop.co",
								newWorkerData, 
								9, 
								"Workshop 2 worker",
								"Cuba",
								"Camagüey",
								"Camagüey");
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testDuplicateKeyException(ex);
		}
	}
}
