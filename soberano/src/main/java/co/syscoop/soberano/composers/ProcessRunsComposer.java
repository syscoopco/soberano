package co.syscoop.soberano.composers;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.domain.tracked.ProcessRun;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.ui.helper.ProcessFormHelper;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.vocabulary.Labels;

@SuppressWarnings({ "serial", "rawtypes" })
public class ProcessRunsComposer extends SelectorComposer {
	
	@Wire
	private Combobox cmbProcess;
	
	@Wire
	private Combobox cmbCostCenter;
	
	@Wire
	private Decimalbox decRuns;
	
	@Wire
	private Textbox txtQuantityExpression;
	
	@Wire
	private Include incProcessIOs;
	
	@Wire
	private Textbox txtCode;
	
	private void estimateCost() throws SQLException, Exception {
		
		if (cmbProcess.getSelectedItem() != null && cmbCostCenter.getSelectedItem() != null) {
			ProcessRun processRun = new ProcessRun();
			BigDecimal estimateCost = processRun.estimateCost(((DomainObject) cmbProcess.getSelectedItem().getValue()).getId(), 
														((DomainObject) cmbCostCenter.getSelectedItem().getValue()).getId());
			((Decimalbox) cmbProcess.query("#wndContentPanel").getParent().query("#incSouth").query("#hboxDecisionButtons").query("#decEstimatedCost")).setValue(estimateCost);
		}
	}
	
	private void updateProcessSpec() throws SQLException {
		
		if (cmbProcess.getSelectedItem() != null) {
			(new ProcessFormHelper()).fillForm(incProcessIOs, ((DomainObject) cmbProcess.getSelectedItem().getValue()).getId(), true, decRuns.getValue());
		}
	}
	
	@Listen("onChange = combobox#cmbProcess")
    public void cmbProcess_onChange() throws Exception {
		
		try {
			updateProcessSpec();
			decRuns.setValue(new BigDecimal(1.0));
			estimateCost();
		} catch (SQLException e) {
			ExceptionTreatment.logAndShow(e, 
					Labels.getLabel("message.validation.someFieldsContainWrongValues"), 
					Labels.getLabel("messageBoxTitle.Validation"),
					Messagebox.EXCLAMATION);
		}
		
	}
	
	/*
	 * Needed for testing. 
	 * combo_onChange event isn't triggered under testing.
	 */
	@Listen("onClick = combobox#cmbProcess")
    public void cmbProcess_onClick() throws SoberanoException {
		
		if (SpringUtility.underTesting()) {			
			try {
				updateProcessSpec();
				decRuns.setValue(new BigDecimal(1.0));
				estimateCost();
			} catch (SQLException e) {
				ExceptionTreatment.logAndShow(e, 
						Labels.getLabel("message.validation.someFieldsContainWrongValues"), 
						Labels.getLabel("messageBoxTitle.Validation"),
						Messagebox.EXCLAMATION);
			} catch (Exception e) {
				ExceptionTreatment.logAndShow(e, 
						Labels.getLabel("message.validation.someFieldsContainWrongValues"), 
						Labels.getLabel("messageBoxTitle.Validation"),
						Messagebox.EXCLAMATION);
			}			
		}
	}
	
	@Listen("onChange = combobox#cmbCostCenter")
    public void cmbCostCenter_onChange() throws SoberanoException {
		try {
			estimateCost();
		} catch (Exception e) {
			ExceptionTreatment.logAndShow(e, 
					Labels.getLabel("message.validation.someFieldsContainWrongValues"), 
					Labels.getLabel("messageBoxTitle.Validation"),
					Messagebox.EXCLAMATION);
		}
	}
	
	/*
	 * Needed for testing. 
	 * combo_onChange event isn't triggered under testing.
	 */
	@Listen("onClick = combobox#cmbCostCenter")
    public void cmbCostCenter_onClick() throws SoberanoException {
		
		if (SpringUtility.underTesting()) {
			try {
				estimateCost();
			} catch (SQLException e) {
				ExceptionTreatment.logAndShow(e, 
						Labels.getLabel("message.validation.someFieldsContainWrongValues"), 
						Labels.getLabel("messageBoxTitle.Validation"),
						Messagebox.EXCLAMATION);
			} catch (Exception e) {
				ExceptionTreatment.logAndShow(e, 
						Labels.getLabel("message.validation.someFieldsContainWrongValues"), 
						Labels.getLabel("messageBoxTitle.Validation"),
						Messagebox.EXCLAMATION);
			}
		}
	}
	
	@Listen("onChange = decimalbox#decRuns")
    public void decRuns_onChange() throws SoberanoException {
		
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