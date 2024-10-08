package co.syscoop.soberano.domain.tracked;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.domain.untracked.PrintableData;
import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.util.SpringUtility;

public class Warehouse extends TrackedObject {

	private Boolean isProcurementWarehouse = false;
	private Boolean isSalesWarehouse = false;
	private Boolean isLossesWarehouse = false;
	private ArrayList<Process> entryProcesses = new ArrayList<Process>();
	private ArrayList<Integer> entryProcessIds = null;
	
	private void fillEntryProcessIds() {
		entryProcessIds = new ArrayList<Integer>();
		for (Process process : entryProcesses) {
			entryProcessIds.add(process.getId());
		}
	}
	
	public Warehouse(Integer id) {
		super(id);
	}
	
	public Warehouse(Integer id, 
					Integer entityTypeInstanceId, 
					String name, 
					String code,
					Boolean isProcurementWarehouse, 
					Boolean isSalesWarehouse,
					Boolean isLossesWarehouse) {
		super(id, entityTypeInstanceId, name);
		this.setStringId(code);
		this.setQualifiedName(name + " : " + code);		
		this.setIsProcurementWarehouse(isProcurementWarehouse);
		this.setIsSalesWarehouse(isSalesWarehouse);
		this.setIsLossesWarehouse(isLossesWarehouse);
	}
	
	public Warehouse(Integer id, 
			Integer entityTypeInstanceId, 
			String name, 
			String code,
			Boolean isProcurementWarehouse, 
			Boolean isSalesWarehouse,
			Boolean isLossesWarehouse,
			ArrayList<Process> entryProcesses) {
		super(id, entityTypeInstanceId, name);
		this.setStringId(code);
		this.setQualifiedName(name + " : " + code);		
		this.setIsProcurementWarehouse(isProcurementWarehouse);
		this.setIsSalesWarehouse(isSalesWarehouse);
		this.setIsLossesWarehouse(isLossesWarehouse);
		this.entryProcesses = entryProcesses;
		fillEntryProcessIds();
	}
	
	public Warehouse() {
		getAllQuery = "SELECT * FROM soberano.\"" + "fn_Warehouse_getAll\"(:loginname)";
		getAllQueryNamedParameters = new HashMap<String, Object>();
	}
	
	@Override
	public Integer record() throws Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		recordQuery = "SELECT soberano.\"fn_Warehouse_create\"(:warehouseName, "
				+ "											:warehouseCode, "
				+ "											:isProcurementWarehouse, "
				+ "											:isSalesWarehouse, "
				+ "											:isLossesWarehouse, "
				+ "											:entryProcesses, "
				+ "											:loginname) AS queryresult";
		recordParameters = new MapSqlParameterSource();
		recordParameters.addValue("warehouseName", this.getName());
		recordParameters.addValue("warehouseCode", this.getStringId());
		recordParameters.addValue("isProcurementWarehouse", this.isProcurementWarehouse);
		recordParameters.addValue("isSalesWarehouse", this.isSalesWarehouse);
		recordParameters.addValue("isLossesWarehouse", this.isLossesWarehouse);
		recordParameters.addValue("entryProcesses", createArrayOfSQLType("integer", entryProcessIds.toArray()));
		
