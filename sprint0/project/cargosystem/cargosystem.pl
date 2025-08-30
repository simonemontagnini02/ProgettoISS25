%====================================================================================
% cargosystem description   
%====================================================================================
request( load_request, load(PID) ).
reply( load_accepted, slot(SlotID) ).  %%for load_request
reply( load_refused, reason(Motivation) ).  %%for load_request
%====================================================================================
context(ctx_cargoservice_clients, "localhost",  "TCP", "8111").
context(ctx_cargoservice, "localhost",  "TCP", "8110").
context(ctx_productservice, "localhost",  "TCP", "8113").
context(ctx_basicrobot, "localhost",  "TCP", "8114").
context(ctx_iodevices, "localhost",  "TCP", "8112").
 qactor( client, ctx_cargoservice_clients, "it.unibo.client.Client").
 static(client).
  qactor( cargoservice, ctx_cargoservice, "it.unibo.cargoservice.Cargoservice").
 static(cargoservice).
  qactor( cargorobot, ctx_cargoservice, "it.unibo.cargorobot.Cargorobot").
 static(cargorobot).
  qactor( webgui_mock, ctx_cargoservice, "it.unibo.webgui_mock.Webgui_mock").
 static(webgui_mock).
  qactor( productservice, ctx_productservice, "it.unibo.productservice.Productservice").
 static(productservice).
  qactor( basicrobot, ctx_basicrobot, "it.unibo.basicrobot.Basicrobot").
 static(basicrobot).
  qactor( sonar, ctx_iodevices, "it.unibo.sonar.Sonar").
 static(sonar).
  qactor( led, ctx_iodevices, "it.unibo.led.Led").
 static(led).
