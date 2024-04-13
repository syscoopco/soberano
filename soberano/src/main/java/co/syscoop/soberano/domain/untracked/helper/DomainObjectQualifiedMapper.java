package co.syscoop.soberano.domain.untracked.helper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.zkoss.util.resource.Labels;

import co.syscoop.soberano.domain.untracked.DomainObject;

public final class DomainObjectQualifiedMapper implements RowMapper<Object> {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		try {
			DomainObject domainObject = new DomainObject();
			int id = rs.getInt("domainObjectId");
			if (!rs.wasNull()) {
				domainObject.setId(id);
				domainObject.setName(rs.getString("domainObjectName"));
				domainObject.setEntityTypeMeaningId(rs.getString("entityTypeMeaningId"));
				domainObject.setEntityTypeName(Labels.getLabel(rs.getString("entityTypeName")));
				domainObject.setEntityTypeInstanceId(rs.getInt("entityTypeInstanceId"));
			}
			return domainObject;
		}
		catch(Exception ex)
		{
			throw ex;
		}			
    }
}