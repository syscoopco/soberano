package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;
import co.syscoop.soberano.helper.xml.SimpleElement;

public class SubtypeDerivationRule extends SimpleElement {
	
	//xml child elements
	private List<SubtypeDerivationPath> subtypeDerivationPaths = new ArrayList<SubtypeDerivationPath>();
				
	//methods
	public void addSubtypeDerivationPath(SubtypeDerivationPath subtypeDerivationPath) {subtypeDerivationPaths.add(subtypeDerivationPath);}
	
    public List<SubtypeDerivationPath> getSubtypeDerivationPaths() {return subtypeDerivationPaths;}
}
