package co.syscoop.soberano.test.helper;

import java.util.Arrays;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Treechildren;

public class WorkerForm extends ConstrainedForm {
	
	private Textbox txtUserName;
	private Textbox txtPassword;
	private Textbox txtConfirmPassword;
	private Textbox txtFirstName;
	private Textbox txtLastName;
	private Combobox cmbResponsibilities;
	private Treechildren tchdnResponsibilities;
	private Textbox txtPhoneNumber;
	private Textbox txtEmailAddress;
	private Textbox txtAddress;
	private Textbox txtPostalCode;
	private Textbox txtTown;
	private Textbox txtCity;
	private Combobox cmbCountry;
	private Combobox cmbProvince;
	private Combobox cmbMunicipality;
	private Doublebox dblLatitude;
	private Doublebox dblLongitude;	

	public WorkerForm(DesktopAgent desktop,
					Textbox txtUserName,
					Textbox txtPassword,
					Textbox txtConfirmPassword,
					Textbox txtFirstName,
					Textbox txtLastName,
					Combobox cmbResponsibilities,
					Textbox txtPhoneNumber,
					Textbox txtEmailAddress,
					Textbox txtAddress,
					Textbox txtPostalCode,
					Textbox txtTown,
					Textbox txtCity,
					Combobox cmbCountry,
					Combobox cmbProvince,
					Combobox cmbMunicipality,
					Doublebox dblLatitude,
					Doublebox dblLongitude) {
		
		this.constrainedComponents = Arrays.asList("txtUserName",
													"txtPassword", 
													"txtConfirmPassword",
													"txtFirstName",
													"txtLastName",
													"txtPhoneNumber",
													"txtEmailAddress",
													"cmbCountry",
													"cmbProvince",
													"cmbMunicipality");
		
		this.setDesktop(desktop);
		
		this.setTxtUserName(txtUserName);
		this.constrainableComponents.add(txtUserName);
		this.constrainableComponentById.put("txtUserName", txtUserName);
		
		this.setTxtPassword(txtPassword);
		this.constrainableComponents.add(txtPassword);
		this.constrainableComponentById.put("txtPassword", txtPassword);
		
		this.setTxtConfirmPassword(txtConfirmPassword);
		this.constrainableComponents.add(txtConfirmPassword);
		this.constrainableComponentById.put("txtConfirmPassword", txtConfirmPassword);
		
		this.setTxtFirstName(txtFirstName);
		this.constrainableComponents.add(txtFirstName);
		this.constrainableComponentById.put("txtFirstName", txtFirstName);
		
		this.setTxtLastName(txtLastName);
		this.constrainableComponents.add(txtLastName);
		this.constrainableComponentById.put("txtLastName", txtLastName);
		
		this.setCmbResponsibilities(cmbResponsibilities);
		this.constrainableComponents.add(cmbResponsibilities);
		this.constrainableComponentById.put("cmbResponsibilities", cmbResponsibilities);
		
		this.setTxtPhoneNumber(txtPhoneNumber);
		this.constrainableComponents.add(txtPhoneNumber);
		this.constrainableComponentById.put("txtPhoneNumber", txtPhoneNumber);
		
		this.setTxtEmailAddress(txtEmailAddress);
		this.constrainableComponents.add(txtEmailAddress);
		this.constrainableComponentById.put("txtEmailAddress", txtEmailAddress);
		
		this.setTxtAddress(txtAddress);
		this.constrainableComponents.add(txtAddress);
		this.constrainableComponentById.put("txtAddress", txtAddress);
		
		this.setTxtPostalCode(txtPostalCode);
		this.constrainableComponents.add(txtPostalCode);
		this.constrainableComponentById.put("txtPostalCode", txtPostalCode);
		
		this.setTxtTown(txtTown);
		this.constrainableComponents.add(txtTown);
		this.constrainableComponentById.put("txtTown", txtTown);
		
		this.setTxtCity(txtCity);
		this.constrainableComponents.add(txtCity);
		this.constrainableComponentById.put("txtCity", txtCity);
		
		this.setCmbCountry(cmbCountry);
		this.constrainableComponents.add(cmbCountry);
		this.constrainableComponentById.put("cmbCountry", cmbCountry);
		
		this.setCmbProvince(cmbProvince);
		this.constrainableComponents.add(cmbProvince);
		this.constrainableComponentById.put("cmbProvince", cmbProvince);
		
		this.setCmbMunicipality(cmbMunicipality);
		this.constrainableComponents.add(cmbMunicipality);
		this.constrainableComponentById.put("cmbMunicipality", cmbMunicipality);
		
		this.setDblLatitude(dblLatitude);
		this.constrainableComponents.add(dblLatitude);
		this.constrainableComponentById.put("dblLatitude", dblLatitude);
		
		this.setDblLongitude(dblLongitude);
		this.constrainableComponents.add(dblLongitude);
		this.constrainableComponentById.put("dblLongitude", dblLongitude);
	}
	
