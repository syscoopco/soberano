package co.syscoop.soberano.composers;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;

import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;

@SuppressWarnings({ "serial", "rawtypes" })
public class ChangeCustomerComboboxComposer extends SelectorComposer {
	
	@Wire
	private Combobox cmbCustomer;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	private void changeCustomer() throws Exception {
		
		try {
			Integer orderId = ((Intbox) cmbCustomer.query("#intObjectId")).getValue();
			Integer newCustomerId = null;
			if (cmbCustomer.getSelectedItem() != null) {
				newCustomerId = (Integer) ((DomainObject) cmbCustomer.getSelectedItem().getValue()).getId();
			}
			Order order = new Order(orderId);
			int result = order.changeCustomer(newCustomerId);				
			if (result == -1) {
				throw new NotEnoughRightsException();
			}
			else {
				Executions.sendRedirect("/order.zul?id=" + orderId);
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
					ex.getMessage(), 
					Labels.getLabel("messageBoxTitle.Error"),
					Messagebox.ERROR);
		}
	}
	
	@Listen("onSelect = combobox#cmbCustomer")
    public void cmbCustomer_onSelect() throws Exception {
		changeCustomer();		
    }
    	
	@Listen("onChange = combobox#cmbCustomer")
    public void cmbCustomer_onChange() throws Exception {
		changeCustomer();		
	}
}