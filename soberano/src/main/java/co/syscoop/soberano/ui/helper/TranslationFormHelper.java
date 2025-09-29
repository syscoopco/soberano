package co.syscoop.soberano.ui.helper;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Box;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;

import co.syscoop.soberano.domain.tracked.Translation;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.SomeFieldsContainWrongValuesException;
import co.syscoop.soberano.models.TranslationsGridModel;

public class TranslationFormHelper extends BusinessActivityTrackedObjectFormHelper {

	@Override
	public void cleanForm(Box boxDetails) {
		
		Clients.scrollIntoView(boxDetails.query("#cmbLanguage"));
		((Textbox) boxDetails.query("#txtFromTerm")).setValue("");
		((Textbox) boxDetails.query("#txtToTerm")).setValue("");
		Grid grd = (Grid) boxDetails.getParent().getParent().getParent().query("center").query("window").query("grid");
		
		TranslationsGridModel model = new TranslationsGridModel();
		model.set_orderBy("translationId");
		model.set_ascending(false);
		model.set_descending(true);
		
		grd.setModel(model);	
	}
	
	@Override
	public Integer recordFromForm(Box boxDetails) throws Exception {
		
		Comboitem cmbiLanguage = ((Combobox) boxDetails.query("#cmbLanguage")).getSelectedItem();
		Intbox intPosition = (Intbox) boxDetails.query("#intPosition");
		Textbox txtFromTerm = (Textbox) boxDetails.query("#txtFromTerm");
		Textbox txtToTerm = (Textbox) boxDetails.query("#txtToTerm");
		
		if (cmbiLanguage == null ||
			intPosition.getValue() == null ||
			txtFromTerm.getValue() == null ||
			txtToTerm.getValue() == null) {
			throw new SomeFieldsContainWrongValuesException();
		}
		else {
			return (new Translation(((DomainObject) cmbiLanguage.getValue()).getStringId(),
												intPosition.getValue(),
												txtFromTerm.getValue(),
												txtToTerm.getValue())).record();
		}
	}

	@Override
	public Integer cancelFromForm(Box boxDetails) throws Exception {
		return null;
	}

	@Override
	public Integer closeFromForm(Box boxDetails) throws Exception {
		return null;
	}

	@Override
	public Integer billFromForm(Box boxDetails) {
		return null;
	}

	@Override
	public Integer makeFromForm(Component boxDetails) {
		return null;
	}
}
