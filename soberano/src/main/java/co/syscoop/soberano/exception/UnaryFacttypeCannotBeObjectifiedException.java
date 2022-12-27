package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class UnaryFacttypeCannotBeObjectifiedException extends SoberanoException{

	public UnaryFacttypeCannotBeObjectifiedException() {
		super(SoberanoExceptionCodes.UNARY_FACTTYPE_CANNOT_BE_OBJECTIFIED);
	}
}