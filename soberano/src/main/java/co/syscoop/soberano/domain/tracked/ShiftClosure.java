package co.syscoop.soberano.domain.tracked;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.zkoss.util.Locales;

import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.exception.ShiftHasBeenClosedException;

public class ShiftClosure extends BusinessActivityTrackedObject {
	
	public ShiftClosure() {
		getAllQuery = "SELECT * FROM soberano.\"" 
							+ "fn_ShiftClosure_getAll\"" 
							+ "(:lang, :loginname)";
		getCountQuery = "SELECT soberano.\"fn_ShiftClosure_getCount\"(:lang, :loginname) AS count";
		getAllQueryNamedParameters = new HashMap<String, Object>();
		getAllQueryNamedParameters.put("lang", Locales.getCurrent().getLanguage());		
	}
	
	public ShiftClosure(Integer id) {
		this.setId(id);
	}
	
	public ShiftClosure(Date shift) {
		super.setOccurrenceTime(shift);
	}

	@Override
	public void get() throws SQLException {
	}

	@Override
	public Integer print() throws SQLException {
		return null;
	}

	@Override
	protected void copyFrom(Object object) {
	}
	
	@Override
	public List<Object> getAll(String orderByColumn, Boolean descOrder, Integer limit, Integer offset, ResultSetExtractor<List<Object>> extractor) throws SQLException {
		return super.getAll(orderByColumn, descOrder, limit, offset, extractor);
	}
	
	@Override
	public Integer getCount() throws SQLException {
		return super.getCount();
	}
	
	@Override
	public Integer record() throws Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		recordQuery = "SELECT soberano.\"fn_ShiftClosure_create\"(:loginname) AS queryresult";
		recordParameters = new MapSqlParameterSource();		
		return super.record();
	}
	
	@Override
	public Integer disable() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		disableQuery = "SELECT soberano.\"fn_ShiftClosure_disable\"(:shiftId, "
				+ "											:loginname) AS queryresult";
		disableParameters = new MapSqlParameterSource();
		disableParameters.addValue("shiftId", this.getId());
		
		Integer qryResult = super.disable();
		if (qryResult == -3) {
			throw new ShiftHasBeenClosedException();
		}
		return qryResult;
	}
	
	@Override
	public List<DomainObject> getAll(Boolean stringId) throws SQLException {	
		return super.getAll(false);
	}

	@Override
	public String getReport() throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		getReportQuery = "SELECT * FROM soberano.\"fn_ShiftClosure_getReport\"(:lang, :closureId, :loginname) AS report";
		getReportParameters = new HashMap<String,	Object>();
		getReportParameters.put("lang", Locales.getCurrent().getLanguage());	
		getReportParameters.put("closureId", this.getId());
		return super.getReport();
	}
}
