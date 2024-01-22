package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class OnlyOneOrderPerCounterIsPermittedException extends SoberanoException {
	
	public OnlyOneOrderPerCounterIsPermittedException() {
		super(SoberanoExceptionCodes.ONLY_ONE_ORDER_PER_COUNTER_PERMITTED);
	}

	public OnlyOneOrderPerCounterIsPermittedException(Throwable cause) {
		super(cause);
		this.code = SoberanoExceptionCodes.ONLY_ONE_ORDER_PER_COUNTER_PERMITTED;
	}
}
