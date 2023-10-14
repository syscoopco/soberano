package co.syscoop.soberano.domain.tracked;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.zkoss.util.Locales;

import co.syscoop.soberano.domain.untracked.DomainObject;

public class Withdrawal extends BusinessActivityTrackedObject {
	
	private Integer cashRegister = 0;
	private Integer Order = 0;
	private Integer Receivable = 0;
	private ArrayList<Integer> currencyIds = new ArrayList<Integer>();
	private ArrayList<BigDecimal> amounts = new ArrayList<BigDecimal>();
	
	public Withdrawal() {
		getAllQuery = "SELECT * FROM soberano.\"" 
							+ "fn_Withdrawal_getAll\"" 
							+ "(:lang, :loginname)";
		getCountQuery = "SELECT soberano.\"fn_Withdrawal_getCount\"(:lang, :loginname) AS count";
		getAllQueryNamedParameters = new HashMap<String, Object>();
		getAllQueryNamedParameters.put("lang", Locales.getCurrent().getLanguage());		
	}
	
	public Withdrawal(Integer cashRegister,
					Integer order,
					Integer receivable,
					ArrayList<Integer> currencyIds,
					ArrayList<BigDecimal> amounts) {
		this.setCashRegister(cashRegister);
		this.setOrder(order);
		this.setReceivable(receivable);
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
	public Integer record() throws Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		recordQuery = "SELECT soberano.\"fn_Withdrawal_create\"(:cashRegister, "
				+ "											:order, "
				+ "											:receivable, "
				+ "											:currencyIds, "
				+ "											:amounts, "
				+ "											:loginname) AS queryresult";
		recordParameters = new MapSqlParameterSource();
		recordParameters.addValue("cashRegister", this.getCashRegister());
		recordParameters.addValue("order", this.getOrder());
		recordParameters.addValue("receivable", this.getReceivable());
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

	public Integer getOrder() {
		return Order;
	}

	public void setOrder(Integer order) {
		Order = order;
	}

	public Integer getReceivable() {
		return Receivable;
	}

	public void setReceivable(Integer receivable) {
		Receivable = receivable;
	}

	public Integer getCashRegister() {
		return cashRegister;
	}

	public void setCashRegister(Integer cashRegister) {
		this.cashRegister = cashRegister;
	}
}
