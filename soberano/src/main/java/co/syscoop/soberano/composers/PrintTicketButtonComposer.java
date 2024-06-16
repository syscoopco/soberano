package co.syscoop.soberano.composers;

import java.math.BigDecimal;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.domain.untracked.PrintableData;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.printjobs.Printer;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.vocabulary.Translator;

@SuppressWarnings({ "serial", "rawtypes" })
public class PrintTicketButtonComposer extends SelectorComposer {
	
	@Wire
	private Button btnPrint;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onClick = button#btnPrint")
    public void btnPrint_onClick() throws Exception {
		
		try{
			Integer orderId = ((Intbox) btnPrint.getParent().getParent().getParent().query("#wndContentPanel").query("#intOrderNumber")).getValue();
			PrintableData pd = new Order(orderId).retrieveTicket(new BigDecimal(0), new BigDecimal(0));
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