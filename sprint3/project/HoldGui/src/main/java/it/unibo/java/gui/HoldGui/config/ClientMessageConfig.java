package it.unibo.java.gui.HoldGui.config;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;

public class ClientMessageConfig {
	

    public static final String CLIENT_NAME = "springclient";
    public static final String PROD_TEMPLATE = "{'\"productId\":PID,\"name\":\"NOME\",\"weight\":PESO'}";

    public static IApplMessage buildCreateProductMessage(String PID, String nome, int peso) {
        String prod = PROD_TEMPLATE.replace("PID", PID)
                                   .replace("NOME", nome)
                                   .replace("PESO", String.valueOf(peso));
        
        return CommUtils.buildRequest(CLIENT_NAME, "createProduct", "product(" + prod + ")", "productservice");
    }

    public static IApplMessage buildLoadRequest(String PID) {
        return CommUtils.buildRequest(CLIENT_NAME, "load_request", "load(" + PID + ")", "cargoservice");
    }

   
    public static final ProtocolType protocol = ProtocolType.tcp;
    public static final String HOST_ADDR = "host.docker.internal";
    public static final String PRODUCT_SERVICE_PORT = "8111";
    public static final String CARGO_SERVICE_PORT = "8110";
}
