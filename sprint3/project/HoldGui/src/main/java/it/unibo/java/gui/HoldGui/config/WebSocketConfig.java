package it.unibo.java.gui.HoldGui.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import it.unibo.java.gui.HoldGui.websocket.WebSocketClientHandler;
import it.unibo.java.gui.HoldGui.websocket.WebSocketGuiHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	private final WebSocketGuiHandler guiHandler;
	private final WebSocketClientHandler clientHandler;
	
	 @Autowired
	    public WebSocketConfig(WebSocketGuiHandler guiHandler, WebSocketClientHandler clientHandler) {
	        this.guiHandler = guiHandler;
	        this.clientHandler= clientHandler;
	    }
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		System.out.println("Registro handler websocket...");
		registry.addHandler(guiHandler,"/ws" ).setAllowedOrigins("*");
		registry.addHandler(clientHandler,"/clientws" ).setAllowedOrigins("*");
		
		
	}
	

}
