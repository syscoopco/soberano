package co.syscoop.soberano.renderers;

import java.sql.SQLException;
import java.util.HashMap;

import org.springframework.jdbc.BadSqlGrammarException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.NotEnoughRightsException;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.util.ExceptionTreatment;
import co.syscoop.soberano.vocabulary.Labels;

public abstract class DomainObjectTreeNodeRenderer implements TreeitemRenderer<DefaultTreeNode<NodeData>>{
	
	@SuppressWarnings("unused")
	private String pageToRefreshZulURI = "";
	
	private HashMap<Treeitem, ActionRequested> requestedActions = new HashMap<Treeitem, ActionRequested>();
	
	public DomainObjectTreeNodeRenderer(String pageToRefreshZulURI) {
		this.pageToRefreshZulURI = pageToRefreshZulURI;
	}

	protected abstract void fillForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException;
	
	protected abstract int disable(DefaultTreeNode<NodeData> data) throws SQLException, Exception;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void render(Treeitem item, DefaultTreeNode<NodeData> data, int index, EventListener delEventListener) throws Exception {
		
		NodeData nodeData = data.getData();
		Treerow tr = new Treerow();
		item.appendChild(tr);
		Treecell tc = new Treecell(nodeData.getLabel());
		Button btnDisable = new Button();
		btnDisable.setId("btnDisable" + ((DomainObject) data.getData().getValue()).getId().toString());
		btnDisable.setImage("./images/delete.png");	
		btnDisable.setClass("ContextualButton");
		btnDisable.addEventListener("onClick", delEventListener);
		tc.appendChild(new Separator("vertical"));
		tc.appendChild(btnDisable);
		tr.appendChild(tc);
		
		//add listener to click event on item row
		item.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				try {
					Include incDetails = (Include) item.query("#wndShowingAll").getParent().query("#incDetails");
					fillForm(incDetails, data);
				}
				catch(NullPointerException ex) {
					ExceptionTreatment.logAndShow(ex, 
												Labels.getLabel("message.permissions.NonExistentObjectOrNotEnoughRights"), 
												Labels.getLabel("messageBoxTitle.Warning"),
												Messagebox.EXCLAMATION);
				}
				catch(BadSqlGrammarException ex) {
					ExceptionTreatment.logAndShow(ex, 
							ex.getMessage(), 
							Labels.getLabel("messageBoxTitle.Error"),
							Messagebox.ERROR);
				}
				catch(Exception ex) {
					ExceptionTreatment.logAndShow(ex, 
												Labels.getLabel("message.error.Undetermined"), 
												Labels.getLabel("messageBoxTitle.Error"),
												Messagebox.ERROR);
				}
			}
		});	
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void render(Treeitem item, DefaultTreeNode<NodeData> data, int index) throws Exception {
		
		render(item, data, index, new EventListener() {

			@Override
			public void onEvent(Event event) throws SQLException, Exception {
				
				try {
					if (requestedActions.get(item) != null && requestedActions.get(item).equals(ActionRequested.DISABLE)) {
						int result = disable(data);
						if (result == -1) {
							throw new NotEnoughRightsException();
						}
						else {					
							//refresh the form
							//Executions.sendRedirect(pageToRefreshZulURI);
							
							//or hide details and disable btnApply instead
							((Include) item.query("#wndShowingAll").getParent().query("#incDetails")).setVisible(false);
							((Button) item.query("#wndShowingAll").getParent().getParent().getParent().getParent().query("south").getParent().getParent().query("#incSouth").query("#btnApply")).setDisabled(true);
							
							//remove the treeitem
							item.detach();
						}
					}
					else {
						requestDeletion(item);
					}
				}
				catch(NotEnoughRightsException ex) {
					ExceptionTreatment.logAndShow(ex, 
							Labels.getLabel("message.permissions.NotEnoughRights"), 
							Labels.getLabel("messageBoxTitle.Warning"),
							Messagebox.EXCLAMATION);					
				}
			}
		});
	}
	
	public void requestDeletion(Treeitem item) {
		requestedActions.put(item, ActionRequested.DISABLE);
		((Treecell) item.query("treecell")).setStyle("background-color:yellow;");
	}
	
	public void cancelRequestedAction(Treeitem item) {
		requestedActions.remove(item);
		((Treecell) item.query("treecell")).setStyle("background-color:transparent;");
	}
}
