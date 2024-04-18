package co.syscoop.soberano.ui.helper;

import java.sql.SQLException;

import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.domain.tracked.Service;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.util.StringIdCodeGenerator;
import co.syscoop.soberano.util.ui.ZKUtilitity;

public class ServiceFormHelper extends TrackedObjectFormHelper {

	@Override
	public void fillForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException {
		
		Service service = new Service(((DomainObject) data.getData().getValue()).getId());
		service.get();
		
		//store in the form the ids of shown object for subsequent modification
		((Intbox) incDetails.getParent().query("#intId")).setValue(service.getId());
		((Textbox) incDetails.getParent().query("#txtStringId")).setText(service.getStringId());
		
		incDetails.setVisible(true);
		Clients.scrollIntoView(incDetails.query("#txtCode"));
		((Button) incDetails.getParent().query("#incSouth").query("#btnApply")).setDisabled(false);
		
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtCode"), service.getStringId());
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtName"), service.getName());
	}

	@Override
	public void cleanForm(Include incDetails) {
		
		Clients.scrollIntoView(incDetails.query("#txtCode"));
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtCode"), new StringIdCodeGenerator().getTenCharsRandomString(""));
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtName"), "");	
	}

	@Override
	public Integer recordFromForm(Include incDetails) throws Exception {
		
		return (new Service(0,
							0,
							((Textbox) incDetails.query("#txtCode")).getValue(),
							((Textbox) incDetails.query("#txtName")).getValue()))
						.record();
	}

	@Override
	public Integer modifyFromForm(Include incDetails) throws Exception {
		
		super.setTrackedObject(new Service(((Intbox) incDetails.getParent().query("#intId")).getValue(),
												0,
												((Textbox) incDetails.query("#txtCode")).getValue(),
												((Textbox) incDetails.query("#txtName")).getValue()));
		return super.getTrackedObject().modify();
	}
}
