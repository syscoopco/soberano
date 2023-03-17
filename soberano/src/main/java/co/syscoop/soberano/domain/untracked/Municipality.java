package co.syscoop.soberano.domain.untracked;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class Municipality extends DomainObject {
	
	private Integer provinceId = 0;
	
	public Municipality(Integer provinceId) {
		this.provinceId = provinceId;
	}
	
	public List<DomainObject> getAll() throws SQLException {		
		getAllQuery = "SELECT * FROM soberano.\"fn_Municipality_getAll\"(:provinceId)";
		getAllQueryNamedParameters = new HashMap<String, Object>();
		getAllQueryNamedParameters.put("provinceId", provinceId);
		return super.getAll(false);
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
}
