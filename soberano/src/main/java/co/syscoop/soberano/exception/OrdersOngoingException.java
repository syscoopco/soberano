package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class OrdersOngoingException extends SoberanoException {
	
	public OrdersOngoingException() {
		super(SoberanoExceptionCodes.ORDERS_ONGOING);
	}

	public OrdersOngoingException(Throwable cause) {
		super(cause);
		this.code = SoberanoExceptionCodes.ORDERS_ONGOING;
	}
}
