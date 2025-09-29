package co.syscoop.soberano.composers;

import co.syscoop.soberano.ui.helper.BusinessActivityTrackedObjectFormHelper;
import co.syscoop.soberano.ui.helper.TranslationFormHelper;

@SuppressWarnings("serial")
public class RecordTranslationButtonComposer extends BusinessActivityTrackedObjectButtonComposer {

	public RecordTranslationButtonComposer() {
		super((BusinessActivityTrackedObjectFormHelper) new TranslationFormHelper());
	}
}
