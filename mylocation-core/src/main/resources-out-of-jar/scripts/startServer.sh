#!/bin/bash 

set -e 

BASE_DIR=`dirname "$0"`
CLASS_NAME="net.devaction.mylocationcore.main.MyLocationCoreMain"
CLASSPATH="${BASE_DIR}/../lib/*:${BASE_DIR}/../conf:${BASE_DIR}/.."

#java -cp $CLASSPATH $CLASS_NAME & 
java -cp $CLASSPATH $CLASS_NAME 
#PID=$!

#wait for the process to come up
#sleep 3

#check if the process is running
#kill -s NULL $PID 
 
#echo "`date` - INFO: application successfully started."

 