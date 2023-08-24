package co.syscoop.soberano.composers;

import java.sql.SQLException;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;

@SuppressWarnings({ "serial", "rawtypes" })
public class ProcessRunsComposer extends SelectorComposer {
	
	@Wire
	private Combobox cmbProcess;
	
	@Wire
	private Combobox cmbCostCenter;
	
	@Wire
	private Intbox intRuns;
	
	@Wire
	private Textbox txtQuantityExpression;
	
	private void estimateCost() {		
	}
	
	@Listen("onChange = combobox#cmbProcess")
    public void cmbProcess_onChange() throws SQLException {
		estimateCost();
	}
	
	/*
	 * Needed for testing. 
	 * combo_onChange event isn't triggered under testing.
	 */
	@Listen("onClick = combobox#cmbProcess")
    public void cmbProcess_onClick() throws SQLException {
		estimateCost();
	}
	
	@Listen("onChange = combobox#cmbCostCenter")
    public void cmbCostCenter_onChange() throws SQLException {
		estimateCost();
	}
	
	/*
	 * Needed for testing. 
	 * combo_onChange event isn't triggered under testing.
	 */
	@Listen("onClick = combobox#cmbCostCenter")
    public void cmbCostCenter_onClick() throws SQLException {
		estimateCost();
	}
	
	@Listen("onChange = intbox#intRuns")
    public void intRuns_onChange() {
		estimateCost();
	}
}