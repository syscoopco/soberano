package co.syscoop.soberano.renderers;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;

import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.domain.untracked.*;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;

public class OrderedItemTreeNodeRenderer implements TreeitemRenderer<DefaultTreeNode<NodeData>> {

	private class OrdersForMovement {
		Integer fromOrder = 0;
		Integer toOrder = 0;
	}
	
	OrdersForMovement ordersForMovement = new OrdersForMovement();
	
	private String getTargetCounterForMovement(Component comp) {
		Window wnd = (Window) comp.getParent().getParent().getParent().getParent().getParent().getParent().getParent();
		Combobox cmbFromOrder;
		Combobox cmbToOrder;
		if (wnd.getId().equals("wndTop")) {
			cmbFromOrder = (Combobox) wnd.query("combobox");
			cmbToOrder = (Combobox) cmbFromOrder.getParent().getParent().getParent().getParent().query("south").query("combobox");
		}
		else {
			cmbFromOrder = (Combobox) wnd.query("combobox");
			cmbToOrder = (Combobox) cmbFromOrder.getParent().getParent().getParent().getParent().query("north").query("combobox");
		}
		return cmbToOrder.getText().substring(0, cmbToOrder.getText().indexOf(" :"));
	}
	
	private void setOrdersForMovement(Component comp) {
		Window wnd = (Window) comp.getParent().getParent().getParent().getParent().getParent().getParent().getParent();
		Combobox cmbFromOrder;
		Combobox cmbToOrder;
		if (wnd.getId().equals("wndTop")) {
			cmbFromOrder = (Combobox) wnd.query("combobox");
			cmbToOrder = (Combobox) cmbFromOrder.getParent().getParent().getParent().getParent().query("south").query("combobox");
		}
		else {
			cmbFromOrder = (Combobox) wnd.query("combobox");
			cmbToOrder = (Combobox) cmbFromOrder.getParent().getParent().getParent().getParent().query("north").query("combobox");
		}
		ordersForMovement.fromOrder = Integer.parseInt(cmbFromOrder.getSelectedItem().getValue());
		ordersForMovement.toOrder = Integer.parseInt(cmbToOrder.getSelectedItem().getValue());
	}
	
	private void refreshTheForm(Button btn) {
		
		Window wnd = (Window) btn.getParent().getParent().getParent().getParent().getParent().getParent().getParent();
		Combobox cmbFromOrder;
		Combobox cmbToOrder;
		if (wnd.getId().equals("wndTop")) {
			cmbFromOrder = (Combobox) wnd.query("combobox");
			cmbToOrder = (Combobox) cmbFromOrder.getParent().getParent().getParent().getParent().query("south").query("combobox");
		}
		else {
			cmbToOrder = (Combobox) wnd.query("combobox");
			cmbFromOrder = (Combobox) cmbToOrder.getParent().getParent().getParent().getParent().query("north").query("combobox");
		}
		Executions.sendRedirect("/split_order.zul?from=" + cmbFromOrder.getSelectedItem().getValue() + "&to=" + cmbToOrder.getSelectedItem().getValue());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void render(Treeitem item, DefaultTreeNode<NodeData> data, int index) throws Exception {
		
		NodeData nodeData = data.getData();
		
		Treerow tr = new Treerow();		
		item.appendChild(tr);
		
		Treecell tc = new Treecell(nodeData.getLabel());
		
		Label lblSep = new Label("-");
		Label lblServedQuantity = new Label(((OrderedItem) nodeData.getValue()).getServedQuantity().toPlainString());
		Label lblSlash = new Label("/");
		Label lblOrderedQuantity = new Label(((OrderedItem) nodeData.getValue()).getOrderedQuantity().toPlainString());
		
		Separator sep = new Separator("vertical");
		tc.appendChild(sep);
		tc.appendChild(lblSep);
		Separator sep1 = new Separator("vertical");
		tc.appendChild(sep1);
		tc.appendChild(lblServedQuantity);
		Separator sep2 = new Separator("vertical");
		tc.appendChild(sep2);
		tc.appendChild(lblSlash);
		Separator sep3 = new Separator("vertical");
		tc.appendChild(sep3);
		tc.appendChild(lblOrderedQuantity);		
		
		Separator sep4 = new Separator("vertical");
		tc.appendChild(sep4);
		Button btnDec = new Button("- 1");
		btnDec.setDisabled(true);
		btnDec.setClass("ContextualButton");
		tc.appendChild(btnDec);
		Separator sep5 = new Separator("vertical");
		tc.appendChild(sep5);
		Button btnDecDec = new Button("- *");
		btnDecDec.setClass("ContextualButton");
		tc.appendChild(btnDecDec);
		Separator sep6 = new Separator("vertical");
		tc.appendChild(sep6);
		Button btnMove = new Button("< >");
		btnMove.setClass("ContextualButton");
		tc.appendChild(btnMove);
		
		btnDec.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {

				try {
					setOrdersForMovement(event.getTarget());
					if (ordersForMovement.fromOrder > 0 && 
							ordersForMovement.toOrder > 0 && 
							ordersForMovement.fromOrder != ordersForMovement.toOrder) {
						int processRunId = ((OrderedItem) (((NodeData) data.getData()).getValue())).getProcessRunId();
						int result = new Order(((OrderedItem) (((NodeData) data.getData()).getValue())).getOrderId()).moveOrderedItemToOrder(ordersForMovement.fromOrder, ordersForMovement.toOrder, processRunId);
						if (result == -1) {
							throw new NotEnoughRightsException();
						}
						else {										
							//refresh the form								
							Button btn = (Button) event.getTarget();
							refreshTheForm(btn);
						}
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
		});
		
		btnDecDec.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {

				try {
					setOrdersForMovement(event.getTarget());
					if (ordersForMovement.fromOrder > 0 && 
							ordersForMovement.toOrder > 0 && 
							ordersForMovement.fromOrder != ordersForMovement.toOrder) {
						int processRunId = ((OrderedItem) (((NodeData) data.getData()).getValue())).getProcessRunId();						
						int result = new Order(((OrderedItem) (((NodeData) data.getData()).getValue())).getOrderId()).moveAllOrderedItemsToOrder(ordersForMovement.fromOrder, ordersForMovement.toOrder, processRunId);
						if (result == -1) {
							throw new NotEnoughRightsException();
						}
						else {										
							//refresh the form								
							Button btn = (Button) event.getTarget();
							refreshTheForm(btn);
						}
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
		});
		
		btnMove.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {

				try {						
					setOrdersForMovement(event.getTarget());
					if (ordersForMovement.fromOrder > 0) {
						
						//retrieve target counter
						String toCounter = getTargetCounterForMovement(event.getTarget());
					
						int result = new Order(((OrderedItem) (((NodeData) data.getData()).getValue())).getOrderId()).moveOrderToCounter(ordersForMovement.fromOrder, toCounter);
						if (result == -1) {
							throw new NotEnoughRightsException();
						}
						else {										
							//refresh the form								
							Executions.sendRedirect("/split_order.zul?from=" + ordersForMovement.fromOrder);
						}
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
		});
		
		tr.appendChild(tc);
	}
}