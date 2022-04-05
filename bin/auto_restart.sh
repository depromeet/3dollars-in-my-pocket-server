#!/bin/bash

SCRIPTS_DIR=/home/ubuntu/scripts
MAINTENANCE_FILE_PATH=${SCRIPTS_DIR}/maintenance

# check if maintenance file exists
if [ -f "${MAINTENANCE_FILE_PATH}"]; then
        exit 0
fi

# check application is running
NUMBER_OF_PROCESSES=`ps -ef | grep 'team5-api.jar' | grep -v grep | wc -l`
if [ "${NUMBER_OF_PROCESSES}" == "1" ]; then
        exit 0
fi

# restart application
echo "Start team5-api application."
${SCRIPTS_DIR}/startup.sh

