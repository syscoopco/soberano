package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.PrinterProfileFormHelper;
import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;

@SuppressWarnings("serial")
public class RecordPrinterProfileButtonComposer extends RecordButtonComposer {

	public RecordPrinterProfileButtonComposer() {
		super((TrackedObjectFormHelper) new PrinterProfileFormHelper());
	}
}
