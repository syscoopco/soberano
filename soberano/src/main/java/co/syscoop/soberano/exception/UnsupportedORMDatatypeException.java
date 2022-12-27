package co.syscoop.soberano.exception;

/**
 * The Object-Role Modeling data type isn't supported by the PostgreSQL generator. Please, modify your 
 * conceptual model or update the generator to a newer version."
 */

@SuppressWarnings("serial")
public class UnsupportedORMDatatypeException extends SoberanoException{

	public UnsupportedORMDatatypeException(String dataTypeClassName) {
		super(SoberanoExceptionCodes.UNSUPPORTED_ORM_DATATYPE, dataTypeClassName);
	}
}
