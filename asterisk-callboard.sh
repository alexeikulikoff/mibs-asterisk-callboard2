#!/bin/bash
#
# /etc/init.d/asterisk-callboard
#
# Startup script for Asterisk-callboard
#
# chkconfig: 2345 80 20
# description: Starts and stops Asterisk-callboard
# pidfile: /var/run/callboard.pid

### BEGIN INIT INFO
# Provides:          asterisk-callboard
# Required-Start:    
# Required-Stop:     
# Should-Start:      
# Should-Stop:       
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: distributed storage system for structured data
# Description:       Asterisk-callboard is a distributed (peer-to-peer) system for
#                    the management call-centers
### END INIT INFO

YML="application.properties"
CONFIG="/usr/local/etc/mibs-asterisk-callboard/"$YML

INSTALL_DIR="/usr/local/bin/"
JAR=$INSTALL_DIR"mibs-asterisk-callboard.jar"

PID="/var/run/mibs-asterisk-callboard.pid"

LOG="/var/log/asterisk-callboard.log"

# If JAVA_HOME has not been set, try to determine it.
if [ -z "$JAVA_HOME" ]; then
    # If java is in PATH, use a JAVA_HOME that corresponds to that. This is
    # both consistent with how the upstream startup script works, and with
    # the use of alternatives to set a system JVM (as is done on Debian and
    # Red Hat derivatives).
    java="`/usr/bin/which java 2>/dev/null`"
    if [ -n "$java" ]; then
        java=`readlink --canonicalize "$java"`
        JAVA_HOME=`dirname "\`dirname \$java\`"`
    else
        # No JAVA_HOME set and no java found in PATH; search for a JVM.
        for jdir in $JVM_SEARCH_DIRS; do
            if [ -x "$jdir/bin/java" ]; then
                JAVA_HOME="$jdir"
                break
            fi
        done
        # if JAVA_HOME is still empty here, punt.
    fi
fi
JAVA="$JAVA_HOME/bin/java"
export JAVA_HOME JAVA

case "$1" in
    start)
   	 	$JAVA -jar $JAR $CONFIG 2>&1 >>$LOG &
		RES=$!
		echo $RES > $PID
    	;;
   stop)
   		kill -9 `cat $PID`
   		;;
    *)
     echo "Usage: `basename $0` start|stop"
     exit 1
esac

exit 0       
   
