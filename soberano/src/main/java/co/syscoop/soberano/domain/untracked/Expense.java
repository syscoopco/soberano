package co.syscoop.soberano.domain.untracked;

import java.math.BigDecimal;

import co.syscoop.soberano.domain.tracked.Currency;

public class Expense extends DomainObject {

	private BigDecimal amount = new BigDecimal(0.0);	
	private Currency currency = new Currency();;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
}
