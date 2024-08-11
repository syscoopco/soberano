package co.syscoop.soberano.composers;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import co.syscoop.soberano.domain.tracked.Unit;
import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.domain.tracked.ProcessRun;
import co.syscoop.soberano.domain.tracked.ProcessRunOutputAllocation;
import co.syscoop.soberano.domain.tracked.Product;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.exception.SomeFieldsContainWrongValuesException;
import co.syscoop.soberano.ui.helper.OrderFormHelper;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.util.Utils;
import co.syscoop.soberano.vocabulary.Labels;

@SuppressWarnings({ "serial" })
public class OrderManagementComposer extends OrderComposer {
	
	@Wire
	private Button btnDec;
	
	@Wire
	private Button btnMake;
	
	@Wire
	private Combobox cmbItemToOrder;
	
	@Wire
	private Textbox txtQuantityExpression;
	
	@Wire
	private Decimalbox decQuantity;
	
	@Wire 
	private Textbox txtSpecialInstructions;
	
	@Wire
	private Combobox cmbUnit;
	
	@Wire
	private Decimalbox decOneRunQuantity;
	
	private void checkRuns() {
		
		if (decQuantity.getValue().compareTo(new BigDecimal(0)) <= 0) {
			decQuantity.setValue(new BigDecimal(0));
			txtQuantityExpression.setValue("0");
			btnDec.setDisabled(true);
			btnMake.setDisabled(true);
		}
		else {
			btnDec.setDisabled(false);
			btnMake.setDisabled(false);
		}
	}
	
	private void cleanOrderItemInputForm() {
		decQuantity.setValue(new BigDecimal(0));
		txtQuantityExpression.setValue("0");
		txtSpecialInstructions.setValue("");
	}
	
	private void processItemToOrderSelection() throws SQLException {
		
		btnMake.setDisabled(true);
		cleanOrderItemInputForm();
		cmbUnit.getChildren().clear();
		if (cmbItemToOrder.getSelectedItem() != null) {
			String productId = ((DomainObject) cmbItemToOrder.getSelectedItem().getValue()).getStringId();
			for (DomainObject unit : new Unit().getAllForInventoryItem(productId)) {
				Comboitem newItem = new Comboitem(unit.getName());
				newItem.setValue(unit.getId().toString());
				if (((Product) cmbItemToOrder.getSelectedItem().getValue()).getUnit() == unit.getId()) {
					cmbUnit.appendChild(newItem);
					cmbUnit.setSelectedItem(newItem);
					decOneRunQuantity.setValue(((Product) cmbItemToOrder.getSelectedItem().getValue()).getOneRunQuantity());
					break;
				}
			}
		}
		else {
			cmbUnit.setText("");
			cmbUnit.setDisabled(true);
		}
	}
	
	@Listen("onChange = combobox#cmbItemToOrder")
    public void cmbItemToOrder_onChange() throws SQLException {
		processItemToOrderSelection();
	}
	
	/*
	 * Needed for testing. 
	 * combo_onChange event isn't triggered under testing.
	 */
	@Listen("onClick = combobox#cmbItemToOrder")
    public void cmbItemToOrder_onClick() throws SQLException {
		if (SpringUtility.underTesting()) processItemToOrderSelection();
	}
	
