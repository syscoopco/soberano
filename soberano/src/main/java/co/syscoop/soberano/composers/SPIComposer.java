package co.syscoop.soberano.composers;

import java.sql.SQLException;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Box;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.models.SPIGridModel;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.util.ui.ZKUtilitity;

@SuppressWarnings({ "serial", "rawtypes" })
public class SPIComposer extends SelectorComposer {
	
	@Wire
	private Combobox cmbWarehouse;
	
	@Wire
	private Combobox cmbMaterial;
	
	@Wire
	private Box boxDetails;
	
	private void processParamSelection() throws SQLException {
		
		SPIGridModel spiGridModel = null;
		
		Integer closureId = 0;
		try {closureId = ZKUtilitity.getObjectIdFromURLQuery("scid");} catch(Exception ex) {};		
		
		if (cmbWarehouse.getSelectedItem() != null && cmbMaterial.getSelectedItem() != null) {
			
			//re-render the grid with the selected warehouse and item
			spiGridModel = new SPIGridModel(closureId, 
										((DomainObject) cmbWarehouse.getSelectedItem().getValue()).getId(),
										((DomainObject) cmbMaterial.getSelectedItem().getValue()).getId());			
		}
		else if (cmbWarehouse.getSelectedItem() != null) {
			
			//re-render the grid with the selected warehouse
			spiGridModel = new SPIGridModel(closureId, 
										((DomainObject) cmbWarehouse.getSelectedItem().getValue()).getId(),
										0);
		}
		else if (cmbMaterial.getSelectedItem() != null) {
			
			//re-render the grid with the selected material
			spiGridModel = new SPIGridModel(closureId, 
										0,
										((DomainObject) cmbMaterial.getSelectedItem().getValue()).getId());
		}
		else {
			//re-render the grid with the whole spi
			spiGridModel = new SPIGridModel(closureId, 0, 0);	
		}
		((Grid) boxDetails.getParent().getParent().getParent().query("center").query("window").query("grid")).setModel(spiGridModel);
	}
	
	@Listen("onChange = combobox#cmbWarehouse")
    public void cmbWarehouse_onChange() throws SQLException {
		processParamSelection();
	}
	
	@Listen("onChange = combobox#cmbMaterial")
    public void cmbMaterial_onChange() throws SQLException {
		processParamSelection();
	}
	
	/*
	 * Needed for testing. 
	 * combo_onChange event isn't triggered under testing.
	 */
	@Listen("onClick = combobox#cmbWarehouse")
    public void cmbWarehouse_onClick() throws SQLException {
		if (SpringUtility.underTesting()) processParamSelection();
	}
	
	/*
	 * Needed for testing. 
	 * combo_onChange event isn't triggered under testing.
	 */
	@Listen("onClick = combobox#cmbMaterial")
    public void cmbMaterial_onClick() throws SQLException {
		if (SpringUtility.underTesting()) processParamSelection();
	}
}