%====================================================================================
% cargoservice description   
%====================================================================================
mqttBroker("localhost", "1883", "sonar").
request( createProduct, product(String) ).
reply( createdProduct, productid(ID) ).  %%for createProduct
request( load_request, load(PID) ).
reply( load_accepted, slot(SlotID) ).  %%for load_request
reply( load_refused, reason(Motivation) ).  %%for load_request
request( validate_request, validate(PID,Weight) ).
reply( validate_accepted, slot(SlotID) ).  %%for validate_request
reply( validate_refused, motivation(X) ).  %%for validate_request
request( getProduct, product(PID) ).
reply( getProductAnswer, product(JSonString) ).  %%for getProduct
event( sonardata, distance(D) ).
event( alarm, alarm(X) ).
dispatch( refused, refused(X) ).
dispatch( container_ioport, container_ioport(X) ).
dispatch( sonar_failure, sonar_failure(X) ).
dispatch( sonar_normal, sonar_normal(X) ).
request( transport, transport(SlotID) ).
reply( robot_home, robot_home(X) ).  %%for transport
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
  qactor( requestvalidator, ctxcargoservice, "it.unibo.requestvalidator.Requestvalidator").
 static(requestvalidator).
  qactor( sonarcontroller, ctxcargoservice, "it.unibo.sonarcontroller.Sonarcontroller").
 static(sonarcontroller).
  qactor( cargorobot, ctxcargoservice, "it.unibo.cargorobot.Cargorobot").
 static(cargorobot).
  qactor( webguimock, ctxcargoservice, "it.unibo.webguimock.Webguimock").
 static(webguimock).
  qactor( sonarmock, ctxcargoservice, "it.unibo.sonarmock.Sonarmock").
 static(sonarmock).
