package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class ProcessRunningException extends SoberanoException {
	
	public ProcessRunningException() {
		super(SoberanoExceptionCodes.PROCESS_RUNNING);
	}

	public ProcessRunningException(Throwable cause) {
		super(cause);
		this.code = SoberanoExceptionCodes.PROCESS_RUNNING;
	}
}
