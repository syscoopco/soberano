package co.syscoop.soberano.beans;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.zkoss.json.JSONObject;
import org.zkoss.json.parser.JSONParser;
import org.zkoss.zk.ui.Executions;

import co.syscoop.soberano.util.PaymentLink;
import co.syscoop.soberano.ws.RESTClient;

public class Tropipay implements IPaymentProcessor {
	
	private String clientId = "";
	private String clientSecret = "";
	private final String apiUrl = "https://tropipay-dev.herokuapp.com/api/v2";
	
	@Override
	public void setParameters(HashMap<String, String> parameters) {
		this.clientId = parameters.get("client_id");
		this.clientSecret = parameters.get("client_secret");
	}

	@Override
	public PaymentLink createPaymentLink(Integer orderId, String invoiceContent, BigDecimal amount) throws MalformedURLException, ProtocolException, IOException, Exception {
		
		ArrayList<Object> headerFields = new ArrayList<Object>();
		ArrayList<Object> headerValues = new ArrayList<Object>();
		ArrayList<Object> requestFields = new ArrayList<Object>();
		ArrayList<Object> requestValues = new ArrayList<Object>();
		
		//get the authentication token
		requestFields.add("grant_type");
		requestValues.add("client_credentials");
		
		requestFields.add("client_id");
		requestValues.add(clientId);
		
		requestFields.add("client_secret");
		requestValues.add(clientSecret);
		
		headerFields.add("Content-Type");
		headerValues.add("application/json; charset=utf-8");
		
		headerFields.add("User-Agent");
		headerValues.add(Executions.getCurrent().getHeader("user-agent"));
		
		String response;
		
		response = (new RESTClient().call(apiUrl,
										"/access/token",
										"POST",
										headerFields.toArray(),
										headerValues.toArray(),
										requestFields.toArray(),
										requestValues.toArray()));
		String accessToken = ((JSONObject) new JSONParser().parse(response)).get("access_token").toString();
		
		//generate payment link
		headerFields.clear();
		headerValues.clear();
		requestFields.clear();
		requestValues.clear();
		
		headerFields.add("Authorization");
		headerValues.add("Bearer " + accessToken);
		
		headerFields.add("Content-Type");
		headerValues.add("application/json");
		
		headerFields.add("User-Agent");
		headerValues.add(Executions.getCurrent().getHeader("user-agent"));
		
		requestFields.add("reference");
		requestValues.add(orderId.toString());
		
		requestFields.add("concept");
		requestValues.add("ONLINE PAYMENT");
		
		requestFields.add("favorite");
		requestValues.add(true);
		
		requestFields.add("description");
		requestValues.add(invoiceContent);
		
		requestFields.add("amount");
		requestValues.add(amount.multiply(new BigDecimal(100)).intValue());
		
		requestFields.add("currency");
		requestValues.add("USD");
		
		requestFields.add("singleUse");
		requestValues.add(true);
		
		requestFields.add("reasonId");
		requestValues.add(3);
		
		requestFields.add("expirationDays");
		requestValues.add(1);
		
		requestFields.add("lang");
		requestValues.add("en");
		
		/*
		requestFields.add("urlSuccess");
		requestValues.add("https://syscoop.co");
		
		requestFields.add("urlFailed");
		requestValues.add("https://syscoop.co");
		
		requestFields.add("urlNotification");
		requestValues.add("https://syscoop.co");
		*/
		
		requestFields.add("directPayment");
		requestValues.add(true);
		
		requestFields.add("serviceDate");
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		c.setTime(new Date());
		//c.add(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		requestValues.add(dateFormat.format(c.getTime()).toString());
		
		response = (new RESTClient().call(apiUrl,
											"/paymentcards",
											"POST",
											headerFields.toArray(),
											headerValues.toArray(),
											requestFields.toArray(),
											requestValues.toArray()));
		
		return new PaymentLink(((JSONObject) new JSONParser().parse(response)).get("shortUrl").toString(),
								((JSONObject) new JSONParser().parse(response)).get("qrImage").toString());
	}

	public String getApiUrl() {
		return apiUrl;
	}

	@Override
	public Boolean openPaymentLinkInNewWindow() {
		return false;
	}
}
