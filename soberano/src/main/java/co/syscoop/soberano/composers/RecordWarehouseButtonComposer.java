package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;
import co.syscoop.soberano.ui.helper.WarehouseFormHelper;

@SuppressWarnings("serial")
public class RecordWarehouseButtonComposer extends RecordButtonComposer {

	public RecordWarehouseButtonComposer() {
		super((TrackedObjectFormHelper) new WarehouseFormHelper());
	}
}
