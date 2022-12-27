package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.ArrayList;
import java.util.List;
import co.syscoop.soberano.helper.xml.SimpleElement;

public class ReferencedBy extends SimpleElement {

	//xml child elements
	private List<FactType> factTypes = new ArrayList<FactType>();
	private List<ObjectType> objectTypes = new ArrayList<ObjectType>();
				
	//methods
	public void addFactType(FactType factType) {factTypes.add(factType);}
	
    public List<FactType> getFactTypes() {return factTypes;}
    
    public void addObjectType(ObjectType objectType) {objectTypes.add(objectType);}
	
    public List<ObjectType> getObjectTypes() {return objectTypes;}
}
