package co.syscoop.soberano.domain.tracked.worker;

import static org.junit.jupiter.api.Assertions.fail;

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
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Textbox;
import co.syscoop.soberano.test.helper.Test_record;
import co.syscoop.soberano.test.helper.WorkerForm;
import co.syscoop.soberano.util.SpringUtility;

@Order(4)
class O4_WorkerTest_record extends Test_record {
	
	WorkerForm workerForm = null;

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
		SpringUtility.setLoggedUserForTesting("user6");
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
									(desktop.query("textbox").query("#incContactData").query("#txtPostalCode")).as(Textbox.class), 
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
			workerForm.testWrongValueException(ex);
		}
	}
	
	@Test
	final void testCase1() {

		SpringUtility.setLoggedUserForTesting("user6");
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
									(desktop.query("textbox").query("#incContactData").query("#txtPostalCode")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtTown")).as(Textbox.class), 
									(desktop.query("textbox").query("#incContactData").query("#txtCity")).as(Textbox.class), 
									(desktop.query("combobox").query("#incContactData").query("#cmbCountry")).as(Combobox.class), 
									(desktop.query("combobox").query("#incContactData").query("#cmbProvince")).as(Combobox.class), 
									(desktop.query("combobox").query("#incContactData").query("#cmbMunicipality")).as(Combobox.class), 
									(desktop.query("doublebox").query("#incContactData").query("#dblLatitude")).as(Doublebox.class), 
									(desktop.query("doublebox").query("#incContactData").query("#dblLongitude")).as(Doublebox.class));
		try {
			workerForm.setComponentValue(workerForm.getTxtUserName(), "");
			workerForm.setComponentValue(workerForm.getTxtPassword(), "12345");
			workerForm.setComponentValue(workerForm.getTxtConfirmPassword(), "12345");
			workerForm.setComponentValue(workerForm.getTxtFirstName(), "Manager");
			workerForm.setComponentValue(workerForm.getTxtLastName(), "Last Name");			
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
			workerForm.setComponentValue(workerForm.getTxtPostalCode(), "54321");
			workerForm.setComponentValue(workerForm.getTxtTown(), "Manager Town");
			workerForm.setComponentValue(workerForm.getTxtCity(), "Manager City");
			//TODO: country, province, mun no registra
			workerForm.setComponentValue(workerForm.getDblLatitude(), 21.0);
			workerForm.setComponentValue(workerForm.getDblLatitude(), -81.0);			
			
			clickOnRecordButton(desktop);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			workerForm.testWrongValueException(ex);
		}
	}
}
