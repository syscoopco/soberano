package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.ProductionLineFormHelper;
import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;

@SuppressWarnings("serial")
public class RecordProductionLineButtonComposer extends RecordButtonComposer {

	public RecordProductionLineButtonComposer() {
		super((TrackedObjectFormHelper) new ProductionLineFormHelper());
	}
}
