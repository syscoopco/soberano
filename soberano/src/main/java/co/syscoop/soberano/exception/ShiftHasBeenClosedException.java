package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class ShiftHasBeenClosedException extends SoberanoException {
	
	public ShiftHasBeenClosedException() {
		super(SoberanoExceptionCodes.SHIFT_HAS_BEEN_CLOSED);
	}

	public ShiftHasBeenClosedException(Throwable cause) {
		super(cause);
		this.code = SoberanoExceptionCodes.SHIFT_HAS_BEEN_CLOSED;
	}
}
