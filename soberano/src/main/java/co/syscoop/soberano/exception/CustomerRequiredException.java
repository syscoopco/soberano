package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class CustomerRequiredException extends SoberanoException {
	
	public CustomerRequiredException() {
		super(SoberanoExceptionCodes.CUSTOMER_REQUIRED);
	}

	public CustomerRequiredException(Throwable cause) {
		super(cause);
		this.code = SoberanoExceptionCodes.CUSTOMER_REQUIRED;
	}
}
