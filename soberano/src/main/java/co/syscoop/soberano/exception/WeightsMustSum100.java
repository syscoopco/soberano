package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class WeightsMustSum100 extends SoberanoException {
	
	public WeightsMustSum100() {
		super(SoberanoExceptionCodes.WEIGHTS_MUST_SUM_100);
	}

	public WeightsMustSum100(Throwable cause) {
		super(cause);
		this.code = SoberanoExceptionCodes.WEIGHTS_MUST_SUM_100;
	}
}
