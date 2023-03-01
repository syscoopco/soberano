package co.syscoop.soberano.renderers;

import java.sql.SQLException;

import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Include;

import co.syscoop.soberano.domain.tracked.CostCenter;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.ui.helper.CostCenterFormHelper;

public class CostCenterTreeNodeRenderer extends DomainObjectTreeNodeRenderer {

	public CostCenterTreeNodeRenderer(String pageToRefreshZulURI) {
		super(pageToRefreshZulURI);
	}

	@Override
	protected void fillTheForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException {
		
		CostCenterFormHelper.fillTheForm(incDetails, data);
	}

	@Override
	protected int disable(DefaultTreeNode<NodeData> data) throws SQLException, Exception {

		return (new CostCenter(((DomainObject) data.getData().getValue()).getId())).disable();
	}
}
