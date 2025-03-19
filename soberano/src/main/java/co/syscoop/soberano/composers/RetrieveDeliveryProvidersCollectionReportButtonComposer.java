package co.syscoop.soberano.composers;

import java.util.ArrayList;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.domain.tracked.Report;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.vocabulary.Translator;

@SuppressWarnings({ "serial", "rawtypes" })
public class RetrieveDeliveryProvidersCollectionReportButtonComposer extends SelectorComposer {
	
	@Wire
	private Button btnRetrieve;
	
	@Wire
	private Button btnPrint;
	
	@Wire
	private Textbox txtReport;
	
	@Wire
	private Datebox dateFrom;
	
	@Wire
	private Datebox dateUntil;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onClick = button#btnRetrieve")
    public void btnRetrieve_onClick() throws SoberanoException {
		
		try{
			ArrayList<String> reportQueryParamNames = new ArrayList<String>();
			ArrayList<Object> reportQueryParamValues = new ArrayList<Object>();
			
			reportQueryParamNames.add("fromDate");
			reportQueryParamValues.add(dateFrom.getValue());
			
			reportQueryParamNames.add("untilDate");
			reportQueryParamValues.add(dateUntil.getValue());
			
			Report report = new Report(reportQueryParamNames, reportQueryParamValues, "fn_Report_deliveryProvidersCollection");
			
			String reportContent = Translator.translate(report.getReportWithPrinterProfile().getTextToPrint());
			if (!reportContent.isEmpty()) {
				
				//set report
				txtReport.setValue(reportContent);
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