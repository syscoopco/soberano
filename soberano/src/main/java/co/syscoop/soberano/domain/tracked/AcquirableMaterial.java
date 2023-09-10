package co.syscoop.soberano.domain.tracked;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.ProcessRunningException;

public class AcquirableMaterial extends InventoryItem { 

	public AcquirableMaterial(Integer id) {
		super(id);
	}
	
	public AcquirableMaterial(Integer id, String name) {
		super(id, name);
	}
	
	public AcquirableMaterial(Integer id, 
					Integer entityTypeInstanceId, 
					String code,
					String name) {
		super(id, entityTypeInstanceId, name);
		this.setStringId(code);
		this.setQualifiedName(name + " : " + code);		
	}
	
	public AcquirableMaterial(Integer id, 
						Integer entityTypeInstanceId, 
						String code,
						String name,
						BigDecimal minimumInventoryLevel,
						Integer unit) {
		super(id, entityTypeInstanceId, name, minimumInventoryLevel, unit);
		this.setStringId(code);
		this.setQualifiedName(name + " : " + code);		
	}
	
	public AcquirableMaterial() {
		getAllQuery = "SELECT * FROM soberano.\"fn_AcquirableMaterial_getAll\"(:loginname)";
		getAllQueryNamedParameters = new HashMap<String, Object>();
	}
	
	@Override
	public Integer record() throws Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		recordQuery = "SELECT soberano.\"fn_AcquirableMaterial_create\"(:itemCode, "
				+ "											:itemName, "
				+ "											:inventoryLevel, "
				+ "											:itemUnit, "
				+ "											:loginname) AS queryresult";
		recordParameters = new MapSqlParameterSource();
		recordParameters.addValue("itemCode", this.getStringId());
		recordParameters.addValue("itemName", this.getName());
		recordParameters.addValue("inventoryLevel", this.getMinimumInventoryLevel());
		recordParameters.addValue("itemUnit", this.getUnit());
		
		Integer qryResult = super.record();
		return qryResult > 0 ? qryResult : -1;
	}
	
	@Override
	public Integer modify() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		modifyQuery = "SELECT soberano.\"fn_AcquirableMaterial_modify\"(:itemId, "
						+ " 								:itemCode, "
						+ " 								:itemName, "
						+ "									:inventoryLevel, "
						+ "									:itemUnit, "
						+ "									:loginname) AS queryresult";
		modifyParameters = new MapSqlParameterSource();
		modifyParameters.addValue("itemId", this.getId());
		modifyParameters.addValue("itemCode", this.getStringId());
		modifyParameters.addValue("itemName", this.getName());
		modifyParameters.addValue("inventoryLevel", this.getMinimumInventoryLevel());
		modifyParameters.addValue("itemUnit", this.getUnit());
		
		Integer qryResult = super.modify();
		if (qryResult == -2) {
			throw new ProcessRunningException();
		}
		return qryResult >= 0 ? qryResult : -1;
	}
	
	@Override
	public Integer disable() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		disableQuery = "SELECT soberano.\"fn_AcquirableMaterial_disable\"(:itemId, "
				+ "											:loginname) AS queryresult";
		disableParameters = new MapSqlParameterSource();
		disableParameters.addValue("itemId", this.getId());
		
		Integer qryResult = super.disable();
		return qryResult >= 0 ? qryResult : -1;
	}
	
	@Override
	public List<DomainObject> getAll(Boolean stringId) throws SQLException {
		if (stringId) {
			getAllQuery = "SELECT * FROM soberano.\"fn_AcquirableMaterial_getAllWithStringId\"(:loginname)";
		}
		return super.getAll(stringId);
	}
		
	public final class AcquirableMaterialMapper implements RowMapper<Object> {

		public AcquirableMaterial mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			try {
				AcquirableMaterial acquirableMaterial = null;
				int id = rs.getInt("itemId");
				if (!rs.wasNull()) {
					acquirableMaterial = new AcquirableMaterial(id,
										rs.getInt("entityTypeInstanceId"),
										rs.getString("itemCode"),
										rs.getString("itemName"),
										rs.getBigDecimal("inventoryLevel"),
										rs.getInt("itemUnit"));
				}
				return acquirableMaterial;
			}
			catch(Exception ex)
			{
				throw ex;
			}			
	    }
	}
	
	@Override
	public void get() throws SQLException {
		
		getQuery = "SELECT * FROM soberano.\"fn_AcquirableMaterial_get\"(:itemId, :loginname)";
		getParameters = new HashMap<String, Object>();
		getParameters.put("itemId", this.getId());
		super.get(new AcquirableMaterialMapper());
	}

	@Override
	protected void copyFrom(Object sourceObject) {
		
		AcquirableMaterial sourceAcquirableMaterial = (AcquirableMaterial) sourceObject;
		setId(sourceAcquirableMaterial.getId());
		setEntityTypeInstanceId(sourceAcquirableMaterial.getEntityTypeInstanceId());
		setName(sourceAcquirableMaterial.getName());
		setStringId(sourceAcquirableMaterial.getStringId());
		setMinimumInventoryLevel(sourceAcquirableMaterial.getMinimumInventoryLevel());
		setUnit(sourceAcquirableMaterial.getUnit());
	}
	
	@Override
	public Integer print() throws SQLException {
		
		// TODO print a report on the object
		return null;
	}
}
