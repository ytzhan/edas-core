@echo off
SETLOCAL
set JAVA_HOME=C:\tools\Java\jdk1.8.0_74
set PATH="%JAVA_HOME%\bin"
set CLASSPATH=".;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar;"
echo "PATH:%PATH%"
echo "CLASSPATH:%CLASSPATH%"
jjs -scripting r.js -- -o build.js
ENDLOCAL
pause

