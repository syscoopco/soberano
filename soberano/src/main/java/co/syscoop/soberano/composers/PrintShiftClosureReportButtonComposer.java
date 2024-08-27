package co.syscoop.soberano.composers;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Intbox;

import co.syscoop.soberano.domain.tracked.ShiftClosure;
import co.syscoop.soberano.domain.untracked.PrintableData;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.printjobs.Printer;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.vocabulary.Translator;

@SuppressWarnings({ "serial", "rawtypes" })

public class PrintShiftClosureReportButtonComposer extends SelectorComposer {
	
	@Wire
	private Button btnPrint;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onClick = button#btnPrint")
    public void btnPrint_onClick() throws Exception {
		
		try{
			Textbox txtShownReport = (Textbox) btnPrint.getParent().getParent().getParent().query("#wndShowingAll").query("#txtShownReport");
			Integer scId = ((Intbox) txtShownReport.query("#intObjectId")).getValue();
			if (txtShownReport.getText().equals("receivables")) {
				PrintableData pd = new ShiftClosure(scId).getReceivablesReportWithPrinterProfile();
				if (!pd.getTextToPrint().isEmpty()) {				
					String fileToPrintFullPath = SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
												"records/closures/" + 
												"CLOSURE_RECEIVABLES_" + scId + ".pdf";
					try {
						Printer.print(Translator.translate(pd.getTextToPrint()), 
									pd.getPrinterProfile(), 
									fileToPrintFullPath, 
									"CLOSURE_RECEIVABLES_" + scId, false);
					}
					catch(Exception ex) {
						ExceptionTreatment.logAndShow(ex, 
							Labels.getLabel("message.error.ConfigurePrinterProfile"), 
							Labels.getLabel("messageBoxTitle.Error"),
							Messagebox.ERROR);
					}
				}
				else {
					throw new NotEnoughRightsException();
				}
			}
			else if (txtShownReport.getText().equals("cashregister")) {
				PrintableData pd = new ShiftClosure(scId).getCashRegisterReportWithPrinterProfile();
				if (!pd.getTextToPrint().isEmpty()) {				
					String fileToPrintFullPath = SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
												"records/closures/" + 
												"CLOSURE_CASH_REGISTER_" + scId + ".pdf";
					try {
						Printer.print(Translator.translate(pd.getTextToPrint()), 
									pd.getPrinterProfile(), 
									fileToPrintFullPath, 
									"CLOSURE_CASH_REGISTER_" + scId, false);
					}
					catch(Exception ex) {
						ExceptionTreatment.logAndShow(ex, 
							Labels.getLabel("message.error.ConfigurePrinterProfile"), 
							Labels.getLabel("messageBoxTitle.Error"),
							Messagebox.ERROR);
					}
				}
				else {
					throw new NotEnoughRightsException();
				}
			}
			else if (txtShownReport.getText().equals("housebill")) {
				PrintableData pd = new ShiftClosure(scId).getHouseBillReportWithPrinterProfile();
				if (!pd.getTextToPrint().isEmpty()) {				
					String fileToPrintFullPath = SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
												"records/closures/" + 
												"CLOSURE_HOUSEBILL_" + scId + ".pdf";
					try {
						Printer.print(Translator.translate(pd.getTextToPrint()), 
									pd.getPrinterProfile(), 
									fileToPrintFullPath, 
									"CLOSURE_HOUSEBILL_" + scId, false);
					}
					catch(Exception ex) {
						ExceptionTreatment.logAndShow(ex, 
							Labels.getLabel("message.error.ConfigurePrinterProfile"), 
							Labels.getLabel("messageBoxTitle.Error"),
							Messagebox.ERROR);
					}
				}
				else {
					throw new NotEnoughRightsException();
				}
			}
			else if (txtShownReport.getText().equals("costcenter")) {
				Combobox cmbCostCenter = (Combobox) btnPrint.query("#cmbCostCenter");
				String costCenterName = "";
				if (cmbCostCenter.getSelectedItem() != null) {
					costCenterName = cmbCostCenter.getText();
				}
				PrintableData pd = new ShiftClosure(scId).getCostCenterReportWithPrinterProfile(costCenterName);
				if (!pd.getTextToPrint().isEmpty()) {				
					String fileToPrintFullPath = SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
												"records/closures/" + 
												"CLOSURE_COSTCENTER_" + scId + ".pdf";
					try {
						Printer.print(Translator.translate(pd.getTextToPrint()), 
									pd.getPrinterProfile(), 
									fileToPrintFullPath, 
									"CLOSURE_COSTCENTER_" + scId, false);
					}
					catch(Exception ex) {
						ExceptionTreatment.logAndShow(ex, 
							Labels.getLabel("message.error.ConfigurePrinterProfile"), 
							Labels.getLabel("messageBoxTitle.Error"),
							Messagebox.ERROR);
					}
				}
				else {
					throw new NotEnoughRightsException();
				}
			}
			else if (txtShownReport.getText().equals("generalfull")) {
				PrintableData pd = new ShiftClosure(scId).getGeneralFullReportWithPrinterProfile();
				if (!pd.getTextToPrint().isEmpty()) {				
					String fileToPrintFullPath = SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
												"records/closures/" + 
												"CLOSURE_GENERAL_FULL_" + scId + ".pdf";
					try {
						Printer.print(Translator.translate(pd.getTextToPrint()), 
									pd.getPrinterProfile(), 
									fileToPrintFullPath, 
									"CLOSURE_GENERAL_FULL_" + scId, false);
					}
					catch(Exception ex) {
						ExceptionTreatment.logAndShow(ex, 
							Labels.getLabel("message.error.ConfigurePrinterProfile"), 
							Labels.getLabel("messageBoxTitle.Error"),
							Messagebox.ERROR);
					}
				}
				else {
					throw new NotEnoughRightsException();
				}
			}
			else {//print general report
				PrintableData pd = new ShiftClosure(scId).getReportWithPrinterProfile();
				if (!pd.getTextToPrint().isEmpty()) {				
					String fileToPrintFullPath = SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
												"records/closures/" + 
												"CLOSURE_GENERAL_" + scId + ".pdf";
					try {
						Printer.print(Translator.translate(pd.getTextToPrint()), 
									pd.getPrinterProfile(), 
									fileToPrintFullPath, 
									"CLOSURE_GENERAL_" + scId, false);
					}
					catch(Exception ex) {
						ExceptionTreatment.logAndShow(ex, 
							Labels.getLabel("message.error.ConfigurePrinterProfile"), 
							Labels.getLabel("messageBoxTitle.Error"),
							Messagebox.ERROR);
					}
				}
				else {
					throw new NotEnoughRightsException();
				}
			}
		}
		catch(NotEnoughRightsException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.permissions.NotEnoughRights"), 
					Labels.getLabel("messageBoxTitle.Warning"),
					Messagebox.EXCLAMATION);
		}
		catch(Exception ex) {
			ExceptionTreatment.logAndShow(ex, 
						ex.getMessage(), 
						Labels.getLabel("messageBoxTitle.Error"),
						Messagebox.ERROR);
		}
	}
}