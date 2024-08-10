package co.syscoop.soberano.composers;

import java.sql.SQLException;

import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Box;
import org.zkoss.zul.Combobox;
import co.syscoop.soberano.util.SpringUtility;

@SuppressWarnings({ "serial" })
public class ShiftClosuresComposer extends ShiftClosureReportLoadingComposer {
	
	@Wire
	private Combobox cmbCostCenter;
	
	@Wire
	private Box boxDetails;
	
	private void processCostCenterSelection() throws SQLException, Exception {
		
		if (cmbCostCenter.getSelectedItem() != null) {			
			loadReport("costcenter", cmbCostCenter.getText());
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