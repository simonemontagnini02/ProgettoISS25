%====================================================================================
% cargoservice description   
%====================================================================================
request( load_request, load(PID) ).
reply( load_accepted, slot(SlotID) ).  %%for load_request
reply( load_refused, reason(Motivation) ).  %%for load_request
%====================================================================================
context(ctx_cargoservice, "localhost",  "TCP", "8110").
 qactor( cargoservice, ctx_cargoservice, "it.unibo.cargoservice.Cargoservice").
 static(cargoservice).
  qactor( cargorobot, ctx_cargoservice, "it.unibo.cargorobot.Cargorobot").
 static(cargorobot).
  qactor( webgui_mock, ctx_cargoservice, "it.unibo.webgui_mock.Webgui_mock").
 static(webgui_mock).
