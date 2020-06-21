set timefmt "%H:%M:%S"
set xdata time
set style data lines
plot "/dev/stdin" using 1:2 with lines notitle
