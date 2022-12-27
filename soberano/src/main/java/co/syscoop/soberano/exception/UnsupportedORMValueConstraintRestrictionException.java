package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class UnsupportedORMValueConstraintRestrictionException extends SoberanoException{

	public UnsupportedORMValueConstraintRestrictionException() {
		super(SoberanoExceptionCodes.UNSUPPORTED_ORM_VALUECONSTRAINT_RESTRICTION);
	}
}