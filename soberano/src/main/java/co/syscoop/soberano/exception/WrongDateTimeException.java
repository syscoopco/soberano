package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class WrongDateTimeException extends SoberanoException {
	
	public WrongDateTimeException() {
		super(SoberanoExceptionCodes.WRONG_DATE);
	}

	public WrongDateTimeException(Throwable cause) {
		super(cause);
		this.code = SoberanoExceptionCodes.WRONG_DATE;
	}
}
