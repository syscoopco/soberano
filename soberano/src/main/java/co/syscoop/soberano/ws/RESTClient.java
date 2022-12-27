package co.syscoop.soberano.ws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.zkoss.json.JSONObject;

public class RESTClient {

	//utf-8 charset and json restricted
	public String call(String url,
				String endpoint, 
				String method,
				Object[] headerFields, 
				Object[] headerValues, 
				Object[] requestFields,
				Object[] requestValues) throws MalformedURLException, ProtocolException, IOException, Exception {
			
		//http header and body string arrays are well formed
		if (headerFields != null && 
			headerValues != null &&  
			requestFields != null && 
			requestValues != null &&
			(headerFields.length == headerValues.length) && 
			(requestFields.length == requestValues.length)) {
			
			//set request method
			URL finalURL = new URL (url + endpoint);			
			HttpURLConnection conn = (HttpURLConnection) finalURL.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod(method);
			
			//build request header
			for (int i = 0; i < headerFields.length; i++) {
				conn.setRequestProperty((String) headerFields[i], (String) headerValues[i]);
			}
			
			//build request body
			JSONObject json = new JSONObject();
			for (int i = 0; i < requestFields.length; i++) {				
				json.put(requestFields[i], requestValues[i]);
			}
			
			//call
			String JSONInputString = json.toString();			
			try(OutputStream os = conn.getOutputStream()) {
			    byte[] input = JSONInputString.getBytes("utf-8");
			    os.write(input, 0, input.length);			
			}
			
			StringBuilder response = new StringBuilder();
			if (conn.getResponseCode() < HttpURLConnection.HTTP_OK && conn.getResponseCode() > 202) {
	            throw new RuntimeException("Method 'call' of class 'RESTClient' failed with HTTP error code: " + conn.getResponseCode());
	        }
			else {
				try(BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {				
				    String responseLine = null;
				    while ((responseLine = br.readLine()) != null) {
				        response.append(responseLine.trim());
				    }
				}
			}
			conn.disconnect();
			return response.toString();
		}
		else throw new Exception("Lengths of (HTTP request header or body) fields and values arrays don't match.");
	}
}
