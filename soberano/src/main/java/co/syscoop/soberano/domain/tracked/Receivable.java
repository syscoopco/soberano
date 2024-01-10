package co.syscoop.soberano.domain.tracked;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.util.Locales;

import co.syscoop.soberano.database.relational.QueryObjectResultMapper;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.util.SpringUtility;

public class Receivable extends BusinessActivityTrackedObject {
	
	public Receivable() {
		getAllQuery = "SELECT * FROM soberano.\"" 
							+ "fn_Receivable_getAll\"(:lang, "
													+ ":delayedDays, "
													+ ":custome, "
													+ ":debto, "
													+ ":dishonored, "
													+ ":loginname)";
		getCountQuery = "SELECT soberano.\"fn_Receivable_getCount\"(:lang, "
																+ ":delayedDays, "
																+ ":custome, "
																+ ":debto, "
																+ ":dishonored, "
																+ ":loginname) AS count";
		getAllQueryNamedParameters = new HashMap<String, Object>();
		getAllQueryNamedParameters.put("lang", Locales.getCurrent().getLanguage());
		
		//filterable params 
		getAllQueryNamedParameters.put("delayedDays", null);
		getAllQueryNamedParameters.put("custome", null);
		getAllQueryNamedParameters.put("debto", null);
		getAllQueryNamedParameters.put("dishonored", false);
	}
	
	public Receivable(Integer id) {
		this.setId(id);
	}
	
	@Override
	public Integer print() throws SQLException {
		return null;
	}

	public Integer dishonor() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT soberano.\"fn_Receivable_dishonor\"(:receivableId, "
							+ "										:loginname) AS queryresult";		
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("receivableId", this.getId());
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (Integer) super.query(qryStr, parametersMap, new QueryObjectResultMapper()).get(0);
	}
	
	@Override
	public List<DomainObject> getAll(Boolean stringId) throws SQLException {	
		return super.getAll(false);
	}	
}
