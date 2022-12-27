package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;
import co.syscoop.soberano.helper.xml.SimpleElement;

public class Notes extends SimpleElement {

	//xml child elements
	private List<Note> notes = new ArrayList<Note>();
	
	//methods
	public void addNote(Note note) {notes.add(note);}
	
    public List<Note> getNotes() {return notes;}
}
