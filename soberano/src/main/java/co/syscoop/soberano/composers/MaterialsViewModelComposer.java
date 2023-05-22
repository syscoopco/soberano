package co.syscoop.soberano.composers;

import java.sql.SQLException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;

import co.syscoop.soberano.domain.tracked.Unit;
import co.syscoop.soberano.domain.untracked.DomainObject;

@SuppressWarnings({ "serial", "rawtypes" })
public /*abstract*/ class MaterialsViewModelComposer extends SelectorComposer {
	
	@Wire
	private Combobox cmbMaterial;
	
	@Wire
	private Combobox cmbUnit;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	private void processMaterialSelection() throws SQLException {
			
		cmbUnit.getChildren().clear();
		if (cmbMaterial.getSelectedItem() != null) {
			Integer materialId = ((DomainObject) cmbMaterial.getSelectedItem().getValue()).getId();
			for (DomainObject unit : new Unit().getAll(materialId)) {
				Comboitem newItem = new Comboitem(unit.getName());
				newItem.setValue(unit.getId());
				cmbUnit.appendChild(newItem);
			}
			cmbUnit.setReadonly(false);
			cmbUnit.setDisabled(false);
		}
		else {
			cmbUnit.setText("");
			cmbUnit.setReadonly(true);
			cmbUnit.setDisabled(true);
		}	
	}
	
	@Listen("onChange = combobox#cmbMaterial")
    public void cmb_onChange() throws SQLException {
		processMaterialSelection();
	}
	
	/*
	 * Needed for testing. 
	 * combo_onChange event isn't triggered under testing.
	 */
	@Listen("onClick = combobox#cmbMaterial")
    public void cmb_onClick() throws SQLException {
		processMaterialSelection();
	}
}