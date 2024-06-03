package co.syscoop.soberano.database.relational;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import co.syscoop.soberano.domain.untracked.Parameter;

public final class ParameterMapper implements RowMapper<Object> {

	public Parameter mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		try {
			Parameter param = null;
			String paramName = rs.getString("paramName");
			if (!rs.wasNull()) {
				param = new Parameter(paramName, rs.getString("paramValue"));
			}
			return param;
		}
		catch(Exception ex)
		{
			throw ex;
		}			
    }
}