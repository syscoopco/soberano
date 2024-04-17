package co.syscoop.soberano.domain.untracked;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class PostalCode extends DomainObject {
	
	private String countryCode = "";
	
	public PostalCode(String countryCode) {
		this.countryCode = countryCode;
		getAllQuery = "SELECT * FROM soberano.\"fn_PostalCode_getAll\"(:countryCode)";
		getAllQueryNamedParameters = new HashMap<String, Object>();
		getAllQueryNamedParameters.put("countryCode", countryCode);
	}
	
	public List<DomainObject> getAll() throws SQLException {		
		return super.getAll(false);
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
}
