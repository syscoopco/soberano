package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;
import co.syscoop.soberano.ui.helper.CustomerFormHelper;

@SuppressWarnings("serial")
public class ModifyCustomerButtonComposer extends ModifyButtonComposer {

	public ModifyCustomerButtonComposer() {
		super((TrackedObjectFormHelper) new CustomerFormHelper());
	}
}