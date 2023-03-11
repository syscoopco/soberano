package co.syscoop.soberano.ui.helper;

import java.sql.SQLException;

import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import co.syscoop.soberano.domain.tracked.ProductCategory;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.models.NodeData;
import co.syscoop.soberano.util.ZKUtilitity;

public class ProductCategoryFormHelper extends TrackedObjectFormHelper {
	
	
	@Override
	public void fillForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException {
		
		ProductCategory category = new ProductCategory(((DomainObject) data.getData().getValue()).getId());
		category.get();
		
		//store in the form the ids of shown object for subsequent modification
		((Intbox) incDetails.getParent().query("#intId")).setValue(category.getId());
		((Textbox) incDetails.getParent().query("#txtStringId")).setText(category.getStringId());
		
		incDetails.setVisible(true);
		Clients.scrollIntoView(incDetails.query("#txtName"));
		((Button) incDetails.getParent().query("#incSouth").query("#btnApply")).setDisabled(false);
		
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtName"), category.getName());
		
		ZKUtilitity.setValueWOValidation((Intbox) incDetails.query("#intPosition"), category.getPosition());
		((Checkbox) incDetails.query("#chkDisabled")).setChecked(!category.getIsEnabled());
	}

	@Override
	public void cleanForm(Include incDetails) {
		
		ZKUtilitity.setValueWOValidation((Textbox) incDetails.query("#txtName"), "");
		((Checkbox) incDetails.query("#chkDisabled")).setChecked(false);
	}

	@Override
	public Integer recordFromForm(Include incDetails) throws Exception {
		
		return (new ProductCategory(0,
									0,
									((Textbox) incDetails.query("#txtName")).getValue(),
									((Intbox) incDetails.query("#intPosition")).getValue(),
									!((Checkbox) incDetails.query("#chkDisabled")).isChecked()))
				.record();
	}

	@Override
	public Integer modifyFromForm(Include incDetails) throws Exception {
		
		super.setTrackedObject(new ProductCategory(((Intbox) incDetails.getParent().query("#intId")).getValue(),
													0,
													((Textbox) incDetails.query("#txtName")).getValue(),
													((Intbox) incDetails.query("#intPosition")).getValue(),
													!((Checkbox) incDetails.query("#chkDisabled")).isChecked()));
		return super.getTrackedObject().modify();
	}
}
