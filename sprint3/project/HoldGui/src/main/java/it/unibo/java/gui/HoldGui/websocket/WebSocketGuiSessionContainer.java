package it.unibo.java.gui.HoldGui.websocket;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class WebSocketGuiSessionContainer {
	
	private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();
	
	
	public void add(WebSocketSession s) {
		
		this.sessions.add(s);
	}

	public void remove(WebSocketSession s) {
		
		this.sessions.remove(s);
	}
	
	public Set<WebSocketSession> getSessions() {
		return sessions;
	}
	
	public void broadcast(String msg) throws IOException {
		
		for(WebSocketSession s: this.sessions) {
			
			if(s.isOpen()) {
				
				s.sendMessage(new TextMessage(msg));
			}
			
		}
		
		
	}

}
