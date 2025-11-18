package test.java;

import static org.junit.Assert.*;

import org.bson.Document;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import main.java.domain.Hold;
import unibo.basicomm23.coap.CoapConnection;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

public class TesterRobotCoap {
	
	
	private static String hostAddr = "localhost";
	private static ProtocolType protocol = ProtocolType.tcp;
	
	static String mongourl = "mongodb://localhost:27017"; 

	static MongoClient mongoClient;
	static MongoDatabase database;
	static MongoCollection<Document> products ;
	static MongoCollection<Document> slots ;
	static Hold hold;
	
	private static String tester = "testercoap";
	
	
	private static IApplMessage deleteDispatch= CommUtils.buildDispatch(tester, "delete", "delete(ok)", "deletetester");


	private static String PROD = "{'\"productId\":31,\"name\":\"p31\",\"weight\":311'}";
	
	
	private static IApplMessage createProduct = CommUtils.buildRequest(tester, "createProduct", "product(" + PROD + ")","productservice");
	private static IApplMessage Correct_load_request = CommUtils.buildRequest(tester, "load_request","load(" + 31 + ")", "cargoservice");
	private static IApplMessage sonarFailure = CommUtils.buildEvent(tester, "alarm", "alarm(failure)");
	private static IApplMessage sonarFailureEnd = CommUtils.buildEvent(tester, "endalarm", "endalarm(ok)");
	
	
	private static IApplMessage Container_at_IOPort=CommUtils.buildDispatch(tester, "containerAtIOPort", "containerAtIOPort(ok)", "cargoservice");
	
	private static Interaction ProductServiceConn, CargoserviceConn, PlanexecConn, BasicrobotConn;

	static volatile String risposta = null;
	static volatile String rispostaBasicrobot=null;
	
	static CoapConnection coapConn,coapConnBasicrobot;
	static CoapClient client,clientBasicrobot;
	static CoapObserveRelation relation,relationBasicrobot;
	
	@BeforeClass
	public static void setup() {
		
		mongoClient = MongoClients.create(mongourl);
		database = mongoClient.getDatabase("cargodb");
		

		products = database.getCollection("products");
		slots = database.getCollection("slots"); 

		products.deleteMany(new Document());
		slots.deleteMany(new Document());
		
		
		
		try {
			ProductServiceConn = ConnectionFactory.createClientSupport23(protocol, hostAddr, "8111");
			CargoserviceConn = ConnectionFactory.createClientSupport23(protocol, hostAddr, "8110");
			PlanexecConn = ConnectionFactory.createClientSupport23(ProtocolType.coap, hostAddr+":"+"8020", "ctxbasicrobot/planexec");
			BasicrobotConn = ConnectionFactory.createClientSupport23(ProtocolType.coap, hostAddr+":"+"8020", "ctxbasicrobot/basicrobot");
			
			
			coapConn = (CoapConnection)PlanexecConn;
			client = coapConn.getClient();
			
			coapConnBasicrobot = (CoapConnection)BasicrobotConn;
			clientBasicrobot = coapConnBasicrobot.getClient();
			
			relation = client.observe(
					new CoapHandler() {
						@Override public void onLoad(CoapResponse response) {
							String msg = response.getResponseText();
							 if(msg.contains("planfailed")) {  
									risposta = msg;
									CommUtils.outgreen("PlanexecObserver | " + risposta );
								} else {
									CommUtils.outblue("PlanexecObserver | ignorato -> " + msg );
								}
							
							
						}					
						@Override public void onError() {
							CommUtils.outred("OBSERVING FAILED  ");
						}
					});	
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		try {
			CargoserviceConn.forward(deleteDispatch);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void sonarFailureAndsonarRestoration() {
		
		try {
			ProductServiceConn.request(createProduct);
			CargoserviceConn.request(Correct_load_request);
			CargoserviceConn.forward(Container_at_IOPort);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		CommUtils.delay(9000);
		
		
		
		
		try {
			CargoserviceConn.forward(sonarFailure);
			PlanexecConn.forward(sonarFailure);
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		
		 int attempts = 0;
		
		while(risposta == null && attempts < 1000) {
	        CommUtils.delay(200);
	        attempts++;
	    }
		
		assertNotNull("Non è arrivato nessun update CoAP", risposta);
	    assertTrue(risposta.contains("planfailed"));
	    
	    
	    CommUtils.delay(2000);
	    
	    
	    relationBasicrobot = clientBasicrobot.observe(
				new CoapHandler() {
					@Override public void onLoad(CoapResponse response) {
						String msg = response.getResponseText();
						 if(msg.contains("stepDone")) {  
								rispostaBasicrobot = msg;
								CommUtils.outmagenta("BasicrobotObserver | " + risposta );
							} else {
								CommUtils.outblue("BasicrobotObserver | ignorato -> " + msg );
							}
						
						
					}					
					@Override public void onError() {
						CommUtils.outred("OBSERVING FAILED  ");
					}
				});	
	    
	    
	    try {
			CargoserviceConn.forward(sonarFailureEnd);
			PlanexecConn.forward(sonarFailureEnd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    while(rispostaBasicrobot == null && attempts < 1000) {
	        CommUtils.delay(200);
	        attempts++;
	    }
	    
	    assertNotNull("Non è arrivato nessun update CoAP", rispostaBasicrobot);
	    assertTrue(rispostaBasicrobot.contains("stepDone"));
	    
		
	}
	
	@After
	public void cleanDB() {
	
		
		products.deleteMany(new Document());
		slots.deleteMany(new Document());
		try {
			CargoserviceConn.forward(deleteDispatch);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	@AfterClass
	public static void close() {
		
		try {
			ProductServiceConn.close();
			CargoserviceConn.close();
			PlanexecConn.close();
			relation.proactiveCancel();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	
	}

}
