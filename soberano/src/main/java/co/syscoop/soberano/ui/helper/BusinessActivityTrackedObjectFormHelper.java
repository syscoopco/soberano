package co.syscoop.soberano.ui.helper;

import org.zkoss.zul.Hbox;
import co.syscoop.soberano.domain.tracked.BusinessActivityTrackedObject;

public abstract class BusinessActivityTrackedObjectFormHelper {
	
	private BusinessActivityTrackedObject trackedObject = null;

	public abstract Integer recordFromForm(Hbox hboxInputForm) throws Exception;

	public abstract void cleanForm(Hbox hboxInputForm);

	public BusinessActivityTrackedObject getTrackedObject() {
		return trackedObject;
	}

	public void setTrackedObject(BusinessActivityTrackedObject trackedObject) {
		this.trackedObject = trackedObject;
	}
}
