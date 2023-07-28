package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.CustomerFormHelper;
import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;

@SuppressWarnings({ "serial" })
public class RecordCustomerButtonComposer extends RecordButtonComposer {

	public RecordCustomerButtonComposer() {
		super((TrackedObjectFormHelper) new CustomerFormHelper());
	}
}