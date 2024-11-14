package co.syscoop.soberano.renderers;

import java.sql.SQLException;

import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Include;

import co.syscoop.soberano.domain.tracked.Customer;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.ui.helper.CustomerFormHelper;

public class CustomerTreeNodeRenderer extends DomainObjectTreeNodeRenderer {

	public CustomerTreeNodeRenderer(String pageToRefreshZulURI) {
		super(pageToRefreshZulURI);
		super.setDontDetachAnItemWhenDisablingIt(true);
	}

	@Override
	protected void fillForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException {
		
		(new CustomerFormHelper()).fillForm(incDetails, data);
	}

	@Override
	protected int disable(DefaultTreeNode<NodeData> data) throws SQLException, Exception {

		return (new Customer(((DomainObject) data.getData().getValue()).getId())).disable();
	}
}
