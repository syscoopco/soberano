package co.syscoop.soberano.domain.untracked;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp2.BasicDataSource;
import co.syscoop.soberano.beans.SoberanoDatasource;
import co.syscoop.soberano.database.relational.DaoBase;
import co.syscoop.soberano.domain.untracked.helper.DomainObjectMapper;
import co.syscoop.soberano.domain.untracked.helper.DomainObjectMapperWithStringId;
import co.syscoop.soberano.util.SpringUtility;

public class DomainObject implements IDomainObject {

	private Integer id = 0;
	private String stringId = "";
	private String name = "";
	
	//it is expected queries and parameters to be set by subclasses
	protected String getAllQuery = "";
	protected Map<String, Object> getAllQueryNamedParameters = null;
	
	public DomainObject() {};
	
	public DomainObject(Integer id) {
		this.setId(id);
	}
	
	public DomainObject(String stringId) {
		this.setStringId(stringId);
	}
		
	public DomainObject(Integer id, String name) {
		this.setId(id);
		this.setName(name);
	}
	
	public DomainObject(String stringId, String name) {
		this.setStringId(stringId);
		this.setName(name);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	private class DomainObjectDao extends DaoBase {

		public DomainObjectDao(BasicDataSource dataSource) {
			super(dataSource);
		}
		
		public List<DomainObject> getAll(Boolean stringId) throws SQLException {			
			return query(getAllQuery, getAllQueryNamedParameters, stringId ? new DomainObjectMapperWithStringId() : new DomainObjectMapper());
		}
	}
	
	private DomainObjectDao domainObjectDao = new DomainObjectDao(((SoberanoDatasource) SpringUtility.applicationContext().getBean("soberanoDatasource")).getDataSource());

	@Override
	public List<DomainObject> getAll(Boolean stringId) throws SQLException {		
		return domainObjectDao.getAll(stringId);
	}

	public String getStringId() {
		return stringId;
	}

	public void setStringId(String stringId) {
		this.stringId = stringId;
	}
}
