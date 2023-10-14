package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Box;

import co.syscoop.soberano.ui.helper.BusinessActivityTrackedObjectFormHelper;
import co.syscoop.soberano.ui.helper.RecordOrderFormHelper;

@SuppressWarnings("serial")
public class RecordOrderButtonComposer extends BusinessActivityTrackedObjectComposer {

	public RecordOrderButtonComposer() {
		super((BusinessActivityTrackedObjectFormHelper) new RecordOrderFormHelper());
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
          boxDetails = (Box) btnRecord.getPreviousSibling().query("#boxDetails");
    }
}
