package co.syscoop.soberano.domain.tracked;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import co.syscoop.soberano.domain.untracked.DomainObject;

public class Process extends TrackedObject {

	private BigDecimal fixedCost = new BigDecimal(0.0);
	
	public Process(Integer id) {
		super(id);
	}
	
	public Process(Integer id, String name) {
		super(id, name);
	}
	
	public Process(Integer id, 
					Integer entityTypeInstanceId, 
					String name,
					BigDecimal fixedCost) {
		super(id, entityTypeInstanceId, name);
		this.setQualifiedName(name);		
		this.setFixedCost(fixedCost);
	}
	
	public Process() {
		getAllQuery = "SELECT * FROM soberano.\"" + "fn_Process_getAll\"(:loginname)";
		getAllQueryNamedParameters = new HashMap<String, Object>();
	}
	
	@Override
	public Integer record() throws Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		recordQuery = "SELECT soberano.\"fn_Process_create\"(:processName, "
				+ "											:fixedCost, "
				+ "											:loginname) AS queryresult";
		recordParameters = new MapSqlParameterSource();
		recordParameters.addValue("processName", this.getName());
		recordParameters.addValue("fixedCost", this.getFixedCost());
		
		Integer qryResult = super.record();
		return qryResult > 0 ? qryResult : -1;
	}
	
	@Override
	public Integer modify() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		modifyQuery = "SELECT soberano.\"fn_Process_modify\"(:processId, "
						+ " 								:processName, "
						+ "									:fixedCost, "
						+ "									:loginname) AS queryresult";
		modifyParameters = new MapSqlParameterSource();
		modifyParameters.addValue("processId", this.getId());
		modifyParameters.addValue("processName", this.getName());
		modifyParameters.addValue("fixedCost", this.getFixedCost());
		
		Integer qryResult = super.modify();
		return qryResult >= 0 ? qryResult : -1;
	}
	
	@Override
	public Integer disable() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		disableQuery = "SELECT soberano.\"fn_Process_disable\"(:processId, "
				+ "											:loginname) AS queryresult";
		disableParameters = new MapSqlParameterSource();
		disableParameters.addValue("processId", this.getId());
		
		Integer qryResult = super.disable();
		return qryResult >= 0 ? qryResult : -1;
	}
	
	@Override
	public List<DomainObject> getAll(Boolean stringId) throws SQLException {	
		return super.getAll(false);
	}
		
	public final class ProcessMapper implements RowMapper<Object> {

		public Process mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			try {
				Process process = null;
				int id = rs.getInt("processId");
				if (!rs.wasNull()) {
					process = new Process(id,
										rs.getInt("entityTypeInstanceId"),
										rs.getString("processName"),
										rs.getBigDecimal("fixedCost"));
				}
				return process;
			}
			catch(Exception ex)
			{
				throw ex;
			}			
	    }
	}
	
	@Override
	public void get() throws SQLException {
		
		getQuery = "SELECT * FROM soberano.\"fn_Process_get\"(:processId, :loginname)";
		getParameters = new HashMap<String, Object>();
		getParameters.put("processId", this.getId());
		super.get(new ProcessMapper());
	}

	@Override
	protected void copyFrom(Object sourceObject) {
		
		Process sourceProcess = (Process) sourceObject;
		setId(sourceProcess.getId());
		setEntityTypeInstanceId(sourceProcess.getEntityTypeInstanceId());
		setName(sourceProcess.getName());
		setStringId(sourceProcess.getStringId());
		setFixedCost(sourceProcess.getFixedCost());
	}
	
	@Override
	public Integer print() throws SQLException {
		
		// TODO print a report on the object
		return null;
	}

	public BigDecimal getFixedCost() {
		return fixedCost;
	}

	public void setFixedCost(BigDecimal fixedCost) {
		this.fixedCost = fixedCost;
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
