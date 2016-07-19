Dev Environment
---------------
The Atom editor was used for code development, and the command line
was used to compile and run source code.

The java version used for dev and test was:

```bash
openjdk version "1.8.0_91"  
OpenJDK Runtime Environment (build 1.8.0_91-8u91-b14-0ubuntu4~16.04.1-b14)  
OpenJDK 64-Bit Server VM (build 25.91-b14, mixed mode)  
```
all running on Ubuntu 16.04.

Directory Contents  
------------------
*  ./input/: input text files to be evaluated
*  ./output/: output text files from the unit tests and test runs
*  ./src/: raw and compiled source java code, long with Makefile for testing, compilation, documentation
*  ./src/docs/: java autodoc output

SRC Files
---------
*  Driver.java:  Program to run problem 1A on the specified input.
*  InsertionSort.java:  insertion sort functions.  
*  MultiStack.java:  Multistack class to implement PushA, PushB, MultiPopA,...
*  RadixSort.java:  Radix sort functions
*  Test*.java: junit unit tests for

Compilation
-----------
The src directory contains a Makefile to clean the directory, run the compile the code, run the tests.

Makefile usage is outlined below.
```bash
make          # run the entire suite
make notest   # run the entire suite without unittests
make compile  # compile the source
make test     # compile and run the tests
make doc      # generate documentation
make run      # run the main driver
```

Problem sets
------------
Problem 1a is illustrated via the Driver.java, which uses the RadixSort function
on the input given in the assignment.

Problem 2 is solved in the MultiStack.java class.  Where each of the functions
needed have been implemented. 
