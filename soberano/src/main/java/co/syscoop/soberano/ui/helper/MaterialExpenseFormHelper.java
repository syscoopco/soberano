package co.syscoop.soberano.ui.helper;

import java.math.BigDecimal;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Box;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.domain.tracked.MaterialExpense;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.ShiftHasBeenClosedException;
import co.syscoop.soberano.exception.SomeFieldsContainWrongValuesException;
import co.syscoop.soberano.exception.WrongDateTimeException;
import co.syscoop.soberano.models.MaterialExpensesGridModel;

public class MaterialExpenseFormHelper extends BusinessActivityTrackedObjectFormHelper {

	@Override
	public void cleanForm(Box boxDetails) {
		
		Clients.scrollIntoView(boxDetails.query("#dateExpenseDate"));
		((Decimalbox) boxDetails.query("#decQuantity")).setValue((BigDecimal) null);
		((Textbox) boxDetails.query("#txtQuantityExpression")).setValue("");
		((Decimalbox) boxDetails.query("#decAmount")).setValue((BigDecimal) null);
		((Textbox) boxDetails.query("#txtAmountExpression")).setValue("");
		((Textbox) boxDetails.query("#txtReference")).setText("");
		Grid grd = (Grid) boxDetails.getParent().getParent().getParent().query("center").query("window").query("grid");
		grd.setModel(new MaterialExpensesGridModel());
	}
	
	@Override
	public Integer recordFromForm(Box boxDetails) throws Exception {
		
		Comboitem cmbiProvider = ((Combobox) boxDetails.query("#cmbProvider")).getSelectedItem();
		Comboitem cmbiMaterial = ((Combobox) boxDetails.query("#cmbMaterial")).getSelectedItem();
		Comboitem cmbiUnit = ((Combobox) boxDetails.query("#cmbUnit")).getSelectedItem();
		Comboitem cmbiCurrency = ((Combobox) boxDetails.query("#cmbCurrency")).getSelectedItem();
		Datebox dateExpenseDate = ((Datebox) boxDetails.query("#dateExpenseDate"));
		Decimalbox decQuantity = (Decimalbox) boxDetails.query("#decQuantity");
		Decimalbox decAmount = (Decimalbox) boxDetails.query("#decAmount");
		Textbox txtReference = (Textbox) boxDetails.query("#txtReference");
		
		if (cmbiProvider == null ||
			cmbiMaterial == null ||
			cmbiUnit == null ||
			cmbiCurrency == null ||
			dateExpenseDate.getValue() == null ||
			decQuantity.getValue() == null ||
			decAmount.getValue() == null) {
			throw new SomeFieldsContainWrongValuesException();
		}
		else {
			Integer qryResult = (new MaterialExpense(dateExpenseDate.getValue(),
										((DomainObject) cmbiProvider.getValue()).getId(),
										((DomainObject) cmbiMaterial.getValue()).getId(),
										decQuantity.getValue(),
										Integer.parseInt(cmbiUnit.getValue()),
										decAmount.getValue(),
										((DomainObject) cmbiCurrency.getValue()).getId(),
										txtReference.getText())).record();
			if (qryResult == -2) {
				throw new WrongDateTimeException();
			}
			else if (qryResult == -3) {
				throw new ShiftHasBeenClosedException();
			}
			else if (qryResult == -4) {
				throw new SomeFieldsContainWrongValuesException();
			}
			return qryResult;
		}
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
	public Integer makeFromForm(Component boxDetails) {
		return null;
	}
}
