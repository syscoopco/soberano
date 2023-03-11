package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.ProductCategoryFormHelper;
import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;

@SuppressWarnings("serial")
public class RecordProductCategoryButtonComposer extends RecordButtonComposer {

	public RecordProductCategoryButtonComposer() {
		super((TrackedObjectFormHelper) new ProductCategoryFormHelper());
	}
}
