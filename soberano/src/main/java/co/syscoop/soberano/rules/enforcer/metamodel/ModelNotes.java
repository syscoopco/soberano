package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;
import co.syscoop.soberano.helper.xml.SimpleElement;

public class ModelNotes extends SimpleElement {

	//xml child elements
	private List<ModelNote> modelNotes = new ArrayList<ModelNote>();
	
	//methods
	public void addModelNote(ModelNote modelNote) {modelNotes.add(modelNote);}
	
    public List<ModelNote> getModelNotes() {return modelNotes;}
}
