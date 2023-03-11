package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.ProviderFormHelper;
import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;

@SuppressWarnings({ "serial" })
public class ModifyProviderButtonComposer extends ModifyButtonComposer {

	public ModifyProviderButtonComposer() {
		super((TrackedObjectFormHelper) new ProviderFormHelper());
	}
}