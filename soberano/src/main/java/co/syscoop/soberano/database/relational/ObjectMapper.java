package co.syscoop.soberano.database.relational;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import co.syscoop.soberano.domain.untracked.DomainObject;

public final class ObjectMapper implements RowMapper<Object> {

	public DomainObject mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		try {
			DomainObject domainObject = new DomainObject();
			int id = rs.getInt("domainObjectId");
			if (!rs.wasNull()) {
				domainObject.setId(id);
				domainObject.setName(rs.getString("domainObjectName"));
			}
			return domainObject;
		}
		catch(Exception ex)
		{
			throw ex;
		}			
    }
}