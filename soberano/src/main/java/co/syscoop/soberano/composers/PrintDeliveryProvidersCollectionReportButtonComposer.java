package co.syscoop.soberano.composers;

import java.util.ArrayList;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Datebox;
import co.syscoop.soberano.domain.tracked.Report;
import co.syscoop.soberano.domain.untracked.PrintableData;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.printjobs.Printer;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.vocabulary.Translator;

@SuppressWarnings({ "serial", "rawtypes" })

public class PrintDeliveryProvidersCollectionReportButtonComposer extends SelectorComposer {
	
	@Wire
	private Button btnPrint;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onClick = button#btnPrint")
    public void btnPrint_onClick() throws Exception {
		
		try{
			Textbox txtReport = (Textbox) btnPrint.getParent().getParent().getParent().query("#wndContentPanel").query("#txtReport");
			Datebox dateFrom = (Datebox) txtReport.query("#dateFrom");
			Datebox dateUntil = (Datebox) txtReport.query("#dateUntil");
			
			ArrayList<String> reportQueryParamNames = new ArrayList<String>();
			ArrayList<Object> reportQueryParamValues = new ArrayList<Object>();
			
			reportQueryParamNames.add("fromDate");
			reportQueryParamValues.add(dateFrom.getValue());
			
			reportQueryParamNames.add("untilDate");
			reportQueryParamValues.add(dateUntil.getValue());
			
			Report report = new Report(reportQueryParamNames, reportQueryParamValues, "fn_Report_deliveryProvidersCollection");
			PrintableData pd = report.getReportWithPrinterProfile();
			
			if (!pd.getTextToPrint().isEmpty()) {				
				String fileToPrintFullPath = SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
											"records/reports/" + 
											"REPORT_deliveryProvidersCollection" + ".pdf";
				try {
					Printer.print(Translator.translate(pd.getTextToPrint()), 
								pd.getPrinterProfile(), 
								fileToPrintFullPath, 
								"REPORT_deliveryProvidersCollection",
								false,
								null);
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