package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.BusinessActivityTrackedObjectFormHelper;
import co.syscoop.soberano.ui.helper.ShiftClosureFormHelper;

@SuppressWarnings("serial")
public class RecordShiftClosureButtonComposer extends BusinessActivityTrackedObjectComposer {

	public RecordShiftClosureButtonComposer() {
		super((BusinessActivityTrackedObjectFormHelper) new ShiftClosureFormHelper());
	}
}
