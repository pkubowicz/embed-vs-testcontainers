set terminal png size 1000,600
set output 'results/all-times.png'

set timefmt "%M:%S"
set ydata time
set style fill solid
set yrange[0:80]
set grid lt 0 lw 0.5
set title "Test execution time (minutes:seconds)"
plot "results/all-times.dat" using 2:xtic(1) with histogram notitle
