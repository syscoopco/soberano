package co.syscoop.soberano.renderers;

import java.sql.SQLException;

import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Include;

import co.syscoop.soberano.domain.tracked.Warehouse;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.ui.helper.WarehouseFormHelper;

public class WarehouseTreeNodeRenderer extends DomainObjectTreeNodeRenderer {

	public WarehouseTreeNodeRenderer(String pageToRefreshZulURI) {
		super(pageToRefreshZulURI);
	}

	@Override
	protected void fillTheForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException {
		
		WarehouseFormHelper.fillTheForm(incDetails, data);
	}

	@Override
	protected int disable(DefaultTreeNode<NodeData> data) throws SQLException, Exception {

		return (new Warehouse(((DomainObject) data.getData().getValue()).getId())).disable();
	}
}
