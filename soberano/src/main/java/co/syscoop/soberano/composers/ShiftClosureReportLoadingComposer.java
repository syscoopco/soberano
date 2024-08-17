package co.syscoop.soberano.composers;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.domain.tracked.ShiftClosure;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.util.ui.ZKUtilitity;
import co.syscoop.soberano.vocabulary.Translator;

@SuppressWarnings({ "serial", "rawtypes" })
public class ShiftClosureReportLoadingComposer extends SelectorComposer {
	
	@Wire
	protected Textbox txtShownReport; 
	
	@Wire
	protected Textbox txtReport;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	protected void loadReport(String reportType, String param) throws SoberanoException {
		
		try{
			txtShownReport.setValue(reportType);
			
			Integer scId = 0;
			try {
				scId = ZKUtilitity.getObjectIdFromURLQuery("id");
			}
			catch(Exception ex) {}
			
			String scReport = "";
			if (reportType.equals("receivables")) {					
				scReport = Translator.translate(new ShiftClosure(scId).getReceivablesReport());
			}
			else if (reportType.equals("cashregister")) {					
				scReport = Translator.translate(new ShiftClosure(scId).getCashRegisterReport());
			}
			else if (reportType.equals("housebill")) {
				scReport = Translator.translate(new ShiftClosure(scId).getHouseBillReport());
			}
			else if (reportType.equals("costcenter")) {
				scReport = Translator.translate(new ShiftClosure(scId).getCostCenterReport(param));
			}
			else if (reportType.equals("generalfull")) {
				scReport = Translator.translate(new ShiftClosure(scId).getGeneralFullReport());
			}
			else {
				scReport = Translator.translate(new ShiftClosure(scId).getReport());
			}
			
			if (!scReport.isEmpty()) {
				
				//set report
				txtReport.setValue(scReport);
			}
			else {
				throw new NotEnoughRightsException();
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