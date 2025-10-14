%====================================================================================
% cargosystem description   
%====================================================================================
request( load_request, load(PID) ).
reply( load_accepted, slot(SlotID) ).  %%for load_request
reply( load_refused, reason(Motivation) ).  %%for load_request
%====================================================================================
context(ctxcargoserviceclients, "localhost",  "TCP", "8111").
context(ctxcargoservice, "localhost",  "TCP", "8110").
context(ctxproductservice, "localhost",  "TCP", "8113").
context(ctxbasicrobot, "localhost",  "TCP", "8114").
context(ctxiodevices, "localhost",  "TCP", "8112").
 qactor( client, ctxcargoserviceclients, "it.unibo.client.Client").
 static(client).
  qactor( cargoservice, ctxcargoservice, "it.unibo.cargoservice.Cargoservice").
 static(cargoservice).
  qactor( cargorobot, ctxcargoservice, "it.unibo.cargorobot.Cargorobot").
 static(cargorobot).
  qactor( webgui_mock, ctxcargoservice, "it.unibo.webgui_mock.Webgui_mock").
 static(webgui_mock).
  qactor( productservice, ctxproductservice, "it.unibo.productservice.Productservice").
 static(productservice).
  qactor( basicrobot, ctxbasicrobot, "it.unibo.basicrobot.Basicrobot").
 static(basicrobot).
  qactor( sonar, ctxiodevices, "it.unibo.sonar.Sonar").
 static(sonar).
  qactor( led, ctxiodevices, "it.unibo.led.Led").
 static(led).
