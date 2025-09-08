%====================================================================================
% cargoservice description   
%====================================================================================
mqttBroker("localhost", "1883", "sonar").
request( createProduct, product(String) ).
reply( createdProduct, productid(ID) ).  %%for createProduct
request( load_request, load(PID) ).
reply( load_accepted, slot(SlotID) ).  %%for load_request
reply( load_refused, reason(Motivation) ).  %%for load_request
request( getProduct, product(PID) ).
reply( getProductAnswer, product(JSonString) ).  %%for getProduct
event( sonardata, distance(D) ).
event( alarm, alarm(X) ).
dispatch( alarm, alarm(X) ).
dispatch( accepted, accepted(X) ).
dispatch( refused, refused(X) ).
dispatch( container_ioport, container_ioport(X) ).
dispatch( sonar_failure, sonar_failure(X) ).
dispatch( sonar_normal, sonar_normal(X) ).
dispatch( transport, transport(SlotID) ).
dispatch( robot_home, robot_home(X) ).
%====================================================================================
context(ctxproductservice, "127.0.0.1",  "TCP", "8111").
context(ctxcargoservice, "localhost",  "TCP", "8110").
context(ctxiodevices, "localhost",  "TCP", "8128").
 qactor( productservice, ctxproductservice, "it.unibo.productservice.Productservice").
 static(productservice).
  qactor( clientmock, ctxcargoservice, "it.unibo.clientmock.Clientmock").
 static(clientmock).
  qactor( cargoservice, ctxcargoservice, "it.unibo.cargoservice.Cargoservice").
 static(cargoservice).
  qactor( sonarcontroller, ctxcargoservice, "it.unibo.sonarcontroller.Sonarcontroller").
 static(sonarcontroller).
  qactor( cargorobot, ctxcargoservice, "it.unibo.cargorobot.Cargorobot").
 static(cargorobot).
  qactor( webguimock, ctxcargoservice, "it.unibo.webguimock.Webguimock").
 static(webguimock).
  qactor( sonarmock, ctxcargoservice, "it.unibo.sonarmock.Sonarmock").
 static(sonarmock).
