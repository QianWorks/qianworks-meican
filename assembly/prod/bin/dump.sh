#!/bin/bash

if [ $(id -u) -ne 0 ];then  
    echo "only root can execute" 
	exit 1 
fi

cd $(dirname $0)

source ./init.sh

# check PIDS
function check_pids()
{
	typeset PIDS
	PIDS=$(ps -ef | grep java | grep "$CONF_DIR" |awk '{print $2}')
	if [ -z "$PIDS" ]; then
    	echo "ERROR: The $SERVER_NAME does not started!"
    	exit 1
	fi
}

# check logs dir

typeset LOGS_DIR
function check_log_dir()
{
	if [ -n "$LOGS_FILE" ]; then
		LOGS_DIR=$(dirname $LOGS_FILE)
	else
		LOGS_DIR=$DEPLOY_DIR/logs
	fi
	if [ ! -d $LOGS_DIR ]; then
		mkdir $LOGS_DIR
	fi
}

#check dump dir
typeset DUMP_DIR DATE_DIR
function check_dump_dir()
{
	DUMP_DIR=$LOGS_DIR/dump
	if [ ! -d $DUMP_DIR ]; then
		mkdir $DUMP_DIR
	fi
	typeset DUMP_DATE=$(date +%Y%m%d%H%M%S)
	DATE_DIR=$DUMP_DIR/$DUMP_DATE
	if [ ! -d $DATE_DIR ]; then
		mkdir $DATE_DIR
	fi
}

# dump
function dump()
{
	echo -e "Dumping the $SERVER_NAME ...\c"
	for PID in $PIDS ; do
		jstack $PID > $DATE_DIR/jstack-$PID.dump 2>&1
		echo -e ".\c"
		jinfo $PID > $DATE_DIR/jinfo-$PID.dump 2>&1
		echo -e ".\c"
		jstat -gcutil $PID > $DATE_DIR/jstat-gcutil-$PID.dump 2>&1
		echo -e ".\c"
		jstat -gccapacity $PID > $DATE_DIR/jstat-gccapacity-$PID.dump 2>&1
		echo -e ".\c"
		jmap $PID > $DATE_DIR/jmap-$PID.dump 2>&1
		echo -e ".\c"
		jmap -heap $PID > $DATE_DIR/jmap-heap-$PID.dump 2>&1
		echo -e ".\c"
		jmap -histo $PID > $DATE_DIR/jmap-histo-$PID.dump 2>&1
		echo -e ".\c"
		if [ -r /usr/sbin/lsof ]; then
		/usr/sbin/lsof -p $PID > $DATE_DIR/lsof-$PID.dump
		echo -e ".\c"
		fi
	done

	if [ -r /bin/netstat ]; then
		/bin/netstat -an > $DATE_DIR/netstat.dump 2>&1
		echo -e ".\c"
	fi
	if [ -r /usr/bin/iostat ]; then
		/usr/bin/iostat > $DATE_DIR/iostat.dump 2>&1
		echo -e ".\c"
	fi
	if [ -r /usr/bin/mpstat ]; then
		/usr/bin/mpstat > $DATE_DIR/mpstat.dump 2>&1
		echo -e ".\c"
	fi
	if [ -r /usr/bin/vmstat ]; then
		/usr/bin/vmstat > $DATE_DIR/vmstat.dump 2>&1
		echo -e ".\c"
	fi
	if [ -r /usr/bin/free ]; then
		/usr/bin/free -t > $DATE_DIR/free.dump 2>&1
		echo -e ".\c"
	fi
	if [ -r /usr/bin/sar ]; then
		/usr/bin/sar > $DATE_DIR/sar.dump 2>&1
		echo -e ".\c"
	fi
	if [ -r /usr/bin/uptime ]; then
		/usr/bin/uptime > $DATE_DIR/uptime.dump 2>&1
		echo -e ".\c"
	fi

	echo "OK!"
	echo "DUMP: $DATE_DIR"
}

function main()
{
	check_pids
	check_log_dir
	check_dump_dir
	dump
}

main
