package co.syscoop.soberano.renderers;

import java.util.HashMap;

import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

@SuppressWarnings("rawtypes")
public abstract class DomainObjectRowRenderer implements RowRenderer{
	
	protected HashMap<Row, ActionRequested> requestedActions = new HashMap<Row, ActionRequested>();

	@Override
	public void render(Row row, Object data, int index) throws Exception {}
	
	public void requestDeletion(Row row) {
		requestedActions.put(row, ActionRequested.DISABLE);
		row.setStyle("background-color:yellow;");
	}
	
	public void cancelRequestedAction(Row row) {
		requestedActions.remove(row);
		row.setStyle("background-color:transparent;");
	}
}
