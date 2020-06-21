#!/usr/bin/env bash

set -e

gnuplot --version

if [[ ! -f $HOME/.testcontainers.properties ]]; then
    echo CREATING ~/.testcontainers.properties
    echo testcontainers.reuse.enable=true > $HOME/.testcontainers.properties
fi

# fill the Gradle cache with embedded Mongo
cd mongo-embed
./gradlew -q core:test
cd ..

# make sure this system can run Testcontainers
cd mongo-testcontainers
./gradlew -q core:test
cd ..

if [[ -z $(which service) ]]; then
    pgrep mongod >/dev/null && echo It is recommended that you stop your Mongo instance or count of Mongo instances will start from 1 instead of 0
else
    pgrep mongod >/dev/null && sudo service mongod stop
fi

echo Ready to run benchmarks!
