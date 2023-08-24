package co.syscoop.soberano.database.relational;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class QueryResultMapper implements RowMapper<Integer> {

	public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		try {
			//always returns 0, or a new object id, whenever everything is right.
			//otherwise, returns a negative code error.
			Integer queryResult = rs.getInt("queryresult");
			if (!rs.wasNull()) {
				return queryResult;
			}
			else {
				return 0;
			}
		}
		catch(Exception ex)
		{
			throw ex;
		}			
    }
}