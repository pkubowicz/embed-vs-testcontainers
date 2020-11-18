Compares performance of [Flapdoodle Embedded MongoDB](https://github.com/flapdoodle-oss/de.flapdoodle.embed.mongo/) and [Testcontainers](https://www.testcontainers.org/modules/databases/mongodb/).

The benchmark measures CPU and memory usage during a Gradle build executing tests of Spring Boot projects: `mongo-embed` and `mongo-testcontainers`. The code of these two projects is identical, except for the part connecting to the test MongoDB instance. Each consists of 3 subprojects which can be built in parallel. Test code is written in a way to maximize Spring context cache misses, so it shows the worst case for Flapdoodle: each test class will start a new instance of the embedded database.

See example results: https://pkubowicz.github.io/embed-vs-testcontainers/

## Scenarios

Both solutions are compared when executing a parallel Gradle build and a non-parallel ('serial') build. Additionally, Testcontainers are executed both with container reuse flag turned off and turned on.

This gives 6 combinations. After running the benchmark, each combination will appear as a directory inside `./results/`. Inside you will find flat files showing how global system CPU usage, memory usage and number of MongoDB instances changes over time, and plots in form of PNG graphics.

You can see an overview of all scenarios by opening `results.html`.

## Running

The code is tested on Ubuntu, but also works on MacOS (with limited functionality).

First check your system is able to run benchmarks:
```
./pre-benchmark.sh
```

You may see a prompt to install gnuplot - do it.

Then execute:
```
./benchmark.sh
```
