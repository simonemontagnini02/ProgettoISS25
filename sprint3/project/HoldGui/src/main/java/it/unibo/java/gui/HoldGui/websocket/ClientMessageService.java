package it.unibo.java.gui.HoldGui.websocket;

import org.springframework.stereotype.Service;
import com.google.gson.JsonObject;

import it.unibo.java.gui.HoldGui.config.ClientMessageConfig;
import unibo.basicomm23.interfaces.*;
import unibo.basicomm23.utils.*;

@Service
public class ClientMessageService {

	private Interaction ProductServiceConn, CargoserviceConn;

	public ClientMessageService() {

		try {

			ProductServiceConn = ConnectionFactory.createClientSupport23(ClientMessageConfig.protocol,
					ClientMessageConfig.HOST_ADDR, ClientMessageConfig.PRODUCT_SERVICE_PORT);
			CargoserviceConn = ConnectionFactory.createClientSupport23(ClientMessageConfig.protocol,
					ClientMessageConfig.HOST_ADDR, ClientMessageConfig.CARGO_SERVICE_PORT);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Impossibile connettersi con gli attori");
		}

	}

	public String productserviceMessage(String PID, String nome, int peso) {
		
		IApplMessage reply = null;
		JsonObject jsonStr=new JsonObject();

		try {
			reply = ProductServiceConn.request(ClientMessageConfig.buildCreateProductMessage(PID, nome, peso));

		} catch (Exception e) {
			e.printStackTrace();
		}
		jsonStr.addProperty("Type", "Register");

		if (reply.msgContent().contains("productid(0)")) {
			
			jsonStr.addProperty("PID", "0");
			
		} else {
			jsonStr.addProperty("PID", PID);
			
		}
		
		return jsonStr.toString();

	}

	public String cargoserviceMessage(String PID) {

		IApplMessage reply = null;
		int start,end;
		String payload;
		JsonObject jsonStr=new JsonObject();
		try {

			reply = CargoserviceConn.request(ClientMessageConfig.buildLoadRequest(PID));

		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		payload=reply.msgContent();
		
		jsonStr.addProperty("Type", "Request");
		
		if (reply.msgId().equals("load_accepted")) {
			
			
			start=payload.indexOf("(");
			end = payload.lastIndexOf(')');
			jsonStr.addProperty("Accepted", "ok");
			jsonStr.addProperty("Slot", payload.substring(start+1, end));

		} else {
			
			start=payload.indexOf("(");
			end = payload.lastIndexOf(')');
			jsonStr.addProperty("Accepted", "no");
			jsonStr.addProperty("Slot", payload.substring(start+1, end ));
			
			
		}
		
		return jsonStr.toString();

	}
}
