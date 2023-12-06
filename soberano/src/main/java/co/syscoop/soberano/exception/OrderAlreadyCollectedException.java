package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class OrderAlreadyCollectedException extends SoberanoException {
	
	public OrderAlreadyCollectedException() {
		super(SoberanoExceptionCodes.ORDER_ALREADY_COLLECTED);
	}

	public OrderAlreadyCollectedException(Throwable cause) {
		super(cause);
		this.code = SoberanoExceptionCodes.ORDER_ALREADY_COLLECTED;
	}
}
