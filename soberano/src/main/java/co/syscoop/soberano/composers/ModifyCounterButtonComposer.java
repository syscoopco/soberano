package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.CounterFormHelper;
import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;

@SuppressWarnings("serial")
public class ModifyCounterButtonComposer extends ModifyButtonComposer {

	public ModifyCounterButtonComposer() {
		super((TrackedObjectFormHelper) new CounterFormHelper());
	}
}