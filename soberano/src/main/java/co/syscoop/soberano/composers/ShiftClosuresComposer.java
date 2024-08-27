package co.syscoop.soberano.composers;

import java.sql.SQLException;

import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.util.SpringUtility;

@SuppressWarnings({ "serial" })
public class ShiftClosuresComposer extends ShiftClosureReportLoadingComposer {
	
	private void processCostCenterSelection() throws SQLException, Exception {
		
		if (cmbCostCenter.getSelectedItem() != null) {			
			loadReport((Textbox) cmbCostCenter.getParent().getParent().getParent().getParent().getParent().query("#wndShowingAll").query("#boxDetails").query("#txtShownReport"),
					"costcenter", 
					cmbCostCenter.getText());
			updateComponentStyles("cmbCostCenter");
		}
	}
	
	@Listen("onChange = combobox#cmbCostCenter")
    public void cmbCostCenter_onChange() throws Exception {
		processCostCenterSelection();
	}
	
	/*
	 * Needed for testing. 
	 * combo_onChange event isn't triggered under testing.
	 */
	@Listen("onClick = combobox#cmbCostCenter")
    public void cmbCostCenter_onClick() throws Exception {
		if (SpringUtility.underTesting()) processCostCenterSelection();
	}
}