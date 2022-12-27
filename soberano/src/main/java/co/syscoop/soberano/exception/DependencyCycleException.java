package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class DependencyCycleException extends SoberanoException{

	public DependencyCycleException() {
		super(SoberanoExceptionCodes.DEPENDENCY_CYCLE);
	}
}