package co.syscoop.soberano.ui.helper;

import java.sql.SQLException;

import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Window;

import co.syscoop.soberano.domain.tracked.Configuration;
import co.syscoop.soberano.models.NodeData;

public class ConfigurationFormHelper extends TrackedObjectFormHelper {
	
	public void initForm(Window wndContentPanel) throws Exception {
		
		Configuration configuration = new Configuration();
		configuration.get();
		
		((Decimalbox) wndContentPanel.query("include").query("#decSurcharge")).setValue(configuration.getSurcharge());
		((Intbox) wndContentPanel.query("include").query("#intHour")).setValue(configuration.getShiftOpeningHour());
		((Intbox) wndContentPanel.query("include").query("#intMinutes")).setValue(configuration.getShiftOpeningMinutes());
		((Checkbox) wndContentPanel.query("include").query("#chkFirstOrderRequiresCashOperation")).setChecked(configuration.getFirstOrderRequiresCashOperation());
		((Checkbox) wndContentPanel.query("include").query("#chkSpiOperationRequiresConfirmation")).setChecked(configuration.getSpiOperationRequiresConfirmation());
		((Checkbox) wndContentPanel.query("include").query("#chkCompensateDeliveryProviderRates")).setChecked(configuration.getCompensateDeliveryProviderRates());
		((Checkbox) wndContentPanel.query("include").query("#chkDoNotSellInCaseOfStockZero")).setChecked(configuration.getDoNotSellInCaseOfStockZero());
		((Checkbox) wndContentPanel.query("include").query("#chkGroupProcessRunOutputAllocations")).setChecked(configuration.getGroupProcessRunOutputAllocations());
	}
	
	@Override
	public void fillForm(Include incDetails, DefaultTreeNode<NodeData> data) throws SQLException {
	}

	@Override
	public void cleanForm(Include incDetails) {
	}

	@Override
	public Integer recordFromForm(Include incDetails) throws Exception {
		return null;
	}

	@Override
	public Integer modifyFromForm(Include incDetails) throws Exception {
		super.setTrackedObject(new Configuration(((Decimalbox) incDetails.query("#decSurcharge")).getValue(),
												((Intbox) incDetails.query("#intHour")).getValue(),
												((Intbox) incDetails.query("#intMinutes")).getValue(),
												((Checkbox) incDetails.query("#chkFirstOrderRequiresCashOperation")).isChecked(),
												((Checkbox) incDetails.query("#chkSpiOperationRequiresConfirmation")).isChecked(),
												((Checkbox) incDetails.query("#chkCompensateDeliveryProviderRates")).isChecked(),
												((Checkbox) incDetails.query("#chkDoNotSellInCaseOfStockZero")).isChecked(),
												((Checkbox) incDetails.query("#chkGroupProcessRunOutputAllocations")).isChecked()));
		return super.getTrackedObject().modify();
	}
}
