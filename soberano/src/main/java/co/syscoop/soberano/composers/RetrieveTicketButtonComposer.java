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
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.vocabulary.Translator;

@SuppressWarnings({ "serial", "rawtypes" })
public class RetrieveTicketButtonComposer extends SelectorComposer {
	
	@Wire
	private Button btnRetrieve;
	
	@Wire
	private Textbox txtReport;
	
	@Wire
	private Intbox intOrderNumber;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onClick = button#btnRetrieve")
    public void btnRetrieve_onClick() throws SoberanoException {
		
		try{
			if (intOrderNumber.getValue() == null) {
				Messagebox.show(Labels.getLabel("message.validation.specifyAnOrderNumber"), 
						Labels.getLabel("messageBoxTitle.Warning"), 
					0, 
					Messagebox.EXCLAMATION);
			}
			else {
				String ticket = Translator.translate(new Order(intOrderNumber.getValue()).retrieveTicket(new BigDecimal(0), new BigDecimal(0)).getTextToPrint());
				if (!ticket.isEmpty()) {
					
					//set report
					txtReport.setValue(ticket);
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