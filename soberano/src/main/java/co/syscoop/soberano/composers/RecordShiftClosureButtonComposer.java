package co.syscoop.soberano.composers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Box;

import co.syscoop.soberano.ui.helper.BusinessActivityTrackedObjectFormHelper;
import co.syscoop.soberano.ui.helper.ShiftClosureFormHelper;

@SuppressWarnings("serial")
public class RecordShiftClosureButtonComposer extends BusinessActivityTrackedObjectButtonComposer {

	public RecordShiftClosureButtonComposer() {
		super((BusinessActivityTrackedObjectFormHelper) new ShiftClosureFormHelper());
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
    	
          super.doAfterCompose(comp);
          boxDetails = (Box) btnRecord.query("#wndShowingAll").query("#boxDetails");
    }
}
