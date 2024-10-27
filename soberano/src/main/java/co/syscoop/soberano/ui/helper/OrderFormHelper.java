package co.syscoop.soberano.ui.helper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Box;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Include;
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
import co.syscoop.soberano.enums.Stage;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.exception.SomeFieldsContainWrongValuesException;
import co.syscoop.soberano.renderers.ActionRequested;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.util.ui.ZKUtilitity;
import co.syscoop.soberano.vocabulary.Translator;

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
	
	public static void updateAmountAndTicket(Order order, Window wndContentPanel) throws Exception {
		BigDecimal amount = order.retrieveAmount();											
		if (amount.compareTo(new BigDecimal(0)) < 0) {
			throw new NotEnoughRightsException();
		}
		else {
			Decimalbox decAmountTop = (Decimalbox) wndContentPanel.query("#decAmountTop");
			decAmountTop.setValue(amount);				
			((Decimalbox) decAmountTop.query("#decAmountBottom")).setValue(amount);
			
			//update ticket
			((Textbox) wndContentPanel.query("#wndOrderItems").query("#wndTicket").query("#txtTicket")).
				setValue(Translator.translate(order.retrieveTicket(new BigDecimal(0), new BigDecimal(0)).getTextToPrint()));
		}
	}
	
	private static void updateServedItems(ItemOperation itemOperation, Event event) throws Exception {
		
		String buttonId = event.getTarget().getId();
		Integer processRunId = Integer.parseInt(buttonId.substring(buttonId.indexOf("s") + 1, buttonId.length()));
		String inventoryItemCode =  ((Label) event.getTarget().query("#lblInventoryItemCode" + processRunId.toString())).getValue();
		ConfirmationOrderTreeitem confTreeitem = (ConfirmationOrderTreeitem) event.getTarget().getParent().getParent().getParent().getParent().getParent();
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
			
			//update amount fields and ticket
			updateAmountAndTicket(confTreeitem.getOrder(), 
								(Window) event.getTarget().getParent().getParent().getParent().getParent().getParent().
												getParent().getParent().getParent().getParent().getParent().getParent().
												getParent().getParent().getParent().getParent().getParent().getParent().
												getParent().query("#wndContentPanel"));
		}
		else {
			confTreeitem.requestAction();
		}
	}
	
	private static void discountBoxHandler(Event event) throws Exception {
		
		try {
			Decimalbox decDiscount = (Decimalbox) event.getTarget();
			String targetId = decDiscount.getId();
			Integer processRunId = Integer.parseInt(targetId.substring(targetId.indexOf("t") + 1, targetId.length()));
			ConfirmationOrderTreeitem confTreeitem = (ConfirmationOrderTreeitem) decDiscount.getParent().getParent().getParent().getParent().getParent();
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
			Order order = confTreeitem.getOrder();
			if (order.applyItemDiscount(processRunId, inventoryItemCode, runsToDiscount) == -1) {
				throw new NotEnoughRightsException();
			}
			else {						
				//update amount fields and ticket
				updateAmountAndTicket(confTreeitem.getOrder(), 
									(Window) event.getTarget().getParent().getParent().getParent().getParent().
												getParent().getParent().getParent().getParent().getParent().getParent().
												getParent().getParent().getParent().getParent().getParent().getParent().
												getParent().getParent().query("#wndContentPanel"));
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
	
	private static void updateCanceledButEndedItems(Event event) throws Exception {
		
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
	private static void renderItems(Order order, String cat, String desc, Treechildren chdnOic, Boolean itsForManagement) {
		
		if (!itsForManagement) {
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
					
					boxOi.appendChild(new Label("/"));					
					
					Label lblCanceledItems = new Label((oi.getCanceledRuns().setScale(8, BigDecimal.ROUND_HALF_EVEN).toString()));
					lblCanceledItems.setId("lblCanceledItems" + oi.getProcessRunId().toString());
					boxOi.appendChild(lblCanceledItems);
					
					boxOi.appendChild(new Separator("horizontal"));
					
					Label lblProductUnit = new Label(oi.getProductUnit());
					lblProductUnit.setId("lblProductUnit" + oi.getProcessRunId().toString());					
					boxOi.appendChild(lblProductUnit);	
					
					boxOi.appendChild(new Separator("horizontal"));				
					boxOi.appendChild(new Separator("horizontal"));
					boxOi.appendChild(new Separator("horizontal"));
					
					Label lblProductName = new Label(oi.getProductName());
					lblProductName.setClass("Caption");
					lblProductName.setId("lblProductName" + oi.getProcessRunId().toString());					
					boxOi.appendChild(lblProductName);
					
					boxOi.setId("cellOrderItemProcessRun" + oi.getProcessRunId());
					cellOi.appendChild(boxOi);
					rowOi.appendChild(cellOi);
					
					//trigger onchange event for making canceled but ended items equal to zero in database, upon rendering.
					Events.sendEvent(Events.ON_CHANGE, decCanceledButEndedItems, null);
				}
			}
		}
		else {
			for (OrderItem oi : order.getOrderItems().get(cat + ":" + desc)) {
				
				if (oi.getThisIsAnAdditionOf() == null || oi.getThisIsAnAdditionOf() == 0) {
					ConfirmationOrderTreeitem oiItem = new ConfirmationOrderTreeitem(order);
					chdnOic.appendChild(oiItem);
					Treerow rowOi = new Treerow();
					rowOi.setId(oi.getProcessRunId().toString());
					oiItem.appendChild(rowOi);
					Treecell cellOi = new Treecell();
					Vbox vboxOi = new Vbox();
					
					Label lblProductName = new Label(oi.getProductName());
					lblProductName.setSclass("Caption");
					lblProductName.setId("lblProductName" + oi.getProcessRunId().toString());					
					vboxOi.appendChild(lblProductName);				
					
					Hbox boxOi = new Hbox();
					boxOi.setAlign("center");
					vboxOi.appendChild(boxOi);				
					
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
					
					boxOi.appendChild(new Separator("horizontal"));
					
					Label lblProductUnit = new Label(oi.getProductUnit());
					lblProductUnit.setId("lblProductUnit" + oi.getProcessRunId().toString());					
					boxOi.appendChild(lblProductUnit);	
					
					boxOi.appendChild(new Separator("horizontal"));				
					boxOi.appendChild(new Separator("horizontal"));
					boxOi.appendChild(new Separator("horizontal"));
					
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
					boxOi.appendChild(new Separator("horizontal"));
					
					Label lblProductUnit1 = new Label(oi.getProductUnit());
					lblProductUnit1.setId("lblProductUnit1" + oi.getProcessRunId().toString());					
					boxOi.appendChild(lblProductUnit1);					
					
					boxOi.setId("cellOrderItemProcessRun" + oi.getProcessRunId());
					cellOi.appendChild(vboxOi);
					rowOi.appendChild(cellOi);
					
					//additions
					//TODO: additions management testing is pending. sessions are unavailable on junit testing context
					if (!SpringUtility.underTesting()) {
						Hbox hboxChooseAddition = new Hbox();
						hboxChooseAddition.setAlign("center");
						hboxChooseAddition.setPack("end");
						boxOi.appendChild(new Separator("horizontal"));
						boxOi.appendChild(new Separator("horizontal"));
						boxOi.appendChild(new Separator("horizontal"));
						boxOi.appendChild(new Separator("horizontal"));
						boxOi.appendChild(hboxChooseAddition);
						
						Map arg = new HashMap();
						arg.put("additionSelectionViewModel", Executions.getCurrent().getSession().getAttribute("addition_selection_view_model"));
						Executions.createComponents("/addition_combobox.zul", hboxChooseAddition, arg);
						
						Button btnAddAddition = new Button("+");
						btnAddAddition.setId("btnAddAddition" + oi.getProcessRunId().toString());				
						btnAddAddition.addEventListener("onClick", new EventListener() {

							@Override
							public void onEvent(Event event) throws Exception {
								
								try {
									Combobox cmbAddition = (Combobox) btnAddAddition.getParent().query("combobox");							
									Comboitem cmbiItemToAdd = cmbAddition.getSelectedItem();
									
									if (cmbiItemToAdd == null) {
										throw new SomeFieldsContainWrongValuesException();
									}
									else {
										if (order.orderAddition(((DomainObject) cmbiItemToAdd.getValue()).getId(), oi.getProcessRunId()) == -1) {
											throw new NotEnoughRightsException();
										}
										else {
											//update form
										}
									}
								}
								catch(SomeFieldsContainWrongValuesException ex) {
									ExceptionTreatment.logAndShow(ex, 
											Labels.getLabel("message.validation.someFieldsContainWrongValues"), 
											Labels.getLabel("messageBoxTitle.Validation"),
											Messagebox.EXCLAMATION);
								}
								catch(NotEnoughRightsException ex) {
									ExceptionTreatment.logAndShow(ex, 
																Labels.getLabel("message.permissions.NotEnoughRights"), 
																Labels.getLabel("messageBoxTitle.Warning"),
																Messagebox.EXCLAMATION);
								}
							}
						});				
						hboxChooseAddition.appendChild(btnAddAddition);
					}
					//the item is an addition
					else {
						
					}
				}
			}
		}
	}
	
	private Order initForm(Window wndContentPanel, Integer orderId) throws Exception {
		
		Order order = new Order(orderId);
		order.get();
		
		((Intbox) wndContentPanel.query("#intObjectId")).setValue(orderId);
		Vbox boxDetails = (Vbox) wndContentPanel.query("#boxDetails");
		((Textbox) wndContentPanel.query("#txtLabel")).setValue(order.getLabel());
		((Textbox) wndContentPanel.query("#txtCounters")).setValue(order.getCountersStr());
		
		Combobox cmbCustomer = (Combobox) wndContentPanel.query("#cmbCustomer");
		
		//initializing for order management
		if (cmbCustomer != null) {			
			
			try {
				cmbCustomer.setText(order.getCustomerStr());
				ZKUtilitity.selectComboitemByLabel(cmbCustomer, order.getCustomerStr());
				
				((Textbox) wndContentPanel.query("#wndOrderItems").query("#wndTicket").query("#txtTicket")).
					setValue(Translator.translate(order.retrieveTicket(new BigDecimal(0), new BigDecimal(0)).getTextToPrint()));
				
				//it's a delivery
				if (order.getDeliverTo() != null) {
					
					Combobox cmbDeliveryProvider = (Combobox) wndContentPanel.query("#cmbDeliveryProvider");					
					cmbDeliveryProvider.setText(order.getDeliveryBy());
					ZKUtilitity.selectComboitemByLabel(cmbDeliveryProvider, order.getDeliveryBy());
					
					//fill out the delivery address form
					Include incContactData = (Include) wndContentPanel.query("popup").query("include").query("#incContactData");
					ZKUtilitity.setValueWOValidation((Textbox) incContactData.query("#txtPhoneNumber"), order.getDeliveryContactData().getMobilePhoneNumber());
					ZKUtilitity.setValueWOValidation((Textbox) incContactData.query("#txtEmailAddress"), order.getDeliveryContactData().getEmailAddress());
					ZKUtilitity.setValueWOValidation((Textbox) incContactData.query("#txtAddress"), order.getDeliveryContactData().getAddress());
					ZKUtilitity.setValueWOValidation((Textbox) incContactData.query("#cmbPostalCode"), order.getDeliveryContactData().getPostalCode());
					ZKUtilitity.setValueWOValidation((Textbox) incContactData.query("#txtTown"), order.getDeliveryContactData().getTown());
					ZKUtilitity.setValueWOValidation((Textbox) incContactData.query("#txtCity"), order.getDeliveryContactData().getCity());
					Combobox cmbCountry = (Combobox) incContactData.query("#cmbCountry");
					ZKUtilitity.setValueWOValidation(cmbCountry, order.getDeliveryContactData().getCountryCode());
					Combobox cmbProvince = (Combobox) incContactData.query("#cmbProvince");
					CountryComboboxHelper.processCountrySelection(cmbCountry, cmbProvince);
					ZKUtilitity.setValueWOValidation(cmbProvince, order.getDeliveryContactData().getProvinceId().toString());
					Combobox cmbMunicipality = (Combobox) incContactData.query("#cmbMunicipality");
					ProvinceComboboxHelper.processProvinceSelection(cmbProvince, cmbMunicipality);
					ZKUtilitity.setValueWOValidation(cmbMunicipality, order.getDeliveryContactData().getMunicipalityId().toString());
					ZKUtilitity.setValueWOValidation((Doublebox) incContactData.query("#dblLatitude"), order.getDeliveryContactData().getLatitude());
					ZKUtilitity.setValueWOValidation((Doublebox) incContactData.query("#dblLongitude"), order.getDeliveryContactData().getLongitude());
				}
			}
			catch(Exception ex) {
				ExceptionTreatment.logAndShow(ex, 
						ex.getMessage(), 
						Labels.getLabel("messageBoxTitle.Error"),
						Messagebox.ERROR);
			}		
		}
		//order billing form doesn't have customer combo. it has textbox instead.
		else {
			((Textbox) wndContentPanel.query("#txtCustomer")).setValue(order.getCustomerStr());
		}
		
		//it's a delivery
		/* commented since this info is shown in the ticket textbox
		if (order.getDeliverTo() != null) {
			((Textbox) wndContentPanel.query("#txtDeliverTo")).setValue(order.getDeliverTo());
			((Label) wndContentPanel.query("#lblDeliverTo")).setVisible(true);
			((Textbox) wndContentPanel.query("#txtDeliverTo")).setVisible(true);
		}
		*/
		
		((Intbox) wndContentPanel.query("#intDiscountTop")).setValue(order.getDiscount());
		((Decimalbox) wndContentPanel.query("#decAmountTop")).setValue(order.getAmount());
		((Label) wndContentPanel.query("#lblCurrencyTop")).setValue(order.getCurrencyCode());
		((Intbox) wndContentPanel.query("#intDiscountBottom")).setValue(order.getDiscount());
		((Decimalbox) wndContentPanel.query("#decAmountBottom")).setValue(order.getAmount());
		((Label) wndContentPanel.query("#lblCurrencyBottom")).setValue(order.getCurrencyCode());
		((Textbox) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#txtStage")).setValue(order.getStage());
		
		if (order.getStageId() == Stage.CLOSED) {
			
			//always, allow print ticket when order is closed
			((Button) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#btnPrint")).setDisabled(false);
		}
		
		return order;
	}
	
	public static void renderOrderItems(Order order, Vbox vboxOrderItems, Boolean itsForManagement) {
		
		Integer catIx = 0;
		for (String cat : order.getCategories()) {
			if (cat != null) {
				catIx++;
				Tree treeCat = new Tree();
				treeCat.setId("treeCat_" + catIx.toString() + "_" + cat.replace(" ", ""));
				vboxOrderItems.appendChild(treeCat);
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
					//cellDesc.setId("cell" + cat + "_desc");
					rowDesc.appendChild(cellDesc);
					Treechildren chdnOic = new Treechildren();
					titemDesc.appendChild(chdnOic);
					titemDesc.setOpen(true);
					renderItems(order, cat, desc, chdnOic, itsForManagement);
				}
			}
		}
	}
	
	private void initForm(Window wndContentPanel, Integer orderId, Boolean itsForManagement) throws Exception {
		
		Order order = initForm(wndContentPanel, orderId);
		Vbox vbox = new Vbox();
		vbox.setId("vboxOrderItems");
		vbox.setHflex("1");
		renderOrderItems(order, vbox, itsForManagement);
		Div divOrderItems = (Div) wndContentPanel.query("#wndOrderItems").query("#divOrderItems");
		divOrderItems.appendChild(vbox);
	}
	
	public void initFormForManagement(Window wndContentPanel, Integer orderId) throws Exception {
		
		initForm(wndContentPanel, orderId, true);
	}
	
	public void initFormForBilling(Window wndContentPanel, Integer orderId) throws Exception {
		
		initForm(wndContentPanel, orderId, false);
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
