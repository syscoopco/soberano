package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;
import co.syscoop.soberano.helper.xml.SimpleElement;

public class CustomReferenceModes extends SimpleElement {

	//xml child elements
	private List<CustomReferenceMode> customReferenceModes = new ArrayList<CustomReferenceMode>();
				
	//methods
	public void addCustomReferenceMode(CustomReferenceMode customReferenceMode) {customReferenceModes.add(customReferenceMode);}
	
    public List<CustomReferenceMode> getCustomReferenceModes() {return customReferenceModes;}
}
