%====================================================================================
% cargosystem description   
%====================================================================================
request( load_request, load(PID) ).
reply( load_accepted, slot(SlotID) ).  %%for load_request
reply( load_refused, reason(String) ).  %%for load_request
%====================================================================================
context(ctx_cargoservice, "localhost",  "TCP", "8110").
context(ctx_cargoservice_clients, "localhost",  "TCP", "8111").
context(ctx_iodevices, "localhost",  "TCP", "8112").
 qactor( client, ctx_cargoservice_clients, "it.unibo.client.Client").
 static(client).
  qactor( cargoservice, ctx_cargoservice, "it.unibo.cargoservice.Cargoservice").
 static(cargoservice).
  qactor( productservice, ctx_cargoservice, "it.unibo.productservice.Productservice").
 static(productservice).
  qactor( hold, ctx_cargoservice, "it.unibo.hold.Hold").
 static(hold).
  qactor( basicrobot, ctx_cargoservice, "it.unibo.basicrobot.Basicrobot").
 static(basicrobot).
  qactor( cargorobot, ctx_cargoservice, "it.unibo.cargorobot.Cargorobot").
 static(cargorobot).
  qactor( webgui, ctx_cargoservice, "it.unibo.webgui.Webgui").
 static(webgui).
  qactor( sonar, ctx_iodevices, "it.unibo.sonar.Sonar").
 static(sonar).
  qactor( led, ctx_iodevices, "it.unibo.led.Led").
 static(led).