		Integer qryResult = super.record();
		return qryResult > 0 ? qryResult : -1;
	}
	
	@Override
	public Integer modify() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		modifyQuery = "SELECT soberano.\"fn_Warehouse_modify\"(:warehouseId, "
						+ " 								:warehouseName, "
						+ "									:warehouseCode, "
						+ "									:isProcurementWarehouse, "
						+ "									:isSalesWarehouse, "
						+ "									:isLossesWarehouse, "
						+ "									:entryProcesses, "
						+ "									:loginname) AS queryresult";
		modifyParameters = new MapSqlParameterSource();
		modifyParameters.addValue("warehouseId", this.getId());
		modifyParameters.addValue("warehouseName", this.getName());
		modifyParameters.addValue("warehouseCode", this.getStringId());
		modifyParameters.addValue("isProcurementWarehouse", this.isProcurementWarehouse);
		modifyParameters.addValue("isSalesWarehouse", this.isSalesWarehouse);
		modifyParameters.addValue("isLossesWarehouse", this.isLossesWarehouse);
		modifyParameters.addValue("entryProcesses", createArrayOfSQLType("integer", entryProcessIds.toArray()));
		
		Integer qryResult = super.modify();
		return qryResult == 0 ? qryResult : -1;
	}
	
	@Override
	public Integer disable() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		disableQuery = "SELECT soberano.\"fn_Warehouse_disable\"(:warehouseId, "
				+ "											:loginname) AS queryresult";
		disableParameters = new MapSqlParameterSource();
		disableParameters.addValue("warehouseId", this.getId());
		
		Integer qryResult = super.disable();
		return qryResult == 0 ? qryResult : -1;
	}
	
	@Override
	public List<DomainObject> getAll(Boolean stringId) throws SQLException {	
		return super.getAll(false);
	}
	
	public final class WarehouseDomainObjectMapper implements RowMapper<Object> {

		public DomainObject mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			try {
				DomainObject doo = null;
				int id = rs.getInt("domainObjectId");
				if (!rs.wasNull()) {
					doo = new DomainObject(id, rs.getString("domainObjectName"));
				}
				return doo;
			}
			catch(Exception ex)
			{
				throw ex;
			}			
	    }
	}
		
	public final class WarehouseMapper implements RowMapper<Object> {

		public Warehouse mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			try {
				Warehouse warehouse = null;
				int id = rs.getInt("warehouseId");
				if (!rs.wasNull()) {
					warehouse = new Warehouse(id,
											rs.getInt("entityTypeInstanceId"),
											rs.getString("warehouseName"),
											rs.getString("warehouseCode"),
											rs.getBoolean("isProcurementWarehouse"),
											rs.getBoolean("isSalesWarehouse"),
											rs.getBoolean("isLossesWarehouse"));
				}
				return warehouse;
			}
			catch(Exception ex)
			{
				throw ex;
			}			
	    }
	}
	
	public final class WarehouseExtractor implements ResultSetExtractor<Object> {
		
		@Override
		public Warehouse extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			Warehouse warehouse = null;
			Integer warehouseCurrentlyBeingExtractedId = -1;
	        while (rs.next()) {
	        	if (warehouseCurrentlyBeingExtractedId != rs.getInt("warehouseId")) {
	        		warehouseCurrentlyBeingExtractedId = rs.getInt("warehouseId");
	        		warehouse = new Warehouse(rs.getInt("warehouseId"),
					        				rs.getInt("entityTypeInstanceId"),
											rs.getString("warehouseName"),
											rs.getString("warehouseCode"),
											rs.getBoolean("isProcurementWarehouse"),
											rs.getBoolean("isSalesWarehouse"),
											rs.getBoolean("isLossesWarehouse"));
	        	}
	        	Integer entryProcessId = rs.getInt("processId");
	        	String entryProcessName = rs.getString("processName");
	        	if (entryProcessId != null && entryProcessId > 0 &&
	        			entryProcessName != null && !entryProcessName.isEmpty()) {
	        		warehouse.getEntryProcesses().add(new Process(rs.getInt("processId"), rs.getString("processName")));
	        	}	        	
	        }
	        return warehouse;
		}
	}
	
	@Override
	public void get() throws SQLException {
		
		getQuery = "SELECT * FROM soberano.\"fn_Warehouse_get\"(:warehouseId, :loginname)";
		getParameters = new HashMap<String, Object>();
		getParameters.put("warehouseId", this.getId());
		super.get(new WarehouseExtractor());
	}

	@Override
	protected void copyFrom(Object sourceObject) {
		
		Warehouse sourceWarehouse = (Warehouse) sourceObject;
		setId(sourceWarehouse.getId());
		setEntityTypeInstanceId(sourceWarehouse.getEntityTypeInstanceId());
		setName(sourceWarehouse.getName());
		setStringId(sourceWarehouse.getStringId());
		setIsProcurementWarehouse(sourceWarehouse.getIsProcurementWarehouse());
		setIsSalesWarehouse(sourceWarehouse.getIsSalesWarehouse());
		setIsLossesWarehouse(sourceWarehouse.getIsLossesWarehouse());
		setEntryProcesses(sourceWarehouse.getEntryProcesses());
	}
	
	@Override
	public Integer print() throws SoberanoException {
		
		
		return null;
	}

	public Boolean getIsProcurementWarehouse() {
		return isProcurementWarehouse;
	}

	public void setIsProcurementWarehouse(Boolean isProcurementWarehouse) {
		this.isProcurementWarehouse = isProcurementWarehouse;
	}

	public Boolean getIsSalesWarehouse() {
		return isSalesWarehouse;
	}

	public void setIsSalesWarehouse(Boolean isSalesWarehouse) {
		this.isSalesWarehouse = isSalesWarehouse;
	}

	public ArrayList<Process> getEntryProcesses() {
		return entryProcesses;
	}

	public void setEntryProcesses(ArrayList<Process> entryProcesses) {
		this.entryProcesses = entryProcesses;
		fillEntryProcessIds();
	}

	@Override
	public List<Object> getAll(String orderByColumn, Boolean descOrder, Integer limit, Integer offset, ResultSetExtractor<List<Object>> extractor) throws SQLException {
		return null;
	}
	
	public List<Object> getLossesWarehouses() throws SQLException {
		
		Map<String, Object> qryParams = new HashMap<String, Object>();
		qryParams.put("loginname", SpringUtility.loggedUser().toLowerCase());		
		return query("SELECT * FROM soberano.\"fn_Warehouse_getLossesWarehouses\"(:loginname)", 
					qryParams, 
					new WarehouseDomainObjectMapper());
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

	public Boolean getIsLossesWarehouse() {
		return isLossesWarehouse;
	}

	public void setIsLossesWarehouse(Boolean isLossesWarehouse) {
		this.isLossesWarehouse = isLossesWarehouse;
	}
}
