package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.AcquirableMaterialFormHelper;
import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;

@SuppressWarnings("serial")
public class ModifyAcquirableMaterialButtonComposer extends ModifyButtonComposer {

	public ModifyAcquirableMaterialButtonComposer() {
		super((TrackedObjectFormHelper) new AcquirableMaterialFormHelper());
	}
}