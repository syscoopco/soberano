package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.ProductFormHelper;
import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;

@SuppressWarnings("serial")
public class RecordProductButtonComposer extends RecordButtonComposer {

	public RecordProductButtonComposer() {
		super((TrackedObjectFormHelper) new ProductFormHelper());
	}
}
