package co.syscoop.soberano.domain.untracked.helper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import co.syscoop.soberano.domain.untracked.DomainObject;

public final class DomainObjectMapperWithStringId implements RowMapper<DomainObject> {

	public DomainObject mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		try {
			DomainObject domainObject = new DomainObject();
			String id = rs.getString("domainObjectStringId");
			if (!rs.wasNull()) {
				domainObject.setStringId(id);
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