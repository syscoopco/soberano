package co.syscoop.soberano.test.helper;

import java.util.Arrays;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Textbox;

public class CustomerForm extends ConstrainedForm {
	
	private Textbox txtFirstName;
	private Textbox txtLastName;
	private Decimalbox decDiscount;
	private Textbox txtPhoneNumber;
	private Textbox txtEmailAddress;
	private Textbox txtAddress;
	private Combobox cmbPostalCode;
	private Textbox txtTown;
	private Textbox txtCity;
	private Combobox cmbCountry;
	private Combobox cmbProvince;
	private Combobox cmbMunicipality;
	private Doublebox dblLatitude;
	private Doublebox dblLongitude;	

	public CustomerForm(DesktopAgent desktop,
					Textbox txtFirstName,
					Textbox txtLastName,
					Decimalbox decDiscount,
					Textbox txtPhoneNumber,
					Textbox txtEmailAddress,
					Textbox txtAddress,
					Combobox cmbPostalCode,
					Textbox txtTown,
					Textbox txtCity,
					Combobox cmbCountry,
					Combobox cmbProvince,
					Combobox cmbMunicipality,
					Doublebox dblLatitude,
					Doublebox dblLongitude) {
		
		this.constrainedComponents = Arrays.asList("txtFirstName",
													"txtLastName",
													"decDiscount",
													"txtPhoneNumber",
													"txtEmailAddress",
													"cmbCountry",
													"cmbProvince",
													"cmbMunicipality");
		
		this.setDesktop(desktop);
		
		this.setTxtFirstName(txtFirstName);
		this.constrainableComponents.add(txtFirstName);
		this.constrainableComponentById.put("txtFirstName", txtFirstName);
		
		this.setTxtLastName(txtLastName);
		this.constrainableComponents.add(txtLastName);
		this.constrainableComponentById.put("txtLastName", txtLastName);
		
		this.setDecDiscount(decDiscount);
		this.constrainableComponents.add(decDiscount);
		this.constrainableComponentById.put("decDiscount", decDiscount);
		
		this.setTxtPhoneNumber(txtPhoneNumber);
//		this.constrainableComponents.add(txtPhoneNumber);
//		this.constrainableComponentById.put("txtPhoneNumber", txtPhoneNumber);
		
		this.setTxtEmailAddress(txtEmailAddress);
		this.constrainableComponents.add(txtEmailAddress);
		this.constrainableComponentById.put("txtEmailAddress", txtEmailAddress);
		
		this.setTxtAddress(txtAddress);
		
		this.setCmbPostalCode(cmbPostalCode);
		
		this.setTxtTown(txtTown);
		
		this.setTxtCity(txtCity);
		
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
		
		this.setDblLongitude(dblLongitude);
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
	
	public Decimalbox getDecDiscount() {
		return decDiscount;
	}

	public void setDecDiscount(Decimalbox decDiscount) {
		this.decDiscount = decDiscount;
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

	public Textbox getCmbPostalCode() {
		return cmbPostalCode;
	}

	public void setCmbPostalCode(Combobox cmbPostalCode) {
		this.cmbPostalCode = cmbPostalCode;
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
}
