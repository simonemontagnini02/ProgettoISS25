%====================================================================================
% sonarled description   
%====================================================================================
dispatch( sonardata, distance(D) ).
dispatch( containerAtIOPort, containerAtIOPort(X) ).
event( alarm, alarm(X) ).
event( endalarm, endalarm(X) ).
dispatch( ledOn, ledOn(X) ).
dispatch( ledOff, ledOff(X) ).
dispatch( sonar_normal, sonar_normal(X) ).
dispatch( sonar_failure, sonar_failure(X) ).
%====================================================================================
context(ctxiodevices, "localhost",  "TCP", "8180").
context(ctxcargoservice, "192.168.137.1",  "TCP", "8110").
context(ctxbasicrobot, "192.168.137.1",  "TCP", "8020").
 qactor( cargoservice, ctxcargoservice, "external").
  qactor( sonar, ctxiodevices, "it.unibo.sonar.Sonar").
 static(sonar).
  qactor( led, ctxiodevices, "it.unibo.led.Led").
 static(led).
  qactor( sonardevice, ctxiodevices, "it.unibo.sonardevice.Sonardevice").
 static(sonardevice).
