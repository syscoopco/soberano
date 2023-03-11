package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.ProcessFormHelper;
import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;

@SuppressWarnings("serial")
public class ModifyProcessButtonComposer extends ModifyButtonComposer {

	public ModifyProcessButtonComposer() {
		super((TrackedObjectFormHelper) new ProcessFormHelper());
	}
}