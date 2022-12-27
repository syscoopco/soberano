package co.syscoop.soberano.domain.untracked;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class Province extends DomainObject {
	
	private String countryCode = "CU";
	
	public Province(String countryCode) {
		this.countryCode = countryCode;
	}
	
	public List<DomainObject> getAll() throws SQLException {		
		getAllQuery = "SELECT * FROM soberano.\"" + "fn_Province_getAll\"" + "(:countryCode)";
		getAllQueryNamedParameters = new HashMap<String, Object>();
		getAllQueryNamedParameters.put("countryCode", countryCode);
		return super.getAll(false);
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
}
