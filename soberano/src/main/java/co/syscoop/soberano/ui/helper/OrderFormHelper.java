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
import co.syscoop.soberano.util.SpringUtility;

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
		CANCELALL(2),
		SETQTY(3);
		
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
		Decimalbox decServedItems = (Decimalbox) event.getTarget().query("#decServedItems" + processRunId.toString());
		Decimalbox decDiscount = (Decimalbox) event.getTarget().query("#decDiscount" + processRunId.toString());
		Label lblOrderedItems = (Label) event.getTarget().query("#lblOrderedItems" + processRunId.toString());
		Button btnDecServedItems = (Button) event.getTarget().query("#btnDecServedItems" + processRunId.toString());
		Button btnIncServedItems = (Button) event.getTarget().query("#btnIncServedItems" + processRunId.toString());
		
		if ((confTreeitem.getRequestedAction() != null && confTreeitem.getRequestedAction().equals(ActionRequested.SOME)) ||
				itemOperation.equals(ItemOperation.SETQTY)) {
			
			confTreeitem.cancelRequestedAction();
			
			BigDecimal orderedItems = new BigDecimal(lblOrderedItems.getValue());
			
			//reorder items
			if (itemOperation.equals(ItemOperation.REORDER)) {
				if (confTreeitem.getOrder().reorder(processRunId, inventoryItemCode, new BigDecimal(1)) == -1) {
					throw new NotEnoughRightsException();
				}
				else {						
					BigDecimal servedItems = decServedItems.getValue().add(decOneRunQuantity.getValue()).setScale(8, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros();
					if (servedItems.compareTo(new BigDecimal(lblOrderedItems.getValue())) > 0) {
						decServedItems.setValue(new BigDecimal(lblOrderedItems.getValue()));
					}
					else {
						decServedItems.setValue(servedItems);
					}
				}
			}
			//cancel 1 item
			else if (itemOperation.equals(ItemOperation.CANCEL)) {
				if (confTreeitem.getOrder().cancel(processRunId, inventoryItemCode, new BigDecimal(1)) == -1) {
					throw new NotEnoughRightsException();
				}
				else {
					BigDecimal servedItems = decServedItems.getValue().subtract(decOneRunQuantity.getValue()).setScale(8, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros();
					if (servedItems.compareTo(new BigDecimal(0)) < 0) {
						decServedItems.setValue(new BigDecimal(0));
					}
					else {
						decServedItems.setValue(servedItems);
					}
					if (decDiscount.getValue().compareTo(servedItems) > 0) {
						decDiscount.setValue(servedItems);
					}
				}
			}
			//cancel all items
			else if (itemOperation.equals(ItemOperation.CANCELALL)) {
				if (confTreeitem.getOrder().cancel(processRunId, inventoryItemCode, orderedItems) == -1) {
					throw new NotEnoughRightsException();
				}
				else {
					decServedItems.setValue(new BigDecimal(0));
					decDiscount.setValue(new BigDecimal(0));
				}
			}
			//set item quantity
			else {
				if (confTreeitem.getOrder().cancel(processRunId, inventoryItemCode, orderedItems) == -1) {
					throw new NotEnoughRightsException();
				}
				else {
					decDiscount.setValue(new BigDecimal(0));
					if (confTreeitem.getOrder().reorder(processRunId, inventoryItemCode, decServedItems.getValue()) == -1) {
						throw new NotEnoughRightsException();
					}
					else {
						if (decServedItems.getValue().compareTo(new BigDecimal(0)) < 0) {
							decServedItems.setValue(new BigDecimal(0));
						} else if (decServedItems.getValue().compareTo(new BigDecimal(lblOrderedItems.getValue())) > 0) {
							decServedItems.setValue(new BigDecimal(lblOrderedItems.getValue()));
						}
					}
				}
			}
			
			if (decServedItems.getValue().compareTo(new BigDecimal(0)) > 0) {
				btnDecServedItems.setDisabled(false);
				if (orderedItems.equals(decServedItems.getValue())) {
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
	
	private void discountBoxHandler(Event event) throws Exception {
		
		try {
			Decimalbox decDiscount = (Decimalbox) event.getTarget();
			String targetId = decDiscount.getId();
			Integer processRunId = Integer.parseInt(targetId.substring(targetId.indexOf("t") + 1, targetId.length()));
			ConfirmationOrderTreeitem confTreeitem = (ConfirmationOrderTreeitem) decDiscount.getParent().getParent().getParent().getParent();
			String inventoryItemCode =  ((Label) decDiscount.query("#lblInventoryItemCode" + processRunId.toString())).getValue();
			Decimalbox decServedItems = (Decimalbox) decDiscount.query("#decServedItems" + processRunId.toString());
			BigDecimal discountableRuns = decServedItems.getValue();
			BigDecimal runsToDiscount = decDiscount.getValue();
			if (runsToDiscount.compareTo(new BigDecimal(0)) < 0) {
				runsToDiscount = new BigDecimal(0);
				decDiscount.setValue(new BigDecimal(0));
			}
			else if (runsToDiscount.compareTo(discountableRuns) > 0) {
				runsToDiscount = discountableRuns;
				decDiscount.setValue(discountableRuns);
			}
			if (confTreeitem.getOrder().applyItemDiscount(processRunId, inventoryItemCode, runsToDiscount) == -1) {
				throw new NotEnoughRightsException();
			}
			else {						
				//update amount fields
				BigDecimal amount = confTreeitem.getOrder().retrieveAmount();												
				if (amount.compareTo(new BigDecimal(0)) < 0) {
					throw new NotEnoughRightsException();
				}
				else {
					((Decimalbox) decDiscount.query("#decAmountTop")).setValue(amount);
					((Decimalbox) decDiscount.query("#decAmountBottom")).setValue(amount);
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
	
	private void updateCanceledButEndedItems(Event event) throws Exception {
		
		try {
			Decimalbox decCanceledButEndedItems = (Decimalbox) event.getTarget();
			String targetId = decCanceledButEndedItems.getId();
			Integer processRunId = Integer.parseInt(targetId.substring(targetId.indexOf("s") + 1, targetId.length()));
			ConfirmationOrderTreeitem confTreeitem = (ConfirmationOrderTreeitem) decCanceledButEndedItems.getParent().getParent().getParent().getParent();
			String inventoryItemCode =  ((Label) decCanceledButEndedItems.query("#lblInventoryItemCode" + processRunId.toString())).getValue();
			Label lblCanceledItems = (Label) decCanceledButEndedItems.query("#lblCanceledItems" + processRunId.toString());
			BigDecimal endableRuns = new BigDecimal(lblCanceledItems.getValue());
			BigDecimal runsToEnd = decCanceledButEndedItems.getValue();
			if (runsToEnd.compareTo(new BigDecimal(0)) < 0) {
				runsToEnd = new BigDecimal(0);
				decCanceledButEndedItems.setValue(new BigDecimal(0));
			}
			else if (runsToEnd.compareTo(endableRuns) > 0) {
				runsToEnd = endableRuns;
				decCanceledButEndedItems.setValue(endableRuns);
			}
			if (confTreeitem.getOrder().endCanceledRuns(processRunId, inventoryItemCode, runsToEnd) == -1) {
				throw new NotEnoughRightsException();
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void renderOrderItemsForBilling(Order order, Div divOrderItems) {

		for (String cat : order.getCategories()) {
			Tree treeCat = new Tree();
			divOrderItems.appendChild(treeCat);
			Treecols treeCols = new Treecols();
			treeCat.appendChild(treeCols);
			Treecol treeCol = new Treecol();
			treeCols.appendChild(treeCol);
			Treechildren chdnCat = new Treechildren();
			treeCat.appendChild(chdnCat);
			Treeitem titemCat = new Treeitem();
			chdnCat.appendChild(titemCat);
			Treerow rowCat = new Treerow();
			titemCat.appendChild(rowCat);
			Treecell cellCat = new Treecell(cat);
			rowCat.appendChild(cellCat);
			Treechildren chdnDesc = new Treechildren();
			titemCat.appendChild(chdnDesc);
			titemCat.setOpen(true);
			for (String desc : order.getDescriptions().get(cat)) {
				Treeitem titemDesc = new Treeitem();
				chdnDesc.appendChild(titemDesc);
				Treerow rowDesc = new Treerow();
				titemDesc.appendChild(rowDesc);
				Treecell cellDesc = new Treecell(desc);
				cellDesc.setId("cell" + cat + "_desc");
				rowDesc.appendChild(cellDesc);
				Treechildren chdnOic = new Treechildren();
				titemDesc.appendChild(chdnOic);
				titemDesc.setOpen(true);
				for (OrderItem oi : order.getOrderItems().get(cat + ":" + desc)) {
					
					//only items with cancellations
					if (oi.getCanceledRuns().compareTo(new BigDecimal(0)) > 0) {
						
						ConfirmationOrderTreeitem oiItem = new ConfirmationOrderTreeitem(order);
						chdnOic.appendChild(oiItem);
						Treerow rowOi = new Treerow();
						rowOi.setId(oi.getProcessRunId().toString());
						oiItem.appendChild(rowOi);
						Treecell cellOi = new Treecell();
						Hbox boxOi = new Hbox();
						boxOi.setAlign("center");
						
						Label lblInventoryItemCode = new Label(oi.getInventoryItemCode());
						lblInventoryItemCode.setId("lblInventoryItemCode" + oi.getProcessRunId().toString());
						lblInventoryItemCode.setVisible(false);
						boxOi.appendChild(lblInventoryItemCode);
																	
						Decimalbox decCanceledButEndedItems = new Decimalbox(new BigDecimal(0));
						decCanceledButEndedItems.setFormat("####.########");
						decCanceledButEndedItems.setConstraint("no negative,no empty");
						decCanceledButEndedItems.setId("decCanceledButEndedItems" + oi.getProcessRunId().toString());
						boxOi.appendChild(decCanceledButEndedItems);
						decCanceledButEndedItems.addEventListener("onChange", new EventListener() {

							@Override
							public void onEvent(Event event) throws Exception {
								
								try {
									updateCanceledButEndedItems(event);
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
						
						decCanceledButEndedItems.addEventListener("onClick", new EventListener() {

							@Override
							public void onEvent(Event event) throws Exception {
								
								try {
									if (SpringUtility.underTesting()) updateCanceledButEndedItems(event);
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
																		
						boxOi.appendChild(new Label("/"));					
						
						Label lblCanceledItems = new Label((oi.getCanceledRuns().setScale(8, BigDecimal.ROUND_HALF_EVEN).toString()));
						lblCanceledItems.setId("lblCanceledItems" + oi.getProcessRunId().toString());
						boxOi.appendChild(lblCanceledItems);
						
						boxOi.appendChild((new Separator("horizontal")));
						
						Label lblProductUnit = new Label(oi.getProductUnit());
						lblProductUnit.setId("lblProductUnit" + oi.getProcessRunId().toString());					
						boxOi.appendChild(lblProductUnit);	
						
						boxOi.appendChild(new Separator("horizontal"));
						
						Label lblProductName = new Label(oi.getProductName());
						lblProductName.setId("lblProductName" + oi.getProcessRunId().toString());					
						boxOi.appendChild(lblProductName);
						
						boxOi.setId("cellOrderItemProcessRun" + oi.getProcessRunId());
						cellOi.appendChild(boxOi);
						rowOi.appendChild(cellOi);
					}
				}
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void renderOrderItemsForManagement(Order order, Div divOrderItems) {
		
		for (String cat : order.getCategories()) {
			Tree treeCat = new Tree();
			divOrderItems.appendChild(treeCat);
			Treecols treeCols = new Treecols();
			treeCat.appendChild(treeCols);
			Treecol treeCol = new Treecol();
			treeCols.appendChild(treeCol);
			Treechildren chdnCat = new Treechildren();
			treeCat.appendChild(chdnCat);
			Treeitem titemCat = new Treeitem();
			chdnCat.appendChild(titemCat);
			Treerow rowCat = new Treerow();
			titemCat.appendChild(rowCat);
			Treecell cellCat = new Treecell(cat);
			rowCat.appendChild(cellCat);
			Treechildren chdnDesc = new Treechildren();
			titemCat.appendChild(chdnDesc);
			titemCat.setOpen(true);
			for (String desc : order.getDescriptions().get(cat)) {
				Treeitem titemDesc = new Treeitem();
				chdnDesc.appendChild(titemDesc);
				Treerow rowDesc = new Treerow();
				titemDesc.appendChild(rowDesc);
				Treecell cellDesc = new Treecell(desc);
				cellDesc.setId("cell" + cat + "_desc");
				rowDesc.appendChild(cellDesc);
				Treechildren chdnOic = new Treechildren();
				titemDesc.appendChild(chdnOic);
				titemDesc.setOpen(true);
				for (OrderItem oi : order.getOrderItems().get(cat + ":" + desc)) {
					ConfirmationOrderTreeitem oiItem = new ConfirmationOrderTreeitem(order);
					chdnOic.appendChild(oiItem);
					Treerow rowOi = new Treerow();
					rowOi.setId(oi.getProcessRunId().toString());
					oiItem.appendChild(rowOi);
					Treecell cellOi = new Treecell();
					Hbox boxOi = new Hbox();
					boxOi.setAlign("center");
					
					Decimalbox decOneRunQuantity = new Decimalbox(oi.getOneRunQuantity());
					decOneRunQuantity.setId("decOneRunQuantity" + oi.getProcessRunId().toString());
					decOneRunQuantity.setVisible(false);
					boxOi.appendChild(decOneRunQuantity);
					
					Label lblInventoryItemCode = new Label(oi.getInventoryItemCode());
					lblInventoryItemCode.setId("lblInventoryItemCode" + oi.getProcessRunId().toString());
					lblInventoryItemCode.setVisible(false);
					boxOi.appendChild(lblInventoryItemCode);
					
					Button btnIncServedItems = new Button("+");
					btnIncServedItems.setId("btnIncServedItems" + oi.getProcessRunId().toString());
					if ((oi.getCanceledRuns()).compareTo(new BigDecimal(0)) > 0) {
						btnIncServedItems.setDisabled(false);
					}
					else {
						btnIncServedItems.setDisabled(true);
					}
					boxOi.appendChild(btnIncServedItems);
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
					if (oi.getCanceledRuns().compareTo(oi.getOrderedRuns()) < 0) {
						btnDecServedItems.setDisabled(false);
					}
					else {
						btnDecServedItems.setDisabled(true);
					}
					boxOi.appendChild(btnDecServedItems);
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
					boxOi.appendChild(btnCancelAllItems);
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
					
					BigDecimal servedItems = oi.getOrderedRuns().subtract(oi.getCanceledRuns());
					servedItems = servedItems.setScale(8, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros();
					Decimalbox decServedItems = new Decimalbox(servedItems);
					decServedItems.setFormat("####.########");
					decServedItems.setId("decServedItems" + oi.getProcessRunId().toString());
					boxOi.appendChild(decServedItems);
					decServedItems.addEventListener("onChange", new EventListener() {

						@Override
						public void onEvent(Event event) throws Exception {
							
							try {
								updateServedItems(ItemOperation.SETQTY, event);
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
					
					decServedItems.addEventListener("onClick", new EventListener() {

						@Override
						public void onEvent(Event event) throws Exception {
							
							try {
								if (SpringUtility.underTesting()) updateServedItems(ItemOperation.SETQTY, event);
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
					
					boxOi.appendChild(new Label("/"));					
					
					Label lblOrderedItems = new Label((oi.getOrderedRuns().setScale(8, BigDecimal.ROUND_HALF_EVEN).toString()));
					lblOrderedItems.setId("lblOrderedItems" + oi.getProcessRunId().toString());
					boxOi.appendChild(lblOrderedItems);
					
					boxOi.appendChild((new Separator("horizontal")));
					
					Label lblProductUnit = new Label(oi.getProductUnit());
					lblProductUnit.setId("lblProductUnit" + oi.getProcessRunId().toString());					
					boxOi.appendChild(lblProductUnit);	
					
					boxOi.appendChild(new Separator("horizontal"));
					
					Label lblProductName = new Label(oi.getProductName());
					lblProductName.setId("lblProductName" + oi.getProcessRunId().toString());					
					boxOi.appendChild(lblProductName);
					
					boxOi.appendChild((new Separator("horizontal")));
					boxOi.appendChild((new Separator("horizontal")));
					
					boxOi.appendChild((new Label(Labels.getLabel("caption.field.discount"))));
					Decimalbox decDiscount = new Decimalbox();
					decDiscount.setId("decDiscount" + oi.getProcessRunId().toString());
					decDiscount.setConstraint("no empty, no negative");
					decDiscount.setValue(oi.getDiscountedRuns());
					decDiscount.setFormat("####.########");
					decDiscount.addEventListener("onChange", new EventListener() {

						@Override
						public void onEvent(Event event) throws Exception {

							discountBoxHandler(event);
						}
					});
					decDiscount.addEventListener("onClick", new EventListener() {

						@Override
						public void onEvent(Event event) throws Exception {

							if (SpringUtility.underTesting()) discountBoxHandler(event);
						}
					});					
					boxOi.appendChild(decDiscount);
					boxOi.appendChild((new Separator("horizontal")));
					
					Label lblProductUnit1 = new Label(oi.getProductUnit());
					lblProductUnit1.setId("lblProductUnit1" + oi.getProcessRunId().toString());					
					boxOi.appendChild(lblProductUnit1);					
					
					boxOi.setId("cellOrderItemProcessRun" + oi.getProcessRunId());
					cellOi.appendChild(boxOi);
					rowOi.appendChild(cellOi);
				}
			}
		}
	}
	
	private Order initForm(Window wndContentPanel, Integer orderId) throws Exception {
		
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
		((Label) wndContentPanel.query("#lblCurrencyTop")).setValue(order.getCurrencyCode());
		((Intbox) wndContentPanel.query("#intDiscountBottom")).setValue(order.getDiscount());
		((Decimalbox) wndContentPanel.query("#decAmountBottom")).setValue(order.getAmount());
		((Label) wndContentPanel.query("#lblCurrencyBottom")).setValue(order.getCurrencyCode());
		((Textbox) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#txtStage")).setValue(order.getStage());
		
		return order;
	}
	
	public void initFormForManagement(Window wndContentPanel, Integer orderId) throws Exception {
		
		Order order = initForm(wndContentPanel, orderId);		
		renderOrderItemsForManagement(order, (Div) wndContentPanel.query("#divOrderItems"));
	}
	
	public void initFormForBilling(Window wndContentPanel, Integer orderId) throws Exception {
		
		Order order = initForm(wndContentPanel, orderId);
		renderOrderItemsForBilling(order, (Div) wndContentPanel.query("#divOrderItems"));
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
