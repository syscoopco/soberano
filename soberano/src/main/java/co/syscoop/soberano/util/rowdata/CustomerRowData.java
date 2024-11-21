package co.syscoop.soberano.util.rowdata;

import org.zkoss.zul.Row;

import co.syscoop.soberano.renderers.ActionRequested;

public class CustomerRowData {
	
	private String domainObjectName = "";
	private Integer domainObjectId = 0;
	private ActionRequested actionRequested = ActionRequested.NONE;
	private boolean confirmationRequested = false;
	
	public CustomerRowData() {
	}

	public String getDomainObjectName() {
		return domainObjectName;
	}

	public void setDomainObjectName(String domainObjectName) {
		this.domainObjectName = domainObjectName;
	}

	public Integer getDomainObjectId() {
		return domainObjectId;
	}

	public void setDomainObjectId(Integer domainObjectId) {
		this.domainObjectId = domainObjectId;
	}

	public ActionRequested getActionRequested() {
		return actionRequested;
	}

	public void setActionRequested(ActionRequested actionRequested) {
		this.actionRequested = actionRequested;
	}

	public boolean isConfirmationRequested() {
		return confirmationRequested;
	}

	public void setConfirmationRequested(boolean confirmationRequested) {
		this.confirmationRequested = confirmationRequested;
	}
	
	public void requestConfirmation(Row row, ActionRequested actionRequested) {
		this.actionRequested = actionRequested;
		confirmationRequested = true;
		row.setStyle("background-color:yellow;");
	}
	
	public void restoreRowDefaultStyle(Row row) {
		actionRequested = ActionRequested.NONE;
		confirmationRequested = false;
		row.setStyle("background-color:transparent;");
	}
}
