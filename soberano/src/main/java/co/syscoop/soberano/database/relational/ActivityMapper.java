package co.syscoop.soberano.database.relational;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import co.syscoop.soberano.util.OrderRowData;

public class ActivityMapper implements RowMapper<Object> {

	public OrderRowData mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		try {
			Integer itemId = rs.getInt("operationId");
			OrderRowData orderRow = new OrderRowData(itemId);			
			if (!rs.wasNull()) {
				orderRow.setObjectType(rs.getInt("objectType"));
				orderRow.setLabel(rs.getString("orderLabel"));
				orderRow.setCustomer(rs.getString("customer"));
				orderRow.setCounter(rs.getString("counter"));
				orderRow.setStage(rs.getString("stage"));
				orderRow.setDescription(rs.getString("description"));
				orderRow.setHistory(rs.getString("history"));
				orderRow.setRecordingDate(rs.getTimestamp("recordingDate"));
				orderRow.setEntityTypeInstanceId(rs.getInt("entityTypeInstanceId"));
			}
			return orderRow;
		}
		catch(Exception ex)
		{
			throw ex;
		}			
    }
}