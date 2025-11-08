package it.unibo.java.gui.HoldGui.mqtt;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import it.unibo.java.gui.HoldGui.websocket.WebSocketGuiSessionContainer;

@Component
public class MqttHandler implements MessageHandler{

	 private final WebSocketGuiSessionContainer sessionContainer;
	
	 @Autowired
	 public MqttHandler(WebSocketGuiSessionContainer sessionContainer) {
	        this.sessionContainer = sessionContainer;
	    }
	
	@Override
	public void handleMessage(Message<?> message) throws MessagingException {
		
		String msg=message.getPayload().toString();
		
		int start = msg.indexOf('{');
		int end = msg.lastIndexOf('}');

		String json = msg.substring(start, end + 1);
		try {
			
			sessionContainer.broadcast(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
