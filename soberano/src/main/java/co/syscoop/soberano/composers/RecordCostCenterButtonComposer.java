package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.CostCenterFormHelper;
import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;

@SuppressWarnings("serial")
public class RecordCostCenterButtonComposer extends RecordButtonComposer {

	public RecordCostCenterButtonComposer() {
		super((TrackedObjectFormHelper) new CostCenterFormHelper());
	}
}