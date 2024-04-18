package co.syscoop.soberano.database.relational;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import co.syscoop.soberano.util.rowdata.ProcessIORowData;

public class ProcessIOMapper implements RowMapper<Object> {

	public ProcessIORowData mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		try {
			ProcessIORowData processIORow = new ProcessIORowData();
			String itemId = rs.getString("itemId");
			if (!rs.wasNull()) {
				processIORow.setItemId(itemId);
				processIORow.setItemName(rs.getString("itemName"));
				processIORow.setUnitAcron(rs.getString("unitAcron"));
				processIORow.setUnitId(rs.getInt("unitId"));
				processIORow.setQuantity(rs.getBigDecimal("quantity"));
				processIORow.setWeight(rs.getInt("weight"));
			}
			return processIORow;
		}
		catch(Exception ex)
		{
			throw ex;
		}			
    }
}