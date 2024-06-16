package co.syscoop.soberano.domain.tracked;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.zkoss.util.Locales;

import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.SoberanoException;

public class Unit extends TrackedObject {

	private String acronym = "";
	
	public Unit(Integer id) {
		super(id);
	}
	
	public Unit(Integer id, String name) {
		super(id, name);
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
		getAllQuery = "SELECT \"domainObjectId\", substring(\"domainObjectName\", 6) \"domainObjectName\" "
						+ "FROM (SELECT * FROM soberano.\"fn_Unit_getAll\"(:loginname) " 
						+ "WHERE position('" + Locales.getCurrent().getLanguage() + " :' in \"domainObjectName\") > 0) sq";
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
	}

	@Override
	public Integer print() throws SoberanoException {
		return null;
	}

	@Override
	protected void copyFrom(Object object) {
	}

	@Override
	public List<Object> getAll(String orderByColumn, Boolean descOrder, Integer limit, Integer offset, ResultSetExtractor<List<Object>> extractor) throws SQLException {
		return null;
	}
	
	@Override
	public Integer getCount() throws SQLException {
		return 0;
	}
	
	public List<DomainObject> getAll(Integer materialId) throws SQLException {
		String qryStr = "SELECT * FROM soberano.\"fn_Unit_getAll\"(:materialId, :lang, :loginname)";
		HashMap<String, Object> qryNamedParameters = new HashMap<String, Object>();
		qryNamedParameters.put("materialId", materialId);
		qryNamedParameters.put("lang",  Locales.getCurrent().getLanguage());
		return trackedObjectDao.getAll(qryStr, qryNamedParameters, false);
	}
		
	public List<DomainObject> getAllForInventoryItem(String inventoryItemCode) throws SQLException {
		String qryStr = "SELECT * FROM soberano.\"fn_Unit_getAllForInventoryItem\"(:inventoryItemCode, :lang, :loginname)";
		HashMap<String, Object> qryNamedParameters = new HashMap<String, Object>();
		qryNamedParameters.put("inventoryItemCode", inventoryItemCode);
		qryNamedParameters.put("lang",  Locales.getCurrent().getLanguage());
		return trackedObjectDao.getAll(qryStr, qryNamedParameters, false);
	}
}
