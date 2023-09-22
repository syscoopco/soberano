package co.syscoop.soberano.domain.tracked;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.zkoss.util.Locales;

import co.syscoop.soberano.database.relational.CurrencyMapper;
import co.syscoop.soberano.database.relational.PaymentProcessorMapper;
import co.syscoop.soberano.util.SpringUtility;

public class CashRegister extends TrackedObject {

	private HashMap<String, BigDecimal> balances = new HashMap<String, BigDecimal>();
	
	public CashRegister() {
		getAllQuery = "SELECT * FROM soberano.\"" 
							+ "fn_CashRegister_getAllOperations\"" 
							+ "(:lang, :loginname)";
		getCountQuery = "SELECT soberano.\"fn_CashRegister_getAllOperationsCount\"(:lang, :loginname) AS count";
		getAllQueryNamedParameters = new HashMap<String, Object>();
		getAllQueryNamedParameters.put("lang", Locales.getCurrent().getLanguage());		
	}
	
	public CashRegister(Integer id) {
		super(id);
	}
	
	public CashRegister(Integer id, 
			Integer entityTypeInstanceId, 
			Integer printer) {
		super(id, entityTypeInstanceId);
		this.setPrinter(printer);
	}
	
	public CashRegister(Integer id, 
					Integer entityTypeInstanceId, 
					Integer printer,
					HashMap<String, BigDecimal> balances) {
		super(id, entityTypeInstanceId);
		this.setPrinter(printer);
		this.setBalances(balances);
	}
		
	public final class CashRegisterExtractor implements ResultSetExtractor<Object> {
		
		@Override
		public CashRegister extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			CashRegister cashRegister = null;
			Integer cashRegisterCurrentlyBeingExtractedId = -1;
	        while (rs.next()) {
	        	if (cashRegisterCurrentlyBeingExtractedId != rs.getInt("cashRegisterId")) {
	        		cashRegisterCurrentlyBeingExtractedId = rs.getInt("cashRegisterId");
	        		cashRegister = new CashRegister(rs.getInt("cashRegisterId"),
					        						rs.getInt("entityTypeInstanceId"),
					        						rs.getInt("printer"));
	        	}
	        	String currencyCode = rs.getString("currencyCode");
	        	BigDecimal balance = rs.getBigDecimal("balance");
	        	if (currencyCode != null && !currencyCode.isEmpty() && balance != null) {
	        		cashRegister.getBalances().put(currencyCode, balance);
	        	}	        	
	        }
	        return cashRegister;
		}
	}
	
	@Override
	public void get() throws SQLException {
		
		getQuery = "SELECT * FROM soberano.\"fn_CashRegister_get\"(:cashRegisterId, :loginname)";
		getParameters = new HashMap<String, Object>();
		getParameters.put("cashRegisterId", this.getId());
		super.get(new CashRegisterExtractor());
	}

	public HashMap<String, BigDecimal> getBalances() {
		return balances;
	}

	public void setBalances(HashMap<String, BigDecimal> balances) {
		this.balances = balances;
	}

	@Override
	public Integer print() throws SQLException {
		return null;
	}

	@Override
	protected void copyFrom(Object sourceObject) {
		
		CashRegister sourceCashRegister = (CashRegister) sourceObject;
		setId(sourceCashRegister.getId());
		setStringId(sourceCashRegister.getStringId());
		setEntityTypeInstanceId(sourceCashRegister.getEntityTypeInstanceId());
		setPrinter(sourceCashRegister.getPrinter());
		setBalances(sourceCashRegister.getBalances());
	}
	
	public List<Object> getCurrencies() throws SQLException {
		
		String qryStr = "SELECT * FROM soberano.\"fn_CashRegister_getCurrencies\"(:cashRegisterId, :loginname)";
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("cashRegisterId", this.getId());
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(qryStr, parametersMap, new CurrencyMapper());
	}
	
	public List<Object> getPaymentProcessors() throws SQLException {
		
		String qryStr = "SELECT * FROM soberano.\"fn_CashRegister_getPaymentProcessors\"(:cashRegisterId, :loginname)";
		Map<String,	Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("cashRegisterId", this.getId());
		parametersMap.put("loginname", SpringUtility.loggedUser().toLowerCase());
		return super.query(qryStr, parametersMap, new PaymentProcessorMapper());
	}
}
