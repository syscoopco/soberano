package co.syscoop.soberano.composers;

import java.sql.SQLException;

import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.syscoop.soberano.util.Mobile;
import co.syscoop.soberano.util.SpringUtility;

@SuppressWarnings({ "serial" })
public class ShiftClosuresComposer extends ShiftClosureReportLoadingComposer {
	
	private Window wndShowingAll = null;
	
	private void setWndShowingAll() {
		
		if (Mobile.isMobile()) {
			wndShowingAll = (Window) btnGeneral.getParent().getParent().getParent().getParent().getParent().getParent().getParent().query("#wndShowingAll");
		}
		else {
			wndShowingAll = (Window) btnGeneral.getParent().getParent().getParent().getParent().query("#wndShowingAll");
		}
	}
	
	private void processCostCenterSelection() throws SQLException, Exception {
		
		setWndShowingAll();
		
		if (cmbCostCenter.getSelectedItem() != null) {			
			loadReport((Textbox) wndShowingAll.query("#boxDetails").query("#txtShownReport"),
					"costcenter", 
					cmbCostCenter.getText());
			updateComponentStyles("cmbCostCenter");
		}
	}
	
	private void processWarehouseSelection() throws SQLException, Exception {
		
		setWndShowingAll();
		
		if (cmbWarehouse.getSelectedItem() != null) {			
			loadReport((Textbox) wndShowingAll.query("#boxDetails").query("#txtShownReport"),
					"spi", 
					cmbWarehouse.getText());
			updateComponentStyles("cmbWarehouse");
		}
	}
	
	@Listen("onChange = combobox#cmbCostCenter")
    public void cmbCostCenter_onChange() throws Exception {
		processCostCenterSelection();
	}
	
	@Listen("onChange = combobox#cmbWarehouse")
    public void cmbWarehouse_onChange() throws Exception {
		processWarehouseSelection();
	}
	
	/*
	 * Needed for testing. 
	 * combo_onChange event isn't triggered under testing.
	 */
	@Listen("onClick = combobox#cmbCostCenter")
    public void cmbCostCenter_onClick() throws Exception {
		if (SpringUtility.underTesting()) processCostCenterSelection();
	}
	@Listen("onClick = combobox#cmbWarehouse")
    public void cmbWarehouse_onClick() throws Exception {
		if (SpringUtility.underTesting()) processWarehouseSelection();
	}
}