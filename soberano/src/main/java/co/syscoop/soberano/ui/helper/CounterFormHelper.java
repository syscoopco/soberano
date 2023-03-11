package co.syscoop.soberano.ui.helper;

import java.sql.SQLException;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import co.syscoop.soberano.domain.tracked.Counter;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.util.ZKUtilitity;

public class CounterFormHelper extends TrackedObjectFormHelper {
	
	@Override
	public void fillForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException {
		
		Counter counter = new Counter(((DomainObject) data.getData().getValue()).getId());
		counter.get();
		
		//store in the form the ids of shown object for subsequent modification
		((Intbox) incDetails.getParent().query("#intId")).setValue(counter.getId());
		((Textbox) incDetails.getParent().query("#txtStringId")).setText(counter.getStringId());
		
		incDetails.setVisible(true);
		Clients.scrollIntoView(incDetails.query("#txtCode"));
		((Button) incDetails.getParent().query("#incSouth").query("#btnApply")).setDisabled(false);
		
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtCode"), counter.getStringId());
		
		ZKUtilitity.setValueWOValidation((Intbox) incDetails.query("#intNumberOfReceivers"), counter.getNumberOfReceivers());
		((Checkbox) incDetails.query("#chkIsSurcharged")).setChecked(counter.getIsSurcharged());
		((Checkbox) incDetails.query("#chkDisabled")).setChecked(!counter.getIsEnabled());
	}

	@Override
	public void cleanForm(Include incDetails) {
		
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtCode"), "");
		((Checkbox) incDetails.query("#chkIsSurcharged")).setChecked(false);
		((Checkbox) incDetails.query("#chkDisabled")).setChecked(false);	
	}

	@Override
	public Integer recordFromForm(Include incDetails) throws Exception {
		
		return (new Counter(0,
							0,
							((Textbox) incDetails.query("#txtCode")).getValue(),
							((Intbox) incDetails.query("#intNumberOfReceivers")).getValue(),
							((Checkbox) incDetails.query("#chkIsSurcharged")).isChecked(),
							!((Checkbox) incDetails.query("#chkDisabled")).isChecked()))
					.record();
	}

	@Override
	public Integer modifyFromForm(Include incDetails) throws Exception {
		
		super.setTrackedObject(new Counter(((Intbox) incDetails.getParent().query("#intId")).getValue(),
											0,
											((Textbox) incDetails.query("#txtCode")).getValue(),
											((Intbox) incDetails.query("#intNumberOfReceivers")).getValue(),
											((Checkbox) incDetails.query("#chkIsSurcharged")).isChecked(),
											!((Checkbox) incDetails.query("#chkDisabled")).isChecked()));
		return super.getTrackedObject().modify();
	}
}
