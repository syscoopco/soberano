package co.syscoop.soberano.ui.helper;

import java.util.ArrayList;
import java.util.HashMap;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Box;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Checkbox;

import co.syscoop.soberano.composers.ItemToOrderComboboxComposer;
import co.syscoop.soberano.domain.tracked.Order;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.FirstOrderRequiresCashOperationException;
import co.syscoop.soberano.exception.OnlyOneOrderPerCounterIsPermittedException;
import co.syscoop.soberano.exception.SomeFieldsContainWrongValuesException;
import co.syscoop.soberano.renderers.ActionRequested;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.util.ui.ZKUtility;
import co.syscoop.soberano.vocabulary.Labels;

public class RecordOrderFormHelper extends BusinessActivityTrackedObjectFormHelper {
	
	private Integer newOrderId = 0;
	private ArrayList<String> counters = new ArrayList<String>();
		
	private void fillCounters(Box boxDetails) {
		counters.clear();
		Grid grdCounters = (Grid) boxDetails.query("#grdCounters");
		if (grdCounters.getRows().getChildren().size() > 0) {			
			if (grdCounters.getRows().getChildren().size() == 1) 
				((Checkbox) grdCounters.getRows().getChildren().get(0).query("checkbox")).setChecked(true);
			for (Component item : grdCounters.getRows().getChildren()) {
				if (((Checkbox) item.query("checkbox")).isChecked()) {
					counters.add(((Label) item.query("label")).getValue());
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer recordFromForm(Box boxDetails) throws Exception {
				
		Comboitem cmbiCustomer = ((Combobox) boxDetails.query("#cmbCustomer")).getSelectedItem();
		Comboitem cmbiProvider = ((Combobox) boxDetails.query("#cmbDeliveryProvider")).getSelectedItem();
		fillCounters(boxDetails);
		if (counters.size() == 0) {
			throw new SomeFieldsContainWrongValuesException();
		}
		else {
			String orderLabel = ((Textbox) boxDetails.query("#txtLabel")).getValue();
			newOrderId = (new Order(orderLabel,
									counters, 
									cmbiCustomer != null ? ((DomainObject) cmbiCustomer.getValue()).getId() : null,
									cmbiProvider != null ? ((DomainObject) cmbiProvider.getValue()).getId() : null).record());
			if (newOrderId == -3) {
				throw new FirstOrderRequiresCashOperationException();
			}
			else if (newOrderId == -2) {
				throw new OnlyOneOrderPerCounterIsPermittedException();
			}
			requestedAction = ActionRequested.NONE;
			((Button) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#btnRecord")).setLabel(Labels.getLabel("caption.action.record"));
			
			//there's not ZK web application context under testing
			if (!SpringUtility.underTesting()) {
				
				//initialize new order's printed allocations store
				((HashMap<Integer, HashMap<Integer, HashMap<Integer, Boolean>>>) Executions.
						getCurrent().
						getDesktop().
						getWebApp().
						getAttribute("printed_allocations")).put(newOrderId, 
																new HashMap<Integer, HashMap<Integer, Boolean>>());
			}
			
			return newOrderId;
		}

//		if required confirmation for order recording, replace the above code block with the following commented one
//
//		if (requestedAction != null && requestedAction.equals(ActionRequested.RECORD)) {
//			Comboitem cmbiCustomer = ((Combobox) boxDetails.query("#cmbCustomer")).getSelectedItem();
//			fillCounters(boxDetails);
//			if (counters.size() == 0) {
//				throw new SomeFieldsContainWrongValuesException();
//			}
//			else {
//				String orderLabel = ((Textbox) boxDetails.query("#txtLabel")).getValue();
//				newOrderId = (new Order(orderLabel,
//										counters, 
//										cmbiCustomer != null ? ((DomainObject) cmbiCustomer.getValue()).getId() : null).record());
//				if (newOrderId == -4) {
//					throw new SomeFieldsContainWrongValuesException();
//				}
//				else if (newOrderId == -2) {
//					throw new OnlyOneOrderPerCounterIsPermittedException();
//				}
//				requestedAction = ActionRequested.NONE;
//				((Button) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#btnRecord")).setLabel(Labels.getLabel("caption.action.record"));
//				
//				//there's not ZK web application context under testing
//				if (!SpringUtility.underTesting()) {
//					
//					//initialize new order's printed allocations store
//					((HashMap<Integer, HashMap<Integer, HashMap<Integer, Boolean>>>) Executions.
//							getCurrent().
//							getDesktop().
//							getWebApp().
//							getAttribute("printed_allocations")).put(newOrderId, 
//																	new HashMap<Integer, HashMap<Integer, Boolean>>());
//				}
//				
//				return newOrderId;
//			}
//		}
//		else {
//			requestedAction = ActionRequested.RECORD;
//			((Button) boxDetails.getParent().getParent().query("#incSouth").query("#hboxDecisionButtons").query("#btnRecord")).setLabel(Labels.getLabel("caption.action.confirm"));
//			throw new ConfirmationRequiredException();
//		}
	}

	@Override
	public void cleanForm(Box boxDetails) {
		
		String fastModeParamStr = ZKUtility.parseURLQueryStringForParam("fast");
		if (!fastModeParamStr.isEmpty()) {
			Boolean fastMode = Boolean.parseBoolean(fastModeParamStr);
			if (!fastMode) {
				Executions.sendRedirect("/order.zul?id=" + newOrderId);
			}
			else {
				ItemToOrderComboboxComposer.openFastOrderingWindow((Window) boxDetails.query("#wndContentPanel"), newOrderId);
			}
		}
		else {
			Executions.sendRedirect("/order.zul?id=" + newOrderId);
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
