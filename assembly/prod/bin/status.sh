#!/bin/bash


cd $(dirname $0)

source ./init.sh

PIDS=$(ps -ef | grep java | grep "$CONF_DIR" | grep -v grep | awk '{print $2}')
if [ -z "$PIDS" ]; then
    echo "The $SERVER_NAME stopped!"
    exit 0
else
	echo "The $SERVER_NAME running!"
	exit 0
fi