	public WorkerForm(DesktopAgent desktop,
					Textbox txtUserName,
					Textbox txtPassword,
					Textbox txtConfirmPassword,
					Textbox txtFirstName,
					Textbox txtLastName,
					Combobox cmbResponsibilities,
					Treechildren tchdnResponsibilities,
					Textbox txtPhoneNumber,
					Textbox txtEmailAddress,
					Textbox txtAddress,
					Textbox txtPostalCode,
					Textbox txtTown,
					Textbox txtCity,
					Combobox cmbCountry,
					Combobox cmbProvince,
					Combobox cmbMunicipality,
					Doublebox dblLatitude,
					Doublebox dblLongitude) {
		
		this(desktop,
				txtUserName,
				txtPassword,
				txtConfirmPassword,
				txtFirstName,
				txtLastName,
				cmbResponsibilities,
				txtPhoneNumber,
				txtEmailAddress,
				txtAddress,
				txtPostalCode,
				txtTown,
				txtCity,
				cmbCountry,
				cmbProvince,
				cmbMunicipality,
				dblLatitude,
				dblLongitude);
		this.tchdnResponsibilities = tchdnResponsibilities;
	}

	public Textbox getTxtUserName() {
		return txtUserName;
	}

	public void setTxtUserName(Textbox txtUserName) {
		this.txtUserName = txtUserName;
	}

	public Textbox getTxtPassword() {
		return txtPassword;
	}

	public void setTxtPassword(Textbox txtPassword) {
		this.txtPassword = txtPassword;
	}

	public Textbox getTxtConfirmPassword() {
		return txtConfirmPassword;
	}

	public void setTxtConfirmPassword(Textbox txtConfirmPassword) {
		this.txtConfirmPassword = txtConfirmPassword;
	}

	public Textbox getTxtFirstName() {
		return txtFirstName;
	}

	public void setTxtFirstName(Textbox txtFirstName) {
		this.txtFirstName = txtFirstName;
	}

	public Textbox getTxtLastName() {
		return txtLastName;
	}

	public void setTxtLastName(Textbox txtLastName) {
		this.txtLastName = txtLastName;
	}

	public Combobox getCmbResponsibilities() {
		return cmbResponsibilities;
	}

	public void setCmbResponsibilities(Combobox cmbResponsibilities) {
		this.cmbResponsibilities = cmbResponsibilities;
	}

	public Textbox getTxtPhoneNumber() {
		return txtPhoneNumber;
	}

	public void setTxtPhoneNumber(Textbox txtPhoneNumber) {
		this.txtPhoneNumber = txtPhoneNumber;
	}

	public Textbox getTxtEmailAddress() {
		return txtEmailAddress;
	}

	public void setTxtEmailAddress(Textbox txtEmailAddress) {
		this.txtEmailAddress = txtEmailAddress;
	}

	public Textbox getTxtAddress() {
		return txtAddress;
	}

	public void setTxtAddress(Textbox txtAddress) {
		this.txtAddress = txtAddress;
	}

	public Textbox getTxtPostalCode() {
		return txtPostalCode;
	}

	public void setTxtPostalCode(Textbox txtPostalCode) {
		this.txtPostalCode = txtPostalCode;
	}

	public Textbox getTxtTown() {
		return txtTown;
	}

	public void setTxtTown(Textbox txtTown) {
		this.txtTown = txtTown;
	}

	public Textbox getTxtCity() {
		return txtCity;
	}

	public void setTxtCity(Textbox txtCity) {
		this.txtCity = txtCity;
	}

	public Combobox getCmbCountry() {
		return cmbCountry;
	}

	public void setCmbCountry(Combobox cmbCountry) {
		this.cmbCountry = cmbCountry;
	}

	public Combobox getCmbProvince() {
		return cmbProvince;
	}

	public void setCmbProvince(Combobox cmbProvince) {
		this.cmbProvince = cmbProvince;
	}

	public Combobox getCmbMunicipality() {
		return cmbMunicipality;
	}

	public void setCmbMunicipality(Combobox cmbMunicipality) {
		this.cmbMunicipality = cmbMunicipality;
	}

	public Doublebox getDblLatitude() {
		return dblLatitude;
	}

	public void setDblLatitude(Doublebox dblLatitude) {
		this.dblLatitude = dblLatitude;
	}

	public Doublebox getDblLongitude() {
		return dblLongitude;
	}

	public void setDblLongitude(Doublebox dblLongitude) {
		this.dblLongitude = dblLongitude;
	}

	public Treechildren getTchdnResponsibilities() {
		return tchdnResponsibilities;
	}

	public void setTchdnResponsibilities(Treechildren tchdnResponsibilities) {
		this.tchdnResponsibilities = tchdnResponsibilities;
	}
}
