#!/bin/bash

HOME=/home/ubuntu
LOG_DIR=${HOME}/logs
SCOUTER_AGENT_DIR=${HOME}/scouter/agent.java

# check parameters and initialize JAR_FILE_PATH
if [ "$#" -ne 1 ]; then
        echo "usage: startup.sh [jar file path to execute. (default: /home/ubuntu/team5-api.jar)]"
        JAR_FILE_PATH=${HOME}/team5-api.jar
else
        JAR_FILE_PATH=${1}
fi


# move
cd ${HOME}

# SCOUTER
JAVA_OPTS="${JAVA_OPTS} -javaagent:${SCOUTER_AGENT_DIR}/scouter.agent.jar"

# REMOTE DEBUGGING
JAVA_OPTS="${JAVA_OPTS} -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=0.0.0.0:5005"

# SPRING
JAVA_OPTS="${JAVA_OPTS} -Dspring.profiles.active=prod"
JAVA_OPTS="${JAVA_OPTS} -Dlogging.path=${LOG_DIR}"

# GC
JAVA_OPTS="${JAVA_OPTS} -verbose:gc"
JAVA_OPTS="${JAVA_OPTS} -Xloggc:${LOG_DIR}/gc_%p.log"
JAVA_OPTS="${JAVA_OPTS} -XX:+HeapDumpOnOutOfMemoryError"
JAVA_OPTS="${JAVA_OPTS} -XX:+PrintGCDetails"
JAVA_OPTS="${JAVA_OPTS} -XX:+PrintGCDateStamps"

# HEAP
JAVA_OPTS="${JAVA_OPTS} -Xmx512m"

# start
nohup java ${JAVA_OPTS} -jar ${JAR_FILE_PATH} &

