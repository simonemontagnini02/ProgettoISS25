%====================================================================================
% webgui description   
%====================================================================================
mqttBroker("localhost", "1883", "guiin").
dispatch( updategui, updategui(PID,SlotID,Weight) ).
event( guin, guin(JsonStr) ).
%====================================================================================
context(ctxwebgui, "localhost",  "TCP", "8169").
 qactor( webgui, ctxwebgui, "it.unibo.webgui.Webgui").
 static(webgui).
