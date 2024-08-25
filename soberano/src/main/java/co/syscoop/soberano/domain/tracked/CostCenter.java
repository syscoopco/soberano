package co.syscoop.soberano.domain.tracked;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.domain.untracked.PrintableData;
import co.syscoop.soberano.exception.SoberanoException;

public class CostCenter extends TrackedObject {

	private Integer inputWarehouse = 0;
	private Integer outputWarehouse = 0;
	
	public CostCenter(Integer id) {
		super(id);
	}
	
	public CostCenter(Integer id, 
					Integer entityTypeInstanceId, 
					String name,
					Integer inputWarehouse, 
					Integer outputWarehouse) {
		super(id, entityTypeInstanceId, name);
		this.setQualifiedName(name);		
		this.setInputWarehouse(inputWarehouse);
		this.setOutputWarehouse(outputWarehouse);
	}
	
	public CostCenter() {
		getAllQuery = "SELECT * FROM soberano.\"" + "fn_CostCenter_getAll\"(:loginname)";
		getAllQueryNamedParameters = new HashMap<String, Object>();
	}
	
	@Override
	public Integer record() throws Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		recordQuery = "SELECT soberano.\"fn_CostCenter_create\"(:costCenterName, "
				+ "											:inputWarehouse, "
				+ "											:outputWarehouse, "
				+ "											:loginname) AS queryresult";
		recordParameters = new MapSqlParameterSource();
		recordParameters.addValue("costCenterName", this.getName());
		recordParameters.addValue("inputWarehouse", this.getInputWarehouse());
		recordParameters.addValue("outputWarehouse", this.getOutputWarehouse());
		
		Integer qryResult = super.record();
		return qryResult > 0 ? qryResult : -1;
	}
	
	@Override
	public Integer modify() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		modifyQuery = "SELECT soberano.\"fn_CostCenter_modify\"(:costCenterId, "
						+ " 								:costCenterName, "
						+ "									:inputWarehouse, "
						+ "									:outputWarehouse, "
						+ "									:loginname) AS queryresult";
		modifyParameters = new MapSqlParameterSource();
		modifyParameters.addValue("costCenterId", this.getId());
		modifyParameters.addValue("costCenterName", this.getName());
		modifyParameters.addValue("inputWarehouse", this.getInputWarehouse());
		modifyParameters.addValue("outputWarehouse", this.getOutputWarehouse());
		
		Integer qryResult = super.modify();
		return qryResult == 0 ? qryResult : -1;
	}
	
	@Override
	public Integer disable() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		disableQuery = "SELECT soberano.\"fn_CostCenter_disable\"(:costCenterId, "
				+ "											:loginname) AS queryresult";
		disableParameters = new MapSqlParameterSource();
		disableParameters.addValue("costCenterId", this.getId());
		
		Integer qryResult = super.disable();
		return qryResult == 0 ? qryResult : -1;
	}
	
	@Override
	public List<DomainObject> getAll(Boolean stringId) throws SQLException {	
		return super.getAll(false);
	}
		
	public final class CostCenterMapper implements RowMapper<Object> {

		public CostCenter mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			try {
				CostCenter costCenter = null;
				int id = rs.getInt("costCenterId");
				if (!rs.wasNull()) {
					costCenter = new CostCenter(id,
											rs.getInt("entityTypeInstanceId"),
											rs.getString("costCenterName"),
											rs.getInt("inputWarehouse"),
											rs.getInt("outputWarehouse"));
				}
				return costCenter;
			}
			catch(Exception ex)
			{
				throw ex;
			}			
	    }
	}
	
	@Override
	public void get() throws SQLException {
		
		getQuery = "SELECT * FROM soberano.\"fn_CostCenter_get\"(:costCenterId, :loginname)";
		getParameters = new HashMap<String, Object>();
		getParameters.put("costCenterId", this.getId());
		super.get(new CostCenterMapper());
	}

	@Override
	protected void copyFrom(Object sourceObject) {
		
		CostCenter sourceCostCenter = (CostCenter) sourceObject;
		setId(sourceCostCenter.getId());
		setEntityTypeInstanceId(sourceCostCenter.getEntityTypeInstanceId());
		setName(sourceCostCenter.getName());
		setStringId(sourceCostCenter.getStringId());
		setInputWarehouse(sourceCostCenter.getInputWarehouse());
		setOutputWarehouse(sourceCostCenter.getOutputWarehouse());
	}
	
	@Override
	public Integer print() throws SoberanoException {
		
		
		return null;
	}

	public Integer getInputWarehouse() {
		return inputWarehouse;
	}

	public void setInputWarehouse(Integer inputWarehouse) {
		this.inputWarehouse = inputWarehouse;
	}

	public Integer getOutputWarehouse() {
		return outputWarehouse;
	}

	public void setOutputWarehouse(Integer outputWarehouse) {
		this.outputWarehouse = outputWarehouse;
	}

	@Override
	public List<Object> getAll(String orderByColumn, Boolean descOrder, Integer limit, Integer offset, ResultSetExtractor<List<Object>> extractor) throws SQLException {
		return null;
	}
	
	@Override
	public Integer getCount() throws SQLException {
		return 0;
	}

	@Override
	public PrintableData getReportFull() throws SQLException {
		return null;
	}

	@Override
	public PrintableData getReportMinimal() throws SQLException {
		return null;
	}
}
