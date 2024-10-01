package co.syscoop.soberano.beans;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;
import co.syscoop.soberano.util.PaymentLink;

public class MLC implements IPaymentProcessor {
	
	@Override
	public void setParameters(HashMap<String, String> parameters) {
	}

	@Override
	public PaymentLink createPaymentLink(Integer orderId, String invoiceContent, BigDecimal amount) throws MalformedURLException, ProtocolException, IOException, Exception {
		
		return new PaymentLink("", "");
	}

	@Override
	public Boolean openPaymentLinkInNewWindow() {
		return false;
	}
}
