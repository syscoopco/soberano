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
import co.syscoop.soberano.exception.ShiftHasBeenClosedException;
import co.syscoop.soberano.exception.SoberanoException;
import co.syscoop.soberano.util.SpringUtility;

public class Deposit extends BusinessActivityTrackedObject {
	
	private Integer cashRegister = 0;
	private Integer Order = 0;
	private Integer Receivable = 0;
	private ArrayList<Integer> currencyIds = new ArrayList<Integer>();
	private ArrayList<BigDecimal> amounts = new ArrayList<BigDecimal>();
	
	public Deposit() {
		getAllQuery = "SELECT * FROM soberano.\"" 
							+ "fn_Deposit_getAll\"" 
							+ "(:lang, :loginname)";
		getCountQuery = "SELECT soberano.\"fn_Deposit_getCount\"(:lang, :loginname) AS count";
		getAllQueryNamedParameters = new HashMap<String, Object>();
		getAllQueryNamedParameters.put("lang", Locales.getCurrent().getLanguage());		
	}
	
	public Deposit(Integer cashRegister,
					Integer order,
					Integer receivable,
					ArrayList<Integer> currencyIds,
					ArrayList<BigDecimal> amounts,
					String notes) {
		this.setCashRegister(cashRegister);
		this.setOrder(order);
		this.setReceivable(receivable);
		this.setCurrencyIds(currencyIds);
		this.setAmounts(amounts);
		this.setNotes(notes);}

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
		recordQuery = "SELECT soberano.\"fn_Deposit_create\"(:cashRegister, "
				+ "											:excludeCash, "
				+ "											:order, "
				+ "											:receivable, "
				+ "											:currencyIds, "
				+ "											:amounts, "
				+ "											:notes, "
				+ "											:loginname) AS queryresult";
		recordParameters = new MapSqlParameterSource();
		recordParameters.addValue("cashRegister", this.getCashRegister());
		recordParameters.addValue("excludeCash", false);
		recordParameters.addValue("order", this.getOrder());
		recordParameters.addValue("receivable", this.getReceivable());
		recordParameters.addValue("currencyIds", createArrayOfSQLType("integer", this.currencyIds.toArray()));
		recordParameters.addValue("amounts", createArrayOfSQLType("numeric", this.amounts.toArray()));
		recordParameters.addValue("notes", this.getNotes());
		
		return super.record();
	}
	
	@Override
	public PrintableData getReportFull() throws SQLException {
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		String qryStr = "SELECT * FROM soberano.\"fn_Deposit_getReport\"(:opid, "
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
		
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		disableQuery = "SELECT soberano.\"fn_Deposit_disable\"(:operationId, "
				+ "											:loginname) AS queryresult";
		disableParameters = new MapSqlParameterSource();
		disableParameters.addValue("operationId", this.getId());
		
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