	@Listen("onChange = textbox#txtQuantityExpression")
    public void txtQuantityExpression_onChange() throws Throwable {
		
		try {
			Double evalResult = Double.parseDouble(Utils.evaluate(txtQuantityExpression.getValue()));
			decQuantity.setValue(new BigDecimal(evalResult));
			txtQuantityExpression.setValue(decQuantity.getValue().toString());
			checkRuns();
		}
		catch(Exception ex) {
			decQuantity.setValue(new BigDecimal(0));
			txtQuantityExpression.setValue("0");
			btnDec.setDisabled(true);
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.validation.typeAValidArithmeticExpression"), 
										Labels.getLabel("messageBoxTitle.Validation"),
										Messagebox.EXCLAMATION);
		}
	}
	
	@Listen("onChange = decbox#decRuns")
    public void decRuns_onChange() {
		
		checkRuns();
    }
	
	@Listen("onClick = button#btnInc")
    public void btnInc_onClick() throws Throwable {
		
		try {
			BigDecimal currentQuantity = decQuantity.getValue();
			txtQuantityExpression.setValue(currentQuantity.add(decOneRunQuantity.getValue()).toString());
			txtQuantityExpression_onChange();
			checkRuns();
		}
		catch(Exception ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.validation.someFieldsContainWrongValues"), 
					Labels.getLabel("messageBoxTitle.Validation"),
					Messagebox.EXCLAMATION);
		}
    }
	
	@Listen("onClick = button#btnDec")
    public void btnDec_onClick() throws Throwable {
		
		try {
			BigDecimal currentQuantity = decQuantity.getValue();
			txtQuantityExpression.setValue(currentQuantity.subtract(decOneRunQuantity.getValue()).toString());
			txtQuantityExpression_onChange();
			checkRuns();
		}
		catch(Exception ex) {
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.validation.someFieldsContainWrongValues"), 
					Labels.getLabel("messageBoxTitle.Validation"),
					Messagebox.EXCLAMATION);
		}
    }
	
	@SuppressWarnings("unchecked")
	@Listen("onClick = button#btnMake")
    public void btnMake_onClick() throws SoberanoException {
		
		try {
			btnMake.setDisabled(true);
			if (decQuantity.getValue().compareTo(new BigDecimal(0)) > 0) {	
				Vbox boxDetails = (Vbox) btnMake.query("#boxDetails");
				OrderFormHelper orderFormHelper = new OrderFormHelper();
				int result = orderFormHelper.makeFromForm(boxDetails);
				if (result == -1) {
					btnMake.setDisabled(false);
					throw new NotEnoughRightsException();						
				}
				else {
					//there's not ZK web application context under testing
					if (!SpringUtility.underTesting()) {
						
						//print order's process run allocations
						try {
							Integer orderId = intObjectId.getValue();
							for (Object object : (new ProcessRun()).getOrderProcessRunAllocations(orderId)) {
								try{
									Integer allocationId = ((ProcessRunOutputAllocation) object).getId();
									Integer productionLineId = ((ProcessRunOutputAllocation) object).getProductionLineId();
									
									//print allocation only in case it wasn't printed yet
									HashMap<Integer, HashMap<Integer, Boolean>> thisOrderPrintedAllocations = 
											((HashMap<Integer, HashMap<Integer, HashMap<Integer, Boolean>>>) Executions.
																								getCurrent().
																								getDesktop().
																								getWebApp().
																								getAttribute("printed_allocations")).
																									get(orderId);
									
									//order hasn't been collected. it's still open.
									if (thisOrderPrintedAllocations != null) {										
										HashMap<Integer, Boolean> productionLineAllocations = thisOrderPrintedAllocations.get(productionLineId);
										
										if (productionLineAllocations == null) {
											thisOrderPrintedAllocations.put(productionLineId, new HashMap<Integer, Boolean>());
											productionLineAllocations = thisOrderPrintedAllocations.get(productionLineId);
										}
										
										Boolean allocationWasPrinted = 
												productionLineAllocations.get(allocationId) == null ? false : productionLineAllocations.get(allocationId);
										
										//allocation was not printed yet
										if (!allocationWasPrinted) {
											/* TODO: global setting to enable this code to print allocations: one by one on making request. 
											Printer.printReport((ProcessRunOutputAllocation) object, 
													SpringUtility.getPath(this.getClass().getClassLoader().getResource("").getPath()) + 
													"records/production_lines/" + 
													"ALLOCATION_" + allocationId + ".pdf",
													"ALLOCATION_",
													true,
													true,
													true);
											productionLineAllocations.put(allocationId, true);
											*/											
										}																			
									}
								}
								catch(Exception ex) {
									throw ex;
								}
							}
						}
						catch(Exception ex) {
							throw ex;
						}
					}				
					Order order = new Order(intObjectId.getValue());
					order.get();
					Window wndContentPanel = (Window) boxDetails.query("#wndContentPanel");
					OrderFormHelper.updateAmountAndTicket(order, wndContentPanel);
					Vbox vboxOrderItems = (Vbox) boxDetails.query("#wndOrderItems").query("#divOrderItems").query("#vboxOrderItems");
					if (vboxOrderItems == null) {
						vboxOrderItems = new Vbox();
						vboxOrderItems.setId("vboxOrderItems");
						vboxOrderItems.setHflex("1");
						Div divOrderItems = (Div) wndContentPanel.query("#wndOrderItems").query("#divOrderItems");
						divOrderItems.appendChild(vboxOrderItems);
					}
					vboxOrderItems.getChildren().clear();
					OrderFormHelper.renderOrderItems(order, 
													vboxOrderItems, 
													true);
					btnDec.setDisabled(true);
					cmbItemToOrder.setSelectedItem(null);
					txtQuantityExpression.setValue("0");
					decQuantity.setValue(new BigDecimal(0));
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
			btnMake.setDisabled(false);
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.permissions.NotEnoughRights"), 
										Labels.getLabel("messageBoxTitle.Warning"),
										Messagebox.EXCLAMATION);
		}
		catch(Exception ex)	{
			btnMake.setDisabled(false);
			ExceptionTreatment.logAndShow(ex, 
										ex.getMessage(), 
										Labels.getLabel("messageBoxTitle.Error"),
										Messagebox.ERROR);
		}
    }
}