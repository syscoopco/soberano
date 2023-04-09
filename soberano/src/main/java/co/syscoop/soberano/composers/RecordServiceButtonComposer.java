package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.ServiceFormHelper;
import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;

@SuppressWarnings("serial")
public class RecordServiceButtonComposer extends RecordButtonComposer {

	public RecordServiceButtonComposer() {
		super((TrackedObjectFormHelper) new ServiceFormHelper());
	}
}
