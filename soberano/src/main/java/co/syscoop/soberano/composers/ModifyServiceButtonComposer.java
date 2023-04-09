package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.ServiceFormHelper;
import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;

@SuppressWarnings("serial")
public class ModifyServiceButtonComposer extends ModifyButtonComposer {

	public ModifyServiceButtonComposer() {
		super((TrackedObjectFormHelper) new ServiceFormHelper());
	}
}