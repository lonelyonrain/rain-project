@echo off
title RentDataReportServer
SETLOCAL ENABLEDELAYEDEXPANSION
set CLASSPATH=.\classes;.\config;%CLASSPATH%
FOR %%C IN (.\*.jar) DO set CLASSPATH=!CLASSPATH!;%%C
FOR %%C IN (.\lib\*.jar) DO set CLASSPATH=!CLASSPATH!;%%C
echo %CLASSPATH%
java -Xmx1024m com.xmgps.taxi.datareportserver.server.App
