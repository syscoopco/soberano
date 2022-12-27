package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class MultipleInheritanceUnsupportedException extends SoberanoException{

	public MultipleInheritanceUnsupportedException(String objectTypeName) {
		super(SoberanoExceptionCodes.MULTIPLE_INHERITANCE_UNSUPPORTED, objectTypeName);
	}
}
