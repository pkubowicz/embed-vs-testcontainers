#!/usr/bin/env bash

print_summary () {
    echo $(tput bold)$(pwd | sed 's/.*\///')$(tput sgr0)
    cat time.dat
    echo -e "CPU\t" $(cat cpu.dat | tail -n +2 | sed '$ d' | awk '{ total += $2; count++; if ($2>max) max=$2; } END { print "Max\t"  max "\tAverage\t" int(total/count) }')
    echo -e "Memory\t" $(cat memory.dat | tail -n +2 | sed '$ d' | awk '{ total += $2; count++; if ($2>max) max=$2; } END { print "Max\t"  max "\tAverage\t" int(total/count) }')
    echo -e "Mongo\t" $(cat mongo-count.dat | awk '{ total += $2; count++; if ($2>max) max=$2; } END { print "Max\t"  max "\tAverage\t" total/count }')
    echo
}

run_benchmark () {
    RESULTS="results/$1"
    CODE=$2
    MAX_DURATION=$3

    PARALLEL=""
    [[ $RESULTS == *parallel* ]] && PARALLEL="--parallel"
    GRADLE_CMD="test $PARALLEL"
    echo $(tput bold)Running \'gradle $GRADLE_CMD\' in $CODE/ reporting to $RESULTS/$(tput sgr0)
    echo

    mkdir -p $RESULTS
    cd $CODE
    ./gradlew -q testClasses cleanTest --parallel
    rm -f ../$RESULTS/*
    echo Used memory: $(free | awk '{if (NR == 2) print $3}')
    sar -o ../$RESULTS/sar.binary 2 $(($MAX_DURATION/2)) >/dev/null 2>&1 &
    ../scripts/count-mongo.sh 2 $(($MAX_DURATION/2)) > ../$RESULTS/mongo-count.dat &
    sleep 2 # sar records first probe after 2 seconds
    /usr/bin/time -f '%E %U %S' -o ../$RESULTS/time.dat ./gradlew --offline $GRADLE_CMD
    sleep 10 # collecting runs in background, give it some time to flatten

    cd ../$RESULTS
    sar -f sar.binary -u 2 | tail -n +3 | awk '{print $1 "\t" $3 "\t" $5}' > cpu.dat
    sar -f sar.binary -r 2 | tail -n +3 | awk '{print $1 "\t" $4}' > memory.dat
    cat cpu.dat | tail -n +2 | sed '$ d' | gnuplot -e "set terminal png size 800,600; set output 'cpu.png'"  ../../gnuplot.cfg
    cat memory.dat | tail -n +2 | sed '$ d' | gnuplot -e "set terminal png size 800,600; set output 'memory.png'"  ../../gnuplot.cfg
    cat mongo-count.dat | gnuplot -e "set terminal png size 800,600; set output 'mongo-count.png'" ../../gnuplot.cfg
    print_summary
    cd ../../
}

run_all () {
    pgrep --full embedmongo | xargs kill -9 2>/dev/null
    run_benchmark "embed-parallel" "mongo-embed" 60
    pgrep --full embedmongo | xargs kill -9 2>/dev/null
    run_benchmark "embed-serial" "mongo-embed" 80
    pgrep --full embedmongo | xargs kill -9 2>/dev/null

    sed -i 's/\(testcontainers.reuse.enable=\).*/\1false/' ~/.testcontainers.properties
    run_benchmark "testcontainers-parallel" "mongo-testcontainers" 40
    run_benchmark "testcontainers-serial" "mongo-testcontainers" 60

    sed -i 's/\(testcontainers.reuse.enable=\).*/\1true/' ~/.testcontainers.properties
    run_benchmark "testcontainers-parallel-reuse" "mongo-testcontainers" 40
    run_benchmark "testcontainers-serial-reuse" "mongo-testcontainers" 60
}

print_all_summaries () {
    MODES="
    embed-parallel
    embed-serial
    testcontainers-parallel
    testcontainers-serial
    testcontainers-parallel-reuse
    testcontainers-serial-reuse
    "
    for MODE in $MODES
    do
        cd results/$MODE
        print_summary
        cd ../../
    done
}

run_all
print_all_summaries
