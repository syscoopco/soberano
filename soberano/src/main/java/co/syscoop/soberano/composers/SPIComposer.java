package co.syscoop.soberano.composers;

import java.sql.SQLException;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Box;
import org.zkoss.zul.Checkbox;
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
	
	@Wire
	private Checkbox chkWithOpeningStock;
	
	@Wire
	private Checkbox chkWithStockOnClosure;
	
	@Wire
	private Checkbox chkWithChanges;
	
	@Wire
	private Checkbox chkSurplus;
	
	private void processParamSelection() throws SQLException {
		
		SPIGridModel spiGridModel = null;
		
		Integer closureId = 0;
		try {closureId = ZKUtilitity.getObjectIdFromURLQuery("scid");} catch(Exception ex) {};		
		
		if (cmbWarehouse.getSelectedItem() != null && cmbMaterial.getSelectedItem() != null) {
			
			//re-render the grid with the selected warehouse and item
			spiGridModel = new SPIGridModel(closureId, 
										((DomainObject) cmbWarehouse.getSelectedItem().getValue()).getId(),
										((DomainObject) cmbMaterial.getSelectedItem().getValue()).getId(),
										chkWithOpeningStock.isChecked(),
										chkWithStockOnClosure.isChecked(),
										chkWithChanges.isChecked(),
										chkSurplus.isChecked(),
										"");			
		}
		else if (cmbWarehouse.getSelectedItem() != null) {
			
			//re-render the grid with the selected warehouse
			spiGridModel = new SPIGridModel(closureId, 
											((DomainObject) cmbWarehouse.getSelectedItem().getValue()).getId(),
											0,
											chkWithOpeningStock.isChecked(),
											chkWithStockOnClosure.isChecked(),
											chkWithChanges.isChecked(),
											chkSurplus.isChecked(),
											cmbMaterial.getText());
		}
		else if (cmbMaterial.getSelectedItem() != null) {
			
			//re-render the grid with the selected material
			spiGridModel = new SPIGridModel(closureId, 
											0,
											((DomainObject) cmbMaterial.getSelectedItem().getValue()).getId(),
											chkWithOpeningStock.isChecked(),
											chkWithStockOnClosure.isChecked(),
											chkWithChanges.isChecked(),
											chkSurplus.isChecked(),
											"");
		}
		else {
			//re-render the grid with the whole spi, filtered by material name
			spiGridModel = new SPIGridModel(closureId, 
											0, 
											0,
											chkWithOpeningStock.isChecked(),
											chkWithStockOnClosure.isChecked(),
											chkWithChanges.isChecked(),
											chkSurplus.isChecked(),
											cmbMaterial.getText());	
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
	
	@Listen("onCheck = checkbox#chkWithOpeningStock")
    public void chkWithOpeningStock_onCheck() throws Throwable {
		processParamSelection();
	}
	
	@Listen("onCheck = checkbox#chkWithStockOnClosure")
    public void chkWithStockOnClosure_onCheck() throws Throwable {
		processParamSelection();
	}
	
	@Listen("onCheck = checkbox#chkWithChanges")
    public void chkWithChanges_onCheck() throws Throwable {
		processParamSelection();
	}
	
	@Listen("onCheck = checkbox#chkSurplus")
    public void chkSurplus_onCheck() throws Throwable {
		processParamSelection();
	}
}