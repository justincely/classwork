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
make test        # compile and run the tests with supplied input files
make unittest    # compile and tun the unit tests with the JUnit framework
make doc         # generate documentation
make lab         # run the code against the input file specified in the lab
```

Individual Usage
-----
```java
```

Output
------
The program prints the instructions to
standard output.  Writing to a file can be done using redirection:
```java
java Driver File1.txt >> simple_out.txt
```

Limitations
-----------
