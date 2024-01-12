package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class ExchangeRateEqualsToZeroException extends SoberanoException {
	
	public ExchangeRateEqualsToZeroException() {
		super(SoberanoExceptionCodes.EXCHANGE_RATE_EQUALS_TO_ZERO);
	}

	public ExchangeRateEqualsToZeroException(Throwable cause) {
		super(cause);
		this.code = SoberanoExceptionCodes.EXCHANGE_RATE_EQUALS_TO_ZERO;
	}
}
