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
request( move, move(X,Y) ).
reply( movedone, movedone(X) ).  %%for move
reply( movefailed, movefailed(X) ).  %%for move
request( engage, engage(OWNER,STEPTIME) ).
reply( engagedone, engagedone(ARG) ).  %%for engage
reply( engagerefused, engagerefused(ARG) ).  %%for engage
request( moverobot, moverobot(TARGETX,TARGETY) ).
reply( moverobotdone, moverobotok(ARG) ).  %%for moverobot
reply( moverobotfailed, moverobotfailed(PLANDONE,PLANTODO) ).  %%for moverobot
%====================================================================================
context(ctxcargoservice, "localhost",  "TCP", "8110").
context(ctxiodevices, "localhost",  "TCP", "8128").
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
context(ctxproductservice, "127.0.0.1",  "TCP", "8111").
 qactor( productservice, ctxproductservice, "external").
  qactor( basicrobot, ctxbasicrobot, "external").
  qactor( clientmock, ctxcargoservice, "it.unibo.clientmock.Clientmock").
 static(clientmock).
  qactor( cargoservice, ctxcargoservice, "it.unibo.cargoservice.Cargoservice").
 static(cargoservice).
  qactor( requestvalidator, ctxcargoservice, "it.unibo.requestvalidator.Requestvalidator").
 static(requestvalidator).
  qactor( cargorobot, ctxcargoservice, "it.unibo.cargorobot.Cargorobot").
 static(cargorobot).
  qactor( moveexec, ctxcargoservice, "it.unibo.moveexec.Moveexec").
 static(moveexec).
  qactor( sonarmock, ctxcargoservice, "it.unibo.sonarmock.Sonarmock").
 static(sonarmock).
