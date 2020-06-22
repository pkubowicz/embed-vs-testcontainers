#!/usr/bin/env bash

INTERVAL=$1
COUNT=$2

COMMAND="pgrep --count --full mongod"
[[ ! -f $(which pgrep) ]] && COMMAND="ps aux | grep mongod | grep --invert-match grep | wc -l"

for i in $(seq $COUNT); do
    echo -e -n $(date '+%H:%M:%S') '\t'
    eval $COMMAND
    sleep $INTERVAL
done
