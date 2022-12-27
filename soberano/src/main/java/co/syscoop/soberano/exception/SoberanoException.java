package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class SoberanoException extends Exception {
	
	private Exception cause = null;
	
	private SoberanoExceptionCodes code = SoberanoExceptionCodes.UNDETERMINED_ERROR;
	
	public SoberanoException(Exception cause) {
		this.cause = cause;
		this.cause.printStackTrace();
		this.cause.fillInStackTrace();
	}
	
	public SoberanoException(SoberanoExceptionCodes code) {
		this.cause = new Exception();
		this.code = code;
		this.cause.initCause(this);
		this.cause.printStackTrace();
		this.cause.fillInStackTrace();
	}
	
	public SoberanoException(SoberanoExceptionCodes code, String details) {
		this.cause = new Exception(details);
		this.code = code;
		this.cause.initCause(this);
		this.cause.printStackTrace();
		this.cause.fillInStackTrace();
	}

	public SoberanoExceptionCodes getCode() {
		return code;
	}
}
