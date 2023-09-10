package co.syscoop.soberano.composers;

import java.sql.SQLException;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.ui.helper.ProcessFormHelper;
import co.syscoop.soberano.util.ExceptionTreatment;
import co.syscoop.soberano.vocabulary.Labels;

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
	
	@Wire
	private Include incProcessIOs;
	
	@Wire
	private Textbox txtCode;
	
	private void estimateCost() {
		//TODO:
	}
	
	private void updateProcessSpec() throws SQLException {
		
		if (cmbProcess.getSelectedItem() != null) {
			(new ProcessFormHelper()).fillForm(incProcessIOs, ((DomainObject) cmbProcess.getSelectedItem().getValue()).getId(), true, intRuns.getValue());
		}
	}
	
	@Listen("onChange = combobox#cmbProcess")
    public void cmbProcess_onChange() throws SQLException {
		
		updateProcessSpec();
		intRuns.setValue(1);
		estimateCost();
	}
	
	/*
	 * Needed for testing. 
	 * combo_onChange event isn't triggered under testing.
	 */
	@Listen("onClick = combobox#cmbProcess")
    public void cmbProcess_onClick() throws SQLException {
		
		updateProcessSpec();
		intRuns.setValue(1);
		estimateCost();
	}
	
	@Listen("onChange = combobox#cmbCostCenter")
    public void cmbCostCenter_onChange() {
		estimateCost();
	}
	
	/*
	 * Needed for testing. 
	 * combo_onChange event isn't triggered under testing.
	 */
	@Listen("onClick = combobox#cmbCostCenter")
    public void cmbCostCenter_onClick() {
		estimateCost();
	}
	
	@Listen("onChange = intbox#intRuns")
    public void intRuns_onChange() throws SoberanoException {
		
		try {
			updateProcessSpec();
		}
		catch(Exception ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.validation.someFieldsContainWrongValues"), 
					Labels.getLabel("messageBoxTitle.Validation"),
					Messagebox.EXCLAMATION);
		}
	}
}