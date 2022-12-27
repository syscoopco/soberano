package co.syscoop.soberano.rules.enforcer.metamodel;

public class _NamedElement extends ModelElement {

	//xml attributes
	private String _Name;
	
	//methods
	public String get_Name() {
		return _Name;
	}
	
	@Override
	public void setAttributeValue(String name, String value) throws Exception {
		
		switch(name) {
			
			case "_Name":
				_Name = value;
				break;
			default:
				super.setAttributeValue(name, value);
		}
    }
}
