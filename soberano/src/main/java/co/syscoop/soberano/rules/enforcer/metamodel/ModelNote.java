package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;

public class ModelNote extends NoteElement{

	//xml child elements
	private List<ReferencedBy> referencedBys = new ArrayList<ReferencedBy>();
		
	//methods
	public void addReferencedBy(ReferencedBy referencedBy) {referencedBys.add(referencedBy);}
	
    public List<ReferencedBy> getReferencedBys() {return referencedBys;}
}
