package co.syscoop.soberano.renderers;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Div;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.ui.helper.OrderFormHelper;
import co.syscoop.soberano.util.rowdata.OrderRowData;

public class ActivityTreeNodeRenderer implements TreeitemRenderer<DefaultTreeNode<NodeData>> {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void render(Treeitem item, DefaultTreeNode<NodeData> data, int index) throws Exception {
		
		NodeData nodeData = data.getData();
		Treerow tr = new Treerow();
		item.appendChild(tr);
		tr.appendChild(new Treecell(nodeData.getLabel()));
		if (nodeData.getValue() != null) {
			
			//highlight the node related to the order specified in the URL
			String orderToHighlight = "";
			try{
				String queryString = Executions.getCurrent().getDesktop().getQueryString();
				orderToHighlight = (queryString.substring(queryString.indexOf("id=") + 3, queryString.length())).replace("%20", " ");
			}
			catch(StringIndexOutOfBoundsException ex) {}
			catch(NullPointerException ex) {}
			if (nodeData.getLabel().trim().contains(orderToHighlight.trim())) {
				item.setSelected(true);
				Clients.scrollIntoView(item);
			}
			
			item.addEventListener("onClick", new EventListener() {

				@Override
				public void onEvent(Event event) throws Exception {
					OrderRowData ord = (OrderRowData) nodeData.getValue();
					if (ord.getObjectType() == 0) {						
						if (event.getTarget().getParent().getParent().getParent().getParent().getParent().getParent().query("popup") == null
							&& Executions.getCurrent().getDesktop().getRequestPath().contains("order.zul")) {
							
							Order order = new Order(ord.getOrderId());
							order.get();
							Window wndContentPanel = (Window) event.getTarget().getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent();
							((Intbox) wndContentPanel.query("#intObjectId")).setValue(ord.getOrderId());
							OrderFormHelper.updateAmountAndTicket(order, wndContentPanel);
							Vbox boxDetails = (Vbox) wndContentPanel.query("#boxDetails");
							((Textbox) boxDetails.query("#txtLabel")).setValue(order.getLabel());
							((Textbox) boxDetails.query("#txtCounters")).setValue(order.getCountersStr());
							((Intbox) boxDetails.query("#intDiscountTop")).setValue(order.getDiscount());
							((Intbox) boxDetails.query("#intDiscountBottom")).setValue(order.getDiscount());
							((Combobox) boxDetails.query("#cmbCustomer")).setText(order.getCustomerStr());
							((Combobox) boxDetails.query("#cmbDeliveryProvider")).setText(order.getDeliveryBy());
							Vbox vboxOrderItems = (Vbox) boxDetails.query("#wndOrderItems").query("#divOrderItems").query("#vboxOrderItems");
							if (vboxOrderItems != null) {
								vboxOrderItems.getChildren().clear();
								OrderFormHelper.renderOrderItems(order, 
																vboxOrderItems, 
																true);
							}
							else {
								Vbox vbox = new Vbox();
								vbox.setId("vboxOrderItems");
								Div divOrderItems = (Div) wndContentPanel.query("#wndOrderItems").query("#divOrderItems");
								divOrderItems.appendChild(vbox);
							}
						}
						else {
							Executions.sendRedirect("/order.zul?id=" + ord.getOrderId());
						}
					}
					else if (ord.getObjectType() == 1)
						Executions.sendRedirect("/process_run.zul?id=" + ord.getOrderId());
				}
			});
		}
		else {
			item.addEventListener("onClick", new EventListener() {

				@Override
				public void onEvent(Event event) throws Exception {
				}
			});			
		}
		item.setOpen(true);
	}
}
