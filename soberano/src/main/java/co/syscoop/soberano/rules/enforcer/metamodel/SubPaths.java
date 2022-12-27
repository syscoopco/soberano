package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;
import co.syscoop.soberano.helper.xml.SimpleElement;

public class SubPaths extends SimpleElement{

	//xml child elements
	private List<SubPath> subPaths = new ArrayList<SubPath>();
			
	//methods
	public void addSubPath(SubPath subPath) {subPaths.add(subPath);}
		
	public List<SubPath> getSubPaths() {return subPaths;}
}
