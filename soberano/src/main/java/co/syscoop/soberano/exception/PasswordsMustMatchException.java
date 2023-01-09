package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class PasswordsMustMatchException extends SoberanoException {
	
	public PasswordsMustMatchException() {
		super(SoberanoExceptionCodes.PASSWORDS_MUST_MATCH);
	}

	public PasswordsMustMatchException(Throwable cause) {
		super(cause);
		this.code = SoberanoExceptionCodes.PASSWORDS_MUST_MATCH;
	}
}