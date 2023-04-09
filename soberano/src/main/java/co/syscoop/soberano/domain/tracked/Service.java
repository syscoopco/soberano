package co.syscoop.soberano.domain.tracked;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import co.syscoop.soberano.domain.untracked.DomainObject;

public class Service extends TrackedObject { 

	public Service(Integer id) {
		super(id);
	}
	
	public Service(Integer id, String name) {
		super(id, name);
	}
	
	public Service(Integer id, 
					Integer entityTypeInstanceId, 
					String code,
					String name) {
		super(id, entityTypeInstanceId, name);
		this.setStringId(code);
		this.setQualifiedName(name + " : " + code);		
	}
	
	public Service() {
		getAllQuery = "SELECT * FROM soberano.\"fn_Service_getAll\"(:loginname)";
		getAllQueryNamedParameters = new HashMap<String, Object>();
	}
	
	@Override
	public Integer record() throws Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		recordQuery = "SELECT soberano.\"fn_Service_create\"(:itemCode, "
				+ "											:itemName, "
				+ "											:loginname) AS queryresult";
		recordParameters = new MapSqlParameterSource();
		recordParameters.addValue("itemCode", this.getStringId());
		recordParameters.addValue("itemName", this.getName());
		
		Integer qryResult = super.record();
		return qryResult > 0 ? qryResult : -1;
	}
	
	@Override
	public Integer modify() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		modifyQuery = "SELECT soberano.\"fn_Service_modify\"(:itemId, "
						+ " 								:itemCode, "
						+ " 								:itemName, "
						+ "									:loginname) AS queryresult";
		modifyParameters = new MapSqlParameterSource();
		modifyParameters.addValue("itemId", this.getId());
		modifyParameters.addValue("itemCode", this.getStringId());
		modifyParameters.addValue("itemName", this.getName());
		
		Integer qryResult = super.modify();
		return qryResult >= 0 ? qryResult : -1;
	}
	
	@Override
	public Integer disable() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		disableQuery = "SELECT soberano.\"fn_Service_disable\"(:itemId, "
				+ "											:loginname) AS queryresult";
		disableParameters = new MapSqlParameterSource();
		disableParameters.addValue("itemId", this.getId());
		
		Integer qryResult = super.disable();
		return qryResult >= 0 ? qryResult : -1;
	}
	
	@Override
	public List<DomainObject> getAll(Boolean stringId) throws SQLException {	
		return super.getAll(false);
	}
		
	public final class ServiceMapper implements RowMapper<Object> {

		public Service mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			try {
				Service Service = null;
				int id = rs.getInt("itemId");
				if (!rs.wasNull()) {
					Service = new Service(id,
										rs.getInt("entityTypeInstanceId"),
										rs.getString("itemCode"),
										rs.getString("itemName"));
				}
				return Service;
			}
			catch(Exception ex)
			{
				throw ex;
			}			
	    }
	}
	
	@Override
	public void get() throws SQLException {
		
		getQuery = "SELECT * FROM soberano.\"fn_Service_get\"(:itemId, :loginname)";
		getParameters = new HashMap<String, Object>();
		getParameters.put("itemId", this.getId());
		super.get(new ServiceMapper());
	}

	@Override
	protected void copyFrom(Object sourceObject) {
		
		Service sourceService = (Service) sourceObject;
		setId(sourceService.getId());
		setEntityTypeInstanceId(sourceService.getEntityTypeInstanceId());
		setName(sourceService.getName());
		setStringId(sourceService.getStringId());
	}
	
	@Override
	public Integer print() throws SQLException {
		
		// TODO print a report on the object
		return null;
	}
}
