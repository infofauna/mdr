#!/usr/bin/env bash

pushd ./jeci
git pull
popd

pushd ./infofauna
git pull
popd

pushd ./midat
git pull
popd

pushd ./midat-sources/
git pull
popd
