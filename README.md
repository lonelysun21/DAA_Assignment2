# DAA Assignment 2 - Data Structures

**Student:** Zhakanov Sanzhar  
**Group:** SE-2526

## Project Description

This project implements three data structures from scratch:

- DynamicArray
- MyLinkedList
- MinHeap

The project also includes:

- operation counters for steps, moves, and comparisons;
- JUnit 5 tests;
- four benchmark workloads;
- CSV result export;
- benchmark plots;
- complexity analysis;
- loop invariant proofs.

## Requirements

- Java 17
- Maven

## Build Project

```bash
mvn clean compile
```

## Run Tests

```bash
mvn test
```

## Run Benchmark

Run the `BenchmarkRunner` class from IntelliJ IDEA.

The benchmark generates:

```text
results/results.csv
```

## Generate Plots

Run the `PlotGenerator` class from IntelliJ IDEA.

Generated plots are saved in:

```text
results/plots/
```

## Project Structure

```text
src/main/java/structures
src/main/java/metrics
src/main/java/benchmark
src/test/java/structures
results/results.csv
results/plots/
REPORT.md
README.md
```

## Results

Benchmark results are stored in:

```text
results/results.csv
```

Benchmark plots are stored in:

```text
results/plots/
```

The complexity analysis, loop invariant proofs and benchmark discussion are available in:

```text
REPORT.md
```