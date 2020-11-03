#!/usr/bin/env bash

if [ $# -eq 0 ]
  then
    echo "No arguments supplied. You must supply the target profile."
    exit -1
fi


case "$1" in
"dev")
    host=vdelta30.unine.ch
    ;;
"test")
    host=vdelta33.unine.ch
    ;;
"prod")
    host=venigma40.unine.ch
    ;;
*)
    echo "The target profile must be one of 'dev', 'test' or 'prod'"
    exit -1
esac

pushd ./jeci
mvn -P $1 clean install -Dmaven.test.skip=true
popd


pushd ./midat
mvn -P $1 clean install  -Dmaven.test.skip=true
popd

echo Copying MIDAT war for profile : $1 to $host

mv  distro/midat*.war  distro/midat.war
scp distro/midat.war sykanso@$host:/app/stage



