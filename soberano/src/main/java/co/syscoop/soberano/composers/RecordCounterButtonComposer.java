package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.CounterFormHelper;
import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;

@SuppressWarnings("serial")
public class RecordCounterButtonComposer extends RecordButtonComposer {

	public RecordCounterButtonComposer() {
		super((TrackedObjectFormHelper) new CounterFormHelper());
	}
}