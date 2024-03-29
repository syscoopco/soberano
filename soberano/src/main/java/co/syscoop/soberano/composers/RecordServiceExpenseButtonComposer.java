package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.BusinessActivityTrackedObjectFormHelper;
import co.syscoop.soberano.ui.helper.ServiceExpenseFormHelper;

@SuppressWarnings("serial")
public class RecordServiceExpenseButtonComposer extends BusinessActivityTrackedObjectButtonComposer {

	public RecordServiceExpenseButtonComposer() {
		super((BusinessActivityTrackedObjectFormHelper) new ServiceExpenseFormHelper());
	}
}
