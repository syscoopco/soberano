package co.syscoop.soberano.domain.untracked;

import java.sql.SQLException;
import java.util.List;

public class Country extends DomainObject {
	
	public Country() {
		getAllQuery = "SELECT * FROM soberano.\"fn_Country_getAll\"()";
	}
	
	public List<DomainObject> getAll() throws SQLException {
		return super.getAll(true);
	}
}
