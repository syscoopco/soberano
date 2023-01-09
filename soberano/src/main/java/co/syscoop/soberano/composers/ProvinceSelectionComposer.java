package co.syscoop.soberano.composers;

import java.sql.SQLException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;

import co.syscoop.soberano.ui.helper.ProvinceComboboxHelper;

@SuppressWarnings({ "serial", "rawtypes" })
public class ProvinceSelectionComposer extends SelectorComposer {
	
	@Wire
	private Combobox cmbProvince;
	
	@Wire
	private Combobox cmbMunicipality;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onSelect = combobox#cmbProvince")
    public void cmbProvince_onSelect() throws SQLException {
		
		ProvinceComboboxHelper.processProvinceSelection(cmbProvince, cmbMunicipality);
    }
    	
	@Listen("onChange = combobox#cmbProvince")
    public void cmbProvince_onChange() throws SQLException {
		
		cmbMunicipality.getChildren().clear();
		cmbMunicipality.setReadonly(true);
		cmbMunicipality.setDisabled(true);
		cmbMunicipality.setSelectedItem(null);
		cmbMunicipality.setText("");
	}
	
	/*
	 * Needed to force municipality combo population on province selection under testing. 
	 * cmbProvince_onSelect event isn't triggered under testing.
	 */
	@Listen("onClick = combobox#cmbProvince")
    public void cmbProvince_onClick() throws SQLException {
		
		ProvinceComboboxHelper.processProvinceSelection(cmbProvince, cmbMunicipality);
    }
}