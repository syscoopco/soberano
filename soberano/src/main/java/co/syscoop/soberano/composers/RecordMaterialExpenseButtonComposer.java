package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.BusinessActivityTrackedObjectFormHelper;
import co.syscoop.soberano.ui.helper.MaterialExpenseFormHelper;

@SuppressWarnings("serial")
public class RecordMaterialExpenseButtonComposer extends BusinessActivityTrackedObjectComposer {

	public RecordMaterialExpenseButtonComposer() {
		super((BusinessActivityTrackedObjectFormHelper) new MaterialExpenseFormHelper());
	}
}
