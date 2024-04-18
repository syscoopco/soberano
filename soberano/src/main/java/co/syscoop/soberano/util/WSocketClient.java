package co.syscoop.soberano.util;

import java.net.URI;
import java.util.Map;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

public class WSocketClient extends WebSocketClient {

	public WSocketClient(URI serverUri, Draft draft) {
	  
		super(serverUri, draft);
	}

	public WSocketClient(URI serverURI) {
	  
		super(serverURI);
	}

	public WSocketClient(URI serverUri, Map<String, String> httpHeaders) {
  
		super(serverUri, httpHeaders);
	}

	@Override
	public void onOpen(ServerHandshake handshakedata) {
	}

	@Override
	public void onMessage(String message) {
	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
	}

	@Override
	public void onError(Exception ex) {
		ex.printStackTrace();
	}
}
