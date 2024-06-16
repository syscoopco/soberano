package co.syscoop.soberano.test.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Textbox;
import co.syscoop.soberano.util.SpringUtility;

public class CustomerActionTest extends ActionTest {
	
	protected static Textbox txtFirstName = null;
	protected static Textbox txtLastName = null;
	protected static Decimalbox decDiscount = null;
	protected static Textbox txtPhoneNumber = null;
	protected static Textbox txtEmailAddress = null;
	protected static Textbox txtAddress = null;
	protected static Combobox cmbPostalCode = null;
	protected static Textbox txtTown = null;
	protected static Textbox txtCity = null;
	protected static Combobox cmbCountry = null;
	protected static Combobox cmbProvince = null;
	protected static Combobox cmbMunicipality = null;
	protected static Doublebox dblLatitude = null;
	protected static Doublebox dblLongitude = null;
	
	protected static CustomerForm setFormComponents(String user, String formZulFilename) {
		
		SpringUtility.setLoggedUserForTesting(user);
		DesktopAgent desktop = Zats.newClient().connect("/" + formZulFilename);
		cmbIntelliSearchAgent = desktop.query("center").query("combobox");
		cmbIntelliSearch = cmbIntelliSearchAgent.as(Combobox.class);
		CustomerForm customerForm = new CustomerForm(desktop, 
												cmbIntelliSearchAgent.query("#incDetails").query("#txtFirstName").as(Textbox.class), 
												cmbIntelliSearchAgent.query("#incDetails").query("#txtLastName").as(Textbox.class),
												cmbIntelliSearchAgent.query("#incDetails").query("#decDiscount").as(Decimalbox.class), 
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
		return customerForm;
	}
	
	protected void checkCustomer(String firstName,
							String lastName,
							BigDecimal discount,
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
		
		String qualifiedName = firstName + " " + lastName + " : " + emailAddress;
		loadObjectDetails(qualifiedName);
		
		assertEquals(firstName.toLowerCase(), txtFirstName.getText().toLowerCase(), "Wrong firstName shown for customer " +  qualifiedName);
		assertEquals(lastName.toLowerCase(), txtLastName.getText().toLowerCase(), "Wrong lastName shown for customer " +  qualifiedName);
		assertEquals(decDiscount.getValue().subtract(discount).abs().doubleValue() < 0.00000001, true, "Wrong discount rate shown for customer " + qualifiedName + ". Expected discount rate: " + discount.toPlainString() + ". Shown discount rate:" + decDiscount.getValue().toPlainString());
				
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
}
