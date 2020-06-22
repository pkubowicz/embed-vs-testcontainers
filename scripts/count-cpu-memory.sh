#!/usr/bin/env bash

INTERVAL=$1
COUNT=$2
RESULTS=$3

which sar >/dev/null
if [[ $? == 0 ]]; then
    sar -o $RESULTS/sar.binary $INTERVAL $COUNT >/dev/null 2>&1
else
    for i in $(seq $COUNT); do
        echo -e -n $(date '+%H:%M:%S') '\t' >>$RESULTS/memory.dat
        vm_stat | sed 's/.$//' | awk '{if ($2=="inactive:") print int($3*4096000/1048576)}' >>$RESULTS/memory.dat
        sleep $INTERVAL
    done
fi
