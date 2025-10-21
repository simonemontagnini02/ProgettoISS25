%====================================================================================
% cargoservice description   
%====================================================================================
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
dispatch( containerAtIOPort, containerAtIOPort(X) ).
event( alarm, alarm(X) ).
dispatch( refused, refused(X) ).
request( transport, transport(SlotID) ).
reply( robot_home, robot_home(X) ).  %%for transport
dispatch( delete, delete(x) ).
%====================================================================================
context(ctxproductservice, "127.0.0.1",  "TCP", "8111").
context(ctxcargoservice, "localhost",  "TCP", "8110").
context(ctxiodevices, "localhost",  "TCP", "8128").
 qactor( productservice, ctxproductservice, "it.unibo.productservice.Productservice").
 static(productservice).
  qactor( cargoservice, ctxcargoservice, "it.unibo.cargoservice.Cargoservice").
 static(cargoservice).
  qactor( requestvalidator, ctxcargoservice, "it.unibo.requestvalidator.Requestvalidator").
 static(requestvalidator).
  qactor( cargorobot, ctxcargoservice, "it.unibo.cargorobot.Cargorobot").
 static(cargorobot).
  qactor( deletetester, ctxcargoservice, "it.unibo.deletetester.Deletetester").
 static(deletetester).
