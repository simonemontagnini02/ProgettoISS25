//package test.java;
//
//import static org.junit.Assert.*;
//
//import javax.naming.CommunicationException;
//
//import org.bson.Document;
//import org.junit.*;
//import org.junit.runner.*;
//import org.junit.runners.*;
//
//import com.mongodb.client.*;
//
//import main.java.domain.Hold;
//import main.java.domain.Slot;
//import unibo.basicomm23.interfaces.IApplMessage;
//import unibo.basicomm23.interfaces.Interaction;
//import unibo.basicomm23.msg.ProtocolType;
//import unibo.basicomm23.utils.CommUtils;
//import unibo.basicomm23.utils.ConnectionFactory;
//
//
//public class Tester {
//	
//
//	private static String hostAddr = "localhost";
//	private static ProtocolType protocol = ProtocolType.tcp;
//	
//	static String mongourl = "mongodb://localhost:27017"; 
//
//	static MongoClient mongoClient;
//	static MongoDatabase database;
//	static MongoCollection<Document> products ;
//	static MongoCollection<Document> slots ;
//	
//	static Hold hold;
//
//	
//
//	private static String tester = "tester";
//
//	private static String PROD = "{'\"productId\":31,\"name\":\"p31\",\"weight\":311'}";
//	private static String PROD2 = "{'\"productId\":32,\"name\":\"p32\",\"weight\":311'}";
//	private static String PROD3 = "{'\"productId\":33,\"name\":\"p33\",\"weight\":1'}";
//	private static String PROD4 = "{'\"productId\":34,\"name\":\"p34\",\"weight\":1'}";
//	private static String PROD5 = "{'\"productId\":35,\"name\":\"p35\",\"weight\":1'}";
//	private static String PROD6 = "{'\"productId\":36,\"name\":\"p36\",\"weight\":1'}";
//	
//
//	private static IApplMessage createProduct = CommUtils.buildRequest(tester, "createProduct", "product(" + PROD + ")","productservice");
//	private static IApplMessage createProduct2 = CommUtils.buildRequest(tester, "createProduct", "product(" + PROD2 + ")","productservice");
//	private static IApplMessage createProduct3 = CommUtils.buildRequest(tester, "createProduct", "product(" + PROD3 + ")","productservice");
//	private static IApplMessage createProduct4 = CommUtils.buildRequest(tester, "createProduct", "product(" + PROD4 + ")","productservice");
//	private static IApplMessage createProduct5 = CommUtils.buildRequest(tester, "createProduct", "product(" + PROD5 + ")","productservice");
//	private static IApplMessage createProduct6 = CommUtils.buildRequest(tester, "createProduct", "product(" + PROD6 + ")","productservice");
//	
//	private static IApplMessage Wrong_load_request = CommUtils.buildRequest(tester, "load_request", "load(" + 90 + ")","cargoservice");
//	private static IApplMessage Correct_load_request = CommUtils.buildRequest(tester, "load_request","load(" + 31 + ")", "cargoservice");
//	private static IApplMessage Correct_load_request2 = CommUtils.buildRequest(tester, "load_request","load(" + 32 + ")", "cargoservice");
//	private static IApplMessage Correct_load_request3 = CommUtils.buildRequest(tester, "load_request","load(" + 33 + ")", "cargoservice");
//	private static IApplMessage Correct_load_request4 = CommUtils.buildRequest(tester, "load_request","load(" + 34 + ")", "cargoservice");
//	private static IApplMessage Correct_load_request5 = CommUtils.buildRequest(tester, "load_request","load(" + 35 + ")", "cargoservice");
//	private static IApplMessage Correct_load_request6 = CommUtils.buildRequest(tester, "load_request","load(" + 36 + ")", "cargoservice");
//	
//	private static IApplMessage deleteDispatch= CommUtils.buildDispatch(tester, "delete", "delete(ok)", "deletetester");
//	
//	private static IApplMessage Container_at_IOPort=CommUtils.buildDispatch(tester, "containerAtIOPort", "containerAtIOPort(ok)", "cargoservice");
//	
//
//	private static Interaction ProductServiceConn, CargoserviceConn;
//	private static IApplMessage risposta;
//
//	@BeforeClass
//	public static void setup() {
//		
//		mongoClient = MongoClients.create(mongourl);
//		database = mongoClient.getDatabase("cargodb");
//		
//
//		products = database.getCollection("products");
//		slots = database.getCollection("slots");
//
//		products.deleteMany(new Document());
//		slots.deleteMany(new Document());
//		
//		
//		
//		try {
//			ProductServiceConn = ConnectionFactory.createClientSupport23(protocol, hostAddr, "8111");
//			CargoserviceConn = ConnectionFactory.createClientSupport23(protocol, hostAddr, "8110");
//			
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		try {
//			//CargoserviceConn.forward(deleteDispatch);
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//		}
//	}
//
//	
//	@Test
//	public void T01_LoadRequestCorrect() {
//		try {
//			ProductServiceConn.request(createProduct);
//			risposta = CargoserviceConn.request(Correct_load_request);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		System.out.println("\n\n\n"+risposta+"\n\n\n");
//		assertEquals("load_accepted", risposta.msgId());
//		assertEquals("slot(0)", risposta.msgContent());
//		
//		hold=Hold.getInstance();
//		
//		Slot[] slots = hold.getSlots();
//		
//		System.out.println(hold.getTotalWeight());
//		
//		Slot s = slots[0];
//		assertEquals(31, s.getProductId());
//		
//		try {
//			CargoserviceConn.forward(Container_at_IOPort);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//
//	}
//
////	@Test
////	public void T02_LoadRequestPidNotRegistered() {
////		try {
////			risposta = CargoserviceConn.request(Wrong_load_request);
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
////
////		assertEquals("load_refused", risposta.msgId());
////		assertEquals("reason(PID_NOT_REGISTERED)", risposta.msgContent());
////
////	}
////	
////	
////	
////	@Test
////	public void T03_NoSlotAvailable() {
////		
////		try {
////			 ProductServiceConn.request(createProduct);
////			 ProductServiceConn.request(createProduct3);
////			 ProductServiceConn.request(createProduct4);
////			 ProductServiceConn.request(createProduct5);
////			 ProductServiceConn.request(createProduct6);
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
////		
////		
////		try {
////			CargoserviceConn.request(Correct_load_request);
////			CargoserviceConn.forward(Container_at_IOPort);
////			
////			CargoserviceConn.request(Correct_load_request3);
////			CargoserviceConn.forward(Container_at_IOPort);
////			
////			CargoserviceConn.request(Correct_load_request4);
////			CargoserviceConn.forward(Container_at_IOPort);
////			
////			CargoserviceConn.request(Correct_load_request5);
////			CargoserviceConn.forward(Container_at_IOPort);
////			
////			risposta = CargoserviceConn.request(Correct_load_request6);
////			
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
////
////		assertEquals("load_refused", risposta.msgId());
////		assertEquals("reason(NO_FREE_SLOTS)", risposta.msgContent());
////		
////	}
////	
////	
////	@Test
////	public void T04_MaxWeight() {
////		try {
////			ProductServiceConn.request(createProduct);
////			CargoserviceConn.request(Correct_load_request);
////			CargoserviceConn.forward(Container_at_IOPort);
////			
////			ProductServiceConn.request(createProduct2);
////			risposta = CargoserviceConn.request(Correct_load_request2);
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
////
////		assertEquals("load_refused", risposta.msgId());
////		assertEquals("reason(MAX_LOAD_EXCEEDED)", risposta.msgContent());
////
////	}
////	
////	@After
////	public void cleanDB() {
////	
////		
////		products.deleteMany(new Document());
////		slots.deleteMany(new Document());
////		try {
////			CargoserviceConn.forward(deleteDispatch);
////		} catch (Exception e) {
////			
////			e.printStackTrace();
////		}
////		
////	}
//	
//	@AfterClass
//	public static void close() {
//		
//		try {
//			ProductServiceConn.close();
//			CargoserviceConn.close();
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//		}
//	
//	}
//
//}
