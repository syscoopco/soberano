package co.syscoop.soberano.ui.helper;

import org.zkoss.zul.Box;
import org.zkoss.zul.Grid;
import co.syscoop.soberano.domain.tracked.BusinessActivityTrackedObject;
import co.syscoop.soberano.models.MaterialExpensesGridModel;
import co.syscoop.soberano.renderers.ActionRequested;

public abstract class BusinessActivityTrackedObjectFormHelper {
	
	protected ActionRequested requestedAction = ActionRequested.NONE;
	
	private BusinessActivityTrackedObject trackedObject = null;

	public abstract Integer recordFromForm(Box boxDetails) throws Exception;

	public void cleanForm(Box boxDetails) {
		((Grid) boxDetails.getParent().getParent().getParent().query("center").query("window").query("grid")).setModel(new MaterialExpensesGridModel());
	};

	public BusinessActivityTrackedObject getTrackedObject() {
		return trackedObject;
	}

	public void setTrackedObject(BusinessActivityTrackedObject trackedObject) {
		this.trackedObject = trackedObject;
	}
}
