package co.syscoop.soberano.ui.helper;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import co.syscoop.soberano.domain.tracked.Process;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.util.ZKUtilitity;

public class ProcessFormHelper extends TrackedObjectFormHelper {
	
	@Override
	public void cleanForm(Include incDetails) {
		
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtName"), "");
		ZKUtilitity.setValueWOValidation((Decimalbox) incDetails.query("#decFixedCost"), new BigDecimal(0.0));
	}
	
	@Override
	public void fillForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException {
		
		Process process = new Process(((DomainObject) data.getData().getValue()).getId());
		process.get();
		
		//store in the form the ids of shown object for subsequent modification
		((Intbox) incDetails.getParent().query("#intId")).setValue(process.getId());
		((Textbox) incDetails.getParent().query("#txtStringId")).setText(process.getStringId());
		
		incDetails.setVisible(true);
		Clients.scrollIntoView(incDetails.query("#txtName"));
		((Button) incDetails.getParent().query("#incSouth").query("#btnApply")).setDisabled(false);
		
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtName"), process.getName());
		ZKUtilitity.setValueWOValidation((Decimalbox) incDetails.query("#decFixedCost"), process.getFixedCost());
	}

	@Override
	public Integer recordFromForm(Include incDetails) throws Exception {
		
		return (new Process(0,
							0,
							((Textbox) incDetails.query("#txtName")).getValue(),
							((Decimalbox) incDetails.query("#decFixedCost")).getValue()))
				.record();
	}
	
	@Override
	public Integer modifyFromForm(Include incDetails) throws Exception {
		
		super.setTrackedObject(new Process(((Intbox) incDetails.getParent().query("#intId")).getValue(),
											0,
											((Textbox) incDetails.query("#txtName")).getValue(),
											((Decimalbox) incDetails.query("#decFixedCost")).getValue()));
		return super.getTrackedObject().modify();
	}
}
