package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.ArrayList;
import java.util.List;

public class RolePath extends ModelElement{

	//xml attributes
	private String splitCombinationOperator;
	
	//xml child elements
	private List<RootObjectType> rootObjectTypes = new ArrayList<RootObjectType>();
	private List<SubPaths> subPathss = new ArrayList<SubPaths>();
	private List<PathedRoles> pathedRoless = new ArrayList<PathedRoles>();
		
	//methods
	public void addRootObjectType(RootObjectType rootObjectType) {rootObjectTypes.add(rootObjectType);}
		
	public List<RootObjectType> getRootObjectTypes() {return rootObjectTypes;}
	
	public void addSubPaths(SubPaths subPaths) {subPathss.add(subPaths);}
	
	public List<SubPaths> getSubPathss() {return subPathss;}
	
	public void addPathedRoles(PathedRoles pathedRoles) {pathedRoless.add(pathedRoles);}
	
	public List<PathedRoles> getPathedRoless() {return pathedRoless;}
	    
	public String getSplitCombinationOperator() {
		return splitCombinationOperator;
	}
	
	@Override
	public void setAttributeValue(String name, String value) throws Exception {
		
		switch(name) {
			
			case "SplitCombinationOperator":
				splitCombinationOperator = value;
				break;
			default:
				super.setAttributeValue(name, value);
		}
    }
}
