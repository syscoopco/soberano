package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Box;

import co.syscoop.soberano.ui.helper.BusinessActivityTrackedObjectFormHelper;
import co.syscoop.soberano.ui.helper.OrderFormHelper;

@SuppressWarnings({ "serial" })
public class BillButtonComposer extends BusinessActivityTrackedObjectButtonComposer {

	public BillButtonComposer() {
		super((BusinessActivityTrackedObjectFormHelper) new OrderFormHelper());
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
          boxDetails = (Box) btnBill.query("#wndContentPanel").query("#boxDetails");
    }
}
