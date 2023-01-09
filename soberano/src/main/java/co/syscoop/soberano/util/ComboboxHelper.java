package co.syscoop.soberano.util;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ListModel;

import co.syscoop.soberano.domain.untracked.DomainObject;

public class ComboboxHelper {
	public static Comboitem getItemByValue(Combobox box, String value) throws IllegalArgumentException {
		
		//non intellisense combobox
		for(Comboitem item : box.getItems()){
			if(item.getValue().toString().equals(value))
				return item;
		}
		
		//intellisense combobox. the value is the corresponding domain object's id recorded in the database
		ListModel<Object> model = box.getModel();
		for(int i = 0; i < model.getSize(); i++) {
			DomainObject doo = (DomainObject) model.getElementAt(i);
			if(doo.getStringId().equals(value)) {
				box.setText(doo.getName());				
				Comboitem ci = new Comboitem(doo.getName());
				ci.setValue(doo);
				box.getItems().add(ci);
				return ci;
			}
		}
		throw new IllegalArgumentException(value + " wasn't found in Combobox store");
	}
}