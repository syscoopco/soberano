package co.syscoop.soberano.domain.tracked;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import org.zkoss.util.Locales;

import co.syscoop.soberano.domain.untracked.DomainObject;

public class Unit extends TrackedObject {

	private String acronym = "";
	
	public Unit(Integer id) {
		super(id);
	}
	
	public Unit(Integer id, 
					Integer entityTypeInstanceId, 
					String name,
					String acronym) {
		super(id, entityTypeInstanceId, name);
		this.setAcronym(acronym);
		this.setQualifiedName(acronym + " : " + name );
	}
	
	public Unit() {
		getAllQuery = "SELECT \"domainObjectId\", substring(\"domainObjectName\", 4) \"domainObjectName\" "
						+ "FROM (SELECT * FROM soberano.\"fn_Unit_getAll\"(:loginname) " 
						+ "WHERE position('" + Locales.getCurrent().getLanguage() + ":' in \"domainObjectName\") > 0) sq";
		getAllQueryNamedParameters = new HashMap<String, Object>();
	}
	
	@Override
	public List<DomainObject> getAll(Boolean stringId) throws SQLException {	
		return super.getAll(false);
	}
		
	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	@Override
	public void get() throws SQLException {
		// TODO Auto-generated method stub		
	}

	@Override
	public Integer print() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void copyFrom(Object object) {
		// TODO Auto-generated method stub		
	}
}
