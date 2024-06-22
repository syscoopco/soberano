package co.syscoop.soberano.domain.tracked;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.zkoss.util.Locales;

import co.syscoop.soberano.database.relational.PrintableDataMapper;
import co.syscoop.soberano.domain.untracked.DomainObject;
import co.syscoop.soberano.domain.untracked.PrintableData;
import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.util.SpringUtility;

public class Balancing extends BusinessActivityTrackedObject {
	
	private Integer cashRegister = 0;
	private ArrayList<Integer> currencyIds = new ArrayList<Integer>();
	private ArrayList<BigDecimal> amounts = new ArrayList<BigDecimal>();
	
	public Balancing() {
		getAllQuery = "SELECT * FROM soberano.\"" 
							+ "fn_Balancing_getAll\"" 
							+ "(:lang, :loginname)";
		getCountQuery = "SELECT soberano.\"fn_Balancingl_getCount\"(:lang, :loginname) AS count";
		getAllQueryNamedParameters = new HashMap<String, Object>();
		getAllQueryNamedParameters.put("lang", Locales.getCurrent().getLanguage());		
	}
	
	public Balancing(Integer cashRegister,
					ArrayList<Integer> currencyIds,
					ArrayList<BigDecimal> amounts,
					String notes) {
		this.setCashRegister(cashRegister);
		this.setCurrencyIds(currencyIds);
		this.setAmounts(amounts);
		this.setNotes(notes);
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
		recordQuery = "SELECT soberano.\"fn_Balancing_create\"(:cashRegister, "
				+ "											:excludeCash, "
				+ "											:currencyIds, "
				+ "											:amounts, "
				+ "											:notes, "
				+ "											:loginname) AS queryresult";
		recordParameters = new MapSqlParameterSource();
		recordParameters.addValue("cashRegister", this.getCashRegister());
		recordParameters.addValue("excludeCash", false);
		recordParameters.addValue("currencyIds", createArrayOfSQLType("integer", this.currencyIds.toArray()));
		recordParameters.addValue("amounts", createArrayOfSQLType("numeric", this.amounts.toArray()));
		recordParameters.addValue("notes", this.getNotes());
		
		return super.record();
	}
	
	public PrintableData getReportFull() throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT * FROM soberano.\"fn_Balancing_getReport\"(:opid, "
							+ "								:lang, "
							+ "								:loginname) AS queryresult";		
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("opid", this.getId());
		parametersMap.put("lang", Locales.getCurrent().getLanguage());
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return (PrintableData) super.query(qryStr, parametersMap, new PrintableDataMapper()).get(0);
	}
	
	@Override
	public Integer disable() throws SQLException, Exception {	
		return 0;
	}
	
	@Override
	public List<DomainObject> getAll(Boolean stringId) throws SQLException {	
		return super.getAll(false);
	}

	public ArrayList<Integer> getCurrencyIds() {
		return currencyIds;
	}

	public void setCurrencyIds(ArrayList<Integer> currencyIds) {
		this.currencyIds = currencyIds;
	}

	public ArrayList<BigDecimal> getAmounts() {
		return amounts;
	}

	public void setAmounts(ArrayList<BigDecimal> amounts) {
		this.amounts = amounts;
	}

	public Integer getCashRegister() {
		return cashRegister;
	}

	public void setCashRegister(Integer cashRegister) {
		this.cashRegister = cashRegister;
	}
}
