package co.syscoop.soberano.renderers;

import java.sql.SQLException;

import org.springframework.jdbc.BadSqlGrammarException;
import org.zkoss.zk.ui.Executions;
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
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.util.ExceptionTreatment;
import co.syscoop.soberano.vocabulary.Labels;

public abstract class DomainObjectTreeNodeRenderer implements TreeitemRenderer<DefaultTreeNode<NodeData>>{
	
	private String pageToRefreshZulURI = "";
	
	public DomainObjectTreeNodeRenderer(String pageToRefreshZulURI) {
		this.pageToRefreshZulURI = pageToRefreshZulURI;
	}

	protected abstract void fillTheForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException;
	
	protected abstract int delete(Treeitem item, DefaultTreeNode<NodeData> data, Event event);
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void render(Treeitem item, DefaultTreeNode<NodeData> data, int index, EventListener delEventListener) throws Exception {
		
		NodeData nodeData = data.getData();
		Treerow tr = new Treerow();
		item.appendChild(tr);
		Treecell tc = new Treecell(nodeData.getLabel());
		Button btnDelete = new Button();
		btnDelete.setImage("./images/delete.png");	
		btnDelete.setClass("ContextualButton");
		btnDelete.addEventListener("onClick", delEventListener);
		tc.appendChild(new Separator("vertical"));
		tc.appendChild(btnDelete);
		tr.appendChild(tc);
		
		//add listener to click event on item row
		item.addEventListener("onClick", new EventListener() {

			@Override
			public void onEvent(Event event) throws Exception {
				
				try {
					Include incDetails = (Include) item.query("#wndShowingAll").getParent().query("#incDetails");
					fillTheForm(incDetails, data);
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
			public void onEvent(Event event) {

				int result = delete(item, data, event);
				if (result == -1) {
					Messagebox.show(Labels.getLabel("message.permissions.NotEnoughRights"), 
				  					Labels.getLabel("messageBoxTitle.Warning"), 
									0, 
									Messagebox.EXCLAMATION);
				}
				else {
					//refresh the form
					Executions.sendRedirect(pageToRefreshZulURI);				
				}
			}
		});
	}
}
