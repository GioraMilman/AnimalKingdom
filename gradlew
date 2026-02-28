#!/bin/sh

APP_HOME=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar

if [ -n "$JAVA_HOME" ] ; then
  JAVACMD="$JAVA_HOME/bin/java"
else
  JAVACMD=java
fi

if [ -f "$CLASSPATH" ]; then
  exec "$JAVACMD" ${JAVA_OPTS:-} ${GRADLE_OPTS:-} -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
fi

if command -v gradle >/dev/null 2>&1; then
  echo "gradle-wrapper.jar is missing; falling back to system Gradle." >&2
  exec gradle "$@"
fi

echo "ERROR: gradle-wrapper.jar is missing and no 'gradle' executable is available on PATH." >&2
echo "Run 'gradle wrapper' in a network-enabled environment to regenerate wrapper binaries." >&2
exit 1
