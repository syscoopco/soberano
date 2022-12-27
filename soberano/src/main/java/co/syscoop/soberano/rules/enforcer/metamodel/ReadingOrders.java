package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;
import co.syscoop.soberano.helper.xml.SimpleElement;

public class ReadingOrders extends SimpleElement{
	
	//xml child elements
	private List<ReadingOrder> readingOrders = new ArrayList<ReadingOrder>();
	
	//methods
	public void addReadingOrder(ReadingOrder readingOrder) {readingOrders.add(readingOrder);}
	
    public List<ReadingOrder> getReadingOrders() {return readingOrders;}
}
