package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class CurrencyHasBalanceException extends SoberanoException {
	
	public CurrencyHasBalanceException() {
		super(SoberanoExceptionCodes.CURRENCY_HAS_BALANCE);
	}

	public CurrencyHasBalanceException(Throwable cause) {
		super(cause);
		this.code = SoberanoExceptionCodes.CURRENCY_HAS_BALANCE;
	}
}
