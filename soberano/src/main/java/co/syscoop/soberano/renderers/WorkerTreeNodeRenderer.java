package co.syscoop.soberano.renderers;

import java.sql.SQLException;

import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Include;
import co.syscoop.soberano.domain.tracked.Worker;
import co.syscoop.soberano.domain.untracked.DomainObject;
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
	protected int disable(DefaultTreeNode<NodeData> data) throws SQLException, Exception {

		return (new Worker(((DomainObject) data.getData().getValue()).getId())).disable();
	}
}
