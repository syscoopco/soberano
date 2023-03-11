package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.CostCenterFormHelper;
import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;

@SuppressWarnings("serial")
public class ModifyCostCenterButtonComposer extends ModifyButtonComposer {

	public ModifyCostCenterButtonComposer() {
		super((TrackedObjectFormHelper) new CostCenterFormHelper());
	}
}