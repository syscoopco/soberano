package co.syscoop.soberano.renderers;

import java.sql.SQLException;

import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Include;

import co.syscoop.soberano.domain.tracked.AcquirableMaterial;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.ui.helper.AcquirableMaterialFormHelper;

public class AcquirableMaterialTreeNodeRenderer extends DomainObjectTreeNodeRenderer {

	public AcquirableMaterialTreeNodeRenderer(String pageToRefreshZulURI) {
		super(pageToRefreshZulURI);
	}

	@Override
	protected void fillForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException {
		
		(new AcquirableMaterialFormHelper()).fillForm(incDetails, data);
	}

	@Override
	protected int disable(DefaultTreeNode<NodeData> data) throws SQLException, Exception {

		return (new AcquirableMaterial(((DomainObject) data.getData().getValue()).getId())).disable();
	}
}
