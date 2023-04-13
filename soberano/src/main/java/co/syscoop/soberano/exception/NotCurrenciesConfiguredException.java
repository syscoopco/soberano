package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class NotCurrenciesConfiguredException extends SoberanoException {
	
	public NotCurrenciesConfiguredException() {
		super(SoberanoExceptionCodes.NO_CURRENCIES_CONFIGURED);
	}

	public NotCurrenciesConfiguredException(Throwable cause) {
		super(cause);
		this.code = SoberanoExceptionCodes.NO_CURRENCIES_CONFIGURED;
	}
}
