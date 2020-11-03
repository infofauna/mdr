#!/usr/bin/env bash

if [ $# -eq 0 ]
  then
    echo "No arguments supplied. Will be using the default profile : dev"
    profile=dev
  else
    profile=$1
fi

pushd ./jeci
mvn -P $profile clean install
popd
pushd ./infofauna
mvn -P $profile clean install
popd
pushd ./midat
mvn -P $profile clean install
popd


rm -rf distro
mkdir distro
cp ./infofauna/infofauna-web/target/*.war ./distro/
cp ./midat/midat-web/target/*.war ./distro/
cp -r ./jeci/jeci-static-website ./distro/

