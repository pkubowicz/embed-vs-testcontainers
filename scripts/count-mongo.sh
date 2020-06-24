#!/usr/bin/env bash

INTERVAL=$1
COUNT=$2

for i in $(seq $COUNT); do
    echo -e $(date '+%H:%M:%S') '\t' $(pgrep -f mongod | wc -l)
    sleep $INTERVAL
done
