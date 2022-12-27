package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;

public class Role extends NamedElement {

	//xml attributes
	private String _isMandatory;
	private String _Multiplicity;
	private String ref;
	
	//xml child elements
	private List<RolePlayer> rolePlayers = new ArrayList<RolePlayer>();
	private List<RoleInstances> roleInstancess = new ArrayList<RoleInstances>();
	private List<ValueRestriction> valueRestrictions = new ArrayList<ValueRestriction>();
	
	//methods
	public void addValueRestriction(ValueRestriction valueRestriction) {valueRestrictions.add(valueRestriction);}
	
    public List<ValueRestriction> getValueRestrictions() {return valueRestrictions;}
	
	public void addRolePlayer(RolePlayer rolePlayer) {rolePlayers.add(rolePlayer);}
	
    public List<RolePlayer> getRolePlayers() {return rolePlayers;}
    
    public void addRoleInstances(RoleInstances roleInstances) {roleInstancess.add(roleInstances);}
	
    public List<RoleInstances> getRoleInstancess() {return roleInstancess;}
	
	public String get_isMandatory() {
		return _isMandatory;
	}
	
	public String get_Multiplicity() {
		return _Multiplicity;
	}
	
	public String getRef() {
		return ref;
	}
	
	@Override
	public void setAttributeValue(String name, String value) throws Exception {
		
		switch(name) {
			
			case "_IsMandatory":
				_isMandatory = value;
				break;
			case "_Multiplicity":
				_Multiplicity = value;
				break;
			case "ref":
				ref = value;
				break;
			default:
				super.setAttributeValue(name, value);
		}
    }
}
