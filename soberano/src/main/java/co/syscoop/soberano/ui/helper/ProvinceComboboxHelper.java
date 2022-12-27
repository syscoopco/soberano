package co.syscoop.soberano.ui.helper;

import java.sql.SQLException;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;

import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.domain.untracked.Municipality;

public class ProvinceComboboxHelper {

	static public void processProvinceSelection(Combobox cmbProvince, Combobox cmbMunicipality) throws SQLException {
		
		cmbMunicipality.getChildren().clear();
		if (cmbProvince.getSelectedItem() != null) {
			Integer provinceId = cmbProvince.getSelectedItem().getValue();
			for (DomainObject domainObject : new Municipality(provinceId).getAll()) {
				Comboitem newItem = new Comboitem(domainObject.getName());
				newItem.setValue(domainObject.getId());
				cmbMunicipality.appendChild(newItem);
			}
			cmbMunicipality.setReadonly(false);
			cmbMunicipality.setDisabled(false);
		}
		else {
			cmbMunicipality.setText("");
			cmbMunicipality.setReadonly(true);
			cmbMunicipality.setDisabled(true);
		}	
	}	
}
