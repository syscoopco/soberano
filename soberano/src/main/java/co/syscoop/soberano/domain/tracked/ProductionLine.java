package co.syscoop.soberano.domain.tracked;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import co.syscoop.soberano.util.SpringUtility;
import co.syscoop.soberano.database.relational.QueryObjectResultMapper;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.domain.untracked.helper.DomainObjectQualifiedMapper;
import co.syscoop.soberano.exception.SoberanoException;

public class ProductionLine extends TrackedObject {

	private ArrayList<Integer> objectUsingThisIds = new ArrayList<Integer>();
	private ArrayList<String> objectUsingThisQualifiedNames = new ArrayList<String>();
		
	public ProductionLine(Integer id) {
		super(id);
	}
	
	public ProductionLine(Integer id, String name) {
		super(id, name);
	}
	
	public ProductionLine(Integer id, 
			Integer entityTypeInstanceId, 
			String name) {
		super(id, entityTypeInstanceId, name);
		this.setQualifiedName(name);
	}
	
	public ProductionLine(Integer id, 
			Integer entityTypeInstanceId, 
			String name,
			ArrayList<Integer> objectUsingThisIds,
			ArrayList<String> objectUsingThisQualifiedNames) {
		this(id, 
			entityTypeInstanceId, 
			name);	
		this.objectUsingThisIds = objectUsingThisIds;
		this.objectUsingThisQualifiedNames = objectUsingThisQualifiedNames;
	}
	
	public ProductionLine() {
		getAllQuery = "SELECT * FROM soberano.\"" + "fn_ProductionLine_getAll\"(:loginname)";
		getAllQueryNamedParameters = new HashMap<String, Object>();
	}
	
	@Override
	public Integer record() throws Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		recordQuery = "SELECT soberano.\"fn_ProductionLine_create\"(:productionLineName, "
				+ "											:objectUsingThisIds, "
				+ "											:loginname) AS queryresult";
		recordParameters = new MapSqlParameterSource();
		recordParameters.addValue("productionLineName", this.getName());
		recordParameters.addValue("objectUsingThisIds", createArrayOfSQLType("integer", this.getObjectUsingThisIds().toArray()));
		Integer qryResult = super.record();
		return qryResult > 0 ? qryResult : -1;
	}
	
	@Override
	public Integer modify() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		modifyQuery = "SELECT soberano.\"fn_ProductionLine_modify\"(:productionLineId,"
				+ "													:productionLineName,"
				+ "													:objectUsingThisIds,"	
				+ "													:loginname) AS queryresult";
		modifyParameters = new MapSqlParameterSource();
		modifyParameters.addValue("productionLineId", this.getId());
		modifyParameters.addValue("productionLineName", this.getName());
		modifyParameters.addValue("objectUsingThisIds", createArrayOfSQLType("integer", this.getObjectUsingThisIds().toArray()));
		Integer qryResult = super.modify();
		return qryResult == 0 ? qryResult : -1;
	}
	
	@Override
	public Integer disable() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		disableQuery = "SELECT soberano.\"fn_ProductionLine_disable\"(:productionLineId, "
				+ "											:loginname) AS queryresult";
		disableParameters = new MapSqlParameterSource();
		disableParameters.addValue("productionLineId", this.getId());
		
		Integer qryResult = super.disable();
		return qryResult == 0 ? qryResult : -1;
	}
	
	@Override
	public List<DomainObject> getAll(Boolean stringId) throws SQLException {	
		return super.getAll(false);
	}
		
	public final class ProductionLineMapper implements RowMapper<Object> {

		public ProductionLine mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			try {
				ProductionLine productionLine = null;
				int id = rs.getInt("productionLineId");
				if (!rs.wasNull()) {
					productionLine = new ProductionLine(id,
														rs.getInt("entityTypeInstanceId"), 
														rs.getString("productionLineName"));
				}
				return productionLine;
			}
			catch(Exception ex)
			{
				throw ex;
			}			
	    }
	}
	
	@Override
	public void get() throws SQLException {
		
		getQuery = "SELECT * FROM soberano.\"fn_ProductionLine_get\"(:productionLineId, :loginname)";
		getParameters = new HashMap<String, Object>();
		getParameters.put("productionLineId", this.getId());
		super.get(new ProductionLineMapper());
	}
	
	public List<Object> getObjectsUsingThis() throws SQLException {
		
		String qryStr = "SELECT * FROM soberano.\"fn_ProductionLine_getObjectsUsingThis\"(:productionLineId, :loginname)";
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("productionLineId", this.getId());
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(qryStr, parametersMap, new DomainObjectQualifiedMapper());
	}

	@Override
	protected void copyFrom(Object sourceObject) {
		
		ProductionLine sourceProductionLine = (ProductionLine) sourceObject;
		setId(sourceProductionLine.getId());
		setStringId(sourceProductionLine.getStringId());
		setEntityTypeInstanceId(sourceProductionLine.getEntityTypeInstanceId());
		setName(sourceProductionLine.getName());
		setQualifiedName(sourceProductionLine.getQualifiedName());
		setObjectUsingThisIds(sourceProductionLine.getObjectUsingThisIds());
		setObjectUsingThisQualifiedNames(sourceProductionLine.getObjectUsingThisQualifiedNames());
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

	public ArrayList<Integer> getObjectUsingThisIds() {
		return objectUsingThisIds;
	}

	public void setObjectUsingThisIds(ArrayList<Integer> objectUsingThisIds) {
		this.objectUsingThisIds = objectUsingThisIds;
	}

	public ArrayList<String> getObjectUsingThisQualifiedNames() {
		return objectUsingThisQualifiedNames;
	}

	public void setObjectUsingThisQualifiedNames(ArrayList<String> objectUsingThisQualifiedNames) {
		this.objectUsingThisQualifiedNames = objectUsingThisQualifiedNames;
	}
	
	public Integer omitProcessRunOutputAllocation(Integer itemId) throws Exception {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT soberano.\"fn_ProductionLine_omitProcessRunOutputAllocation\"(:itemId, "
							+ "								:loginname) AS queryresult";		
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("itemId", itemId);
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (Integer) super.query(qryStr, parametersMap, new QueryObjectResultMapper()).get(0);
	}
}
