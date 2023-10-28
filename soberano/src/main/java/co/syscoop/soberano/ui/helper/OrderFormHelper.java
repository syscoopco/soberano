package co.syscoop.soberano.ui.helper;

import java.math.BigDecimal;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Box;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treecol;
import org.zkoss.zul.Treecols;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.domain.untracked.helper.OrderItem;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.exception.SomeFieldsContainWrongValuesException;
import co.syscoop.soberano.renderers.ActionRequested;
import co.syscoop.soberano.util.ExceptionTreatment;

public class OrderFormHelper extends BusinessActivityTrackedObjectFormHelper {
	
	@Override
	public Integer recordFromForm(Box boxDetails) throws Exception {
		return null;
	}

	@Override
	public void cleanForm(Box boxDetails) {}
	
	private enum ItemOperation
	{
		CANCEL(0),
		REORDER(1),
		CANCELALL(2);
		
	    @SuppressWarnings("unused")
		private int actionCode;
	    ItemOperation(int actionCode) {this.actionCode = actionCode;}
	}
	
	private void updateServedItems(ItemOperation itemOperation, Event event) throws Exception {
		
		String buttonId = event.getTarget().getId();
		Integer processRunId = Integer.parseInt(buttonId.substring(buttonId.indexOf("s") + 1, buttonId.length()));
		String inventoryItemCode =  ((Label) event.getTarget().query("#lblInventoryItemCode" + processRunId.toString())).getValue();
		ConfirmationOrderTreeitem confTreeitem = (ConfirmationOrderTreeitem) event.getTarget().getParent().getParent().getParent().getParent();
		Decimalbox decOneRunQuantity = (Decimalbox) event.getTarget().query("#decOneRunQuantity" + processRunId.toString());
		Label lblServedItems = (Label) event.getTarget().query("#lblServedItems" + processRunId.toString());
		Label lblOrderedItems = (Label) event.getTarget().query("#lblOrderedItems" + processRunId.toString());
		Button btnDecServedItems = (Button) event.getTarget().query("#btnDecServedItems" + processRunId.toString());
		Button btnIncServedItems = (Button) event.getTarget().query("#btnIncServedItems" + processRunId.toString());
		
		if (confTreeitem.getRequestedAction() != null && confTreeitem.getRequestedAction().equals(ActionRequested.SOME)) {
			
			confTreeitem.cancelRequestedAction();
			
			//reorder items
			if (itemOperation.equals(ItemOperation.REORDER)) {
				if (confTreeitem.getOrder().reorder(processRunId, inventoryItemCode, new BigDecimal(1)) == -1) {
					throw new NotEnoughRightsException();
				}
				else {						
					Double servedItems = Double.parseDouble(lblServedItems.getValue()) + decOneRunQuantity.getValue().doubleValue();
					if (servedItems > Double.parseDouble(lblOrderedItems.getValue())) {
						lblServedItems.setValue(lblOrderedItems.getValue());
					}
					else {
						lblServedItems.setValue(servedItems.toString());
					}
				}
			}
			//cancel 1 item
			else if (itemOperation.equals(ItemOperation.CANCEL)) {
				if (confTreeitem.getOrder().cancel(processRunId, inventoryItemCode, new BigDecimal(1)) == -1) {
					throw new NotEnoughRightsException();
				}
				else {
					Double servedItems = Double.parseDouble(lblServedItems.getValue()) - decOneRunQuantity.getValue().doubleValue();
					if (servedItems < 0.0) {
						lblServedItems.setValue("0");
					}
					else {
						lblServedItems.setValue(servedItems.toString());
					}
				}
			}
			//cancel all items
			else {
				Double orderedItems = Double.parseDouble(lblOrderedItems.getValue());
				if (confTreeitem.getOrder().cancel(processRunId, inventoryItemCode, new BigDecimal(orderedItems)) == -1) {
					throw new NotEnoughRightsException();
				}
				else {
					lblServedItems.setValue("0");
				}
			}
			
			Double servedItems = Double.parseDouble(lblServedItems.getValue());
			Double orderedItems = Double.parseDouble(lblOrderedItems.getValue());			
			if (servedItems > 0) {
				btnDecServedItems.setDisabled(false);
				if (orderedItems.equals(servedItems)) {
					btnIncServedItems.setDisabled(true);
				}
				else {
					btnIncServedItems.setDisabled(false);
				}
			}//servedItems == 0
			else {
				btnDecServedItems.setDisabled(true);
				btnIncServedItems.setDisabled(false);
			}
			
			//update amount fields
			BigDecimal amount = confTreeitem.getOrder().retrieveAmount();												
			if (amount.compareTo(new BigDecimal(0)) < 0) {
				throw new NotEnoughRightsException();
			}
			else {
				((Decimalbox) event.getTarget().query("#decAmountTop")).setValue(amount);
				((Decimalbox) event.getTarget().query("#decAmountBottom")).setValue(amount);
			}
		}
		else {
			confTreeitem.requestAction();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void renderOrderItems(Order order, Div divOrderItems) {
		
		for (String cat : order.getCategories()) {
			Tree catTree = new Tree();
			divOrderItems.appendChild(catTree);
			Treecols treeCols = new Treecols();
			catTree.appendChild(treeCols);
			Treecol treeCol = new Treecol();
			treeCols.appendChild(treeCol);
			Treechildren catChdn = new Treechildren();
			catTree.appendChild(catChdn);
			Treeitem catItem = new Treeitem();
			catChdn.appendChild(catItem);
			Treerow catRow = new Treerow();
			catItem.appendChild(catRow);
			Treecell catCell = new Treecell(cat);
			catRow.appendChild(catCell);
			Treechildren descChdn = new Treechildren();
			catItem.appendChild(descChdn);
			catItem.setOpen(true);
			for (String desc : order.getDescriptions().get(cat)) {
				Treeitem descItem = new Treeitem();
				descChdn.appendChild(descItem);
				Treerow descRow = new Treerow();
				descItem.appendChild(descRow);
				Treecell descCell = new Treecell(desc);
				descRow.appendChild(descCell);
				Treechildren oicChdn = new Treechildren();
				descItem.appendChild(oicChdn);
				descItem.setOpen(true);
				for (OrderItem oi : order.getOrderItems().get(cat + ":" + desc)) {
					ConfirmationOrderTreeitem oiItem = new ConfirmationOrderTreeitem(order);
					oicChdn.appendChild(oiItem);
					Treerow oiRow = new Treerow();
					oiItem.appendChild(oiRow);
					Treecell oiCell = new Treecell();
					Hbox oiBox = new Hbox();
					oiBox.setAlign("center");
					
					Decimalbox decOneRunQuantity = new Decimalbox(oi.getOneRunQuantity());
					decOneRunQuantity.setId("decOneRunQuantity" + oi.getProcessRunId().toString());
					decOneRunQuantity.setVisible(false);
					oiBox.appendChild(decOneRunQuantity);
					
					Label lblInventoryItemCode = new Label(oi.getInventoryItemCode());
					lblInventoryItemCode.setId("lblInventoryItemCode" + oi.getProcessRunId().toString());
					lblInventoryItemCode.setVisible(false);
					oiBox.appendChild(lblInventoryItemCode);
					
					Button btnIncServedItems = new Button("+");
					btnIncServedItems.setId("btnIncServedItems" + oi.getProcessRunId().toString());
					if (oi.getCanceledRuns() > 0) {
						btnIncServedItems.setDisabled(false);
					}
					else {
						btnIncServedItems.setDisabled(true);
					}
					oiBox.appendChild(btnIncServedItems);
					btnIncServedItems.addEventListener("onClick", new EventListener() {

						@Override
						public void onEvent(Event event) throws Exception {
							
							try {
								updateServedItems(ItemOperation.REORDER, event);
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
					
					Button btnDecServedItems = new Button("-");
					btnDecServedItems.setId("btnDecServedItems" + oi.getProcessRunId().toString());
					if (oi.getCanceledRuns() < oi.getOrderedRuns()) {
						btnDecServedItems.setDisabled(false);
					}
					else {
						btnDecServedItems.setDisabled(true);
					}
					oiBox.appendChild(btnDecServedItems);
					btnDecServedItems.addEventListener("onClick", new EventListener() {

						@Override
						public void onEvent(Event event) throws Exception {
							
							try {
								updateServedItems(ItemOperation.CANCEL, event);
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
					
					Button btnCancelAllItems = new Button();
					btnCancelAllItems.setImage("./images/delete.png");
					btnCancelAllItems.setId("btnCancelAllItems" + oi.getProcessRunId().toString());
					oiBox.appendChild(btnCancelAllItems);
					btnCancelAllItems.addEventListener("onClick", new EventListener() {

						@Override
						public void onEvent(Event event) throws Exception {
							
							try {
								updateServedItems(ItemOperation.CANCELALL, event);
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
					
					Label lblServedItems = new Label(new Double((oi.getOrderedRuns() - oi.getCanceledRuns())).toString());
					lblServedItems.setId("lblServedItems" + oi.getProcessRunId().toString());
					oiBox.appendChild(lblServedItems);
					oiBox.appendChild(new Label("/"));
					Label lblOrderedItems = new Label(new Double((oi.getOrderedRuns())).toString());
					lblOrderedItems.setId("lblOrderedItems" + oi.getProcessRunId().toString());
					oiBox.appendChild(lblOrderedItems);
					oiBox.appendChild((new Separator("horizontal")));
					oiBox.appendChild(new Label(oi.getProductUnit()));
					oiBox.appendChild(new Separator("horizontal"));
					oiBox.appendChild(new Label(oi.getProductName()));
					oiBox.appendChild((new Separator("horizontal")));
					oiBox.appendChild((new Separator("horizontal")));
					
					oiBox.appendChild((new Label(Labels.getLabel("caption.field.discount"))));
					Decimalbox decDiscount = new Decimalbox();
					decDiscount.setId("decDiscount" + oi.getProcessRunId().toString());
					decDiscount.setConstraint("no empty, no negative");
					decDiscount.setValue(new BigDecimal(0));
					decDiscount.addEventListener("onChange", new EventListener() {

						@Override
						public void onEvent(Event event) throws Exception {

							try {
								String targetId = event.getTarget().getId();
								Integer processRunId = Integer.parseInt(targetId.substring(targetId.indexOf("t") + 1, targetId.length()));
								ConfirmationOrderTreeitem confTreeitem = (ConfirmationOrderTreeitem) event.getTarget().getParent().getParent().getParent().getParent();
								if (confTreeitem.getOrder().itemDiscount(processRunId, ((Decimalbox) event.getTarget()).getValue()) == -1) {
									throw new NotEnoughRightsException();
								}
								else {						
									//update amount fields
									BigDecimal amount = confTreeitem.getOrder().retrieveAmount();												
									if (amount.compareTo(new BigDecimal(0)) < 0) {
										throw new NotEnoughRightsException();
									}
									else {
										((Decimalbox) event.getTarget().query("#decAmountTop")).setValue(amount);
										((Decimalbox) event.getTarget().query("#decAmountBottom")).setValue(amount);
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
					oiBox.appendChild(decDiscount);
					oiBox.appendChild((new Separator("horizontal")));
					oiBox.appendChild(new Label(oi.getProductUnit()));					
					
					oiBox.setId("cellOrderItemProcessRun" + oi.getProcessRunId());
					oiCell.appendChild(oiBox);
					oiRow.appendChild(oiCell);
				}
			}
		}
	}
	
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
		
		renderOrderItems(order, (Div) wndContentPanel.query("#divOrderItems"));
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
		Decimalbox decQuantity = (Decimalbox) boxDetails.query("#decQuantity");
		Decimalbox decOneRunQuantity = (Decimalbox) boxDetails.query("#decOneRunQuantity");
		Textbox txtSpecialInstructions = (Textbox) boxDetails.query("#txtSpecialInstructions");
		BigDecimal runs = decQuantity.getValue().divide(decOneRunQuantity.getValue());
		if (cmbiItemToOrder == null || runs.compareTo(new BigDecimal(0)) <= 0) {
			throw new SomeFieldsContainWrongValuesException();
		}
		else {
			return new Order(((Intbox) boxDetails.query("#intObjectId")).getValue())
						.make(((DomainObject) cmbiItemToOrder.getValue()).getId(),
								txtSpecialInstructions.getValue(),
								runs);
		}		
	}
}
