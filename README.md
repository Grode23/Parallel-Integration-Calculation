# Parallel Integration Calculation

The point of this project is the understanding of some concurrency objects.

This project is not meant to be the best solution for findng pi, algorithm was already given and emphasis was placed to parallelism.

## Solutions
  * ThreadPool
  * ForkJoin (binary tree)

## Result
Number of steps (numSteps) variable is that much (10 billions) for there to be an appreciable difference in computing time.
  * ### ThreadPool:
  ```
sequential program results with 10000000000 steps
computed pi = 3.14159265358979360000
difference between estimated pi and Math.PI = 0.00000000000000044409
time to compute = 32.096000 seconds
```
  * ### ForkJoin:
```
sequential program results with 10000000000 steps
computed pi = 3.14159265358979360000
difference between estimated pi and Math.PI = 0.00000000000000044409
time to compute = 6.078000 seconds
```
