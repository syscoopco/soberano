package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class ObjectifiedUnaryFactTypesUnsupportedException extends SoberanoException{

	public ObjectifiedUnaryFactTypesUnsupportedException(String factTypeName) {
		super(SoberanoExceptionCodes.OBJECTIFIED_UNARY_FACTTYPES_UNSUPPORTED, factTypeName);
	}
}
