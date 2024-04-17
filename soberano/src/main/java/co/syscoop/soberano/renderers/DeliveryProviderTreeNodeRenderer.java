package co.syscoop.soberano.renderers;

import java.sql.SQLException;

import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Include;

import co.syscoop.soberano.domain.tracked.DeliveryProvider;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.ui.helper.DeliveryProviderFormHelper;

public class DeliveryProviderTreeNodeRenderer extends DomainObjectTreeNodeRenderer {

	public DeliveryProviderTreeNodeRenderer(String pageToRefreshZulURI) {
		super(pageToRefreshZulURI);
	}

	@Override
	protected void fillForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException {
		
		(new DeliveryProviderFormHelper()).fillForm(incDetails, data);
	}

	@Override
	protected int disable(DefaultTreeNode<NodeData> data) throws SQLException, Exception {

		return (new DeliveryProvider(((DomainObject) data.getData().getValue()).getId())).disable();
	}
}
