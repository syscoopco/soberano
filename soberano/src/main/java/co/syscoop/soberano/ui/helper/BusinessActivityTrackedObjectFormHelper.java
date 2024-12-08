package co.syscoop.soberano.ui.helper;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Box;
import co.syscoop.soberano.domain.tracked.BusinessActivityTrackedObject;
import co.syscoop.soberano.renderers.ActionRequested;

public abstract class BusinessActivityTrackedObjectFormHelper {
	
	private Integer newObjectId = 0;
	
	protected ActionRequested requestedAction = ActionRequested.NONE;
	
	private BusinessActivityTrackedObject trackedObject = null;

	public abstract Integer recordFromForm(Box boxDetails) throws Exception;
	
	public abstract Integer cancelFromForm(Box boxDetails) throws Exception;
	
	public abstract Integer closeFromForm(Box boxDetails) throws Exception;
	
	public abstract Integer billFromForm(Box boxDetails);
	
	public abstract Integer makeFromForm(Component boxDetails) throws Exception;

	public abstract void cleanForm(Box boxDetails);

	public BusinessActivityTrackedObject getTrackedObject() {
		return trackedObject;
	}

	public void setTrackedObject(BusinessActivityTrackedObject trackedObject) {
		this.trackedObject = trackedObject;
	}

	public Integer getNewObjectId() {
		return newObjectId;
	}

	public void setNewObjectId(Integer newObjectId) {
		this.newObjectId = newObjectId;
	}
}
