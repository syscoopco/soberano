package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class FirstOrderRequiresCashOperationException extends SoberanoException {
	
	public FirstOrderRequiresCashOperationException() {
		super(SoberanoExceptionCodes.CASH_OPERATION_REQUIRED);
	}

	public FirstOrderRequiresCashOperationException(Throwable cause) {
		super(cause);
		this.code = SoberanoExceptionCodes.CASH_OPERATION_REQUIRED;
	}
}
