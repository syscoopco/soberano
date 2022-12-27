package co.syscoop.soberano.renderers;

import java.sql.SQLException;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Include;
import org.zkoss.zul.Treeitem;

import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.ui.helper.WorkerFormHelper;

public class WorkerTreeNodeRenderer extends DomainObjectTreeNodeRenderer {

	public WorkerTreeNodeRenderer(String pageToRefreshZulURI) {
		super(pageToRefreshZulURI);
	}

	@Override
	protected void fillTheForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException {
		
		WorkerFormHelper.fillTheForm(incDetails, data);
	}

	@Override
	protected int delete(Treeitem item, DefaultTreeNode<NodeData> data, Event event) {
		// TODO Auto-generated method stub
		return 0;
	}
}
