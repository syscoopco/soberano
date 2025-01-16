package co.syscoop.soberano.composers;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.view.viewmodel.DeliveryProviderSelectionViewModel;

@SuppressWarnings({ "serial", "rawtypes" })
public class ChangeCustomerComboboxFromNewOrderFormComposer extends SelectorComposer {
	
	@Wire
	private Combobox cmbCustomer;
	
	@Wire
	private Combobox cmbDeliveryProvider;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	private void changeCustomer() throws Exception {
		
		try {
			if (cmbCustomer.getSelectedItem() != null) {
				cmbDeliveryProvider.setSelectedItem(null);
				DeliveryProviderSelectionViewModel dpSelectionViewModel = new DeliveryProviderSelectionViewModel();
				cmbDeliveryProvider.setModel(dpSelectionViewModel.getModel());
			}
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
		//changeCustomer();		
    }
    	
	@Listen("onChange = combobox#cmbCustomer")
    public void cmbCustomer_onChange() throws Exception {
		changeCustomer();		
	}
}