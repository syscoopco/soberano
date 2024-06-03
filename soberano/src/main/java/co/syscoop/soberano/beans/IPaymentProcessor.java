package co.syscoop.soberano.beans;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;

public interface IPaymentProcessor {
	
	public abstract void setParameters(HashMap<String, String> parameters);
	public abstract String createPaymentLink(Integer orderId, String invoiceContent, BigDecimal amount) throws MalformedURLException, ProtocolException, IOException, Exception;
}
