package co.syscoop.soberano.composers;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.ui.helper.OrderFormHelper;
import co.syscoop.soberano.view.viewmodel.DeliveryProviderSelectionViewModel;

@SuppressWarnings({ "serial", "rawtypes" })
public class ChangeCustomerComboboxComposer extends SelectorComposer {
	
	@Wire
	private Combobox cmbCustomer;
	
	@Wire
	private Combobox cmbDeliveryProvider;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@SuppressWarnings("null")
	private void changeCustomer() throws Exception {
		
		try {
			Integer orderId = ((Intbox) cmbCustomer.query("#intObjectId")).getValue();
			Integer newCustomerId = null;
			if (cmbCustomer.getSelectedItem() != null) {
				newCustomerId = ((DomainObject) cmbCustomer.getSelectedItem().getValue()).getId();
			}
			Order order = new Order(orderId);
			int result = order.changeCustomer(newCustomerId);				
			if (result == -1) {
				throw new NotEnoughRightsException();
			}
			else {
				cmbDeliveryProvider.setSelectedItem(null);
				DeliveryProviderSelectionViewModel dpSelectionViewModel = new DeliveryProviderSelectionViewModel();
				cmbDeliveryProvider.setModel(dpSelectionViewModel.getModel());				
				Window wndContentPanel = (Window) cmbCustomer.query("#wndContentPanel");
				OrderFormHelper.updateAmountAndTicket(order, wndContentPanel);
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