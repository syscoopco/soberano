package co.syscoop.soberano.composers;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Box;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;

import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.models.SPIGridModel;
import co.syscoop.soberano.util.SpringUtility;

@SuppressWarnings({ "serial", "rawtypes" })
public class SPIComposer extends SelectorComposer {
	
	@Wire
	private Combobox cmbWarehouse;
	
	@Wire
	private Combobox cmbMaterial;
	
	@Wire
	private Datebox dateShift;
	
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
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	
	private void processParamSelection() throws SQLException {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String shiftDateStr = "";
		try {
			shiftDateStr = dateFormat.format(dateShift.getValue());
			if (shiftDateStr.equals(dateFormat.format(new Date()))) 
				dateShift.setValue(null);
		} 
		catch(Exception ex) {};
		
		SPIGridModel spiGridModel = null;
		if (cmbWarehouse.getSelectedItem() != null && cmbMaterial.getSelectedItem() != null) {
			
			//re-render the grid with the selected warehouse and item
			spiGridModel = new SPIGridModel(shiftDateStr, 
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
			spiGridModel = new SPIGridModel(dateShift.getText(), 
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
			spiGridModel = new SPIGridModel(dateShift.getText(), 
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
			spiGridModel = new SPIGridModel(dateShift.getText(), 
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
	
	@Listen("onChange = datebox#dateShift")
    public void dateShift_onChange() throws WrongValueException, Exception {
		processParamSelection();
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