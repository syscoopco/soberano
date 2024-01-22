package co.syscoop.soberano.ui.helper;

import java.util.ArrayList;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Box;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Checkbox;

import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.ConfirmationRequiredException;
import co.syscoop.soberano.exception.OnlyOneOrderPerCounterIsPermittedException;
import co.syscoop.soberano.exception.SomeFieldsContainWrongValuesException;
import co.syscoop.soberano.renderers.ActionRequested;
import co.syscoop.soberano.vocabulary.Labels;

public class RecordOrderFormHelper extends BusinessActivityTrackedObjectFormHelper {
	
	private Integer newOrderId = 0;
	private ArrayList<String> counters = new ArrayList<String>();
		
	private void fillCounters(Box boxDetails) {
		counters.clear();
		Grid grdCounters = (Grid) boxDetails.query("#grdCounters");
		if (grdCounters.getRows().getChildren().size() > 0) {			
			for (Component item : grdCounters.getRows().getChildren()) {
				if (((Checkbox) item.query("checkbox")).isChecked()) {
					counters.add(((Label) item.query("label")).getValue());
				}
			}
		}
	}

	@Override
	public Integer recordFromForm(Box boxDetails) throws Exception {
		
		if (requestedAction != null && requestedAction.equals(ActionRequested.RECORD)) {
			Comboitem cmbiCustomer = ((Combobox) boxDetails.query("#cmbCustomer")).getSelectedItem();
			fillCounters(boxDetails);
			if (counters.size() == 0) {
				throw new SomeFieldsContainWrongValuesException();
			}
			else {
				String orderLabel = ((Textbox) boxDetails.query("#txtLabel")).getValue();
				newOrderId = (new Order(orderLabel,
										counters, 
										cmbiCustomer != null ? ((DomainObject) cmbiCustomer.getValue()).getId() : null).record());
				if (newOrderId == -4) {
					throw new SomeFieldsContainWrongValuesException();
				}
				else if (newOrderId == -2) {
					throw new OnlyOneOrderPerCounterIsPermittedException();
				}
				requestedAction = ActionRequested.NONE;
				((Button) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#btnRecord")).setLabel(Labels.getLabel("caption.action.record"));
				return newOrderId;
			}
		}
		else {
			requestedAction = ActionRequested.RECORD;
			((Button) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#btnRecord")).setLabel(Labels.getLabel("caption.action.confirm"));
			throw new ConfirmationRequiredException();
		}
	}

	@Override
	public void cleanForm(Box boxDetails) {
		Executions.sendRedirect("/order.zul?id=" + newOrderId);
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
	public Integer makeFromForm(Box boxDetails) {
		return null;
	}
}
