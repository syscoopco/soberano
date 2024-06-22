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
			}
			return processRunOutputAllocation;
		}
		catch(Exception ex)
		{
			throw ex;
		}			
    }
}