package co.syscoop.soberano.composers;

import java.sql.SQLException;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Box;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.models.StockGridModel;
import co.syscoop.soberano.util.SpringUtility;

@SuppressWarnings({ "serial", "rawtypes" })
public class StockComposer extends SelectorComposer {
	
	@Wire
	private Combobox cmbWarehouse;
	
	@Wire
	private Combobox cmbMaterial;
	
	@Wire
	private Box boxDetails;
	
	private void processParamSelection() throws SQLException {
		
		StockGridModel stockGridModel = null;
		if (cmbWarehouse.getSelectedItem() != null && cmbMaterial.getSelectedItem() != null) {
			
			//re-render the grid with the selected warehouse and item
			stockGridModel = new StockGridModel(((DomainObject) cmbWarehouse.getSelectedItem().getValue()).getId(),
										((DomainObject) cmbMaterial.getSelectedItem().getValue()).getId(),
										"");			
		}
		else if (cmbWarehouse.getSelectedItem() != null) {
			
			//re-render the grid with the selected warehouse
			stockGridModel = new StockGridModel(((DomainObject) cmbWarehouse.getSelectedItem().getValue()).getId(),
											0,
											cmbMaterial.getText());
		}
		else if (cmbMaterial.getSelectedItem() != null) {
			
			//re-render the grid with the selected material
			stockGridModel = new StockGridModel(0,
											((DomainObject) cmbMaterial.getSelectedItem().getValue()).getId(),
											"");
		}
		else {
			//re-render the grid with the whole stock, filtered by material name
			stockGridModel = new StockGridModel(0, 
											0,
											cmbMaterial.getText());	
		}
		((Grid) boxDetails.getParent().getParent().getParent().query("center").query("window").query("grid")).setModel(stockGridModel);
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
}