Directory Contents  
------------------
*  ./analysis/: Lab write-up  
*  ./input/: input text files to be evaluated
*  ./output/: output text files from runs against the included input
*  ./src/: raw and compiled source java code, long with Makefile for testing, compilation, documentation
*  ./src/docs/: java autodoc output

Compilation/Batch Running
-------------------------
The src directory contains a Makefile to clean the directory, run the compile the code, run the tests, produce documentation, and finally pass the available input through the evaluator.

Note that the JUnit library is required to run the unittests.  If JUnit is not installed, "make nounittest" should be run to avoid that section of the code. 

Makefile usage is outlined below.
```bash
make             # run the entire suite
make nounittest  # run the entire suite without the JUnit unittest framework
make compile     # compile the source
make unittest    # compile and tun the unit tests with the JUnit framework
make clean       # clean up all output files
make timing      # run all the sorting methods and output to timing files
make doc         # generate documentation
make lab         # run the code against the input files
```

Individual Usage
-----
```java 
java Driver inputFile outputFile sortingMethod
```
Sorting method can be any of: insertion, heap, heaprecursive, quick, quickmed.  For quick and quickmed, a 4th optional arguemtn can be supplied to indicate the minumum partition size to employ insertion sort.
