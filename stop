#!/bin/bash/

PROCCESS=$(jps -m | grep *.war)
WINPID=$( echo $PROCCESS | awk '{ print $1 }')
if [ -z  "$WINPID" ]
then
	echo "[WARNING]Not found! Application is already stopped"
else
	echo "[INFO]" found proccess $PROCCESS
	APPLICATION_NAME=$(echo $PROCCESS | awk '{ print $2 }' )
	echo "[INFO]" application name: $APPLICATION_NAME

	PID=$(ps -e | grep $WINPID | awk '{ print $1 }')
	echo "[INFO]" application pid: $PID
	kill $PID
	echo "[OK] $APPLICATION_NAME Application is stoped"
fi
