package co.syscoop.soberano.domain.tracked.worker;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.zkoss.zats.mimic.ComponentAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zats.mimic.operation.InputAgent;

import co.syscoop.soberano.test.helper.WorkerActionTest;
import co.syscoop.soberano.test.helper.WorkerForm;

//TODO: enable test
//@Disabled

class O6_WorkerTest_modify extends WorkerActionTest {
		
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
	final void testCase1() {

		try {
			WorkerForm workerForm = setFormComponents("systemadmin@soberano.syscoop.co", "workers.zul");			
			loadObjectDetails("Manager Manager : manager@soberano.syscoop.co");			
			workerForm.setComponentValue(workerForm.getTxtPassword(), "abcde");
			workerForm.setComponentValue(workerForm.getTxtConfirmPassword(), "abcd");
			clickOnApplyButton(workerForm.getDesktop());
			
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
	final void testCase2() {

		try {
			WorkerForm workerForm = setFormComponents("systemadmin@soberano.syscoop.co", "workers.zul");			
			loadObjectDetails("Manager Manager : manager@soberano.syscoop.co");			
			ComponentAgent btnRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#btnRowDeletion2");
			btnRowDeletion.click();
			clickOnApplyButton(workerForm.getDesktop());
			
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			testWorkerMustBeAssignedToAResponsibilityException(ex);			
		}		
	}
	
	private void setFieldsForTesting(WorkerForm workerForm) {
		
		workerForm.setComponentValue(workerForm.getTxtPassword(), "accounterm");
		workerForm.setComponentValue(workerForm.getTxtConfirmPassword(), "accounterm");
		workerForm.setComponentValue(workerForm.getTxtFirstName(), "accounterfn");
		workerForm.setComponentValue(workerForm.getTxtLastName(), "accounterln");
		workerForm.setComponentValue(workerForm.getTxtPhoneNumber(), "accounterln");
		workerForm.setComponentValue(workerForm.getTxtAddress(), "accounterln");
		workerForm.setComponentValue(workerForm.getTxtPostalCode(), "accounterln");
		workerForm.setComponentValue(workerForm.getTxtTown(), "accounterln");
		workerForm.setComponentValue(workerForm.getTxtCity(), "accounterln");
		
		//country is the same
		
		//province combobox
		ComponentAgent cmbProvinceAgent = cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#cmbProvince");
		InputAgent cmbProvinceInputAgent = cmbProvinceAgent.as(InputAgent.class);
		cmbProvinceInputAgent.typing("Camagüey");
		workerForm.selectComboitemByLabel(workerForm.getCmbProvince(), "Camagüey");
		cmbProvinceAgent.click(); 	//needed to force municipality combo population. 
									//cmbProvince's onSelect event isn't triggered under testing
		
		//municipality combobox
		ComponentAgent cmbMunicipalityAgent = cmbProvinceAgent.query("#cmbMunicipality");
		InputAgent cmbMunicipalityInputAgent = cmbMunicipalityAgent.as(InputAgent.class);
		cmbMunicipalityInputAgent.typing("Camagüey");
		workerForm.selectComboitemByLabel(workerForm.getCmbMunicipality(), "Camagüey");
		workerForm.setComponentValue(workerForm.getDblLatitude(), 5.0);
		workerForm.setComponentValue(workerForm.getDblLatitude(), 7.0);	
	}
	
	@Test
	final void testCase3() {

		WorkerForm workerForm = null;
		try {
			workerForm = setFormComponents("systemadmin@soberano.syscoop.co", "workers.zul");			
			loadObjectDetails("Accounter Accounter : accounter@soberano.syscoop.co");
			workerForm.setComponentValue(workerForm.getCmbResponsibilities(), new Integer(7).toString() /* Auditor */);
			ComponentAgent btnAssignResponsibility = cmbIntelliSearchAgent.query("#incDetails").query("#btnAssignResponsibility");
			btnAssignResponsibility.click();			
			
			setFieldsForTesting(workerForm);
			clickOnApplyButton(workerForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}		
	}
}
