package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.ArrayList;
import java.util.List;

public class JoinPath extends ModelElement {

	//xml attributes
	private String isAutomatic;
	
	//xml child elements
	private List<PathComponents> pathComponentss = new ArrayList<PathComponents>();
	private List<JoinPathProjections> joinPathProjectionss = new ArrayList<JoinPathProjections>();
	
	//methods
	public String getIsAutomatic() {
		return isAutomatic;
	}
	
	@Override
	public void setAttributeValue(String name, String value) throws Exception {
		
		switch(name) {
			
			case "IsAutomatic":
				isAutomatic = value;
				break;
			default:
				super.setAttributeValue(name, value);
		}
    }
	
	public void addPathComponents(PathComponents pathComponents) {pathComponentss.add(pathComponents);}
	
    public List<PathComponents> getPathComponentss() {return pathComponentss;}
    
    public void addJoinPathProjections(JoinPathProjections joinPathProjections) {joinPathProjectionss.add(joinPathProjections);}
	
    public List<JoinPathProjections> getJoinPathProjectionss() {return joinPathProjectionss;}
}
