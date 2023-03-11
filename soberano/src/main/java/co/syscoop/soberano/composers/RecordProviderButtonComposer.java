package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.ProviderFormHelper;
import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;

@SuppressWarnings("serial")
public class RecordProviderButtonComposer extends RecordButtonComposer {

	public RecordProviderButtonComposer() {
		super((TrackedObjectFormHelper) new ProviderFormHelper());
	}
}
