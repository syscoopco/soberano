package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.ProductionLineFormHelper;
import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;

@SuppressWarnings("serial")
public class ModifyProductionLineButtonComposer extends ModifyButtonComposer {

	public ModifyProductionLineButtonComposer() {
		super((TrackedObjectFormHelper) new ProductionLineFormHelper());
	}
}