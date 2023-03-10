package co.syscoop.soberano.renderers;

import java.sql.SQLException;

import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Include;

import co.syscoop.soberano.domain.tracked.Counter;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.ui.helper.CounterFormHelper;

public class CounterTreeNodeRenderer extends DomainObjectTreeNodeRenderer {

	public CounterTreeNodeRenderer(String pageToRefreshZulURI) {
		super(pageToRefreshZulURI);
	}

	@Override
	protected void fillForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException {
		
		(new CounterFormHelper()).fillForm(incDetails, data);
	}

	@Override
	protected int disable(DefaultTreeNode<NodeData> data) throws SQLException, Exception {

		return (new Counter(((DomainObject) data.getData().getValue()).getId())).disable();
	}
}
