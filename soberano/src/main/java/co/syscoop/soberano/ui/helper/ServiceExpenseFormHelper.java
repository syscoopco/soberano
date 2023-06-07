package co.syscoop.soberano.ui.helper;

import java.math.BigDecimal;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Box;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.domain.tracked.ServiceExpense;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.ShiftHasBeenClosedException;
import co.syscoop.soberano.exception.SomeFieldsContainWrongValuesException;
import co.syscoop.soberano.exception.WrongDateTimeException;
import co.syscoop.soberano.models.ServiceExpensesGridModel;

public class ServiceExpenseFormHelper extends BusinessActivityTrackedObjectFormHelper {

	@Override
	public void cleanForm(Box boxDetails) {
		
		Clients.scrollIntoView(boxDetails.query("#dateExpenseDate"));
		((Decimalbox) boxDetails.query("#decAmount")).setValue((BigDecimal) null);
		((Textbox) boxDetails.query("#txtAmountExpression")).setValue("");
		((Textbox) boxDetails.query("#txtReference")).setText("");
		((Grid) boxDetails.getParent().getParent().getParent().query("center").query("window").query("grid")).setModel(new ServiceExpensesGridModel());
	}
	
	@Override
	public Integer recordFromForm(Box boxDetails) throws Exception {
		
		Comboitem cmbiProvider = ((Combobox) boxDetails.query("#cmbProvider")).getSelectedItem();
		Comboitem cmbiService = ((Combobox) boxDetails.query("#cmbService")).getSelectedItem();
		Comboitem cmbiCurrency = ((Combobox) boxDetails.query("#cmbCurrency")).getSelectedItem();
		Datebox dateExpenseDate = ((Datebox) boxDetails.query("#dateExpenseDate"));
		Decimalbox decAmount = (Decimalbox) boxDetails.query("#decAmount");
		Textbox txtReference = (Textbox) boxDetails.query("#txtReference");
		
		if (cmbiProvider == null ||
			cmbiService == null ||
			cmbiCurrency == null ||
			dateExpenseDate.getValue() == null ||
			decAmount.getValue() == null) {
			throw new SomeFieldsContainWrongValuesException();
		}
		else {
			Integer qryResult = (new ServiceExpense(dateExpenseDate.getValue(),
										((DomainObject) cmbiProvider.getValue()).getId(),
										((DomainObject) cmbiService.getValue()).getId(),
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
}
