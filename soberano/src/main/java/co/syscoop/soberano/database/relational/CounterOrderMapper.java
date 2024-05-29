package co.syscoop.soberano.database.relational;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import co.syscoop.soberano.util.rowdata.OrderRowData;

public class CounterOrderMapper implements RowMapper<Object> {

	public OrderRowData mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		try {
			Integer itemId = rs.getInt("operationId");
			OrderRowData orderRow = new OrderRowData(itemId);			
			if (!rs.wasNull()) {
				orderRow.setLabel(rs.getString("orderLabel"));
				orderRow.setCounter(rs.getString("counter"));
			}
			return orderRow;
		}
		catch(Exception ex)
		{
			throw ex;
		}			
    }
}