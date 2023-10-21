package co.syscoop.soberano.ui.helper;

import org.zkoss.zul.Box;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.SomeFieldsContainWrongValuesException;

public class OrderFormHelper extends BusinessActivityTrackedObjectFormHelper {
	
	@Override
	public Integer recordFromForm(Box boxDetails) throws Exception {
		return null;
	}

	@Override
	public void cleanForm(Box boxDetails) {}
	
	public void initForm(Window wndContentPanel, Integer orderId) throws Exception {
		
		Order order = new Order(orderId);
		order.get();
		
		((Intbox) wndContentPanel.query("#intObjectId")).setValue(orderId);
		
		Vbox boxDetails = (Vbox) wndContentPanel.query("#boxDetails");
		((Intbox) wndContentPanel.query("#intObjectId")).setValue(orderId);
		((Textbox) wndContentPanel.query("#txtLabel")).setValue(order.getLabel());
		((Textbox) wndContentPanel.query("#txtCounters")).setValue(order.getCountersStr());
		((Textbox) wndContentPanel.query("#txtCustomer")).setValue(order.getCustomerStr());
		
		//it's a delivery
		if (order.getDeliverTo() != null) {
			((Textbox) wndContentPanel.query("#txtDeliverTo")).setValue(order.getDeliverTo());
			((Label) wndContentPanel.query("#lblDeliverTo")).setVisible(true);
			((Textbox) wndContentPanel.query("#txtDeliverTo")).setVisible(true);
		}
		((Intbox) wndContentPanel.query("#intDiscountTop")).setValue(order.getDiscount());
		((Decimalbox) wndContentPanel.query("#decAmountTop")).setValue(order.getAmount());
		((Intbox) wndContentPanel.query("#intDiscountBottom")).setValue(order.getDiscount());
		((Decimalbox) wndContentPanel.query("#decAmountBottom")).setValue(order.getAmount());
		((Textbox) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#txtStage")).setValue(order.getStage());
		
		Grid gridOrderItems = (Grid) wndContentPanel.query("#gridOrderItems");
	}

	@Override
	public Integer cancelFromForm(Box boxDetails) throws Exception {
		return null;
	}
	
	@Override
	public Integer closeFromForm(Box boxDetails) throws Exception {
		return null;
	}

	@Override
	public Integer billFromForm(Box boxDetails) {
		return null;
	}
	
	@Override
	public Integer makeFromForm(Box boxDetails) throws Exception {
		
		Comboitem cmbiItemToOrder = ((Combobox) boxDetails.query("#cmbItemToOrder")).getSelectedItem();
		Intbox intRuns = (Intbox) boxDetails.query("#intRuns");
		Textbox txtSpecialInstructions = (Textbox) boxDetails.query("#txtSpecialInstructions");
		if (cmbiItemToOrder == null || intRuns.getValue() <= 0) {
			throw new SomeFieldsContainWrongValuesException();
		}
		else {
			return new Order(((Intbox) boxDetails.query("#intObjectId")).getValue())
						.make(((DomainObject) cmbiItemToOrder.getValue()).getId(),
								txtSpecialInstructions.getValue(),
								intRuns.getValue());
		}		
	}
}
