package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.ArrayList;
import java.util.List;

public class CustomReferenceMode extends NamedElement{

	//xml child elements
	private List<CustomFormatString> customFormatStrings = new ArrayList<CustomFormatString>();
	private List<Kind> kinds = new ArrayList<Kind>();
					
	//methods
	public void addCustomFormatString(CustomFormatString customFormatString) {customFormatStrings.add(customFormatString);}
	
    public List<CustomFormatString> getCustomFormatStrings() {return customFormatStrings;}
	
	public void addKind(Kind kind) {kinds.add(kind);}
	
    public List<Kind> getKinds() {return kinds;}
}
