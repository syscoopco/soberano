package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class ComposedValueTypesUnsupportedException extends SoberanoException{

	public ComposedValueTypesUnsupportedException(String valueTypeName) {
		super(SoberanoExceptionCodes.COMPOSED_VALUETYPES_UNSUPPORTED, valueTypeName);
	}
}
