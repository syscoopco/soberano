package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;

import co.syscoop.soberano.domain.tracked.PrinterProfile;
import co.syscoop.soberano.domain.tracked.TrackedObject;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.printjobs.Printer;
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
			String report = ZKUtilitity.getReportFromURLQuery();
			if (report.isEmpty()) {
				report = trackedObject.getReport();
				Printer.print(Translator.translate(report),
								trackedObject, 
								fileToPrintFullPath,
								false);
			}
			else {
				//file to print path is passed in report param
				PrinterProfile printerProfile = new PrinterProfile(trackedObject.getPrinterProfile());
				printerProfile.get();
				Printer.print(null, 
							report, 
							printerProfile.getPrinterName(), 
							trackedObject.getClass().getSimpleName() + "_" + trackedObject.getId());
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
