package co.syscoop.soberano.database.relational;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class QueryStringResultMapper implements RowMapper<Object> {

	public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		try {
			String queryResult = rs.getString("queryresult");
			if (!rs.wasNull()) {
				return queryResult;
			}
			else {
				return new String("");
			}
		}
		catch(Exception ex)
		{
			throw ex;
		}			
    }
}