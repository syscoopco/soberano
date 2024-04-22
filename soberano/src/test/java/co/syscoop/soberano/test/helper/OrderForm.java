package co.syscoop.soberano.test.helper;

import java.util.Arrays;

import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;

public class OrderForm extends ConstrainedForm {
	
	private Textbox txtLabel;
	private Textbox txtCounters;
	private Combobox cmbCustomer;
	private Combobox cmbItemToOrder;
	private Textbox txtSpecialInstructions;
	private Decimalbox decQuantity;
	private Combobox cmbUnit;
	private Button btnMake;
	private Intbox intDiscountTop;
	private Decimalbox decAmountTop;
	private Intbox intDiscountBottom;
	private Decimalbox decAmountBottom;
	private Div divOrderItems;
	private Textbox txtStage;
	
	public OrderForm(DesktopAgent desktop,
					Textbox txtLabel,
					Textbox txtCounters,
					Combobox cmbCustomer,
					Combobox cmbItemToOrder,
					Textbox txtSpecialInstructions,
					Decimalbox decQuantity,
					Combobox cmbUnit,
					Button btnMake,
					Intbox intDiscountTop,
					Decimalbox decAmountTop,
					Intbox intDiscountBottom,
					Decimalbox decAmountBottom,
					Div divOrderItems,
					Textbox txtStage) {
		
		this.constrainedComponents = Arrays.asList("intDiscountTop", "intDiscountBottom");

		this.setDesktop(desktop);
		
		this.setTxtLabel(txtLabel);
		this.setTxtCounters(txtCounters);
		this.setCmbCustomer(cmbCustomer);
		this.setCmbItemToOrder(cmbItemToOrder);
		this.setTxtSpecialInstructions(txtSpecialInstructions);
		this.setDecQuantity(decQuantity);
		this.setCmbUnit(cmbUnit);
		this.setBtnMake(btnMake);
		this.setDecAmountTop(decAmountTop);
		this.setDecAmountBottom(decAmountBottom);
		this.setDivOrderItems(divOrderItems);
		this.setTxtStage(txtStage);

		this.setIntDiscountTop(intDiscountTop);
		this.constrainableComponents.add(intDiscountTop);
		this.constrainableComponentById.put("intDiscountTop", intDiscountTop);
		
		this.setIntDiscountBottom(intDiscountBottom);
		this.constrainableComponents.add(intDiscountBottom);
		this.constrainableComponentById.put("intDiscountBottom", intDiscountBottom);
	}

	public Combobox getCmbUnit() {
		return cmbUnit;
	}

	public void setCmbUnit(Combobox cmbUnit) {
		this.cmbUnit = cmbUnit;
	}

	public Textbox getTxtLabel() {
		return txtLabel;
	}

	public void setTxtLabel(Textbox txtLabel) {
		this.txtLabel = txtLabel;
	}

	public Textbox getTxtCounters() {
		return txtCounters;
	}

	public void setTxtCounters(Textbox txtCounters) {
		this.txtCounters = txtCounters;
	}

	public Combobox getCmbCustomer() {
		return cmbCustomer;
	}

	public void setCmbCustomer(Combobox cmbCustomer) {
		this.cmbCustomer = cmbCustomer;
	}

	public Combobox getCmbItemToOrder() {
		return cmbItemToOrder;
	}

	public void setCmbItemToOrder(Combobox cmbItemToOrder) {
		this.cmbItemToOrder = cmbItemToOrder;
	}

	public Textbox getTxtSpecialInstructions() {
		return txtSpecialInstructions;
	}

	public void setTxtSpecialInstructions(Textbox txtSpecialInstructions) {
		this.txtSpecialInstructions = txtSpecialInstructions;
	}

	public Button getBtnMake() {
		return btnMake;
	}

	public void setBtnMake(Button btnMake) {
		this.btnMake = btnMake;
	}

	public Intbox getIntDiscountTop() {
		return intDiscountTop;
	}

	public void setIntDiscountTop(Intbox intDiscountTop) {
		this.intDiscountTop = intDiscountTop;
	}

	public Decimalbox getDecAmountTop() {
		return decAmountTop;
	}

	public void setDecAmountTop(Decimalbox decAmountTop) {
		this.decAmountTop = decAmountTop;
	}

	public Intbox getIntDiscountBottom() {
		return intDiscountBottom;
	}

	public void setIntDiscountBottom(Intbox intDiscountBottom) {
		this.intDiscountBottom = intDiscountBottom;
	}

	public Decimalbox getDecAmountBottom() {
		return decAmountBottom;
	}

	public void setDecAmountBottom(Decimalbox decAmountBottom) {
		this.decAmountBottom = decAmountBottom;
	}

	public Div getDivOrderItems() {
		return divOrderItems;
	}

	public void setDivOrderItems(Div divOrderItems) {
		this.divOrderItems = divOrderItems;
	}

	public Textbox getTxtStage() {
		return txtStage;
	}

	public void setTxtStage(Textbox txtStage) {
		this.txtStage = txtStage;
	}

	public Decimalbox getDecQuantity() {
		return decQuantity;
	}

	public void setDecQuantity(Decimalbox decQuantity) {
		this.decQuantity = decQuantity;
	}
}
