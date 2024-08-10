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
import co.syscoop.soberano.exception.CustomerRequiredException;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.exception.ZoneNotCoveredByDeliveryProviderException;
import co.syscoop.soberano.ui.helper.OrderFormHelper;

@SuppressWarnings({ "serial", "rawtypes" })
public class ChangeDeliveryProviderComboboxComposer extends SelectorComposer {
	
	@Wire
	private Combobox cmbDeliveryProvider;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	private void changeDeliveryProvider() throws Exception {
		
		try {
			Integer orderId = ((Intbox) cmbDeliveryProvider.query("#intObjectId")).getValue();
			Integer newDeliveryProviderId = null;
			if (cmbDeliveryProvider.getSelectedItem() != null) {
				newDeliveryProviderId = ((DomainObject) cmbDeliveryProvider.getSelectedItem().getValue()).getId();
			}
			Order order = new Order(orderId);
			int result = order.changeDeliveryProvider(newDeliveryProviderId);				
			if (result == -1) {
				throw new NotEnoughRightsException();
			}
			else if (result == -2) {
				throw new CustomerRequiredException();
			}
			else if (result == -3) {
				throw new ZoneNotCoveredByDeliveryProviderException();
			}
			else {
				Window wndContentPanel = (Window) cmbDeliveryProvider.query("#wndContentPanel");
				OrderFormHelper.updateAmountAndTicket(order, wndContentPanel);
			}
		}
		catch(NotEnoughRightsException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.permissions.NotEnoughRights"), 
					Labels.getLabel("messageBoxTitle.Warning"),
					Messagebox.EXCLAMATION);
		}
		catch(CustomerRequiredException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.validation.selectACustomer"), 
					Labels.getLabel("messageBoxTitle.Warning"),
					Messagebox.EXCLAMATION);
		}
		catch(ZoneNotCoveredByDeliveryProviderException ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.validation.ZoneNotCoveredByDeliveryProvider"), 
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
	
	@Listen("onSelect = combobox#cmbDeliveryProvider")
    public void cmbDeliveryProvider_onSelect() throws Exception {
		changeDeliveryProvider();		
    }
    	
	@Listen("onChange = combobox#cmbDeliveryProvider")
    public void cmbDeliveryProvider_onChange() throws Exception {
		changeDeliveryProvider();		
	}
}