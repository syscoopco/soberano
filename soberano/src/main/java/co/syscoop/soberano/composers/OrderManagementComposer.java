package co.syscoop.soberano.composers;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import co.syscoop.soberano.domain.tracked.Unit;
import co.syscoop.soberano.domain.tracked.Order;
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
import co.syscoop.soberano.vocabulary.Translator;

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
	
	@Wire
	private Button btnMakeFromAdditionsWindow;
	
	private void checkRuns() {
		
		if (decQuantity.getValue().compareTo(new BigDecimal(0)) <= 0) {
			decQuantity.setValue(new BigDecimal(0));
			txtQuantityExpression.setValue("0");
			btnDec.setDisabled(true);
			btnMake.setDisabled(true);
			btnMakeFromAdditionsWindow.setDisabled(true);
		}
		else {
			btnDec.setDisabled(false);
			btnMake.setDisabled(false);
			btnMakeFromAdditionsWindow.setDisabled(false);
		}
	}
	
	private void cleanOrderItemInputForm() {
		decQuantity.setValue(new BigDecimal(0));
		txtQuantityExpression.setValue("0");
		txtSpecialInstructions.setValue("");
	}
	
	private void processItemToOrderSelection() throws SQLException {
		
		btnMake.setDisabled(true);
		btnMakeFromAdditionsWindow.setDisabled(true);
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
					Integer orderId = intObjectId.getValue();
					OrderFormHelper.updateForm(orderId, boxDetails);
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
	
	@Listen("onClick = button#btnMakeFromAdditionsWindow")
    public void btnMakeFromAdditionsWindow_onClick() throws SoberanoException {
		
		try {
			btnMakeFromAdditionsWindow.setDisabled(true);
			if (decQuantity.getValue().compareTo(new BigDecimal(0)) > 0) {	
				Component boxDetails = btnMakeFromAdditionsWindow.query("#boxDetails");
				Decimalbox decQuantity = (Decimalbox) boxDetails.query("#decQuantity");
				Decimalbox decOneRunQuantity = (Decimalbox) boxDetails.query("#decOneRunQuantity");
				Textbox txtSpecialInstructions = (Textbox) boxDetails.query("#txtSpecialInstructions");
				BigDecimal runs = decQuantity.getValue().divide(decOneRunQuantity.getValue());
				Combobox cmbItemToOrder = (Combobox) boxDetails.query("#cmbItemToOrder");
				Comboitem cmbiItemToOrder = cmbItemToOrder.getSelectedItem() == null ? (Comboitem) cmbItemToOrder.getChildren().get(0)  : cmbItemToOrder.getSelectedItem();
				if (cmbiItemToOrder == null || runs.compareTo(new BigDecimal(0)) <= 0) {
					throw new SomeFieldsContainWrongValuesException();
				}
				else {					
					ArrayList<Integer> additions = new ArrayList<Integer>();
					for (Component comp : boxDetails.query("#spanAdditions").getChildren()) {
						if ((Boolean) comp.getAttribute("checked")) {
							additions.add((Integer) comp.getAttribute("productId"));
							
							((Button) comp).setWidth("100px");
							((Button) comp).setHeight("100px");
							((Button) comp).setStyle("margin-left: 3px; margin-top: 3px;");
							comp.setAttribute("checked", false);
						}
					}					
					int result = new Order((Integer) btnMakeFromAdditionsWindow.getAttribute("orderId"))
							.make(((DomainObject) cmbiItemToOrder.getValue()).getId(),
									txtSpecialInstructions.getValue(),
									runs,
									additions);
					if (result == -1) {
						btnMakeFromAdditionsWindow.setDisabled(false);
						throw new NotEnoughRightsException();						
					}
					else {
						Order order = new Order((Integer) btnMakeFromAdditionsWindow.getAttribute("orderId"));
						((Textbox) boxDetails.query("#txtTicket")).setValue(Translator.translate(order.getReport()));						
						btnDec.setDisabled(true);
						cmbItemToOrder.setSelectedItem(null);
						cmbItemToOrder.setText("");
						txtQuantityExpression.setValue("0");
						decQuantity.setValue(new BigDecimal(0));
					}
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