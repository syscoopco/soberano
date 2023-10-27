package co.syscoop.soberano.database.relational;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class QueryBigDecimalResultMapper implements RowMapper<Object> {

	public BigDecimal mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		try {
			BigDecimal queryResult = rs.getBigDecimal("queryresult");
			if (!rs.wasNull()) {
				return queryResult;
			}
			else {
				return new BigDecimal(0);
			}
		}
		catch(Exception ex)
		{
			throw ex;
		}			
    }
}