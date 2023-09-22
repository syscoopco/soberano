package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class RunningOutOfInventoryException extends SoberanoException {
	
	public RunningOutOfInventoryException() {
		super(SoberanoExceptionCodes.RUNNING_OUT_OF_INVENTORY);
	}

	public RunningOutOfInventoryException(Throwable cause) {
		super(cause);
		this.code = SoberanoExceptionCodes.RUNNING_OUT_OF_INVENTORY;
	}
}