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
public class ProcessFormComposer extends SelectorComposer {
	
	@Wire
	private Combobox cmbInput;
	
	@Wire
	private Combobox cmbInputUnit;
	
	@Wire
	private Decimalbox decInputQuantity;
	
	@Wire
	private Textbox txtInputQuantityExpression;
	
	@Wire
	private Combobox cmbOutput;
	
	@Wire
	private Combobox cmbOutputUnit;
	
	@Wire
	private Decimalbox decOutputQuantity;
	
	@Wire
	private Textbox txtOutputQuantityExpression;
	
	private void processInputMaterialSelection() throws SQLException {
		
		cmbInputUnit.getChildren().clear();
		if (cmbInput.getSelectedItem() != null) {
			String inventoryItemCode = ((DomainObject) cmbInput.getSelectedItem().getValue()).getStringId();
			for (DomainObject unit : new Unit().getAllForInventoryItem(inventoryItemCode)) {
				Comboitem newItem = new Comboitem(unit.getName());
				newItem.setValue(unit.getId().toString());
				cmbInputUnit.appendChild(newItem);
			}
			cmbInputUnit.setReadonly(false);
			cmbInputUnit.setDisabled(false);
		}
		else {
			cmbInputUnit.setText("");
			cmbInputUnit.setReadonly(true);
			cmbInputUnit.setDisabled(true);
		}	
	}
	
	private void processOutputItemSelection() throws SQLException {
		
		cmbOutputUnit.getChildren().clear();
		if (cmbOutput.getSelectedItem() != null) {
			String inventoryItemCode = ((DomainObject) cmbOutput.getSelectedItem().getValue()).getStringId();
			for (DomainObject unit : new Unit().getAllForInventoryItem(inventoryItemCode)) {
				Comboitem newItem = new Comboitem(unit.getName());
				newItem.setValue(unit.getId().toString());
				cmbOutputUnit.appendChild(newItem);
			}
			cmbOutputUnit.setReadonly(false);
			cmbOutputUnit.setDisabled(false);
		}
		else {
			cmbOutputUnit.setText("");
			cmbOutputUnit.setReadonly(true);
			cmbOutputUnit.setDisabled(true);
		}	
	}
	
	@Listen("onChange = combobox#cmbInput")
    public void cmbInput_onChange() throws SQLException {
		processInputMaterialSelection();
	}
	
	/*
	 * Needed for testing. 
	 * combo_onChange event isn't triggered under testing.
	 */
	@Listen("onClick = combobox#cmbInput")
    public void cmbInput_onClick() throws SQLException {
		processInputMaterialSelection();
	}
	
	@Listen("onChange = combobox#cmbOutput")
    public void cmbOutput_onChange() throws SQLException {
		processOutputItemSelection();
	}
	
	/*
	 * Needed for testing. 
	 * combo_onChange event isn't triggered under testing.
	 */
	@Listen("onClick = combobox#cmbOutput")
    public void cmbOutput_onClick() throws SQLException {
		processOutputItemSelection();
	}
	
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
    }
	
	@Listen("onChange = textbox#txtInputQuantityExpression")
    public void txtInputQuantityExpression_onChange() throws Throwable {
		
		try {
			Double evalResult = Double.parseDouble(Utils.evaluate(txtInputQuantityExpression.getValue()));
			decInputQuantity.setValue(new BigDecimal(evalResult));
			txtInputQuantityExpression.setValue(decInputQuantity.getValue().toString());
		}
		catch(Exception ex) {
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.validation.typeAValidArithmeticExpression"), 
										Labels.getLabel("messageBoxTitle.Validation"),
										Messagebox.EXCLAMATION);
		}
	}
	
	@Listen("onChange = textbox#txtOutputQuantityExpression")
    public void txtOutputQuantityExpression_onChange() throws Throwable {
		
		try {
			Double evalResult = Double.parseDouble(Utils.evaluate(txtOutputQuantityExpression.getValue()));
			decOutputQuantity.setValue(new BigDecimal(evalResult));
			txtOutputQuantityExpression.setValue(decOutputQuantity.getValue().toString());
		}
		catch(Exception ex) {
			ExceptionTreatment.logAndShow(ex, 
										Labels.getLabel("message.validation.typeAValidArithmeticExpression"), 
										Labels.getLabel("messageBoxTitle.Validation"),
										Messagebox.EXCLAMATION);
		}
	}
}