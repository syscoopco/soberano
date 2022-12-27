package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;
import co.syscoop.soberano.helper.xml.SimpleElement;

public class ReferenceModeKinds extends SimpleElement {

	//xml child elements
	private List<ReferenceModeKind> referenceModeKinds = new ArrayList<ReferenceModeKind>();
				
	//methods
	public void addReferenceModeKind(ReferenceModeKind referenceModeKind) {referenceModeKinds.add(referenceModeKind);}
	
    public List<ReferenceModeKind> getReferenceModeKinds() {return referenceModeKinds;}
}
