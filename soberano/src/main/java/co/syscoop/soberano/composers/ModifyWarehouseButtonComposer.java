package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;
import co.syscoop.soberano.ui.helper.WarehouseFormHelper;

@SuppressWarnings("serial")
public class ModifyWarehouseButtonComposer extends ModifyButtonComposer {

	public ModifyWarehouseButtonComposer() {
		super((TrackedObjectFormHelper) new WarehouseFormHelper());
	}
}