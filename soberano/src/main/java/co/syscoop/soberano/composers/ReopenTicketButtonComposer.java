package co.syscoop.soberano.composers;

import java.util.HashMap;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.exception.ConfirmationRequiredException;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.exception.ShiftHasBeenClosedException;
import co.syscoop.soberano.renderers.ActionRequested;
import co.syscoop.soberano.util.SpringUtility;

@SuppressWarnings({ "serial", "rawtypes" })
public class ReopenTicketButtonComposer extends SelectorComposer {
	
	protected ActionRequested requestedAction = ActionRequested.NONE;
	
	@Wire
	private Button btnReopen;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@SuppressWarnings("unchecked")
	@Listen("onClick = button#btnReopen")
    public void btnReopen_onClick() throws Exception {
		try {
			if (requestedAction != null && requestedAction.equals(ActionRequested.RECORD)) {
				Integer orderId = ((Intbox) btnReopen.getParent().getParent().getParent().query("#wndContentPanel").query("#intOrderNumber")).getValue();
				if (orderId != null) {			
					int result = new Order(orderId).reopen();
					if (result == -1) {
						throw new NotEnoughRightsException();
					}
					else if (result == -2) {
						throw new ShiftHasBeenClosedException();
					}
					else {
						//there's not ZK web application context under testing
						if (!SpringUtility.underTesting()) {
							
							//re-initialize order's printed allocations store
							((HashMap<Integer, HashMap<Integer, Boolean>>) Executions.
									getCurrent().
									getDesktop().
									getWebApp().
									getAttribute("printed_allocations")).put(orderId, new HashMap<Integer, Boolean>());
						}
						
						Executions.sendRedirect("/order.zul?id=" + orderId);
					}
				}
				else {
					Messagebox.show(Labels.getLabel("message.validation.specifyAnOrderNumber"), 
									Labels.getLabel("messageBoxTitle.Warning"), 
									0, 
									Messagebox.EXCLAMATION);
				}
			}
			else {
				requestedAction = ActionRequested.RECORD;
				btnReopen.setLabel(Labels.getLabel("caption.action.confirm"));
				throw new ConfirmationRequiredException();
			}			
		}
		catch(ConfirmationRequiredException ex) {
			return;
		}
		catch(NotEnoughRightsException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.permissions.NotEnoughRights"), 
					Labels.getLabel("messageBoxTitle.Warning"),
					Messagebox.EXCLAMATION);
		}
		catch(ShiftHasBeenClosedException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.validation.shiftHasBeenClosed"), 
					Labels.getLabel("messageBoxTitle.Warning"),
					Messagebox.EXCLAMATION);
		}
		catch(Exception ex)	{
			ExceptionTreatment.logAndShow(ex, 
					ex.getMessage(), 
					Labels.getLabel("messageBoxTitle.Error"),
					Messagebox.ERROR);
		}	
	}
}