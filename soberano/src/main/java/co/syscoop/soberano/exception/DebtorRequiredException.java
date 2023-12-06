package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class DebtorRequiredException extends SoberanoException {
	
	public DebtorRequiredException() {
		super(SoberanoExceptionCodes.DEBTOR_REQUIRED);
	}

	public DebtorRequiredException(Throwable cause) {
		super(cause);
		this.code = SoberanoExceptionCodes.DEBTOR_REQUIRED;
	}
}
