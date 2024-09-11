package co.syscoop.soberano.composers;

import java.util.Base64;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Vbox;

import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.domain.tracked.PrinterProfile;
import co.syscoop.soberano.domain.tracked.TrackedObject;
import co.syscoop.soberano.domain.untracked.PrintableData;
import co.syscoop.soberano.enums.Stage;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.printjobs.Printer;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.util.ui.ZKUtilitity;
import co.syscoop.soberano.vocabulary.Labels;
import co.syscoop.soberano.vocabulary.Translator;

@SuppressWarnings({ "rawtypes", "serial" })
public class PrintButtonComposer extends SelectorComposer {

	protected TrackedObject trackedObject = null;
	protected String fileToPrintFullPath = "";
	
	@Wire
	protected Button btnPrint;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onClick = button#btnPrint")
    public void btnPrint_onClick() throws Throwable {
		
		try {
			Vbox boxDetails = (Vbox) btnPrint.getParent().getParent().getParent().query("#wndContentPanel").query("#boxDetails");
			Integer orderId = ((Intbox) boxDetails.query("#intObjectId")).getValue();
			trackedObject.setId(orderId);
			trackedObject.get();
			
			if (((Order) trackedObject).getStageId() == Stage.ONGOING) {
				PrintableData pd = new Order(orderId).retrieveTicket();
				if (!pd.getTextToPrint().isEmpty()) {				
					String fileToPrintFullPath = SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
													"records/tickets/" + 
													"TICKET_" + orderId + ".pdf";
					try {
						Printer.print(Translator.translate(pd.getTextToPrint()), pd.getPrinterProfile(), fileToPrintFullPath, "TICKET_" + orderId, false);
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
			else {
				fileToPrintFullPath = SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
						"records/orders/" + 
						"ORDER_" + (trackedObject.getId() == 0 ? trackedObject.getStringId() : trackedObject.getId() + ".pdf");
				
				String report = ZKUtilitity.getReportFromURLQuery();
				Integer orderIdFromURL = ZKUtilitity.getObjectIdFromURLQuery("id");
				if (!report.isEmpty() && orderIdFromURL.equals(trackedObject.getId())) {
					
					//file to print path is passed in report param
					PrinterProfile printerProfile = new PrinterProfile(trackedObject.getPrinterProfile());
					printerProfile.get();
					Printer.print(null, 
								new String(Base64.getDecoder().decode(report)), 
								printerProfile.getPrinterName(), 
								trackedObject.getClass().getSimpleName() + "_" + trackedObject.getId());
				}
				else {
					report = trackedObject.getReport();
					Printer.print(Translator.translate(report),
									trackedObject, 
									fileToPrintFullPath,
									false);
				}
			}			
		}
		catch(NotEnoughRightsException ex) {
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.permissions.NotEnoughRights"), 
										Labels.getLabel("messageBoxTitle.Warning"),
										Messagebox.EXCLAMATION);
		}
		catch(Exception ex)	{
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.error.ConfigurePrinterProfile"), 
										Labels.getLabel("messageBoxTitle.Error"),
										Messagebox.ERROR);
		}
	}
}
