package co.syscoop.soberano.ui.helper;

import org.zkoss.zul.Treeitem;

import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.renderers.ActionRequested;

@SuppressWarnings("serial")
public class ConfirmationOrderTreeitem extends Treeitem {
	
	private ActionRequested requestedAction = ActionRequested.NONE;
	private Order order = null;

	public ConfirmationOrderTreeitem(Order order) {
		super();
		this.setOrder(order);		
	}
	
	public void requestAction() {
		setRequestedAction(ActionRequested.SOME);
		this.setStyle("background-color:yellow;");
	}
	
	public void cancelRequestedAction() {
		setRequestedAction(ActionRequested.NONE);
		this.setStyle("background-color:transparent;");
	}

	public ActionRequested getRequestedAction() {
		return requestedAction;
	}

	public void setRequestedAction(ActionRequested requestedAction) {
		this.requestedAction = requestedAction;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}
