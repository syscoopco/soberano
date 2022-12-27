package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.ArrayList;
import java.util.List;
import co.syscoop.soberano.helper.xml.SimpleElement;

public class RoleSequences extends SimpleElement {
	
	//xml child elements
	private List<RoleSequence> roleSequences = new ArrayList<RoleSequence>();
	private List<JoinRule> joinRules = new ArrayList<JoinRule>();
		
	//methods
	public void addRoleSequence(RoleSequence roleSequence) {roleSequences.add(roleSequence);}
	
    public List<RoleSequence> getRoleSequences() {return roleSequences;}
    
    public void addJoinRule(JoinRule joinRule) {joinRules.add(joinRule);}
	
    public List<JoinRule> getJoinRules() {return joinRules;}
}
