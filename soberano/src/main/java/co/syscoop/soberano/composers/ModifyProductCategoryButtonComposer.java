package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.ProductCategoryFormHelper;
import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;

@SuppressWarnings({ "serial" })
public class ModifyProductCategoryButtonComposer extends ModifyButtonComposer {

	public ModifyProductCategoryButtonComposer() {
		super((TrackedObjectFormHelper) new ProductCategoryFormHelper());
	}
}