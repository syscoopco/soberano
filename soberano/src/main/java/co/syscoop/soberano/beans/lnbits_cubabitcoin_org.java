package co.syscoop.soberano.beans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import org.zkoss.json.JSONObject;
import org.zkoss.json.parser.JSONParser;

import co.syscoop.soberano.util.PaymentLink;
import co.syscoop.soberano.ws.RESTClient;

public class lnbits_cubabitcoin_org implements IPaymentProcessor {
	
	private String X_Api_Key = "";
	private final String apiUrl = "https://lnbits.cubabitcoin.org/lnurlp/api/v1";
	
	@Override
	public void setParameters(HashMap<String, String> parameters) {
		this.X_Api_Key = parameters.get("X-Api-Key");
	}
	
	private Integer convertUSDToSats(BigDecimal amount) throws MalformedURLException, ProtocolException, IOException, Exception {
		
		URL url = new URL("https://api.yadio.io/convert/" + amount.toPlainString() + "/usd/btc");
		
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setConnectTimeout(60000);
		con.setReadTimeout(60000);
		
		StringBuilder response = new StringBuilder();
		if (con.getResponseCode() < HttpURLConnection.HTTP_OK && con.getResponseCode() > 202) {
            throw new RuntimeException("Error retrieving USDBTC rate from yadio.io. HTTP error code: " + con.getResponseCode());
        }
		else {
			try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {				
			    String responseLine = null;
			    while ((responseLine = br.readLine()) != null) {
			        response.append(responseLine.trim());
			    }
			}
		}
		con.disconnect();
		
		Double dblBTC = (Double) ((JSONObject) new JSONParser().parse(response.toString())).get("result") * 100000000;
		return dblBTC.intValue();
	}

	@Override
	public PaymentLink createPaymentLink(Integer orderId, String invoiceContent, BigDecimal amount) throws MalformedURLException, ProtocolException, IOException, Exception {
		
		ArrayList<Object> headerFields = new ArrayList<Object>();
		ArrayList<Object> headerValues = new ArrayList<Object>();
		ArrayList<Object> requestFields = new ArrayList<Object>();
		ArrayList<Object> requestValues = new ArrayList<Object>();
		
		//convert amount to sats
		Integer amountInSats = convertUSDToSats(amount);
		
		//form header
		headerFields.add("X-Api-Key");
		headerValues.add(X_Api_Key);
		
		headerFields.add("Content-Type");
		headerValues.add("application/json");
		
		//form request body
		requestFields.add("description");
		requestValues.add(invoiceContent);		
		
		requestFields.add("amount");
		requestValues.add(amountInSats);
		
		requestFields.add("max");
		requestValues.add(amountInSats);
		
		requestFields.add("min");
		requestValues.add(amountInSats);
		
		requestFields.add("comment_chars");
		requestValues.add("0");		
		
		//send http request
		String response = (new RESTClient().call(apiUrl,
										"/links",
										"POST",
										headerFields.toArray(),
										headerValues.toArray(),
										requestFields.toArray(),
										requestValues.toArray()));
		
		return new PaymentLink("https://lnbits.cubabitcoin.org/lnurlp/link/" + ((String) ((JSONObject) new JSONParser().parse(response)).get("id")), "");
	}

	@Override
	public Boolean openPaymentLinkInNewWindow() {
		return true;
	}
}
