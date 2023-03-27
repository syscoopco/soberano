package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.CurrencyFormHelper;
import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;

@SuppressWarnings("serial")
public class ModifyCurrencyButtonComposer extends ModifyButtonComposer {

	public ModifyCurrencyButtonComposer() {
		super((TrackedObjectFormHelper) new CurrencyFormHelper());
	}
}