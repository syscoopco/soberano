package co.syscoop.soberano.domain.tracked;

import java.sql.SQLException;
import java.util.List;
import co.syscoop.soberano.database.relational.CatalogEntryMapper;

public class CatalogEntry extends BusinessActivityTrackedObject {
	
	public List<Object> getAll() throws SQLException {
		
		String qryStr = "SELECT * FROM soberano.\"fn_Catalog_getAll\"()";	
		return query(qryStr, getAllQueryNamedParameters, new CatalogEntryMapper());
	}
}
