package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;
import co.syscoop.soberano.ui.helper.WorkerFormHelper;

@SuppressWarnings({ "serial" })
public class RecordWorkerButtonComposer extends RecordButtonComposer {

	public RecordWorkerButtonComposer() {
		super((TrackedObjectFormHelper) new WorkerFormHelper());
	}
}