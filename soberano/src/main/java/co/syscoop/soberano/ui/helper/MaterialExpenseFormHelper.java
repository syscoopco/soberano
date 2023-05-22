package co.syscoop.soberano.ui.helper;

import java.math.BigDecimal;
import java.util.Date;

import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.domain.tracked.MaterialExpense;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.ShiftHasBeenClosedException;
import co.syscoop.soberano.exception.SomeFieldsContainWrongValuesException;
import co.syscoop.soberano.exception.WrongDateTimeException;

public class MaterialExpenseFormHelper extends BusinessActivityTrackedObjectFormHelper {

	@Override
	public void cleanForm(Hbox hboxDetails) {
		
		Clients.scrollIntoView(hboxDetails.query("#dateExpenseDate"));
		((Decimalbox) hboxDetails.query("#decQuantity")).setValue((BigDecimal) null);
		((Decimalbox) hboxDetails.query("#decAmount")).setValue((BigDecimal) null);
		((Textbox) hboxDetails.query("#txtReference")).setText("");
		
		Grid grd = (Grid) hboxDetails.query("#incGrid").query("grid").query("#grd");
		grd.invalidate();
		grd.renderAll();
	}
	
	@Override
	public Integer recordFromForm(Hbox hboxDetails) throws Exception {
		
		Comboitem cmbiProvider = ((Combobox) hboxDetails.query("#cmbProvider")).getSelectedItem();
		Comboitem cmbiMaterial = ((Combobox) hboxDetails.query("#cmbMaterial")).getSelectedItem();
		Comboitem cmbiUnit = ((Combobox) hboxDetails.query("#cmbUnit")).getSelectedItem();
		Comboitem cmbiCurrency = ((Combobox) hboxDetails.query("#cmbCurrency")).getSelectedItem();
		Date dateExpenseDate = ((Datebox) hboxDetails.query("#dateExpenseDate")).getValue();
		Decimalbox decQuantity = (Decimalbox) hboxDetails.query("#decQuantity");
		Decimalbox decAmount = (Decimalbox) hboxDetails.query("#decAmount");
		Textbox txtReference = (Textbox) hboxDetails.query("#txtReference");
		
		if (cmbiProvider == null ||
			cmbiMaterial == null ||
			cmbiUnit == null ||
			cmbiCurrency == null ||
			dateExpenseDate == null ||
			decQuantity.getValue() == null ||
			decAmount.getValue() == null) {
			throw new SomeFieldsContainWrongValuesException();
		}
		else {
			Integer qryResult = (new MaterialExpense(dateExpenseDate,
										((DomainObject) cmbiProvider.getValue()).getId(),
										((DomainObject) cmbiMaterial.getValue()).getId(),
										decQuantity.getValue(),
										cmbiUnit.getValue(),
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
