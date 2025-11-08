package it.unibo.java.gui.HoldGui.websocket;

import org.springframework.stereotype.Service;

import it.unibo.java.gui.HoldGui.config.ClientMessageConfig;
import unibo.basicomm23.interfaces.*;
import unibo.basicomm23.utils.*;

@Service
public class ClientMessageService {
	

	private  Interaction ProductServiceConn, CargoserviceConn;
	
	public ClientMessageService() {
		
		try {
			
			ProductServiceConn = ConnectionFactory.createClientSupport23(ClientMessageConfig.protocol, ClientMessageConfig.HOST_ADDR, ClientMessageConfig.PRODUCT_SERVICE_PORT);
			CargoserviceConn = ConnectionFactory.createClientSupport23(ClientMessageConfig.protocol, ClientMessageConfig.HOST_ADDR, ClientMessageConfig.CARGO_SERVICE_PORT);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Impossibile connettersi con gli attori");
		}
		
	}

    public void productserviceMessage(String PID,String nome,int peso) {
        
        try {
			ProductServiceConn.forward(ClientMessageConfig.buildCreateProductMessage(PID, nome, peso));
		} catch (Exception e) {
			e.printStackTrace();
		}
        
    }
    
    
    public void cargoserviceMessage(String PID) {
    	
    	try {
    		
    		CargoserviceConn.forward(ClientMessageConfig.buildLoadRequest(PID));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
}
