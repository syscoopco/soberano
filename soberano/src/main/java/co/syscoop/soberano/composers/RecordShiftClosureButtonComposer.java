package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.BusinessActivityTrackedObjectFormHelper;
import co.syscoop.soberano.ui.helper.ShiftClosureFormHelper;

@SuppressWarnings("serial")
public class RecordShiftClosureButtonComposer extends BusinessActivityTrackedObjectButtonComposer {

	public RecordShiftClosureButtonComposer() {
		super((BusinessActivityTrackedObjectFormHelper) new ShiftClosureFormHelper());
	}
}
