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

Compilation/Batch Running
-------------------------
The src directory contains a Makefile to clean the directory, run the compile the code, run the tests, produce documentation, and finally pass the available input through the evaluator.

Makefile usage is outlined below.
```bash
make          # run the entire suite
make notest   # run the entire suite without unittests
make compile  # compile the source
make test     # compile and run the tests
make doc      # generate documentation
make run      # run the code against the two files supplied in the inputs directory
```

Driver
------
After compilation, the main driver can be run to:  
1.  Create a tree from an input file  
2.  Calculate and print the height  
3.  Calculate and print the number of leaves  
4.  Calcualte and print the number of nonleaves  
5.  Print the post-order traversal of the tree  

Input files must simple be space separated integers or strings (no mixing within a file).  All contents of a given file will be read into a single tree.

The driver can be called on separate intput files as shown below.  Output will be displayed to STDOUT.

```bash
java Driver ../input/sample_input.txt
java Driver ../input/sample_string_input.txt
```
