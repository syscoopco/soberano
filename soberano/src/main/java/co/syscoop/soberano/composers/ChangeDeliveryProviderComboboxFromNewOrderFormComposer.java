package co.syscoop.soberano.composers;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.CustomerRequiredException;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.ZoneNotCoveredByDeliveryProviderException;

@SuppressWarnings({ "serial", "rawtypes" })
public class ChangeDeliveryProviderComboboxFromNewOrderFormComposer extends SelectorComposer {
	
	@Wire
	private Combobox cmbCustomer;
	
	@Wire
	private Combobox cmbDeliveryProvider;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	private void changeDeliveryProvider() throws Exception {
		
		try {
			Integer newDeliveryProviderId = null;
			if (cmbDeliveryProvider.getSelectedItem() != null) {
				newDeliveryProviderId = ((DomainObject) cmbDeliveryProvider.getSelectedItem().getValue()).getId();
				
				if (cmbCustomer.getSelectedItem() == null) {
					throw new CustomerRequiredException();
				}
				
				//check whether provider covers that customer's zone
				if (new Order().checkDeliveryProviderZone(newDeliveryProviderId, 
														((DomainObject)cmbCustomer.getSelectedItem().getValue()).getId()) < 0) {
					cmbDeliveryProvider.setSelectedItem(null);
					cmbDeliveryProvider.setText("");
					throw new ZoneNotCoveredByDeliveryProviderException();
				}
			}
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
		//changeDeliveryProvider();		
    }
    	
	@Listen("onChange = combobox#cmbDeliveryProvider")
    public void cmbDeliveryProvider_onChange() throws Exception {
		changeDeliveryProvider();		
	}
}