package co.syscoop.soberano.composers;

import java.math.BigDecimal;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.ui.helper.OrderFormHelper;
import co.syscoop.soberano.util.Utils;

@SuppressWarnings({ "serial", "rawtypes" })
public class ChangeSalesAgentComboboxComposer extends SelectorComposer {
	
	@Wire
	private Combobox cmbSalesAgent;
	
	@Wire
	private Textbox txtSalesAgentCommissionExpression;
	
	@Wire
	private Decimalbox decSalesAgentCommission;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	private void changeSalesAgent() throws Exception {
		
		try {
			Integer orderId = ((Intbox) cmbSalesAgent.query("#intObjectId")).getValue();
			Integer newProviderId = null;
			if (cmbSalesAgent.getSelectedItem() != null) {
				newProviderId = ((DomainObject) cmbSalesAgent.getSelectedItem().getValue()).getId();
			}
			Order order = new Order(orderId);
			int result = order.changeSalesAgent(newProviderId);			
			if (result == -1) {
				throw new NotEnoughRightsException();
			}
			else {
				Window wndContentPanel = (Window) cmbSalesAgent.query("#wndContentPanel");
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
	
	@Listen("onChange = textbox#txtSalesAgentCommissionExpression")
    public void txtSalesAgentCommissionExpression_onChange() throws Throwable {
		
		try {
			Double evalResult = Double.parseDouble(Utils.evaluate(txtSalesAgentCommissionExpression.getValue()));
			decSalesAgentCommission.setValue(new BigDecimal(evalResult));
			txtSalesAgentCommissionExpression.setValue(decSalesAgentCommission.getValue().toString());			
			
			Integer orderId = ((Intbox) cmbSalesAgent.query("#intObjectId")).getValue();
			Order order = new Order(orderId);
			int result = order.changeSalesAgentCommission(decSalesAgentCommission.getValue());				
			if (result == -1) {
				throw new NotEnoughRightsException();
			}
			else {
				Window wndContentPanel = (Window) decSalesAgentCommission.query("#wndContentPanel");
				OrderFormHelper.updateAmountAndTicket(order, wndContentPanel);
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
										Labels.getLabel("message.validation.typeAValidArithmeticExpression"), 
										Labels.getLabel("messageBoxTitle.Validation"),
										Messagebox.EXCLAMATION);
		}
	}
    	
	@Listen("onChange = combobox#cmbSalesAgent")
    public void cmbSalesAgent_onChange() throws Exception {
		changeSalesAgent();		
	}
}