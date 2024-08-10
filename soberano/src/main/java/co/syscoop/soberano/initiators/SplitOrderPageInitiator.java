package co.syscoop.soberano.initiators;

import java.math.BigDecimal;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zk.ui.util.InitiatorExt;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tree;

import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.models.CounterOrdersListModel;
import co.syscoop.soberano.models.OrderOrderedItemsTreeModelPopulator;
import co.syscoop.soberano.renderers.CounterOrdersRenderer;

public class SplitOrderPageInitiator implements Initiator, InitiatorExt {
	
	private Integer fromOrderId = 0;
	private Integer toOrderId = 0;
	
	protected void setFields(Page page) {
		
		try{
			String queryString = Executions.getCurrent().getDesktop().getQueryString();
			if (queryString.indexOf("from=") != -1) {
				if (queryString.indexOf("&to=") != -1) {
					toOrderId = Integer.parseInt((queryString.substring(queryString.indexOf("&to=") + 4, queryString.length())));
					fromOrderId = Integer.parseInt((queryString.substring(queryString.indexOf("from=") + 5, queryString.indexOf("&to="))));
				}
				else {
					toOrderId = 0;
					fromOrderId = Integer.parseInt((queryString.substring(queryString.indexOf("from=") + 5, queryString.length())));
				}
			}	
			
		}
		catch(StringIndexOutOfBoundsException ex) {
			Messagebox.show(Labels.getLabel("message.pageSplitOrder.WrongOrderForThatCounter"), 
  					Labels.getLabel("messageBoxTitle.Information"), 
					0, 
					Messagebox.EXCLAMATION);
		}
		catch(NumberFormatException ex) {
			Messagebox.show(Labels.getLabel("message.pageSplitOrder.WrongOrderForThatCounter"), 
  					Labels.getLabel("messageBoxTitle.Information"), 
					0, 
					Messagebox.EXCLAMATION);
		}
		catch(NullPointerException ex) {
			Messagebox.show(Labels.getLabel("message.pageSplitOrder.WrongOrderForThatCounter"), 
  					Labels.getLabel("messageBoxTitle.Information"), 
					0, 
					Messagebox.EXCLAMATION);
		}
	}
	
