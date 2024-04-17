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
	protected static Textbox cmbPostalCode = null;
	protected static Textbox txtTown = null;
	protected static Textbox txtCity = null;
	protected static Combobox cmbCountry = null;
	protected static Combobox cmbProvince = null;
	protected static Combobox cmbMunicipality = null;
	protected static Doublebox dblLatitude = null;
	protected static Doublebox dblLongitude = null;
	
	protected void testWorkerMustBeAssignedToAResponsibilityException(Throwable ex) {
		Throwable cause = ExceptionTreatment.getRootCause(ex);
		assertEquals("co.syscoop.soberano.exception.WorkerMustBeAssignedToAResponsibilityException", cause.getClass().getName(), "Only co.syscoop.soberano.exception.WorkerMustBeAssignedToAResponsibilityException can be caught here.");
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
												cmbIntelliSearchAgent.query("#incDetails").query("#incContactData").query("#cmbPostalCode").as(Combobox.class), 
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
							String responsibilities[],
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
		
		assertEquals(userName.toLowerCase(), txtUserName.getText().toLowerCase(), "Wrong username shown for user " +  qualifiedName);
		assertEquals(firstName.toLowerCase(), txtFirstName.getText().toLowerCase(), "Wrong firstName shown for user " +  qualifiedName);
		assertEquals(lastName.toLowerCase(), txtLastName.getText().toLowerCase(), "Wrong lastName shown for user " +  qualifiedName);
		
		assertEquals(1, tchdnResponsibilities.getChildren().size(), "None or more than one assigments for user " +  qualifiedName + " One and only one is exṕected.");
		
		for (int i = 0; i < responsibilities.length; i++) {
			assertEquals(responsibilities[i], ((Treeitem) tchdnResponsibilities.getChildren().get(i)).getLabel(), "Wrong responsibility shown for user " +  qualifiedName);
		}
				
		assertEquals(phoneNumber, txtPhoneNumber.getText(), "Wrong phoneNumber shown for user " +  qualifiedName);
		assertEquals(emailAddress.toLowerCase(), txtEmailAddress.getText().toLowerCase(), "Wrong emailAddress shown for user " +  qualifiedName);
		assertEquals(country, cmbCountry.getText(), "Wrong country shown for user " +  qualifiedName);
		assertEquals(province, cmbProvince.getText(), "Wrong province shown for user " +  qualifiedName);
		assertEquals(municipality, cmbMunicipality.getText(), "Wrong municipality shown for user " +  qualifiedName);
		assertEquals(address, txtAddress.getText(), "Wrong address shown for user " +  qualifiedName);
		assertEquals(postalCode, cmbPostalCode.getText(), "Wrong postalCode shown for user " +  qualifiedName);
		assertEquals(town, txtTown.getText(), "Wrong town shown for user " +  qualifiedName);
		assertEquals(city, txtCity.getText(), "Wrong city shown for user " +  qualifiedName);
		assertEquals(latitude, dblLatitude.getValue(), "Wrong latitude shown for user " +  qualifiedName);
		assertEquals(longitude, dblLongitude.getValue(), "Wrong longitude shown for user " +  qualifiedName);			
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
