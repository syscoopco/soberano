package co.syscoop.soberano.renderers;

import java.sql.SQLException;

import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;

import co.syscoop.soberano.domain.tracked.Currency;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.CurrencyHasBalanceException;
import co.syscoop.soberano.exception.ExceptionTreatment;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.ui.helper.CurrencyFormHelper;
import co.syscoop.soberano.vocabulary.Labels;

public class CurrencyTreeNodeRenderer extends DomainObjectTreeNodeRenderer {

	public CurrencyTreeNodeRenderer(String pageToRefreshZulURI) {
		super(pageToRefreshZulURI);
	}

	@Override
	protected void fillForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException {
		
		(new CurrencyFormHelper()).fillForm(incDetails, data);
	}

	@Override
	protected int disable(DefaultTreeNode<NodeData> data) throws SQLException, Exception {

		try {
			return (new Currency(((DomainObject) data.getData().getValue()).getId())).disable();
		}
		catch(CurrencyHasBalanceException ex) { 
			ExceptionTreatment.logAndShow(ex, 
					Labels.getLabel("message.validation.currencyWithBalanceCannotBeDisabled"), 
					Labels.getLabel("messageBoxTitle.Validation"),
					Messagebox.EXCLAMATION);
			return -2;
		}
	}
}
