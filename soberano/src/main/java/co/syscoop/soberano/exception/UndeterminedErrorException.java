package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class UndeterminedErrorException extends SoberanoException{

	public UndeterminedErrorException() {
		super(SoberanoExceptionCodes.UNDETERMINED_ERROR);
	}
}