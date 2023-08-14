package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class DisabledCurrencyException extends SoberanoException {
	
	public DisabledCurrencyException() {
		super(SoberanoExceptionCodes.DISABLED_CURRENCY);
	}

	public DisabledCurrencyException(Throwable cause) {
		super(cause);
		this.code = SoberanoExceptionCodes.DISABLED_CURRENCY;
	}
}
