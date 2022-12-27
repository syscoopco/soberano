package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;
import co.syscoop.soberano.helper.xml.SimpleElement;

public class InformalRule extends SimpleElement {

	//xml child elements
	private List<DerivationNote> derivationNotes = new ArrayList<DerivationNote>();
	
	//methods
	public void addDerivationNote(DerivationNote derivationNote) {derivationNotes.add(derivationNote);}
	
    public List<DerivationNote> getDerivationNotes() {return derivationNotes;}
}
