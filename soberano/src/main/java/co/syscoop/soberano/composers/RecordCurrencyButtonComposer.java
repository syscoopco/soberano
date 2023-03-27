package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.CurrencyFormHelper;
import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;

@SuppressWarnings("serial")
public class RecordCurrencyButtonComposer extends RecordButtonComposer {

	public RecordCurrencyButtonComposer() {
		super((TrackedObjectFormHelper) new CurrencyFormHelper());
	}
}
