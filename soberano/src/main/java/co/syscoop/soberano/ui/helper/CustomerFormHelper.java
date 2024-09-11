package co.syscoop.soberano.ui.helper;

import java.sql.SQLException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import co.syscoop.soberano.domain.tracked.Customer;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.util.ui.ZKUtilitity;
import co.syscoop.soberano.view.viewmodel.CountrySelectionViewModel;

public class CustomerFormHelper extends TrackedObjectFormHelper {
	
	@Override
	public void fillForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException {
		
		Customer customer = new Customer(((DomainObject) data.getData().getValue()).getId());
		customer.get();
		
		//store in the form the ids of shown object for subsequent modification
		((Intbox) incDetails.getParent().query("#intId")).setValue(customer.getId());
		((Textbox) incDetails.getParent().query("#txtStringId")).setText(customer.getStringId());
		
		incDetails.setVisible(true);
		Clients.scrollIntoView(incDetails.query("#txtFirstName"));
		((Button) incDetails.getParent().query("#incSouth").query("#btnApply")).setDisabled(false);
		
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtFirstName"), customer.getFirstName());
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtLastName"), customer.getLastName());
		ZKUtilitity.setValueWOValidation((Decimalbox) incDetails.query("#decDiscount"), customer.getDiscount());
		
		Include incContactData = (Include) incDetails.query("#incContactData");
		ZKUtilitity.setValueWOValidation((Textbox) incContactData.query("#txtPhoneNumber"), customer.getContactData().getMobilePhoneNumber());
		ZKUtilitity.setValueWOValidation((Textbox) incContactData.query("#txtEmailAddress"), customer.getContactData().getEmailAddress());
		ZKUtilitity.setValueWOValidation((Textbox) incContactData.query("#txtAddress"), customer.getContactData().getAddress());
		ZKUtilitity.setValueWOValidation((Textbox) incContactData.query("#cmbPostalCode"), customer.getContactData().getPostalCode());
		ZKUtilitity.setValueWOValidation((Textbox) incContactData.query("#txtTown"), customer.getContactData().getTown());
		ZKUtilitity.setValueWOValidation((Textbox) incContactData.query("#txtCity"), customer.getContactData().getCity());
		Combobox cmbCountry = (Combobox) incContactData.query("#cmbCountry");
		
		CountrySelectionViewModel cSelectionViewModel = new CountrySelectionViewModel();
		cmbCountry.setModel(cSelectionViewModel.getModel());
		
		ZKUtilitity.setValueWOValidation(cmbCountry, customer.getContactData().getCountryCode());
		Combobox cmbProvince = (Combobox) incContactData.query("#cmbProvince");
		CountryComboboxHelper.processCountrySelection(cmbCountry, cmbProvince);
		ZKUtilitity.setValueWOValidation(cmbProvince, customer.getContactData().getProvinceId().toString());
		Combobox cmbMunicipality = (Combobox) incContactData.query("#cmbMunicipality");
		ProvinceComboboxHelper.processProvinceSelection(cmbProvince, cmbMunicipality);
		ZKUtilitity.setValueWOValidation(cmbMunicipality, customer.getContactData().getMunicipalityId().toString());
		ZKUtilitity.setValueWOValidation((Doublebox) incContactData.query("#dblLatitude"), customer.getContactData().getLatitude());
		ZKUtilitity.setValueWOValidation((Doublebox) incContactData.query("#dblLongitude"), customer.getContactData().getLongitude());
	}

	@Override
	public void cleanForm(Include incDetails) {
		
		Executions.sendRedirect("/new_customer.zul");
	}

	@Override
	public Integer recordFromForm(Include incDetails) throws Exception {
		
		Include incContactData = (Include) incDetails.query("#incContactData");
		return (new Customer(0,
							0,
							((Textbox) incContactData.query("#txtEmailAddress")).getValue(),
							((Textbox) incDetails.query("#txtFirstName")).getValue(),
							((Textbox) incDetails.query("#txtLastName")).getValue(),
							((Textbox) incContactData.query("#txtPhoneNumber")).getValue(),
							((DomainObject) (((Combobox) incContactData.query("#cmbCountry")).getSelectedItem().getValue())).getStringId(),
							((Textbox) incContactData.query("#txtAddress")).getValue(),
							((Textbox) incContactData.query("#cmbPostalCode")).getValue(),
							((Textbox) incContactData.query("#txtTown")).getValue(),
							((Combobox) incContactData.query("#cmbMunicipality")).getSelectedItem().getValue(),
							((Textbox) incContactData.query("#txtCity")).getValue(),
							((Combobox) incContactData.query("#cmbProvince")).getSelectedItem().getValue(),
							((Doublebox) incContactData.query("#dblLatitude")).getValue(),
							((Doublebox) incContactData.query("#dblLongitude")).getValue(),
							((Decimalbox) incDetails.query("#decDiscount")).getValue()))
					.record();
	}

	@Override
	public Integer modifyFromForm(Include incDetails) throws Exception {
		
		Include incContactData = (Include) incDetails.query("#incContactData");
		super.setTrackedObject(new Customer(((Intbox) incDetails.getParent().query("#intId")).getValue(),
											0,
											((Textbox) incContactData.query("#txtEmailAddress")).getValue(),
											((Textbox) incDetails.query("#txtFirstName")).getValue(),
											((Textbox) incDetails.query("#txtLastName")).getValue(),
											((Textbox) incContactData.query("#txtPhoneNumber")).getValue(),
											((DomainObject) (((Combobox) incContactData.query("#cmbCountry")).getSelectedItem().getValue())).getStringId(),
											((Textbox) incContactData.query("#txtAddress")).getValue(),
											((Textbox) incContactData.query("#cmbPostalCode")).getValue(),
											((Textbox) incContactData.query("#txtTown")).getValue(),
											((Combobox) incContactData.query("#cmbMunicipality")).getSelectedItem().getValue(),
											((Textbox) incContactData.query("#txtCity")).getValue(),
											((Combobox) incContactData.query("#cmbProvince")).getSelectedItem().getValue(),
											((Doublebox) incContactData.query("#dblLatitude")).getValue(),
											((Doublebox) incContactData.query("#dblLongitude")).getValue(),
											((Decimalbox) incDetails.query("#decDiscount")).getValue()));
		return super.getTrackedObject().modify();
	}
}
