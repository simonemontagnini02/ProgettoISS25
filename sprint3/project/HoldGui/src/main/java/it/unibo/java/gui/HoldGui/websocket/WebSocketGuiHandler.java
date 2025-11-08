package it.unibo.java.gui.HoldGui.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketGuiHandler extends TextWebSocketHandler{
	
	private final WebSocketGuiSessionContainer sessions;
	
	
	@Autowired
	public WebSocketGuiHandler(WebSocketGuiSessionContainer sessions) {
		
		this.sessions=sessions;
		
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
		System.out.println(session.getId() + " Connected");
		
		sessions.add(session);
	}
	
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		
		System.out.println(session.getId() + " Disconnected");
		sessions.remove(session);
		
	}
	
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		System.out.println("Sessione: "+session.getId()+" messaggio inviato: "+message);
		
	}
	
	
	

}
