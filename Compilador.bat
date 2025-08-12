@Echo off
del %1.cm1
del %1.cm3
del %1.java

javac AnalexProyecto.java
java AnalexProyecto %1

if errorlevel 1 goto final

javac SLR1.java
java SLR1 %1

if errorlevel 1 goto final

javac Ligador.java
java Ligador %1

javac %1.java
Echo Su programa esta listo, ejecutelo con: java %1

:final
rem del %1.cm1
rem del %1.cm3