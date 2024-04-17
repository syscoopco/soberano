package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.DeliveryProviderFormHelper;
import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;

@SuppressWarnings("serial")
public class ModifyDeliveryProviderButtonComposer extends ModifyButtonComposer {

	public ModifyDeliveryProviderButtonComposer() {
		super((TrackedObjectFormHelper) new DeliveryProviderFormHelper());
	}
}