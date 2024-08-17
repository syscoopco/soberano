package co.syscoop.soberano.domain.tracked;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.zkoss.util.Locales;

import co.syscoop.soberano.database.relational.PrintableDataMapper;
import co.syscoop.soberano.database.relational.QueryBigDecimalResultMapper;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.domain.untracked.PrintableData;
import co.syscoop.soberano.exception.ShiftHasBeenClosedException;
import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.util.SpringUtility;

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
	public Integer print() throws SoberanoException {
		return null;
	}

	@Override
	protected void copyFrom(Object object) {
	}
	
	@Override
	public Integer record() throws Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		recordQuery = "SELECT soberano.\"fn_ShiftClosure_create\"(:lang, :loginname) AS queryresult";
		recordParameters = new MapSqlParameterSource();	
		recordParameters.addValue("lang", Locales.getCurrent().getLanguage());	
		return super.record();
	}
	
	@Override
	public Integer disable() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		disableQuery = "SELECT soberano.\"fn_ShiftClosure_cancel\"(:shiftId, "
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
	
	public String getHouseBillReport() throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		getReportQuery = "SELECT * FROM soberano.\"fn_ShiftClosure_getHouseBillReport\"(:lang, :closureId, :loginname) AS report";
		getReportParameters = new HashMap<String,	Object>();
		getReportParameters.put("lang", Locales.getCurrent().getLanguage());	
		getReportParameters.put("closureId", this.getId());
		return super.getReport();
	}
	
	public String getCashRegisterReport() throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		getReportQuery = "SELECT * FROM soberano.\"fn_ShiftClosure_getCashRegisterReport\"(:lang, :closureId, :loginname) AS report";
		getReportParameters = new HashMap<String,	Object>();
		getReportParameters.put("lang", Locales.getCurrent().getLanguage());	
		getReportParameters.put("closureId", this.getId());
		return super.getReport();
	}
	
	public String getReceivablesReport() throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		getReportQuery = "SELECT * FROM soberano.\"fn_ShiftClosure_getReceivablesReport\"(:lang, :closureId, :loginname) AS report";
		getReportParameters = new HashMap<String,	Object>();
		getReportParameters.put("lang", Locales.getCurrent().getLanguage());	
		getReportParameters.put("closureId", this.getId());
		return super.getReport();
	}
	
	public String getGeneralFullReport() throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		getReportQuery = "SELECT * FROM soberano.\"fn_ShiftClosure_getFullReport\"(:lang, :closureId, :loginname) AS report";
		getReportParameters = new HashMap<String,	Object>();
		getReportParameters.put("lang", Locales.getCurrent().getLanguage());	
		getReportParameters.put("closureId", this.getId());
		return super.getReport();
	}
	
	public String getCostCenterReport(String costCenterName) throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		getReportQuery = "SELECT * FROM soberano.\"fn_ShiftClosure_getCostCenterReport\"(:lang, :closureId, :costCenterName, :loginname) AS report";
		getReportParameters = new HashMap<String,	Object>();
		getReportParameters.put("lang", Locales.getCurrent().getLanguage());	
		getReportParameters.put("closureId", this.getId());
		getReportParameters.put("costCenterName", costCenterName);
		return super.getReport();
	}
	
	public PrintableData getReportWithPrinterProfile() throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String query = "SELECT * FROM soberano.\"fn_ShiftClosure_getReportWithPrinterProfile\"(:lang, :closureId, :loginname) AS report";
		Map<String, Object> qryParameters = new HashMap<String,	Object>();
		qryParameters.put("lang", Locales.getCurrent().getLanguage());	
		qryParameters.put("closureId", this.getId());
		qryParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (PrintableData) super.query(query, qryParameters, new PrintableDataMapper()).get(0);
	}
	
	public PrintableData getHouseBillReportWithPrinterProfile() throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String query = "SELECT * FROM soberano.\"fn_ShiftClosure_getHouseBillReportWithPrinterProfile\"(:lang, :closureId, :loginname) AS report";
		Map<String, Object> qryParameters = new HashMap<String,	Object>();
		qryParameters.put("lang", Locales.getCurrent().getLanguage());	
		qryParameters.put("closureId", this.getId());
		qryParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (PrintableData) super.query(query, qryParameters, new PrintableDataMapper()).get(0);
	}

	public PrintableData getCashRegisterReportWithPrinterProfile() throws SQLException {
	
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String query = "SELECT * FROM soberano.\"fn_ShiftClosure_getCashRegisterReportWithPrinterProfile\"(:lang, :closureId, :loginname) AS report";
		Map<String, Object> qryParameters = new HashMap<String,	Object>();
		qryParameters.put("lang", Locales.getCurrent().getLanguage());	
		qryParameters.put("closureId", this.getId());
		qryParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (PrintableData) super.query(query, qryParameters, new PrintableDataMapper()).get(0);
	}
	
	public PrintableData getReceivablesReportWithPrinterProfile() throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String query = "SELECT * FROM soberano.\"fn_ShiftClosure_getReceivablesReportWithPrinterProfile\"(:lang, :closureId, :loginname) AS report";
		Map<String, Object> qryParameters = new HashMap<String,	Object>();
		qryParameters.put("lang", Locales.getCurrent().getLanguage());	
		qryParameters.put("closureId", this.getId());
		qryParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (PrintableData) super.query(query, qryParameters, new PrintableDataMapper()).get(0);
	}
	
	public PrintableData getGeneralFullReportWithPrinterProfile() throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String query = "SELECT * FROM soberano.\"fn_ShiftClosure_getFullReportWithPrinterProfile\"(:lang, :closureId, :loginname) AS report";
		Map<String, Object> qryParameters = new HashMap<String,	Object>();
		qryParameters.put("lang", Locales.getCurrent().getLanguage());	
		qryParameters.put("closureId", this.getId());
		qryParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (PrintableData) super.query(query, qryParameters, new PrintableDataMapper()).get(0);
	}
	
	public PrintableData getCostCenterReportWithPrinterProfile(String costCenterName) throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String query = "SELECT * FROM soberano.\"fn_ShiftClosure_getCostCenterReportWithPrinterProfile\"(:lang, :closureId, :costCenterName, :loginname) AS report";
		Map<String, Object> qryParameters = new HashMap<String,	Object>();
		qryParameters.put("lang", Locales.getCurrent().getLanguage());	
		qryParameters.put("closureId", this.getId());
		qryParameters.put("costCenterName", costCenterName);
		qryParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (PrintableData) super.query(query, qryParameters, new PrintableDataMapper()).get(0);
	}
	
	public BigDecimal getShiftSales(Integer closureid) throws SQLException, Exception {
		
		String qryStr = "SELECT * FROM soberano.\"fn_ShiftClosure_getSales\"(:closureid, :loginname) AS queryresult";
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("closureid", closureid);
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (BigDecimal) super.query(qryStr, parametersMap, new QueryBigDecimalResultMapper()).get(0);
	}
}
