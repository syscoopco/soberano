package co.syscoop.soberano.ui.helper;

import java.sql.SQLException;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;

import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.domain.untracked.Province;

public class CountryComboboxHelper {
	
	static public void processCountrySelection(Combobox cmbCountry, Combobox cmbProvince, Combobox cmbPostalCode) throws SQLException {
		
		cmbProvince.getChildren().clear();
		if (cmbCountry.getSelectedItem() != null) {
			String countryCode = ((DomainObject) cmbCountry.getSelectedItem().getValue()).getStringId();
			for (DomainObject domainObject : new Province(countryCode).getAll()) {
				Comboitem newItem = new Comboitem(domainObject.getName());
				newItem.setValue(domainObject.getId());
				cmbProvince.appendChild(newItem);
			}
			cmbProvince.setReadonly(false);
			cmbProvince.setDisabled(false);
		}
		else {
			cmbProvince.setText("");
			cmbProvince.setReadonly(true);
			cmbProvince.setDisabled(true);
		}	
	}	
}
