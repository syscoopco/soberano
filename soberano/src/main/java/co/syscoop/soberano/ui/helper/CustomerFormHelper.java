package co.syscoop.soberano.ui.helper;

import java.sql.SQLException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import co.syscoop.soberano.domain.tracked.Customer;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.util.ui.ZKUtility;
import co.syscoop.soberano.view.viewmodel.CountrySelectionViewModel;
import co.syscoop.soberano.view.viewmodel.PrinterProfileSelectionViewModel;

public class CustomerFormHelper extends TrackedObjectFormHelper {
	
	public void fillForm(Include incDetails, Integer customerId) throws SQLException {
		
		Customer customer = new Customer(customerId);
		customer.get();
		
		//store in the form the ids of shown object for subsequent modification
		((Intbox) incDetails.getParent().query("#intId")).setValue(customer.getId());
		((Textbox) incDetails.getParent().query("#txtStringId")).setText(customer.getStringId());
		
		incDetails.setVisible(true);
		Clients.scrollIntoView(incDetails.query("#txtFirstName"));
		((Button) incDetails.getParent().query("#incSouth").query("#btnApply")).setDisabled(false);
		
		ZKUtility.setValueWOValidation((Textbox) incDetails.query("#txtFirstName"), customer.getFirstName());
		ZKUtility.setValueWOValidation((Textbox) incDetails.query("#txtLastName"), customer.getLastName());
		ZKUtility.setValueWOValidation((Decimalbox) incDetails.query("#decDiscount"), customer.getDiscount());
		
		Combobox cmbPrinterProfile = (Combobox) incDetails.query("#cmbPrinterProfile");
		PrinterProfileSelectionViewModel pfSelectionViewModel = new PrinterProfileSelectionViewModel();
		cmbPrinterProfile.setModel(pfSelectionViewModel.getModel());
		
		if (customer.getPrinterProfile() == null || customer.getPrinterProfile() == 0) {
			cmbPrinterProfile.setSelectedItem(null);
			cmbPrinterProfile.setText("");
		}			
		else
			ZKUtility.setValueWOValidation(cmbPrinterProfile, customer.getPrinterProfile());
			
		
		Include incContactData = (Include) incDetails.query("#incContactData");
		ZKUtility.setValueWOValidation((Textbox) incContactData.query("#txtPhoneNumber"), customer.getContactData().getMobilePhoneNumber());
		ZKUtility.setValueWOValidation((Textbox) incContactData.query("#txtEmailAddress"), customer.getContactData().getEmailAddress());
		ZKUtility.setValueWOValidation((Textbox) incContactData.query("#txtAddress"), customer.getContactData().getAddress());
		ZKUtility.setValueWOValidation((Textbox) incContactData.query("#cmbPostalCode"), customer.getContactData().getPostalCode());
		ZKUtility.setValueWOValidation((Textbox) incContactData.query("#txtTown"), customer.getContactData().getTown());
		ZKUtility.setValueWOValidation((Textbox) incContactData.query("#txtCity"), customer.getContactData().getCity());
		Combobox cmbCountry = (Combobox) incContactData.query("#cmbCountry");
		
		CountrySelectionViewModel cSelectionViewModel = new CountrySelectionViewModel();
		cmbCountry.setModel(cSelectionViewModel.getModel());
		
		ZKUtility.setValueWOValidation(cmbCountry, customer.getContactData().getCountryCode());
		Combobox cmbProvince = (Combobox) incContactData.query("#cmbProvince");
		CountryComboboxHelper.processCountrySelection(cmbCountry, cmbProvince);
		ZKUtility.setValueWOValidation(cmbProvince, customer.getContactData().getProvinceId().toString());
		Combobox cmbMunicipality = (Combobox) incContactData.query("#cmbMunicipality");
		ProvinceComboboxHelper.processProvinceSelection(cmbProvince, cmbMunicipality);
		ZKUtility.setValueWOValidation(cmbMunicipality, customer.getContactData().getMunicipalityId().toString());
		ZKUtility.setValueWOValidation((Doublebox) incContactData.query("#dblLatitude"), customer.getContactData().getLatitude());
		ZKUtility.setValueWOValidation((Doublebox) incContactData.query("#dblLongitude"), customer.getContactData().getLongitude());
	}
	
	@Override
	public void fillForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException {
		
		fillForm(incDetails, ((DomainObject) data.getData().getValue()).getId());
	}

	@Override
	public void cleanForm(Include incDetails) {
		
		Executions.sendRedirect("/new_customer.zul");
	}

	@Override
	public Integer recordFromForm(Include incDetails) throws Exception {
		
		Include incContactData = (Include) incDetails.query("#incContactData");
		
		Comboitem iPrinterProfileItem = ((Combobox) incDetails.query("#cmbPrinterProfile")).getSelectedItem();
		Integer iPrinterProfileId = iPrinterProfileItem != null ? ((DomainObject) iPrinterProfileItem.getValue()).getId() : 0;
				
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
							((Decimalbox) incDetails.query("#decDiscount")).getValue(),
							iPrinterProfileId))
					.record();
	}

	@Override
	public Integer modifyFromForm(Include incDetails) throws Exception {
		
		Comboitem iPrinterProfileItem = ((Combobox) incDetails.query("#cmbPrinterProfile")).getSelectedItem();
		Integer iPrinterProfileId = iPrinterProfileItem != null ? ((DomainObject) iPrinterProfileItem.getValue()).getId() : 0;
		
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
											((Decimalbox) incDetails.query("#decDiscount")).getValue(),
											iPrinterProfileId));
		return super.getTrackedObject().modify();
	}
}
