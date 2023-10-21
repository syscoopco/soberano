package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.BusinessActivityTrackedObjectFormHelper;
import co.syscoop.soberano.ui.helper.MaterialExpenseFormHelper;

@SuppressWarnings("serial")
public class RecordMaterialExpenseButtonComposer extends BusinessActivityTrackedObjectButtonComposer {

	public RecordMaterialExpenseButtonComposer() {
		super((BusinessActivityTrackedObjectFormHelper) new MaterialExpenseFormHelper());
	}
}
