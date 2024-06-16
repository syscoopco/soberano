package co.syscoop.soberano.database.relational;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class QueryResultWithReportMapper implements RowMapper<Object> {

	public QueryResultWithReport mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		try {
			Integer result = rs.getInt("res");
			if (!rs.wasNull()) {
				return new QueryResultWithReport(result, rs.getString("rep"), rs.getInt("printerProfile"));
			}
			else {
				return new QueryResultWithReport(null, null, null);
			}
		}
		catch(Exception ex)
		{
			throw ex;
		}			
    }
}