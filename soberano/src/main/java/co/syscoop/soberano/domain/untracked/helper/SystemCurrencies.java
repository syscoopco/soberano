package co.syscoop.soberano.domain.untracked.helper;

import java.math.BigDecimal;

public class SystemCurrencies {
	
	private String systemCurrencyCode = "";
	private String referenceCurrencyCode = "";
	private BigDecimal referenceCurrencyExchangeRate = new BigDecimal(0.0);
	
	public SystemCurrencies(String systemCurrencyCode, 
							String referenceCurrencyCode, 
							BigDecimal referenceCurrencyExchangeRate) {
		this.setSystemCurrencyCode(systemCurrencyCode);
		this.setReferenceCurrencyCode(referenceCurrencyCode);
		this.setReferenceCurrencyExchangeRate(referenceCurrencyExchangeRate);
	}

	public String getSystemCurrencyCode() {
		return systemCurrencyCode;
	}

	public void setSystemCurrencyCode(String systemCurrencyCode) {
		this.systemCurrencyCode = systemCurrencyCode;
	}

	public String getReferenceCurrencyCode() {
		return referenceCurrencyCode;
	}

	public void setReferenceCurrencyCode(String referenceCurrencyCode) {
		this.referenceCurrencyCode = referenceCurrencyCode;
	}

	public BigDecimal getReferenceCurrencyExchangeRate() {
		return referenceCurrencyExchangeRate;
	}

	public void setReferenceCurrencyExchangeRate(BigDecimal referenceCurrencyExchangeRate) {
		this.referenceCurrencyExchangeRate = referenceCurrencyExchangeRate;
	}
}