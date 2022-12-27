package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;

public class Reading extends ModelElement {
	
	//xml attributes
	private String data;
	
	//xml child elements
	private List<ExpandedData> expandedDatas = new ArrayList<ExpandedData>();
	
	//methods
	public void addExpandedData(ExpandedData expandedData) {expandedDatas.add(expandedData);}
	
    public List<ExpandedData> getExpandedDatas() {return expandedDatas;}

	//methods
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
}
