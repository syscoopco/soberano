package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;
import co.syscoop.soberano.ui.helper.WorkerFormHelper;

@SuppressWarnings({ "serial" })
public class ModifyWorkerButtonComposer extends ModifyButtonComposer {

	public ModifyWorkerButtonComposer() {
		super((TrackedObjectFormHelper) new WorkerFormHelper());
	}
}