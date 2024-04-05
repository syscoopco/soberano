package co.syscoop.soberano.test.classes;

import static org.junit.jupiter.api.Assertions.*;

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
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zats.mimic.operation.InputAgent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;

import co.syscoop.soberano.test.helper.WorkerActionTest;
import co.syscoop.soberano.test.helper.WorkerForm;

@Order(6)

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
			WorkerForm workerForm = setFormComponents("user1@soberano.syscoop.co", "workers.zul");			
			loadObjectDetails("manager manager : manager@soberano.syscoop.co");			
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
			WorkerForm workerForm = setFormComponents("user2@soberano.syscoop.co", "workers.zul");			
			loadObjectDetails("manager manager : manager@soberano.syscoop.co");			
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
		
		workerForm.setComponentValue(workerForm.getTxtPassword(), "changedpassword");
		workerForm.setComponentValue(workerForm.getTxtConfirmPassword(), "changedpassword");
		workerForm.setComponentValue(workerForm.getTxtFirstName(), "fn");
		workerForm.setComponentValue(workerForm.getTxtLastName(), "ln");
		workerForm.setComponentValue(workerForm.getTxtPhoneNumber(), "66666666");
		workerForm.setComponentValue(workerForm.getTxtAddress(), "address");
		workerForm.setComponentValue(workerForm.getTxtPostalCode(), "Postal code");
		workerForm.setComponentValue(workerForm.getTxtTown(), "Town M");
		workerForm.setComponentValue(workerForm.getTxtCity(), "City M");
		
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
		workerForm.setComponentValue(workerForm.getDblLongitude(), 7.0);	
	}
	
	@Test
	final void testCase3() {

		WorkerForm workerForm = null;
		try {
			workerForm = setFormComponents("user3@soberano.syscoop.co", "workers.zul");			
			loadObjectDetails("accounter accounter : accounter@soberano.syscoop.co");
			
			assignResponsibility(cmbIntelliSearchAgent.query("#incDetails").query("#cmbResponsibilities"), 
								new Integer(7), 
								"Auditor");			
			assignResponsibility(cmbIntelliSearchAgent.query("#incDetails").query("#cmbResponsibilities"), 
								new Integer(11), 
								"Catalog maintainer");
			
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
	
	@Test
	final void testCase4() {

		WorkerForm workerForm = null;
		try {
			workerForm = setFormComponents("user4@soberano.syscoop.co", "workers.zul");			
			loadObjectDetails("auditor auditor : auditor@soberano.syscoop.co");
			
			assignResponsibility(cmbIntelliSearchAgent.query("#incDetails").query("#cmbResponsibilities"), 
								new Integer(6), 
								"Checker");			
			assignResponsibility(cmbIntelliSearchAgent.query("#incDetails").query("#cmbResponsibilities"), 
								new Integer(11), 
								"Catalog maintainer");
			
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
	
	@Test
	final void testCase5() {

		WorkerForm workerForm = null;
		try {
			workerForm = setFormComponents("user5@soberano.syscoop.co", "workers.zul");			
			loadObjectDetails("catalogMaintainer catalogMaintainer : catalogMaintainer@soberano.syscoop.co");
			
			assignResponsibility(cmbIntelliSearchAgent.query("#incDetails").query("#cmbResponsibilities"), 
								new Integer(6), 
								"Checker");			
			assignResponsibility(cmbIntelliSearchAgent.query("#incDetails").query("#cmbResponsibilities"), 
								new Integer(12), 
								"Community manager");
			
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
	
	@Test
	final void testCase6() {

		WorkerForm workerForm = null;
		try {
			workerForm = setFormComponents("user7@soberano.syscoop.co", "workers.zul");			
			loadObjectDetails("checker checker : checker@soberano.syscoop.co");
			
			assignResponsibility(cmbIntelliSearchAgent.query("#incDetails").query("#cmbResponsibilities"), 
								new Integer(2), 
								"Manager");			
			assignResponsibility(cmbIntelliSearchAgent.query("#incDetails").query("#cmbResponsibilities"), 
								new Integer(12), 
								"Community manager");
			
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
	
	@Test
	final void testCase7() {

		WorkerForm workerForm = null;
		try {
			workerForm = setFormComponents("user8@soberano.syscoop.co", "workers.zul");			
			loadObjectDetails("communityManager communityManager : communityManager@soberano.syscoop.co");
			
			assignResponsibility(cmbIntelliSearchAgent.query("#incDetails").query("#cmbResponsibilities"), 
								new Integer(2), 
								"Manager");			
			assignResponsibility(cmbIntelliSearchAgent.query("#incDetails").query("#cmbResponsibilities"), 
								new Integer(13), 
								"Procurement worker");
			
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
	
	@Test
	final void testCase8() {
		
		WorkerForm workerForm = null;
		try {
			workerForm = setFormComponents("user6@soberano.syscoop.co", "workers.zul");			
			loadObjectDetails("fn ln : communityManager@soberano.syscoop.co");
			
			ComponentAgent btnRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#btnRowDeletion13");
			btnRowDeletion.click();
			clickOnApplyButton(workerForm.getDesktop());
			
			setFieldsForTesting(workerForm);
			clickOnApplyButton(workerForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			//an auditor can list the users but not modifying them
			testNotEnoughRightsException(ex);
		}
	}
	
	@Test
	final void testCase9() {

		WorkerForm workerForm = null;
		try {
			workerForm = setFormComponents("user9@soberano.syscoop.co", "workers.zul");			
			loadObjectDetails("fn ln : communityManager@soberano.syscoop.co");
			
			ComponentAgent btnRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#btnRowDeletion13");
			btnRowDeletion.click();
			clickOnApplyButton(workerForm.getDesktop());
			
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
	
	@Test
	final void testCase10() {

		WorkerForm workerForm = null;
		try {
			workerForm = setFormComponents("user11@soberano.syscoop.co", "workers.zul");			
			loadObjectDetails("manager manager : manager@soberano.syscoop.co");
			
			assignResponsibility(cmbIntelliSearchAgent.query("#incDetails").query("#cmbResponsibilities"), 
								new Integer(4), 
								"Salesclerk");			
			assignResponsibility(cmbIntelliSearchAgent.query("#incDetails").query("#cmbResponsibilities"), 
								new Integer(13), 
								"Procurement worker");
			
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
	
	@Test
	final void testCase11() {

		WorkerForm workerForm = null;
		try {
			workerForm = setFormComponents("user15@soberano.syscoop.co", "workers.zul");			
			loadObjectDetails("procurementWorker procurementWorker : procurementWorker@soberano.syscoop.co");
			
			assignResponsibility(cmbIntelliSearchAgent.query("#incDetails").query("#cmbResponsibilities"), 
								new Integer(4), 
								"Salesclerk");			
			assignResponsibility(cmbIntelliSearchAgent.query("#incDetails").query("#cmbResponsibilities"), 
								new Integer(5), 
								"Shift manager");
			
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
	
	@Test
	final void testCase12() {
		
		WorkerForm workerForm = null;
		try {
			workerForm = setFormComponents("user10@soberano.syscoop.co", "workers.zul");			
			loadObjectDetails("fn ln : communityManager@soberano.syscoop.co");
			
			ComponentAgent btnRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#btnRowDeletion12");
			btnRowDeletion.click();
			clickOnApplyButton(workerForm.getDesktop());
			
			setFieldsForTesting(workerForm);
			clickOnApplyButton(workerForm.getDesktop());
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			//user10 can list the users but not modifying them
			testNotEnoughRightsException(ex);
		}	
	}
	
	@Test
	final void testCase13() {

		WorkerForm workerForm = null;
		try {
			workerForm = setFormComponents("user16@soberano.syscoop.co", "workers.zul");			
			loadObjectDetails("fn ln : procurementWorker@soberano.syscoop.co");
			
			ComponentAgent btnRowDeletion = cmbIntelliSearchAgent.query("#incDetails").query("#btnRowDeletion5");
			btnRowDeletion.click();
			clickOnApplyButton(workerForm.getDesktop());
			
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
	
	@Test
	final void testCase14() {
		
		WorkerForm workerForm = null;
		try {
			workerForm = setFormComponents("user12@soberano.syscoop.co", "workers.zul");			
			loadObjectDetails("fn ln : procurementWorker@soberano.syscoop.co");		
			
			assignResponsibility(cmbIntelliSearchAgent.query("#incDetails").query("#cmbResponsibilities"), 
								new Integer(5), 
								"Shift manager");
			
			setFieldsForTesting(workerForm);
			clickOnApplyButton(workerForm.getDesktop());						
			fail("None exception was thrown when it should.");
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			//an auditor can list the users but not modifying them
			testNotEnoughRightsException(ex);
		}		
	}
	
	protected void checkUser(WorkerForm workerForm,
							String userName,
							String firstName,
							String lastName,
							ArrayList<String> responsibilities,
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

		String qualifiedName = firstName + " " + lastName + " : " + userName;
		loadObjectDetails(qualifiedName);
		
		assertEquals(userName.toLowerCase(), workerForm.getTxtUserName().getText().toLowerCase(), "Wrong username shown for user " +  qualifiedName);
		assertEquals(firstName.toLowerCase(), workerForm.getTxtFirstName().getText().toLowerCase(), "Wrong firstName shown for user " +  qualifiedName);
		assertEquals(lastName.toLowerCase(), workerForm.getTxtLastName().getText().toLowerCase(), "Wrong lastName shown for user " +  qualifiedName);
		
		assertEquals(responsibilities.size(), workerForm.getTchdnResponsibilities().getChildren().size(), "Wrong assigments count for user " +  qualifiedName);
		
		for (Component comp : workerForm.getTchdnResponsibilities().getChildren()) {
			if (!responsibilities.contains(((Treeitem) comp).getLabel())) {
				fail("Missed responsibility: " +  ((Treeitem) comp).getLabel());
			}
		}
		
		assertEquals(phoneNumber, workerForm.getTxtPhoneNumber().getText(), "Wrong phoneNumber shown for user " +  qualifiedName);
		assertEquals(emailAddress.toLowerCase(), workerForm.getTxtEmailAddress().getText().toLowerCase(), "Wrong emailAddress shown for user " +  qualifiedName);
		assertEquals(country, workerForm.getCmbCountry().getText(), "Wrong country shown for user " +  qualifiedName);
		assertEquals(province, workerForm.getCmbProvince().getText(), "Wrong province shown for user " +  qualifiedName);
		assertEquals(municipality, workerForm.getCmbMunicipality().getText(), "Wrong municipality shown for user " +  qualifiedName);
		assertEquals(address, workerForm.getTxtAddress().getText(), "Wrong address shown for user " +  qualifiedName);
		assertEquals(postalCode, workerForm.getTxtPostalCode().getText(), "Wrong postalCode shown for user " +  qualifiedName);
		assertEquals(town, workerForm.getTxtTown().getText(), "Wrong town shown for user " +  qualifiedName);
		assertEquals(city, workerForm.getTxtCity().getText(), "Wrong city shown for user " +  qualifiedName);
		assertEquals(latitude, workerForm.getDblLatitude().getValue(), "Wrong latitude shown for user " +  qualifiedName);
		assertEquals(longitude, workerForm.getDblLongitude().getValue(), "Wrong longitude shown for user " +  qualifiedName);			
	}
	
	@Test
	final void testCase15() {
		
		try {
			WorkerForm workerForm = setFormComponents("user1@soberano.syscoop.co", "workers.zul");
			ArrayList<String> rl = new ArrayList<String>();
			rl.add("Auditor");
			rl.add("Catalog maintainer");
			rl.add("Accounter");
			checkUser(	workerForm,
						"accounter@soberano.syscoop.co",
						"fn",
						"ln",
						rl,
						"66666666",
						"accounter@soberano.syscoop.co",
						"Cuba",
						"Camagüey",
						"Camagüey",
						"address",
						"Postal code",
						"Town M",
						"City M",
						5.0,
						7.0);
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
			WorkerForm workerForm = setFormComponents("user1@soberano.syscoop.co", "workers.zul");
			ArrayList<String> rl = new ArrayList<String>();
			rl.add("Auditor");
			rl.add("Catalog maintainer");
			rl.add("Checker");
			checkUser(	workerForm,
						"auditor@soberano.syscoop.co",
						"fn",
						"ln",
						rl,
						"66666666",
						"auditor@soberano.syscoop.co",
						"Cuba",
						"Camagüey",
						"Camagüey",
						"address",
						"Postal code",
						"Town M",
						"City M",
						5.0,
						7.0);
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
			WorkerForm workerForm = setFormComponents("user1@soberano.syscoop.co", "workers.zul");
			ArrayList<String> rl = new ArrayList<String>();
			rl.add("Catalog maintainer");
			rl.add("Checker");
			rl.add("Community manager");
			checkUser(	workerForm,
						"catalogMaintainer@soberano.syscoop.co",
						"fn",
						"ln",
						rl,
						"66666666",
						"catalogMaintainer@soberano.syscoop.co",
						"Cuba",
						"Camagüey",
						"Camagüey",
						"address",
						"Postal code",
						"Town M",
						"City M",
						5.0,
						7.0);
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
			WorkerForm workerForm = setFormComponents("user1@soberano.syscoop.co", "workers.zul");
			ArrayList<String> rl = new ArrayList<String>();
			rl.add("Manager");
			rl.add("Checker");
			rl.add("Community manager");
			checkUser(	workerForm,
						"checker@soberano.syscoop.co",
						"fn",
						"ln",
						rl,
						"66666666",
						"checker@soberano.syscoop.co",
						"Cuba",
						"Camagüey",
						"Camagüey",
						"address",
						"Postal code",
						"Town M",
						"City M",
						5.0,
						7.0);
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
			WorkerForm workerForm = setFormComponents("user1@soberano.syscoop.co", "workers.zul");
			ArrayList<String> rl = new ArrayList<String>();
			rl.add("Manager");
			rl.add("Community manager");
			checkUser(	workerForm,
						"communityManager@soberano.syscoop.co",
						"fn",
						"ln",
						rl,
						"66666666",
						"communityManager@soberano.syscoop.co",
						"Cuba",
						"Camagüey",
						"Camagüey",
						"address",
						"Postal code",
						"Town M",
						"City M",
						5.0,
						7.0);
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
			WorkerForm workerForm = setFormComponents("user1@soberano.syscoop.co", "workers.zul");
			ArrayList<String> rl = new ArrayList<String>();
			rl.add("Manager");
			rl.add("Procurement worker");
			rl.add("Salesclerk");
			checkUser(	workerForm,
						"manager@soberano.syscoop.co",
						"fn",
						"ln",
						rl,
						"66666666",
						"manager@soberano.syscoop.co",
						"Cuba",
						"Camagüey",
						"Camagüey",
						"address",
						"Postal code",
						"Town M",
						"City M",
						5.0,
						7.0);
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
			WorkerForm workerForm = setFormComponents("user1@soberano.syscoop.co", "workers.zul");
			ArrayList<String> rl = new ArrayList<String>();
			rl.add("Procurement worker");
			rl.add("Salesclerk");
			checkUser(	workerForm,
						"procurementWorker@soberano.syscoop.co",
						"fn",
						"ln",
						rl,
						"66666666",
						"procurementWorker@soberano.syscoop.co",
						"Cuba",
						"Camagüey",
						"Camagüey",
						"address",
						"Postal code",
						"Town M",
						"City M",
						5.0,
						7.0);
		}
		catch(AssertionFailedError ex) {
			fail(ex.getMessage());
		}
		catch(Throwable ex) {
			fail(ex.getMessage());
		}
	}
}
