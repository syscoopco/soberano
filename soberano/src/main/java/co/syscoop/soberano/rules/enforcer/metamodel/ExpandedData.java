package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.ArrayList;
import java.util.List;
import co.syscoop.soberano.helper.xml.SimpleElement;

public class ExpandedData extends SimpleElement {
	
	//xml attributes
	private String frontText;
		
	//xml child elements
	private List<RoleText> roleTexts = new ArrayList<RoleText>();
		
	//methods
	public void addRoleText(RoleText roleText) {roleTexts.add(roleText);}
	
    public List<RoleText> getRoleTexts() {return roleTexts;}
    
    @Override
	public void setAttributeValue(String name, String value) throws Exception {
		
		switch(name) {
		
			case "FrontText":
				this.frontText = value;
				break;
			default:
				super.setAttributeValue(name, value);
		}
    }

	public String getFrontText() {
		return frontText;
	}
}
