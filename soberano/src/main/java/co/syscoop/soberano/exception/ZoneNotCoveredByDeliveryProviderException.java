package co.syscoop.soberano.exception;

@SuppressWarnings("serial")
public class ZoneNotCoveredByDeliveryProviderException extends SoberanoException {
	
	public ZoneNotCoveredByDeliveryProviderException() {
		super(SoberanoExceptionCodes.ZONE_NOT_COVERED_BY_DELIVERY_PROVIDER);
	}

	public ZoneNotCoveredByDeliveryProviderException(Throwable cause) {
		super(cause);
		this.code = SoberanoExceptionCodes.ZONE_NOT_COVERED_BY_DELIVERY_PROVIDER;
	}
}
