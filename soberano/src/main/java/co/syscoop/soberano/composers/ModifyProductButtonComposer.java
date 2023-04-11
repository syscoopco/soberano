package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.ProductFormHelper;
import co.syscoop.soberano.ui.helper.TrackedObjectFormHelper;

@SuppressWarnings("serial")
public class ModifyProductButtonComposer extends ModifyButtonComposer {

	public ModifyProductButtonComposer() {
		super((TrackedObjectFormHelper) new ProductFormHelper());
	}
}