package it.unibo.java.gui.HoldGui.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Component
public class WebSocketClientHandler extends TextWebSocketHandler{
	
	private final WebSocketClientSessionContainer sessions;
	private final ClientMessageService service;

	
	@Autowired
	public WebSocketClientHandler(WebSocketClientSessionContainer sessions, ClientMessageService service) {
		
		this.sessions=sessions;
		this.service = service;
		
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
		System.out.println(session.getId() + " Client Connected");
		
		sessions.add(session);
	}
	
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		
		System.out.println(session.getId() + " Client Disconnected");
		sessions.remove(session);
		
	}
	
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
			
		JsonObject obj = JsonParser.parseString(message.getPayload()).getAsJsonObject();
		String risposta="";
		
		if(obj.get("Type").getAsString().equals("Register")) {
			
			risposta=service.productserviceMessage(obj.get("PID").getAsString(), obj.get("Nome").getAsString(), obj.get("Peso").getAsInt());
	
		}
		else {
			
			risposta=service.cargoserviceMessage(obj.get("PID").getAsString());
		}
		
		session.sendMessage(new TextMessage(risposta));
		
	}
	
	
	

}
