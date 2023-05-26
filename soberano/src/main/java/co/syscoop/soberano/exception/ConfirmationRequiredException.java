package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class ConfirmationRequiredException extends SoberanoException {
	
	public ConfirmationRequiredException() {
		super(SoberanoExceptionCodes.CONFIRMATION_REQUIRED);
	}

	public ConfirmationRequiredException(Throwable cause) {
		super(cause);
		this.code = SoberanoExceptionCodes.CONFIRMATION_REQUIRED;
	}
}
