package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.DeliveryProviderFormHelper;
import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;

@SuppressWarnings("serial")
public class RecordDeliveryProviderButtonComposer extends RecordButtonComposer {

	public RecordDeliveryProviderButtonComposer() {
		super((TrackedObjectFormHelper) new DeliveryProviderFormHelper());
	}
}
