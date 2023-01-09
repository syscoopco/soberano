package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class NotEnoughRightsException extends SoberanoException {
	
	public NotEnoughRightsException() {
		super(SoberanoExceptionCodes.NOT_ENOUGH_RIGHTS);
	}

	public NotEnoughRightsException(Throwable cause) {
		super(cause);
		this.code = SoberanoExceptionCodes.NOT_ENOUGH_RIGHTS;
	}
}
