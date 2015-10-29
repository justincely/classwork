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

Makefile usage is outlined below.
```bash
make          # run the entire stuite
make compile  # compile the source
make test     # compile and run the tests
make doc      # generate documentation
make run      # run the code against the two files supplied in the inputs directory
```

Individual Usage
-----
The main driver is Driver.java in the src directory.
```java
$ java Driver File1.txt
$ java Driver File1.txt File2.txt
# java Driver *.txt
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
1.  Floating-point numbers are not supported.
2.  Precision higher than the standard int is not supported.
