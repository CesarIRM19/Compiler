@Echo off
del %1.cm1
del %1.cm3
del .\Compiled_Programs\%1.java
del .\Compiled_Programs\%1.class

javac .\Source_files\AnalexProyecto.java
java Source_files.AnalexProyecto %1

if errorlevel 1 goto final

javac .\Source_files\SLR1.java
java Source_files.SLR1 %1

if errorlevel 1 goto final

javac Source_files/Ligador.java

java Source_files.Ligador %1


javac .\Compiled_Programs\%1.java

Echo Su programa esta listo, ejecutelo con: java Compiled_Programs.%1

del %1.cm1
del %1.cm3
:final
