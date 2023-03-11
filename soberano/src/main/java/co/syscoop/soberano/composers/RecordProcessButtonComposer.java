package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.ProcessFormHelper;
import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;

@SuppressWarnings("serial")
public class RecordProcessButtonComposer extends RecordButtonComposer {

	public RecordProcessButtonComposer() {
		super((TrackedObjectFormHelper) new ProcessFormHelper());
	}
}
