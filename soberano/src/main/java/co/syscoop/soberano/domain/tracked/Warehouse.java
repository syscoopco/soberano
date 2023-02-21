package co.syscoop.soberano.domain.tracked;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import co.syscoop.soberano.domain.untracked.DomainObject;

public class Warehouse extends TrackedObject {

	private Boolean isProcurementWarehouse = false;
	private Boolean isSalesWarehouse = false;
	
	public Warehouse(Integer id) {
		super(id);
	}
	
	public Warehouse(Integer id, 
					Integer entityTypeInstanceId, 
					String name, 
					String code,
					Boolean isProcurementWarehouse, 
					Boolean isSalesWarehouse) {
		super(id, entityTypeInstanceId, name);
		this.setStringId(code);
		this.setQualifiedName(name + ":" + code);		
		this.setIsProcurementWarehouse(isProcurementWarehouse);
		this.setIsSalesWarehouse(isSalesWarehouse);
	}
	
	public Warehouse() {
		getAllQuery = "SELECT * FROM soberano.\"" + "fn_Warehouse_getAll\"" + "(:loginname)";
		getAllQueryNamedParameters = new HashMap<String, Object>();
	}
	
	@Override
	public Integer record() throws Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		recordQuery = "SELECT soberano.\"fn_Warehouse_create\"(:warehouseName, "
				+ "											:warehouseCode, "
				+ "											:isProcurementWarehouse, "
				+ "											:isSalesWarehouse, "
				+ "											:loginname) AS queryresult";
		recordParameters = new MapSqlParameterSource();
		recordParameters.addValue("warehouseName", this.getName());
		recordParameters.addValue("warehouseCode", this.getStringId());
		recordParameters.addValue("isProcurementWarehouse", this.isProcurementWarehouse);
		recordParameters.addValue("isSalesWarehouse", this.isSalesWarehouse);
		
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
						+ "									:loginname) AS queryresult";
		modifyParameters = new MapSqlParameterSource();
		modifyParameters.addValue("warehouseId", this.getId());
		modifyParameters.addValue("warehouseName", this.getName());
		modifyParameters.addValue("warehouseCode", this.getStringId());
		modifyParameters.addValue("isProcurementWarehouse", this.isProcurementWarehouse);
		modifyParameters.addValue("isSalesWarehouse", this.isSalesWarehouse);
		
		Integer qryResult = super.modify();
		return qryResult >= 0 ? qryResult : -1;
	}
	
	@Override
	public Integer disable() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		disableQuery = "SELECT soberano.\"fn_Warehouse_disable\"(:warehouseId, "
				+ "											:loginname) AS queryresult";
		disableParameters = new MapSqlParameterSource();
		disableParameters.addValue("warehouseId", this.getId());
		
		Integer qryResult = super.disable();
		return qryResult >= 0 ? qryResult : -1;
	}
	
	@Override
	public List<DomainObject> getAll(Boolean stringId) throws SQLException {	
		return super.getAll(false);
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
											rs.getBoolean("isSalesWarehouse"));
				}
				return warehouse;
			}
			catch(Exception ex)
			{
				throw ex;
			}			
	    }
	}
	
	@Override
	public void get() throws SQLException {
		
		getQuery = "SELECT * FROM soberano.\"fn_Warehouse_get\"(:warehouseId, :loginname)";
		getParameters = new HashMap<String, Object>();
		getParameters.put("warehouseId", this.getId());
		super.get(new WarehouseMapper());
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
	}
	
	@Override
	public Integer print() throws SQLException {
		
		// TODO print a report on the object
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
}
