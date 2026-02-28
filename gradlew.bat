@ECHO OFF
SET DIRNAME=%~dp0
IF "%DIRNAME%"=="" SET DIRNAME=.
SET APP_HOME=%DIRNAME%
SET CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar

IF EXIST "%CLASSPATH%" GOTO RUN_WRAPPER

WHERE gradle >NUL 2>NUL
IF %ERRORLEVEL% EQU 0 (
  ECHO gradle-wrapper.jar is missing; falling back to system Gradle.
  gradle %*
  EXIT /B %ERRORLEVEL%
)

ECHO ERROR: gradle-wrapper.jar is missing and no 'gradle' executable is available on PATH.
ECHO Run 'gradle wrapper' in a network-enabled environment to regenerate wrapper binaries.
EXIT /B 1

:RUN_WRAPPER
IF DEFINED JAVA_HOME (
  SET JAVA_EXE=%JAVA_HOME%\bin\java.exe
) ELSE (
  SET JAVA_EXE=java.exe
)

"%JAVA_EXE%" %JAVA_OPTS% %GRADLE_OPTS% -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*
