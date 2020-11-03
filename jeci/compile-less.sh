#!/usr/bin/env bash
# This script transpiles the less files from JECI web moduel and copies the resulting CSS file into the target web modules.
# It needs to have Node JS and the lessc command installed (npm install -g less)

pushd jeci-web-commons/src/main/less/
lessc styles.less styles.css
popd
cp jeci-web-commons/src/main/less/styles.css ../midat-sources/mds-web/target/midat-sources/resources/css
cp jeci-web-commons/src/main/less/styles.css ../midat/midat-web/target/midat-sources/resources/css
cp jeci-web-commons/src/main/less/styles.css ../infofauna/infofauna-web/target/midat-sources/resources/css