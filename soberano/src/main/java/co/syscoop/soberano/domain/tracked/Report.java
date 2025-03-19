package co.syscoop.soberano.domain.tracked;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.util.Locales;

import co.syscoop.soberano.database.relational.PrintableDataMapper;
import co.syscoop.soberano.domain.untracked.PrintableData;
import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.util.SpringUtility;

public class Report extends TrackedObject implements IReport {
	
	private ArrayList<String> reportQueryParamNames = new ArrayList<String>();
	private ArrayList<Object> reportQueryParamValues = new ArrayList<Object>();
	private String reportFunctionName = "";
	
	public Report(ArrayList<String> reportQueryParamNames,
				ArrayList<Object> reportQueryParamValues,
				String reportFunctionName) {
		this.reportQueryParamNames = reportQueryParamNames;
		this.reportQueryParamValues = reportQueryParamValues;
		this.reportFunctionName = reportFunctionName;
	}
	
	public ArrayList<String> getReportQueryParamNames() {
		return reportQueryParamNames;
	}
	
	public void setReportQueryParamNames(ArrayList<String> reportQueryParamNames) {
		this.reportQueryParamNames = reportQueryParamNames;
	}

	public ArrayList<Object> getReportQueryParamValues() {
		return reportQueryParamValues;
	}

	public void setReportQueryParamValues(ArrayList<Object> reportQueryParamValues) {
		this.reportQueryParamValues = reportQueryParamValues;
	}

	public String getReportFunctionName() {
		return reportFunctionName;
	}

	public void setReportFunctionName(String reportFunctionName) {
		this.reportFunctionName = reportFunctionName;
	}

	@Override
	public PrintableData getReportWithPrinterProfile() throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		Map<String, Object> qryParameters = new HashMap<String,	Object>();
		qryParameters.put("lang", Locales.getCurrent().getLanguage());
		qryParameters.put("loginname", SpringUtility.loggedUser().toLowerCase());
		
		String query = "SELECT * FROM soberano.\"" + reportFunctionName + "\"(";
		
		
		Object paramNames[] = reportQueryParamNames.toArray();
		Object paramValues[] = reportQueryParamValues.toArray();
		for (int i = 0; i < paramNames.length; i++) {
			qryParameters.put((String) paramNames[i], paramValues[i]);
			query += ":" + (String) paramNames[i] + ",";
		}
		query += ":lang, :loginname) AS report";
		
		return (PrintableData) super.query(query, qryParameters, new PrintableDataMapper()).get(0);
	}

	@Override
	public void get() throws SQLException {		
	}

	@Override
	public Integer print() throws SoberanoException {
		return null;
	}

	@Override
	public PrintableData getReportFull() throws SQLException {
		return null;
	}

	@Override
	public PrintableData getReportMinimal() throws SQLException {
		return null;
	}

	@Override
	protected void copyFrom(Object object) {
	}
}
