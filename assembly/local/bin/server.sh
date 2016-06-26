#!/bin/bash
cd $(dirname $0)
case $1 in 
	start) 
		./start.sh
        ;; 
    stop) 
		./stop.sh         
		;;
	debug)
		./start.sh debug
		;;
	restart)
		./restart.sh
		;;
	dump)
		./dump.sh
		;; 
	status)
		./status.sh
		;;
    *) 
    echo "Usage: $0 start|stop|debug|restart|dump|status" 
    ;; 
esac
