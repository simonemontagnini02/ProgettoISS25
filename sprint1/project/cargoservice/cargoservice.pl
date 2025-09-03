%====================================================================================
% cargoservice description   
%====================================================================================
request( createProduct, product(String) ).
reply( createdProduct, productid(ID) ).  %%for createProduct
request( load_request, load(PID) ).
reply( load_accepted, slot(SlotID) ).  %%for load_request
reply( load_refused, reason(Motivation) ).  %%for load_request
request( getProduct, product(PID) ).
reply( getProductAnswer, product(JSonString) ).  %%for getProduct
%====================================================================================
context(ctxproductservice, "localhost",  "TCP", "8111").
context(ctxcargoservice, "localhost",  "TCP", "8110").
 qactor( productservice, ctxproductservice, "it.unibo.productservice.Productservice").
 static(productservice).
  qactor( exec_createdelete, ctxproductservice, "it.unibo.exec_createdelete.Exec_createdelete").
 static(exec_createdelete).
  qactor( clientmock, ctxcargoservice, "it.unibo.clientmock.Clientmock").
 static(clientmock).
  qactor( cargoservice, ctxcargoservice, "it.unibo.cargoservice.Cargoservice").
 static(cargoservice).
  qactor( cargorobot, ctxcargoservice, "it.unibo.cargorobot.Cargorobot").
 static(cargorobot).
  qactor( webguimock, ctxcargoservice, "it.unibo.webguimock.Webguimock").
 static(webguimock).
