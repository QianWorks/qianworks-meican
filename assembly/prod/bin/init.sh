#!/bin/bash

cd $(dirname $0)

typeset BIN_DIR DEPLOY_DIR CONF_DIR OUT_JARS_DIR 
typeset SERVER_NAME SERVER_PROTOCOL SERVER_PORT LOGS_FILE



export BIN_DIR=$(pwd)
export DEPLOY_DIR=$(dirname $BIN_DIR)
export CONF_DIR=$DEPLOY_DIR/conf
export OUT_JARS_DIR='/home/jars'

typeset APP_CONF_FILE=$CONF_DIR/app.properties

export SERVER_PROTOCOL=`sed '/application.protocol.name/!d;s/.*=//' $APP_CONF_FILE | tr -d '\r'`
export SERVER_PORT=`sed '/application.protocol.port/!d;s/.*=//' $APP_CONF_FILE | tr -d '\r'`
export LOGS_FILE=`sed '/application.log4j.file/!d;s/.*=//' $APP_CONF_FILE | tr -d '\r'`
export MAIN_CLASS=`sed '/application.main/!d;s/.*=//' $APP_CONF_FILE | tr -d '\r'`

SERVER_NAME=$(sed '/application.name/!d;s/.*=//' $APP_CONF_FILE| tr -d '\r')
if [ -z "$SERVER_NAME" ]; then
	SERVER_NAME=$(hostname)
fi
export SERVER_NAME=$SERVER_NAME
export OUT_PROJECT_CONFIG_DIR="/home/project/java_server_config/${DEPLOY_DIR##*/}/config"

