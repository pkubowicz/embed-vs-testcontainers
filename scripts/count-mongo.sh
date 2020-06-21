#!/usr/bin/env bash

INTERVAL=$1
COUNT=$2

for i in $(seq $COUNT); do
    echo -n $(date '+%H:%M:%S')
    echo -n -e '\t'
    echo $(pgrep --count --full mongod)
    sleep $INTERVAL
done