	private void selectOrderComboitem(Combobox cmb, Order order) {
		String orderId = ((Integer) order.getId()).toString();
		String orderTag = order.getLabel();
		String counterCode = order.getCountersStr();
		counterCode = order.getCountersStr().substring(0, counterCode.indexOf(","));		
		cmb.setValue(orderTag == null || orderTag.isEmpty() ? counterCode + " : " + orderId : counterCode + " : " + orderId + " (" + orderTag + ")");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void doAfterCompose(Page page, Component[] comps) throws Exception {
		
		try {
			Order originalOrder = null;
			Order targetOrder = null;			
			
			originalOrder = new Order(fromOrderId);
			originalOrder.get();
								
			//populate top and bottom combos			
			Combobox cmbFromOrder = (Combobox) comps[0].getPreviousSibling().query("#wndTop").query("combobox").query("#cmbFromOrder");
	  		Combobox cmbToOrder = (Combobox) comps[0].getPreviousSibling().query("#wndBottom").query("combobox").query("#cmbToOrder");
	  		cmbFromOrder.setModel(new CounterOrdersListModel());
	  		cmbToOrder.setModel(new CounterOrdersListModel());
	  		cmbFromOrder.setItemRenderer(new CounterOrdersRenderer());
	  		cmbToOrder.setItemRenderer(new CounterOrdersRenderer());
	  		
	  		Decimalbox decAmountTop = (Decimalbox) comps[0].getPreviousSibling().query("#wndTop").query("combobox").query("#decAmountTop");
	  		Decimalbox decAmountBottom = (Decimalbox) comps[0].getPreviousSibling().query("#wndBottom").query("combobox").query("#decAmountBottom");
	  			  		
			//original and target orders were provided
			if (toOrderId > 0) {
				
				//fill top form with original order data and botton form with target order's data
				targetOrder = new Order(toOrderId);
				targetOrder.get();
				selectOrderComboitem(cmbFromOrder, originalOrder);
				selectOrderComboitem(cmbToOrder, targetOrder);
				
				BigDecimal orderAmount = originalOrder.getAmount();				
				decAmountTop.setValue(orderAmount);
				
				orderAmount = targetOrder.getAmount();
				decAmountBottom.setValue(orderAmount);
			}
			//only original order was provided
			else {
				
				//fill forms with original order's data
				selectOrderComboitem(cmbFromOrder, originalOrder);
				selectOrderComboitem(cmbToOrder, originalOrder);
				
				BigDecimal orderAmount = originalOrder.getAmount();
				
				decAmountTop.setValue(orderAmount);
				decAmountBottom.setValue(orderAmount);
			}
			
			//populate orders tree
			Tree treeOrderedItemsFrom = (Tree) comps[0].getPreviousSibling().query("#wndTop").query("tree").query("#treeOrderedItemsFrom");
			Tree treeOrderedItemsTo = (Tree) comps[0].getPreviousSibling().query("#wndBottom").query("tree").query("#treeOrderedItemsTo");
			int targetOrderId = targetOrder == null ? originalOrder.getId() : targetOrder.getId();
			OrderOrderedItemsTreeModelPopulator.rerenderOrderedItemsTree(treeOrderedItemsFrom, originalOrder.getId(), originalOrder.getId(), targetOrderId);
			OrderOrderedItemsTreeModelPopulator.rerenderOrderedItemsTree(treeOrderedItemsTo, targetOrderId, targetOrderId, originalOrder.getId());
			
			//set the page title
			Label lblPageTitle = (Label) comps[0].query("include").getPreviousSibling().query("hbox").query("#hboxTitle").query("#lblPageTitle");
			lblPageTitle.setValue(Labels.getLabel("pageSplitOrder.WindowTitle"));
			
			//add listeners to combos
			cmbFromOrder.addEventListener("onSelect", new EventListener() {

				@Override
				public void onEvent(Event event) throws Exception {

					Combobox cmbFromOrder = (Combobox) event.getTarget();
					Combobox cmbToOrder = (Combobox) cmbFromOrder.getParent().getParent().getParent().getParent().query("south").query("combobox");
					if (Integer.parseInt(cmbFromOrder.getSelectedItem().getValue()) != 0 &&
							Integer.parseInt(cmbToOrder.getSelectedItem().getValue()) != 0) {
						Executions.sendRedirect("/split_order.zul?from=" + cmbFromOrder.getSelectedItem().getValue() + "&to=" + cmbToOrder.getSelectedItem().getValue());
					}
					else {
						if (Integer.parseInt(cmbFromOrder.getSelectedItem().getValue()) != 0) {
							Executions.sendRedirect("/split_order.zul?from=" + cmbFromOrder.getSelectedItem().getValue());
						}
						else {
							((Tree) cmbFromOrder.getParent().getParent().query("tree")).getChildren().clear();
						}						
					}
				}
			});
			cmbToOrder.addEventListener("onSelect", new EventListener() {

				@Override
				public void onEvent(Event event) throws Exception {

					Combobox cmbToOrder = (Combobox) event.getTarget();
					Combobox cmbFromOrder = (Combobox) cmbToOrder.getParent().getParent().getParent().getParent().query("north").query("combobox");
					if (Integer.parseInt(cmbFromOrder.getSelectedItem().getValue()) != 0 &&
							Integer.parseInt(cmbToOrder.getSelectedItem().getValue()) != 0) {
						Executions.sendRedirect("/split_order.zul?from=" + cmbFromOrder.getSelectedItem().getValue() + "&to=" + cmbToOrder.getSelectedItem().getValue());
					}
					else {
						if (Integer.parseInt(cmbToOrder.getSelectedItem().getValue()) != 0) {
							Executions.sendRedirect("/split_order.zul?from=" + cmbToOrder.getSelectedItem().getValue());
						}
						else {
							((Tree) cmbToOrder.getParent().getParent().query("tree")).getChildren().clear();
						}						
					}
				}
			});	
		}
		catch(Exception ex) {
			Messagebox.show(Labels.getLabel("message.pageSplitOrder.WrongOrderForThatCounter"), 
  					Labels.getLabel("messageBoxTitle.Information"), 
					0, 
					Messagebox.EXCLAMATION);
		}
	}

	@Override
	public boolean doCatch(Throwable ex) throws Exception {
		return false;
	}

	@Override
	public void doFinally() throws Exception {
	}

	@Override
	public void doInit(Page page, Map<String, Object> args) throws Exception {
		
		setFields(page);
	}
}