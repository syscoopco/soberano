package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class WorkerMustBeAssignedToAResponsibilityException extends SoberanoException {
	
	public WorkerMustBeAssignedToAResponsibilityException() {
		super(SoberanoExceptionCodes.WORKER_MUST_BE_ASSIGNED_TO_A_RESPONSIBILITY);
	}

	public WorkerMustBeAssignedToAResponsibilityException(Throwable cause) {
		super(cause);
		this.code = SoberanoExceptionCodes.WORKER_MUST_BE_ASSIGNED_TO_A_RESPONSIBILITY;
	}
}