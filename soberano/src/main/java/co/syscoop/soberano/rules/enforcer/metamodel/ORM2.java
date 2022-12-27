package co.syscoop.soberano.rules.enforcer.metamodel;

import java.util.*;
import co.syscoop.soberano.helper.xml.SimpleElement;

public class ORM2 extends SimpleElement {

	//xml attributes
	private String xmlns_orm;
	private String xmlns_ormDiagram;
	private String xmlns_ormRoot;
	private String xmlns_oial;
	private String xmlns_odt;
	private String xmlns_rcd;
	private String xmlns_ddt;
	private String xmlns_ormtooial;
	private String xmlns_oialtocdb;
	
	//xml child elements
	private List<ORMModel> ormModels = new ArrayList<ORMModel>();
	
	//methods
	public void addORMModel(ORMModel ormModel) {ormModels.add(ormModel);}
	
    public List<ORMModel> getORMModels() {return ormModels;}
    
    public String getXmlns_orm() {
		return xmlns_orm;
	}
    
	public String getXmlns_ormDiagram() {
		return xmlns_ormDiagram;
	}
	
	public String getXmlns_ormRoot() {
		return xmlns_ormRoot;
	}
	
	@Override
	public void setAttributeValue(String name, String value) throws Exception {
		
		switch(name) {
			
			case "xmlns:orm":
				xmlns_orm = value;
				break;
			case "xmlns:ormDiagram":
				xmlns_ormDiagram = value;
				break;
			case "xmlns:ormRoot":
				xmlns_ormRoot = value;
				break;
			case "xmlns:oial":
				xmlns_oial = value;
				break;
			case "xmlns:odt":
				xmlns_odt = value;
				break;
			case "xmlns:rcd":
				xmlns_rcd = value;
				break;
			case "xmlns:ddt":
				xmlns_ddt = value;
				break;
			case "xmlns:ormtooial":
				xmlns_ormtooial = value;
				break;
			case "xmlns:oialtocdb":
				xmlns_oialtocdb = value;
				break;
			default:
				super.setAttributeValue(name, value);
		}
    }

	public String getXmlns_oial() {
		return xmlns_oial;
	}

	public String getXmlns_odt() {
		return xmlns_odt;
	}

	public String getXmlns_rcd() {
		return xmlns_rcd;
	}

	public String getXmlns_ddt() {
		return xmlns_ddt;
	}

	public String getXmlns_ormtooial() {
		return xmlns_ormtooial;
	}

	public String getXmlns_oialtocdb() {
		return xmlns_oialtocdb;
	}
}
