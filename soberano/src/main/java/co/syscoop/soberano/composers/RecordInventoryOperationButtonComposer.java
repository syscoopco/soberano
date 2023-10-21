package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Box;

import co.syscoop.soberano.ui.helper.BusinessActivityTrackedObjectFormHelper;
import co.syscoop.soberano.ui.helper.InventoryOperationFormHelper;

@SuppressWarnings("serial")
public class RecordInventoryOperationButtonComposer extends BusinessActivityTrackedObjectButtonComposer {

	public RecordInventoryOperationButtonComposer() {
		super((BusinessActivityTrackedObjectFormHelper) new InventoryOperationFormHelper());
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
          boxDetails = (Box) btnRecord.getPreviousSibling().query("#boxDetails");
    }
}
