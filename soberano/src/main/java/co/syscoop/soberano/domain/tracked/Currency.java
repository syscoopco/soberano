package co.syscoop.soberano.domain.tracked;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import co.syscoop.soberano.domain.untracked.DomainObject;

public class Currency extends TrackedObject {

	private Boolean isSystemCurrency = false;
	private Boolean isPriceReferenceCurrency = false;
	private Boolean isCash = true;
	private BigDecimal exchangeRate = new BigDecimal(0.0);	
	private Integer position = 0;
	private Integer paymentProcessor = 1;
	
	public Currency(Integer id) {
		super(id);
	}
	
	public Currency(Integer id, 
					Integer entityTypeInstanceId, 
					String code,
					String name) {
		super(id, entityTypeInstanceId, name);
		this.setStringId(code);
		this.setQualifiedName(name + " : " + code);		
	}
	
	public Currency(Integer id, 
					Integer entityTypeInstanceId, 
					String code,
					String name,
					Boolean isSystemCurrency, 
					Boolean isPriceReferenceCurrency,
					Boolean isCash,
					BigDecimal exchangeRate,
					Integer position,
					Integer paymentProcessor) {
		this(id, entityTypeInstanceId, code, name);
		this.setIsSystemCurrency(isSystemCurrency);
		this.setIsPriceReferenceCurrency(isPriceReferenceCurrency);
		this.setIsCash(isCash);
		this.setExchangeRate(exchangeRate);
		this.setPosition(position);
		this.setPaymentProcessor(paymentProcessor);
	}
	
	public Currency() {
		getAllQuery = "SELECT * FROM soberano.\"" + "fn_Currency_getAll\"(:loginname)";
		getAllQueryNamedParameters = new HashMap<String, Object>();
	}
	
	@Override
	public Integer record() throws Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		recordQuery = "SELECT soberano.\"fn_Currency_create\"(:currencyCode, "
				+ "											:currencyName, "
				+ "											:isSystemCurrency, "
				+ "											:isPriceReferenceCurrency, "
				+ "											:isCash, "
				+ "											:exchangeRate, "
				+ "											:position, "
				+ "											:paymentProcessor, "
				+ "											:loginname) AS queryresult";
		recordParameters = new MapSqlParameterSource();
		recordParameters.addValue("currencyName", this.getName());
		recordParameters.addValue("currencyCode", this.getStringId());
		recordParameters.addValue("isSystemCurrency", this.getIsSystemCurrency());
		recordParameters.addValue("isPriceReferenceCurrency", this.getIsPriceReferenceCurrency());
		recordParameters.addValue("isCash", this.getIsCash());
		recordParameters.addValue("exchangeRate", this.getExchangeRate());
		recordParameters.addValue("position", this.getPosition());
		recordParameters.addValue("paymentProcessor", this.getPaymentProcessor());
		
		Integer qryResult = super.record();
		return qryResult > 0 ? qryResult : -1;
	}
	
	@Override
	public Integer modify() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		modifyQuery = "SELECT soberano.\"fn_Currency_modify\"(:currencyId, "
						+ " 								:currencyName, "
						+ "									:currencyCode, "
						+ "									:isSystemCurrency, "
						+ "									:isPriceReferenceCurrency, "
						+ "									:isCash, "
						+ "									:exchangeRate, "
						+ "									:position, "
						+ "									:paymentProcessor, "
						+ "									:loginname) AS queryresult";
		modifyParameters = new MapSqlParameterSource();
		modifyParameters.addValue("currencyId", this.getId());
		recordParameters.addValue("currencyName", this.getName());
		recordParameters.addValue("currencyCode", this.getStringId());
		recordParameters.addValue("isSystemCurrency", this.getIsSystemCurrency());
		recordParameters.addValue("isPriceReferenceCurrency", this.getIsPriceReferenceCurrency());
		recordParameters.addValue("isCash", this.getIsCash());
		recordParameters.addValue("exchangeRate", this.getExchangeRate());
		recordParameters.addValue("position", this.getPosition());
		recordParameters.addValue("paymentProcessor", this.getPaymentProcessor());
		
		Integer qryResult = super.modify();
		return qryResult >= 0 ? qryResult : -1;
	}
	
	@Override
	public Integer disable() throws SQLException, Exception {
					
		//it must be passed loginname. output alias must be queryresult. both in lower case.
		disableQuery = "SELECT soberano.\"fn_Currency_disable\"(:currencyId, "
				+ "											:loginname) AS queryresult";
		disableParameters = new MapSqlParameterSource();
		disableParameters.addValue("currencyId", this.getId());
		
		Integer qryResult = super.disable();
		return qryResult >= 0 ? qryResult : -1;
	}
	
	@Override
	public List<DomainObject> getAll(Boolean stringId) throws SQLException {	
		return super.getAll(false);
	}
		
	public final class CurrencyMapper implements RowMapper<Object> {

		public Currency mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			try {
				Currency currency = null;
				int id = rs.getInt("itemId");
				if (!rs.wasNull()) {
					currency = new Currency(id,
											rs.getInt("entityTypeInstanceId"),											
											rs.getString("itemCode"),
											rs.getString("itemName"),
											rs.getBoolean("isSystemCurrency"),
											rs.getBoolean("isPriceReferenceCurrency"),
											rs.getBoolean("isCash"),
											rs.getBigDecimal("exchangeRate"),
											rs.getInt("itemPosition"),
											rs.getInt("paymentProcessor"));
				}
				return currency;
			}
			catch(Exception ex)
			{
				throw ex;
			}			
	    }
	}
	
	@Override
	public void get() throws SQLException {
		
		getQuery = "SELECT * FROM soberano.\"fn_Currency_get\"(:currencyId, :loginname)";
		getParameters = new HashMap<String, Object>();
		getParameters.put("currencyId", this.getId());
		super.get(new CurrencyMapper());
	}

	@Override
	protected void copyFrom(Object sourceObject) {
		
		Currency sourceCurrency = (Currency) sourceObject;
		setId(sourceCurrency.getId());
		setEntityTypeInstanceId(sourceCurrency.getEntityTypeInstanceId());
		setName(sourceCurrency.getName());
		setStringId(sourceCurrency.getStringId());
		setIsSystemCurrency(sourceCurrency.getIsSystemCurrency());
		setIsPriceReferenceCurrency(sourceCurrency.getIsPriceReferenceCurrency());
		setIsCash(sourceCurrency.getIsCash());
		setExchangeRate(sourceCurrency.getExchangeRate());
		setPosition(sourceCurrency.getPosition());
		setPaymentProcessor(sourceCurrency.getPaymentProcessor());
	}
	
	@Override
	public Integer print() throws SQLException {
		
		// TODO print a report on the object
		return null;
	}

	public Boolean getIsSystemCurrency() {
		return isSystemCurrency;
	}

	public void setIsSystemCurrency(Boolean isSystemCurrency) {
		this.isSystemCurrency = isSystemCurrency;
	}

	public Boolean getIsPriceReferenceCurrency() {
		return isPriceReferenceCurrency;
	}

	public void setIsPriceReferenceCurrency(Boolean isPriceReferenceCurrency) {
		this.isPriceReferenceCurrency = isPriceReferenceCurrency;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public Boolean getIsCash() {
		return isCash;
	}

	public void setIsCash(Boolean isCash) {
		this.isCash = isCash;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getPaymentProcessor() {
		return paymentProcessor;
	}

	public void setPaymentProcessor(Integer paymentProcessor) {
		this.paymentProcessor = paymentProcessor;
	}
}
