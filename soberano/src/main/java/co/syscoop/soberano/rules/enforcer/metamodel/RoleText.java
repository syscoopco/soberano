package co.syscoop.soberano.rules.enforcer.metamodel;

import co.syscoop.soberano.helper.xml.SimpleElement;

public class RoleText extends SimpleElement {

	//xml attributes
	private String roleIndex;
	private String followingText;
	
	//methods
	public String getRoleIndex() {
		return roleIndex;
	}
	
	public String getFollowingText() {
		return followingText;
	}
	
	@Override
	public void setAttributeValue(String name, String value) throws Exception {
		
		switch(name) {
			
			case "RoleIndex":
				roleIndex = value;
				break;
			case "FollowingText":
				followingText = value;
				break;
			default:
				super.setAttributeValue(name, value);
		}
    }
}
