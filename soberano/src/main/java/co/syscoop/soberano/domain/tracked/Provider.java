package co.syscoop.soberano.domain.tracked;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.SoberanoException;

public class Provider extends TrackedObject {

	public Provider(Integer id) {
		super(id);
	}
	
	public Provider(Integer id, 
					Integer entityTypeInstanceId, 
					String name) {
		super(id, entityTypeInstanceId, name);
		this.setQualifiedName(name);
	}
	
	public Provider() {
		getAllQuery = "SELECT * FROM soberano.\"" + "fn_Provider_getAll\"(:loginname)";
		getAllQueryNamedParameters = new HashMap<String, Object>();
	}
	
	@Override
	public Integer record() throws Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		recordQuery = "SELECT soberano.\"fn_Provider_create\"(:providerName, "
				+ "											:loginname) AS queryresult";
		recordParameters = new MapSqlParameterSource();
		recordParameters.addValue("providerName", this.getName());
		
		Integer qryResult = super.record();
		return qryResult > 0 ? qryResult : -1;
	}
	
	@Override
	public Integer modify() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		modifyQuery = "SELECT soberano.\"fn_Provider_modify\"(:providerId, "
				+ "											:providerName, "
				+ "											:loginname) AS queryresult";
		modifyParameters = new MapSqlParameterSource();
		modifyParameters.addValue("providerId", this.getId());
		modifyParameters.addValue("providerName", this.getName());
		
		Integer qryResult = super.modify();
		return qryResult == 0 ? qryResult : -1;
	}
	
	@Override
	public Integer disable() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		disableQuery = "SELECT soberano.\"fn_Provider_disable\"(:providerId, "
				+ "											:loginname) AS queryresult";
		disableParameters = new MapSqlParameterSource();
		disableParameters.addValue("providerId", this.getId());
		
		Integer qryResult = super.disable();
		return qryResult == 0 ? qryResult : -1;
	}
	
	@Override
	public List<DomainObject> getAll(Boolean stringId) throws SQLException {	
		return super.getAll(false);
	}
		
	public final class ProviderMapper implements RowMapper<Object> {

		public Provider mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			try {
				Provider provider = null;
				int id = rs.getInt("providerId");
				if (!rs.wasNull()) {
					provider = new Provider(id,
										rs.getInt("entityTypeInstanceId"),
										rs.getString("providerName"));
				}
				return provider;
			}
			catch(Exception ex)
			{
				throw ex;
			}			
	    }
	}
	
	@Override
	public void get() throws SQLException {
		
		getQuery = "SELECT * FROM soberano.\"fn_Provider_get\"(:providerId, :loginname)";
		getParameters = new HashMap<String, Object>();
		getParameters.put("providerId", this.getId());
		super.get(new ProviderMapper());
	}

	@Override
	protected void copyFrom(Object sourceObject) {
		
		Provider sourceprovider = (Provider) sourceObject;
		setId(sourceprovider.getId());
		setEntityTypeInstanceId(sourceprovider.getEntityTypeInstanceId());
		setName(sourceprovider.getName());
	}
	
	@Override
	public Integer print() throws SoberanoException {
		return null;
	}

	@Override
	public List<Object> getAll(String orderByColumn, Boolean descOrder, Integer limit, Integer offset, ResultSetExtractor<List<Object>> extractor) throws SQLException {
		return null;
	}
	
	@Override
	public Integer getCount() throws SQLException {
		return 0;
	}
}
