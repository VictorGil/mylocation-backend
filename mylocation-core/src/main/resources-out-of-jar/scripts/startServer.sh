#!/bin/bash 

set -e 

BASE_DIR=`dirname "$0"`
CLASS_NAME="net.devaction.mylocation.vertxutilityextensions.main.VertxStarter"
CLASSPATH="${BASE_DIR}/../conf:${BASE_DIR}/../conf/lcl:${BASE_DIR}/..:${BASE_DIR}/../lib/*"

JAVA_SYSTEM_PROPERTIES="-Dhazelcast.logging.type=slf4j -Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory -Djava.net.preferIPv4Stack=true -Dvertx.cluster.host=localhost -Dmylocation.service.id=core"

java $JAVA_SYSTEM_PROPERTIES -classpath $CLASSPATH $CLASS_NAME 

#PID=$!

#wait for the process to come up
#sleep 5

#check if the process is running
#kill -s NULL $PID 
 
#echo "`date` - INFO: application successfully started."

 