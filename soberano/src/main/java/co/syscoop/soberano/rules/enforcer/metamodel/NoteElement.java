package co.syscoop.soberano.rules.enforcer.metamodel;

public class NoteElement extends ModelElement{

	//xml attributes
	private String text;
		
	//methods
	public String getText() {
		
		return text;
	}
	
	public void setText(String text) {
		
		this.text = text;
	}
}
