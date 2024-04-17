package co.syscoop.soberano.composers;

import java.sql.SQLException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import co.syscoop.soberano.ui.helper.CountryComboboxHelper;
import co.syscoop.soberano.util.SpringUtility;

@SuppressWarnings({ "serial", "rawtypes" })
public class CountrySelectionComposer extends SelectorComposer {
	
	@Wire
	private Combobox cmbCountry;
	
	@Wire
	private Combobox cmbProvince;
	
	@Wire
	private Combobox cmbMunicipality;
	
	@Wire
	private Combobox cmbPostalCode;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onSelect = combobox#cmbCountry")
    public void cmbCountry_onSelect() throws SQLException {
		
		CountryComboboxHelper.processCountrySelection(cmbCountry, cmbProvince, cmbPostalCode);	
    }
    	
	@Listen("onChange = combobox#cmbCountry")
    public void cmbCountry_onChange() throws SQLException {
		
		cmbProvince.getChildren().clear();
		cmbProvince.setReadonly(true);
		cmbProvince.setDisabled(true);
		cmbProvince.setSelectedItem(null);
		cmbProvince.setText("");
		
		cmbMunicipality.getChildren().clear();
		cmbMunicipality.setReadonly(true);
		cmbMunicipality.setDisabled(true);
		cmbMunicipality.setSelectedItem(null);
		cmbMunicipality.setText("");
	}
	
	/*
	 * Needed to force province combo population on country selection under testing. 
	 * cmbCountry_onSelect event isn't triggered under testing.
	 */
	@Listen("onClick = combobox#cmbCountry")
    public void cmbCountry_onClick() throws SQLException {
		
		if (SpringUtility.underTesting()) CountryComboboxHelper.processCountrySelection(cmbCountry, cmbProvince, cmbPostalCode);	
    }
}