package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class WrongProcessSpecificationException extends SoberanoException {
	
	public WrongProcessSpecificationException() {
		super(SoberanoExceptionCodes.WRONG_PROCESS_SPECIFICATION);
	}

	public WrongProcessSpecificationException(Throwable cause) {
		super(cause);
		this.code = SoberanoExceptionCodes.WRONG_PROCESS_SPECIFICATION;
	}
}