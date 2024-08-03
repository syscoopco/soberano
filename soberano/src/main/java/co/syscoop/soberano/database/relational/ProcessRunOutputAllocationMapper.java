package co.syscoop.soberano.database.relational;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import co.syscoop.soberano.domain.tracked.ProcessRunOutputAllocation;

public class ProcessRunOutputAllocationMapper implements RowMapper<Object> {

	public ProcessRunOutputAllocation mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		try {
			ProcessRunOutputAllocation processRunOutputAllocation = new ProcessRunOutputAllocation();
			Integer allocationId = rs.getInt("allocationId");
			if (!rs.wasNull()) {
				processRunOutputAllocation.setId(allocationId);
				processRunOutputAllocation.setProductionLineId(rs.getInt("productionLineId"));
				processRunOutputAllocation.setDescription(rs.getString("description"));	
				processRunOutputAllocation.setItemName(rs.getString("itemName"));	
			}
			return processRunOutputAllocation;
		}
		catch(Exception ex)
		{
			throw ex;
		}			
    }
}