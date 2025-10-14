//package test.java;
//
//
//
//
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.junit.jupiter.api.TestInstance.Lifecycle;
//
//import unibo.basicomm23.interfaces.IApplMessage;
//import unibo.basicomm23.interfaces.Interaction;
//import unibo.basicomm23.msg.ProtocolType;
//import unibo.basicomm23.utils.CommUtils;
//import unibo.basicomm23.utils.ConnectionFactory;
//
//@TestInstance(Lifecycle.PER_CLASS)
//public class Test1 {
//
//	 String hostAddr       = "127.0.0.1"; 
//     int port              = 8111;
//     ProtocolType protocol = ProtocolType.tcp;
//    
//    private  String PROD = "{'\"productId\":31,\"name\":\"p31\",\"weight\":311'}";
//    private  IApplMessage createProduct= CommUtils.buildRequest("TestNotRegistered", "createProduct", "product("+PROD+")", "productservice"); 
//    
//	private  Interaction conn;
//	
//	
//	@BeforeAll
//	public void createConnection(){
//		
//		IApplMessage risposta;
//		conn=ConnectionFactory.createClientSupport(protocol, hostAddr, ""+port);
//		try {
//			risposta=conn.request(createProduct);
//			
//			System.out.println("Start");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	}
//	
//	@Test
//	public void test() {
//		//fail("Not yet implemented");
//	}
//
//}
