#!/bin/sh
APP="com.xmgps.taxi.datareportserver.server.App"
PROC_NAME="RentDataReportServer"

CLASS_PATH="./classes:./lib/*:./config:./*"

_current_path() {
    SOURCE=${BASH_SOURCE[0]}
    DIR=$( dirname "$SOURCE" )
    while [ -h "$SOURCE" ]
    do
        SOURCE=$(readlink "$SOURCE")
        [[ $SOURCE != /* ]] && SOURCE="$DIR/$SOURCE"
        DIR=$( cd -P "$( dirname "$SOURCE"  )" && pwd )
    done
    DIR=$( cd -P "$( dirname "$SOURCE" )" && pwd )
    echo $DIR
}

#start shell
start_run(){
   echo "Application starting"
   exec -a $PROC_NAME java -cp $CLASS_PATH -XX:+HeapDumpOnOutOfMemoryError -Xmx2048m $APP > $CUR_DIR/console.txt 2>&1 &
   echo "Application started"
   echo $! > $CUR_DIR/pid.txt
}

CUR_DIR=$(_current_path)

echo "PATH: $CUR_DIR"
cd $CUR_DIR

if [ ! -f "$CUR_DIR/pid.txt" ]; then 
   #pid.txt is not exist, then the process is not exist!
   start_run
else 
   if
      ps ax | awk  '{print $1}' | grep -x $(cat $CUR_DIR/pid.txt)
   then
      echo "The process is running, please exec 'sh stop.sh' first!"
   else
   	  #echo "The process is running..."
      start_run
   fi
fi
