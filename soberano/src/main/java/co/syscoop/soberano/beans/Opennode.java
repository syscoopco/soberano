package co.syscoop.soberano.beans;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.HashMap;
import org.zkoss.json.JSONObject;
import org.zkoss.json.parser.JSONParser;
import co.syscoop.soberano.ws.RESTClient;

public class Opennode implements IPaymentProcessor {
	
	private String authorization = "";
	
	private final String checkout_url = "https://app.dev.opennode.com";
	
	@Override
	public void setParameters(HashMap<String, String> parameters) {
		this.authorization = parameters.get("Authorization");
	}

	@Override
	public String createPaymentLink(Integer orderId, String invoiceContent, BigDecimal amount) throws MalformedURLException, ProtocolException, IOException, Exception {
		
		ArrayList<Object> headerFields = new ArrayList<Object>();
		ArrayList<Object> headerValues = new ArrayList<Object>();
		ArrayList<Object> requestFields = new ArrayList<Object>();
		ArrayList<Object> requestValues = new ArrayList<Object>();
		
		//form header
		headerFields.add("Accept");
		headerValues.add("application/json");
		
		headerFields.add("Content-Type");
		headerValues.add("application/json");
		
		headerFields.add("Authorization");
		headerValues.add(authorization);					
		
		//form request body
		requestFields.add("description");
		requestValues.add(invoiceContent);		
		
		requestFields.add("amount");
		requestValues.add(amount.toPlainString());
		
		requestFields.add("currency");
		requestValues.add("USD");
		
		requestFields.add("customer_email");
		requestValues.add("");
		
		requestFields.add("notif_email");
		requestValues.add("");
		
		requestFields.add("customer_name");
		requestValues.add("");						
		
		requestFields.add("order_id");
		requestValues.add(orderId);
		
		requestFields.add("success_url");
		requestValues.add("https://");						
		
		requestFields.add("auto_settle");
		requestValues.add(false);
		
		requestFields.add("ttl");
		requestValues.add(1440);
		
		//send http request
		String response = (new RESTClient().call(checkout_url,
										"/charges",
										"POST",
										headerFields.toArray(),
										headerValues.toArray(),
										requestFields.toArray(),
										requestValues.toArray()));
		JSONObject data = (JSONObject) ((JSONObject) new JSONParser().parse(response)).get("data");
		
		String checkoutURL = checkout_url + "/" + (String) data.get("id");
	    
		return checkoutURL;
	}
}
