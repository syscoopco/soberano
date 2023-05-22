package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class SomeFieldsContainWrongValuesException extends SoberanoException {
	
	public SomeFieldsContainWrongValuesException() {
		super(SoberanoExceptionCodes.SOME_FIELDS_CONTAIN_WRONG_VALUES);
	}

	public SomeFieldsContainWrongValuesException(Throwable cause) {
		super(cause);
		this.code = SoberanoExceptionCodes.SOME_FIELDS_CONTAIN_WRONG_VALUES;
	}
}
