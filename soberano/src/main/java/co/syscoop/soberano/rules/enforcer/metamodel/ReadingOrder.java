package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.ArrayList;
import java.util.List;

public class ReadingOrder extends ModelElement {
	
	//xml child elements
	private List<Readings> readingss = new ArrayList<Readings>();
	private List<RoleSequence> roleSequences = new ArrayList<RoleSequence>();
	
	//methods
	public void addReadings(Readings readings) {readingss.add(readings);}
	
    public List<Readings> getReadingss() {return readingss;}
    
    public void addRoleSequence(RoleSequence roleSequence) {roleSequences.add(roleSequence);}
	
    public List<RoleSequence> getRoleSequences() {return roleSequences;}
}
