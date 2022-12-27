package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.ArrayList;
import java.util.List;
import co.syscoop.soberano.helper.xml.SimpleElement;

public class ModelElement extends SimpleElement {

	//xml attributes
	private String id;
	
	//xml child elements
	private List<Definitions> definitionss = new ArrayList<Definitions>();
	
	private List<Notes> notess = new ArrayList<Notes>();
	
	//methods
	public void addDefinitions(Definitions definitions) {definitionss.add(definitions);}
   	
    public List<Definitions> getDefinitionss() {return definitionss;}
	
	public void addNotes(Notes notes) {notess.add(notes);}
   	
    public List<Notes> getNotess() {return notess;}
	
	public String getId() {
		return id;
	}
	
	@Override
	public void setAttributeValue(String name, String value) throws Exception {
		
		switch(name) {
			
			case "id":
				id = value;
				break;
			default:
				super.setAttributeValue(name, value);
		}
    }
}
