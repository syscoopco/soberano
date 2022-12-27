package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.ArrayList;
import java.util.List;
import co.syscoop.soberano.helper.xml.SimpleElement;

public class Definitions extends SimpleElement {

	//xml child elements
	private List<Definition> definitions = new ArrayList<Definition>();
	
	//methods
	public void addDefinition(Definition definition) {definitions.add(definition);}
	
    public List<Definition> getDefinitions() {return definitions;}
}
