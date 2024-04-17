package co.syscoop.soberano.composers;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;

import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.ui.helper.DeliveryProviderFormHelper;

@SuppressWarnings({ "serial", "rawtypes" })
public class AddDeliveryFeeButtonComposer extends SelectorComposer {
	
	@Wire
	private Treechildren tchdnFees;
	
	@Wire
	private Combobox cmbCountry;
	
	@Wire
	private Textbox txtPostalCode;
	
	@Wire
	private Decimalbox decFee;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
        super.doAfterCompose(comp);
    }
	
	@Listen("onClick = button#btnAddFee")
    public void btnAddFee_onClick() {
		
		try{
			String countryCode = ((DomainObject) cmbCountry.getSelectedItem().getValue()).getStringId();
			
			//if the tree has no items,
			if (tchdnFees.getChildren().size() == 0) {
				DeliveryProviderFormHelper.addDeliveryFee(countryCode, 
															txtPostalCode.getText(),
															decFee.getValue(),
															tchdnFees);
			}
			else {
				int i = 0;
				for (Component item : tchdnFees.getChildren()) {
					
					//if that item was already added,
					if ((((Treeitem) item).getLabel()).equals(countryCode + " : " + txtPostalCode.getText())) {
						break;
					}
					else i++;
				}
				
				//if that item wasn't already added,
				if (i == tchdnFees.getChildren().size()) {
					DeliveryProviderFormHelper.addDeliveryFee(countryCode, 
															txtPostalCode.getText(),
															decFee.getValue(),
															tchdnFees);
				}
			}
		}
		catch(Exception ex) {
			Messagebox.show(Labels.getLabel("message.validation.selectAnItemFromTheList"), 
						  					Labels.getLabel("messageBoxTitle.Warning"), 
											0, 
											Messagebox.EXCLAMATION);
		}
    }
}