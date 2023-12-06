package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class OrderCanceledException extends SoberanoException {
	
	public OrderCanceledException() {
		super(SoberanoExceptionCodes.ORDER_CANCELED);
	}

	public OrderCanceledException(Throwable cause) {
		super(cause);
		this.code = SoberanoExceptionCodes.ORDER_CANCELED;
	}
}
