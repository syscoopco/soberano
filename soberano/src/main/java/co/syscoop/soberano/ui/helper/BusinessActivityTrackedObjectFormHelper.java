package co.syscoop.soberano.ui.helper;

import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import co.syscoop.soberano.domain.tracked.BusinessActivityTrackedObject;
import co.syscoop.soberano.models.MaterialExpensesGridModel;

public abstract class BusinessActivityTrackedObjectFormHelper {
	
	private BusinessActivityTrackedObject trackedObject = null;

	public abstract Integer recordFromForm(Hbox hboxDetails) throws Exception;

	public void cleanForm(Hbox hboxDetails) {
		((Grid) hboxDetails.query("#incGrid").query("grid").query("#grd")).setModel(new MaterialExpensesGridModel());
	};

	public BusinessActivityTrackedObject getTrackedObject() {
		return trackedObject;
	}

	public void setTrackedObject(BusinessActivityTrackedObject trackedObject) {
		this.trackedObject = trackedObject;
	}
}
