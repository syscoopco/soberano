package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.BusinessActivityTrackedObjectFormHelper;
import co.syscoop.soberano.ui.helper.PayrollExpenseFormHelper;

@SuppressWarnings("serial")
public class RecordPayrollExpenseButtonComposer extends BusinessActivityTrackedObjectRecordButtonComposer {

	public RecordPayrollExpenseButtonComposer() {
		super((BusinessActivityTrackedObjectFormHelper) new PayrollExpenseFormHelper());
	}
}
