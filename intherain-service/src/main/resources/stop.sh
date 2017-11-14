#!/bin/sh
APP="com.xmgps.taxi.datareportserver.server.App"

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

CUR_DIR=$(_current_path)

echo "PATH: $CUR_DIR"
cd $CUR_DIR

kill `cat $CUR_DIR/pid.txt`
echo "Application exited"

