package co.syscoop.soberano.composers;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.exception.CustomerRequiredException;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.exception.ZoneNotCoveredByDeliveryProviderException;

@SuppressWarnings({ "serial", "rawtypes" })
public class ChangeDeliveryAddressButtonComposer extends SelectorComposer {
	
	@Wire
	private Button btnApply;
	
	@Wire
	private Button btnCancel;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onClick = button#btnCancel")
    public void btnCancel_onClick() throws Throwable {
		
		((Popup) btnCancel.getParent().getParent().getParent().query("#wndContentPanel").query("#pp")).close();
	}
	
	@Listen("onClick = button#btnApply")
    public void btnApply_onClick() throws Throwable {
		
		try {
			Integer orderId = ((Intbox) btnApply.getParent().getParent().getParent().query("#wndContentPanel").query("#intObjectId")).getValue();
			Popup pp = (Popup) btnCancel.getParent().getParent().getParent().query("#wndContentPanel").query("#pp");
			
			Order order = new Order();
			int result = order.changeDeliveryAddress(orderId,
													((Textbox) pp.query("include").query("#txtEmailAddress")).getText(),
													((Textbox) pp.query("include").query("#txtPhoneNumber")).getText(),
													((Textbox) pp.query("include").query("#txtAddress")).getText(),
													((Combobox) pp.query("include").query("#cmbPostalCode")).getText(),
													((Textbox) pp.query("include").query("#txtTown")).getText(),
													((Combobox) pp.query("include").query("#cmbMunicipality")).getSelectedItem().getValue(),
													((Textbox) pp.query("include").query("#txtCity")).getText(),
													((Doublebox) pp.query("include").query("#dblLatitude")).getValue(),
													((Doublebox) pp.query("include").query("#dblLongitude")).getValue());				
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
				Executions.sendRedirect("/order.zul?id=" + orderId);
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
}