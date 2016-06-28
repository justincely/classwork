Directory Contents  
------------------
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

Output
------
