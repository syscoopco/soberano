package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.PrinterProfileFormHelper;
import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;

@SuppressWarnings("serial")
public class ModifyPrinterProfileButtonComposer extends ModifyButtonComposer {

	public ModifyPrinterProfileButtonComposer() {
		super((TrackedObjectFormHelper) new PrinterProfileFormHelper());
	}
}