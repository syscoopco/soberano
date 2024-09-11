package co.syscoop.soberano.util.ui;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ListModel;

import co.syscoop.soberano.domain.untracked.DomainObject;

public class ComboboxHelper {
	
	public static Comboitem getItemByText(Combobox box, String text) throws IllegalArgumentException {
		
		for (Comboitem item : box.getItems()){
			if (item.getLabel().equals(text)) {
				return item;
			}
		}
		return null;
	}
	
	public static Comboitem getItemByValue(Combobox box, String value) throws IllegalArgumentException {
		
		//non intellisense combobox
		for (Comboitem item : box.getItems()){
			if (item.getValue().toString().equals(value))
				return item;
		}
		
		//intellisense combobox. the value is the corresponding domain object's id recorded in the database
		ListModel<Object> model = box.getModel();
		for (int i = 0; i < model.getSize(); i++) {
			DomainObject doo = (DomainObject) model.getElementAt(i);
			if (doo.getStringId().equals(value)) {
				box.setText(doo.getName());				
				Comboitem ci = new Comboitem(doo.getName());
				ci.setValue(doo);
				box.getItems().add(ci);
				return ci;
			}
		}
		throw new IllegalArgumentException(value + " wasn't found in Combobox store");
	}
	
	public static Comboitem getItemByValue(Combobox box, Integer value) throws IllegalArgumentException {
		
		//non intellisense combobox
		for (Comboitem item : box.getItems()){
			if (item.getValue().getClass().getName().contains("DomainObject")) {
				if (((DomainObject) item.getValue()).getId() == value)
					return item;
			}
			else {			
				if (Integer.parseInt(item.getValue()) == value)
					return item;
			}
		}
		
		//intellisense combobox. the value is the corresponding domain object's id recorded in the database
		ListModel<Object> model = box.getModel();
		for(int i = 0; i < model.getSize(); i++) {
			DomainObject doo = (DomainObject) model.getElementAt(i);
			if (doo.getId().equals(value)) {
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