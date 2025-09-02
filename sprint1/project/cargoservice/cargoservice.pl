%====================================================================================
% cargoservice description   
%====================================================================================
request( load_request, load(PID) ).
reply( load_accepted, slot(SlotID) ).  %%for load_request
reply( load_refused, reason(Motivation) ).  %%for load_request
request( getProduct, product(PID) ).
reply( getProductAnswer, product(JSonString) ).  %%for getProduct
%====================================================================================
context(ctxproductservice, "localhost",  "TCP", "8111").
context(ctxcargoservice, "localhost",  "TCP", "8110").
 qactor( cargoservice, ctxcargoservice, "it.unibo.cargoservice.Cargoservice").
 static(cargoservice).
  qactor( cargorobot, ctxcargoservice, "it.unibo.cargorobot.Cargorobot").
 static(cargorobot).
  qactor( webgui_mock, ctxcargoservice, "it.unibo.webgui_mock.Webgui_mock").
 static(webgui_mock).
  qactor( productservice, ctxproductservice, "it.unibo.productservice.Productservice").
 static(productservice).
