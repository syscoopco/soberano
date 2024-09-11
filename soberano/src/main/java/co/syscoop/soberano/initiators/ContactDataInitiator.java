package co.syscoop.soberano.initiators;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zk.ui.util.InitiatorExt;
import org.zkoss.zul.Combobox;
import co.syscoop.soberano.ui.helper.CountryComboboxHelper;
import co.syscoop.soberano.ui.helper.ProvinceComboboxHelper;
import co.syscoop.soberano.util.ui.ComboboxHelper;
import co.syscoop.soberano.util.ui.ZKUtilitity;

public class ContactDataInitiator implements Initiator, InitiatorExt {
	
	@Override
	public void doAfterCompose(Page page, Component[] comps) throws Exception {
		try {
			Component incDetails = comps[1].getParent().getParent().getParent().getParent().query("#wndContentPanel").query("#incDetails");
			Component incContactData = incDetails.query("#incContactData");
			Combobox cmbCountry = (Combobox) incContactData.query("combobox").query("#cmbCountry");
			ZKUtilitity.setValueWOValidation(cmbCountry, "__");
			Combobox cmbProvince = (Combobox) cmbCountry.query("#cmbProvince");
			CountryComboboxHelper.processCountrySelection(cmbCountry, cmbProvince);
			ZKUtilitity.setValueWOValidation(cmbProvince, new Integer(0).toString());
			Combobox cmbMunicipality = (Combobox) cmbCountry.query("#cmbMunicipality");
			ProvinceComboboxHelper.processProvinceSelection(cmbProvince, cmbMunicipality);
			cmbMunicipality.setSelectedItem(ComboboxHelper.getItemByText(cmbMunicipality, "__"));
		}
		catch(Exception ex) {
			ex.printStackTrace();
			ex.fillInStackTrace();
		}		
	}
	
	@Override
	public boolean doCatch(Throwable ex) throws Exception {
		return false;
	}
	
	@Override
	public void doFinally() throws Exception {		
	}
	
	@Override
	public void doInit(Page page, Map<String, Object> args) throws Exception {
	}
}
