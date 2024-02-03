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

@SuppressWarnings({ "serial", "rawtypes" })
public class SPIComposer extends SelectorComposer {
	
	@Wire
	private Combobox cmbWarehouse;
	
	@Wire
	private Box boxDetails;
	
	private void processWarehouseSelection() throws SQLException {
		
		SPIGridModel spiGridModel = null;
		if (cmbWarehouse.getSelectedItem() != null) {
			
			//re-render the grid with the selected warehouse spi
			spiGridModel = new SPIGridModel(((DomainObject) cmbWarehouse.getSelectedItem().getValue()).getId());			
		}
		else {
			//re-render the grid with the whole spi
			spiGridModel = new SPIGridModel();	
		}
		((Grid) boxDetails.getParent().getParent().getParent().query("center").query("window").query("grid")).setModel(spiGridModel);
	}
	
	@Listen("onChange = combobox#cmbWarehouse")
    public void cmbWarehouse_onChange() throws SQLException {
		processWarehouseSelection();
	}
	
	/*
	 * Needed for testing. 
	 * combo_onChange event isn't triggered under testing.
	 */
	@Listen("onClick = combobox#cmbWarehouse")
    public void cmbWarehouse_onClick() throws SQLException {
		if (SpringUtility.underTesting()) processWarehouseSelection();
	}
}