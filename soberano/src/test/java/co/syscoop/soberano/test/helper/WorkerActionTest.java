package co.syscoop.soberano.test.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

import co.syscoop.soberano.util.ExceptionTreatment;
import co.syscoop.soberano.util.SpringUtility;

public class WorkerActionTest extends ActionTest {
	
	protected static Textbox txtUserName = null;
	protected static Textbox txtPassword = null;
	protected static Textbox txtConfirmPassword = null;
	protected static Textbox txtFirstName = null;
	protected static Textbox txtLastName = null;
	protected static Treechildren tchdnResponsibilities = null;
	protected static Textbox txtPhoneNumber = null;
	protected static Textbox txtEmailAddress = null;
	protected static Textbox txtAddress = null;
	protected static Textbox txtPostalCode = null;
	protected static Textbox txtTown = null;
	protected static Textbox txtCity = null;
	protected static Combobox cmbCountry = null;
	protected static Combobox cmbProvince = null;
	protected static Combobox cmbMunicipality = null;
	protected static Doublebox dblLatitude = null;
	protected static Doublebox dblLongitude = null;
	
	protected void testPasswordsMustMatchException(Throwable ex) {
		Throwable cause = ExceptionTreatment.getRootCause(ex);
		assertEquals(cause.getClass().getName(), "co.syscoop.soberano.exception.PasswordsMustMatchException","Only co.syscoop.soberano.exception.PasswordsMustMatchException can be caught here.");
	}
	
	protected void testWorkerMustBeAssignedToAResponsibilityException(Throwable ex) {
		Throwable cause = ExceptionTreatment.getRootCause(ex);
		assertEquals(cause.getClass().getName(), "co.syscoop.soberano.exception.WorkerMustBeAssignedToAResponsibilityException","Only co.syscoop.soberano.exception.WorkerMustBeAssignedToAResponsibilityException can be caught here.");
	}
	
	protected static WorkerForm setFormComponents(String user, String formZulFilename) {
		
		SpringUtility.setLoggedUserForTesting(user);
		DesktopAgent desktop = Zats.newClient().connect("/" + formZulFilename);
		cmbIntelliSearchAgent = desktop.query("combobox");
		cmbIntelliSearch = cmbIntelliSearchAgent.as(Combobox.class);
		ComponentAgent cmbResponsibilities = cmbIntelliSearchAgent.query("#incDetails").query("#cmbResponsibilities");
		WorkerForm workerForm = new WorkerForm(desktop, 
												cmbIntelliSearchAgent.query("#incDetails").query("#txtUserName").as(Textbox.class), 
												cmbIntelliSearchAgent.query("#incDetails").query("#txtPassword").as(Textbox.class), 
												cmbIntelliSearchAgent.query("#incDetails").query("#txtConfirmPassword").as(Textbox.class), 
												cmbIntelliSearchAgent.query("#incDetails").query("#txtFirstName").as(Textbox.class), 
												cmbIntelliSearchAgent.query("#incDetails").query("#txtLastName").as(Textbox.class), 
												cmbResponsibilities.as(Combobox.class),
												cmbResponsibilities.query("#tchdnResponsibilities").as(Treechildren.class),
												cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#txtPhoneNumber").as(Textbox.class), 
												cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#txtEmailAddress").as(Textbox.class), 
												cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#txtAddress").as(Textbox.class), 
												cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#txtPostalCode").as(Textbox.class), 
												cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#txtTown").as(Textbox.class), 
												cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#txtCity").as(Textbox.class), 
												cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#cmbCountry").as(Combobox.class), 
												cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#cmbProvince").as(Combobox.class), 
												cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#cmbMunicipality").as(Combobox.class), 
												cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#dblLatitude").as(Doublebox.class), 
												cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#dblLongitude").as(Doublebox.class));
		return workerForm;
	}
	
	protected void checkUser(String userName,
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
		
		String qualifiedName = firstName + " " + lastName + " : " + userName;
		loadObjectDetails(qualifiedName);
		
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
	
	protected void assignResponsibility(ComponentAgent respComboAgent, Integer responsibilityId, String responsibilityName) {
		for (Component cmbItem : (respComboAgent.as(Combobox.class)).getChildren()) {
			if (Integer.parseInt(((Comboitem) cmbItem).getValue()) == responsibilityId) {
				InputAgent cmbResponsibilitiesInputAgent = respComboAgent.as(InputAgent.class);
				cmbResponsibilitiesInputAgent.typing(responsibilityName);
				(new ConstrainedForm()).selectComboitemByValueForcingLabel(respComboAgent.as(Combobox.class), responsibilityId.toString(), responsibilityName);
				ComponentAgent btnAssignResponsibility = respComboAgent.getNextSibling();
				btnAssignResponsibility.click();
				break;
			}
		}	
	}
}
