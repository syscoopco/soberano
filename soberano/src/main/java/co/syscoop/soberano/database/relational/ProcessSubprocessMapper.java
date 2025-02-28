package co.syscoop.soberano.database.relational;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import co.syscoop.soberano.util.rowdata.ProcessSubprocessRowData;

public class ProcessSubprocessMapper implements RowMapper<Object> {

	public ProcessSubprocessRowData mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		try {
			ProcessSubprocessRowData processSubprocessRowData = new ProcessSubprocessRowData();
			Integer itemId = rs.getInt("itemId");
			if (!rs.wasNull()) {
				processSubprocessRowData.setItemId(itemId);
				processSubprocessRowData.setItemName(rs.getString("itemName"));
			}
			return processSubprocessRowData;
		}
		catch(Exception ex)
		{
			throw ex;
		}			
    }
}