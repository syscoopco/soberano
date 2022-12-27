package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;
import co.syscoop.soberano.helper.xml.SimpleElement;

public class Readings extends SimpleElement {

	//xml child elements
	private List<Reading> readings = new ArrayList<Reading>();
	
	//methods
	public void addReading(Reading reading) {readings.add(reading);}
	
    public List<Reading> getReadings() {return readings;}
}
