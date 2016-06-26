#!/bin/bash

if [ $(id -u) -ne 0 ];then  
    echo "only root can execute" 
	exit 1 
fi

#typeset MAIN_CLASS

cd $(dirname $0)

source ./init.sh

#MAIN_CLASS='com.fenqile.outbiz.pay19.server.Main'


# check pids
function check_pids()
{
    typeset PIDS
    PIDS=$(ps -ef | grep java | grep "$CONF_DIR" |grep -v grep |awk '{print $2}')
    if [ -n "$PIDS" ]; then
        echo "ERROR: The $SERVER_NAME already started!"
        echo "PID: $PIDS"
        exit 1
    fi
}


# check server port
function check_port()
{
    typeset SERVER_PORT_COUNT
    if [ -n "$SERVER_PORT" ]; then
        SERVER_PORT_COUNT=$(netstat -tln | grep $SERVER_PORT |grep -v grep | wc -l)
        if [ $SERVER_PORT_COUNT -gt 0 ]; then
            echo "ERROR: The $SERVER_NAME port $SERVER_PORT already used!"
            exit 1
        fi
    fi
}

typeset STDOUT_FILE
# check logs dir
function check_stdout_file()
{
    typeset LOGS_DIR
    #LOGS_DIR=""
    if [ -n "$LOGS_FILE" ]; then
        LOGS_DIR=$(dirname $LOGS_FILE)
    else
        LOGS_DIR=$DEPLOY_DIR/logs
    fi
    if [ ! -d $LOGS_DIR ]; then
        mkdir $LOGS_DIR
    fi
    STDOUT_FILE=$LOGS_DIR/stdout.log
}


function start_server()
{

    typeset LIB_DIR LIB_JARS JAVA_OPTS JAVA_DEBUG_OPTS JAVA_JMX_OPTS JAVA_MEM_OPTS BITS

    # set lib
    LIB_DIR=$DEPLOY_DIR/lib
    LIB_JARS=$(ls $LIB_DIR | grep .jar | grep -v grep | awk '{print "'$LIB_DIR'/"$0}' | tr "\n" ":")

    #set out jars dir
    if [ -d $OUT_JARS_DIR ];then
		OUT_JARS_DIR=$(ls $OUT_JARS_DIR | grep .jar| grep -v grep | awk '{print "'$OUT_JARS_DIR'/"$0}' | tr "\n" ":")
	fi	

    #set java options
    JAVA_OPTS=" -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true "
    JAVA_DEBUG_OPTS=""
    if [ "$1" = "debug" ]; then
        JAVA_DEBUG_OPTS=" -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n "
    fi
    JAVA_JMX_OPTS=""
    if [ "$1" = "jmx" ]; then
        JAVA_JMX_OPTS=" -Dcom.sun.management.jmxremote.port=1099 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false "
    fi
    JAVA_MEM_OPTS=""
    BITS=$(java -version 2>&1 | grep -i 64-bit | grep -v grep)

    JAVA_MEM_OPTS=" -server -Xms256m -Xmx256m -XX:SurvivorRatio=2 -XX:+UseParallelGC "

    #start server
    cd $DEPLOY_DIR
    echo -e "Starting the $SERVER_NAME ...\c"
    nohup java $JAVA_OPTS $JAVA_MEM_OPTS $JAVA_DEBUG_OPTS $JAVA_JMX_OPTS -classpath $CONF_DIR:$OUT_PROJECT_CONFIG_DIR:$OUT_JARS_DIR:$LIB_JARS $MAIN_CLASS > $STDOUT_FILE 2>&1 &
    cd $BIN_DIR
}


# check if server started
function check_is_started()
{
    typeset -i COUNT=0
    typeset -i time_out_count=0
    while [ $COUNT -lt 1 ]; do
        if [ $time_out_count -gt 30 ]; then
            echo -e "\n start time out\n"
            exit 1
        fi

        echo -e ".\c"
        sleep 1
        if [ -n "$SERVER_PORT" ]; then
        	if [ "$SERVER_PROTOCOL" == "dubbo" ]; then
				COUNT=$(echo status | nc -i 1 127.0.0.1 $SERVER_PORT | grep -c OK)
			else
				COUNT=$(netstat -an | grep $SERVER_PORT | grep -v grep | wc -l)
			fi
		else
			COUNT=$(ps -f | grep java | grep "$DEPLOY_DIR" | grep -v grep | awk '{print $2}' | wc -l)
		fi

		if [ $COUNT -gt 0 ]; then
			break
		fi
		((time_out_count++))
	done

	echo "OK!"
	PIDS=$(ps -f | grep java | grep "$DEPLOY_DIR" | grep -v grep | awk '{print $2}')
	echo "PID: $PIDS"
	echo "STDOUT: $STDOUT_FILE"
}

function main()
{
	check_pids
	check_port
	check_stdout_file
	start_server
	check_is_started
}

main
