package co.syscoop.soberano.domain.tracked;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import co.syscoop.soberano.domain.untracked.DomainObject;

public class Counter extends TrackedObject {

	private Integer numberOfReceivers = 0;
	private Boolean isSurcharged = false;
	private Boolean isEnabled = false;
	
	public Counter(Integer id) {
		super(id);
	}
	
	public Counter(Integer id, 
					Integer entityTypeInstanceId, 
					String code, 
					Integer numberOfReceivers, 
					Boolean isSurcharged, 
					Boolean isEnabled) {
		super(id, entityTypeInstanceId, code);
		this.setStringId(code);
		this.setQualifiedName(code);		
		this.setNumberOfReceivers(numberOfReceivers);
		this.setIsSurcharged(isSurcharged);
		this.setIsEnabled(isEnabled);
	}
	
	public Counter() {
		getAllQuery = "SELECT * FROM soberano.\"" + "fn_Counter_getAll\"" + "(:loginname)";
		getAllQueryNamedParameters = new HashMap<String, Object>();
	}
	
	@Override
	public Integer record() throws Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		recordQuery = "SELECT soberano.\"fn_Counter_create\"(:counterCode, "
				+ "											:numberOfReceivers, "
				+ "											:isSurcharged, "
				+ "											:isEnabled, "
				+ "											:loginname) AS queryresult";
		recordParameters = new MapSqlParameterSource();
		recordParameters.addValue("counterCode", this.getStringId());
		recordParameters.addValue("numberOfReceivers", this.numberOfReceivers);
		recordParameters.addValue("isSurcharged", this.isSurcharged);
		recordParameters.addValue("isEnabled", this.isEnabled);
		
		Integer qryResult = super.record();
		return qryResult > 0 ? qryResult : -1;
	}
	
	@Override
	public Integer modify() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		modifyQuery = "SELECT soberano.\"fn_Counter_modify\"(:counterId, "
				+ "											:counterCode, "
				+ "											:numberOfReceivers, "
				+ "											:isSurcharged, "
				+ "											:isEnabled, "
				+ "											:loginname) AS queryresult";
		modifyParameters = new MapSqlParameterSource();
		modifyParameters.addValue("counterId", this.getId());
		modifyParameters.addValue("counterCode", this.getStringId());
		modifyParameters.addValue("numberOfReceivers", this.numberOfReceivers);
		modifyParameters.addValue("isSurcharged", this.isSurcharged);
		modifyParameters.addValue("isEnabled", this.isEnabled);
		
		Integer qryResult = super.modify();
		return qryResult >= 0 ? qryResult : -1;
	}
	
	@Override
	public Integer disable() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		disableQuery = "SELECT soberano.\"fn_Counter_disable\"(:counterId, "
				+ "											:loginname) AS queryresult";
		disableParameters = new MapSqlParameterSource();
		disableParameters.addValue("counterId", this.getId());
		
		Integer qryResult = super.disable();
		return qryResult >= 0 ? qryResult : -1;
	}
	
	@Override
	public List<DomainObject> getAll(Boolean stringId) throws SQLException {	
		return super.getAll(false);
	}
		
	public final class CounterMapper implements RowMapper<Object> {

		public Counter mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			try {
				Counter counter = null;
				int id = rs.getInt("counterId");
				if (!rs.wasNull()) {
					counter = new Counter(id,
										rs.getInt("entityTypeInstanceId"),
										rs.getString("counterCode"),
										rs.getInt("numberOfReceivers"),
										rs.getBoolean("isSurcharged"),
										rs.getBoolean("isEnabled"));
				}
				return counter;
			}
			catch(Exception ex)
			{
				throw ex;
			}			
	    }
	}
	
	@Override
	public void get() throws SQLException {
		
		getQuery = "SELECT * FROM soberano.\"fn_Counter_get\"(:counterId, :loginname)";
		getParameters = new HashMap<String, Object>();
		getParameters.put("counterId", this.getId());
		super.get(new CounterMapper());
	}

	@Override
	protected void copyFrom(Object sourceObject) {
		
		Counter sourcecounter = (Counter) sourceObject;
		setId(sourcecounter.getId());
		setEntityTypeInstanceId(sourcecounter.getEntityTypeInstanceId());
		setStringId(sourcecounter.getStringId());
		setNumberOfReceivers(sourcecounter.getNumberOfReceivers());
		setIsSurcharged(sourcecounter.getIsSurcharged());
		setIsEnabled(sourcecounter.getIsEnabled());
	}
	
	@Override
	public Integer print() throws SQLException {
		
		// TODO print a report on the object
		return null;
	}

	public Integer getNumberOfReceivers() {
		return numberOfReceivers;
	}

	public void setNumberOfReceivers(Integer numberOfReceivers) {
		this.numberOfReceivers = numberOfReceivers;
	}

	public Boolean getIsSurcharged() {
		return isSurcharged;
	}

	public void setIsSurcharged(Boolean isSurcharged) {
		this.isSurcharged = isSurcharged;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
}
