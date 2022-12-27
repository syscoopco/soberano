package co.syscoop.soberano.exception;

/**
 * An exception has been thrown by the DBMS driver.
 */

@SuppressWarnings("serial")
public class SoberanoSQLException extends SoberanoException{

	public SoberanoSQLException(String DBMSErrorMessage) {
		super(SoberanoExceptionCodes.SQL_EXCEPTION, DBMSErrorMessage);
	}
}