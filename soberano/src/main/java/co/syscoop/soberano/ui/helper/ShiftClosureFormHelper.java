package co.syscoop.soberano.ui.helper;

import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Box;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.domain.tracked.ShiftClosure;
import co.syscoop.soberano.exception.ConfirmationRequiredException;
import co.syscoop.soberano.exception.ShiftHasBeenClosedException;
import co.syscoop.soberano.renderers.ActionRequested;
import co.syscoop.soberano.vocabulary.Labels;

public class ShiftClosureFormHelper extends BusinessActivityTrackedObjectFormHelper {
	
	@Override
	public void cleanForm(Box boxDetails) {
		
		Clients.scrollIntoView(boxDetails);
		((Textbox) boxDetails.query("#txtReport")).setText("");
		super.cleanForm(boxDetails);
	}
	
	@Override
	public Integer recordFromForm(Box boxDetails) throws Exception {
		
		Integer qryResult = 0;
		if (requestedAction != null && requestedAction.equals(ActionRequested.RECORD)) {
			qryResult = (new ShiftClosure()).record();
			if (qryResult == -3) {
				throw new ShiftHasBeenClosedException();
			}
		}
		else {
			requestedAction = ActionRequested.RECORD;
			((Button) boxDetails.query("#btnRecord")).setLabel(Labels.getLabel("caption.action.confirm"));
			((Textbox) boxDetails.query("#txtReport")).setText((new ShiftClosure()).getReport());
			throw new ConfirmationRequiredException();
		}
		return qryResult;	
	}
}
