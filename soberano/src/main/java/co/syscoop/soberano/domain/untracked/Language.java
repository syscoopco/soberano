package co.syscoop.soberano.domain.untracked;

import java.sql.SQLException;
import java.util.List;

public class Language extends DomainObject {
	
	public Language() {
		getAllQuery = "SELECT * FROM soberano.\"fn_Language_getAll\"()";
	}
	
	public List<DomainObject> getAll() throws SQLException {
		return super.getAll(true);
	}
}
