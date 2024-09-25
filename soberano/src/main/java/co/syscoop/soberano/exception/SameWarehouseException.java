package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class SameWarehouseException extends SoberanoException {
	
	public SameWarehouseException() {
		super(SoberanoExceptionCodes.SAME_WAREHOUSE);
	}

	public SameWarehouseException(Throwable cause) {
		super(cause);
		this.code = SoberanoExceptionCodes.SAME_WAREHOUSE;
	}
}
