package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.ui.helper.ShiftClosureFormHelper;

@SuppressWarnings({ "serial", "rawtypes" })
public class ShiftClosureReportLoadingComposer extends SelectorComposer {
	
	@Wire
	protected Intbox intObjectId;
	
	@Wire
	protected Textbox txtReport;
	
	@Wire
	protected Button btnGeneral;
	
	@Wire
	protected Button btnGeneralFull; 
	
	@Wire
	protected Button btnHouseBill;
	
	@Wire
	protected Button btnCashRegister; 
	
	@Wire
	protected Button btnReceivables; 
	
	@Wire
	protected Button btnSalesByPrice;
	
	@Wire
	protected Button btnNotes; 
		
	@Wire
	protected Combobox cmbCostCenter;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	protected void loadReport(Textbox txtShownReport, String reportType, String param) throws SoberanoException {
		
		ShiftClosureFormHelper.loadReport(txtShownReport, (Textbox) txtShownReport.query("#txtReport"), reportType, param, ((Intbox) txtShownReport.query("#intObjectId")).getValue());
    }
	
	protected void updateComponentStyles(String clickedButtonId) {
		
		switch(clickedButtonId) {
		case "btnGeneral":
			btnGeneral.setClass("ReportButtonPushed");
			btnGeneralFull.setClass("DecisionButton");
			btnHouseBill.setClass("DecisionButton");
			btnCashRegister.setClass("DecisionButton");
			btnReceivables.setClass("DecisionButton");
			btnSalesByPrice.setClass("DecisionButton");
			btnNotes.setClass("DecisionButton");
			cmbCostCenter.setClass("DecisionButton");
			cmbCostCenter.setSelectedItem(null);
			break;
		case "btnGeneralFull":
			btnGeneral.setClass("DecisionButton");
			btnGeneralFull.setClass("ReportButtonPushed");
			btnHouseBill.setClass("DecisionButton");
			btnCashRegister.setClass("DecisionButton");
			btnReceivables.setClass("DecisionButton");
			btnSalesByPrice.setClass("DecisionButton");
			btnNotes.setClass("DecisionButton");
			cmbCostCenter.setClass("DecisionButton");
			cmbCostCenter.setSelectedItem(null);
			break;
		case "btnHouseBill":
			btnGeneral.setClass("DecisionButton");
			btnGeneralFull.setClass("DecisionButton");
			btnHouseBill.setClass("ReportButtonPushed");
			btnCashRegister.setClass("DecisionButton");
			btnReceivables.setClass("DecisionButton");
			btnSalesByPrice.setClass("DecisionButton");
			btnNotes.setClass("DecisionButton");
			cmbCostCenter.setClass("DecisionButton");
			cmbCostCenter.setSelectedItem(null);
			break;
		case "btnCashRegister":
			btnGeneral.setClass("DecisionButton");
			btnGeneralFull.setClass("DecisionButton");
			btnHouseBill.setClass("DecisionButton");
			btnCashRegister.setClass("ReportButtonPushed");
			btnReceivables.setClass("DecisionButton");
			btnSalesByPrice.setClass("DecisionButton");
			btnNotes.setClass("DecisionButton");
			cmbCostCenter.setClass("DecisionButton");
			cmbCostCenter.setSelectedItem(null);
			break;	
		case "btnReceivables":
			btnGeneral.setClass("DecisionButton");
			btnGeneralFull.setClass("DecisionButton");
			btnHouseBill.setClass("DecisionButton");
			btnCashRegister.setClass("DecisionButton");
			btnReceivables.setClass("ReportButtonPushed");
			btnSalesByPrice.setClass("DecisionButton");
			btnNotes.setClass("DecisionButton");
			cmbCostCenter.setClass("DecisionButton");
			cmbCostCenter.setSelectedItem(null);
			break;
		case "cmbCostCenter":
			btnGeneral.setClass("DecisionButton");
			btnGeneralFull.setClass("DecisionButton");
			btnHouseBill.setClass("DecisionButton");
			btnCashRegister.setClass("DecisionButton");
			btnReceivables.setClass("DecisionButton");
			btnSalesByPrice.setClass("DecisionButton");
			btnNotes.setClass("DecisionButton");
			cmbCostCenter.setClass("ReportButtonPushed");
			break;
		case "btnSalesByPrice":
			btnGeneral.setClass("DecisionButton");
			btnGeneralFull.setClass("DecisionButton");
			btnHouseBill.setClass("DecisionButton");
			btnCashRegister.setClass("DecisionButton");
			btnReceivables.setClass("DecisionButton");
			btnSalesByPrice.setClass("ReportButtonPushed");
			btnNotes.setClass("DecisionButton");
			cmbCostCenter.setClass("DecisionButton");
			cmbCostCenter.setSelectedItem(null);
			break;
		case "btnNotes":
			btnGeneral.setClass("DecisionButton");
			btnGeneralFull.setClass("DecisionButton");
			btnHouseBill.setClass("DecisionButton");
			btnCashRegister.setClass("DecisionButton");
			btnReceivables.setClass("DecisionButton");
			btnSalesByPrice.setClass("DecisionButton");
			btnNotes.setClass("ReportButtonPushed");
			cmbCostCenter.setClass("DecisionButton");
			cmbCostCenter.setSelectedItem(null);
			break;
		}
	}
}