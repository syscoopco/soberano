package co.syscoop.soberano.domain.tracked;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.zkoss.util.Locales;

import co.syscoop.soberano.domain.untracked.DomainObject;

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
					ArrayList<BigDecimal> amounts) {
		this.setCashRegister(cashRegister);
		this.setCurrencyIds(currencyIds);
		this.setAmounts(amounts);
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
		recordQuery = "SELECT soberano.\"fn_Balancing_create\"(:cashRegister, "
				+ "											:currencyIds, "
				+ "											:amounts, "
				+ "											:loginname) AS queryresult";
		recordParameters = new MapSqlParameterSource();
		recordParameters.addValue("cashRegister", this.getCashRegister());
		recordParameters.addValue("currencyIds", createArrayOfSQLType("integer", this.currencyIds.toArray()));
		recordParameters.addValue("amounts", createArrayOfSQLType("numeric", this.amounts.toArray()));
		
		return super.record();
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
