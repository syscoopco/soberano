package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Box;

import co.syscoop.soberano.ui.helper.BusinessActivityTrackedObjectFormHelper;
import co.syscoop.soberano.ui.helper.ProcessRunFormHelper;

@SuppressWarnings({ "serial" })
public class CancelProcessRunButtonComposer extends BusinessActivityTrackedObjectButtonComposer {

	public CancelProcessRunButtonComposer() {
		super((BusinessActivityTrackedObjectFormHelper) new ProcessRunFormHelper());
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
          boxDetails = (Box) btnCancel.query("#wndContentPanel").query("#boxDetails");
    }
}
