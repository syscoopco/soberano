package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.AcquirableMaterialFormHelper;
import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;

@SuppressWarnings("serial")
public class RecordAcquirableMaterialButtonComposer extends RecordButtonComposer {

	public RecordAcquirableMaterialButtonComposer() {
		super((TrackedObjectFormHelper) new AcquirableMaterialFormHelper());
	}
}
