package co.syscoop.soberano.composers;

import java.math.BigDecimal;
import java.sql.SQLException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.domain.tracked.Unit;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.util.ExceptionTreatment;
import co.syscoop.soberano.util.Utils;
import co.syscoop.soberano.vocabulary.Labels;

@SuppressWarnings({ "serial", "rawtypes" })
public /*abstract*/ class MaterialExpensesComposer extends SelectorComposer {
	
	@Wire
	private Combobox cmbMaterial;
	
	@Wire
	private Combobox cmbUnit;
	
	@Wire
	private Decimalbox decQuantity;
	
	@Wire
	private Textbox txtQuantityExpression;
	
	@Wire
	private Decimalbox decAmount;
	
	@Wire
	private Textbox txtAmountExpression;
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	private void processMaterialSelection() throws SQLException {
			
		cmbUnit.getChildren().clear();
		if (cmbMaterial.getSelectedItem() != null) {
			Integer materialId = ((DomainObject) cmbMaterial.getSelectedItem().getValue()).getId();
			for (DomainObject unit : new Unit().getAll(materialId)) {
				Comboitem newItem = new Comboitem(unit.getName());
				newItem.setValue(unit.getId().toString());
				cmbUnit.appendChild(newItem);
			}
			cmbUnit.setReadonly(false);
			cmbUnit.setDisabled(false);
		}
		else {
			cmbUnit.setText("");
			cmbUnit.setReadonly(true);
			cmbUnit.setDisabled(true);
		}	
	}
	
	@Listen("onChange = combobox#cmbMaterial")
    public void cmb_onChange() throws SQLException {
		processMaterialSelection();
	}
	
	/*
	 * Needed for testing. 
	 * combo_onChange event isn't triggered under testing.
	 */
	@Listen("onClick = combobox#cmbMaterial")
    public void cmb_onClick() throws SQLException {
		processMaterialSelection();
	}
	
	@Listen("onChange = textbox#txtQuantityExpression")
    public void txtQuantityExpression_onChange() throws Throwable {
		
		try {
			Double evalResult = Double.parseDouble(Utils.evaluate(txtQuantityExpression.getValue()));
			decQuantity.setValue(new BigDecimal(evalResult));
			txtQuantityExpression.setValue(decQuantity.getValue().toString());
		}
		catch(Exception ex) {
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.validation.typeAValidArithmeticExpression"), 
										Labels.getLabel("messageBoxTitle.Validation"),
										Messagebox.EXCLAMATION);
		}
	}
	
	@Listen("onChange = textbox#txtAmountExpression")
    public void txtAmountExpression_onChange() throws Throwable {
		
		try {
			Double evalResult = Double.parseDouble(Utils.evaluate(txtAmountExpression.getValue()));
			decAmount.setValue(new BigDecimal(evalResult));
			txtAmountExpression.setValue(decAmount.getValue().toString());
		}
		catch(Exception ex) {
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.validation.typeAValidArithmeticExpression"), 
										Labels.getLabel("messageBoxTitle.Validation"),
										Messagebox.EXCLAMATION);
		}
	}
}