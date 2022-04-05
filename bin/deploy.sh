#!/bin/bash

SCRIPTS_DIR=/home/ubuntu/scripts
MAINTENANCE_FILE_PATH=${SCRIPTS_DIR}/maintenance

# create maintenance file
if [ ! -f "${MAINTENANCE_FILE_PATH}" ]; then
        touch ${MAINTENANCE_FILE_PATH}
fi

# set jar file path
JAR_FILE_PATH=${1}

# shutdown
${SCRIPTS_DIR}/shutdown.sh

# rename
if [ -f "${JAR_FILE_PATH}" ]; then
        echo "change target path of team5-api.jar"
        ln -sf ${JAR_FILE_PATH} /home/ubuntu/team5-api.jar
fi

# startup
${SCRIPTS_DIR}/startup.sh &

# wait for starting application
WAITING_SECONDS=60
echo "waiting ${WAITING_SECONDS}s for starting application."
sleep ${WAITING_SECONDS}

# check health (TODO: spring boot actuator /health)
NUMBER_OF_PROCESSES=`ps -ef | grep team5-api | grep -v grep | wc -l`
if [ "${NUMBER_OF_PROCESSES}" -ne 1 ]; then
        echo "Failed to deploy application. 'team5-api' process is not found."
fi

# delete maintenance file
if [ -f "${MAINTENANCE_FILE_PATH}" ]; then
        rm ${MAINTENANCE_FILE_PATH}
fi
